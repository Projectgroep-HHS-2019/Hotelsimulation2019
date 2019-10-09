package Factories;

import Areas.Cinema;
import Areas.Elevator;
import Areas.Fitness;
import Areas.HotelRoom;
import Areas.Lobby;
import Areas.Restaurant;
import Areas.Stairway;
import Areas.Area;

/**
 * The AreaFactory allows you to create a Area.
 *
 */

public class AreaFactory {
	
	public static Area createArea(int id, String areaType, int dimensionW, int dimensionH, int stars, long capacity, int x, int y){

	      if(areaType == null)
	      {
	         return null;
	      }
	      
	      if(areaType.equalsIgnoreCase("Cinema")) 
	      {
	    	  return new Cinema(id, dimensionW, dimensionH, x, y, areaType);
	      } 
	      
	      else if(areaType.equalsIgnoreCase("Elevator"))
	      {
	         return new Elevator(id, dimensionW, dimensionH, x, y, areaType);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Fitness"))
	      {
	         return new Fitness(id, dimensionW, dimensionH, x, y, areaType);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Room"))
	      {
	         return new HotelRoom(id, dimensionW, dimensionH, stars, x, y, areaType);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Lobby"))
	      {
	         return new Lobby(id, dimensionW, dimensionH, x, y, areaType);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Restaurant"))
	      {
	         return new Restaurant(id, dimensionW, dimensionH, capacity, x, y, areaType);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Stairway"))
	      {
	         return new Stairway(id, dimensionW, dimensionH, x, y, areaType);
	      }
	      
	      return null;
	   }
}
