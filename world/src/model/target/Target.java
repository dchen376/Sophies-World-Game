package model.target;

import model.mansion.Mansion;

import java.util.HashMap;

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
    for (String str : itemRoomMap.keySet()) {
      if (itemRoomMap.get(str) == 0) {
        int damage = itemDamageMap.get(str);
        targetHealth -= damage;
      }
    } // end of the for-loop

    /// updating target location & move it to the next room:
    targetLocation += 1;

    for (String str : itemRoomMap.keySet()) {
      if (itemRoomMap.get(str) == targetLocation) {
        int damage = itemDamageMap.get(str);
        targetHealth -= damage;
      }
    }

    return targetLocation;
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
