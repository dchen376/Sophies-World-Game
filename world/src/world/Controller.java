package world;

import java.io.*;
import java.util.*;

public class Controller {
  /**
   * declare fields.
   */
  private final Readable in;
  private final Appendable out;
  Mansion m;

  /**
   * Constructor
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
   * play the world game.
   *
   * @throws IOException for file handles.
   */
  public void playGame(Mansion m) throws IOException {
    Objects.requireNonNull(m);
    this.m = m;
    Scanner scanController = new Scanner(in);

    /* first, init. Mansion */
    String worldInput = scanController.nextLine();
    File file = new File(worldInput);
    try {
      // Creates a reader using the FileReader
      FileReader input = new FileReader(file);
      // Reads the 'readable'
      m.readFile(input);
      // Closes the reader
      input.close();
    } catch (Exception e) {
      e.getStackTrace();
    }
    m.drawWorld();

    /* second, handle the players */
    // 1. first line: an integer N (declaring how many players for this game).
    /// for example, 2
    ArrayList<Player> allPlayers = m.getAllPlayers();
    String totalPlayersStr = scanController.nextLine(); // READ 1ST LINE: 2
    int totalPlayers = Integer.parseInt(totalPlayersStr);
    out.append(totalPlayersStr + '\n');
    // 2. next N lines: each line represent each players information:
    /// line1(x4): h playerA, The Top Hat, 5(total items allowed)
    /// line2 (x4) : c(if it's just a pc) computerA, 3(room), 3(total items allowed)
    for (int i = 0; i < totalPlayers; i++) { // next N lines for players info.
      String typeStr = scanController.nextLine();
      out.append("ComputerORHuman: " + typeStr);

      String playerNameStr = scanController.nextLine();
      out.append("PlayerName: " + playerNameStr);
      String roomNameStr = scanController.nextLine();
      out.append("Player initial room: " + roomNameStr);

      String itemsAmountAllowedStr = scanController.nextLine();
      out.append("Total items allowed for this player: " + itemsAmountAllowedStr + '\n');
      // *note: assume the players not poccessing items when first dropped into the
      // rooms.

      /* adding attributes to one player instance */
      Player player = new Player(m.getRoomNameIndexMap(), m.getTotalItemsAllowedMap(),
          m.getItemsDamageMap(), m.getTargetHealth(), m.getTargetLocation(), true,
          // default player turn is TRUE
          typeStr, playerNameStr, roomNameStr, Integer.parseInt(itemsAmountAllowedStr),
          new ArrayList<>(), // player items list?
          m.getPlayersTargetNameRoomMap(), m.getPlayersItemsMap(), m.getItemsRoomMap(),
          m.getTurnsMap());
      allPlayers.add(player); // add one player to the 'allPlayers' list.
      out.append(String.format("%s is successfully added to this Mansion.", playerNameStr));

    } // end of for loop.

    String input = "";
    boolean exit = false;
    while (!exit) { /// from here the scan.next should only have 'move actions'
      for (int i = 0; i < totalPlayers; i++) { // start iterating over each readline()
        Player currPlayer = allPlayers.get(i);
        if (currPlayer.getPlayerTurn()) { // if his turn is -> true.
          while (currPlayer.getPlayerTurn()) {
            if (currPlayer.getComputerOrHuman().equals("human")) { // a human player
              input = scanController.nextLine(); // for example， move
              switch (input) {
                // ===================Methods Cost a turn:
                case "move": /* int movePlayer() */
                  currPlayer.movePlayer(m.getAllNeighborsMap());
                  this.out.append(String.format("%s is trying to move to another room.\n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.

                  break;
                case "pick": /* String pickUp() */
                  currPlayer.pickUp();
                  this.out.append(String.format("%s is trying to pick up an item. \n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.

                  break;
                case "look around": /* String lookAround(String playerName) */
                  input = scanController.nextLine();
                  currPlayer.lookAround(input, m.getAllNeighborsMap());
                  this.out.append(String.format("%s is looking around on another player.\n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.

                  break;

                // =========Methods Dont cost a turn:
                case "display": /* String displayPlayerInfo(String playerName) */
                  input = scanController.nextLine();
                  currPlayer.displayPlayerInfo(input);
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
                  break;
              }
            } else if (currPlayer.getComputerOrHuman().equals("computer")) { // a PC player
              /*
               * moves for PC: 1. move() -> costs Turn! 2. pickup() -> costs Turn! 3.
               * lookAround() -> costs Turn!
               */

              int move = this.helperRandNum(2);
              input = this.helperGetComputerMove(move);
              switch (input) { // for a computer
                case "move": /* int movePlayer() */
                  currPlayer.movePlayer(m.getAllNeighborsMap());
                  this.out.append(String.format("%s is trying to move to another room.\n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.
                  break;
                case "pick": /* String pickUp() */
                  this.out.append(String.format("%s is trying to pick up an item. \n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.
                  break;
                case "look around": /* String lookAround(String playerName) */
                  this.out.append(String.format("%s is looking around on another player.\n",
                      currPlayer.getPlayerName()));
                  // flip its boolean turn value:
                  currPlayer.flipTurn(); // now val is false.
                  currPlayer.setPlayerTurn(false); // now val is false.
                  break;
              }
            }
            if (i + 1 == totalPlayers) { // just finished a round .
            } // finished of round checking.
          } // end if: check if it's a certain player's turn.
        }
      } // end for().
      for (int j = 0; j < totalPlayers; j++) {
        allPlayers.get(j).flipTurn(); // flip them all back to true.
        allPlayers.get(j).setPlayerTurn(true);
      }
      this.out.append("Just finished one round.");
    } // end of while(true) loop.
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
