package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 * this is the player class.
 */
public class Player implements PlayerBuilder {
  private Mansion mansion;
  /* for fixed value map (no need for update) */ /*
                                                  * porb. need to be parsed in from mansion after
                                                  * calling playGame() from controller
                                                  */
  private HashMap<String, Integer> roomNameIndexMap; // <room name, room Index>
  private HashMap<String, Integer> itemsDamageMap; // <item, damage>
  // private fields: (x6)
  private Pet pet;
  Set<String> evidenceSet;
  private Target target;
  private boolean playerTurn;
  private String computerOrHuman;
  private String playerName;
  private String playerRoom; // the room the player's currently at
  private int playerTotalAllowedItem;
  private HashMap<String, Integer> totalItemsAllowedMap; // <room name, the total number alloed
  private ArrayList<String> playerItemsLst; // each this list represent a player's all items.
  private HashMap<String, ArrayList<String>> playersItemsMap;
  private HashMap<String, String> playersTargetNameRoomMap;
  private HashMap<String, Integer> itemsRoomMap;
  private HashMap<String, Integer> turnsMap; // defaults are true -> 1.

  /* a list for storing evidence (items) */

  /**
   * Constructor.
   * @param mansion the mansion object for the whole game.
   * @param computerOrHuman computer or human player
   * @param playerName the player's name
   * @param playerRoom the player's room
   * @param playerTotalAllowedItem the total items allowed to carry for the player.
   */
  public Player(Mansion mansion, String computerOrHuman, String playerName, String playerRoom, int playerTotalAllowedItem) {
    this.mansion = mansion;
    this.pet = this.mansion.getPet();
    this.evidenceSet = this.mansion.getEvidenceSet();
    this.target = this.mansion.getTarget();
    // inputs from mansion:
    this.roomNameIndexMap = this.mansion.getRoomNameIndexMap();
    this.itemsDamageMap = this.mansion.getItemsDamageMap();
    this.playerTurn = false; // default is false. //TODO: double check!
    // inputs from controller:
    this.computerOrHuman = computerOrHuman;
    this.playerName = playerName;
    this.playerRoom = playerRoom;
    this.playerTotalAllowedItem = playerTotalAllowedItem;
    // below inputs from mansion:
    this.playerItemsLst = new ArrayList<>();
    this.playersItemsMap = this.mansion.getPlayersItemsMap();
    this.itemsRoomMap = this.mansion.getItemsRoomMap();
    // update maps:
    this.totalItemsAllowedMap = this.mansion.getTotalItemsAllowedMap();
    this.totalItemsAllowedMap.put(this.playerName, this.playerTotalAllowedItem);
    this.playersTargetNameRoomMap = this.mansion.getPlayersTargetNameRoomMap();
    this.playersTargetNameRoomMap.put(this.playerName, this.playerRoom);
    this.turnsMap = this.mansion.getTurnsMap();
    this.turnsMap.put(this.playerName, 1); // default is 1 for true
  }

  /* methods added for milestone3: gameplay */

  @Override
  public String movePet(int roomIndex, Pet pet) {
    String roomName = this.helperIndexGetRoomName(roomIndex);
    pet.setPetLocation(roomIndex);
    return roomName;
  }

  @Override public boolean seenPlayer() {
    this.

    return false;
  }

  /**
   * attempting with the most damaged item a player have.
   * 
   * @param target the target to kill in this game.
   * @return the target's final health
   */
  @Override
  public int pcAttemptTarget(Target target) {
    //  player attempt on target's life costing a turn.
    /*
     * when costing a turn: 1. the target will move() TODO: dfs() move for the pet
     * each turn 2. the pet will move()
     */
    this.turnsMap.put(this.playerName, 0);

    int maxDamage = 1; // if the player doesn't have items(weapons), then poke.
    String currItem = null;
    int currDamage = 0;
    /* find item with the max damage the player had. */
    for (int i = 0; i < this.playerItemsLst.size(); i++) {
      currItem = this.playerItemsLst.get(i);
      currDamage = this.itemsRoomMap.get(currItem);
      if (currDamage > maxDamage) {
        maxDamage = currDamage;
      }
    }

    // TODO: a check to see if the other player's saw this attempt ?
    /*
    * 1. whether pet is in this room:
    *     pet space cannot be seen from neighbors.
    * */

    int targetHealth = target.getTargetHealth();
    targetHealth -= currDamage;
    target.setTargetHealth(targetHealth);

    if (currItem != null){
      int size = this.playerItemsLst.size();
      for (int i = 0; i < size; i++){
        if (this.playerItemsLst.get(i).equals(currItem)){
          this.playerItemsLst.remove(i); //found & delete from player items lst.
          break;
        }
      }
      this.evidenceSet.add(currItem); //add to the evidence set.
    }

    this.autoMoveTarget();

    return target.getTargetHealth();
  }

  @Override public int attemptTarget(Target target, String itemUsed) {
    //  player attempt on target's life costing a turn.
    /*
     * when costing a turn: 1. the target will move() TODO: dfs() move for the pet
     * each turn 2. the pet will move()
     */
    this.turnsMap.put(this.playerName, 0);

    int maxDamage = 1; // if the player doesn't have items(weapons), then poke.
    String currItem = null;
    int currDamage = 0;


    // TODO: a check to see if the other player's saw this attempt ?
    /*
     * 1. whether pet is in this room:
     *     pet space cannot be seen from neighbors.
     * */

    int targetHealth = target.getTargetHealth();
    targetHealth -= currDamage;
    target.setTargetHealth(targetHealth);

    if (currItem != null){
      int size = this.playerItemsLst.size();
      for (int i = 0; i < size; i++){
        if (this.playerItemsLst.get(i).equals(currItem)){
          this.playerItemsLst.remove(i); //found & delete from player items lst.
          break;
        }
      }
      this.evidenceSet.add(currItem); //add to the evidence set.
    }

    this.autoMoveTarget();

    return target.getTargetHealth();
  }

  /**
   * modify: to include players in the room.
   *
   * @param roomName Integer
   */
  @Override
  public void updatePlayerRoomInfo(String roomName) {
    this.playersTargetNameRoomMap.put(playerName, roomName);
  }

  /**
   * pick up an item.
   * 
   * @return the picked item.
   */
  @Override
  public String pickUp() {
    /* costing a TURN */

    this.turnsMap.put(this.playerName, 0);
    /// which room player at?
    int roomIndex = this.roomNameIndexMap.get(playerRoom);
    /// what items in this room?
    String roomName = this.helperIndexGetRoomName(roomIndex);
    // TODO: fix the null roomName
    ArrayList<String> allItems = this
        .helperRoomGetItems(roomName); /* here will get the items list for the room */
    /// can he pick it up?
    if (!allItems.isEmpty()
        && (totalItemsAllowedMap.get(this.playerName) - playerItemsLst.size()) > 0) {
      playerItemsLst.add(allItems.get(0)); // always picked the 1st item in the list.
      this.itemsRoomMap.remove(allItems.get(0)); // room's removed the item.
      this.playersItemsMap.put(this.playerName, playerItemsLst); // add it to hashmap:
    }

    this.autoMoveTarget();
    // return the picked up item?
    if (allItems.isEmpty()) {
      return null; // if no items in the current room.
    }
    this.setPlayerTurn(false);
    return allItems.get(0); // null means no items left in room.
  }

  // TODO: make the LOOK AROUND worth the turn.
  /**
   * a player LOOK AROUND to check a specific player's information: -what room's
   * the specific player is at -what neighboring rooms the player can reach -
   * *this COST A TURN.
   *
   * @param playerName String
   * @return String (the checked player's information)
   */
  @Override
  public String lookAround(String playerName, HashMap<String, ArrayList<String>> allNeighborsMap) {

    /* costing a TURN */
    this.turnsMap.put(this.playerName, 0);
    String currRoom = this.playersTargetNameRoomMap.get(playerName);
    // 2. get its neighboring rooms:
    ArrayList<String> allNeighbors = allNeighborsMap.get(currRoom);
    String strNeighbors = this.helperArrayListToString(allNeighbors);

    String res = String.format(
        "The current room the player is in: %s. And its neighboring rooms: %s", currRoom,
        strNeighbors);

    this.autoMoveTarget();
    this.setPlayerTurn(false);

    return res;
  }

  //////////////// methods below don't cost a Turn//////////////////////////////

  /**
   * move the player.
   * 
   * @param allNeighborsMap the hashmap
   * @return the index room the player moved to.
   */
  @Override
  public int movePlayer(HashMap<String, ArrayList<String>> allNeighborsMap) {
    // TODO: player be moved to a specific room, not a random room.
    /* costing a TURN */
    this.turnsMap.put(this.playerName, 0);
    ArrayList<String> playerNeighbors = allNeighborsMap.get(this.playerRoom);
    int size = playerNeighbors.size();
    int index = 0;
    if (size != 1) {
      index = this.helperRandNum(size - 1);
    }
    // player move to a random index:
    String neighborRoom = playerNeighbors.get(index);
    // update hashmaps:
    int neighborIndex = this.roomNameIndexMap.get(neighborRoom);
    this.playerRoom = this.helperIndexGetRoomName(neighborIndex); // to update player's current room
    this.updatePlayerRoomInfo(playerRoom);
    this.setPlayerTurn(false);
    // return the moved to room index?
    this.autoMoveTarget();
    return neighborIndex;
  }

  // --------s6 -displayPlayerInfo.(done.)------------------------------------//

  /**
   * Display a description of a specific player(from GOD's View) including:
   * 1.where the player's at (which room) 2.what items his carrying.
   *
   * @param playerName the name of the player
   * @return String (the specific player's information)
   */
  @Override
  public String displayPlayerInfo(String playerName) {
    // this doesn't cost a turn.
    // know the player's room (where they are) 1.
    String roomStr = this.playersTargetNameRoomMap.get(playerName);
    // what they carry 2.
    // TODO: fix null input roomStr
    ArrayList<String> allTheItems = this.helperRoomGetItems(roomStr);
    String itemsStr = allTheItems.toString();

    String ans = String.format("The player is at room: %s. The item(s) in the room: %s", roomStr,
        itemsStr);
    return ans;
  }

  // TODO: how to move this target & its starting position where?
  /**
   * the TARGET!! moves randomly in the world each time by calling this method.
   *
   * @return Integer (the room's index the TARGET just moved to)
   */
  @Override
  public int autoMoveTarget() { // this method probably wil be called from any methods that
    // return 1;

    // TARGET TARGET MOVE!!! EVERY TURN!!!! OF THE GAME!

    int totalRooms = this.itemsRoomMap.size();
    int targetLocation = this.target.getTargetLocation();
    if (targetLocation + 1 != totalRooms) {
      targetLocation += 1;
      this.target.setTargetLocation(targetLocation);
    } else {
      targetLocation = 0;
      this.target.setTargetLocation(targetLocation);
    }

//    // 1. the target moves to one of a random room.
//    // CHECK:
//    int roomIndex = targetLocation;
//    String roomStr = this.helperIndexGetRoomName(roomIndex);
//    /// items in this room?
//    ArrayList<String> allItemsLst = this.helperRoomGetItems(roomStr);
//    String itemsStr = this.helperArrayListToString(allItemsLst); // 2.

//    /// 3. target taking damge:
//    for (String playerName : this.playersTargetNameRoomMap.keySet()) {
//      if (this.playersTargetNameRoomMap.get(playerName).equals(roomStr)) {
//        // items the each player has:
//        ArrayList<String> itemsLst = this.playersItemsMap.get(playerName);
//
//        if (itemsLst == null) {
//          return targetLocation; // if no items for the player simply return.
//        }
//        if (!itemsLst.isEmpty()) {
//          for (String eachItem : itemsLst) {
//            int damage = this.itemsDamageMap.get(eachItem);
//            this.targetHealth -= damage;
//          }
//        }
//      }
//    }

    // 2.check if there's player(s) in the room and INTERACT!
    // - target taking damages from each player.

    return this.target.getTargetLocation();
  }

  /**
   * check how many more items a player can still pick up.
   *
   * @return the number of more items a player can pick up.
   */
  @Override
  public int pickMoreItems() {
    int total = this.totalItemsAllowedMap.get(this.playerName);
    int size = this.playerItemsLst.size();
    return total - size;
  }

  /**
   * flip the turns: 1 -> 0 0 -> 1.
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

  /* some helper functions. */

  /**
   * to generate a random number between 1 and (i).
   *
   * @param i the upper limit of the random number.
   */
  private int helperRandNum(int i) {
    // random generator:
    Random ran = new Random();
    // int val = ran.nextInt(i);
    return ran.nextInt(i);
  }

  /**
   * get room name from room index from hashmap.
   *
   * @return the string representation
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
   * get items in the room from hashmap.
   *
   * @return all items in the room
   */
  private ArrayList<String> helperRoomGetItems(String room) { // room get items
    ArrayList<String> arrLst = new ArrayList<>();
    if (room == null) {
      return null; // if the room doesn't exist.
    }
    int roomIndex = this.roomNameIndexMap.get(room);
    for (String item : this.itemsRoomMap.keySet()) {
      if (roomIndex == this.itemsRoomMap.get(item)) {
        arrLst.add(item);
      }
    }
    return arrLst;
  }

  private String helperArrayListToString(ArrayList<String> arrLst) {
    if (arrLst == null || arrLst.isEmpty()) {
      return null; // if the arrLst is null just return null.
    }
    StringBuffer sb = new StringBuffer();
    for (String str : arrLst) {
      sb.append(str);
      sb.append(",");
    }
    String str = sb.toString();
    str = str.substring(0, str.length() - 2); // deleting the last ','
    return str;
  }

  /* below are all getters & setters. */
  /**
   * getter.
   * 
   * @return the hashmap for items
   */
  public HashMap<String, Integer> getTotalItemsAllowedMap() {
    return totalItemsAllowedMap;
  }

  /**
   * setter.
   * 
   * @param totalItemsAllowedMap hashmap
   */
  public void setTotalItemsAllowedMap(HashMap<String, Integer> totalItemsAllowedMap) {
    this.totalItemsAllowedMap = totalItemsAllowedMap;
  }

  /**
   * getter.
   * 
   * @return the hashmap
   */
  public HashMap<String, String> getPlayersTargetNameRoomMap() {
    return playersTargetNameRoomMap;
  }

  /**
   * getter.
   * 
   * @return the hashmap
   */
  public HashMap<String, ArrayList<String>> getPlayersItemsMap() {
    return playersItemsMap;
  }

  /**
   * getter.
   * 
   * @return the hashmap
   */
  public HashMap<String, Integer> getItemsRoomMap() {
    return itemsRoomMap;
  }

  /**
   * setter.
   * 
   * @return the hashmap
   */
  public void setItemsRoomMap(HashMap<String, Integer> itemsRoomMap) {
    this.itemsRoomMap = itemsRoomMap;
  }

  /**
   * check if the turnsMap returns 1 (true) or 0(false) for the current player.
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
   * getter.
   * 
   * @return the array list
   */
  public ArrayList<String> getPlayerItemsLst() {
    return playerItemsLst;
  }

  /**
   * setter.
   * 
   * @param playerItemsLst the player's current item-list.
   */
  public void setPlayerItemsLst(ArrayList<String> playerItemsLst) {
    this.playerItemsLst = playerItemsLst;
  }

  /**
   * getter.
   * 
   * @return the total number of players
   */
  public int getPlayerTotalAllowedItem() {
    return playerTotalAllowedItem;
  }

  /**
   * setter.
   */
  public void setPlayerTotalAllowedItem(int playerTotalAllowedItem) {
    this.playerTotalAllowedItem = playerTotalAllowedItem;
  }

  /**
   * getter.
   * 
   * @return the player's turn
   */
  public boolean getPlayerTurn() {
    return this.playerTurn;
  }

  /**
   * setter.
   */
  public void setPlayerTurn(boolean playerTurn) {
    this.playerTurn = playerTurn;
  }

  /**
   * getter.
   * 
   * @return a human or a computer as a String.
   */
  public String getComputerOrHuman() {
    return computerOrHuman;
  }

  /**
   * getter.
   * 
   * @return string type of player's room
   */
  public String getPlayerRoom() {
    return playerRoom;
  }

  /**
   * getter.
   * 
   * @return the player's name
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * getter.
   * 
   * @return hashmap
   */
  public HashMap<String, Integer> getRoomNameIndexMap() {
    return roomNameIndexMap;
  }

  /**
   * setter.
   * 
   * @param roomNameIndexMap hashmap
   */
  public void setRoomNameIndexMap(HashMap<String, Integer> roomNameIndexMap) {
    this.roomNameIndexMap = roomNameIndexMap;
  }

  /**
   * getter.
   * 
   * @return hashmap
   */
  public HashMap<String, Integer> getTurnsMap() {
    return turnsMap;
  }

  /**
   * getter.
   * 
   * @return hashmap
   */
  public HashMap<String, Integer> getItemsDamageMap() {
    return itemsDamageMap;
  }

} // end of Player.java
