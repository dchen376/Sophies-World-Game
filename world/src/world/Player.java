package world;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * this is the player class.
 */
public class Player implements PlayerBuilder {
  /* objects */
  private Mansion mansion;
  private Pet pet;
  private Target target;
  /* attributes */
  private boolean playerTurn;
  private int playerTotalAllowedItem;
  private String computerOrHuman;
  private String playerName;
  private String playerRoom; // the room the player's currently at
  private ArrayList<String> playerItemsLst; // each this list represent a player's all items.
  private Set<String> evidenceSet; // to store the evidence.
  /* a list for storing evidence (items) */

  /**
   * Constructor.
   * 
   * @param mansion                the mansion object for the whole game.
   * @param computerOrHuman        computer or human player
   * @param playerName             the player's name
   * @param playerRoom             the player's room
   * @param playerTotalAllowedItem the total items allowed to carry for the
   *                               player.
   */
  public Player(Mansion mansion, String computerOrHuman, String playerName, String playerRoom,
      int playerTotalAllowedItem) {
    this.mansion = mansion;
    this.pet = this.mansion.getPet();
    this.evidenceSet = this.mansion.getEvidenceSet();
    this.target = this.mansion.getTarget();
    // inputs from mansion:
    this.playerTurn = false; // default is false. //TODO: double check!
    // inputs from controller:
    this.playerTotalAllowedItem = playerTotalAllowedItem;
    this.computerOrHuman = computerOrHuman;
    this.playerName = playerName;
    this.playerRoom = playerRoom;
    this.playerItemsLst = new ArrayList<>();
    this.evidenceSet = new HashSet<>();
    // below inputs from mansion:
    // update maps:
    this.mansion.getTotalItemsAllowedMap().put(this.playerName, this.playerTotalAllowedItem);
    this.mansion.getPlayersNameRoomMap().put(this.playerName, this.playerRoom);
    this.mansion.getTurnsMap().put(this.playerName, 1); // default is 1 for true
  }

  /* methods added for milestone3: gameplay */

  @Override
  public String movePet() {
    int movedLoc = this.mansion.getRoomNameIndexMap().get(this.getPlayerRoom());
    // pet moved to this location for a brief moment; hence,
    // the player whoever is the next turn will have someway
    // to find out this sneak move.
    pet.setPetLocation(movedLoc);
    this.mansion.getPetBlessings().put(this, true);

    String roomName = this.helperIndexGetRoomName(movedLoc);
    return roomName;
  }

  // helper method for granting a player's invisibility for
  @Override
  public boolean checkBlessings() {
    return this.mansion.getPetBlessings().get(this);
  }

  // implemented the seen methods
  @Override
  public boolean seenPlayer() {
    // : if a pet is in here, the this place cannot be seen:
    if (checkBlessings()) {
      this.mansion.getPetBlessings().put(this, false); // update the blessings.
      return false; // if blessed, then cannot be seen by others.
    }

    boolean seen = false;
    ArrayList<String> neighborRooms = this.mansion.getAllNeighborsMap().get(this.getPlayerName());
    ArrayList<Player> allPlayers = this.mansion.getAllPlayers();
    neighborRooms.add(this.getPlayerRoom()); // also add player's current room to the list.
    /* to check if it's in a neighbor room OR the same room */
    for (String room : neighborRooms) {
      for (Player player : allPlayers) {
        if (room.equals(this.mansion.getPlayersNameRoomMap().get(player.getPlayerName()))) {
          seen = true;
          break;
        }
      }
    }

    return seen;
  }

  /**
   * attempting with the most damaged item a player have.
   * 
   * @return the target's final health
   */
  @Override
  public int pcAttemptTarget() {
    // player attempt on target's life costing a turn.
    /*
     * : dfs() move for the pet when costing a turn: 1. the target will move() each
     * turn 2. the pet will move()
     */

    // : also costing a turn
//    this.mansion.getTurnsMap().put(this.playerName, 0);

    if (this.seenPlayer()) {
      this.autoMoveTarget();
      this.mansion.getPet().dfsMove();
      return this.mansion.getTargetHealth();
    }

    // if attack is seen: still cost the turn but did nothing to target:
//    if (this.seenPlayer()) {
//      return this.target.getTargetHealth();
//    } else { // else attack is not being seen.
    int maxDamage = 1; // if the player doesn't have items(weapons), then poke.
    String currItem = null;
    int currDamage = 0;
    /* find item with the max damage the player had. */
    for (int i = 0; i < this.playerItemsLst.size(); i++) {
      currItem = this.playerItemsLst.get(i);
      currDamage = this.mansion.getItemsRoomMap().get(currItem);
      if (currDamage > maxDamage) {
        maxDamage = currDamage;
      }
    }

    /*
     * 1. whether pet is in this room: pet space cannot be seen from neighbors.
     */

    int targetHealth = target.getTargetHealth();
    targetHealth -= currDamage;
    target.setTargetHealth(targetHealth);

    if (currItem != null) {
      int size = this.playerItemsLst.size();
      for (int i = 0; i < size; i++) {
        if (this.playerItemsLst.get(i).equals(currItem)) {
          this.playerItemsLst.remove(i); // found & delete from player items lst.
          break;
        }
      }
      this.evidenceSet.add(currItem); // add to the evidence set.
    }

//    }

    /* target move during each turn */


    /* costing a TURN */
    this.mansion.getTurnsMap().put(this.playerName, 0);
    this.autoMoveTarget();
    this.mansion.getPet().dfsMove();
    this.setPlayerTurn(false);

    return target.getTargetHealth();
  }

  @Override
  public int attemptTarget(String itemUsed) {
    // player attempt on target's life costing a turn.
    /*
     * : dfs() move for the pet when costing a turn: 1. the target will move() each
     * turn 2. the pet will move()
     */
//    this.mansion.getTurnsMap().put(this.playerName, 0);

    if (this.seenPlayer()) {
      this.autoMoveTarget();
      this.mansion.getPet().dfsMove();
      return this.mansion.getTargetHealth();
    }

    int maxDamage = 1; // if the player doesn't have items(weapons), then poke.
    String currItem = null;
    int currDamage = 0;

    /*
     * 1. whether pet is in this room: pet space cannot be seen from neighbors.
     */

    int targetHealth = target.getTargetHealth();
    targetHealth -= currDamage;
    target.setTargetHealth(targetHealth);

    if (currItem != null) {
      int size = this.playerItemsLst.size();
      for (int i = 0; i < size; i++) {
        if (this.playerItemsLst.get(i).equals(currItem)) {
          this.playerItemsLst.remove(i); // found & delete from player items lst.
          break;
        }
      }
      this.evidenceSet.add(currItem); // add to the evidence set.
    }


    /* costing a TURN */
    this.mansion.getTurnsMap().put(this.playerName, 0);
    this.autoMoveTarget();
    this.mansion.getPet().dfsMove();
    this.setPlayerTurn(false);

    return target.getTargetHealth();
  }

  /**
   * modify: to include players in the room.
   *
   * @param roomName Integer
   */
  @Override
  public void updatePlayerRoomInfo(String roomName) {
    this.mansion.getPlayersNameRoomMap().put(playerName, roomName);
  }

  /**
   * pick up an item.
   * 
   * @return the picked item.
   */
  @Override
  public String pickUp() {
    /* costing a TURN */

//    this.mansion.getTurnsMap().put(this.playerName, 0);
    /// which room player at?
    int roomIndex = this.mansion.getRoomNameIndexMap().get(playerRoom);
    /// what items in this room?
    String roomName = this.helperIndexGetRoomName(roomIndex);
    // TODO: fix the null roomName
    ArrayList<String> allItems = this
        .helperRoomGetItems(roomName); /* here will get the items list for the room */
    /// can he pick it up?
    if (!allItems.isEmpty() && (this.mansion.getTotalItemsAllowedMap().get(this.playerName)
        - playerItemsLst.size()) > 0) {
      playerItemsLst.add(allItems.get(0)); // always picked the 1st item in the list.
      this.mansion.getItemsRoomMap().remove(allItems.get(0)); // room's removed the item.
      this.mansion.getPlayersItemsMap().put(this.playerName, playerItemsLst); // add it to hashmap:
    }


    /* costing a TURN */
    this.mansion.getTurnsMap().put(this.playerName, 0);
    this.autoMoveTarget();
    this.mansion.getPet().dfsMove();
    this.setPlayerTurn(false);

    // return the picked up item?
    if (allItems.isEmpty()) {
      return null; // if no items in the current room.
    }


    return allItems.get(0); // null means no items left in room.
  }

  // helper method to get an arraylist representation of player's items with items
  // corresponding damage points.
  public ArrayList<String> getItemsDamagesLst() {
    ArrayList<String> allItems = this.getPlayerItemsLst();
    ArrayList<String> res = new ArrayList<>();
    for (String item : allItems) {
      int damage = this.mansion.getItem().getDamageAmount(item);
      String str = item + " -> " + damage + ". ";
      res.add(str);
    }
    return res;
  }

  public boolean checkValidItem(String checkedItem) {
    boolean checker = false;
    for (String str : this.getPlayerItemsLst()) {
      if (checkedItem.equals(str)) {
        checker = true;
      }
    }
    return checker;
  }

  // : make the LOOK AROUND worth the turn.

  /**
   * a player to look around by displaying information about his position in the
   * world including neighboring space information, also indicate pet info also
   * indicate target into. This represents a turn. this COST A TURN.
   *
   * @return a string representation of the final info.
   */
  @Override
  public String lookAround() {
    ArrayList<String> finalRes = new ArrayList<>();

    // 1. get all neighbor players room info.
    /* get current room */
    String currRoom = this.playerRoom;
    ArrayList<Player> playersReside = new ArrayList<>();
    this.mansion.getPlayersNameRoomMap();

    for (int i = 0; i < this.mansion.getAllPlayers().size(); i++) {
      for (Player player : this.mansion.getAllPlayers()) {
        if (player.getPlayerRoom().equals(currRoom)
            && !player.getPlayerName().equals(this.getPlayerName())) {
          /* get all players in current room */
          playersReside.add(player);
        }
      }
    }
    /* get all players in neighboring rooms */
    ArrayList<String> neighborRooms = this.mansion.getAllNeighborsMap().get(this.getPlayerRoom());
    for (int i = 0; i < neighborRooms.size(); i++) {
      for (int j = 0; j < this.mansion.getAllPlayers().size(); j++) {
        if (neighborRooms.get(i).equals(this.mansion.getAllPlayers().get(j).getPlayerRoom())) {
          playersReside.add(this.mansion.getAllPlayers().get(j));
        }
      }
    }

    //  2-1 get all items info
    /* put self info first */
    String selfInfo = String.format("You are: %s and is at room %s, and is having items: %s.",
        this.getPlayerName(), this.getPlayerRoom(), this.getPlayerItemsLst().toString());
    finalRes.add(selfInfo);
    for (Player player : playersReside) {
      String playerInfo = String.format("The player: %s is at room %s, and is having items: %s.",
          player.getPlayerName(), player.getPlayerRoom(), player.getPlayerItemsLst().toString());
      finalRes.add(playerInfo);
    }

    //  2-2 to also indicate if pets is nearing? (if so, indicate which room is
    // invisible. if no, just say no.)
    int petLoc = this.mansion.getPet().getPetLocation();
    ArrayList<Integer> neighborsIndex = new ArrayList<>();
    for (int i = 0; i < neighborRooms.size(); i++){
      neighborsIndex.add(this.mansion.getRoomNameIndexMap().get(neighborRooms.get(i)));
    } //finished adding room index to this list.

    for (int i = 0; i < neighborsIndex.size(); i++){
      if (petLoc == neighborsIndex.get(i)){
        finalRes.add(String.format("The pet is nearing!!! The pet is at room: %s", this.pet.getPetLocation()));
      }else {
        finalRes.add("The pet is not anywhere in your visibility yet; it is not in any of the neighboring rooms.");
      }
    }


    //  2-3 to also indicate if target is nearing?

    int tarLoc = this.mansion.getTargetLocation();
    for (int i = 0; i < neighborsIndex.size(); i++){
      if (tarLoc == neighborsIndex.get(i)){
        finalRes.add(String.format("The Target is nearing!!! The target is at room: %s", this.target.getTargetLocation()));
        finalRes.add(String.format("The Target's current health is: %d", this.target.getTargetHealth()));
      }else {
        finalRes.add("The Target is not anywhere in your visibility yet; it is not in any of the neighboring rooms.");
      }
    }





    /* costing a TURN */
    this.mansion.getTurnsMap().put(this.playerName, 0);
    this.autoMoveTarget();
    this.mansion.getPet().dfsMove();
    this.setPlayerTurn(false);

    return finalRes.toString();
  }

  //////////////// methods below don't cost a Turn//////////////////////////////

  /**
   * move the player.
   * 
   * @param roomPicked the room player picked to move to.
   * @return the index room the player moved to.
   */
  @Override
  public int movePlayer(String roomPicked) {
    // : player be moved to a specific room, not a random room.
    /* costing a TURN */

//    this.mansion.getTurnsMap().put(this.playerName, 0);

//      ArrayList<String> playerNeighbors = this.mansion.getAllNeighborsMap().get(this.playerRoom);
//      int size = playerNeighbors.size();
//      int index = 0;
//      if (size != 1) {
//        index = this.helperRandNum(size - 1);
//      }

    // player move to a random index:
//    String neighborRoom = playerNeighbors.get(index);
    String neighborRoom = roomPicked;
    // update hashmaps:
    int neighborIndex = this.mansion.getRoomNameIndexMap().get(neighborRoom);
    this.playerRoom = this.helperIndexGetRoomName(neighborIndex); // to update player's current room
    this.updatePlayerRoomInfo(playerRoom);
    this.setPlayerTurn(false);

    // return the moved to room index?

    /* costing a TURN */
    this.mansion.getTurnsMap().put(this.playerName, 0);
    this.autoMoveTarget();
    this.pet.dfsMove();
    this.setPlayerTurn(false);

    return neighborIndex;
  }

  @Override
  public boolean checkValidRoom(String checkedRoom) { // TODO: put this check condition in the
                                                      // driver.
    ArrayList<String> playerNeighbors = this.mansion.getAllNeighborsMap().get(this.playerRoom);
    for (String room : playerNeighbors) {
      if (checkedRoom.equals(room)) {
        return true;
      }
    }
    return false;
  }

  /**
   * a helper function to get its neighboring rooms.
   * 
   * @return return the player's neighboring rooms.
   */
  public ArrayList<String> helperNeighborRooms() {
    ArrayList<String> res = this.mansion.getAllNeighborsMap().get(this.getPlayerRoom());
    return res;
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
    String roomStr = this.mansion.getPlayersNameRoomMap().get(playerName);
    // what they carry 2.
    // TODO: fix null input roomStr: so maybe use the a checker too here.
    ArrayList<String> allTheItems = this.helperRoomGetItems(roomStr);
    String itemsStr = allTheItems.toString();

    String ans = String.format("The player is at room: %s. The item(s) in the room: %s", roomStr,
        itemsStr);
    return ans;
  }

  /**
   * the TARGET!! moves one index room forward in the world each time by calling
   * this method.
   *
   * @return Integer (the room's index the TARGET just moved to)
   */
  @Override
  public int autoMoveTarget() { // this method probably wil be called from any methods that
    // return 1;

    // TARGET TARGET MOVE!!! EVERY TURN!!!! OF THE GAME!

    int totalRooms = this.mansion.getItemsRoomMap().size();
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
    int total = this.mansion.getTotalItemsAllowedMap().get(this.playerName);
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
    for (String str : this.mansion.getRoomNameIndexMap().keySet()) {
      if (this.mansion.getRoomNameIndexMap().get(str) == index) {
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
    int roomIndex = this.mansion.getRoomNameIndexMap().get(room);
    for (String item : this.mansion.getItemsRoomMap().keySet()) {
      if (roomIndex == this.mansion.getItemsRoomMap().get(item)) {
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

  /**
   * check if the turnsMap returns 1 (true) or 0(false) for the current player.
   *
   * @return boolean
   */
  public boolean checkTurnsMap() {
    if (this.mansion.getTurnsMap().get(this.playerName) == 1) {
      return true;
    } else if (this.mansion.getTurnsMap().get(this.playerName) == 0) {
      return false;
    }
    return false;
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
   * @return the player's name
   */
  public String getPlayerName() {
    return playerName;
  }

  public boolean isPlayerTurn() {
    return playerTurn;
  }

  public int getPlayerTotalAllowedItem() {
    return playerTotalAllowedItem;
  }

  public String getPlayerRoom() {
    return playerRoom;
  }

  public ArrayList<String> getPlayerItemsLst() {
    return playerItemsLst;
  }

  public Set<String> getEvidenceSet() {
    return evidenceSet;
  }
} // end of Player.java
