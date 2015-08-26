// Copy paste this Java Verifier and save it as "SchedulingDeliveriesVerifier.java"
// Usage: "java SchedulingDeliveriesVerifier < yourproposedtestcasefilename" and see if this verifier reports anything
import java.util.*;

class SchedulingDeliveriesVerifier {
  private static void checkName(String name) {
    if (name.length() > 15)
      System.out.println("ERROR, name " + name + " is longer than 15 characters");
    for (int i = 0; i < name.length(); i++)
      if (!Character.isUpperCase(name.charAt(i))) {
        System.out.println("ERROR, name " + name + " contains non-uppercase character");
        break;
      }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    TreeMap < String, Integer > women = new TreeMap < String, Integer >();
    String name;

    int numCMD = sc.nextInt();
    if (numCMD > 1000000) {
      System.out.println("ERROR, too many commands...");
      return;
    }

    while (numCMD-- > 0) {
      int cmd = sc.nextInt();
      switch (cmd) {
        case 0: // ArriveAtHospital
          name = sc.next();
          checkName(name);
          if (women.containsKey(name)) {
            System.out.println("ERROR, duplicate woman name found..." + name);
            return;
          }
          int dilation = sc.nextInt();
          if (dilation < 30 || dilation > 100) {
            System.out.println("ERROR, dilation is not in [30..100]..., it is = " + dilation);
            return;
          }
          women.put(name, dilation);
          if (women.size() > 200000) {
            System.out.println("ERROR, this test case contains too many women..., it is = " + women.size());
            return;
          }
          break;
        case 1: // UpdateDilation
          name = sc.next();
          checkName(name);
          if (!women.containsKey(name)) {
            System.out.println("ERROR, this woman " + name + " has not arrived in hospital yet...");
            return;
          }
          int increaseDilation = sc.nextInt();
          if (increaseDilation < 0 || increaseDilation > 70) {
            System.out.println("ERROR, increaseDilation is not in [0..70]..., it is = " + increaseDilation);
            return;
          }
          if (women.get(name)+increaseDilation > 100) {
            System.out.println("ERROR, add increaseDilation = " + increaseDilation + " will cause " + name + " to have dilation status greater than 100, that is medically impossible");
            return;
          }
          women.put(name, women.get(name)+increaseDilation);
          break;
        case 2: // GiveBirth
          name = sc.next();
          checkName(name);
          if (!women.containsKey(name)) {
            System.out.println("ERROR, this woman " + name + " has not arrived in hospital yet...");
            return;
          }
          break;
        case 3: // Query
          break;
      }
    }

    System.out.println("Test data is valid :)");
  }
}