// Copy paste this Java Template and save it as "Supermarket.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0146876M
// write your name here: Michael Noven
// write list of collaborators here: Ian Leow
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)



class IntegerPair implements Comparable < IntegerPair > {
  Integer _first, _second;

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

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

class Supermarket {
  private int N; // number of items in the supermarket. V = N+1 
  private int K; // the number of items that Steven has to buy
  private int[] shoppingList; // indices of items that Steven has to buy
  private int[][] T; // the complete weighted graph that measures the direct walking time to go from one point to another point in seconds
  private int[][] memo;
  private PriorityQueue<IntegerPair> pq;

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  // --------------------------------------------
  public Supermarket() {
    // Write necessary code during construction
    //
    // write your answer here


  }

  private void debugTable(){

    for(int i = 0 ; i < N+1; i++)
      for(int j = 0; j < N+1; j++)
        System.out.println("T[" + i + "][" + j + "] = " + T[i][j]);

  }

  private int DP_TSP(int u, int m){

    //System.out.println("lol");
    // Base case 1, visted everything
    if(m == (1 << K) - 1){ // if m is all 1 in binary 
      return T[u][0];
    }

    if(memo[u][m] != -1){
      return memo[u][m];
    }

    memo[u][m] = Integer.MAX_VALUE;

    for(int v = 0; v < K; v++){
      if(((1 << v) & m) == 0){ 
        memo[u][m] = Math.min(memo[u][m], T[u][shoppingList[v]] + DP_TSP(shoppingList[v], (1 << v) | m));
      }
    }

    return memo[u][m];

  }



  private void floydWarshall(){

    for (int k = 0; k < N+1; k++) // remember, k first
      for (int i = 0; i < N+1; i++) // before i
        for (int j = 0; j < N+1; j++) // then j
          T[i][j] = Math.min(T[i][j], T[i][k]+T[k][j]);

  }

int[] calcDijkstra(int s){ 

  pq = new PriorityQueue<IntegerPair>();

  //int numIterations = 0;
  int[] dist = new int[N+1];
  // Set the distance to all vertices (from source) to inf
  for(int i = 0; i < N+1; i++){
      dist[i] = Integer.MAX_VALUE;
  }

  dist[s] = 0;
  pq.add(new IntegerPair(s, 0)); // Init PQ with source, distance 0 and 1 hop

  while(!pq.isEmpty()){

    IntegerPair currentNode = pq.poll();

    // For all neighbours
    for(int i = 0; i < N+1; i++){

      if(dist[i] >  dist[currentNode._first] + T[currentNode._first][i]){

        dist[i] = dist[currentNode._first] + T[currentNode._first][i];    
        pq.add(new IntegerPair(i, dist[i]));
      

      }
    }
  }
  
  // not found
  return dist;
  
}


  private void multiDijkstra(){

    int[] distToAllOtherNodes = new int[N+1];

    for(int i = 0; i < K; i++){
      
      //System.out.println("i " + shoppingList[i]);
      distToAllOtherNodes = calcDijkstra(shoppingList[i]);

      // Update the adjacancy matrix
      for (int j = 0; j < N+1; j++){

        T[shoppingList[i]][j] = distToAllOtherNodes[j];
        T[j][shoppingList[i]] = distToAllOtherNodes[j];

      }
    }
    
  }


  /* IAN PSEUDO TO JAVA */

  // B,C Convert to TSP and then give the answer TSP ++
  // Slower TSP runs in N!. 2^n * N^2 for faster solution.
  // -------------------------------------------------------
  // USE FLOYD WARSHALL . V = 400 , can run floyd warshall.
  // BUT if V = 2000, we get 8 billion.. C definitely will get TLE.
  // In C, K is only 16. is like ps4+5. WE can run djikstras!! :) :) :)

  int Query() {
    // You have to report the quickest shopping time that is measured
    // since Steven enters the supermarket (vertex 0),
    // completes the task of buying K items in that supermarket as ordered by Grace,
    // then, reaches the cashier of the supermarket (back to vertex 0).
    //
    // write your answer here

    //memo = new int[N][(int)Math.pow(N, 2)];
    memo = new int[N+1][1 << K];

    for(int i = 0; i < N+1; i++){
      Arrays.fill(memo[i], -1);
    }

    //floydWarshall();
    //debugTable();
    multiDijkstra();

    return DP_TSP(0,0);


  }
 
  void run() throws Exception {
    // do not alter this method to standardize the I/O speed (this is already very fast)
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      // read the information of the complete graph with N+1 vertices
      N = sc.nextInt(); K = sc.nextInt(); // K is the number of items to be bought

      shoppingList = new int[K];
      for (int i = 0; i < K; i++)
        shoppingList[i] = sc.nextInt();

      T = new int[N+1][N+1];
      for (int i = 0; i <= N; i++)
        for (int j = 0; j <= N; j++)
          T[i][j] = sc.nextInt();

      pw.println(Query());

    }
    
    pw.close();

  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    Supermarket ps6 = new Supermarket();
    ps6.run();
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