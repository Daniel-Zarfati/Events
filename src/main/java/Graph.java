import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {

    private Edge[] edges;

    public Graph(Edge[] edges) {
        this.edges = edges;
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

    public static void main(String[] arg) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject map = LoadMap();
            String filePath = "src/main/resources/data/locations.json";
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));
            System.out.println(jsonArray);
            List<Edge> edgeList = new ArrayList<Edge>();

            jsonArray.forEach(item -> {
                JSONObject obj = (JSONObject) item;
                String origin = obj.get("origin").toString();
                String target = obj.get("target").toString();

                int originIndex = ((Number) map.get(origin)).intValue();
                int targetIndex = ((Number) map.get(target)).intValue();
                double distance = ((Number) obj.get("distance")).doubleValue();

                Edge edge = new Edge(originIndex, targetIndex, (int) distance);
                System.out.println("New Edge Created from " + origin + " to " + target);
                edgeList.add(edge);
            });

            Graph graph = new Graph(edgeList.toArray()); FIX: this.

            System.out.println(graph.getClass()); // NOTE: do what you want with the Graph

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
