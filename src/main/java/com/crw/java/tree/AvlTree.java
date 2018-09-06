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
}
