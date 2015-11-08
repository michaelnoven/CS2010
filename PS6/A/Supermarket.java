// Copy paste this Java Template and save it as "Supermarket.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0146876M
// write your name here: Michael Noven
// write list of collaborators here: Ian Leow
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class Supermarket {
  private int N; // number of items in the supermarket. V = N+1 
  private int K; // the number of items that Steven has to buy
  private int[] shoppingList; // indices of items that Steven has to buy
  private int[][] T; // the complete weighted graph that measures the direct walking time to go from one point to another point in seconds
  private int[][] memo;

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  // --------------------------------------------
  public Supermarket() {
    // Write necessary code during construction
    //
    // write your answer here


  }

  private void debugTable(){

    for(int i = 0 ; i < N; i++)
      for(int j = 0; j < N; j++)
        System.out.println("T[" + i + "][" + j + "] = " + T[i][j]);

  
  }

  private int DP_TSP(int u, int m){

    // Base case 1, visted everything
    if(m == (1 << N+1) - 1){ // if m is all 1 in binary 
      return T[u][0];
    }

    if(memo[u][m] != -1){
      return memo[u][m];
    }

    memo[u][m] = Integer.MAX_VALUE;

    for(int v = 0; v <= N; v++){
      if(((1 << v) & m) == 0){ 
        memo[u][m] = Math.min(memo[u][m], T[u][v] + DP_TSP(v, (1 << v) | m));
      }
    }

    return memo[u][m];

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
    memo = new int[N+1][1 << N+1];

    for(int i = 0; i <= N; i++){
      Arrays.fill(memo[i], -1);
    }

    return DP_TSP(0,1);
    //debugTable();
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