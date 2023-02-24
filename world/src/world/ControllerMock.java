package world;

import java.io.*;
import java.util.*;

public class ControllerMock {
 /**
  * declare fields & mansion, player objects
  */
 private final Readable in;
 private final Appendable out;
 MansionMockModel m;
 Player playerMock1_Sam;
 Player playerMock2_Amy;
 private ArrayList<Player> allPlayersLst;
// private ArrayList<String> moves_player1;
// private ArrayList<String> moves_player2;
 private ArrayList<String> moves_lst;
 private ArrayList<String> namesLookAroundLst;

 /**
  * Constructor: initialize mansion and player objects.
  *
  * @param in  an InputStream prompts inputs from user.
  * @param out an OutputStream that appends game logs.
  */
 public ControllerMock(Readable in, Appendable out) {
  /*initialize Readable and Appendable.*/
  if (in == null || out == null) {
   throw new IllegalArgumentException("Readable and Appendable can't be null");
  }
  this.in = in;
  this.out = out;

  /*initialize mock model*/
  this.m = new MansionMockModel();



  /*assigning players moves: move, pick, look around, display */
  this.moves_lst = new ArrayList<>();
  moves_lst.add("move"); //sam
  moves_lst.add("move"); //amy
  moves_lst.add("look around"); //amy
  moves_lst.add("move"); //amy
  moves_lst.add("move"); //sam
  moves_lst.add("look around"); //amy
  moves_lst.add("look around"); //amy
  moves_lst.add("look around"); //amy
  moves_lst.add("pick"); //sam
  moves_lst.add("pick"); //amy
  moves_lst.add("display"); //
  moves_lst.add("move"); ///
  moves_lst.add("display"); //
  moves_lst.add("pick"); //sam
  moves_lst.add("display"); //
  moves_lst.add("move"); //


//  moves_lst.add("quite");
/*names list for look around option */
  this.namesLookAroundLst = new ArrayList<>();
  this.namesLookAroundLst.add("Amy");
  this.namesLookAroundLst.add("Sam");
  this.namesLookAroundLst.add("Amy");
  this.namesLookAroundLst.add("Sam");
  this.namesLookAroundLst.add("Sam");
  this.namesLookAroundLst.add("Amy");
  this.namesLookAroundLst.add("Sam");



 } //end of constructor.

 /**
  * play the world game.
  *
  * @throws IOException for file handles.
  */
 public void playGame(MansionMockModel m) throws IOException {
  Objects.requireNonNull(m);
  this.m = m;
  Scanner scanController = new Scanner(in);
  //  scanController.close();

  /*first, init. Mansion */
  out.append("playgame() started.\n");
  String worldInput = "C:\\Users\\dongpingchen\\Documents\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt";
  File file = new File(worldInput);
  out.append("File path entered correctly.\n");
  try {
   // Creates a reader using the FileReader
   FileReader input = new FileReader(file);
   out.append("FileReader read this file.");
   m.readFile(input);
   out.append("Mock model read this file");
   input.close();
  } catch (Exception e) {
   e.getStackTrace();
  }
  m.drawWorld();
  out.append("draw the Mansion.\n");

  /*initialize mock players*/
  this.allPlayersLst = m.getAllPlayers();
  this.playerMock1_Sam = new Player(m.getRoomNameIndexMap(), m.getTotalItemsAllowedMap(),
      m.getItemsDamageMap(), m.getTargetHealth(), m.getTargetLocation(), true, "human", "Sam",
      "Fate", 5, new ArrayList<>(), this.m.getPlayersTargetNameRoomMap(),
      this.m.getPlayersItemsMap(), this.m.getItemsRoomMap(), this.m.getTurnsMap());
  this.allPlayersLst.add(playerMock1_Sam);
  this.playerMock2_Amy = new Player(m.getRoomNameIndexMap(), m.getTotalItemsAllowedMap(),
      m.getItemsDamageMap(), m.getTargetHealth(), m.getTargetLocation(), true, "human", "Amy",
      "The Enlightenment", 4, new ArrayList<>(), this.m.getPlayersTargetNameRoomMap(),
      this.m.getPlayersItemsMap(), this.m.getItemsRoomMap(), this.m.getTurnsMap());
  this.allPlayersLst.add(playerMock2_Amy);

  out.append("finished initializing players.");
  System.out.println("finished initializing players.");





  //1. first line: an integer N (declaring how many players for this game).
  int totalPlayers = this.allPlayersLst.size();
  out.append(totalPlayers + " players for this game.\n");
  System.out.println(totalPlayers + " players for this game.\n");

  //2. next N lines: each line represent each players information:

  for (int i = 0; i < totalPlayers; i++) { //next N lines for players info.
   Player currPlayer = allPlayersLst.get(i);

   String typeStr = currPlayer.getComputerOrHuman();
   out.append("A " + typeStr + " player just entered this game.\n");
//   System.out.println("A " + typeStr + " player just entered this game.\n");

   String playerNameStr = currPlayer.getPlayerName();
   out.append("PlayerName: \n" + playerNameStr);
//   System.out.println("PlayerName: \n" + playerNameStr);

   String roomNameStr = currPlayer.getPlayerRoom();
   out.append("Player initial room: \n" + roomNameStr);
//   System.out.println("Player initial room: \n" + roomNameStr);

   int itemsAmountAllowed = currPlayer.getPlayerTotalAllowedItem();
   out.append("Total items allowed for this player: \n" + itemsAmountAllowed);
//   System.out.println("Total items allowed for this player: \n" + itemsAmountAllowed);
   //*note: assume the players not poccessing items when first dropped into the rooms.

   out.append(String.format("%s is successfully added to this Mansion.\n", playerNameStr));
//   System.out.println(String.format("%s is successfully added to this Mansion.\n", playerNameStr));
  }//end of for loop.

  String input = "";
  int track_moves = 0;
  int track_names = 0;
  boolean exit = true;
  while (exit) { ///from here the scan.next should only have 'move actions'
   for (int i = 0; i < totalPlayers; i++) { //start iterating over each readline()
    Player currPlayer = allPlayersLst.get(i);
     while (currPlayer.getPlayerTurn()) {
      System.out.println("curr player is :" + currPlayer.getPlayerName());
      if (currPlayer.getComputerOrHuman().equals("human")) { //a human player
       out.append("Human Player, " + currPlayer.getPlayerName() + " ,is having this turn and picking the move.");
       System.out.println("Human Player, " + currPlayer.getPlayerName() + " ,is having this turn and picking the move.");
       if (track_moves == moves_lst.size()) {
        exit = false;
        break;
       }
       input = moves_lst.get(track_moves++); //for example， move
        System.out.println("the switch input is " + input);
       switch (input) {
        //===================Methods Cost a turn:
        case "move": /*int movePlayer()*/
//         System.out.println(String.format("%s is trying to move to another room.\n", currPlayer.getPlayerName()));
//         System.out.println("player room:  " + currPlayer.getPlayerRoom());
//         System.out.println("ItemsRoomMap: "+currPlayer.getItemsRoomMap());

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
         currPlayer.lookAround(this.namesLookAroundLst.get(track_names++), m.getAllNeighborsMap());
         this.out.append(String
             .format("%s is looking around on another player.\n", currPlayer.getPlayerName()));
         //flip its boolean turn value:
         currPlayer.flipTurn(); //now val is false.
         currPlayer.setPlayerTurn(false); //now val is false.
         break;

        //=========Methods Dont cost a turn:
        case "display": /*String displayPlayerInfo(String playerName)*/
         System.out.println("11Please input the name of the player you want to display for: ");
         currPlayer.displayPlayerInfo(this.namesLookAroundLst.get(track_names++));
         this.out.append(
             String.format("%s is trying to pick up an item.\n", currPlayer.getPlayerName()));
         break;

        case "quit":
         this.out.append("game has just ended.");
         System.out.println("game ended.");
         break;

       }


      } else if (currPlayer.getComputerOrHuman().equals("computer")) { //a PC player
       out.append("Computer Player, " + currPlayer.getPlayerName() + " ,is having the turn and picking the move.");
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
         break;

        case "pick": /*String pickUp()*/
         this.out.append(
             String.format("%s is trying to pick up an item. \n", currPlayer.getPlayerName()));
         //flip its boolean turn value:
         currPlayer.flipTurn(); //now val is false.
         currPlayer.setPlayerTurn(false); //now val is false.
         break;

        case "look around": /*String lookAround(String playerName)*/
         this.out.append(String
             .format("%s is looking around on another player.\n", currPlayer.getPlayerName()));
         //flip its boolean turn value:
         currPlayer.flipTurn(); //now val is false.
         currPlayer.setPlayerTurn(false); //now val is false.
         break;
        // default:


       }
      }
     }//end while(current players turn).
   }//end for() -> finished one round.

  //TODO: 2nd part (get players to play)
   /* flip turns all back to 'true' after each round fnishing. */
       for (int j = 0; j < totalPlayers; j++) {
        this.allPlayersLst.get(j).flipTurn(); //flip them all back to true.
        this.allPlayersLst.get(j).setPlayerTurn(true);
       }
    this.out.append("One round of game has been finished.");
  }//end of while(true) loop -> game ends.
 } //end of playGame().

 //===============================helper functions====================================

 /**
  * helper function:
  * generate a random integer value from 0 to 'j'
  *
  * @param j the upper limit of the value generated.
  */
 private int helperRandNum(int j) {
  //random generator:
  Random ran = new Random();
  return ran.nextInt(j);
 }

 /**
  * get the string representation of moves based on integer values.
  *
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


