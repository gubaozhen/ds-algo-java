package com.github.andavid.ds.datastructure.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree {

  public void preorder(TreeNode root) {
    if (root == null) {
      return;
    }

    System.out.print(root.val + " ");
    preorder(root.left);
    preorder(root.right);
  }

  public void inorder(TreeNode root) {
    if (root == null) {
      return;
    }

    inorder(root.left);
    System.out.print(root.val + " ");
    inorder(root.right);
  }

  public void postorder(TreeNode root) {
    if (root == null) {
      return;
    }

    postorder(root.left);
    postorder(root.right);
    System.out.print(root.val + " ");
  }

  public List<Integer> preorderTraversal1(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    if (root == null) {
      return list;
    }

    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);

    while (!stack.empty()) {
      TreeNode p = stack.pop();
      list.add(p.val);
      if (p.right != null) {
        stack.push(p.right);
      }
      if (p.left != null) {
        stack.push(p.left);
      }
    }

    return list;
  }

  public List<Integer> preorderTraversal2(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode p = root;

    while (p != null || !stack.empty()) {
      while (p != null) {
        list.add(p.val);
        stack.push(p);
        p = p.left;
      }
      p = stack.pop();
      p = p.right;
    }

    return list;
  }

  public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode p = root;

    while (p != null || !stack.empty()) {
      while (p != null) {
        stack.push(p);
        p = p.left;
      }
      p = stack.pop();
      list.add(p.val);
      p = p.right;
    }

    return list;
  }

  public List<Integer> postorderTraversal1(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    if (root == null) {
      return list;
    }

    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);

    while (!stack.empty()) {
      TreeNode p = stack.pop();
      if (p.left != null) {
        stack.push(p.left);
      }
      if (p.right != null) {
        stack.push(p.right);
      }
      // 逆序添加节点值
      list.add(0, p.val);
    }

    return list;
  }

  public List<Integer> postorderTraversal2(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    Stack<TreeNode> stack = new Stack<>();
    TreeNode cur = root;
    TreeNode last = null; // 记录上一次访问的节点

    while (cur != null || !stack.empty()) {
      // 从根节点开始， 将其所有左子节点入栈
      while (cur != null) {
        stack.push(cur);
        cur = cur.left;
      }
      cur = stack.peek();
      if (cur.right == null || cur.right == last) {
        // 如果当前节点没有右孩子，或者当前节点的右孩子刚刚被访问过，
        // 这时应该访问当前节点
        list.add(cur.val);
        stack.pop();
        last = cur;
        cur = null;
      } else {
        // 否则，将当前指针移到其右孩子节点上
        cur = cur.right;
      }
    }
    return list;
  }

  public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) {
      return result;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      List<Integer> level = new ArrayList<>();
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        level.add(node.val);
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
      }
      result.add(level);
    }

    return result;
  }

  /**
   * 树的最大深度
   */
  public int maxDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
  }

  private boolean isBalanced = true;

  /**
   * 是否是高度平衡的二叉树
   */
  public boolean isBalanced(TreeNode root) {
    getDepth(root);
    return isBalanced;
  }

  public int getDepth(TreeNode root) {
    if (root == null) {
      return 0;
    }
    int left = getDepth(root.left);
    int right = getDepth(root.right);
    if (Math.abs(left - right) > 1) {
      isBalanced = false;
    }
    return left > right ? left + 1 : right + 1;
  }

  /**
   * 是否是镜像对称的二叉树
   */
  public boolean isSymmetric(TreeNode root) {
    if (root == null) {
      return true;
    }
    return isSym(root.left, root.right);
  }

  public boolean isSym(TreeNode r1, TreeNode r2) {
    if (r1 == null && r2 == null) {
      return true;
    }
    if (r1 == null || r2 == null) {
      return false;
    }
    return r1.val == r2.val && isSym(r1.left, r2.right) && isSym(r1.right, r2.left);
  }

  // Encodes a tree to a single string.
  public String serialize(TreeNode root) {
    if (root == null) {
      return "[]";
    }

    List<String> list = new ArrayList<>();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();

      if (node == null) {
        list.add("null");
        continue;
      } else {
        list.add("" + node.val);
      }

      if (node.left != null) {
        queue.offer(node.left);
      } else {
        queue.offer(null);
      }

      if (node.right != null) {
        queue.offer(node.right);
      } else {
        queue.offer(null);
      }
    }

    // 移除末尾的 null
    for (int i = list.size() - 1; i >= 0; i--) {
      if (list.get(i).equals("null")) {
        list.remove(i);
      } else {
        break;
      }
    }

    StringBuilder sb = new StringBuilder();
    sb.append('[');
    for (int i = 0; i < list.size(); i++) {
      sb.append(list.get(i));
      if (i == list.size() - 1) {
        sb.append("]");
      } else {
        sb.append(",");
      }
    }

    return sb.toString();
  }

  // Decodes your encoded data to tree.
  public TreeNode deserialize(String data) {
    if (data == null || data.equals("[]") || data.length() <= 2) {
      return null;
    }

    String[] strArray = data.substring(1, data.length() - 1).split(",");
    Queue<String> list = new LinkedList<>();
    list.addAll(Arrays.asList(strArray));

    Queue<TreeNode> queue = new LinkedList<>();
    TreeNode root = new TreeNode(Integer.valueOf(list.poll()));
    queue.offer(root);

    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();

      String leftVal = list.poll();
      if (leftVal == null || leftVal.equals("null")) {
        node.left = null;
      } else {
        TreeNode leftNode = new TreeNode(Integer.valueOf(leftVal));
        node.left = leftNode;
        queue.offer(leftNode);
      }

      String rightVal = list.poll();
      if (rightVal == null || rightVal.equals("null")) {
        node.right = null;
      } else {
        TreeNode rightNode = new TreeNode(Integer.valueOf(rightVal));
        node.right = rightNode;
        queue.offer(rightNode);
      }
    }

    return root;
  }

}