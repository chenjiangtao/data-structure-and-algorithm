package com.crw.java.tree;

/**
 * 二叉查找树
 * 1. 提供 节点类，一个根节点
 *
 * @param <T>
 */
public class BinarySearchTree<T extends Comparable<? super T>> {

    /**
     * 节点
     *
     * @param <T>
     */
    private static class BinaryNode<T extends Comparable<? super T>> {
        T element; // 节点元素
        BinaryNode<T> left; //左儿子
        BinaryNode<T> right; //右儿子

        public BinaryNode(T theElement) {
            this(theElement, null, null);
        }

        public BinaryNode(T theElement, BinaryNode<T> left, BinaryNode<T> right) {
            this.element = theElement;
            this.left = left;
            this.right = right;
        }
    }

    private BinaryNode<T> root; // 根节点

    public BinarySearchTree(T t) {
        root = new BinaryNode<T>(t, null, null);
    }

    public BinarySearchTree() {
        root = null;
    }

    public void makeEmpty() {
        this.root = null;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public boolean constains(T t) {
        return constains(t, root);
    }

    /**
     * 查询元素是否在某节点内
     *
     * @param t
     * @param node
     * @return
     */
    public boolean constains(T t, BinaryNode<T> node) {
        if (node == null) {
            return false;
        }
        int compareValue = t.compareTo(node.element); // 与当前节点比较
        if (compareValue < 0) { // 比当前节点小就与左子树比较
            return constains(t, node.left);
        } else if (compareValue > 0) { // 比当前节点大就与右子树比较
            return constains(t, node.right);
        } else {
            return false;
        }
    }

    public void insert(T t) {
        root = insert(t, root);
    }

    /**
     * 插入元素，思想与constains一样
     *
     * @param t
     * @param node
     * @return
     */
    public BinaryNode<T> insert(T t, BinaryNode<T> node) {
        if (node == null) { // 递归完结，没有比较的节点就插入
            return new BinaryNode<T>(t, null, null);
        }

        int compareValue = t.compareTo(node.element); // 与当前节点比较
        if (compareValue < 0) { // 比当前节点小就与左子树比较
            node.left = insert(t, node.left);
        } else if (compareValue > 0) { // 比当前节点大就与右子树比较
            node.right = insert(t, node.right);
        } else {
            // do nothing;
        }
        return node;
    }

    public T findMin() {
        if (isEmpty()) {
            return null;
        }
        return findMin(root).element;
    }

    /**
     * 查找某节点下最小的节点,递归方式
     *
     * @param node
     * @return
     */
    private BinaryNode<T> findMin(BinaryNode<T> node) {
        if (node == null) {
            return null;
        } else if (node.left != null) {
            return findMin(node.left);
        } else {
            return node;
        }
    }

    public T findMax() {
        if (isEmpty()) {
            return null;
        }
        return findMax(root).element;
    }

    /**
     * 某节点下找到最大的节点,while循环方式
     *
     * @param node
     * @return
     */
    private BinaryNode<T> findMax(BinaryNode<T> node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * 删除某节点下的某个值，删除操作比较复杂，涉及的情况比较多：
     * 1. 如果节点是树叶，可直接删除
     * 2. 如果节点只有一个儿子，直接修改其父节点的引用到其儿子上即可
     * 3. 如果节点有两个儿子，其节点的右子树上最小的数据代替该节点的数据并递归的删除该节点。因为右子树最小的节点不可能有子节点，所以第二次remove很容易
     *
     * @param t
     * @param node
     * @return
     */
    private BinaryNode<T> remove(T t, BinaryNode<T> node) {
        if (node == null) {
            return node; // 找不到指定的元素
        }
        int compareValue = t.compareTo(node.element);
        if (compareValue < 0) { // 从左子树上查找值为t的节点
            node.left = remove(t, node.left);
        } else if (compareValue > 0) { // 从右子树上查找值为t的节点
            node.right = remove(t, node.right);
        } else if (node.left != null && node.right != null) { // 找到该节点，并且该节点有两个儿子
            node.element = findMin(node.right).element; // 找到右子树上最小的元素替换当前节点的元素
            node.right = remove(node.element, node.right); // 递归删除右子树上的当前元素
        } else { // 找到该节点，并且节点只有一个儿子
            node = (node.left != null) ? node.left : node.right;
        }
        return node;
    }

    private void remove(T t) {
        root = remove(t, root);
    }

    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        }

        printTree(root);
    }

    /**
     * 打印节点下的所有元素
     *
     * @param node
     */
    public void printTree(BinaryNode<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.println(node.element);
            printTree(node.right);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(6);
        tree.insert(10);
        tree.insert(8);
        tree.insert(2);
        tree.insert(3);
        tree.insert(7);
        tree.insert(5);
        tree.printTree();

        tree.remove(5);
        tree.remove(8);
        tree.printTree();
    }
}
