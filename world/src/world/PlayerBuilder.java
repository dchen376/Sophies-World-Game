package world;

/**
 * this is the interface for the player object.
 */
public interface PlayerBuilder {

  /**
   * to update the player's information.
   * @param roomName the name of the room.
   */
  void updatePlayerRoomInfo(String roomName);

  /**
   * to move the player.
   * @return the room index it moved to, as a type of int.
   */
  int movePlayer();

  /**
   * to pick up an item in the room.
   * @return the picked item's name, as a type String.
   */
  String pickUp();

  /**
   * the player who is about to 'look Around' at his current location.
   * @param playerName the name of the player performing the action.
   * @return the lookAround result, as a type of String.
   */
  String lookAround(String playerName);

  /**
   * Display the player's information.
   * @param playerName the name of the player to be displayed.
   * @return the result, as as type of String.
   */
  String displayPlayerInfo(String playerName);

  /**
   * automatically move the target during each turn.
   * @return the room index it moved to, as a type of string.
   */
  int autoMoveTarget();

}
