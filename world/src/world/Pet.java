package world;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;

/**
 * this is the pet class.
 */
public class Pet {
  private Mansion mansion;
  private String petName;
  private int petLocation;

  /**
   * constructor.
   */
  public Pet(Mansion mansion, String petName, int petLocation) {
    this.mansion = mansion;
    this.petName = petName;
    this.petLocation = petLocation;
  }

  /* getters and setters (begin). */
  public String getPetName() {
    return petName;
  }

  public void setPetName(String petName) {
    this.petName = petName;
  }

  public int getPetLocation() {
    return petLocation;
  }

  public void setPetLocation(int petLocation) {
    this.petLocation = petLocation;
  }

  /* getters and setters (end). */

  /* other major methods. */


  /**
   * a helper method to populate the NaryTree:
   * @return the root of the tree.
   */
  private NaryTreeNode helperPopulateTree() {
                                             // file in readfile()
    ArrayList<String> allRooms = this.mansion.getAllRoomsNamesLst();
    int size = allRooms.size();
    int index = 0;
    NaryTreeNode root = new NaryTreeNode(index);
    NaryTreeNode dupRoot = root;
    while (index < size) {
      root = new NaryTreeNode(index); // starting with index 0.
      ArrayList<String> allNeighbors = this.mansion.getAllNeighborsMap().get(allRooms.get(index++));
      for (int i = 0; i < allNeighbors.size(); i++) {
        String room = allNeighbors.get(i);
        int roomIndex = this.mansion.getRoomNameIndexMap().get(room);
        root.addChild(new NaryTreeNode(roomIndex));
      }
    } // end of while() loop.

    return dupRoot;
  }

  /**
   * the pet is doing the depth-first-search move inside the mansion. the BST is
   * built and balanced based on the room index.
   * 
   * @return an integer value to represent the room index it moved to.
   */
  public int dfsMove() {

    /*1. build the tree*/
    NaryTreeNode root = this.helperPopulateTree();

    // TODO: finish the dfsMove() & show it explicitly in the test run.

    /*2. get the dfs results in a list for later parsing*/
    ArrayList<Integer> res = this.getDfsResult(root);

    /*3. perform the actual move for the pet*/
    for (int i = 0; i < this.mansion.getTotalRooms(); i++){
      if (!this.mansion.getDfsCheckMap().get(i)){
        this.mansion.getPet().setPetLocation(i);
        this.mansion.getDfsCheckMap().put(i, true); //true means the room's been visited already.
        return i; //return the current index the pet's at.
      }
    }
    //if not returning from above, meaning all hash values are false!
    for (int i = 0; i < this.mansion.getTotalRooms(); i++){
      this.mansion.getDfsCheckMap().put(i, false);
    }

    this.mansion.getPet().setPetLocation(0);
    return 0; //now return the original starting position of the pet.
  }


  //helper function to get the dfs result
  private ArrayList<Integer> getDfsResult(NaryTreeNode root){
    ArrayList<Integer> res = new ArrayList<>();
    this.dfs(root, res);
    return res;
  }

  //a helper function for dfsMove()
  private void dfs(NaryTreeNode node, ArrayList<Integer> res){
    if (node == null){
      return;
    }
    res.add(node.getData());
    for (NaryTreeNode child : node.getChildren()){
      dfs(child, res);
    }

  }

} // end of Pet.java
