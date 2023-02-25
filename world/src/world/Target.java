package world;

import java.util.HashMap;

public class Target {
 private String targetName;
 private int targetHealth;
 private int targetLocation;
 private HashMap<String, Integer> itemRoomMap;
 private HashMap<String, Integer> itemDamageMap;

 //constructor (good)
 public Target(String targetName, int targetHealth, int targetLocation,
     HashMap<String, Integer> itemRoomMap, HashMap<String, Integer> itemDamageMap) {
  this.targetName = targetName;
  this.targetHealth = targetHealth;
  this.targetLocation = targetLocation;
  this.itemRoomMap = itemRoomMap;
  this.itemDamageMap = itemDamageMap;
 }

 public int getTargetHealth() {
  return targetHealth;
 }

 /**
  * this method moves the target one index
  * at a time to a different room in the world.
  * <p>
  * Notes-to-myself:
  * More implementations would probably be needed
  * such as throw exceptions when the index if out of
  * the range, but I'll await for more information
  * before fully implementing this method.
  *
  * @return the current room index.
  */
 public int moveTarget() {
  //room0's items' damgages?

  ///if target starts at room 0, it takes the damage from this room before moving to the next room:
  for (String str : itemRoomMap.keySet()) {
   if (itemRoomMap.get(str) == 0) {
    int damage = itemDamageMap.get(str);
    targetHealth -= damage;
   }
  } //end of the for-loop

  ///updating target location & move it to the next room:
  targetLocation += 1;

  for (String str : itemRoomMap.keySet()) {
   if (itemRoomMap.get(str) == targetLocation) {
    int damage = itemDamageMap.get(str);
    targetHealth -= damage;
   }
  }

  return targetLocation;
 }

 //setters:

 public void setTargetName(String targetName) {
  this.targetName = targetName;
 }

 public void setTargetHealth(int targetHealth) {
  this.targetHealth = targetHealth;
 }

 public String getTargetName() {
  return targetName;
 }

 public int getTargetLocation() {
  return targetLocation;
 }

}
