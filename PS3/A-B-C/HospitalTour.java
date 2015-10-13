// Copy paste this Java Template and save it as "HospitalTour.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0146876M
// write your name here: Michael Noven
// write list of collaborators here:
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)



class Graph{

  public HashMap<Integer, List<Integer>> adjList;

  public Graph(){

    adjList = new HashMap<Integer, List<Integer>>();

  }

  public void addEntry(int v, List<Integer> vertices){

    adjList.put(v, vertices);

  }

  /* Only for debug purposes */
  public void print(){

    for(int i = 0; i < adjList.size(); i++){

      for(int j = 0; j < adjList.get(i).size(); j++){

        System.out.print(" " + adjList.get(i).get(j));

      }

      System.out.println();


    }

  }

}

class HospitalTour {

  public static final int MAX_RATING_SCORE = 100001;
  private int V; // number of vertices in the graph
  private int[] RatingScore; // the weight of each vertex
  private Graph graph;
  private int countVertices;

  public HospitalTour() {

    graph = new Graph();
    V = 0;
    countVertices = 0;

  }

  void performDFS(int i, int[] visited, int[] blockedVertices){

    visited[i] = 1;
   // System.out.println("at vertex " + i + "with weight : " + RatingScore[i]);

    List<Integer> neighbours =  graph.adjList.get(i);

    for(int j = 0; j < neighbours.size(); j++){

      if(visited[neighbours.get(j)] != 1 && blockedVertices[neighbours.get(j)] != 1 ){
        countVertices++;
        performDFS(neighbours.get(j), visited, blockedVertices);
      }

    }

  }

  /*void removeVertex(int index){

    // Loop through all vertices
    for(int j = 0; j < graph.adjList.size(); j++){

      List<Integer> neighbours = graph.adjList.get(j);

      for(int k = 0; k < neighbours.size(); k++){

        if(neighbours.get(k) == index)
          neighbours.remove(k);

      }

    }

    // Remove vertex from our hashmap in O(1)
    graph.adjList.remove(index);
    //graph.print();

  }

  void insertVertex(int index, List<Integer> neighbours){

    graph.addEntry(index, neighbours);

  }
*/

  int Query() {

    // empty graph
    if(V <= 1)
      return -1;

    int[] visited = new int[V];
    int[] blockedVertices = new int[V];
    int minRatingScore = MAX_RATING_SCORE;

    for(int i = 0; i < graph.adjList.size(); i++){ // or V

      countVertices = 0;

      blockedVertices[i] = 1;

      if(i == 0)
        performDFS(1, visited, blockedVertices);
      else
        performDFS(0, visited, blockedVertices);

      if(countVertices < V - 2 ){
        //System.out.println("Important room " + i + "count is " + countVertices);
        if(RatingScore[i] < minRatingScore)
          minRatingScore = RatingScore[i];
      }

      visited = new int[V];
      blockedVertices[i] = 0;

    }

    graph.adjList.clear();

    return minRatingScore < MAX_RATING_SCORE ? minRatingScore : -1;
    
  }

  // You can add extra function if needed
  // --------------------------------------------



  // --------------------------------------------

  void run() throws Exception {
    // for this PS3, you can alter this method as you see fit

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int TC = Integer.parseInt(br.readLine()); // there will be several test cases
    while (TC-- > 0) {

      br.readLine(); // ignore dummy blank line
      V = Integer.parseInt(br.readLine());

      StringTokenizer st = new StringTokenizer(br.readLine());
      // read rating scores, A (index 0), B (index 1), C (index 2), ..., until the V-th index
      RatingScore = new int[V];

      for (int i = 0; i < V; i++)
        RatingScore[i] = Integer.parseInt(st.nextToken());

      // clear the graph and read in a new graph as Adjacency Matrix
      //AdjMatrix = new int[V][V];
      for (int i = 0; i < V; i++) {

        st = new StringTokenizer(br.readLine());
        int k = Integer.parseInt(st.nextToken());

        int nodeId = i;
        List<Integer> nodeList = new ArrayList<Integer>();

        // Loop through all numbers in the row
        while (k-- > 0) {

          int j = Integer.parseInt(st.nextToken());
          nodeList.add(j);
          //AdjMatrix[i][j] = 1; // edge weight is always 1 (the weight is on vertices now)
        }

        graph.addEntry(nodeId, nodeList);

       /* System.out.println("Inserting node: " + nodeId + " with neghbours...");
        for(int q = 0; q < nodeList.size(); q++){

          System.out.println(nodeList.get(q));

        }*/

      }

      pr.println(Query());
      //System.out.println("--------------------");
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    HospitalTour ps3 = new HospitalTour();
    ps3.run();

  }
}



class IntegerPair implements Comparable<IntegerPair> {

  Integer _first, _second;

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  public int compareTo(IntegerPair o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else
      return this.second() - o.second();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }

}