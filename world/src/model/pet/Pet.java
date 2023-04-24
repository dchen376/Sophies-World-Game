package model.pet;

import java.util.Random;

import model.mansion.MansionBuilder;

/**
 * this is the pet class.
 */
public class Pet {
  private MansionBuilder mansionBuilder;
  private String petName;
  private int petLocation;

  /**
   * constructor.
   */
  public Pet(MansionBuilder mansionBuilder, String petName, int petLocation) {
    this.mansionBuilder = mansionBuilder;
    this.petName = petName;
    this.petLocation = petLocation;
  }

  /**
   * the pet is doing the depth-first-search move inside the mansion. the BST is
   * built and balanced based on the room index.
   *
   * @return an integer value to represent the room index it moved to.
   */
  public int dfsMove() {

    // TODO: to implemnt a dfs move or just make a rand. move for pet.

    int totalRoom = this.mansionBuilder.getTotalRooms();
    Random random = new Random();
    int randomNumber = random.nextInt(totalRoom); // between 0 and totalRoom -1.
    System.out.println("this rand number is: " + randomNumber);
    this.setPetLocation(randomNumber);
    this.mansionBuilder.setPetLocation(randomNumber);
    return mansionBuilder.getPetLocation();

//
//
//    /*1. build the tree*/
//    NaryTreeNode root = this.helperPopulateTree();
//
//    // : finish the dfsMove() & show it explicitly in the test run.
//
//    /*2. get the dfs results in a list for later parsing*/
//    ArrayList<Integer> res = this.getDfsResult(root);
//
//    /*3. perform the actual move for the pet*/
//    for (int i = 0; i < this.mansionBuilder.getTotalRooms(); i++){
//      if (!this.mansionBuilder.getDfsCheckMap().get(i)){
//        this.mansionBuilder.getPet().setPetLocation(i);
//        this.mansionBuilder.getDfsCheckMap().put(i, true); //true means the room's been visited already.
//        return i; //return the current index the pet's at.
//      }
//    }
//    //if not returning from above, meaning all hash values are false!
//    for (int i = 0; i < this.mansionBuilder.getTotalRooms(); i++){
//      this.mansionBuilder.getDfsCheckMap().put(i, false);
//    }
//
//    this.mansionBuilder.getPet().setPetLocation(0);
//    return 0; //now return the original starting position of the pet.
//  }
//  //helper function to get the dfs result
//  private ArrayList<Integer> getDfsResult(NaryTreeNode root){
//    ArrayList<Integer> res = new ArrayList<>();
//    this.dfs(root, res);
//    return res;
//  }
//  //a helper function for dfsMove()
//  private void dfs(NaryTreeNode node, ArrayList<Integer> res){
//    if (node == null){
//      return;
//    }
//    res.add(node.getData());
//    for (NaryTreeNode child : node.getChildren()){
//      dfs(child, res);
//    }
//
//  }
//
//  /**
//   * a helper method to populate the NaryTree:
//   * @return the root of the tree.
//   */
//  private NaryTreeNode helperPopulateTree() {
//    // file in readfile()
//    ArrayList<String> allRooms = this.mansionBuilder.getAllRoomsNamesLst();
//    int size = allRooms.size();
//    int index = 0;
//    NaryTreeNode root = new NaryTreeNode(index);
//    NaryTreeNode dupRoot = root;
//    while (index < size) {
//      root = new NaryTreeNode(index); // starting with index 0.
//      ArrayList<String> allNeighbors = this.mansionBuilder.getAllNeighborsMap().get(allRooms.get(index++));
//      for (int i = 0; i < allNeighbors.size(); i++) {
//        String room = allNeighbors.get(i);
//        int roomIndex = this.mansionBuilder.getRoomNameIndexMap().get(room);
//        root.addChild(new NaryTreeNode(roomIndex));
//      }
//    } // end of while() loop.
//
//    return dupRoot;
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

  // newly added:
  public String getPetLocationName() {
    int index = this.getPetLocation();
    return this.mansionBuilder.getAllRoomsNamesLst().get(index);
  }

} // end of Pet.java
