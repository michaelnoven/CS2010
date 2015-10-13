// Copy paste this Java Template and save it as "BabyNames.java"
import java.util.*;
import java.io.*;

// write your matric number here: A0146876M
// write your name here: Michael Noven
// write list of collaborators here:
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class Node {

  public Baby value;
  public Node parent, left, right;
  public int height, rank;

  public Node(Baby value){

    this.value = value;
    parent = left = right = null;
    height = rank = 0;

  }

}

class Baby {

  public static final int MALE = 1;
  public static final int FEMALE = 2;

  public String babyName;
  public int babyGender;

  public Baby(String babyName, int babyGender){

    this.babyName = babyName;
    this.babyGender = babyGender;

  }

  // For debugging
  public String toString(){

    return babyName + " : " + babyGender;

  }

}

class BabyTree{

  // instance variables
  protected Node root;

  // constructor
  public BabyTree() { root = null; }

  // protected class methods 
  protected Node find(Node node, String key) {

    if (node == null) return node;                               // not found
    else if (node.value.babyName.equals(key)) return node;                        // found
    else if (node.value.babyName.compareTo(key) > 0)  return find(node.right, key);       // search to the right
    else return find(node.left, key);  // search to the left

  }

  protected Node insert(Node node, Baby babyToInsert) {

    if (node == null) return new Node(babyToInsert);          // insertion point is found

    if (node.value.babyName.compareTo(babyToInsert.babyName) > 0){                  // search to the right
      node.right = insert(node.right, babyToInsert);
      node.right.parent = node;
    }
    else {                                          // search to the left
      node.left = insert(node.left, babyToInsert);
      node.left.parent = node;
    }

    return node;                                       // return the updated BST
  }

  // public methods to user 
  public Baby find(String key) {

    Node res = find(root, key);
    return res.value;

  }

  protected Baby findMin(Node node) {
    if (node == null) throw new NoSuchElementException("BST is empty, no minimum");
    else if (node.left == null) return node.value;                    // this is the min
    else return findMin(node.left);           // go to the left
  }

  protected Baby findMax(Node node) {
    if (node == null) throw new NoSuchElementException("BST is empty, no maximum");
    else if (node.right == null) return node.value;                   // this is the max
    else return findMax(node.right);        // go to the right
  }

  protected void inorder(Node node) {
    if (node == null) return;
    inorder(node.left);                               // recursively go to the left
    //System.out.printf(" %s", node.value);                      // visit this BST node
    System.out.println(node.value);
    inorder(node.right);                             // recursively go to the right
  }

  // will be used in AVL lecture
  protected int getHeight(Node node) {
    if (node == null) return -1;
    else return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
  }

  protected Baby successor(Node node) {
    if (node.right != null)                       // this subtree has right subtree
      return findMin(node.right);  // the successor is the minimum of right subtree
    else {
      Node par = node.parent;
      Node cur = node;
      // if par(ent) is not root and cur(rent) is its right children
      while ((par != null) && (cur == par.right)) {
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par.value;           // this is the successor of node, watch out for null
    }
  }

  protected Baby predecessor(Node node) {
    if (node.left != null)                         // this subtree has left subtree
      return findMax(node.left);  // the predecessor is the maximum of left subtree
    else {
      Node par = node.parent;
      Node cur = node;
      // if par(ent) is not root and cur(rent) is its left children
      while ((par != null) && (cur == par.left)) { 
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par.value; // watch out for null
    }
  }

  protected Node delete(Node node, String key) {
    if (node == null)  return node;              // cannot find the item to be deleted

    if (node.value.babyName.equals(key)) {                          // this is the node to be deleted
      if (node.left == null && node.right == null)                   // this is a leaf
        node = null;                                      // simply erase this node
      else if (node.left == null && node.right != null) {   // only one child at right
        Node temp = node;
        node.right.parent = node.parent;
        node = node.right;                                                 // bypass T
        temp = null;
      }
      else if (node.left != null && node.right == null) {    // only one child at left
        Node temp = node;
        node.left.parent = node.parent;
        node = node.left;                                                  // bypass T
        temp = null;
      }
      else {                                 // has two children, find successor
        Baby successorV = successor(node.value.babyName);
        node.value = successorV;         // replace this key with the successor's key
        node.right = delete(node.right, successorV.babyName);      // delete the old successorV
      }
    }
    else if (node.value.babyName.compareTo(key) > 0)                                   // search to the right
      node.right = delete(node.right, key);
    else                                                   // search to the left
      node.left = delete(node.left, key);
    return node;                                          // return the updated BST
  }

  public void insert(Baby babyToInsert) { root = insert(root, babyToInsert); }

  public void delete(String key) { root = delete(root, key); }

  public Baby findMin() { return findMin(root); }

  public Baby findMax() { return findMax(root); }

  public void inorder() { 
    inorder(root);
    System.out.println();
  }

  public int getHeight() { return getHeight(root); }

  public Baby successor(String key) { 
    Node vPos = find(root, key);
    //return vPos == null ? null : successor(vPos);
    return successor(vPos);
  }

  public Baby predecessor(String key) { 
    Node vPos = find(root, key);
    //return vPos == null ? null : predecessor(vPos);
    return predecessor(vPos);
  }

}

class BabyNames {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class

  //public BabyTree bt;
  //public TreeMap<String, Baby> tm;
  public TreeMap<String, Baby> maleBabies;
  public TreeMap<String, Baby> femaleBabies;

  public BabyNames() {
    // Write necessary code during construction;
    //
    //bt = new BabyTree();
    maleBabies = new TreeMap<String, Baby>();
    femaleBabies = new TreeMap<String, Baby>();

  }

  void AddSuggestion(String babyName, int genderSuitability) {
    // You have to insert the information (babyName, genderSuitability)
    // into your chosen data structure
    //

    Baby baby = new Baby(babyName, genderSuitability);

    switch(genderSuitability){
      case 1:
        maleBabies.put(babyName, baby);
        break;
      case 2:
        femaleBabies.put(babyName, baby);
      default:
        break;
    }
    
  }

  void RemoveSuggestion(String babyName) {
    // You have to remove the babyName from your chosen data structure
    //
    // write your answer here
    //bt.delete(babyName);
    maleBabies.remove(babyName);
    femaleBabies.remove(babyName);
    
  }

  int Query(String START, String END, int genderPreference) {

   // int ans = 0;

    // You have to answer how many baby name starts
    // with prefix that is inside query interval [START..END)
    //
    // write your answer here
    if(genderPreference == 0)
      return maleBabies.subMap(START, END).size() + femaleBabies.subMap(START,END).size();
    else if(genderPreference == 1)
      return maleBabies.subMap(START, END).size();
    else 
      return femaleBabies.subMap(START, END).size();

   // ans++;

   // return ans;
  }

  void run() throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    while (true) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      int command = Integer.parseInt(st.nextToken());
      if (command == 0) // end of input
        break;
      else if (command == 1) // AddSuggestion
        AddSuggestion(st.nextToken(), Integer.parseInt(st.nextToken()));
      else if (command == 2) // RemoveSuggestion
        RemoveSuggestion(st.nextToken());
      else // if (command == 3) // Query
        pr.println(Query(st.nextToken(), // START
                         st.nextToken(), // END
                         Integer.parseInt(st.nextToken()))); // GENDER
    }
    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method to avoid unnecessary errors with the automated judging
    BabyNames ps2 = new BabyNames();
    ps2.run();
  }
}