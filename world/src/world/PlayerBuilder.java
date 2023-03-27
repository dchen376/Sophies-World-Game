package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * this is the interface for the player object.
 */
public interface PlayerBuilder {

  /**
   * check if any other players are in the same room or neighboring rooms.
   * @return true if yes, false if no neighboring players.
   */
  boolean seenPlayer();

  /**
   * move the pet to a specific room
   * @param roomIndex the room index it is about to be moved to
   * @param pet to be moved.
   * @return the room name it moved to.
   */
  String movePet(int roomIndex, Pet pet);

  /**
   * newly added methods for milestone 3: gamePlay. players tries to attempt on
   * the target's life to kill it.
   * @param target be attempted
   * @param itemUsed item used for the attack
   * @return the target's final health
   */
  int attemptTarget(Target target, String itemUsed);

  /**
   * the computer's attempt on the target if not seen.
   * @param target be attempted
   * @return the target's final health.
   */
  int pcAttemptTarget(Target target);





  /**
   * to update the player's information.
   * 
   * @param roomName the name of the room.
   */
  void updatePlayerRoomInfo(String roomName);

  /**
   * to pick up an item in the room.
   * 
   * @return the picked item's name, as a type String.
   */
  String pickUp();

  /**
   * the player who is about to 'look Around' at his current location.
   * 
   * @param playerName the name of the player performing the action.
   * @return the lookAround result, as a type of String.
   */
  String lookAround(String playerName, HashMap<String, ArrayList<String>> allNeighborsMap);

  /**
   * move the player.
   * 
   * @param allNeighborsMap
   * @return
   */
  int movePlayer(HashMap<String, ArrayList<String>> allNeighborsMap);

  /**
   * Display the player's information.
   * 
   * @param playerName the name of the player to be displayed.
   * @return the result, as as type of String.
   */
  String displayPlayerInfo(String playerName);

  /**
   * automatically move the target during each turn.
   * 
   * @return the room index it moved to, as a type of string.
   */
  int autoMoveTarget();

  /**
   * check if a player can pick more items.
   * 
   * @return the number of more items a player can pick up.
   */
  int pickMoreItems();

  /**
   * flip the turns: 1 -> 0 0 -> 1.
   *
   * @return an integer representation as true (1) or false (0)
   */
  int flipTurn();

}
