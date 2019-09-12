package hw6;
import java.io.*;
import java.util.*;
import java.util.HashMap.*;
import java.time.Duration;
import java.time.Instant;

public class YelpAnalysis {
	private static BufferedReader buffreader; 
	public static int the_count = 0; 
	public static void openBusiness() throws FileNotFoundException {
		
		InputStream in = new FileInputStream("yelpDatasetParsed_medium.txt");
		Reader r = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(r); 
		buffreader = br; 
	}

	public static Business readBusiness() throws IOException {
		String line = buffreader.readLine(); 
		if(line!=null) {
			String[] items = new String[4]; 
			items = line.split(","); 
			return new Business(items[0].substring(1), items[1].substring(1), items[2].substring(1), items[3].substring(0,items[3].length()-1)); 
		}
		else
			return null; 


	}

	public static void closeBusiness() throws Exception {
		buffreader.close(); 
	}

	public static void addDocumentCount(Map<String, Integer> corpus, Business b) {

		Set<String> already_added = new HashSet<String>(); 
		for(String word : b.reviews.split(" ")) {
			if(corpus.containsKey(word)==true && already_added.contains(word)==false && word.equals("")==false) {
				corpus.put(word,corpus.get(word)+1); 
				already_added.add(word); 
				/*if(word.equals("the")){
					the_count++;
					System.out.println(the_count);
				}*/
			}
			else if(corpus.containsKey(word)==false && already_added.contains(word)==false && word.equals("")==false ) {
				corpus.put(word,1);
				already_added.add(word); 
			}



		}


	}

	public static Map<String, Double> getTfidfScore(Map<String, Integer> corpus, Business b, int lower_limit) {
		Map<String, Double> results = new HashMap<String, Double>();
		int single_doc_count;  
		for(String word : corpus.keySet()) {
			single_doc_count = 0; 
			String reviews = b.getreviews(); 
			if(corpus.get(word)<lower_limit) 
				results.put(word, 0.0); 
			else if(reviews.contains(word)==false)
				results.put(word, 0.0);
			else {
				for(String doc_word : reviews.split(" ")) {
					if(doc_word.equals(word))
						single_doc_count++; 
				}
				results.put(word, (double) single_doc_count / corpus.get(word));
			}



		}
		return results; 

	}

	public static void SortByTfidf(List<Map.Entry<String, Double>> tfids ) {

		tfids.sort(Map.Entry.comparingByValue(Comparator.reverseOrder())); 
	}

	public static void printTopWords(List<Map.Entry<String, Double>> tfids, int num_listed) {

		for(int i = 0; i<num_listed; i++) {

			System.out.print("("+tfids.get(i).getKey()+","+String.format("%.2f", tfids.get(i).getValue())+")"); //idk what to do here
		}
		System.out.println(""); 
	}





	public static void main(String[] args) throws Exception {
		Instant start = Instant.now();
		openBusiness(); 
		Map<String, Integer> corpusDFCount = new HashMap<String, Integer>(); 
		List<Business> businessList = new ArrayList<Business>(85538); 
		while(true) {
			Business b = readBusiness();
			if(b == null)
				break; 
			businessList.add(b); 


		}
		closeBusiness(); //this might not work 
		

		for(Business b : businessList) {
			addDocumentCount(corpusDFCount, b); 

		}
		
		

		YelpAnalysis.Lex compare = new YelpAnalysis().new Lex();

		Collections.sort(businessList, compare.reversed()); // idk if this correct? 
		//now that busniessList is sorted probably need a queue coming out of this 
		

		//Queue<Business> businessQueue = new PriorityQueue<Business>(businessList); 
		
		for(int i = 0; i<10; i++) {
			Map<String, Double> tfidfScoreMap = getTfidfScore(corpusDFCount , businessList.get(i) , 5);  //dont have all inputs but function should work 

			List<Map.Entry<String, Double>> tfidfScoreList = new ArrayList<>(tfidfScoreMap.entrySet()); 

			SortByTfidf(tfidfScoreList);

			System.out.println(businessList.get(i)); 

			printTopWords(tfidfScoreList, 30); 


			}

		


		Instant end = Instant.now(); 
		Duration interval = Duration.between(start, end);  
		System.out.println("total time: "+interval.getSeconds());
		}

	private class Lex implements Comparator<Business> {
		public int compare(Business b1, Business b2) {
			if(b1.getreviewlength()>b2.getreviewlength())
				return 1; 
			else if(b1.getreviewlength()<b2.getreviewlength())
				return -1;
			else
				return 0; 

		}
	}










}


