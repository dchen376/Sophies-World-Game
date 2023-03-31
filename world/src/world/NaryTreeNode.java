package world;

import java.util.ArrayList;
import java.util.List;

/**
 * class for building a N-nary Tree stucture.
 */
public class NaryTreeNode {
 private int data;
 private List<NaryTreeNode> children;

 public NaryTreeNode(int data) {
  this.data = data;
  this.children = new ArrayList<>();
 }

 public void addChild(NaryTreeNode child) {
  this.children.add(child);
 }

 public int getData() {
  return data;
 }

 public List<NaryTreeNode> getChildren() {
  return children;
 }
}