package Factories;

import Persons.Cleaner;
import Persons.Guest;
import Persons.Person;

/**
 * The PersonFactory allows you to create a Person (guest/cleaner).
 *
 */

public class PersonFactory {

	public static Person createPerson(String personType, String status, int id, boolean visibility, int x, int y){

	      if(personType == null){
	         return null;
	      }
	      if(personType.equalsIgnoreCase("GUEST")) {
	    	  return new Guest(status, id, visibility, x, y);
	      } 
	      else if(personType.equalsIgnoreCase("CLEANER")){
	         return new Cleaner(status, visibility, x, y);
	      }
	      
	      return null;
	   }
}

