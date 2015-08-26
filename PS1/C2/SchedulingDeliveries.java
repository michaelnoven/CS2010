// Copy paste this Java Template and save it as "SchedulingDeliveries.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0146876M
// write your name here: Michael Noven
// write list of collaborators here:
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class SchedulingDeliveries {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  //PriorityQueue<Woman> pq;
  final int NUM_WOMAN = 200000;
  int queueNumberGlobal = 1;
  TreeSet<Woman> ts;
  HashMap<String, Woman> hm;

  class Woman{

    public String name;
    public int dilation;
    public int queueNumber;
  
    public Woman(String name, int dilation){

      this.name = name;
      this.dilation = dilation;
      this.queueNumber = queueNumberGlobal++;

    }

    // For debug purposes
    public String toString(){

      return name + " : " + dilation + " : " + queueNumber;

    }

  }
  public SchedulingDeliveries() {
    // Write necessary code during construction
    //
    // write your answer here
    ts = new TreeSet<Woman>(new Comparator<Woman>(){

      public int compare(Woman woman1, Woman woman2){

            if(woman1.dilation < woman2.dilation)
                return 1;

            else if (woman1.dilation > woman2.dilation)
                return -1;

            else{ // equals

              if(woman1.queueNumber > woman2.queueNumber)
                return 1;

              else if(woman1.queueNumber < woman2.queueNumber)
                return -1;

              else // totally equal, not gonna happen
                return 0;

            }

        }

    });

    hm = new HashMap<String, Woman>();
     
  }

  void ArriveAtHospital(String womanName, int dilation) {
    // You have to insert the information (womanName, dilation)
    // into your chosen data structure
    //
    // write your answer here

    Woman newWoman = new Woman(womanName, dilation);
    hm.put(womanName, newWoman);
    ts.add(newWoman);
   // System.out.println(ts);

  }

  void UpdateDilation(String womanName, int increaseDilation) {

    Woman womanToUpdate = hm.get(womanName);
    ts.remove(womanToUpdate);

    womanToUpdate.dilation += increaseDilation;
    ts.add(womanToUpdate);

    hm.put(womanName, womanToUpdate);

    //System.out.println("Hashmap: " + hm);

  }

  void GiveBirth(String womanName) {

    Woman womanToRemove = hm.remove(womanName);
    ts.remove(womanToRemove);

  }

  String Query() {

    // You have to report the name of the woman that the doctor
    // has to give the most attention to. If there is no more woman to
    // be taken care of, return a String "The delivery suite is empty"
    //
    // write your answer here

    if(ts.size() == 0){
      return "The delivery suite is empty";
    } else{
      return ts.first().name;
    }

  }

  void run() throws Exception {
    // do not alter this method

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    int numCMD = Integer.parseInt(br.readLine()); // note that numCMD is >= N
    while (numCMD-- > 0) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      switch (command) {
        case 0: ArriveAtHospital(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 1: UpdateDilation(st.nextToken(), Integer.parseInt(st.nextToken())); break;
        case 2: GiveBirth(st.nextToken()); break;
        case 3: pr.println(Query()); break;
      }
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    SchedulingDeliveries ps1 = new SchedulingDeliveries();
    ps1.run();
  }
}