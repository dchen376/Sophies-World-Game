package world;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * this is the room class.
 */
public class RoomMock {
 private MansionMockModel mansion;

 /**
  * Constructor.
  *
  * @param mansion the main object of the game.
  */
 public RoomMock(MansionMockModel mansion) {
  this.mansion = mansion;
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
  int roomIndex = this.mansion.getRoomNameIndexMap().get(roomName);
  int totalRooms = this.mansion.getRoomNameIndexMap().size();

  /// (2) get the items in this room index:
  StringBuilder itemsSb = new StringBuilder();
  for (String str : this.mansion.getItemsRoomMap().keySet()) {
   if (this.mansion.getItemsRoomMap().get(str) == roomIndex) {
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
  int roomIndex = this.mansion.getRoomNameIndexMap().get(roomName);
  ArrayList<ArrayList<Integer>> coordinates =
      this.mansion.getListOfRoomCoordinates().get(roomIndex); // gets
  int x1 = coordinates.get(0).get(0);
  int y1 = coordinates.get(0).get(1);
  int x2 = coordinates.get(1).get(0);
  int y2 = coordinates.get(1).get(1);
  int width = x2 - x1;
  int height = y2 - y1;
  ArrayList<Integer> roomIndexes = new ArrayList<>();
  ArrayList<String> rooms = this.mansion.getAllRoomsNamesLst(); // gets all room names.
  for (int i = 0; i < rooms.size(); i++) {
   if (i == roomIndex) {
    continue; // if it's the current room that is checking, skip it.
   }

   ArrayList<ArrayList<Integer>> checkedCoords = this.mansion.getListOfRoomCoordinates().get(i);
   int a1 = checkedCoords.get(0).get(0);
   int b1 = checkedCoords.get(0).get(1);
   int a2 = checkedCoords.get(1).get(0);
   int b2 = checkedCoords.get(1).get(1);
   // checking should be done here in the loop below.
   int len;
   if (y1 == b2) { // check if the room is just above.
    len = x1 + width;
    if (len >= a2 && a2 > x1) {
     roomIndexes.add(i);
    } else if (a1 < len && a1 >= x1) {
     roomIndexes.add(i);
    } else if (a1 < x1 && a2 > x2) {
     roomIndexes.add(i);
    }
   } else if (b1 == y2) { // check if it's below it.
    len = x1 + width;
    if (a2 <= len && a2 > x1) {
     roomIndexes.add(i);
    } else if (a1 < len && a1 >= x1) {
     roomIndexes.add(i);
    } else if (a1 < x1 && a2 > x2) {
     roomIndexes.add(i);
    }
   } else if (x1 == a2) { // check if it's on the left of it.
    len = y1 + height;
    if (b2 <= len && b2 > y1) {
     roomIndexes.add(i);
    } else if (b1 >= y1 && b1 < len) {
     roomIndexes.add(i);
    } else if (b1 < y1 && b2 > y2) {
     roomIndexes.add(i);
    }
   } else if (x2 == a1) { // check if it's on the right.
    len = y1 + height;
    if (b2 <= len && b2 > y1) {
     roomIndexes.add(i);
    } else if (b1 >= y1 && b1 < len) {
     roomIndexes.add(i);
    } else if (b1 < y1 && b2 > y2) {
     roomIndexes.add(i);
    }
   }
  } // end of the loop to put all the neighboring rooms' indexes
  // into this Integer array-list: roomIndexes
  ArrayList<String> allRooms = rooms;
  ArrayList<String> neighbors = new ArrayList<String>();
  for (Integer num : roomIndexes) {
   neighbors.add(allRooms.get(num)); // the name of that neighbor room.
  }
  return neighbors;
 }

 /**
  * hashmap for all the neighbors of the player's current room.
  *
  * @return hashmap
  */
 public HashMap<String, ArrayList<String>> getAllNeighborsMap() {
  for (int i = 0; i < this.mansion.getRoomNameIndexMap().size(); i++) {
   this.mansion.getAllNeighborsMap().put(this.mansion.getAllRoomsNamesLst().get(i), this.getNeighbors(this.mansion.getAllRoomsNamesLst().get(i)));
  }
  return this.mansion.getAllNeighborsMap();
 }
}
