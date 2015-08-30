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
  public int height, rank, size;

  public Node(Baby value){

    this.value = value;
    parent = left = right = null;
    rank = height = 0;
    size = 1;

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

  protected Node rotateLeft(Node node){

//    System.out.println("rotating left");
    // Rotate Left
    Node temp = node.right;
    node.right = temp.left;
    temp.left = node;

    node.height = Math.max(getHeightOfNode(node.left), getHeightOfNode(node.right)) + 1;
    temp.height = Math.max(getHeightOfNode(temp.left), getHeightOfNode(temp.right)) + 1;
    
    node.size = getSizeOfNode(node.left) + getSizeOfNode(node.right) + 1;
    temp.size = getSizeOfNode(temp.left) + getSizeOfNode(temp.right) + 1;

    //node.size = getSizeOfNode(node.left) + getSizeOfNode(node.right);

    return temp;

  }

  protected int getHeightOfNode(Node node){

    if(node == null)
      return -1;

    return node.height;

  }

  protected Node rotateRight(Node node){

   // System.out.println("Rotating right ...");
    Node temp = node.left;

    node.left = temp.right; // if any
    temp.right = node;

    node.height = Math.max(getHeightOfNode(node.left), getHeightOfNode(node.right)) + 1;
    temp.height = Math.max(getHeightOfNode(temp.left), getHeightOfNode(temp.right)) + 1;

    node.size = getSizeOfNode(node.left) + getSizeOfNode(node.right) + 1;
    temp.size = getSizeOfNode(temp.left) + getSizeOfNode(temp.right) + 1;

    //node.size = getSizeOfNode(node.left) + getSizeOfNode(node.right);

    return temp;

  }

  protected Node balanceTree(Node node){

    int heightRightTree = getHeightOfNode(node.right);
    int heightLeftTree = getHeightOfNode(node.left);

    int balanceOfNode = Math.abs(heightLeftTree - heightRightTree);
    
    // Needs rotation
    if(balanceOfNode > 1){

     //System.out.println("Balancing, currently at node " + node.value + "Balance is " + balanceOfNode);

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

        //  System.out.println("Single right rotation");
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
    
    //node = updateHeight(node, heightCount++);
    // Updating height
    //System.out.println("updatin and stuff" + node.value);
    node.height = Math.max(getHeightOfNode(node.left), getHeightOfNode(node.right)) + 1;
    node.size = getSizeOfNode(node.left) + getSizeOfNode(node.right) + 1;
    node = balanceTree(node);
    
    return node;                                       // return the updated BST
  }


  protected Node updateSize(Node node){

    node.size = size(node);
    return node;

  }

  protected int getSizeOfNode(Node node){

    if(node == null)
      return 0;

    return node.size;

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
    System.out.println(node.value + "height :" + node.height + "size : " + node.size);
    inorder(node.right);                             // recursively go to the right
  }

  // will be used in AVL lecture
 /* protected int getHeight(Node node) {
    if (node == null) return -1;
    else {
      return Math.max(getHeightOfNode(node.left), getHeightOfNode(node.right)) + 1;
    }
  }*/

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

  // Get size of tre binary tree
  protected int size(Node node) { 

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

  public void insert(Baby babyToInsert) { 

    root = insert(root, babyToInsert, 0); 
    //root = updateSize(root);

  }

  //public void delete(String key) { root = delete(root, key); }

  //public Baby findMin() { return findMin(root); }

 // public Baby findMax() { return findMax(root); }

  public void inorder() { 
    inorder(root);
    System.out.println();
  }

  //public int getHeight() { return getHeight(root); }

  public int countSubMap(String start, String end){

    if(root == null)
      return 0;

    Node leftBound = root, rightBound = root;
    int cutOffBoundLeft = 0, cutOffBoundRight = 0;    

    // Look for node that is smaller than start
    if(leftBound.value.babyName.compareTo(start) >= 0){
      while(leftBound.left != null && leftBound.left.value.babyName.compareTo(start) >= 0){

        leftBound = leftBound.left;

      }

    } else if(leftBound.right != null){

      while(leftBound.right != null && leftBound.right.value.babyName.compareTo(start) >= 0){

        leftBound = leftBound.right;

      }

    }

    if(rightBound.value.babyName.compareTo(end) < 0){

      // Look for node that is bigger than end
      while(rightBound.right != null && rightBound.right.value.babyName.compareTo(end) < 0){

        rightBound = rightBound.right;

      } 

    } else if(rightBound.left != null){

      while(rightBound.left != null && rightBound.right.value.babyName.compareTo(end) < 0){

        rightBound = rightBound.left;

      }

    }

         System.out.println("leftbound stopped at" + leftBound.value.babyName);
     System.out.println("rightBOund stopped at" + rightBound.value.babyName);

     // Not in interval
    if(leftBound.value.babyName.compareTo(start) < 0 )
      return 0;

    if(leftBound.value.babyName.equals(rightBound.value.babyName))
      return 1;

    if(leftBound.left != null)
        cutOffBoundLeft = getSizeOfNode(leftBound.left);

    if(rightBound.right != null)
      cutOffBoundRight =  getSizeOfNode(rightBound.right);


   

    // If still nodes left, cut them off
   /* if(leftBound.left != null)
      cutOffBoundLeft += getSizeOfNode(leftBound.left);

    if(rightBound.right != null)
      cutOffBoundRight = getSizeOfNode(rightBound.right);*/

    // CORNER CASE with only two nodes 
    /*if(root.left == null){
      if(leftBound.value.babyName.compareTo(start) < 0 && leftBound.right != null){
       // System.out.println("start is not in interval");
        leftBound = leftBound.right;
      
        if(leftBound.value.babyName.compareTo(start) < 0){
         // System.out.println("not this time eitehr");
          return 0;
        } else {
          return 1;
        }

      }

    }*/

    // System.out.println("root " + getSizeOfNode(root));
    // System.out.println("cutleft " + cutOffBoundLeft);
    // System.out.println("cutright " + cutOffBoundRight);

    return getSizeOfNode(root) - cutOffBoundLeft - cutOffBoundRight;
 
  }
 
  public Baby getRoot(){ return root.value; }

}

class BabyNames {
  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  public BabyTree maleBabies;
  public BabyTree femaleBabies;

  public BabyNames() {
    // Write necessary code during construction;
  
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

    return;

     /*System.out.println("root value" + femaleBabies.getRoot());
  

     System.out.println("INORDER TRAVERSAL");
     femaleBabies.inorder();

     System.out.println("peek root : " + femaleBabies.getRoot());*/
    
  }

  int Query(String START, String END, int genderPreference) {
    // write your answer her
    if(genderPreference == 0)
      return maleBabies.countSubMap(START, END) + femaleBabies.countSubMap(START,END);
    else if(genderPreference == 1)
      return maleBabies.countSubMap(START, END);
    else 
      return femaleBabies.countSubMap(START, END);

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