package world;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Controller {
 private Appendable out;
 private Scanner scanController;
 private Scanner scanWorld;

 /**
  * Constructor for the controller.
  *
  * @param ControllerIn the source to read from
  * @param out          the target to print to
  * @param worldIn
  */
 public Controller(Readable worldIn, Readable ControllerIn, Appendable out) {
  if (ControllerIn == null || out == null || worldIn == null) {
   throw new IllegalArgumentException("Readable and Appendable can't be null");
  }
  this.out = out;
  this.scanWorld = new Scanner(worldIn);
  this.scanController = new Scanner(ControllerIn);

 } //end of constructor.

 //main controller method
 public void playGame() throws IOException {

  /*first, init. Mansion */
  Mansion m = new Mansion();
  System.out.println("First, we will test the Mansion class!!");
  System.out.println("Please enter the text file path to read: ");
  String worldInput = scanWorld.nextLine();
  File file = new File(worldInput);
  try {
   // Creates a reader using the FileReader
   FileReader input = new FileReader(worldInput);
   //Reads the 'readable'
   m.readFile(input);
   //Closes the reader
   input.close();
  } catch (Exception e) {
   e.getStackTrace();
  }
  m.drawWorld();

  /*TODO: implement the 2nd part players handling (adding in the game)*/
  /*second, handle the players*/
  //1. first line: an integer N (declaring how many players for this game).
  ///for example, 2
  ArrayList<Player> allPlayers = new ArrayList<>();
  System.out.println("How many players are here for the game?");
  String totalPlayersStr = scanController.nextLine(); //READ 1ST LINE: 2
  int totalPlayers = Integer.parseInt(totalPlayersStr);
  out.append(totalPlayersStr + '\n');

  //2. next N lines: each line represent each players information:
  ///line1(x4): h playerA, The Top Hat, 5(total items allowed)
  ///line2 (x4) : c(if it's just a pc) computerA, 3(room), 3(total items allowed)
  for (int i = 0; i < totalPlayers; i++) { //next N lines for players info.
   System.out.println("Is this a human or a computer: ");
   String typeStr = scanController.nextLine();
   out.append("ComputerORHuman: " + typeStr);
//   String[] strSplit = input.split(", ");
//   ArrayList<String> strList = new ArrayList<String>(Arrays.asList(strSplit));
//   String type = strList.get(0); //h
   //   op.getComputerOrHumanLst().add(type.charAt(0));//put 'type' into the lst

   System.out.println("Please input one player's name: ");
   String playerNameStr = scanController.nextLine();
   //   m.getPlayersNameLst().add(name);
   out.append("PlayerName: " + playerNameStr);

   //   m.getPlayersNameRoomMap().put(name, room);
   //   m.getPlayersRoomNamesLst().add(room);
   System.out.println("Please input this player's initial room name: ");
   String roomNameStr= scanController.nextLine();
   out.append("Player initial room: " + roomNameStr);

   System.out.println("Please input the total numbers allowed for this player:");
   String itemsAmountAllowedStr = scanController.nextLine();
   out.append("Total items allowed for this player: " + itemsAmountAllowedStr + '\n');
   //*note: assume the players not poccessing items when first dropped into the rooms.

   /*adding attributes to one player instance*/
   Player player = new Player(m.getRoomNameIndexMap(), m.getTotalItemsAllowedMap(),
       m.getItemsDamageMap(), m.getAllNeighborsMap(),//vals 2from mansion
       m.getTargetHealth(), m.getTargetLocation(), //target info from mansion
       true, //player default boolean turn value is true.
       typeStr, //human or computer player
       playerNameStr, roomNameStr,Integer.parseInt(itemsAmountAllowedStr),
           new ArrayList<>(), //empty arrlist is for the items each player possess
       //initialize all hashmaps that need be updating constantly after each moves from players:
       m.getPlayersTargetNameRoomMap(), // HashMap<String, String> playersTargetNameRoomMap
       m.getPlayersItemsMap(), //HashMap<String, ArrayList<String>> playersItemsMap
       m.getItemsRoomMap(), //HashMap<String, Integer> itemsRoomMap
       m.getTurnsMap() // HashMap<String,Integer> turnsMap
   );
   allPlayers.add(player); //add one player to the 'allPlayers' list.
   out.append(String.format("%s is successfully added to this Mansion.", playerNameStr));
  }//end of for loop.


String input = "";

  /*  TODO 3rd. read the rest 'move actions':*/
  while (true) { ///from here the scan.next should only have 'move actions'
   if (!scanController.hasNextLine()) {
    break; //break the while loop, game IS DONE!!
   } else {
    //note: each line represents an action.
     input = scanController.nextLine(); //for example， move
   }

   //   int i = 0;//to track the players turn.
   for (int i = 0; i < totalPlayers; i++) { //start iterating over each readline()
    if (allPlayers.get(i).getTurn()) { // if his turn is -> true.
      Player currPlayer = allPlayers.get(i);
     if (currPlayer.getComputerOrHuman().equals("human")) { //a human player
      switch (input) {
       //===================Methods Cost a turn:
       case "move": /*int movePlayer()*/
        currPlayer.movePlayer();
        this.out.append(
            String.format("%s is trying to move to another room.\n", currPlayer.getPlayerName()));
        //flip its boolean turn value:
        currPlayer.flipTurn(); //now val is false.
        break;
       case "pick": /*String pickUp()*/
        this.out.append(
            String.format("%s is trying to pick up an item. %d\n", currPlayer.getPlayerName()));
        //flip its boolean turn value:
        currPlayer.flipTurn(); //now val is false.
        break;
       case "look": /*String lookAround(String playerName)*/
        this.out.append(String
            .format("%s is looking around on another player.%d\n", currPlayer.getPlayerName()));
        //flip its boolean turn value:
        currPlayer.flipTurn(); //now val is false.
        break;

       //=========Methods Dont cost a turn:
       case "update": /*void updatePlayerRoomInfo(String roomName)*/
        this.out.append(
            String.format("%s updated the p. %d\n", currPlayer.getPlayerName()));

        break;
       case "display": /*String displayPlayerInfo(String playerName)*/
        this.out.append(
            String.format("%s is trying to pick up an item. %d\n", currPlayer.getPlayerName()));

        break;
      }
     }
     else if (currPlayer.getComputerOrHuman().equals("computer")) { //a PC player
      /*moves for PC:
       * 1. move() -> costs Turn!
       * 2. pickup() -> costs Turn!
       * 3. lookAround() -> costs Turn!
       * */
      switch (input) {
       //===================Methods Cost a turn:
       case "move": /*int movePlayer()*/
        currPlayer.movePlayer();
        this.out.append(
            String.format("%s is trying to move to another room.\n", currPlayer.getPlayerName()));
        //flip its boolean turn value:
        currPlayer.flipTurn(); //now val is false.
        break;
       case "pick": /*String pickUp()*/
        this.out.append(
            String.format("%s is trying to pick up an item. %d\n", currPlayer.getPlayerName()));
        //flip its boolean turn value:
        currPlayer.flipTurn(); //now val is false.
        break;
       case "look": /*String lookAround(String playerName)*/
        this.out.append(String
            .format("%s is looking around on another player.%d\n", currPlayer.getPlayerName()));
        //flip its boolean turn value:
        currPlayer.flipTurn(); //now val is false.
        break;
      }
     }
     if (i + 1 == totalPlayers) { //just finished a round .
      for (int j = 0; j < totalPlayers; j++) {
       allPlayers.get(j).flipTurn(); //flip them all back to true.
      }
     }//finished of round checking.
    }//end if: check if it's a certain player's turn.

   }//end of for-loop: found whose turn is it, and execute certain action from readline.

  }//end of while(true) loop.

 } //end of playGame().

 //===============================helper functions====================================

} //end of HumanController!


