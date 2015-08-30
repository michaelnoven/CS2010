import java.util.*;
import java.util.NoSuchElementException; // we will use this to illustrate Java Error Handling mechanism

// write your matric number here: A0146876M
// write your name here: Michael Novén
// write list of collaborators here:
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

// Every vertex in this BST is a Java Class
class BSTVertex {
  BSTVertex(int v) { key = v; parent = left = right = null; height = 0; }
  // all these attributes remain public to slightly simplify the code
  public BSTVertex parent, left, right;
  public int key;
  public int height; // will be used in AVL lecture
}

class Node {

  public Baby value;
  public Node parent, left, right;
  public int height;

  public Node(Baby value){

    this.value = value;
    parent = left = right = null;
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

}

class BabyTree{

  // instance variables
  protected Node root;

  // constructor
  public BabyTree() { root = null; }

  // protected class methods 
  protected Node search(Node node, String key) {

    if (node == null) return node;                               // not found
    else if (node.value.babyName.equals(key)) return node;                        // found
    else if (node.value.babyName.compareTo(key) > 0)  return search(node.right, key);       // search to the right
    else return search(node.left, key);  // search to the left

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

  /* public methods to user */
  public Baby search(String key) {

    Node res = search(root, key);
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
    System.out.printf(" %d", node.value);                      // visit this BST node
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

  public void insert(Baby babyToInsert) { root = insert(root, babyToInsert); }

  public Baby findMin() { return findMin(root); }

  public Baby findMax() { return findMax(root); }

  public void inorder() { 
    inorder(root);
    System.out.println();
  }

  public int getHeight() { return getHeight(root); }

  public Baby successor(String key) { 
    Node vPos = search(root, key);
    //return vPos == null ? null : successor(vPos);
    return successor(vPos);
  }

  public Baby predecessor(String key) { 
    Node vPos = search(root, key);
    //return vPos == null ? null : predecessor(vPos);
    return predecessor(vPos);
  }


}

// This is just a sample implementation
// There are other ways to implement BST concepts...
class BST {
  protected BSTVertex root;

  protected BSTVertex search(BSTVertex T, int v) {
    if (T == null) return T;                                  // not found
    else if (T.key == v) return T;                                      // found
    else if (T.key < v)  return search(T.right, v);       // search to the right
    else                 return search(T.left, v);         // search to the left
  }

  protected BSTVertex insert(BSTVertex T, int v) {
    if (T == null){
      System.out.println("Tree was empty. inserting at root");
      return new BSTVertex(v);          // insertion point is found

    } 

    if (T.key < v) {      

      System.out.println("Bigger, inserting right");                                // search to the right
      T.right = insert(T.right, v);
      T.right.parent = T;
    }
    else {  
      System.out.println("smaller, inserting right");                                                  // search to the left
      T.left = insert(T.left, v);
      T.left.parent = T;
    }

    return T;                                          // return the updated BST
  }

  protected void inorder(BSTVertex T) {
    if (T == null) return;
    inorder(T.left);                               // recursively go to the left
    System.out.printf(" %d", T.key);                      // visit this BST node
    inorder(T.right);                             // recursively go to the right
  }

  // Example of Java error handling mechanism
  /* // old code, returns -1 when there is no minimum (the BST is empty)
  protected int findMin(BSTVertex T) {
         if (T == null)      return -1;                             // not found
    else if (T.left == null) return T.key;                    // this is the min
    else                     return findMin(T.left);           // go to the left
  }
  */

  protected int findMin(BSTVertex T) {
         if (T == null)      throw new NoSuchElementException("BST is empty, no minimum");
    else if (T.left == null) return T.key;                    // this is the min
    else                     return findMin(T.left);           // go to the left
  }

  protected int findMax(BSTVertex T) {
         if (T == null)       throw new NoSuchElementException("BST is empty, no maximum");
    else if (T.right == null) return T.key;                   // this is the max
    else                      return findMax(T.right);        // go to the right
  }

  protected int successor(BSTVertex T) {
    if (T.right != null)                       // this subtree has right subtree
      return findMin(T.right);  // the successor is the minimum of right subtree
    else {
      BSTVertex par = T.parent;
      BSTVertex cur = T;
      // if par(ent) is not root and cur(rent) is its right children
      while ((par != null) && (cur == par.right)) {
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par == null ? -1 : par.key;           // this is the successor of T
    }
  }

  protected int predecessor(BSTVertex T) {
    if (T.left != null)                         // this subtree has left subtree
      return findMax(T.left);  // the predecessor is the maximum of left subtree
    else {
      BSTVertex par = T.parent;
      BSTVertex cur = T;
      // if par(ent) is not root and cur(rent) is its left children
      while ((par != null) && (cur == par.left)) { 
        cur = par;                                         // continue moving up
        par = cur.parent;
      }
      return par == null ? -1 : par.key;           // this is the successor of T
    }
  }

  protected BSTVertex delete(BSTVertex T, int v) {
    if (T == null)  return T;              // cannot find the item to be deleted

    if (T.key == v) {                          // this is the node to be deleted
      if (T.left == null && T.right == null)                   // this is a leaf
        T = null;                                      // simply erase this node
      else if (T.left == null && T.right != null) {   // only one child at right
        BSTVertex temp = T;
        T.right.parent = T.parent;
        T = T.right;                                                 // bypass T
        temp = null;
      }
      else if (T.left != null && T.right == null) {    // only one child at left
        BSTVertex temp = T;
        T.left.parent = T.parent;
        T = T.left;                                                  // bypass T
        temp = null;
      }
      else {                                 // has two children, find successor
        int successorV = successor(v);
        T.key = successorV;         // replace this key with the successor's key
        T.right = delete(T.right, successorV);      // delete the old successorV
      }
    }
    else if (T.key < v)                                   // search to the right
      T.right = delete(T.right, v);
    else                                                   // search to the left
      T.left = delete(T.left, v);
    return T;                                          // return the updated BST
  }

  public BST() { root = null; }

  public int search(int v) {
    BSTVertex res = search(root, v);
    return res == null ? -1 : res.key;
  }

  public void insert(int v) { root = insert(root, v); }

  public void inorder() { 
    inorder(root);
    System.out.println();
  }

  public int findMin() { return findMin(root); }

  public int findMax() { return findMax(root); }

  public int successor(int v) { 
    BSTVertex vPos = search(root, v);
    return vPos == null ? -1 : successor(vPos);
  }

  public int predecessor(int v) { 
    BSTVertex vPos = search(root, v);
    return vPos == null ? -1 : predecessor(vPos);
  }

  public void delete(int v) { root = delete(root, v); }

  // will be used in AVL lecture
  protected int getHeight(BSTVertex T) {
    if (T == null) return -1;
    else return Math.max(getHeight(T.left), getHeight(T.right)) + 1;
  }

  public int getHeight() { return getHeight(root); }
}

class BSTDemo {
  public static void main(String[] args) throws Exception {
    BST T = new BST();                                           // an empty BST

    try {
      System.out.println(T.findMin());                       // Exception occurs
      System.out.println(T.findMax());                   // will not be executed
    }
    catch (NoSuchElementException e) {
      System.out.println(e);
    }

    // Sample BST as shown in Lecture
    T.insert(15);
    T.insert(23);
    T.insert(6);
    T.insert(71);
    T.insert(50);
    T.insert(4);
    T.insert(7);
    T.insert(5);

    System.out.println(T.search(71));                               // found, 71
    System.out.println(T.search(7));                                 // found, 7
    System.out.println(T.search(22));                           // not found, -1

    try {
      System.out.println(T.findMin());                                      // 4
      System.out.println(T.findMax());                                     // 71
    }
    catch (NoSuchElementException e) {
      System.out.println(e);                             // will not be executed
    }

    System.out.println(T.successor(23));                                   // 50
    System.out.println(T.successor(7));                                    // 15
    System.out.println(T.successor(71));                                   // -1
    System.out.println(T.predecessor(23));                                 // 15
    System.out.println(T.predecessor(7));                                   // 6
    System.out.println(T.predecessor(71));                                 // 50

    T.inorder();                          // The BST: 4, 5, 6, 7, 15, 23, 50, 71

    System.out.println("Deleting 5");
    T.delete(5);
    System.out.println("Deleting 71");
    T.delete(71);
    System.out.println("Deleting 15");
    T.delete(15);

    T.inorder();                                     // The BST: 4, 6, 7, 23, 50
  }
}