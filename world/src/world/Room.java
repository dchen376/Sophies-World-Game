package world;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {
 private HashMap<String, Integer> itemsRoomMap;
 private HashMap<String, Integer> roomNamesMap; //<room name, room Index>
 private ArrayList<ArrayList<ArrayList<Integer>>> listOfRoomCoordinates; //room Coords store in arraylist.
 private ArrayList<String> roomNames;
 private HashMap<String, ArrayList<String>> allNeighborsMap;



//
//  private String roomName;
//  private int[] roomCoordinate;

 //constructor (good)
public Room(HashMap<String, Integer> itemsRoomMap, HashMap<String, Integer> roomNamesMap,
    ArrayList<ArrayList<ArrayList<Integer>>> listOfRoomCoordinates,
    ArrayList<String> roomNames, HashMap<String, ArrayList<String>> allNeighborsMap){

 this.itemsRoomMap = itemsRoomMap;
 this.roomNamesMap = roomNamesMap;
 this.listOfRoomCoordinates = listOfRoomCoordinates;
 this.roomNames = roomNames;
 this.allNeighborsMap = allNeighborsMap;
}

 /**
  * get the room index of the room.
  * @param roomName
  * @return
  */
   public int getRoomIndex(String roomName) {
     return roomNamesMap.get(roomName);
   }

 /**
  * this method finds the other rooms that share a common wall with the given room as neighbors.
  * I, however, assume that by sharing a single coordinate will be not considered as a "shared wall" senario.
  *
  * @param roomName
  * @return all the neighbors of the current room as a String arrayList.
  */
 public ArrayList<String> getNeighbors(String roomName) {
  int roomIndex = roomNamesMap.get(roomName);
  ArrayList<ArrayList<Integer>> coordinates = listOfRoomCoordinates
      .get(roomIndex);//gets current room two coordinates.
  int x1, y1, x2, y2;
  x1 = coordinates.get(0).get(0);
  y1 = coordinates.get(0).get(1);
  x2 = coordinates.get(1).get(0);
  y2 = coordinates.get(1).get(1);
  int width = x2 - x1;
  int height = y2 - y1;

  int a1, b1, a2, b2;
  ArrayList<Integer> roomIndexes = new ArrayList<>();
  ArrayList<String> rooms = roomNames; //gets all room names.
  for (int i = 0; i < rooms.size(); i++) {
   if (i == roomIndex) {
    continue; //if it's the current room that is checking, skip it.
   }

   ArrayList<ArrayList<Integer>> checkedCoords = listOfRoomCoordinates.get(i);
   a1 = checkedCoords.get(0).get(0);
   b1 = checkedCoords.get(0).get(1);
   a2 = checkedCoords.get(1).get(0);
   b2 = checkedCoords.get(1).get(1);
   //checking should be done here in the loop below.
   int len;
   if (y1 == b2) { //check if the room is just above.
    len = x1 + width;
    if (len >= a2 && a2 > x1) {
     roomIndexes.add(i);
    } else if (a1 < len && a1 >= x1) {
     roomIndexes.add(i);
    } else if (a1 < x1 && a2 > x2) {
     roomIndexes.add(i);
    }
   } else if (b1 == y2) { //check if it's below it.
    len = x1 + width;
    if (a2 <= len && a2 > x1) {
     roomIndexes.add(i);
    } else if (a1 < len && a1 >= x1) {
     roomIndexes.add(i);
    } else if (a1 < x1 && a2 > x2) {
     roomIndexes.add(i);
    }
   } else if (x1 == a2) { //check if it's on the left of it.
    len = y1 + height;
    if (b2 <= len && b2 > y1) {
     roomIndexes.add(i);
    } else if (b1 >= y1 && b1 < len) {
     roomIndexes.add(i);
    } else if (b1 < y1 && b2 > y2) {
     roomIndexes.add(i);
    }
   } else if (x2 == a1) { //check if it's on the right.
    len = y1 + height;
    if (b2 <= len && b2 > y1) {
     roomIndexes.add(i);
    } else if (b1 >= y1 && b1 < len) {
     roomIndexes.add(i);
    } else if (b1 < y1 && b2 > y2) {
     roomIndexes.add(i);
    }
   }
  } //end of the loop to put all the neighboring rooms' indexes
  //into this Integer array-list: roomIndexes
  ArrayList<String> allRooms = roomNames;

  ArrayList<String> neighbors = new ArrayList<String>();
  for (Integer num : roomIndexes) {
   neighbors.add(allRooms.get(num)); //the name of that neighbor room.
  }
  return neighbors;
 }

 public HashMap<String, ArrayList<String>> getAllNeighborsMap(){
  for (int i = 0; i < roomNamesMap.size(); i++){
   this.allNeighborsMap.put(roomNames.get(i), this.getNeighbors(roomNames.get(i)));
  }
  return this.allNeighborsMap;
 }



 /**
  * display the room's information
  * @param roomName
  * @return
  */
 public String displayRoomInfo(String roomName) {
  /*
   * display the rooms info in a string format in an ArrayList:
   * the name of the room, the items here, and its neighbors by calling getNeighbors().
   * e.g.{(roomName), (item1, item2, ...), (neighbor1, neighbor2, ...)}
   */

  ///1. get the name: roomName.
  String roomNameStr = roomName;
  //2. get the items:
  ///(1) get the room index:
  int roomIndex = roomNamesMap.get(roomName);
  int totalRooms = roomNamesMap.size();

  ///(2) get the items in this room index:
  StringBuilder itemsSB = new StringBuilder();
  for (String str : this.itemsRoomMap.keySet()) {
   if (this.itemsRoomMap.get(str) == roomIndex) {
    itemsSB.append(str);
    itemsSB.append(", ");
   }
  }
  String itemsStr = itemsSB.toString();
  itemsStr = itemsStr.substring(0, itemsStr.length() - 2);

  //3. get the neighbors.
  ArrayList<String> neighbors = this.getNeighbors(roomName);


  String neighborsStr = this.helperArrayListToString(neighbors);

  String ans = String
      .format("Room name: %s. Items in this room: %s. The room's neighbors: %s.", roomNameStr,
          itemsStr, neighborsStr);

  return ans;
 }

 //helper
 public String helperArrayListToString(ArrayList<String> arrLst) {
  StringBuffer sb = new StringBuffer();
  for (String str : arrLst) {
   sb.append(str);
   sb.append(",");
  }
  String str = sb.toString();
  str = str.substring(0, str.length() - 2); //deleting the last ','
  return str;
 }

}
