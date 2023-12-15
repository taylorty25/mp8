/**
 * Node of a BitTree.
 *
 * @author Reed Colloton
 */
class BitTreeNode {
  final String bits;
  BitTreeNode left;
  BitTreeNode right;

  /**
   * Constructor
   */
  BitTreeNode(String bits) {
    this.bits = bits;
  } // BitTreeNode

  /**
   * Is it a leaf?
   */
  boolean isLeaf() {
    return false;
  } // isLeaf

} // class BitTreeNode
