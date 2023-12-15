import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * Stores mappings from bits to values.
 *
 * @author Tyrell Taylor
 */
class BitTree {
  private final int depth;
  private BitTreeNode root = null;

  /**
   * builds a tree that will store mappings from strings of n bits to strings
   */
  BitTree(int n) {
    this.depth = n;
  } // BitTree()

  /**
   * Follows the path through the tree given by bits (adding nodes as appropriate)
   * and adds or replaces the value at the end with value.
   * set should throw an exception if bits is the inappropriate length
   * or contains values other than 0 or 1.
   */
  void set(String bits, String value) {
    if (bits.length() != this.depth || !bits.matches("[0-9]+")) {
      throw new IllegalArgumentException();
    } // if
    this.root = setRecurse(bits, value, this.root);
  } // set()

  /**
   * set helper
   */
  private BitTreeNode setRecurse(String bits, String value, BitTreeNode node) {
    if (bits.isEmpty()) {
      return new BitTreeLeaf(bits, value);
    } else {
      if (node == null) {
        node = new BitTreeNode(bits);
      } // if
      if (bits.charAt(0) == '0') {
        node.left = setRecurse(bits.substring(1), value, node.left);
      } else {
        node.right = setRecurse(bits.substring(1), value, node.right);
      } // if/else
    } // if/else
    return node;
  } // setRecurse()

  /**
   * follows the path through the tree given by bits,
   * returning the value at the end. If there is no such path,
   * or if bits is the incorrect length, get should throw an exception.
   */
  String get(String bits) {
    if (bits.length() != this.depth || !bits.matches("[0-9]+")) {
      throw new IllegalArgumentException();
    } // if
    return getRecurse(bits, this.root);
  } // get()

  /**
   * get helper
   */
  private String getRecurse(String bits, BitTreeNode node) {
    if (node == null) {
      throw new IllegalArgumentException();
    } else if (node.isLeaf() && bits.isEmpty()) {
      return ((BitTreeLeaf) node).value;
    } else {
      if (bits.charAt(0) == '0') {
        return getRecurse(bits.substring(1), node.left);
      } else {
        return getRecurse(bits.substring(1), node.right);
      } // if/else
    } // if/else
  } // get()

  /**
   * prints out the contents of the tree in CSV format.
   * For example, one row of our braille tree will be “101100,M”
   */
  void dump(PrintWriter pen) {
    dumpRecurse(pen, this.root);
  } // dump()

  /**
   * dump helper
   */
  private void dumpRecurse(PrintWriter pen, BitTreeNode node) {
    if (node == null) {
      return;
    } // if
    if (node.isLeaf()) {
      pen.println(node.bits + "," + ((BitTreeLeaf) node).value);
    } else {
      dumpRecurse(pen, node.left);
      dumpRecurse(pen, node.right);
    } // if/else
  } // dump()

  /**
   * Reads a series of lines of the form bits,value and stores them in the tree.
   */
  void load(InputStream source) {
    String[] lines;
    try {
      lines = new String(source.readAllBytes()).split("\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    for (String line : lines) {
      String[] elements = line.split(",");
      if (elements[0].equals("h")) {
        int ik = 0;
      }
      this.set(elements[0], elements[1]);
    } // for
  } // set()

} // class BitTree
