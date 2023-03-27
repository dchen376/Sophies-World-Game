package world;

import java.util.HashMap;

/**
 * this is the pet class.
 */
public class Pet {
 private String petName;
 private int petLocation;
 private HashMap<String, Integer> roomNameIndexMap;

 /**
  * constructor.
  */
 public Pet(String petName, int petLocation, HashMap<String, Integer> roomNameIndexMap) {
  this.petName = petName;
  this.petLocation = petLocation;
  this.roomNameIndexMap = roomNameIndexMap;
 }


 /*getters and setters (begin).*/
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

 /*getters and setters (end). */


 /*other major methods.*/

 /**
  * the pet is doing the depth-first-search move inside the mansion.
  * @return an integer value to represent the room index it moved to.
  */
 private int dfsMove(){
  return 0;
 }




} //end of Pet.java
