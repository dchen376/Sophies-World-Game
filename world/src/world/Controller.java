package world;

import java.io.*;
import java.util.*;

public class Controller {
 /**
  * declare fields.
  */
// private Appendable out;
// private Scanner scanController;
 private final Readable in;
 private final Appendable out;
 Mansion m;

 /**
  * Constructor
  * @param in an InputStream prompts inputs from user.
  * @param out an OutputStream that appends game logs.
  */
 public Controller(Readable in, Appendable out) {
  if (in == null || out == null) {
   throw new IllegalArgumentException("Readable and Appendable can't be null");
  }
  this.in = in;
  this.out = out;

 } //end of constructor.

 /**
  * play the world game.
  * @throws IOException for file handles.
  */
 public void playGame(Mansion m) throws IOException {
  Objects.requireNonNull(m);
  this.m = m;
  Scanner scanController = new Scanner(in);
//  scanController.close();

  /*first, init. Mansion */
  System.out.println("First, we will test the Mansion class!!");
  System.out.println("Please enter the text file path to read: ");
  String worldInput = scanController.nextLine();
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

  /*second, handle the players*/
  //1. first line: an integer N (declaring how many players for this game).
  ///for example, 2
  ArrayList<Player> allPlayers = m.getAllPlayers();
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

   System.out.println("Please input one player's name: ");
   String playerNameStr = scanController.nextLine();
   out.append("PlayerName: " + playerNameStr);
   System.out.println("Please input this player's initial room name: ");
   String roomNameStr = scanController.nextLine();
   out.append("Player initial room: " + roomNameStr);

   System.out.println("Please input the total numbers of items allowed for this player:");
   String itemsAmountAllowedStr = scanController.nextLine();
   out.append("Total items allowed for this player: " + itemsAmountAllowedStr + '\n');
   //*note: assume the players not poccessing items when first dropped into the rooms.

   /*adding attributes to one player instance*/
   Player player = new Player(m.getRoomNameIndexMap(), m.getTotalItemsAllowedMap(),
       m.getItemsDamageMap(), m.getTargetHealth(), m.getTargetLocation(), true,
       // default player turn is TRUE
       typeStr, playerNameStr, roomNameStr, Integer.parseInt(itemsAmountAllowedStr),
       new ArrayList<>(),//player items list?
       m.getPlayersTargetNameRoomMap(), m.getPlayersItemsMap(), m.getItemsRoomMap(),
       m.getTurnsMap());
   allPlayers.add(player); //add one player to the 'allPlayers' list.
   out.append(String.format("%s is successfully added to this Mansion.", playerNameStr));

   //   /*TODO: print outs player info before proceeding*/
   //   System.out.println("==================================================================");
   //   System.out.println(m.getRoomNameIndexMap());
   //   System.out.println(m.getTotalItemsAllowedMap());
   //   System.out.println(m.getItemsDamageMap());
   //   System.out.println(m.getTargetHealth());
   //   System.out.println(m.getTargetLocation());
   //   System.out.println(true);
   //   System.out.println(typeStr);
   //   System.out.println(playerNameStr);
   //   System.out.println(roomNameStr);
   //   System.out.println(itemsAmountAllowedStr);
   //   System.out.println("empty array list first!");
   //   System.out.println(m.getPlayersTargetNameRoomMap()); // update each time the players/target move
   //   //TODO: supposed to be null first until picking up an item
   //   System.out.println(m.getPlayersItemsMap()); //*just 'put' new items into this arrlst
   //   System.out.println(m.getItemsRoomMap());
   //   //TODO: supposed to be empty at first. Update each pick() / move():
   //   System.out.println(m.getTurnsMap()); //defaults are true -> 1.
   //   System.out.println("size of allPlayers list is: " + allPlayers.size());
   //   System.out.println("==================================================================");

  }//end of for loop.

  String input = "";
  while (true) { ///from here the scan.next should only have 'move actions'
   for (int i = 0; i < totalPlayers; i++) { //start iterating over each readline()
    Player currPlayer = allPlayers.get(i);
    if (currPlayer.getPlayerTurn()) { // if his turn is -> true.
     while (currPlayer.getPlayerTurn()) {
      if (currPlayer.getComputerOrHuman().equals("human")) { //a human player
       System.out
           .println("Please input the action you want for player: " + currPlayer.getPlayerName());
       System.out.println("Available moves for human players: move, pick, look around, display.");
       input = scanController.nextLine(); //for example， move
       switch (input) {
        //===================Methods Cost a turn:
        case "move": /*int movePlayer()*/
         currPlayer.movePlayer(m.getAllNeighborsMap());
         this.out.append(
             String.format("%s is trying to move to another room.\n", currPlayer.getPlayerName()));
         //flip its boolean turn value:
         currPlayer.flipTurn(); //now val is false.
         currPlayer.setPlayerTurn(false); //now val is false.

         break;
        case "pick": /*String pickUp()*/
         currPlayer.pickUp();
         this.out.append(
             String.format("%s is trying to pick up an item. \n", currPlayer.getPlayerName()));
         //flip its boolean turn value:
         currPlayer.flipTurn(); //now val is false.
         currPlayer.setPlayerTurn(false); //now val is false.

         break;
        case "look around": /*String lookAround(String playerName)*/
         System.out.println("Please input the name of the player you want to look around for: ");
         input = scanController.nextLine();
         currPlayer.lookAround(input, m.getAllNeighborsMap());
         this.out.append(String
             .format("%s is looking around on another player.\n", currPlayer.getPlayerName()));
         //flip its boolean turn value:
         currPlayer.flipTurn(); //now val is false.
         currPlayer.setPlayerTurn(false); //now val is false.

         break;

        //=========Methods Dont cost a turn:
        case "display": /*String displayPlayerInfo(String playerName)*/
         System.out.println("Please input the name of the player you want to display for: ");
         input = scanController.nextLine();
         currPlayer.displayPlayerInfo(input);
         this.out.append(
             String.format("%s is trying to pick up an item.\n", currPlayer.getPlayerName()));
         System.out.println(currPlayer.displayPlayerInfo(input));
         break;
       }
      } else if (currPlayer.getComputerOrHuman().equals("computer")) { //a PC player
       /*moves for PC:
        * 1. move() -> costs Turn!
        * 2. pickup() -> costs Turn!
        * 3. lookAround() -> costs Turn!
        * */

       System.out.println("Now, it's the computer player's move");
       int move = this.helperRandNum(2);
       input = this.helperGetComputerMove(move);
       switch (input) { //for a computer
        case "move": /*int movePlayer()*/
         currPlayer.movePlayer(m.getAllNeighborsMap());
         this.out.append(
             String.format("%s is trying to move to another room.\n", currPlayer.getPlayerName()));
         //flip its boolean turn value:
         currPlayer.flipTurn(); //now val is false.
         currPlayer.setPlayerTurn(false); //now val is false.

         System.out.println("Computer player just executed 'move'. ");
         break;
        case "pick": /*String pickUp()*/
         this.out.append(
             String.format("%s is trying to pick up an item. \n", currPlayer.getPlayerName()));
         //flip its boolean turn value:
         currPlayer.flipTurn(); //now val is false.
         currPlayer.setPlayerTurn(false); //now val is false.

         System.out.println("Computer player just executed 'pick'. ");
         break;
        case "look around": /*String lookAround(String playerName)*/
         this.out.append(String
             .format("%s is looking around on another player.\n", currPlayer.getPlayerName()));
         //flip its boolean turn value:
         currPlayer.flipTurn(); //now val is false.
         currPlayer.setPlayerTurn(false); //now val is false.

         System.out.println("Computer player just executed 'look'. ");
         break;

        //       default:

       }
      }
      if (i + 1 == totalPlayers) { //just finished a round .
       for (int j = 0; j < totalPlayers; j++) {
        allPlayers.get(j).flipTurn(); //flip them all back to true.
        allPlayers.get(j).setPlayerTurn(true);
       }

      }//finished of round checking.
     }//end if: check if it's a certain player's turn.
    }
    System.out.println("One round has just finished");
    this.out.append("Just finished one round.");
   }//end of for-loop: found whose turn is it, and execute certain action from readline.
  }//end of while(true) loop.
 } //end of playGame().

 //===============================helper functions====================================

 /**
  * helper function:
  *generate a random integer value from 0 to 'j'
  * @param j the upper limit of the value generated.
  */
 private int helperRandNum(int j) {
  //random generator:
  Random ran = new Random();
  return ran.nextInt(j);
 }

 /**
  * get the string representation of moves based on integer values.
  * @param j upper limit of the random value.
  * @return String the move a player executed.
  */
 private String helperGetComputerMove(int j) {
  if (j == 0)
   return "move";
  if (j == 1)
   return "pick";
  if (j == 2)
   return "look";
  return "wrong answer";
 }
} //end of HumanController!


