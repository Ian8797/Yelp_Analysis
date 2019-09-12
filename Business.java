package hw6;

import java.io.*; 
import java.util.*;

public class Business {
  String businessID;
  String businessName;
  String businessAddress;
  String reviews;
  int reviewCharCount;
   

  public Business (String id, String name, String address, String review) {

    businessID = id; 
    businessName = name; 
    businessAddress = address; 
    reviews = review; 
    reviewCharCount = review.length(); 
  }

  public int getreviewlength() {
    return reviewCharCount; 
  }
  public String getreviews() {
    return reviews; 
  }
  
  
  public String toString() {
    return "-------------------------------------------------------------------------------\n"
          + "Business ID: " + businessID + "\n"
          + "Business Name: " + businessName + "\n"
          + "Business Address: " + businessAddress + "\n"
          //+ "Reviews: " + reviews + "\n"
          + "Character Count: " + reviewCharCount;
  }
}