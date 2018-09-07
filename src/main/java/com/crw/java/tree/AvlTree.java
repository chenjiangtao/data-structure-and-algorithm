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


    public boolean isEmpty() {
        return this.root == null;
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
    private AVLNode<T> findMin(AVLNode<T> node) {
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
    private AVLNode<T> findMax(AVLNode<T> node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * 删除节点，此处和 BinarySearchTree.java 中类似，只需要最好平衡一下节点即可
     *
     * @param t
     * @param node
     * @return
     */
    /*private AVLNode<T> remove(T t, AVLNode<T> node) {
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
        return balance(node);
    }*/

    /**
     * 删除的另一种写法，思路基本不变。三种情况删除。
     *
     * @param t    元素
     * @param node 根数下的某节点
     * @return
     */
    private AVLNode<T> remove(T t, AVLNode<T> node) {
        if (t == null || node == null) { // 找不到指定元素
            return null;
        }
        int compareValue = t.compareTo(node.element);
        if (compareValue < 0) { // 要删除的节点在左子树
            node.left = remove(t, node.left);
        } else if (compareValue > 0) { // 要删除的节点在右子树
            node.right = remove(t, node.right);
        } else { // node就是要删除的节点
            if ((node.left != null) && (node.right != null)) { // 当前节点有俩个孩子
                if (height(node.left) > height(node.right)) { // 左子树比右子树高，则1.找到左子树的最大节点；2.将该值赋值给当前节点node；3.删除该最大节点
                    AVLNode<T> max = findMax(node);
                    node.element = max.element;
                    node.left = remove(max.element, node.left);
                } else { // 右子树比左子树高，则1.找到右子树的最小节点；2.将该值赋值给当前节点node；3.删除该最小节点
                    AVLNode<T> min = findMin(node);
                    node.element = min.element;
                    node.right = remove(min.element, node.right);
                }
            } else { // 当前节点只有一个孩子
                node = (node.left != null) ? node.left : node.right;
            }
        }

        return balance(node);
    }

    private void remove(T t) {
        root = remove(t, root);
    }


    /*
     * 前序遍历"AVL树"
     */
    private void preOrder(AVLNode<T> node) {
        if (node != null) {
            System.out.print(node.element + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public void preOrder() {
        preOrder(root);
    }

    /*
     * 中序遍历"AVL树"
     */
    private void inOrder(AVLNode<T> node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.element + " ");
            inOrder(node.right);
        }
    }

    public void inOrder() {
        inOrder(root);
    }

    /*
     * 后序遍历"AVL树"
     */
    private void postOrder(AVLNode<T> node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.element + " ");
        }
    }

    public void postOrder() {
        postOrder(root);
    }

    /*
     * 打印"二叉查找树"
     *
     * key        -- 节点的键值
     * direction  --  0，表示该节点是根节点;
     *               -1，表示该节点是它的父结点的左孩子;
     *                1，表示该节点是它的父结点的右孩子。
     */
    private void print(AVLNode<T> node, T x, int direction) {
        if (node != null) {
            if (direction == 0)    // tree是根节点
                System.out.printf("%2d is root\n", node.element, x);
            else                // tree是分支节点
                System.out.printf("%2d is %2d's %6s child\n", node.element, x, direction == 1 ? "right" : "left");

            print(node.left, node.element, -1);
            print(node.right, node.element, 1);
        }
    }

    public void print() {
        if (root != null)
            print(root, root.element, 0);
    }

    /*
     * 销毁AVL树
     */
    private void destroy(AVLNode<T> node) {
        if (node == null)
            return;

        if (node.left != null)
            destroy(node.left);
        if (node.right != null)
            destroy(node.right);

        node = null;
    }

    public void destroy() {
        destroy(root);
    }

    public static void main(String[] args) {
        int arr[] = {3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9};
        int i;
        AvlTree<Integer> tree = new AvlTree<Integer>();

        System.out.printf("== 依次添加: ");
        for (i = 0; i < arr.length; i++) {
            System.out.printf("%d ", arr[i]);
            tree.insert(arr[i]);
        }

        System.out.printf("\n== 前序遍历: ");
        tree.preOrder();

        System.out.printf("\n== 中序遍历: ");
        tree.inOrder();

        System.out.printf("\n== 后序遍历: ");
        tree.postOrder();
        System.out.printf("\n");

        System.out.printf("== 高度: %d\n", tree.height());
        System.out.printf("== 最小值: %d\n", tree.findMin());
        System.out.printf("== 最大值: %d\n", tree.findMax());
        System.out.printf("== 树的详细信息: \n");
        tree.print();

        i = 8;
        System.out.printf("\n== 删除根节点: %d", i);
        tree.remove(i);

        System.out.printf("\n== 高度: %d", tree.height());
        System.out.printf("\n== 中序遍历: ");
        tree.inOrder();
        System.out.printf("\n== 树的详细信息: \n");
        tree.print();

        // 销毁二叉树
        tree.destroy();
    }
}
