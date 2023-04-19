//package model.player;
//
///**
// * this is the interface for the player object.
// */
//public interface PlayerBuilderMock {
//
// /**
//  * this is mainly a helper function for movePlayer().
//  * to check if the room is a valid neighboring room.
//  * @param checkedRoom the room to be checked
//  * @return true if it is one of the neighboring room.
//  */
// boolean checkValidRoom(String checkedRoom);
//
//
//
// // helper method for granting a player's invisibility for
//
// /**
//  * check if a player has the blessings from the pet.
//  * @return true if the player has the pet's blessings.
//  */
// boolean checkBlessings();
//
// /**
//  * check if any other players are in the same room or neighboring rooms.
//  * @return true if yes, false if no neighboring players.
//  */
// boolean seenPlayer();
//
// /**
//  * move the pet to whoever the player is calling this method; to the player's room.
//  * @return the room name it moved to.
//  */
// String movePet();
//
// /**
//  * newly added methods for milestone 3: gamePlay. players tries to attempt on
//  * the target's life to kill it.
//  * @param itemUsed item used for the attack
//  * @return the target's final health
//  */
// int attemptTarget(String itemUsed);
//
// /**
//  * the computer's attempt on the target if not seen.
//  * @return the target's final health.
//  */
// int pcAttemptTarget();
//
//
//
//
//
// /**
//  * to update the player's information.
//  *
//  * @param roomName the name of the room.
//  */
// void updatePlayerRoomInfo(String roomName);
//
// /**
//  * to pick up an item in the room.
//  *
//  * @return the picked item's name, as a type String.
//  */
// String pickUp();
//
// /**
//  * the player who is about to 'look Around' at his current location.
//  *
//  * @param playerName the name of the player performing the action.
//  * @return the lookAround result, as a type of String.
//  */
// String lookAround();
//
// /**
//  * move the player.
//  *
//  * @param roomPicked
//  * @return
//  */
// int movePlayer(String roomPicked);
//
// /**
//  * Display the player's information.
//  *
//  * @param playerName the name of the player to be displayed.
//  * @return the result, as as type of String.
//  */
// String displayPlayerInfo(String playerName);
//
// /**
//  * automatically move the target during each turn.
//  *
//  * @return the room index it moved to, as a type of string.
//  */
// int autoMoveTarget();
//
// /**
//  * check if a player can pick more items.
//  *
//  * @return the number of more items a player can pick up.
//  */
// int pickMoreItems();
//
// /**
//  * flip the turns: 1 -> 0 0 -> 1.
//  *
//  * @return an integer representation as true (1) or false (0)
//  */
// int flipTurn();
//
//}
