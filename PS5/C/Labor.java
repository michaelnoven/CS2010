// Copy paste this Java Template and save it as "Labor.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0146876M
// write your name here: Michael Noven
// write list of collaborators here: Ian Leow, Jonathan Irwin, Khanh Truong
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class Labor {
  private int V; // number of vertices in the graph or number of junctions in Singapore map
  private int Q; // number of queries
  private Vector < Vector < IntegerPair > > adjList;  // the weighted graph the Singapore map, the length of each edge road is stored here too, as the weight of edge
  private PriorityQueue<IntegerTriple> pq;
  private int[][] dist;
  private boolean[] done;
  private static final int INFINITY = 1000000000;
  private static final int NUM_SOURCES = 10;

  public Labor() {
    // Write necessary code during construction
    //
    // write your answer here
    V = Q = 0;
    adjList = new Vector<Vector<IntegerPair>>();
    pq = new PriorityQueue<IntegerTriple>();

}


void debugDist(int k) {

  for(int i = 0; i < V; i++  ){

    for(int j = 1; j < k + 1 ; j++ ){

      System.out.println("dist[" + i + "][" + j + "] = " + dist[i][j]);

    }

  }

}


int calcDjikstras(int s, int t, int k){ 

  //System.out.println("----------------------");

  if(k == 1)
    return -1;

  //System.out.println("K : " + k);

  pq = new PriorityQueue<IntegerTriple>();

  //int numIterations = 0;
  dist = new int[V][k+1];
  done = new boolean[V];
  // Set the distance to all vertices (from source) to inf
  for(int i = 0; i < V; i++){
    for(int j = 1 ; j < k + 1; j++){

      dist[i][j] = INFINITY;

    }
  }

  dist[s][1] = 0;
  pq.add(new IntegerTriple(s, 0, 1)); // Init PQ with source, distance 0 and 1 hop

  while(!pq.isEmpty()){

    IntegerTriple currentNode = pq.poll();

    if(currentNode._first == t){
      return dist[currentNode._first][currentNode._third];
    }

    else if(currentNode._third == k )
      continue;

    //done[currentNode._first] = true;

    Vector<IntegerPair> neighbours = adjList.get(currentNode._first);

    // For all neighbours
    for(int i = 0; i < neighbours.size(); i++){

      IntegerPair neighbour = neighbours.get(i);

      if(dist[neighbour._first][currentNode._third + 1] > dist[currentNode._first][currentNode._third] + neighbour._second){

        dist[neighbour._first][currentNode._third + 1] = dist[currentNode._first][currentNode._third] + neighbour._second;        
        pq.add(new IntegerTriple(neighbour._first, dist[neighbour._first][currentNode._third + 1], currentNode._third + 1));
        
        //System.out.println("ADDED " + neighbour._first + " to queue ");

      }
    
    }

  }
  
  // not found
  return -1;
  
}

/*
void debugOutput(){

  for(int i = 0; i < V; i++){
    for(int j = 0; j < V; j++){
      System.out.println(i + " -> " + j + " = " + resArr[i][j]);
    }
  }

}
*/
/*
void PreProcess() {

  // Declare result array
  resArr = new int[NUM_SOURCES][V];

  // Init array with -1 except for same node
  for(int i = 0; i < NUM_SOURCES && i < V; i++)
    for(int j = 0; j < V; j++)
      resArr[i][j] = i != j ? -1 : 0;

//  calcDjikstras();
  //debugOutput();

}*/

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

    if(s == t)
      return 0;
    // You have to report the shortest path from Steven and Grace's current position s
    // to reach their chosen hospital t, output -1 if t is not reachable from s
    // with one catch: this path cannot use more than k vertices

    //debugList();
    //return resArr[s][t];
    return calcDjikstras(s,t,k);
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

      //PreProcess(); // optional

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

class IntegerTriple implements Comparable < IntegerTriple > {
  Integer _first, _second, _third;

  public IntegerTriple(Integer f, Integer s, Integer t) {
    _first = f;
    _second = s;
    _third = t;
  }

  public int compareTo(IntegerTriple o) {
    
   // System.out.println("comparing " + this._second + " with " + o._second);

    if(o._second > _second)
      return -1;
    else if(o._second < _second)
      return 1;

    else 
      return 0;
  }

  public String toString(){

    if(_second == 1000000000)
      return "VERTEX : " + _first + " ,DISTANCE : INF, HOPS :" + _third ;

    else
      return "VERTEX : " + _first + ", DISTANCE : " + _second + ", HOPS :" + _third ;

  }

  Integer third() { return _first; } 
  Integer first() { return _first; }
  Integer second() { return _second; }
}


class IntegerPair implements Comparable < IntegerPair > {
  Integer _first, _second;

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  /*public int compareTo(IntegerPair o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else
      return this.second() - o.second();
  }*/

  public int compareTo(IntegerPair o){

    if(o._second > _second)
      return -1;
    else if(o._second < _second)
      return 1;

    else 
      return 0;

  }

  Integer first() { return _first; }
  Integer second() { return _second; }
}