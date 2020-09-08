import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

public class Graph {

    private List<Edge> edgeList;
    private int noOfNodes;
    private Node[] nodes;
    private int noOfEdges;

    public Graph(List<Edge> edgeList) {

        this.edgeList = edgeList;
        this.noOfNodes = calculateNoOfNodes(edgeList);
        this.nodes= new Node[this.noOfNodes];
        //create all nodes ready to be updated with edges
        for(int n=0; n<this.noOfNodes; n++){
            this.nodes[n] = new Node();
        }
        //add all the edges to the nodes, each edge added to two nodes(to and from)
        this.noOfEdges = edgeList.size();
        for(int edgeToAdd=0; edgeToAdd< this.noOfEdges; edgeToAdd++){
            this.nodes[edgeList.get(edgeToAdd).getFromNodeIndex()].getEdges().add(edgeList.get(edgeToAdd));
            this.nodes[edgeList.get(edgeToAdd).getToNodeIndex()].getEdges().add(edgeList.get(edgeToAdd));
        }
    }
    private int calculateNoOfNodes(List<Edge> edgeList){
        int noOfNodes = 0;
        for(Edge e : edgeList){
            if(e.getToNodeIndex() > noOfNodes)
                noOfNodes = e.getToNodeIndex();
            if(e.getFromNodeIndex() > noOfNodes)
                noOfNodes= e.getFromNodeIndex();
        }
        noOfNodes++;
        System.out.println(noOfNodes-1);
        return noOfNodes;
    }

    public Node[] getNodes(){
        return nodes;
    }
    public int getNoOfNodes() {
        return noOfNodes;
    }
    public List<Edge> getEdges(){
        return edgeList;
    }
    public int getNoOfEdges(){
        return noOfEdges;
    }
    public static JSONObject LoadMap() throws IOException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
            String filePath = "src/main/resources/data/locationsIndex.json";
            jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
        } catch (FileNotFoundException | ParseException err) {
            System.out.println("Can't open locationsIndex file");
        }

        return jsonObject;
    }

    public static void main (String[] arg) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject map = LoadMap();
            String filePath = "src/main/resources/data/locations.json";
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
           // System.out.println(jsonArray);
            List<Edge> edgeList = new ArrayList<Edge>();

            jsonArray.forEach(item -> {
                JSONObject obj = (JSONObject) item;
                String origin = obj.get("origin").toString();
                String target = obj.get("target").toString();

                int originIndex = ((Number) map.get(origin)).intValue();
                int targetIndex = ((Number) map.get(target)).intValue();
                double distance = ((Number) obj.get("distance")).doubleValue();

                Edge edge = new Edge(originIndex, targetIndex, (int) distance);
                //System.out.println("New Edge Created from " + origin + " to " + target);
                edgeList.add(edge);
            });

          Graph graph = new Graph(edgeList);
          //System.out.println(graph.getClass()); // NOTE: do what you want with the Graph



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
