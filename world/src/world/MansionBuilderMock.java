package world;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * MansionBuilder Interface.
 */
public interface MansionBuilderMock {



 /**
  * read the provided .txt file.
  *
  * @param readable to read the file
  */
 void readFile(Readable readable);

 /**
  * draw the world.
  */
 void drawWorld();

 /**
  * this method gets all the names of the rooms in an array list.
  *
  * @return all the names of the rooms
  */
 HashMap<String, Integer> getRoomNameIndexMap();

 /**
  * getter.
  * @return the arraylist of all rooms' coordinates.
  */
 ArrayList<ArrayList<ArrayList<Integer>>> getListOfRoomCoordinates();

 /**
  * getter.
  * @return hashmap of all neighbors
  */
 HashMap<String, ArrayList<String>> getAllNeighborsMap();

 /**
  * getter.
  * @return target's health
  */
 int getTargetHealth();

 /**
  * getter.
  * @return target's location
  */
 int getTargetLocation();

 /**
  * getter.
  * @return target's name
  */
 String getTargetName();

 /**
  * this method gets all the items names from each room, and at each index
  * represents the related room index.
  *
  * @return all items names
  */
 HashMap<String, Integer> getItemsRoomMap();

 /**
  * this method will get the damage of each item in the 'world'.
  *
  * @return an array-list of array-list representing the damages of the items.
  */
 HashMap<String, Integer> getItemsDamageMap();

 /**
  * getter.
  * @return all the players
  */
 ArrayList<PlayerMock> getAllPlayers();

 /**
  * getter.
  * @return total items allowed for each player as hashmap
  */
 HashMap<String, Integer> getTotalItemsAllowedMap();

 /**
  * getter.
  * @return item object
  */
 ItemMock getItem();

 /**
  * getter.
  * @return room object
  */
 RoomMock getRoom();

 /**
  * getter.
  * @return target object.
  */
 TargetMock getTarget();

 /**
  * getter.
  * @return all the names of the rooms as a list
  */
 ArrayList<String> getAllRoomsNamesLst();

 /**
  * getter.
  * @return hashmap for rooms and players(and target)
  */
 HashMap<String, String> getPlayersNameRoomMap();

 /**
  * getter.
  * @return hashmap of all items for each player
  */
 HashMap<String, ArrayList<String>> getPlayersItemsMap();

 /**
  * getter.
  * @return hashmap for players' turns.
  */
 HashMap<String, Integer> getTurnsMap();
}
