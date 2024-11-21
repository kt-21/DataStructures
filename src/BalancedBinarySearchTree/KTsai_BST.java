package BalancedBinarySearchTree;
/*
 * Katherine Tsai
 * This program provides methods for the Balanced BST, which is faster than the BST by ensuring subtree levels differ by at most one.
 */

public class KTsai_BST<E extends Comparable<E>> {

    private TreeNode root;

    //inserts item into its proper position in the tree
    public void insert(E item) {
        root = insert(root, item);
    }

    private TreeNode insert(TreeNode r, E item) {

        //spot is found
        if(r == null)
            return new TreeNode(item, null, null);

        //duplicate items
        if(r.data.equals(item)) {
            r.occurrences++;
            return r;
        }

        //decide whether items should go into left or right subtree
        if(item.compareTo(r.data) < 0)
            r.left = insert(r.left, item);
        else
            r.right = insert(r.right, item);

        //balance if unbalanced
        r.updateHeight();
        if(Math.abs(r.differential) > 1)
            return balance(r);

        return r;
    }

    //removes item from the tree if found
    public void remove(E item) {
        root = remove(root, item);
    }

    private TreeNode remove(TreeNode r, E item) {

        if(r == null)
            return null;

        if(r.data.equals(item)) {

            //duplicate
            if(r.occurrences > 1) {
                r.occurrences--;
                return r;
            }

            return removeNode(r);
        }

        //determine whether item is in left or right subtree
        if(item.compareTo(r.data) < 0)
            r.left = remove(r.left, item);
        else
            r.right = remove(r.right, item);

        //balance if unbalanced
        r.updateHeight();
        if(Math.abs(r.differential) > 1)
            return balance(r);

        return r;
    }

    //returns subtree with toRem removed
    private TreeNode removeNode(TreeNode toRem) {

        //no children
        if(toRem.left == toRem.right)
            return null;

        //two children
        if(toRem.left != null && toRem.right != null) {

            TreeNode success = findMin(toRem.right);
            int succOccurence = success.occurrences;

            success.occurrences = 1;		//dups
            toRem.right = remove(toRem.right, success.data);

            toRem.data = success.data;
            toRem.occurrences = succOccurence;
            return toRem;
        }

        TreeNode toReturn;

        //one child
        if(toRem.left != null) {
            toReturn = toRem.left;
            toRem.left = null;
        }
        else {
            toReturn = toRem.right;
            toRem.right = null;
        }

        return toReturn;
    }

    //returns the smallest item in subtree r
    private TreeNode findMin(TreeNode r) {

        if(r == null)
            return null;

        //leftmost node is reached
        if(r.left == null)
            return r;

        return findMin(r.left);
    }

    //rotate subtree by LL rules
    private TreeNode rotateLL(TreeNode r) {

        //rotate
        TreeNode toReturn = r.left;
        r.left = r.left.right;
        toReturn.right = r;

        //update heights
        toReturn.right.updateHeight();
        toReturn.updateHeight();

        return toReturn;
    }

    //rotate subtree by RR rules
    private TreeNode rotateRR(TreeNode r) {

        //rotate
        TreeNode toReturn = r.right;
        r.right = r.right.left;
        toReturn.left = r;

        //update heights
        toReturn.left.updateHeight();
        toReturn.updateHeight();

        return toReturn;
    }

    //rotate subtree by LR rules
    private TreeNode rotateLR(TreeNode r) {

        //left
        TreeNode toReturn = r.left.right;
        r.left.right = r.left.right.left;
        r.left.updateHeight();
        toReturn.left = r.left;

        //right
        r.left = toReturn.right;
        r.updateHeight();
        toReturn.right = r;
        toReturn.updateHeight();

        return toReturn;
    }

    //rotate subtree by RL rules
    private TreeNode rotateRL(TreeNode r) {

        //right
        TreeNode toReturn = r.right.left;
        r.right.left = r.right.left.right;
        r.right.updateHeight();
        toReturn.right = r.right;

        //left
        r.right = toReturn.left;
        r.updateHeight();
        toReturn.left = r;
        toReturn.updateHeight();

        return toReturn;
    }

    //calls appropriate rotation method to balance tree
    private TreeNode balance(TreeNode r) {

        if(r.differential < 0) {

            if(r.left.differential > 0)
                return rotateLR(r);

            return rotateLL(r);
        }

        if(r.right.differential < 0)
            return rotateRL(r);

        return rotateRR(r);
    }

    public class TreeNode {

        private E data;
        private TreeNode left;
        private TreeNode right;
        private int occurrences;
        private int height;
        private int differential;

        public TreeNode(E d, TreeNode l, TreeNode r) {

            data = d;
            left = l;
            right = r;
            occurrences = 1;
            height = 1;
            differential = 0;
        }

        public void updateHeight() {

            //no children
            if(left == right) {
                height = 1;
                differential = 0;
                return;
            }

            //one child
            if(left == null) {
                height = right.height + 1;
                differential = right.height;
                return;
            }
            if(right == null) {
                height = left.height + 1;
                differential = -left.height;
                return;
            }

            //two children
            if(left.data.compareTo(right.data) < 0)
                height = right.height + 1;
            else
                height = left.height + 1;
            differential = right.height - left.height;
        }
    }
}
