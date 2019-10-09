package ShortestPath;

import Areas.Area;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

public class Dijkstra {

    private ArrayList<Area> open;
    public int distance = 0;

    public Dijkstra() {
        open = new ArrayList<>();
    }

    /*
    Calculates shortest path between 2 nodes
    @par Node start = starting point
    @par Node end = goal to reach
     */
    public ArrayList<Area> Dijkstra(Area start, Area end) {

    	Area toCheck = start;

        while(!Visit(toCheck, end)) {

            //Get the best option from the to-visit list
            Comparator<Area> comp = (n1, n2) -> Integer.compare(n1.distance, n2.distance);
            toCheck = open.stream().min(comp).get();
        }


        return makePath(end);
    }

    public  boolean Visit(Area check, Area end) {


        distance = check.distance;

        //check if we reached the end
        if(check == end) {
            return true;
        }

        //remove the current node from the toCheck / open list
        if(open.contains(check)) {
            open.remove(check);
        }

        //check neighbours
        for (Map.Entry<Area, Integer> entry : check.neighbours.entrySet()) {

            //Distance to node if current node is followed
            int newDistance = check.distance + entry.getValue();

            //If the newly calculated distance is shorter than the current distance
            if(newDistance < entry.getKey().distance) {

                //if so, we replace or add the distance
                entry.getKey().distance = newDistance;
                entry.getKey().latest = check;

                //check if we have seen the Node before, or if we have to add it to our to-visit list
                if(!open.contains(entry.getKey())) {
                    open.add(entry.getKey());
                }
                //debug info
//                System.out.println("Added to open: " + entry.getKey().id);
            }
        }

        return false;
    }


    /*
    Makes the path by walking from the end to the start and following the shortest path
    @par end = Node to walk from
     */
    private ArrayList<Area> makePath(Area end) {
        boolean cont = true;

        Area current = end;

        ArrayList<Area> pathWithAreas = new ArrayList<Area>();
        

        while (cont){

        	pathWithAreas.add(current);
        	

            //Check if we reached the end
            if(current.latest != null) {
                current = current.latest;
            }
            else {
                cont = false;
            }

        }

        return pathWithAreas;
    }
}
