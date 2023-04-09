package model.target;

import model.mansion.Mansion;

/**
 * this is the target class.
 */
public class Target {
  private Mansion mansion;
  private String targetName;
  private int targetHealth;
  private int targetLocation;

  /**
   * constructor.
   * @param targetName target name
   * @param targetHealth target health
   * @param targetLocation target location
   */
  public Target(Mansion mansion, String targetName, int targetHealth, int targetLocation) {
    this.mansion = mansion;
    this.targetName = targetName;
    this.targetHealth = targetHealth;
    this.targetLocation = targetLocation;
  }




  /**
   * move the target character.
   * @return the room index moved to
   */
  public int moveTarget() {
    // room0's items' damgages?

    /// if target starts at room 0, it takes the damage from this room before moving
    /// to the next room:
    for (String str : this.mansion.getItemsRoomMap().keySet()) {
      if (this.mansion.getItemsRoomMap().get(str) == 0) {
        int damage = this.mansion.getItemsRoomMap().get(str);
        targetHealth -= damage;
      }
    } // end of the for-loop

    /// updating target location & move it to the next room:
    targetLocation += 1;

    for (String str : this.mansion.getItemsRoomMap().keySet()) {
      if (this.mansion.getItemsRoomMap().get(str) == targetLocation) {
        int damage = this.mansion.getItemsRoomMap().get(str);
        targetHealth -= damage;
      }
    }
    return targetLocation;
  }


  /**
   * the TARGET!! moves one index room forward in the world each time by calling
   * this method.
   *
   * @return Integer (the room's index the TARGET just moved to)
   */
  public int targetAutomove() { // this method probably wil be called from any methods that
    // return 1;

    // TARGET TARGET MOVE!!! EVERY TURN!!!! OF THE GAME!

    int totalRooms = this.mansion.getItemsRoomMap().size();
    int targetLocation = this.getTargetLocation();
    if (targetLocation + 1 != totalRooms) {
      targetLocation += 1;
      this.setTargetLocation(targetLocation);
    } else {
      targetLocation = 0;
      this.setTargetLocation(targetLocation);
    }

    return this.getTargetLocation();
  }

  /*below are all getters & setters.*/

  /**
   * getter.
   * @return get target's location
   */
  public int getTargetLocation() {
    return targetLocation;
  }

  public void setTargetLocation(int targetLocation) {
    this.targetLocation = targetLocation;
  }

  /**
   * getter.
   * @return target health
   */
  public int getTargetHealth() {
    return targetHealth;
  }

  /**
   * setter.
   * @param targetHealth target health
   */
  public void setTargetHealth(int targetHealth) {
    this.targetHealth = targetHealth;
  }

  /**
   * get the target's name.
   * @return the target's name
   */
  public String getTargetName() {
    return targetName;
  }

  /**
   * setter.
   * @param targetName set target's name
   */
  public void setTargetName(String targetName) {
    this.targetName = targetName;
  }

}
