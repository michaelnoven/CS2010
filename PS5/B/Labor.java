// Copy paste this Java Template and save it as "Labor.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0146876M
// write your name here: Michael Noven
// write list of collaborators here:
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class Node implements Comparable<Node>{

  int index;
  Vector<IntegerPair> neighbours;
  int distToSource; // Need this one to sort the PQ

  public Node(int index, Vector<IntegerPair> neighbours, int distToSource){

    this.index = index;
    this.neighbours = neighbours;
    this.distToSource = distToSource;

  }

  public int compareTo(Node n){

    if(n.distToSource > distToSource)
      return -1;
    else if(n.distToSource < distToSource)
      return 1;

    else 
      return 0;

  }

  // Debuggning
  public String toString(){

    return "NODE : " + index + " with weight: " + distToSource;

  }

}

class Labor {
  private int V; // number of vertices in the graph or number of junctions in Singapore map
  private int Q; // number of queries
  private Vector < Vector < IntegerPair > > adjList;  // the weighted graph the Singapore map, the length of each edge road is stored here too, as the weight of edge
  private PriorityQueue<Node> pq;
  private int[][] resArr;
  private int[] dist;
  private boolean[] done;
  private static final int INFINITY = 1000000000;
  private static final int NUM_SOURCES = 10;

  public Labor() {
    // Write necessary code during construction
    //
    // write your answer here
    V = Q = 0;
    adjList = new Vector<Vector<IntegerPair>>();
    pq = new PriorityQueue<Node>();

  }

void calcDjikstras(){ 

  // Calculate shortest path for ALL vertices with djikstras
  for(int s = 0; s < NUM_SOURCES && s < V; s++){

    dist = new int[V];
    done = new boolean[V];
    // Set the distance to all vertices (from source) to inf
    for(int i = 0; i < V; i++){
      dist[i] = i != s ? INFINITY : 0;
    }

    // add first element to queue with 0 distance
    Node node = new Node(s, adjList.get(s), 0);
    pq.add(node);
  
    while(!pq.isEmpty()){

      Node currentNode = pq.poll();
    
      // Speed up things a little
      if(done[currentNode.index])
        continue;

      done[currentNode.index] = true;

      // For all neighbours
      for(int i = 0; i < currentNode.neighbours.size(); i++){

        IntegerPair neighbour = currentNode.neighbours.get(i);

        if(dist[neighbour._first] > dist[currentNode.index] + neighbour._second){

          dist[neighbour._first] = dist[currentNode.index] + neighbour._second;
          resArr[s][neighbour._first] = dist[currentNode.index] + neighbour._second;
          
          pq.add(new Node(neighbour._first, adjList.get(neighbour._first), dist[currentNode.index] + neighbour._second));
        
        }
      
      }

    }

  }
  
}


void debugOutput(){

  for(int i = 0; i < V; i++){
    for(int j = 0; j < V; j++){
      System.out.println(i + " -> " + j + " = " + resArr[i][j]);
    }
  }

}

void PreProcess() {

  // Declare result array
  resArr = new int[NUM_SOURCES][V];

  // Init array with -1 except for same node
  for(int i = 0; i < NUM_SOURCES && i < V; i++)
    for(int j = 0; j < V; j++)
      resArr[i][j] = i != j ? -1 : 0;

  calcDjikstras();
  //debugOutput();

}

  void debugList(){

    for(int i = 0; i < adjList.size(); i++){
      System.out.println("Vertex " + i + " : ");
      Vector<IntegerPair> neighbours = adjList.get(i);

      for(int j = 0; j < neighbours.size(); j++){
        System.out.print(" ( " + neighbours.get(j)._first + " , " + neighbours.get(j)._second + " )");
      }

      System.out.println();
    }
    System.out.println("----------------------");
  }

  int Query(int s, int t, int k) {

    // You have to report the shortest path from Steven and Grace's current position s
    // to reach their chosen hospital t, output -1 if t is not reachable from s
    // with one catch: this path cannot use more than k vertices

    //debugList();
    return resArr[s][t];

    //------------------------------------------------------------------------- 

  }

  void run() throws Exception {
    // you can alter this method if you need to do so
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt();

      // clear the graph and read in a new graph as Adjacency List
      adjList = new Vector < Vector < IntegerPair > >();
      for (int i = 0; i < V; i++) {
        adjList.add(new Vector < IntegerPair >());

        int k = sc.nextInt();
        while (k-- > 0) {
          int j = sc.nextInt(), w = sc.nextInt();
          adjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (in minutes) is stored here
        }
      }

      PreProcess(); // optional

      Q = sc.nextInt();
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt(), sc.nextInt()));

      if (TC > 0)
        pr.println();
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    Labor ps5 = new Labor();
    ps5.run();
  }
}



class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
  BufferedInputStream bis;
  IntegerScanner(InputStream is) {
    bis = new BufferedInputStream(is, 1000000);
  }
  
  public int nextInt() {
    int result = 0;
    try {
      int cur = bis.read();
      if (cur == -1)
        return -1;

      while ((cur < 48 || cur > 57) && cur != 45) {
        cur = bis.read();
      }

      boolean negate = false;
      if (cur == 45) {
        negate = true;
        cur = bis.read();
      }

      while (cur >= 48 && cur <= 57) {
        result = result * 10 + (cur - 48);
        cur = bis.read();
      }

      if (negate) {
        return -result;
      }
      return result;
    }
    catch (IOException ioe) {
      return -1;
    }
  }
}



class IntegerPair implements Comparable < IntegerPair > {
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