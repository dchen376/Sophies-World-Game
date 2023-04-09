package controller;

import model.mansion.MansionMockModel;
import model.player.PlayerMock;

import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * this is used for mocking testing for the method playGame().
 */
public class ControllerMock {
 /**
  * declare fields & mansion, player objects
  */
 private final Readable in;
 private final Appendable out;
 MansionMockModel m;
 PlayerMock playerMock1_Sam;
 PlayerMock playerMock2_Amy;
 PlayerMock playerMock_Computer1;
 private ArrayList<PlayerMock> allPlayersLst;
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
  /* initialize Readable and Appendable. */
  if (in == null || out == null) {
   throw new IllegalArgumentException("Readable and Appendable can't be null");
  }
  this.in = in;
  this.out = out;

  /* initialize mock model */
  this.m = new MansionMockModel();

  /* assigning players moves: move, pick, look around, display(don't cost turn) */
  this.moves_lst = new ArrayList<>();
  moves_lst.add("move"); // sam
  moves_lst.add("move"); // amy
  moves_lst.add("pick"); // sam
  moves_lst.add("look around"); // amy -> Sam
  moves_lst.add("display"); // Sam -> computer1
  moves_lst.add("move"); // Sam
  moves_lst.add("pick"); // amy
  moves_lst.add("look around"); // sam -> amy
  moves_lst.add("move"); // amy
  moves_lst.add("pick"); // sam
  moves_lst.add("look around"); // amy -> computer1
  moves_lst.add("move"); // sam
  moves_lst.add("move"); // amy
  moves_lst.add("pick"); // sam
  moves_lst.add("pick"); // amy
  moves_lst.add("move"); // sam
  moves_lst.add("move"); // amy
  moves_lst.add("pick"); // sam
  moves_lst.add("pick"); // amy
  moves_lst.add("pick"); // sam
  moves_lst.add("pick"); // amy
  moves_lst.add("pick"); // sam
  moves_lst.add("move"); // amy
  moves_lst.add("pick"); // Sam
  moves_lst.add("move"); // amy
  moves_lst.add("quit"); // Sam calls to quit the game.

  /* names list for look around option */
  this.namesLookAroundLst = new ArrayList<>();
  this.namesLookAroundLst.add("Sam");
  this.namesLookAroundLst.add("computer_player_1");
  this.namesLookAroundLst.add("Amy");
  this.namesLookAroundLst.add("computer_player_1");
 } // end of constructor.

 /**
  * play the world game.
  *
  * @throws IOException for file handles.
  */
 public void playGame(MansionMockModel m) throws IOException {
  //note: the above parameter causing that I need a mockController which I dont need.
  Objects.requireNonNull(m);
  this.m = m;
  Scanner scanController = new Scanner(in);

  /* first, init. Mansion */
  out.append("playgame() started.\n");
  String worldInput = "C:\\Users\\dongpingchen\\Documents\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt";
  File file = new File(worldInput);
  out.append("File path entered correctly.\n");
  try {
   // Creates a reader using the FileReader
   FileReader input = new FileReader(file);
   out.append("FileReader parsed in this file path.\n");
   m.readFile(input);
   out.append("Mock model read this file\n");
   input.close();
  } catch (Exception e) {
   e.getStackTrace();
  }
  m.drawWorld();
  out.append("The Mansion has been just drawn.\n");

  /* initialize mock players */
  this.allPlayersLst = m.getAllPlayers();
  this.playerMock1_Sam = new PlayerMock(m, "human", "Sam",
      "Fate", 5);
  this.allPlayersLst.add(playerMock1_Sam);

  this.playerMock2_Amy = new PlayerMock(m, "human", "Amy",
      "The Enlightenment", 4);
  this.allPlayersLst.add(playerMock2_Amy);

  this.playerMock_Computer1 = new PlayerMock(m, "computer",
      "computer_player_1", "The Myths", 10);
  this.allPlayersLst.add(playerMock_Computer1);

  out.append("Finished initializing all the players in this game.\n");

  // 1. first line: an integer N (declaring how many players for this game).
  int totalPlayers = this.allPlayersLst.size();
  out.append("There are " + totalPlayers + " players for this game.\n");

  // 2. next N lines: each line represent each players information:

  for (int i = 0; i < totalPlayers; i++) { // next N lines for players info.
   PlayerMock currPlayer = allPlayersLst.get(i);

   String typeStr = currPlayer.getComputerOrHuman();
   out.append("-> A " + typeStr + " player just entered this game.\n");

   String playerNameStr = currPlayer.getPlayerName();
   out.append("PlayerName: " + playerNameStr + '\n');

   String roomNameStr = currPlayer.getPlayerRoom();
   out.append("Player initial room: " + roomNameStr + '\n');

   int itemsAmountAllowed = currPlayer.getPlayerTotalAllowedItem();
   out.append("Total items allowed for this player: " + itemsAmountAllowed + '\n');
   // *note: assume the players not poccessing items when first dropped into the
   // rooms.

   out.append(String.format("%s is successfully added to this Mansion. <-\n", playerNameStr));
  } // end of for loop.

  String input = "";
  int track_moves = 0;
  int track_names = 0;
  boolean exit = false;
  while (!exit) { /// from here the scan.next should only have 'move actions'
   for (int i = 0; i < totalPlayers; i++) { // start iterating over each readline()
    PlayerMock currPlayer = allPlayersLst.get(i);
    while (currPlayer.getPlayerTurn()) {
     if (currPlayer.getComputerOrHuman().equals("human")) { // a human player
      out.append("Human Player, " + currPlayer.getPlayerName()
          + ", is having this turn and picking the move.\n");
      if (track_moves == moves_lst.size()) {
       exit = true;
       out.append("Players running out of moves. GAME IS ENDED!!!");
       break;
      }
      input = moves_lst.get(track_moves++); // for example， move
      switch (input) {
       // ===================Methods Cost a turn:
       case "move": /* int movePlayer() */

        this.out.append(String.format("%s is trying to 'move' to another room.\n",
            currPlayer.getPlayerName()));
        // flip its boolean turn value:
        currPlayer.flipTurn(); // now val is false.
        currPlayer.setPlayerTurn(false); // now val is false.
        break;

       case "pick": /* String pickUp() */
        currPlayer.pickUp();
        this.out.append(String.format("%s is trying to 'pick up' an item. \n",
            currPlayer.getPlayerName()));
        // flip its boolean turn value:
        currPlayer.flipTurn(); // now val is false.
        currPlayer.setPlayerTurn(false); // now val is false.

        break;

       case "look around": /* String lookAround(String playerName) */
        currPlayer.lookAround();
        this.out.append(String.format("%s is 'looking around' on another player.\n",
            currPlayer.getPlayerName()));
        // flip its boolean turn value:
        currPlayer.flipTurn(); // now val is false.
        currPlayer.setPlayerTurn(false); // now val is false.
        break;

       // =========Methods Dont cost a turn:
       case "display": /* String displayPlayerInfo(String playerName) */
        currPlayer.displayPlayerInfo(this.namesLookAroundLst.get(track_names++));
        this.out.append(String.format("%s is trying to 'pick up' an item.\n",
            currPlayer.getPlayerName()));
        break;

       case "quit":
        this.out.append("Current player, " + currPlayer.getPlayerName()
            + ", chose to close this game. :)))\n");
        currPlayer.setPlayerTurn(false);
        this.out.append("Game has been Ended!");
        exit = true;
        i = totalPlayers; // to prevent computer player for running another round.
        break;

      }

     } else if (currPlayer.getComputerOrHuman().equals("computer")) { // a PC player
      out.append("Computer Player, " + currPlayer.getPlayerName()
          + " ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n");
      int move = this.helperRandNum(2);
      input = this.helperGetComputerMove(move);
      switch (input) { // for a computer
       case "move": /* int movePlayer() */
        // this.out.append(
        // String.format("%s is trying to 'move' to another room.\n",
        // currPlayer.getPlayerName()));
        // flip its boolean turn value:
        currPlayer.flipTurn(); // now val is false.
        currPlayer.setPlayerTurn(false); // now val is false.
        break;

       case "pick": /* String pickUp() */
        // this.out.append(
        // String.format("%s is trying to 'pick up' an item. \n",
        // currPlayer.getPlayerName()));
        // flip its boolean turn value:
        currPlayer.flipTurn(); // now val is false.
        currPlayer.setPlayerTurn(false); // now val is false.
        break;

       case "look around": /* String lookAround(String playerName) */
        // this.out.append(
        // String.format("%s is 'looking around' on another player.\n",
        // currPlayer.getPlayerName()));
        // flip its boolean turn value:
        currPlayer.flipTurn(); // now val is false.
        currPlayer.setPlayerTurn(false); // now val is false.
        break;
      }
     }

    } // end while(current players turn).
   } // end for() -> finished one round.

   // TODO: 2nd part (get players to play)
   /* flip turns all back to 'true' after each round fnishing. */
   for (int j = 0; j < totalPlayers; j++) {
    this.allPlayersLst.get(j).flipTurn(); // flip them all back to true.
    this.allPlayersLst.get(j).setPlayerTurn(true);
   }
   if (!exit) {
    this.out.append("-->One round of game has been just finished.<--\n");
   }
  } // end of while(true) loop -> game ends.
 } // end of playGame().

 // ===============================helper
 // functions====================================

 /**
  * helper function: generate a random integer value from 0 to 'j'
  *
  * @param j the upper limit of the value generated.
  */
 private int helperRandNum(int j) {
  // random generator:
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
} // end of HumanController!
