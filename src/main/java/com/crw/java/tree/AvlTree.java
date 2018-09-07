package com.crw.java.tree;

/**
 * AVL树
 * 1.提供节点，节点中需有树高属性
 * 参考博客: https://www.cnblogs.com/skywang12345/p/3577479.html
 *
 * @param <T>
 */
public class AvlTree<T extends Comparable<? super T>> {

    private AVLNode<T> root;

    /**
     * AVL树节点
     *
     * @param <T>
     */
    class AVLNode<T extends Comparable<? super T>> {
        T element; // 数据
        int height; // 该节点高度
        AVLNode<T> left; // 左孩子
        AVLNode<T> right; // 右孩子

        public AVLNode(T element) {
            this(element, null, null);
        }

        public AVLNode(T element, AVLNode<T> left, AVLNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.height = 0;
        }
    }

    /**
     * 获取节点的的高度
     *
     * @param node
     * @return
     */
    private int height(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }

    public int height() {
        return height(root);
    }

    /**
     * 获取较大的值
     *
     * @param a
     * @param b
     * @return
     */
    private int max(int a, int b) {
        return a > b ? a : b;
    }

    /**
     * LL：左左对应的情况(左单旋转)。
     * 围绕"失去平衡的AVL根节点"进行操作，因为是LL的情况，就用手抓着"左孩子"使劲摇，
     * 1.将"左孩子"变成AVL根节点，
     * 2."左孩子的右子树" 变成 "当前AVL根节点的左子树"。
     *
     * @param root 失衡avl根节点
     * @return 旋转后的根节点
     */
    private AVLNode<T> leftLeftRotation(AVLNode<T> root) {
        AVLNode<T> left = root.left; // 当前失衡avl根节点的左子树
        root.left = left.right; // "左孩子的右子树" 变成 "AVL根节点的左子树"
        left.right = root; // "左孩子"变成AVL根节点

        // 重新计算高度
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        left.height = Math.max(height(left.left), root.height) + 1;

        return left;
    }

    /**
     * RR：右右对应的情况(右单旋转)。
     * 与LL情况类似
     * 1.将"右孩子"变成AVL根节点
     * 2."右孩子的左子树" 变成 "当前AVL根节点的右子树"。
     *
     * @param root 失衡avl根节点
     * @return 旋转后的根节点
     */
    private AVLNode<T> rightRightRotation(AVLNode<T> root) {
        AVLNode<T> right = root.right; // 当前失衡avl根节点的右子树
        root.right = right.left; // "右孩子的左子树" 变成 "当前AVL根节点的右子树"
        right.left = root; // 将"右孩子"变成AVL根节点

        // 重新计算高度
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        right.height = Math.max(height(right.right), root.height) + 1;

        return right;
    }

    /**
     * LR：左右对应的情况(左双旋转)。
     * 进行两次旋转：
     * 1.以"根节点的左子树"为根节点进行一次RR单旋。
     * 2.以"根节点"为根节点进行一次LL单旋。
     *
     * @param root
     * @return 旋转后的根节点
     */
    private AVLNode<T> leftRightRotation(AVLNode<T> root) {
        root.left = rightRightRotation(root.left); // 1.以"根节点的左子树"为根节点进行一次RR单旋。
        return leftLeftRotation(root); // 2.以"根节点"为根节点进行一次LL单旋。
    }

    /**
     * RL：右左对应的情况(右双旋转)。
     * LR双旋的对称情况：
     * 1.以"根节点的右子树"为根节点进行一次LL单旋。
     * 2.以"根节点"为根节点进行一次RR单旋。
     *
     * @param root
     * @return
     */
    private AVLNode<T> rightLeftRotation(AVLNode<T> root) {
        root.right = leftLeftRotation(root.right);
        return rightRightRotation(root);
    }

    public void insert(T x) {
        root = insert(x, root);
    }

    /**
     * 将结点插入到AVL树中，并返回根节点.
     * 基本与二叉查找树差不多，再加入平衡动作即可。
     *
     * @param x    要插入的元素
     * @param node AVL树的根节点
     * @return
     */
    private AVLNode<T> insert(T x, AVLNode<T> node) {
        if (node == null) {
            return new AVLNode<T>(x);
        }

        int compareResult = x.compareTo(node.element);
        if (compareResult < 0) { // 比当前元素小，插入左子树
            node.left = insert(x, node.left);
        } else if (compareResult > 0) { // 比当前元素大，插入右子树
            node.right = insert(x, node.right);
        } else {
            // do nothing
        }
        return balance(node);
    }

    /**
     * 平衡当前节点
     *
     * @param node
     * @return
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        if (node == null) {
            return null;
        }

        if (height(node.left) - height(node.right) > 1) { // 左子树比右子树高度之差大于等于2，不平衡
            if (height(node.left.left) > height(node.left.right)) { // 左子树的左子树 比 左子树的右子树高，属于LL情况
                leftLeftRotation(node);
            } else { // 否则，属于 LR情况
                leftRightRotation(node);
            }
        } else if (height(node.right) - height(node.left) > 1) { // 右子树比左子树高度之差大于等于2，不平衡
            if (height(node.left.left) > height(node.left.right)) { // 右子树的左子树 比 右子树的右子树高，属于RL情况
                rightLeftRotation(node);
            } else { // 否则，属于 RR情况
                rightRightRotation(node);
            }
        }
        return node;
    }


    private AVLNode<T> remove(AVLNode<T> tree, AVLNode<T> z) {
        return null;
    }
}
