package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Player {
 /*for fixed value map (no need for update)*/ /*porb. need to be parsed in from mansion after calling playGame() from controller*/
 private HashMap<String, Integer> roomNameIndexMap; //<room name, room Index>
 private HashMap<String, Integer> itemsDamageMap; // <item, damage>
 //private fields: (x6)
 private int targetHealth;
 private int targetLocation;
 private boolean playerTurn;
 private String computerOrHuman;
 private String playerName;
 private String playerRoom; //the room the player's currently at

 private int playerTotalAllowedItem;
 private HashMap<String, Integer> totalItemsAllowedMap; //<room name, the total number alloed

 // private ArrayList<String> totalItemsLst; //{all items in mansion put in this list.
 private ArrayList<String> playerItemsLst; //each this list represent a player's all items.
 private HashMap<String, ArrayList<String>> playersItemsMap; //*just 'put' new items into this arrlst.

 private HashMap<String, String> playersTargetNameRoomMap; // update each time the players/target move
 private HashMap<String, Integer> itemsRoomMap; //*remove the items in this hashmap once the player pick it.
 private HashMap<String, Integer> turnsMap; //defaults are true -> 1. //TODO turns map

 //Constructor

 public Player(HashMap<String, Integer> roomNameIndexMap,
     HashMap<String, Integer> totalItemsAllowedMap, HashMap<String, Integer> itemsDamageMap,
     int targetHealth, int targetLocation, boolean playerTurn, String computerOrHuman,
     String playerName, String playerRoom, int playerTotalAllowedItem,
     ArrayList<String> playerItemsLst, HashMap<String, String> playersTargetNameRoomMap,
     HashMap<String, ArrayList<String>> playersItemsMap, HashMap<String, Integer> itemsRoomMap,
     HashMap<String, Integer> turnsMap) {
  //inputs from mansion:
  this.roomNameIndexMap = roomNameIndexMap;
  this.totalItemsAllowedMap = totalItemsAllowedMap;
  this.itemsDamageMap = itemsDamageMap;
  this.targetHealth = targetHealth;
  this.targetLocation = targetLocation;
  this.playerTurn = playerTurn; //default is false.
  //inputs from controller:
  this.computerOrHuman = computerOrHuman;
  this.playerName = playerName;
  this.playerRoom = playerRoom;
  this.playerTotalAllowedItem = playerTotalAllowedItem;
  //below inputs from mansion:
  this.playerItemsLst = playerItemsLst;
  this.playersTargetNameRoomMap = playersTargetNameRoomMap;
  this.playersItemsMap = playersItemsMap;
  this.itemsRoomMap = itemsRoomMap;
  this.turnsMap = turnsMap;

  //update maps:
  this.totalItemsAllowedMap.put(this.playerName, this.playerTotalAllowedItem);
  this.playersTargetNameRoomMap.put(this.playerName, this.playerRoom);
  this.turnsMap.put(this.playerName, 1); //default is 1 for true
 }

 public HashMap<String, Integer> getTotalItemsAllowedMap() {
  return totalItemsAllowedMap;
 }

 public HashMap<String, String> getPlayersTargetNameRoomMap() {
  return playersTargetNameRoomMap;
 }

 public HashMap<String, ArrayList<String>> getPlayersItemsMap() {
  return playersItemsMap;
 }

 public HashMap<String, Integer> getItemsRoomMap() {
  return itemsRoomMap;
 }

 /**
  * check if the turnsMap returns 1 (true) or 0(false) for the current player
  *
  * @return boolean
  */
 public boolean checkTurnsMap() {
  if (this.turnsMap.get(this.playerName) == 1) {
   return true;
  } else if (this.turnsMap.get(this.playerName) == 0) {
   return false;
  }
  return false;
 }

 /**
  * getters & setters
  */

 public void setPlayerTotalAllowedItem(int playerTotalAllowedItem) {
  this.playerTotalAllowedItem = playerTotalAllowedItem;
 }

 public void setTotalItemsAllowedMap(HashMap<String, Integer> totalItemsAllowedMap) {
  this.totalItemsAllowedMap = totalItemsAllowedMap;
 }

 public ArrayList<String> getPlayerItemsLst() {
  return playerItemsLst;
 }

 public void setPlayerItemsLst(ArrayList<String> playerItemsLst) {
  this.playerItemsLst = playerItemsLst;
 }

 //------------------------------------------------------------ getters (x10) ---------------------------------------------------//
 public int getPlayerTotalAllowedItem() {
  return playerTotalAllowedItem;
 }

 public boolean getPlayerTurn() {
  return this.playerTurn;
 }

 public String getComputerOrHuman() {
  return computerOrHuman;
 }

 public void setComputerOrHuman(String computerOrHuman) {
  this.computerOrHuman = computerOrHuman;
 }

 public String getPlayerRoom() {
  return playerRoom;
 }

 public String getPlayerName() {
  return playerName;
 }

 public boolean isPlayerTurn() {
  return playerTurn;
 }

 public void setPlayerTurn(boolean playerTurn) {
  this.playerTurn = playerTurn;
 }

 public void setPlayerName(String playerName) {
  this.playerName = playerName;
 }

 public void setPlayerRoom(String playerRoom) {
  this.playerRoom = playerRoom;
 }

 public void setTurnsMap(HashMap<String, Integer> turnsMap) {
  this.turnsMap = turnsMap;
 }

 public void setTargetHealth(int targetHealth) {
  this.targetHealth = targetHealth;
 }

 public void setTargetLocation(int targetLocation) {
  this.targetLocation = targetLocation;
 }

 public HashMap<String, Integer> getRoomNameIndexMap() {
  return roomNameIndexMap;
 }

 public void setRoomNameIndexMap(HashMap<String, Integer> roomNameIndexMap) {
  this.roomNameIndexMap = roomNameIndexMap;
 }

 public void setItemsRoomMap(HashMap<String, Integer> itemsRoomMap) {
  this.itemsRoomMap = itemsRoomMap;
 }

 public HashMap<String, Integer> getTurnsMap() {
  return turnsMap;
 }

 public void setPlayersTargetNameRoomMap(HashMap<String, String> playersTargetNameRoomMap) {
  this.playersTargetNameRoomMap = playersTargetNameRoomMap;
 }

 public void setPlayersItemsMap(HashMap<String, ArrayList<String>> playersItemsMap) {
  this.playersItemsMap = playersItemsMap;
 }

 public HashMap<String, Integer> getItemsDamageMap() {
  return itemsDamageMap;
 }

 public void setItemsDamageMap(HashMap<String, Integer> itemsDamageMap) {
  this.itemsDamageMap = itemsDamageMap;
 }

 // 0getters:

 public int getTargetLocation() {
  return targetLocation;
 }

 public int getTargetHealth() {
  return targetHealth;
 }

 // public ArrayList<String> getPlayerItemsLst() {
 //  return playerItemsLst;
 // }

 /**
  * Main methods:
  */

 //--------s3 - movePlayer(done.) ------------------------------------//

 /**
  * move a player to his neighboring room (if there is one & player can still carry more items):
  * what happend when a player moved:
  * 1. pick items in current room if any.
  * 2.CHANGE the HASHMAP for items info.
  * 3. update player's current damage.
  * 4.update what he carrying now.
  * 5.check if the TARGET in this room:
  * -if IN:
  * ->fight the target by inflicing damage to target
  * ->update target's health.
  * - *this COST A TURN!
  *
  * @return Integer (the moved room's index)
  */
 public int movePlayer(HashMap<String, ArrayList<String>> allNeighborsMap) {
  //  System.out.println("running movePlayer()");
  /*costing a TURN*/
  this.turnsMap.put(this.playerName, 0);

  ArrayList<String> playerNeighbors = allNeighborsMap.get(this.playerRoom);
  //  System.out.println("the all neighborsmap is: " + allNeighborsMap);
  int size = playerNeighbors.size();
  int index = 0;
  if (size != 1) {
   index = this.helperRandNum(size - 1);
  }
  //player move to a random index:
  String neighborRoom = playerNeighbors
      .get(index); //got the name of the room the player is supposed to move to.
  //update hashmaps:
  int neighborIndex = this.roomNameIndexMap.get(neighborRoom);
  this.playerRoom = this.helperIndexGetRoomName(neighborIndex); //to update player's current room
  //  this.pickUp(); //player performing the pickup() action(updaing items hashmap happening inside this function).
  this.updatePlayerRoomInfo(playerRoom);

  //return the moved to room index?
  //  System.out.println("movePlayer() execute autoMoveTarget()");
  this.autoMoveTarget();
  return neighborIndex;
 }

 //--------s4 - pickUp(done.)------------------------------------//

 /**
  * a player pick up an item in his room
  * condition1: if there are items in the rom.
  * condition2: if the player hasn't reaching its picking limit.
  * - *this COST A TURN!
  *
  * @return Item (the player just picked)
  */
 public String pickUp() {
  //  System.out.println("!!running pickUp()");
  /*costing a TURN*/

  this.turnsMap.put(this.playerName, 0);
  ///which room player at?
  //  System.out.println("the roomnameindexmap is " + this.roomNameIndexMap);
  int roomIndex = this.roomNameIndexMap.get(playerRoom);
  ///what items in this room?
  String roomName = this.helperIndexGetRoomName(roomIndex);
  //TODO: fix the null roomName
  ArrayList<String> allItems = this.helperRoomGetItems(roomName);
  ///can he pick it up?
  if (!allItems.isEmpty() && (totalItemsAllowedMap.get(this.playerName) - playerItemsLst
      .size()) > 0) {
   ///- if so, update this room items info: player pick, mansion deduct this item.
   playerItemsLst.add(allItems.get(0));//(get the first item in the list) player's added the item.
   this.itemsRoomMap.remove(allItems.get(0)); //room's removed the item.
   this.playersItemsMap.put(this.playerName, playerItemsLst); //add it to hashmap: playerItemsMap
  }
  //  System.out.println("pickUp() execute autoMoveTarget()");

  this.autoMoveTarget();
  //return the picked up item?
  //  System.out.println("!!ending pickUp()");
  if (allItems.isEmpty())
   return null; //if no items in the current room.

  return allItems.get(0); //null means no items left in room.
 }

 /**
  * a player LOOK AROUND to check a specific player's information:
  * -what room's the specific player is at
  * -what neighboring rooms the player can reach
  * - *this COST A TURN!
  *
  * @param playerName String
  * @return String (the checked player's information)
  */
 public String lookAround(String playerName, HashMap<String, ArrayList<String>> allNeighborsMap) {
  //  System.out.println("running lookAround()");

  /*costing a TURN*/
  this.turnsMap.put(this.playerName, 0);
  String currRoom = this.playersTargetNameRoomMap.get(playerName);
  //  System.out.println("it's current room is: " + currRoom);
  //2. get its neighboring rooms:
  ArrayList<String> allNeighbors = allNeighborsMap.get(currRoom);
  //  System.out.println("all its neighbors: " + allNeighbors);
  String strNeighbors = this.helperArrayListToString(allNeighbors);

  String res = String
      .format("The current room the player is in: %s. And its neighboring rooms: %s", currRoom,
          strNeighbors);
  //  System.out.println("lookAround() execute autoMoveTarget()");

  this.autoMoveTarget();
  return res;
 }

 ////////////////methods below don't cost a Turn//////////////////////////////

 //--------s2 - updatePlayerRoomInfo (not sure but done.)------------------------------//

 /**
  * modify: to include players in the room
  *
  * @param roomName Integer
  */
 public void updatePlayerRoomInfo(String roomName) {
  this.playersTargetNameRoomMap.put(playerName, roomName);
 }

 //--------s6 -displayPlayerInfo.(done.)------------------------------------//

 /**
  * Display a description of a specific player(from GOD's View)
  * including:
  * 1.where the player's at (which room)
  * 2.what items his carrying.
  *
  * @param playerName
  * @return String (the specific player's information)
  */
 public String displayPlayerInfo(String playerName) {
  //  System.out.println("running displayPlayerInfo()");

  //this doesn't cost a turn.
  //  System.out.println("player name is: " + playerName);
  //  System.out.println("the target roomNameMap is: "+ this.playersTargetNameRoomMap);
  //  System.out.println("this is: " + this.playersTargetNameRoomMap.get(playerName));
  //know the player's room (where they are) 1.
  String roomStr = this.playersTargetNameRoomMap.get(playerName);
  //  System.out.println("so the room is: " + roomStr);
  //what they carry 2.
  //TODO: fix null input roomStr
  ArrayList<String> allTheItems = this.helperRoomGetItems(roomStr);
  String itemsStr = allTheItems.toString();

  String ans = String
      .format("The player is at room: %s. The item(s) in the room: %s", roomStr, itemsStr);
  //  System.out.println("end of displayPlaerInfo()");
  return ans;
 }

 //--------s7 - autoMoveTarget------------------------------------//

 /**
  * the TARGET!! moves randomly in the world each time by calling this method.
  *
  * @return Integer (the room's index the TARGET just moved to)
  */
 public int autoMoveTarget() { //this method probably wil be called from any methods that consuming a TURN.
  //  return 1;
  //  System.out.println("running autoMoveTarget()");

  //TARGET TARGET MOVE!!! EVERY TURN!!!! OF THE GAME!
  int totalRooms = this.itemsRoomMap.size();
  if (targetLocation + 1 != totalRooms) {
   targetLocation += 1;
  } else {
   targetLocation = 0;
  }

  //1. the target moves to one of a random room.
  //CHECK:
  int roomIndex = targetLocation;
  //  System.out.println("target's current location index: " + roomIndex);
  String roomStr = this.helperIndexGetRoomName(roomIndex);
  //  System.out.println("room name related to index: " +roomStr);
  ///items in this room?
  ArrayList<String> allItemsLst = this.helperRoomGetItems(roomStr);
  //  System.out.println("all items in the room?: " + allItemsLst);
  String itemsStr = this.helperArrayListToString(allItemsLst);//2.
  //  System.out.println("now to reformatted items list: " + itemsStr);

  ///3. target taking damge:
  for (String playerName : this.playersTargetNameRoomMap.keySet()) {
   //   System.out.println("here1: this player's name: " + playerName);
   if (this.playersTargetNameRoomMap.get(playerName).equals(roomStr)) {
    //    System.out.println("here2 this player's room in: " + roomStr);
    //items the each player has:
    ArrayList<String> itemsLst = this.playersItemsMap.get(playerName);
    //    System.out.println("this player's playersItemsMap?: " + itemsLst);

    if (itemsLst == null)
     return targetLocation; //if no items for the player simply return.
    if (!itemsLst.isEmpty()) {
     for (String eachItem : itemsLst) {
      int damage = this.itemsDamageMap.get(eachItem);
      this.targetHealth -= damage;
     }
    }
   }
  }

  //2.check if there's player(s) in the room and INTERACT!
  // - target taking damages from each player.

  return targetLocation;
 }

 //===================================================
 //=====================================================
 //other useful methods:

 /**
  * check how many more items a player can still pick up.
  *
  * @return the number of more items a player can pick up.
  */
 public int pickMoreItems() {
  //  System.out.println("running pickMoreItems");
  int total = this.totalItemsAllowedMap.get(this.playerName);
  int size = this.playerItemsLst.size();
  return total - size;
 }

 /**
  * flip the turns:
  * 1 -> 0
  * 0 -> 1
  *
  * @return an integer representation as true (1) or false (0)
  */
 public int flipTurn() {
  boolean currTurn = this.getPlayerTurn();
  if (currTurn) {
   currTurn = false;
   return 0;
  } else if (!currTurn) {
   currTurn = true;
   return 1;
  }
  return -1;
 }

 //------------------------------------------------------------PRIVATE helper functions---------------------------------------------------//

 /**
  * to generate a random number between 1 and (i).
  *
  * @param i
  */
 private int helperRandNum(int i) {
  //random generator:
  Random ran = new Random();
  //  int val = ran.nextInt(i);
  return ran.nextInt(i);
 }

 /**
  * get room name from room index from hashmap.
  *
  * @return
  */
 private String helperIndexGetRoomName(int index) {
  for (String str : this.roomNameIndexMap.keySet()) {
   if (this.roomNameIndexMap.get(str) == index) {
    return str;
   }
  }
  return null;
 }

 /**
  * get items in the room from hashmap
  *
  * @return
  */ //TODO: fix this method for: []
 private ArrayList<String> helperRoomGetItems(String room) { //room get items
  ArrayList<String> arrLst = new ArrayList<>();
  //  System.out.println("the player is: " + this.playerName);
  //  System.out.println("the player is at room: " + this.playerRoom);
  //  System.out.println("this room is: " + room );
  //  System.out.println("playersTargetNameRoomMap: " + this.playersTargetNameRoomMap);
  //  System.out.println("roomnameIndexMap: " + this.roomNameIndexMap);
  //  System.out.println("items in this roomMap? " + this.roomNameIndexMap);
  //TODO
  if (room == null)
   return null; //if the room doesn't exist.
  int roomIndex = this.roomNameIndexMap.get(room);
  for (String item : this.itemsRoomMap.keySet()) {
   if (roomIndex == this.itemsRoomMap.get(item)) {
    arrLst.add(item);
   }
  }
  return arrLst;
 }

 private String helperArrayListToString(ArrayList<String> arrLst) {
  if (arrLst == null || arrLst.isEmpty())
   return null; //if the arrLst is null just return null.
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



