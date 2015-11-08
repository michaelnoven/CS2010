// Copy paste this Java Template and save it as "OutForAWalk.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0146876M
// write your name here: Michael Noven
// write list of collaborators here: Jonathan Irvin G, Ian Leow
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class OutForAWalk {
  
  private int V; // number of vertices in the \graph (number of rooms in the building)
  private Vector < Vector < IntegerPair > > AdjList; // the weighted \graph (the building), effort rating of each corridor is stored here too
  private static PriorityQueue < IntegerTriple > pq;
  private static Vector < Boolean > taken;
  private Vector < Vector < IntegerPair > > MSTAdjList;
  private int[] maxEfforts;
  private int maxEffort;
  private int[][] preProcessArr;

  private static int NUM_SOURCE_NODES = 10;

  public OutForAWalk() {
    V = 0;
    AdjList = new Vector < Vector < IntegerPair > >();
    maxEffort = 0;
  }


  void sortMSTByWeight(){

    for(int k = 0; k < V; k++){

      Vector<IntegerPair> neighbours = MSTAdjList.get(k);

      boolean swapped = true;
      int j = 0;
      IntegerPair tmp;
      while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < neighbours.size() - j; i++) {                                       
                  if (neighbours.get(i)._second > neighbours.get(i + 1)._second) {  

                      tmp = new IntegerPair(neighbours.get(i));

                      neighbours.get(i)._first = neighbours.get(i + 1)._first; 
                      neighbours.get(i)._second = neighbours.get(i + 1)._second;  

                      neighbours.get(i + 1)._first = tmp._first;
                      neighbours.get(i + 1)._second = tmp._second;

                      swapped = true;
                  }
            }                
      }

    }

  }

  void PreProcess() {

    buildMST();
    sortMSTByWeight(); // Need to sort all the edges by weight for proper DFS

    // Run dfs from 0..9 (all source nodes) to every other node store it in an array[10][V]
    // Get answer in O(1) time. 

    preProcessArr = new int[NUM_SOURCE_NODES][V];

    for(int i = 0; i < NUM_SOURCE_NODES && i < V; i++){

      int[] visited = new int[V];
      performDFS(i, visited, preProcessArr, i, 0);

    }

  }

  private void process(int vtx) {

    //System.out.println(">> At vertex " + vtx);
    taken.set(vtx, true);
    for (int j = 0; j < AdjList.get(vtx).size(); j++) {
      IntegerPair v = AdjList.get(vtx).get(j);
      if (!taken.get(v.first())) {
        //pq.offer(new IntegerPair(v.second(), v.first()));  // we sort by weight then by adjacent vertex
        pq.offer(new IntegerTriple(v.second(), v.first(), vtx)); // Changed to integer tripple to include source vertex aswell
       // System.out.println(">> Inserting (" + v.second() + ", " + v.first() + ") to priority queue");
      }
     // else
        //System.out.println(">> Ignoring (" + v.second() + ", " + v.first() + ")");
    }

  }
  
  private void buildMST(){

    taken = new Vector < Boolean >(); taken.addAll(Collections.nCopies(V, false));
    pq = new PriorityQueue < IntegerTriple > ();
    // take any vertex of the graph, for simplicity, vertex 0, to be included in the MST
    process(0);
    int mst_cost = 0;
    
    while (!pq.isEmpty()) { // we will do this until all V vertices are taken (or E = V-1 edges are taken)
      IntegerTriple front = pq.poll();

      if (!taken.get(front.second())) { // we have not connected this vertex yet
        mst_cost += front.first(); // add the weight of this edge

        /* Simply MST vertices to new adj list */
        MSTAdjList.get(front.third()).add(new IntegerPair(front.second(), front.first()));
        MSTAdjList.get(front.second()).add(new IntegerPair(front.third(), front.first()));

        //System.out.println("Adding edge: (" + front.first() + ", " + front.second() + "), MST cost now = " + mst_cost);
        process(front.second());

      }

    }

  }

  void debugPreProcessArr(){

    for(int i = 0; i < NUM_SOURCE_NODES && i < V; i++){

      System.out.println(" source " + i);
      System.out.println(" neighbour");

      for(int j = 0; j < V; j++){

        System.out.print(" " + preProcessArr[i][j]);

      }

      System.out.println();

    }

  }

  int Query(int source, int destination) {

    //debugPreProcessArr();
    return preProcessArr[source][destination];

  } 

  void performDFS(int i, int[] visited, int[][] preProcessArr, int source, int maxEffort){

    visited[i] = 1;

    Vector<IntegerPair> neighbours = MSTAdjList.get(i);

    for(int j = 0; j < neighbours.size(); j++){

      if(visited[neighbours.get(j)._first] != 1){

        int currMaxEffort = neighbours.get(j)._second;

        if(currMaxEffort > maxEffort)
          maxEffort = currMaxEffort;

        preProcessArr[source][neighbours.get(j)._first] = maxEffort;
        performDFS(neighbours.get(j)._first, visited, preProcessArr, source, maxEffort);

      }
    }
  }

  void debugMSTAdjList(){

    for(int i = 0; i < V; i++){

      System.out.print("Vertex " + i + " ->  ");

      Vector<IntegerPair> neighbours = MSTAdjList.get(i);

      for(int j = 0; j < neighbours.size(); j++){
        System.out.print(neighbours.get(j)._first + " | " + neighbours.get(j)._second + ", ");
      }

      System.out.println();

    }

  }

  // --------------------------------------------

  void run() throws Exception {
    // do not alter this method
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    final long startTime = System.currentTimeMillis();

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt();

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new Vector < Vector < IntegerPair > >();
      MSTAdjList = new Vector < Vector < IntegerPair > >();
      for (int i = 0; i < V; i++) {
        AdjList.add(new Vector < IntegerPair >());
        MSTAdjList.add(new Vector < IntegerPair >());

        int k = sc.nextInt();
        while (k-- > 0) {
          int j = sc.nextInt(), w = sc.nextInt();
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (corridor) weight (effort rating) is stored here
        }
      }

      PreProcess(); // you may want to use this function or leave it empty if you do not need it

      int Q = sc.nextInt();
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt()));
      pr.println(); // separate the answer between two different graphs
    }

    final long endTime = System.currentTimeMillis();
    //pr.println("Total execution time: " + (endTime - startTime));
  
    pr.close();

  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    
    OutForAWalk ps4 = new OutForAWalk();
    ps4.run();
  
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

  public IntegerPair(IntegerPair another){

    this._first = another._first;
    this._second = another._second;

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



class IntegerTriple implements Comparable < IntegerTriple > {
  Integer _first, _second, _third;

  public IntegerTriple(Integer f, Integer s, Integer t) {
    _first = f;
    _second = s;
    _third = t;
  }

  public int compareTo(IntegerTriple o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else if (!this.second().equals(o.second()))
      return this.second() - o.second();
    else
      return this.third() - o.third();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
  Integer third() { return _third; }
}