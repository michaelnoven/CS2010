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
    rank = 0;
    height = 0;

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

    if (node == null){ 
    //  System.out.println("node was null in find");
      return node;
    }                               // not found
    else if (node.value.babyName.equals(key)){
     // System.out.println("found" + key); 
      return node;
    }                        // found
    else if (node.value.babyName.compareTo(key) < 0){ 
     // System.out.println("search to the right");
      return find(node.right, key);       // search to the right
    }
    else return find(node.left, key);  // search to the left

  }

  protected int calcHeight(Node node){

    if(node == null) // No value inserted 
      return -1;
    if(node.left == null && node.right == null) // Leaf
      return 0;
    else if(node.left == null) // Right Child
      return 1 + calcHeight(node.right);
    else if(node.right == null)
      return 1 + calcHeight(node.left); // Left child
    else return 1 + Math.max(calcHeight(node.left), calcHeight(node.right));// Internal node 

  }

  protected Node rotateLeft(Node node){

   // System.out.println("Rotating left ...");

    // Rotate Left
    Node temp = node.right;
    node.right = temp.left;
    temp.left = node;

    //node.height = Math.max(getHeight(node.left), getHeight(node.right));
    //temp.height = Math.max(getHeight(node.left), getHeight(node.right));
    node.height = Math.max(getHeightOfNode(node.left), getHeightOfNode(node.right)) + 1;
    temp.height = Math.max(getHeightOfNode(temp.left), getHeightOfNode(temp.right)) + 1;

    //if(temp.right != null)
      //node.height = temp.right.height;

    return temp;

  }

  protected int getHeightOfNode(Node n){

    if(n == null)
      return -1;

    return n.height;

  }

  protected Node rotateRight(Node node){

   // System.out.println("Rotating right ...");
    Node temp = node.left;

    //temp.parent = node.parent;
    //node.parent = temp;
    node.left = temp.right; // if any
    temp.right = node;

    //node.height = Math.max(getHeight(node.left), getHeight(node.right));
    //temp.height = Math.max(getHeight(node.left), getHeight(node.right));
    node.height = Math.max(getHeightOfNode(node.left), getHeightOfNode(node.right)) + 1;
    temp.height = Math.max(getHeightOfNode(temp.left), getHeightOfNode(temp.right)) + 1;

    System.out.println("after rot right the node " + node.value + "has height" + node.height) ;
    System.out.println("after rot right the node " + temp.value + "has height" + temp.height);
    /*temp.height = node.height;
    node.height = temp.left.height ;
    temp.left.height = node.height ;*/
   // if(temp.left != null)
     // node.height = temp.left.height;

    return temp;

  }

  protected Node balanceTree(Node node){

    /*int heightNodeLeftLeft = -1, heightNodeLeftRight = -1, heightNodeRightRight = -1, heightNodeRightLeft = -1;

    if(node.left != null){

      if(node.left.left != null)
        heightNodeLeftLeft = node.left.left.height;
      if(node.left.right != null)
        heightNodeLeftRight = node.left.right.height;

    }

    if(node.right != null){
    
      if(node.right.right != null)
        heightNodeRightRight = node.right.right.height;
      if(node.right.left != null)
        heightNodeRightLeft = node.right.left.height;

    }
*/
  /*  int heightRightTree, heightLeftTree;

    if(node.right == null)
      heightRightTree = -1;
    else
      heightRightTree = node.right.height;

    if(node.left == null)
      heightLeftTree = -1;
    else
      heightLeftTree =  node.left.height;*/

    

    int heightRightTree = getHeightOfNode(node.right);
    int heightLeftTree = getHeightOfNode(node.left);

    System.out.println("looking at node" + node.value);
    System.out.println("height right tree" + heightRightTree);
    System.out.println("height left tree" + heightLeftTree);


    int balanceOfNode = Math.abs(heightLeftTree - heightRightTree);
    
    // Needs rotation
    if(balanceOfNode > 1){

     System.out.println("Balancing, currently at node " + node.value + "Balance is " + balanceOfNode);

      if(heightRightTree > heightLeftTree){

      //  System.out.println("Right subtree is bigger");

        if(getHeightOfNode(node.right.right) >= getHeightOfNode(node.right.left)){
         
          node = rotateLeft(node);

        } else {

          // Double rotation
          node.right = rotateRight(node.right);
          node = rotateLeft(node);
            
        }

      // Left Subtree unbalanced
      } else {

        if(getHeightOfNode(node.left.left) >= getHeightOfNode(node.left.right)){

          System.out.println("Single right rotation");
          //System.out.println("why here");
          node = rotateRight(node);

        } else {
          // Double rotation right
          node.left = rotateLeft(node.left);
          node =  rotateRight(node);
      
        }

      }


    }

    return node;

    //System.out.println("returning");
  }

  protected Node insert(Node node, Baby babyToInsert, int heightCount) {

    if (node == null){ 
     // System.out.println("Inserting ...");
      return new Node(babyToInsert);          // insertion point is found
    }

    if (node.value.babyName.compareTo(babyToInsert.babyName) < 0){                  // search to the right
      //System.out.println("Going right");
      node.right = insert(node.right, babyToInsert, heightCount++);
      node.right.parent = node;

    }
    else {                                          // search to the left
     // System.out.println("Going left");
      node.left = insert(node.left, babyToInsert, heightCount++);
      node.left.parent = node;
      
    }

   // System.out.println("recurring trying to balance , " + node.value);
    
    node = updateHeight(node, heightCount++);
    node = balanceTree(node);
    
    return node;                                       // return the updated BST
  }

  // public methods to user 
  
  protected Node updateHeight(Node node, int v){

   // System.out.println("updateing height of" + node.value + "to " + v);
    //node.height += v;
    //node.height += v;
    node.height = getHeight(node);
    return node;
    //System.out.println("updated height of " + node.value + " to " + node.height);
    //return node;

  }

  protected Node findMin(Node node) {
    if (node == null) throw new NoSuchElementException("BST is empty, no minimum");
    else if (node.left == null) return node;                    // this is the min
    else return findMin(node.left);           // go to the left
  }

  protected Node findMax(Node node) {
    if (node == null) throw new NoSuchElementException("BST is empty, no maximum");
    else if (node.right == null) return node;                   // this is the max
    else return findMax(node.right);        // go to the right
  }

  protected void inorder(Node node) {
    if (node == null) return;
    inorder(node.left);                               // recursively go to the left
   // System.out.printf(" %s", node.value);                      // visit this BST node
    System.out.println(node.value + "height :" + node.height);
    inorder(node.right);                             // recursively go to the right
  }

  // will be used in AVL lecture
  protected int getHeight(Node node) {
    if (node == null) return -1;
    else return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
  }

  protected Node successor(Node node) {
    if (node.right != null){      
      //System.out.println("Finding min in the right subtree");                 // this subtree has right subtree
      return findMin(node.right);  // the successor is the minimum of right subtree
    }
    else {

      //System.out.println("Right subtree was null ...");

      Node par = node.parent;
      Node cur = node;
      // if par(ent) is not root and cur(rent) is its right children
      while ((par != null) && (cur == par.right)) {
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par;           // this is the successor of node, watch out for null
    }
  }

  protected Node predecessor(Node node) {
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
      return par; // watch out for null
    }
  }

/*
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
  }*/

  // Get size of tre binary tree
  private int size(Node node) { 

    if (node == null) {
      return 0;
    }
    else { 
      return(size(node.left) + 1 + size(node.right)); 
    } 
  } 



  public Baby find(String key) {

    Node res = find(root, key);
    return res.value;

  }

  //Test

  public void insert(Baby babyToInsert) { 

    root = insert(root, babyToInsert, 0); 
  }

  //public void delete(String key) { root = delete(root, key); }

  //public Baby findMin() { return findMin(root); }

 // public Baby findMax() { return findMax(root); }

  public void inorder() { 
    inorder(root);
    System.out.println();
  }

  public int getHeight() { return getHeight(root); }

  public int countSubMap(String start, String end){if (root == null) return 0; else return 5;}
 
  public Baby getRoot(){ return root.value; }

}

class BabyNames {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class

  //public BabyTree bt;
  //public TreeMap<String, Baby> tm;
  public BabyTree maleBabies;
  public BabyTree femaleBabies;

  public BabyNames() {
    // Write necessary code during construction;
    //
    //bt = new BabyTree();
    maleBabies = new BabyTree();
    femaleBabies = new BabyTree();

  }

  void AddSuggestion(String babyName, int genderSuitability) {
    // You have to insert the information (babyName, genderSuitability)
    // into your chosen data structure
    //

    Baby baby = new Baby(babyName, genderSuitability);

    switch(genderSuitability){
      case 0:
        maleBabies.insert(baby);
        femaleBabies.insert(baby);
        break;
      case 1:
        maleBabies.insert(baby);
        break;
      case 2:
        femaleBabies.insert(baby);
        break;
      default:
        System.out.println("Error in input data!");
        break;
    }
    
  }

  void RemoveSuggestion(String babyName) {

   // return;

     System.out.println("root value" + femaleBabies.getRoot());
  

     System.out.println("INORDER TRAVERSAL");
     femaleBabies.inorder();

     System.out.println("peek root : " + femaleBabies.getRoot());
    
   // System.out.println(femaleBabies.getRoot());
    // You have to remove the babyName from your chosen data structure
    //
    // write your answer here
    //bt.delete(babyName);
    /*maleBabies.remove(babyName);
    femaleBabies.remove(babyName);*/
    
  }

  int Query(String START, String END, int genderPreference) {

   // int ans = 0;

    // You have to answer how many baby name starts
    // with prefix that is inside query interval [START..END)
    //
    // write your answer her
    if(genderPreference == 0)
      return maleBabies.countSubMap(START, END) + femaleBabies.countSubMap(START,END);
    else if(genderPreference == 1)
      return maleBabies.countSubMap(START, END);
    else 
      return femaleBabies.countSubMap(START, END);


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