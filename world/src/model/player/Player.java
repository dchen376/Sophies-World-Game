package model.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import model.mansion.MansionBuilder;
import model.pet.Pet;
import model.target.Target;

/**
 * this is the player class.
 */
public class Player implements PlayerBuilder {
  /* fields */
  /* objects */
  private MansionBuilder mansionBuilder;
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
   * @param mansionBuilder         the mansion object for the whole game.
   * @param computerOrHuman        computer or human player
   * @param playerName             the player's name
   * @param playerRoom             the player's room
   * @param playerTotalAllowedItem the total items allowed to carry for the
   *                               player.
   */
  public Player(MansionBuilder mansionBuilder, String computerOrHuman, String playerName,
      String playerRoom, int playerTotalAllowedItem) {
    this.mansionBuilder = mansionBuilder;
    this.pet = this.mansionBuilder.getPet();
    this.evidenceSet = this.mansionBuilder.getEvidenceSet();
    this.target = this.mansionBuilder.getTarget();
    // inputs from mansion:
    this.playerTurn = true; // default is true. //: double check!
    // inputs from controller:
    this.playerTotalAllowedItem = playerTotalAllowedItem;
    this.computerOrHuman = computerOrHuman;
    this.playerName = playerName;
    this.playerRoom = playerRoom;
    this.playerItemsLst = new ArrayList<>();
    this.evidenceSet = new HashSet<>();
    // below inputs from mansion:
    // update maps:
    this.mansionBuilder.getTotalItemsAllowedMap().put(this.playerName, this.playerTotalAllowedItem);
    this.mansionBuilder.getPlayersNameRoomMap().put(this.playerName, this.playerRoom);
    this.mansionBuilder.getTurnsMap().put(this.playerName, 1); // default is 1 for true
    this.mansionBuilder.getPetBlessingsMap().put(this, false);
  }

  /* methods added for milestone3: gameplay */

  @Override
  public String movePet() {
    int movedLoc = this.mansionBuilder.getRoomNameIndexMap().get(this.getPlayerRoom());
    // pet moved to this location for a brief moment; hence,
    // the player whoever is the next turn will have someway
    // to find out this sneak move.
    pet.setPetLocation(movedLoc);
    this.mansionBuilder.getPetBlessingsMap().put(this, true);

    String roomName = this.helperIndexGetRoomName(movedLoc);
    return roomName;
  }

  // helper method for granting a player's invisibility for
  @Override
  public boolean checkBlessings() {
    return this.mansionBuilder.getPetBlessingsMap().get(this);
  }

  // implemented the seen methods
  @Override
  public boolean seenPlayer() {
    // : if a pet is in here, then this place cannot be seen:

    //extra condition: if pet is just at the current room: then it makes the room unseen
      //including the players in this room: nobody can see nobody.
    if (this.getPlayerRoomIndex() == this.mansionBuilder.getPetLocation()) {
      return false;
    }
    ArrayList<String> allCheckRooms = this.mansionBuilder.getRoom().getNeighbors(this.getPlayerRoom());
    allCheckRooms.add(this.getPlayerRoom());
    ArrayList<String> allRestPlayersRooms = new ArrayList<>();
    for (Player player : this.mansionBuilder.getAllPlayers()){
      if (!player.getPlayerName().equals(this.playerName)){
      allRestPlayersRooms.add(player.getPlayerRoom());
      }
    }
    for (String checkRoom: allCheckRooms) {
      for (String playerRoom: allRestPlayersRooms){
       if (checkRoom.equals(playerRoom)){
         return true;
       }
      }
    }
    return false;
  }

  /**
   * check target is near. if not return false
   * 
   * @return false if not near.
   */
  private Boolean checkTargetNear() { // false if fail attempt
    ArrayList<String> neighborRoomLst = this.mansionBuilder.getRoom().getNeighbors(this.getPlayerRoom());
    neighborRoomLst.add(this.playerRoom);
    String targetRoom = this.target.getTargetLocationName();


    for (String room : neighborRoomLst) {
      if (targetRoom.equals(room)) {
        return true;
      }
    }
    return false;
  }

  private Boolean checkPetInteference() { // true if fail attempt
    Boolean petInteferenceAttempt = false;
    // if pet the same room as target, but player not in the room -> fail
    if (target.getTargetLocation() == this.mansionBuilder.getPetLocation()
        && target.getTargetLocation() != this.getPlayerRoomIndex()) {
      return true;
    }

    return petInteferenceAttempt;
  }

  /**
   * attempting with the most damaged item a player have.
   * 
   * @return the target's final health
   */
  @Override
  public int pcAttemptTarget() {

    // TODO: check target in nearing
    /*
     * 1. whether pet is in this room: pet space cannot be seen from neighbors.
     */
    /* the above is done inside the seenPlayer method -> checkBlessings() */

    // player attempt on target's life costing a turn.


    if (!this.checkTargetNear()) {
//      System.out.println("*target is not near!");
//      System.out.println("target at room : " + this.target.getTargetLocationName());
      this.mansionBuilder.getTurnsMap().put(this.playerName, 0);
      this.mansionBuilder.getTarget().targetAutomove();
      this.mansionBuilder.getPet().dfsMove();
      this.setPlayerTurn(false);
      return this.mansionBuilder.getTargetHealth();

    } else if (this.checkPetInteference()){
//      System.out.println("*pet is interferencing");
      this.mansionBuilder.getTurnsMap().put(this.playerName, 0);
      this.mansionBuilder.getTarget().targetAutomove();
      this.mansionBuilder.getPet().dfsMove();
      this.setPlayerTurn(false);
      return this.mansionBuilder.getTargetHealth();

    }
    else if (this.seenPlayer()) { // if caught by other players, no damage done.
//      System.out.println("*pc attack seen by other players");
      this.mansionBuilder.getTurnsMap().put(this.playerName, 0);
      this.mansionBuilder.getTarget().targetAutomove();
      this.mansionBuilder.getPet().dfsMove();
      this.setPlayerTurn(false);
      return this.mansionBuilder.getTargetHealth();
    }
    else {
//      System.out.println("*attempt is hapeening now!!");
      int maxDamage = 1; // if the player doesn't have items(weapons), then poke.
      String currItem = null;
      int currDamage = 0;
      /* find item with the max damage the player had. */
      for (int i = 0; i < this.playerItemsLst.size(); i++) {
        currItem = this.playerItemsLst.get(i);
        if (currItem == null) {
          break;
        }
        currDamage = this.mansionBuilder.getItemsRoomMap().get(currItem);
        if (currDamage > maxDamage) {
          maxDamage = currDamage;
        }
      }

      /* excluding all other seen condition, and pet condition */
      /* now damage can be done! */
      int targetHealth = target.getTargetHealth();
//      System.out.println("current final damage is: " + maxDamage);
      targetHealth -= maxDamage;
      target.setTargetHealth(targetHealth);
//      System.out.println("target health after damage: " + targetHealth);
      /* add to the evidence set for item being used */
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

      this.mansionBuilder.getTurnsMap().put(this.playerName, 0);
      this.mansionBuilder.getTarget().targetAutomove();
      this.mansionBuilder.getPet().dfsMove();
      this.setPlayerTurn(false);
    }
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

    if (!this.checkTargetNear()) {
//      System.out.println("target is not near!");
      this.mansionBuilder.getTurnsMap().put(this.playerName, 0);
      this.mansionBuilder.getTarget().targetAutomove();
      this.mansionBuilder.getPet().dfsMove();
      this.setPlayerTurn(false);
      return this.mansionBuilder.getTargetHealth();

    } else if (this.checkPetInteference()){
//      System.out.println("pet is interferencing");
      this.mansionBuilder.getTurnsMap().put(this.playerName, 0);
      this.mansionBuilder.getTarget().targetAutomove();
      this.mansionBuilder.getPet().dfsMove();
      this.setPlayerTurn(false);
      return this.mansionBuilder.getTargetHealth();

    }
    else if (this.seenPlayer()) {
      this.mansionBuilder.getTurnsMap().put(this.playerName, 0);
      this.mansionBuilder.getTarget().targetAutomove();
      this.mansionBuilder.getPet().dfsMove();
      this.setPlayerTurn(false);
      return this.mansionBuilder.getTargetHealth();
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
    this.mansionBuilder.getTurnsMap().put(this.playerName, 0);
    this.mansionBuilder.getTarget().targetAutomove();
    this.mansionBuilder.getPet().dfsMove();
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
    this.mansionBuilder.getPlayersNameRoomMap().put(playerName, roomName);
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
    int roomIndex = this.mansionBuilder.getRoomNameIndexMap().get(playerRoom);
    /// what items in this room?
    String roomName = this.helperIndexGetRoomName(roomIndex);
    // TODOO: fix the null roomName
    ArrayList<String> allItems = this.helperRoomGetItems(roomName); /* here will get the items list for the room */
    /// can he pick it up?
    if (allItems != null &&
        (this.getPlayerTotalAllowedItem()
        - playerItemsLst.size()) > 0 && allItems.size() != 0 ) {
      playerItemsLst.add(allItems.get(0)); // always picked the 1st item in the list.
      this.mansionBuilder.getItemsRoomMap().remove(allItems.get(0)); // room's removed the item.
      this.mansionBuilder.getPlayersItemsMap().put(this.playerName, playerItemsLst); // add it to
                                                                                     // hashmap:
    }

    /* costing a TURN */
    this.mansionBuilder.getTurnsMap().put(this.playerName, 0);
    this.mansionBuilder.getTarget().targetAutomove();
    this.mansionBuilder.getPet().dfsMove();
    this.setPlayerTurn(false);

    // return the picked up item?
    if (allItems.isEmpty()) {
      return null; // if no items in the current room.
    }

    return allItems.get(0); // null means no items left in room.
  }

  // helper method to get an arraylist representation of player's items with items
  // corresponding damage points.
  @Override
  public ArrayList<String> getItemsDamagesLst() {
    ArrayList<String> allItems = this.getPlayerItemsLst();
    ArrayList<String> res = new ArrayList<>();
    for (String item : allItems) {
      int damage = this.mansionBuilder.getItem().getDamageAmount(item);
      String str = item + " -> " + damage + ". ";
      res.add(str);
    }
    return res;
  }

  @Override
  public boolean checkValidItem(String checkedItem) {
    boolean checker = false;
    for (String str : this.getPlayerItemsLst()) {
      if (checkedItem.equals(str)) {
        checker = true;
      }
    }
    return checker;
  }

  /**
   * a player to look around by displaying information about his position in the
   * world including neighboring space information, also indicate pet info also
   * indicate target into. This represents a turn. this COST A TURN.
   *
   * @return a string representation of the final info.
   */
  @Override
  public ArrayList<String> lookAround() {
    ArrayList<String> finalRes = new ArrayList<>();

    // 1. get all neighbor players room info.
    /* get current room */
    String currRoom = this.playerRoom;
    ArrayList<Player> playersReside = this.mansionBuilder.getAllPlayers();
    this.mansionBuilder.getPlayersNameRoomMap();

    /* put self info first */
    String selfInfo = String.format("You, %s, is at room %s, with items: %s.", this.getPlayerName(),
        this.getPlayerRoom(), this.getPlayerItemsLst().toString()); // add self info.
    finalRes.add(selfInfo);

    for (Player player : playersReside) { // add other players info.

      if (!player.getPlayerName().equals(this.playerName)){
        String playerInfo = String.format("The player, %s, is at room %s with items: %s.",
            player.getPlayerName(), player.getPlayerRoom(), player.getPlayerItemsLst().toString());
        finalRes.add(playerInfo);
      }

    }

    // 2-2 to also indicate if pets is nearing? (if so, indicate which room is
    // invisible. if no, just say no.)
    // : pet
    int petLoc = this.mansionBuilder.getPet().getPetLocation();

    /* make a neighborIndexLst. */
    ArrayList<Integer> neighborsIndexLst = new ArrayList<>();
    ArrayList<String> neighborRoomsLst = this.mansionBuilder.getRoom().getNeighbors(this.playerRoom);
    neighborRoomsLst.add(this.playerRoom);
    for (int i = 0; i < neighborRoomsLst.size(); i++) {
      neighborsIndexLst.add(this.mansionBuilder.getRoomNameIndexMap().get(neighborRoomsLst.get(i)));
    }
    boolean checkAddPet = false;
    for (int i = 0; i < neighborsIndexLst.size(); i++) {
      if (petLoc == neighborsIndexLst.get(i)) {
        String petRoom = this.pet.getPetLocationName();
        finalRes.add(String.format("The pet is nearing!!! The pet is at room: %s", petRoom));
        checkAddPet = true;
        break;
      }
    }
    if (!checkAddPet) {
      finalRes.add(
          "The pet is not anywhere in your visibility yet; it is not in any of the neighboring rooms.");
    }
    // 2-3 to also indicate if target is nearing?


    //TODO: check target impl
    int tarLoc = this.mansionBuilder.getTargetLocation();
    boolean checkAddTarget = false;
    for (int i = 0; i < neighborsIndexLst.size(); i++) {
      if (tarLoc == neighborsIndexLst.get(i)) {
        finalRes.add(String.format("The Target is nearing!!! The target is at room: %s",
            this.target.getTargetLocationName()));
        finalRes.add(
            String.format("The Target's current health is: %d", this.target.getTargetHealth()));
        checkAddTarget = true;
      }
    }
    if (!checkAddTarget) {
      finalRes.add(
          "The Target is not anywhere in your visibility yet; it is not in any of the neighboring rooms.");
    }
    /* costing a TURN */
    this.mansionBuilder.getTurnsMap().put(this.playerName, 0);
    this.mansionBuilder.getTarget().targetAutomove(); // note: check this right?
    this.mansionBuilder.getPet().dfsMove(); // note: check this right?
    this.setPlayerTurn(false);

    return finalRes;
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
    String neighborRoom = roomPicked;
    // update hashmaps:
    int neighborIndex = this.mansionBuilder.getRoomNameIndexMap().get(neighborRoom);
    this.playerRoom = this.helperIndexGetRoomName(neighborIndex); // to update player's current room
    this.updatePlayerRoomInfo(playerRoom);
    this.setPlayerTurn(false);
    // return the moved to room index?
    /* costing a TURN */
    this.mansionBuilder.getTurnsMap().put(this.playerName, 0);
    this.mansionBuilder.getTarget().targetAutomove();
    this.pet.dfsMove(); // move player
    this.setPlayerTurn(false);
    return neighborIndex;
  }

  @Override
  public boolean checkValidRoom(String checkedRoom) { // : put this check condition in the driver.
    ArrayList<String> playerNeighbors = this.mansionBuilder.getAllNeighborsMap()
        .get(this.playerRoom);
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
  @Override
  public ArrayList<String> helperNeighborRooms() {
    ArrayList<String> res = this.mansionBuilder.getAllNeighborsMap().get(this.getPlayerRoom());
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
//    System.out.println("player nameroommap: " + this.mansionBuilder.getPlayersNameRoomMap());
    String roomStr = this.mansionBuilder.getPlayersNameRoomMap().get(playerName);
    // what they carry 2.
    // TODOO: fix null input roomStr: so maybe use the a checker too here.
//    System.out.println("current room is: " + roomStr);
//    System.out.println("map is: " + this.mansionBuilder.getRoomNameIndexMap());
    ArrayList<String> allTheItems = this.helperRoomGetItems(roomStr);
    String itemsStr = allTheItems.toString();

    String ans = String.format("The player is at room: %s. The item(s) in the room: %s", roomStr,
        itemsStr);
    return ans;
  }

  /**
   * check how many more items a player can still pick up.
   *
   * @return the number of more items a player can pick up.
   */
  @Override
  public int checkPickMore() {
    int total = this.mansionBuilder.getTotalItemsAllowedMap().get(this.playerName);
    int size = this.playerItemsLst.size();
    return total - size;
  }

  /**
   * flip the turns: 1 -> 0 0 -> 1.
   *
   * @return an integer representation as true (1) or false (0)
   */
  @Override
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
  @Override
  public int helperRandNum(int i) {
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
  @Override
  public String helperIndexGetRoomName(int index) {
    for (String str : this.mansionBuilder.getRoomNameIndexMap().keySet()) {
      if (this.mansionBuilder.getRoomNameIndexMap().get(str) == index) {
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
  @Override
  public ArrayList<String> helperRoomGetItems(String room) { // room get items
    ArrayList<String> arrLst = new ArrayList<>();
    if (room == null) {
      return null; // if the room doesn't exist.
    }
    int roomIndex = this.mansionBuilder.getRoomNameIndexMap().get(room);
    for (String item : this.mansionBuilder.getItemsRoomMap().keySet()) {
      if (roomIndex == this.mansionBuilder.getItemsRoomMap().get(item)) {
        arrLst.add(item);
      }
    }
    return arrLst;
  }

  @Override
  public String helperArrayListToString(ArrayList<String> arrLst) {
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
  @Override
  public boolean checkTurnsMap() {
    if (this.mansionBuilder.getTurnsMap().get(this.playerName) == 1) {
      return true;
    } else if (this.mansionBuilder.getTurnsMap().get(this.playerName) == 0) {
      return false;
    }
    return false;
  }

  @Override
  public boolean isPlayerTurn() {
    return playerTurn;
  }

  // getters and setters

  public int getPlayerTotalAllowedItem() {
    return playerTotalAllowedItem;
  }

  public String getPlayerRoom() {
    return playerRoom;
  }

  public ArrayList<String> getPlayerItemsLst() {
    return playerItemsLst;
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

  public Set<String> getEvidenceSet() {
    return evidenceSet;
  }

  // newly added method:

  @Override
  public void setPlayerTotalAllowedItem(int playerTotalAllowedItem) {
    this.playerTotalAllowedItem = playerTotalAllowedItem;
    //update hashmap:
    this.mansionBuilder.getTotalItemsAllowedMap().put(this.playerName, playerTotalAllowedItem);
    // Note: update hashmap too

  }

  @Override
  public void setComputerOrHuman(String computerOrHuman) {
    this.computerOrHuman = computerOrHuman;
  }

  @Override
  public void setPlayerName(String playerName) {
    String prevRoom = this.mansionBuilder.getPlayersNameRoomMap().get(this.playerName);
    this.mansionBuilder.getPlayersNameRoomMap().remove(this.playerName);
    this.playerName = playerName;
    this.mansionBuilder.getPlayersNameRoomMap().put(this.playerName, prevRoom);
    // Note: update hashmap too
  }

  @Override
  public void setPlayerRoom(String playerRoom) {
    this.playerRoom = playerRoom;
    // Note: update hashmap too
    this.mansionBuilder.getPlayersNameRoomMap().remove(this.playerName);
    this.mansionBuilder.getPlayersNameRoomMap().put(this.playerName, this.playerRoom);

  }

  @Override
  public int getPlayerRoomIndex() {
    String room = this.playerRoom;
    return this.mansionBuilder.getRoomNameIndexMap().get(room);
  }



} // end of Player.java
