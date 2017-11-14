// An IntTree object represents an entire binary tree of ints.
public class IntTree {
    private IntTreeNode overallRoot;   // null for an empty tree
    
    public void print() {
        print(overallRoot);
        System.out.println();   // end the line of output
    }

    private void print(IntTreeNode root) {
        // (base case is implicitly to do nothing on null)
        if (root != null) {
            // recursive case: print left, center, right
            print(overallRoot.left);
            System.out.print(overallRoot.data + " ");
            print(overallRoot.right);
        }
    }
}
