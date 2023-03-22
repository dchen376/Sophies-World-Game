package world;

import java.util.HashMap;

/**
 * this is the target class.
 */
public class Target {
  private String targetName;
  private int targetHealth;
  private int targetLocation;
  private HashMap<String, Integer> itemRoomMap;
  private HashMap<String, Integer> itemDamageMap;

  /**
   * constructor.
   * @param targetName target name
   * @param targetHealth target health
   * @param targetLocation target location
   * @param itemRoomMap hashmap of items and rooms
   * @param itemDamageMap hashmap for items and damages
   */
  public Target(String targetName, int targetHealth, int targetLocation,
      HashMap<String, Integer> itemRoomMap, HashMap<String, Integer> itemDamageMap) {
    this.targetName = targetName;
    this.targetHealth = targetHealth;
    this.targetLocation = targetLocation;
    this.itemRoomMap = itemRoomMap;
    this.itemDamageMap = itemDamageMap;
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

  /**
   * getter.
   * @return get target's location
   */
  public int getTargetLocation() {
    return targetLocation;
  }

}
