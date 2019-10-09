package ShortestPath;

import java.util.HashMap;

public class Node {

    public HashMap<Node, Integer> neighbours;
    public int distance;
    public Node latest;
    public String name;

    public Node(String _name){
        neighbours = new HashMap<>();
        distance = Integer.MAX_VALUE;
        latest = null;
        name = _name;

    }

}
