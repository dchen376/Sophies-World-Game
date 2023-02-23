package world;

import java.util.ArrayList;
import java.util.HashMap;

public interface MansionBuilder {

  /**
   * read the provided .txt file
   * @param readable
   */
  void readFile(Readable readable);

  /**
   *
   */
  void drawWorld();

  /**
   * this method gets all the names of the rooms in an array list.
   *
   * @param roomNames
   * @return all the names of the rooms
   */
  HashMap<String, Integer> getRoomNameIndexMap();

  ArrayList<ArrayList<ArrayList<Integer>>> getListOfRoomCoordinates();

  HashMap<String, ArrayList<String>> getAllNeighborsMap();

  int getTargetHealth();

  int getTargetLocation();



  /**
   * this method gets the name of the target.
   *
   * @param targetName
   * @return the target's name
   */
  String getTargetName();


  /**
   * this method gets all the items names from each room, and at each index represents the related room index.
   *
   * @param itemNames
   * @return all items names
   */
  HashMap<String, Integer> getItemsRoomMap();

  /**
   * this method will get the damage of each item in the 'world'
   *
   * @return an array-list of array-list representing the damages of the items.
   */
  HashMap<String, Integer> getItemsDamageMap();


}
