package model.room;

import model.mansion.Mansion;
import model.mansion.MansionBuilder;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this is the room class.
 */
public class Room {
  private MansionBuilder mansionBuilder;

  /**
   * Constructor.
   * 
   * @param mansionBuilder the main object of the game.
   */
  public Room(MansionBuilder mansionBuilder) {
    this.mansionBuilder = mansionBuilder;
  }


  /**
   * display the room's information.
   * 
   * @param roomName the name of the room
   * @return return a string representation
   */
  public String displayRoomInfo(String roomName) {
    /// 1. get the name: roomName.
    String roomNameStr = roomName;
    int roomIndex = this.mansionBuilder.getRoomNameIndexMap().get(roomName);
    int totalRooms = this.mansionBuilder.getRoomNameIndexMap().size();

    /// (2) get the items in this room index:
    StringBuilder itemsSb = new StringBuilder();
    for (String str : this.mansionBuilder.getItemsRoomMap().keySet()) {
      if (this.mansionBuilder.getItemsRoomMap().get(str) == roomIndex) {
        itemsSb.append(str);
        itemsSb.append(", ");
      }
    }
    String itemsStr = itemsSb.toString();
    itemsStr = itemsStr.substring(0, itemsStr.length() - 2);

    // 3. get the neighbors.
    ArrayList<String> neighbors = this.getNeighbors(roomName);
    String neighborsStr = this.helperArrayListToString(neighbors);
    String ans = String.format("Room name: %s. Items in this room: %s. The room's neighbors: %s.",
        roomNameStr, itemsStr, neighborsStr);
    return ans;
  }

  /*helper method below.*/

  /**
   * helper method to convert array list to String.
   * 
   * @param arrLst an array list
   * @return a string
   */
  public String helperArrayListToString(ArrayList<String> arrLst) {
    StringBuffer sb = new StringBuffer();
    for (String str : arrLst) {
      sb.append(str);
      sb.append(",");
    }
    String str = sb.toString();
    str = str.substring(0, str.length() - 1); // deleting the last ','
    return str;
  }

  /*getters below:*/

  /**
   * this method finds the other rooms that share a common wall with the given
   * room as neighbors. I, however, assume that by sharing a single coordinate
   * will be not considered as a "shared wall" scenario.
   *
   * @param roomName the name of the room as a string
   * @return all the neighbors of the current room as a String arrayList.
   */
  public ArrayList<String> getNeighbors(String roomName) {
    roomName = roomName.toLowerCase();
    int currRoomIndex = this.mansionBuilder.getRoomNameIndexMap().get(roomName);
    ArrayList<ArrayList<Integer>> coordinates =
        this.mansionBuilder.getListOfRoomCoordinates().get(currRoomIndex); // gets
    int x1 = coordinates.get(0).get(0);
    int y1 = coordinates.get(0).get(1);
    int x2 = coordinates.get(1).get(0);
    int y2 = coordinates.get(1).get(1);
    int width = x2 - x1;
    int height = y2 - y1;
    ArrayList<Integer> roomIndexes = new ArrayList<>();
    ArrayList<String> rooms = this.mansionBuilder.getAllRoomsNamesLst(); // gets all room names.
    for (int i = 0; i < rooms.size(); i++) {
      if (i == currRoomIndex) {
        continue; // if it's the current room that is checking, skip it.
      }

      ArrayList<ArrayList<Integer>> checkedCoords = this.mansionBuilder.getListOfRoomCoordinates().get(i);
//      System.out.println("Current room is: " + rooms.get(i));
//      System.out.println("current room coordinates: " + checkedCoords.toString());

      int a1 = checkedCoords.get(0).get(0);

      int b1 = checkedCoords.get(0).get(1);
      int a2 = checkedCoords.get(1).get(0);
      int b2 = checkedCoords.get(1).get(1);
      // checking should be done here in the loop below.
      int len;
      if (y1 == b2) { // check if the room is just above.
        len = x1 + width;
        if (len >= a2 && a2 > x1) {
//          System.out.println("here1 is: " + i);
          roomIndexes.add(i);
        } else if (a1 < len && a1 >= x1) {
//          System.out.println("here2 is: " + i);

          roomIndexes.add(i);
        } else if (a1 < x1 && a2 > x2) {
//          System.out.println("here3 is: " + i);

          roomIndexes.add(i);
        }
      } else if (y2 == b1) { // check if it's below it.
        len = x1 + width;
        if (a2 <= len && a2 > x1) {
          roomIndexes.add(i);
//          System.out.println("here4 is: " + i);

        } else if (a1 < len && a1 >= x1) {
          roomIndexes.add(i);
//          System.out.println("here5 is: " + i);

        } else if (a1 < x1 && a2 > x2) {
          roomIndexes.add(i);
//          System.out.println("here6 is: " + i);

        }
      } else if (x1 == a2) { // check if it's on the left of it.
        len = y1 + height;
        if (b2 <= len && b2 > y1) {
//          System.out.println("here7 is: " + i);

          roomIndexes.add(i);
        } else if (b1 >= y1 && b1 < len) {
//          System.out.println("here8 is: " + i);

          roomIndexes.add(i);
        } else if (b1 < y1 && b2 > y2) {
//          System.out.println("here9 is: " + i);

          roomIndexes.add(i);
        }
      } else if (x2 == a1) { // check if it's on the right.
        len = y1 + height;
        if (b2 <= len && b2 > y1) {
          roomIndexes.add(i);
//          System.out.println("here10 is: " + i);

        } else if (b1 >= y1 && b1 < len) {
//          System.out.println("here11 is: " + i);

          roomIndexes.add(i);
        } else if (b1 < y1 && b2 > y2) {
//          System.out.println("here12 is: " + i);
          roomIndexes.add(i);
        }
      }
    } // end of the loop to put all the neighboring rooms' indexes
    // into this Integer array-list: roomIndexes
    ArrayList<String> allRooms = rooms;
    ArrayList<String> neighbors = new ArrayList<String>();
    for (Integer num : roomIndexes) {
      neighbors.add(allRooms.get(num).toLowerCase()); // the name of that neighbor room.
    }
    return neighbors;
  }

  /**
   * hashmap for all the neighbors of the player's current room.
   *
   * @return hashmap //note: re-put this method elsewhere
   */
  public HashMap<String, ArrayList<String>> getAllNeighborsMap() {
    for (int i = 0; i < this.mansionBuilder.getRoomNameIndexMap().size(); i++) {
      this.mansionBuilder.getAllNeighborsMap().put(this.mansionBuilder.getAllRoomsNamesLst().get(i), this.getNeighbors(this.mansionBuilder
          .getAllRoomsNamesLst().get(i)));
    }
    return this.mansionBuilder.getAllNeighborsMap();
  }
}
