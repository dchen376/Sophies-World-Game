package world;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * this is the controller class.
 */
public class Controller {
  /* class attributes */
  Mansion mansion;
  private final Readable in;
  private final Appendable out;

  /**
   * Constructor.
   * 
   * @param in  an InputStream prompts inputs from user.
   * @param out an OutputStream that appends game logs.
   */
  public Controller(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.in = in;
    this.out = out;

  } // end of constructor.

  /**
   * play the game.
   * 
   * @throws IOException for file handles.
   */
  public void playGame(Mansion m) throws IOException {
    //: the user is provided with some limited information about where they are in the world at the start of their turn.
    // This should not be the same information as the information providing by looking around.

    Objects.requireNonNull(m);
    this.mansion = m;
    Scanner scanner = new Scanner(in);

    /* first, init. Mansion, readFile(), and drawWrold(). */
    String scannedLine = scanner.nextLine();
    File file = new File(scannedLine);
    try {
      // Creates a reader using the FileReader
      FileReader fileReader = new FileReader(file);
      // Reads the 'readable'
      this.mansion.readFile(fileReader); // readFile()
      // Closes the reader
      fileReader.close();
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    }
    this.mansion.drawWorld(); // drawWorld()

    System.out.println(this.mansion.welcomeMessage().toString());

    /* second, add total_turns, and parse the players */
    // 1. first line: an integer N (declaring how many players for this game).
    /// for example, 2
    ArrayList<Player> allPlayers = this.mansion.getAllPlayers();
    /* total turns for the game */
    System.out.println("please input the total number of turns allowed for this game: \n");
    String totalTurnsStr = scanner.nextLine(); // 20 turns for example.
    int totalTurns = Integer.parseInt(totalTurnsStr);
    out.append(totalTurnsStr + '\n');
    /* total players for the game */
    System.out.println("Please input the total number of players for this game: \n");
    String totalPlayersStr = scanner.nextLine(); // READ 1ST LINE: 2
    int totalPlayers = Integer.parseInt(totalPlayersStr);
    out.append(totalPlayersStr + '\n');
    // 2. next N lines: each line represent each players information:
    /// line1(x4): h playerA, The Top Hat, 5(total items allowed)
    /// line2 (x4) : c(if it's just a pc) computerA, 3(room), 3(total items allowed)
    for (int i = 0; i < totalPlayers; i++) { // next N lines for players info.
      System.out.println("Is this a human player or maybe a computer?\n");
      String typeStr = scanner.nextLine();
      out.append("ComputerORHuman: " + typeStr);

      System.out.println("Please give this player a name: \n");
      String playerNameStr = scanner.nextLine();
      out.append("PlayerName: " + playerNameStr);
      System.out.println("Please input the name of the room to first drop the player:\n");
      String roomNameStr = scanner.nextLine();
      out.append("Player initial room: " + roomNameStr);
      System.out
          .println("please input the total amount items allowed for this player to possess: ");
      String itemsAmountAllowedStr = scanner.nextLine();
      out.append("Total items allowed for this player: " + itemsAmountAllowedStr + '\n');
      // *note: assume the players not poccessing items when first dropped into the
      // rooms.

      /* adding attributes to one player instance */
      Player player = new Player(this.mansion, typeStr, playerNameStr, roomNameStr,
          Integer.parseInt(itemsAmountAllowedStr));
      allPlayers.add(player); // add one player to the 'allPlayers' list.
      out.append(String.format("%s is successfully added to this Mansion.", playerNameStr));

      /*populate hashmap: petBlessings*/
      this.mansion.getPetBlessings().put(player, false);

    } // end of for loop; finished adding all the Players.


    //: a welcome message
    String welcomer = this.mansion.beforeGameMessage(totalTurns).toString();
//    System.out.println(welcomer);

    /* third, the actual game play. */
    String input = "";
    boolean exit = false;
    int round = 1;
    int maxTurns = totalTurns; // the max turns reached will stop the game.
    String playerEndGame = ""; // a statement telling whoever ends this game.
    while (!exit) { /// from here the scan.next should only have 'move actions'
      /* condition check when max turns reached; need to end the game */
      if (maxTurns == 0) {
        exit = true;
        System.out
            .println("Maximum turns reached ! Target just run away !! Maybe another day ... ");
        this.out.append("Target escaped.");
        break;
      }
      
    

      /* check whoever turn it is, and let it make the move */
      /* via switch() statements */
      for (int i = 0; i < totalPlayers; i++) {
        Player currPlayer = allPlayers.get(i);
        if (currPlayer.getPlayerTurn()) { // if his turn is -> true.
          while (currPlayer.getPlayerTurn()) {
            if (currPlayer.getComputerOrHuman().equals("human")) { // a human player
              System.out.println(
                  String.format("%s, this is your turn now !\n", currPlayer.getPlayerName()));
              System.out.println("please pick one of these options to execute: 'move', "
                  + "'pick', look around', 'display', 'attempt', 'move pet' \n");
              System.out.println("or if you want to quit the game," + " simply enter 'quit'");
              input = scanner.nextLine(); // for example， move
              switch (input) {
                // ===================Methods Cost a turn:
                // : move the pet
                case "move pet":
                  System.out.println(String.format(
                      "Please enter the location you would like to move this pet: %s \n",
                      this.mansion.getPetName()));
                  input = scanner.nextLine();
                  int locIndex = this.mansion.getRoomNameIndexMap().get(input);
                  currPlayer.movePet();
                  this.out.append(String.format("%s is trying to move the pet to a new location.\n",
                      currPlayer.getPlayerName()));

                case "attempt":
                  // flip its boolean turn value:4
                  // : print out player items it has:
                  System.out.println(
                      "Emmm... Let me see what items you currently holding! Hold on One Sec!");
                  if (currPlayer.getPlayerItemsLst().size() != 0) {
                    System.out.println(
                        "You have the below items at you disposal, pick one to use please: ");
                    System.out.println(currPlayer.getItemsDamagesLst().toString());

                    input = scanner.nextLine();
                    // : to avoid invalid inputs may be use switch:

                    while (!currPlayer.checkValidItem(input)) {
                      System.out.println("Sorry this is not a valid item to use!!");
                      System.out.println("Please use a valid item in your item's bag :))");
                      input = scanner.nextLine();
                    }

                    currPlayer.attemptTarget(input);
                    this.out.append(String.format("%s is trying to attempt on the target's life.\n",
                        currPlayer.getPlayerName()));
                  } else {
                    // if players got no items:
                    System.out.println(
                        "Ohhhhh you got no items at your disposal! But! You can always poke...");
                    currPlayer.pcAttemptTarget(); // since no attempts here this method will just
                                                  // poke.
                    this.out.append(String.format("%s is trying to poke the target.\n",
                        currPlayer.getPlayerName()));
                  }
                  // int locIndex = this.mansion.getRoomNameIndexMap().get(input);
                  this.out.append(String.format("%s is trying to attempt on Target's life.\n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.
                  maxTurns--;

                  // end game if target killed
                  int currHealth = this.mansion.getTargetHealth();
                  if (currHealth <= 0) {
                    this.out.append("Target killed by player: " + currPlayer.getPlayerName()
                        + "!!! Game ended :)))\n");
                    currPlayer.setPlayerTurn(false);
                    this.out.append("Game has been Ended!");
                    exit = true;
                    i = totalPlayers; // to prevent computer player for running another round.
                    playerEndGame = currPlayer.getPlayerName();
                    break;
                  }

                  // TODO: succeed or failed?
                  System.out.println();
                  break;

                case "move": // to move the player.
                  // : to print out the rooms first?
                  System.out.println("You are currently in room: " + currPlayer.getPlayerRoom());
                  System.out.println(
                      "You have the below neighboring rooms that you could pick and move to:");
                  System.out.println(currPlayer.helperNeighborRooms().toString());
                  System.out.println("Please pick a room you would like to move to:");
                  input = scanner.nextLine();
                  while (!currPlayer.checkValidRoom(input)) {
                    System.out.println("Sorry this is not a valid room to move to!!");
                    System.out.println("Please pick a valid room you would like to move to :))");
                    input = scanner.nextLine();
                  }
                  currPlayer.movePlayer(input);
                  this.out.append(String.format("%s is trying to move to another room.\n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.
                  maxTurns--;
                  break;
                case "pick": /* String pickUp() */
                  currPlayer.pickUp();
                  this.out.append(String.format("%s is trying to pick up an item. \n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.
                  maxTurns--;
                  break;
                case "look around": /* String lookAround(String playerName) */
                  System.out.println(currPlayer.lookAround());
                  this.out.append(String.format("%s is looking around on another player.\n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.
                  maxTurns--;
                  break;

                // =========Methods Dont cost a turn:
                case "display": /* String displayPlayerInfo(String playerName) */
                  System.out.println("please input the player name you want to 'display':\n");
                  input = scanner.nextLine();
                  System.out.println(currPlayer.displayPlayerInfo(input));
                  this.out.append(String.format("%s is trying to pick up an item.\n",
                      currPlayer.getPlayerName()));
                  break;

                case "quit":
                  this.out.append("Current player, " + currPlayer.getPlayerName()
                      + ", chose to close this game. :)))\n");
                  currPlayer.setPlayerTurn(false);
                  this.out.append("Game has been Ended!");
                  exit = true;
                  i = totalPlayers; // to prevent computer player for running another round.
                  playerEndGame = currPlayer.getPlayerName();
                  break;

                default:
                  System.out.println("Input error !!");
                  break;
              }
            } else if (currPlayer.getComputerOrHuman().equals("computer")) { // a PC player
              /*
               * moves for PC: 1. move() -> costs Turn! 2. pickup() -> costs Turn! 3.
               * lookAround() -> costs Turn!
               */

              System.out.println("the computer player, " + currPlayer.getPlayerName()
                  + ", is having its turn and picking a move now!\n");
              int move = this.helperRandNum(3); //TODO if pc lookaround: change 2 to 3.
              input = this.helperGetComputerMove(move);
              System.out.println(
                  "computer player, " + currPlayer.getPlayerName() + ", chose to " + input);
              switch (input) { // for a computer

                // : attempt on target

                /*
                 * : Computer-controlled players always choose to make an attempt on the
                 * target character's life (if they cannot be seen by others) using the item in
                 * their inventory that does the most damage.
                 */
                case "attempt":
                  // flip its boolean turn value:4
                  // : print out player items it has:
                  /*System.out.println(
                      String.format("Computer player %s is attempting on the Target's life",
                          currPlayer.getPlayerName()))*/;
//                  if (currPlayer.getPlayerItemsLst().size() != 0) {
//                    System.out.println(currPlayer.getItemsDamagesLst().toString());
//                    // : to avoid invalid inputs may be use switch:
////                    while (!currPlayer.checkValidItem(input)){
////                      System.out.println("Sorry this is not a valid item to use!!");
////                      System.out.println("Please use a valid item in your item's bag :))");
////                      input = scanner.nextLine();
////                    }
//
//
//                    currPlayer.attemptTarget(input);
//                    this.out.append(String.format("%s is trying to attempt on the target's life.\n",
//                        currPlayer.getPlayerName()));
//                  } else {
                  // if players got no items:
//                    System.out.println("Ohhhhh you got no items at your disposal! But! You can always poke...");
                  currPlayer.pcAttemptTarget(); // since no attempts here this method will just
                  // poke.
                  this.out
                      .append(String.format("Computer player %s is attempting on the Target's life",
                          currPlayer.getPlayerName()));
//                  }
                  // int locIndex = this.mansion.getRoomNameIndexMap().get(input);

                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.
                  maxTurns--;

                  // end game if target killed
                  int currHealth = this.mansion.getTargetHealth();
                  if (currHealth <= 0) {
                    this.out.append("Target killed by player: " + currPlayer.getPlayerName()
                        + "!!! Game ended :)))\n");
                    currPlayer.setPlayerTurn(false);
                    this.out.append("Game has been Ended!");
                    exit = true;
                    i = totalPlayers; // to prevent computer player for running another round.
                    playerEndGame = currPlayer.getPlayerName();
                    break;
                  }

                  // TODO: succeed or failed?
                  System.out.println();
                  break;

                case "move": // to move the computer.
                  // : let computer move to a random room.
//                  this.mansion
                  ArrayList<String> neighborRooms = currPlayer.helperNeighborRooms();
                  int roomsize = neighborRooms.size();
                  int randNum = this.helperRandNum(roomsize - 1);
                  String randRoom = neighborRooms.get(randNum); //pc will pick a random move.
                  currPlayer.movePlayer(randRoom);
                  this.out.append(String.format("%s is trying to move to another room.\n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.
                  maxTurns--;
                  break;
                case "pick": /* String pickUp() */
                  currPlayer.pickUp();
                  this.out.append(String.format("%s is trying to pick up an item. \n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.
                  maxTurns--;
                  break;
                case "look around": //TODO: check! does computer look around too?
                  System.out.println(currPlayer.lookAround());
                  this.out.append(String.format("%s is looking around on another player.\n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.
                  maxTurns--;
                  break;
                default:
                  System.out.println("Input error !!");
                  break;
              }
            }
            if (i + 1 == totalPlayers) { // just finished a round .
              for (Player player : this.mansion.getAllPlayers()){
                this.mansion.getPetBlessings().put(player, false);
              } //after each round, the blessings will all be gone and have to be regained.
              System.out.println("One round of game has just been finished :)))");
            } // finished of round checking.
          } // end if: check if it's a certain player's turn.
        } // end for().

        for (int j = 0; j < totalPlayers; j++) {
          allPlayers.get(j).flipTurn(); // flip them all back to true.
          allPlayers.get(j).setPlayerTurn(true);
        }

        if (!("quit".equals(input))) {
          System.out.println(round + " round(s) of game has been just finished :-))");
        } else {
          System.out.println("game has been ended earlier by player: " + playerEndGame + "! :)))");
        }

        this.out.append("Just finished one round.");
      }
    } // end of while(true) loop.
  } // end of playGame().

  /* below are only helper methods. */

  /**
   * helper function: generate a random integer value from 0 to 'j'.
   *
   * @param j the upper limit of the value generated.
   */
  private int helperRandNum(int j) {
    // random generator:
    Random ran = new Random();
    return ran.nextInt(j);
  }

  /**
   * helper function: get the string representation of moves based on integer
   * values.
   *
   * @param j upper limit of the random value.
   * @return String the move a player executed.
   */
  private String helperGetComputerMove(int j) { // : update this helper method.
    if (j == 0) {
      return "attempt";
    }
    if (j == 1) {
      return "move";
    }
    if (j == 2) {
      return "pick";
    }
    if (j == 3) {
      return "look around";
    }
    return "wrong answer";
  }

} // end of Controller.java
