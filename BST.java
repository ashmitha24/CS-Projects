import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 *
 * @author Ashmitha Julius Aravind
 * @version 11.0
 * @userid aaravind7
 * @GTID 903700995
 *
 * Collaborators: N/A
 *
 * Resources: N/A
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is not valid");
        }
        for (T x: data) {
            add(x);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is not valid");
        }
        root = hAdd(root, data);
    }

    /**
     * Helper method to add data to BST.
     * @param curr the current node
     * @param data the data to add
     * @return the current node
     */
    private BSTNode<T> hAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode(data);
        } else if ((curr.getData()).compareTo(data) > 0) {
            curr.setLeft(hAdd(curr.getLeft(), data));
        } else if ((curr.getData()).compareTo(data) < 0) {
            curr.setRight(hAdd(curr.getRight(), data));
        }
        return curr;
    }


    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is not valid");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = rRemove(root, data, dummy);
        return dummy.getData();
    }

    /**
     * Helper method to remove data from BST.
     * @param curr the current node
     * @param data the given data
     * @param node the dummy node to store data
     * @return dummy node
     * @throws java.util.NoSuchElementException if data is not in the tree
     */
    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> node) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in tree");
        } else if ((curr.getData()).compareTo(data) > 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, node));
        } else if ((curr.getData()).compareTo(data) < 0) {
            curr.setRight(rRemove(curr.getRight(), data, node));
        } else {
            node.setData(curr.getData());
            size--;
            if (curr.getLeft() == null & curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode(null);
                curr.setRight(successor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * Helper method to return successor during removal.
     * @param curr the current node
     * @param holder the dummy node for the data
     * @return the current node
     */
    private BSTNode<T> successor(BSTNode<T> curr, BSTNode<T> holder) {
        if (curr.getLeft() == null) {
            holder.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(successor(curr.getLeft(), holder));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is not valid");
        }
        T input = searchNode(root, data);
        if (input == null) {
            throw new NoSuchElementException("Data is not in the tree");
        }
        return input;
    }

    /**
     * Helper method to get value from BST.
     * @param curr the current node
     * @param value the given data
     * @return the value of the node
     */
    public T searchNode(BSTNode<T> curr, T value) {
        if (curr == null) {
            return null;
        }
        if (curr.getData() == value) {
            return curr.getData();
        } else if ((curr.getData()).compareTo(value) > 0) {
            return searchNode(curr.getLeft(), value);
        }
        return searchNode(curr.getRight(), value);
    }


    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is not valid");
        }
        return rContains(root, data);
    }

    /**
     * Helper method that tells whether value is in BST.
     * @param node the current node
     * @param data the given data
     * @return boolean on if the data exists in BST
     */
    public boolean rContains(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        } else if ((node.getData()).compareTo(data) > 0) {
            return rContains(node.getLeft(), data);
        } else if ((node.getData()).compareTo(data) < 0) {
            return rContains(node.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> traverse = new ArrayList<T>();
        return preOr(traverse, root);
    }

    /**
     * Helper method that traverses BST.
     * @param list the list of element in BST
     * @param node the current node
     * @return list of items in BST
     */
    private List<T> preOr(List<T> list, BSTNode<T> node) {
        if (node != null) {
            list.add(node.getData());
            preOr(list, node.getLeft());
            preOr(list, node.getRight());
        }
        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> traverse = new ArrayList<T>();
        return inOr(traverse, root);
    }
    /**
     * Helper method that traverses BST.
     * @param list the list of element in BST
     * @param node the current node
     * @return list of items in BST
     */
    private List<T> inOr(List<T> list, BSTNode<T> node) {
        if (node != null) {
            inOr(list, node.getLeft());
            list.add(node.getData());
            inOr(list, node.getRight());
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> traverse = new ArrayList<T>();
        return postOr(traverse, root);
    }
    /**
     * Helper method that traverses BST.
     * @param list the list of element in BST
     * @param node the current node
     * @return list of items in BST
     */
    private List<T> postOr(List<T> list, BSTNode<T> node) {
        if (node != null) {
            postOr(list, node.getLeft());
            postOr(list, node.getRight());
            list.add(node.getData());
        }
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<>();
        List<T> list = new ArrayList<T>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.remove();
            if (curr != null) {
                list.add(curr.getData());
                q.add(curr.getLeft());
                q.add(curr.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(root);
    }

    /**
     * Helper method to find BST height.
     * @param node the current node
     * @return int height
     */
    private int rHeight(BSTNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            int left = rHeight(node.getLeft());
            int right = rHeight(node.getRight());
            return Math.max(left, right) + 1;
        }
    }


    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Finds the path between two elements in the tree, specifically the path
     * from data1 to data2, inclusive of both.
     *
     * To do this, you must first find the deepest common ancestor of both data
     * and add it to the list. Then traverse to data1 while adding its ancestors
     * to the front of the list. Finally, traverse to data2 while adding its
     * ancestors to the back of the list. Please note that there is no
     * relationship between the data parameters in that they may not belong
     * to the same branch. You will most likely have to split off and
     * traverse the tree for each piece of data.
     * *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you will have to add to the front and
     * back of the list.
     *
     * This method only need to traverse to the deepest common ancestor once.
     * From that node, go to each data in one traversal each. Failure to do
     * so will result in a penalty.
     *
     * If both data1 and data2 are equal and in the tree, the list should be
     * of size 1 and contain the element from the tree equal to data1 and data2.
     *
     * Ex:
     * Given the following BST composed of Integers
     *              50
     *          /        \
     *        25         75
     *      /    \
     *     12    37
     *    /  \    \
     *   11   15   40
     *  /
     * 10
     * findPathBetween(10, 40) should return the list [10, 11, 12, 25, 37, 40]
     * findPathBetween(50, 37) should return the list [50, 25, 37]
     * findPathBetween(75, 75) should return the list [75]
     *
     * Must be O(log n) for a balanced tree and O(n) for worst case.
     *
     * @param data1 the data to start the path from
     * @param data2 the data to end the path on
     * @return the unique path between the two elements
     * @throws java.lang.IllegalArgumentException if either data1 or data2 is
     *                                            null
     * @throws java.util.NoSuchElementException   if data1 or data2 is not in
     *                                            the tree
     */
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data is not valid");
        }
        LinkedList<T> list = new LinkedList<T>();
        BSTNode<T> ancestor = ancestor(root, data1, data2);

        list = traverse1(ancestor, data1, list);

        BSTNode<T> node2 = ancestor;
        list = traverse2(node2, data2, list, ancestor.getData());
        return list;
    }

    /**
     * Helper method that finds deepest common ancestor.
     * @param curr the current node
     * @param data1 first data value
     * @param data2 second data value
     * @return the ancestor node
     */
    private BSTNode<T> ancestor(BSTNode<T> curr, T data1, T data2) {
        if ((curr.getData()).compareTo(data1) >= 0 && (curr.getData()).compareTo(data2) <= 0) {
            return curr;
        } else if ((curr.getData()).compareTo(data1) <= 0 && (curr.getData()).compareTo(data2) >= 0) {
            return curr;
        } else if ((curr.getData()).compareTo(data1) > 0 && (curr.getData()).compareTo(data2) > 0) {
            return ancestor(curr.getLeft(), data1, data2);
        }
        return ancestor(curr.getRight(), data1, data2);
    }

    /**
     * Helper method for traversal up until first data.
     * @param curr the current node
     * @param data1 the first data value
     * @param list list of elements in path
     * @return the list of element in path
     * @throws java.util.NoSuchElementException if data1 is not in tree
     */
    private LinkedList<T> traverse1(BSTNode<T> curr, T data1, LinkedList<T> list) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in tree");
        }
        if (curr.getData() == data1) {
            list.addFirst(curr.getData());
        } else if ((curr.getData()).compareTo(data1) > 0) {
            list.addFirst(curr.getData());
            traverse1(curr.getLeft(), data1, list);
        } else if ((curr.getData()).compareTo(data1) < 0) {
            list.addFirst(curr.getData());
            traverse1(curr.getRight(), data1, list);
        }
        return list;
    }

    /**
     * Helper method for traversal up until second data value.
     * @param curr the current node
     * @param data2 the second data value
     * @param list list of elements in path
     * @param ancestor the common ancestor
     * @return list of elements in path
     * @throws java.util.NoSuchElementException if data2 is not in tree
     */
    private LinkedList<T> traverse2(BSTNode<T> curr, T data2, LinkedList<T> list, T ancestor) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in tree");
        }
        if (curr.getData() == data2 && ancestor != data2) {
            list.addLast(curr.getData());
        } else if ((curr.getData()).compareTo(data2) > 0) {
            if (curr.getData() != ancestor) {
                list.addLast(curr.getData());
            }
            traverse2(curr.getLeft(), data2, list, ancestor);
        } else if ((curr.getData()).compareTo(data2) < 0) {
            if (curr.getData() != ancestor) {
                list.addLast(curr.getData());
            }
            traverse2(curr.getRight(), data2, list, ancestor);
        }
        return list;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
