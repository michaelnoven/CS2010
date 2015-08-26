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
  PriorityQueue<Woman> pq;
  int queueNumber = 1;

  class Woman{

    public String name;
    public int dilation;
    public int queueNumber;

    public Woman(String name, int dilation, int queueNumber){

      this.name = name;
      this.dilation = dilation;
      this.queueNumber = queueNumber;

    }

    public String getName(){ return name; }

    // For debug
    public String toString(){

      return "Name woman: " + name + " Dilation Woman: " + dilation;

    }

  };


  public SchedulingDeliveries() {
    // Write necessary code during construction
    //
    // write your answer here
    pq = new PriorityQueue<Woman>(10, new Comparator<Woman>(){

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

  }

  void ArriveAtHospital(String womanName, int dilation) {
    // You have to insert the information (womanName, dilation)
    // into your chosen data structure
    //
    // write your answer here
    Woman woman = new Woman(womanName, dilation, queueNumber);
    pq.add(woman);
    queueNumber++;

  }

  void UpdateDilation(String womanName, int increaseDilation) {
    // You have to update the dilation of womanName to
    // dilation += increaseDilation
    // and modify your chosen data structure (if needed)
    //
    // write your answer here

    Woman[] allWoman = pq.toArray(new Woman[10]);
    int oldDilation, oldQueueNumber;

    for(int i = 0; i < pq.size(); i++){

      if(allWoman[i].name.equals(womanName)){

        oldDilation = allWoman[i].dilation;
        oldQueueNumber = allWoman[i].queueNumber;
        pq.remove(allWoman[i]);
        pq.add(new Woman(womanName, oldDilation + increaseDilation, oldQueueNumber));
        return;

      }

    }

  }

  void GiveBirth(String womanName) {
    // This womanName gives birth 'instantly'
    // remove her from your chosen data structure
    //
    // write your answer here
   Woman[] allWoman = pq.toArray(new Woman[10]);

    for(int i = 0; i < pq.size(); i++){

      if(allWoman[i].name.equals(womanName)){

        pq.remove(allWoman[i]);
        return;

      }

    }


  }

  String Query() {

    if(pq.size() == 0)
      return "The delivery suite is empty";

    else
      return pq.peek().name;

    // You have to report the name of the woman that the doctor
    // has to give the most attention to. If there is no more woman to
    // be taken care of, return a String "The delivery suite is empty"
    //
    // write your answer here

  }

 /* void dump(){

    while(true){

      Woman currentWoman = pq.poll();
      if(currentWoman == null)
        break;

      System.out.println(currentWoman);

    }

  }*/

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