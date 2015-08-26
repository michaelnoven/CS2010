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
  TreeMap<Integer, ArrayList<Woman>> tm;
  HashMap<String, Integer> hm;
  //TreeMap<Woman, Woman> tm;
  int queueNumber = 1;
  final int NUM_WOMAN = 200000;

  class Woman{

    public String name;
    public int queueNumber;

    public Woman(String name, int queueNumber){

      this.name = name;
      this.queueNumber = queueNumber;

    }

    public String toString(){

      return "N: " + name + " Q: " + queueNumber;

    }

  
    public String getName(){

        return name;
    }

  };


  // class WomanComparator implements Comparator<Woman>{
 
  //   public int compare(Woman woman1, Woman woman2){

  //       if(woman1.dilation < woman2.dilation)
  //           return 1;

  //       else if (woman1.dilation > woman2.dilation)
  //           return -1;

  //       else{ // equals

  //         if(woman1.queueNumber > woman2.queueNumber)
  //           return 1;

  //         else if(woman1.queueNumber < woman2.queueNumber)
  //           return -1;

  //         else // totally equal, not gonna happen
  //           return 0;

  //       }

  //   }
     
  // }

  public SchedulingDeliveries() {
    // Write necessary code during construction
    //
    // write your answer here

    tm = new TreeMap<Integer, ArrayList<Woman>>();
    hm = new HashMap<String, Integer>();
   /* tm = new TreeMap<WomanKey, Woman>(new Comparator<WomanKey>(){

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

    });*/

    //hm = new HashMap<String, Woman>();
    /*pq = new PriorityQueue<Woman>(NUM_WOMAN, new Comparator<Woman>(){

        /*public int compare(Woman woman1, Woman woman2){

            if(hm.get(woman1.name) == null)
              return 0;
            else if(hm.get(woman2.name) == null)
              return 0;

            if(hm.get(woman1.name).dilation < hm.get(woman2.name).dilation)
                return 1;

            else if (hm.get(woman1.name).dilation > hm.get(woman2.name).dilation)
                return -1;

            else{ // equals

              if(hm.get(woman1.name).queueNumber > hm.get(woman2.name).queueNumber)
                return 1;

              else if(hm.get(woman1.name).queueNumber < hm.get(woman2.name).queueNumber)
                return -1;

              else{ // totally equal, not gonna happen
                return 0;
              }

            }

        }

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

    });*/


  }

  void ArriveAtHospital(String womanName, int dilation) {
    // You have to insert the information (womanName, dilation)
    // into your chosen data structure
    //
    // write your answer here
    //Woman woman = new Woman(womanName, dilation, queueNumber);
   // hm.put(womanName, woman);

    // We know that this woman is unique..

    hm.put(womanName, dilation);

    if(!tm.containsKey(dilation)){
      ArrayList<Woman> list = new ArrayList<Woman>();
      list.add(new Woman(womanName, queueNumber));
      tm.put(hm.get(womanName), list);
    } else {
      tm.get(dilation).add(new Woman(womanName, queueNumber));
    }

    System.out.println(tm);
    queueNumber++;
    //pq.add(woman);
    //queueNumber++;
    //tm.put(woman, woman); // Does not matter what type of key in this case since we defined a comparator in constructor

  }

  void UpdateDilation(String womanName, int increaseDilation) {

    
    // Get the womans corresponding dilation
    int dilation = hm.get(womanName);
    int oldQueueNumber = 0;

    // Many women can correspond to the same dilation, remove woman from the list
    ArrayList<Woman> list = tm.get(dilation);

    for(int i = 0; i < list.size(); i++){

      if(list.get(i).name.equals(womanName)){
        oldQueueNumber = list.get(i).queueNumber;
        list.remove(i);
        if(list.size() == 0){
          tm.remove(dilation);
        }
      }

    }
// lol
    System.out.println("removed" + tm);

    // There is a list
    if(tm.containsKey(dilation+increaseDilation)){

      ArrayList<Woman> womanList = tm.get(dilation+increaseDilation);

      if(womanList.get(0).queueNumber < oldQueueNumber){
        womanList.add(new Woman(womanName, oldQueueNumber));
        System.out.println("here");

      } else{
        womanList.add(0, new Woman(womanName, oldQueueNumber));
        System.out.println("or here");
      }

    } else{

      ArrayList<Woman> newList = new ArrayList<Woman>();
      newList.add(new Woman(womanName, oldQueueNumber));

      tm.put(dilation+increaseDilation, list);

    }

    // Update in hashmap
    hm.remove(womanName);
    hm.put(womanName, dilation+increaseDilation);

    System.out.println("added again" + tm);

    // You have to update the dilation of womanName to
    // dilation += increaseDilation
    // and modify your chosen data structure (if needed)
    //
    // write your answer here

   // Woman womanToModify = hm.get(womanName);
    //womanToModify.dilation += increaseDilation;

    //Object currentWoman = tm.get(womanName);
    //System.out.println(currentWoman);
   // currentWoman.dilation += increaseDilation;


   /* Woman[] allWoman = pq.toArray(new Woman[10]);
    int oldDilation, oldQueueNumber;

    for(int i = 0; i < pq.size(); i++){

      if(allWoman[i].name.equals(womanName)){

        oldDilation = allWoman[i].dilation;
        oldQueueNumber = allWoman[i].queueNumber;
        pq.remove(allWoman[i]);
        pq.add(new Woman(womanName, oldDilation + increaseDilation, oldQueueNumber));
        return;

      }

    }*/

  }

  void GiveBirth(String womanName) {

    System.out.println("TM IS " + tm);

    ArrayList<Woman> womanList = tm.get(hm.get(womanName));

    for(int i = 0; i < womanList.size(); i++){

      if(womanList.get(i).name.equals(womanName)){
        womanList.remove(i);
        if(womanList.size() == 0)
          tm.remove(hm.get(womanName));
      }

    }

    System.out.println("TM IS AFTER" + tm);

    hm.remove(womanName);
    
    //hm.remove(womanName);
    // This womanName gives birth 'instantly'
    // remove her from your chosen data structure
    //
    // write your answer here


   /*Woman[] allWoman = pq.toArray(new Woman[10]);

    for(int i = 0; i < pq.size(); i++){

      if(allWoman[i].name.equals(womanName)){

        pq.remove(allWoman[i]);
        return;

      }

    }*/



  }

  String Query() {


    /*if(pq.size() == 0)
      return "The delivery suite is empty";

    else{

      return pq.peek().name;
      
    }*/
    if(tm.size() == 0){
      return "The delivery suite is empty";
    } else{
      return tm.get(tm.lastKey()).get(0).name;
    }




    // You have to report the name of the woman that the doctor
    // has to give the most attention to. If there is no more woman to
    // be taken care of, return a String "The delivery suite is empty"
    //
    // write your answer here

      

   // Woman thewoman = tm.firstEntry().getValue();

    //return thewoman.name;


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