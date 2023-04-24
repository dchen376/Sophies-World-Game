package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import model.mansion.MansionBuilder;
import model.player.Player;
import view.GamePanel;
import view.MouseClick;
import view.ViewBuilder;

/**
 * this is the controller class.
 */
public class Controller implements ActionListener {
  /* class attributes */
  MansionBuilder model;
  ViewBuilder view;
  GamePanel panel;
  private final Readable in;
  private final Appendable out;
  private int totalTurns;
  private ArrayList<ArrayList<String>> messageLst;

  // added for milestone4:
  private boolean firstTime;
  private int tracker;
//  private String outputText;
  private int totalPlayers;
  private int track_player;
  private int track_four;
  private int currentTurn;
  Player currPlayer;
  private boolean correctInput;
  private boolean wrongInput;
  private boolean attemptInput;
  private boolean moveInput;
  private boolean enterGame;
  private boolean gameEnd;
  private boolean failAttempt;

  private String pName;
  private String pRoom;
  private String pType;
  private int totalAllowed;
  private int currIndexPlayer;
  private boolean stageTwoEnd;

  /**
   * Constructor.
   * 
   * @param in  an InputStream prompts inputs from user.
   * @param out an OutputStream that appends game logs.
   */
  public Controller(Readable in, Appendable out, MansionBuilder mansionBuilder,
      ViewBuilder viewBuilder) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.in = in;
    this.out = out;
    this.model = mansionBuilder;
    this.view = viewBuilder;

    // set controller as the button listener.
    this.view.setActionListener(this); // controller has control over BUTTON on view

    // add controller as the mouse click listener FOR Jframe.
    this.view.addClickListener(this); // controller has control over CLICK on view

    // add mouse listener FOR Jpanel.
    this.panel = new GamePanel(model);
    this.panel.addMouseListener(new MouseClick(this)); // controller has control over click on
                                                       // GAMEPANEL.
    // newly added:
    this.firstTime = true;
    this.messageLst = new ArrayList<>();
    this.tracker = -1;
    this.totalTurns = 0;
    this.track_player = 0;
    this.track_four = 0;
    this.currentTurn = 1;
    this.correctInput = true;
    this.wrongInput = false;
    this.attemptInput = false;
    this.moveInput = false;
    this.enterGame = false;
    this.gameEnd = false;
    this.failAttempt = false;
    this.currIndexPlayer = 0;
    this.stageTwoEnd = false;

  } // end of constructor.

  /**
   * this method is for setting up the actions performed for the buttons.
   * 
   * @param e the button that is being clicked.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) { // read from the input text field
      case "Exit Button":
        System.exit(0);
        break; /**/
      case "Confirm Button": // TODO work on the logic confirm once player confirmed the action

        System.out.println("output is: " + this.view.getTextField().getText());
        if (firstTime) {
          this.messageLst = this.getMessageLst();
          this.view.getMyLabel()
              .setText(this.lstGetHTML(this.messageLst.get(this.incrementTrack())));
          this.view.getTextField().setVisible(true);
          this.firstTime = false;
          // set up the game:
          break;
        }

        if (!this.view.getTextField().getText().isEmpty()) {
          if (tracker == 0) {
            System.out.println("tracker is 0");
            this.setTotalTurns(Integer.parseInt(this.view.getTextField().getText()));
            System.out.println("AND total turn is: " + this.view.getInputString());
          } else if (tracker == 1) {
            System.out.println("tracker is 1");
            this.setTotalPlayers(Integer.parseInt(this.view.getTextField().getText()));
            System.out.println("and total player is: " + this.view.getInputString());
            this.totalPlayers = Integer.parseInt(this.view.getInputString());
            System.out.println("total player: ");
            this.messageLst = this.getMessageLst();
          } else if (this.tracker <= (this.totalPlayers * 4)  + 1) { // set player values x4.
//          } else if (!this.stageTwoEnd) { // set player values x4.




            System.out.println("come here!");
            System.out.println("how many palyers? : " + this.model.getAllPlayers());
//            System.out.println(this.model.getAllPlayers().get(0).getPlayerName());
            boolean checkAssign = false;
            if (this.track_four == 0) { // player type.
              if (!("human".equals(this.view.getTextField().getText().toLowerCase())
                  || "computer".equals(this.view.getTextField().getText().toLowerCase()))) {
                this.correctInput = false;
                this.view.getTextField().setText("");
                break;
              }
//              this.model.getAllPlayers().get(this.track_player).setComputerOrHuman(this.view.getTextField().getText());
              this.correctInput = true;
              track_four++;
              this.pType = this.view.getInputString();
            } else if (this.track_four == 1) { // player name.
//              this.model.getAllPlayers().get(this.track_player).setPlayerName(this.view.getTextField().getText());
              track_four++;
              this.pName = this.view.getInputString();
              this.correctInput = true;

            } else if (this.track_four == 2) { // player room
              ArrayList<String> roomLst = this.model.getAllRoomsNamesLst();
              this.correctInput = false;
              for (String room : roomLst) {
                if (this.view.getTextField().getText().toLowerCase().equals(room.toLowerCase())) {
                  this.correctInput = true;
                }
              } // check input ?correct
              if (!this.correctInput) {
                this.view.getTextField().setText("");
                break;
              }
//              this.model.getAllPlayers().get(this.track_player).setPlayerRoom(this.view.getTextField().getText());
              track_four++;
              this.pRoom = this.view.getInputString();
              this.correctInput = true;
            } else if (this.track_four == 3) { // == 3. player total items.
//              this.model.getAllPlayers().get(this.track_player).setPlayerTotalAllowedItem(Integer.parseInt(this.view.getTextField().getText()));
              System.out.println("text is : " + this.view.getTextField().getText());
//              System.out.println("player name is: " + this.currPlayer.getPlayerName());
//              System.out.println("current hash map size: " + this.model.getTotalItemsAllowedMap().size());
//              this.model.getTotalItemsAllowedMap().put(this.currPlayer.getPlayerName(), Integer.parseInt(this.view.getTextField().getText()));
              track_four = 0;
//              checkAssign = true;
              this.totalAllowed = Integer.parseInt(this.view.getInputString());
              this.model.getAllPlayers().add(new Player(this.model, this.pType, this.pName, this.pRoom, this.totalAllowed));
              this.currPlayer = this.model.getAllPlayers().get(this.currIndexPlayer);
//              if (this.currIndexPlayer == this.totalPlayers){
//                this.stageTwoEnd = true;
//                break;
//              }
              this.currIndexPlayer++;
              this.messageLst = this.getMessageLst();


            }
          }

          else { // for each player actions.
            this.correctInput = false; // to eliminate prev steps prompts.

//            System.out.println("here now!! 3rd tracker for player actions");
            System.out.println("Current player is: " + this.currPlayer.getPlayerName());
            String prevName = this.currPlayer.getPlayerName();
            /* get current player */ //TODO here
            for (int i = 0; i < this.model.getAllPlayers().size(); i++) {
              if (this.model.getAllPlayers().get(i).getPlayerTurn()) {
                this.currPlayer = this.model.getAllPlayers().get(i);
                break;
              }
            }
//            System.out.println("NOWWW current player is: " + this.currPlayer.getPlayerName());


            if (prevName.equals(currPlayer.getPlayerName())){
              for (Player player : this.model.getAllPlayers()){
                player.setPlayerTurn(true);
                this.model.getTurnsMap().put(player.getPlayerName(), 1);
              }
            }


            /* get parameter inputs */ // note: parameter check before action prompts.
            if (this.attemptInput) {
//              if (Integer.parseInt(this.view.getTextField().getText()) == 0) { // with POKE.
              if (this.model.getPlayersItemsMap().size() == 0) { // with POKE.
//                System.out.println("here pc attempt");
//                int prevHealth = this.model.getTargetHealth();
//                int health = this.currPlayer.pcAttemptTarget();
//                if (health == prevHealth){
//                  this.failAttempt = true;
//                }
                this.currPlayer.pcAttemptTarget();
                this.attemptInput = false;
                this.currentTurn++;
                this.currPlayer.setPlayerTurn(false);
                this.helperNewPromptNextPlayer();


              } else { // with an item
                String itemUsed = this.view.getTextField().getText();
                boolean foundItem = false;
                ArrayList<String> itemLst = this.currPlayer.getPlayerItemsLst();
                /* check if it's a valid item. */
                for (String item : itemLst) {
                  if (itemUsed.toLowerCase().equals(item.toLowerCase())) {
                    foundItem = true;
                    // note: later add to evidence set.
                    break;
                  }
                }
                if (foundItem) { // for valid item.
                  this.currPlayer.attemptTarget(itemUsed);
                  this.attemptInput = false;
                  this.currentTurn++;
                  this.currPlayer.setPlayerTurn(false); // note set player turn
                  this.helperNewPromptNextPlayer();
                  break;
                } else { // for invalid item.
                  this.view.clearInputString();
//                this.currentTurn--;
                  break;
                }
              }
            } // end of if(this.attemptInput)
            else if (this.moveInput) {

              String roomPicked = this.view.getTextField().getText();
              boolean foundRoom = false;
              ArrayList<String> neighborRooms = this.model.getRoom()
                  .getNeighbors(this.currPlayer.getPlayerRoom());
              for (String room : neighborRooms) {
                if (roomPicked.toLowerCase().equals(room.toLowerCase())) {
                  foundRoom = true;
                  break;
                }
              }

              if (foundRoom) { // if valid room input.
                this.currPlayer.movePlayer(roomPicked);
                this.moveInput = false;
                this.currentTurn++;
                this.currPlayer.setPlayerTurn(false); // note set player turn
                this.helperNewPromptNextPlayer();
                break;
              }
              else { // invalid input.
                this.view.clearInputString();
                break;
              }
            }

            /* ending the game based on conditions */
             if (this.model.getTargetHealth() <= 0) {
//              System.out.println("target killed");
              this.gameEnd = true;
              ArrayList<String> lst = new ArrayList<>();
              lst.add("Target killed! Good Job!");
              this.view.getMyLabel().setText(this.lstGetHTML(lst));
              this.view.getTextField().setVisible(false);
            } else if (this.currentTurn > this.totalTurns && this.model.getTargetHealth() > 0) {
//              System.out.println("Total turns reached.");
//              System.out.println("Target health is: " + this.model.getTargetHealth());
//              System.out.println("Target is at: " + this.model.getTargetLocation());
//              System.out.println("pet is at: " + this.model.getPetLocation());
              this.gameEnd = true;
              ArrayList<String> lst = new ArrayList<>();
              lst.add("Total turns reached. Game Ended Now!");
              this.view.getMyLabel().setText(this.lstGetHTML(lst));
              this.view.getTextField().setVisible(false);
//              break;
            } else { // later rounds prompts.
//              System.out.println("else? prompts");
//              System.out.println("current turn is: " + this.currentTurn);
//              System.out.println("total turn is: " + this.totalTurns); // note: some checks
              ArrayList<String> lst = new ArrayList<>();
              lst.add("Target current health is: " + this.model.getTargetHealth());
              lst.add("Current turn: TURN " + (this.currentTurn));
              lst.add("Actions -> Move Pet, Attempt, Move, Look Around, Pick, Display");
              lst.add(String.format("%S, this is your turn!", this.currPlayer.getPlayerName()));
              this.view.getMyLabel().setText(this.lstGetHTML(lst));
            }
//            else if (this.wrongInput){
//              ArrayList<String> lst = new ArrayList<>();
//              lst.add("Wrong input!");
//              lst.add("Target current health is: " + this.model.getTargetHealth());
//              lst.add("Current turn: TURN " + (this.currentTurn));
//              lst.add(String.format("%S, this is your turn!", this. currPlayer.getPlayerName()));
//              lst.add("Please re-enter: ");
//              this.view.getMyLabel().setText(this.lstGetHTML(lst));
//              System.out.println("wrong input??");
//            }


              
              
              //TODO:  Computer vs. Human

              String action = this.view.getTextField().getText();

            if ("human".equals(this.currPlayer.getComputerOrHuman())){
              /* check first: Wrong action inputs */
              this.wrongInput = false;
              if (!("Move Pet".toLowerCase().equals(action.toLowerCase())
                  || "Attempt".toLowerCase().equals(action.toLowerCase())
                  || "Move".toLowerCase().equals(action.toLowerCase())
                  || "Look Around".toLowerCase().equals(action.toLowerCase())
                  || "Pick".toLowerCase().equals(action.toLowerCase())
                  || "Display".toLowerCase().equals(action.toLowerCase()))) {
                this.wrongInput = true;
                this.view.getTextField().setText("");
                break;
              }
            }


            if ("computer".equals(this.currPlayer.getComputerOrHuman())){
              /* check first: Wrong action inputs */
              this.wrongInput = false;
              if (!("Move Pet".toLowerCase().equals(action.toLowerCase())
                  || "Attempt".toLowerCase().equals(action.toLowerCase())
                  || "Move".toLowerCase().equals(action.toLowerCase())
                  || "Pick".toLowerCase().equals(action.toLowerCase()))){
                this.wrongInput = true;
                this.view.getTextField().setText("");
                break;
              }
            }
            
            
            

            // TODO: for robot options: move pet, attempt, move, pick

            // condition check for prompting ending game message

            /* check each correct action input */
            if ("move pet".equals(action) && !this.gameEnd) {
              this.currPlayer.movePet();
              ArrayList<String> arrLst = new ArrayList<>();
              arrLst.add("Pet has just moved!");
              this.view.getMyLabel().setText(this.lstGetHTML(arrLst));
              this.currPlayer.setPlayerTurn(false); // note set player turn

            } else if ("attempt".equals(action) && !this.gameEnd) { // need parameter //
              if ("human".equals(this.currPlayer.getComputerOrHuman())){
                ArrayList<String> playerItemLst = new ArrayList<>();
                playerItemLst
                    .add("Please choose an item at your disposal; enter 0 if you don't have any. ");
                playerItemLst.add("your items: " + this.currPlayer.getPlayerItemsLst().toString());
                this.view.getMyLabel().setText(this.lstGetHTML(playerItemLst));
                this.attemptInput = true;
                this.view.clearInputString();
              } 
              else if ("computer".equals(this.currPlayer.getComputerOrHuman())) {
                ArrayList<String> playerItemLst = new ArrayList<>();
                playerItemLst
                    .add("Computer Item attempting. Type yes for the next turn.");
                this.currPlayer.pcAttemptTarget();
                this.currentTurn++;
                this.currPlayer.setPlayerTurn(false);
                this.view.getMyLabel().setText(this.lstGetHTML(playerItemLst));
                this.attemptInput = false;
                this.view.clearInputString();
                }
            }else if ("move".equals(action) && !this.gameEnd) { // need parameter
              ArrayList<String> moveLst = new ArrayList<>();
              moveLst.add("please choose a room to move into");
              ArrayList<String> neighborRooms = this.model.getRoom()
                  .getNeighbors(this.currPlayer.getPlayerRoom());
              moveLst.add(neighborRooms.toString());
              this.view.getMyLabel().setText(this.lstGetHTML(moveLst));
              this.moveInput = true;
            } else if ("look around".equals(action) && !this.gameEnd) {
              ArrayList<String> lstInfo = this.currPlayer.lookAround();
              lstInfo.add("Type yes and \"Confirm\" for next turn.");
              this.view.getMyLabel().setText(this.lstGetHTML(lstInfo));
              this.currPlayer.setPlayerTurn(false); // note set player turn
              this.currentTurn++;

            } else if ("pick".equals(action) && !this.gameEnd) {
              String itemPicked = this.currPlayer.pickUp();
              ArrayList<String> lstInfo = new ArrayList<>();
              lstInfo.add("Type yes and \"Confirm\" for next turn.");
              if (itemPicked == null) {
                lstInfo.add("Nothing in the room to pick!");
              } else {
                lstInfo.add("You've just picked: " + itemPicked);
              }
              this.view.getMyLabel().setText(this.lstGetHTML(lstInfo));
              this.currPlayer.setPlayerTurn(false); // note set player turn
              this.currentTurn++;
            } else if ("display".equals(action) && !this.gameEnd) { // don't cost turn

              if ("computer".equals(this.currPlayer.getComputerOrHuman())){
                this.view.getTextField().setText("");
                break;
              }

              String info = this.currPlayer.displayPlayerInfo(this.currPlayer.getPlayerName());
              ArrayList<String> lstInfo = new ArrayList();
              lstInfo.add("Type yes and \"Confirm\" to continue your turn.");
              lstInfo.add(info);
              this.view.getMyLabel().setText(this.lstGetHTML(lstInfo));
//              this.currentTurn--;
            }

            this.view.clearInputString();
          } // end tracking counts; end turn.

          if (correctInput) {
            this.view.getMyLabel()
                .setText(this.lstGetHTML(this.messageLst.get(this.incrementTrack())));
          }

        } // for text is not empty.

//        System.out.println("tracker is: " + tracker);
        this.view.getTextField().setText("");
        break;

      default:
        throw new IllegalStateException("Error: unknown button");

    }

  }

  /**
   * play the game.
   * 
   * @throws IOException for file handles.
   */
  public void playGame() throws IOException {
    Objects.requireNonNull(model);
    Scanner scanner = new Scanner(in);

    /* first, init. Mansion, readFile(), and drawWrold(). */
    String scannedLine = scanner.nextLine();
    File file = new File(scannedLine);
    try {
      FileReader fileReader = new FileReader(file);
      this.model.readFile(fileReader); // readFile()
      fileReader.close();
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    }
    this.model.drawWorld(); // drawWorld()
    view.makeVisible();

    System.out.println("Welcome!!");
    System.out.println("Welcome to the game: " + this.model.getWorldName());
    System.out
        .println(String.format("There are %d rooms in this game: ", this.model.getTotalRooms()));
    System.out.println(this.model.getAllRoomsNamesLst().toString());
    System.out.println("Please follow the instructions below in order to add the players: ");
    System.out.println("The target is: " + this.model.getTargetName());
    System.out.println("The magic pet is: " + this.model.getPetName() + ". HOORAYYY!!!");

    ArrayList<Player> allPlayers = this.model.getAllPlayers();
    System.out.println("please input the total number of turns allowed for this game: \n");
    String totalTurnsStr = scanner.nextLine(); // 20 turns for example.
    this.totalTurns = Integer.parseInt(totalTurnsStr);
    out.append(totalTurnsStr + '\n');
    System.out.println("Please input the total number of players for this game: \n");
    String totalPlayersStr = scanner.nextLine(); // READ 1ST LINE: 2
    this.totalPlayers = Integer.parseInt(totalPlayersStr);
    out.append(totalPlayersStr + '\n');

    for (int i = 0; i < totalPlayers; i++) { // next N lines for players info.
      System.out.println("Is this a human player or maybe a computer?\n");
      String typeStr = scanner.nextLine().toLowerCase();
      while (!(typeStr.equals("human") || typeStr.equals("computer"))) {
        System.out.println("Wrong input! Please re-enter: ");
        typeStr = scanner.nextLine();
      }
      out.append("ComputerORHuman: " + typeStr);

      System.out.println("Please give this player a name: \n");
      String playerNameStr = scanner.nextLine();
      out.append("PlayerName: " + playerNameStr);
      System.out.println("Please input the name of the room to first drop the player:\n");

      boolean checkRoom = true;
      String roomNameStr = scanner.nextLine();
      while (checkRoom) {
        for (String room : this.model.getAllRoomsNamesLst()) {
          if (roomNameStr.equals(room)) {
            checkRoom = false;
            break;
          }
        }
        if (checkRoom) {
          System.out.println("Wrong input! Please re-enter: ");
          roomNameStr = scanner.nextLine();
        }
      }

      out.append("Player initial room: " + roomNameStr);
      System.out
          .println("please input the total amount items allowed for this player to possess: ");
      String itemsAmountAllowedStr = scanner.nextLine();
      out.append("Total items allowed for this player: " + itemsAmountAllowedStr + '\n');
      // *note: assume the players not poccessing items when first dropped into the
      // rooms.

      /* adding attributes to one player instance */
      Player player = new Player(this.model, typeStr, playerNameStr, roomNameStr,
          Integer.parseInt(itemsAmountAllowedStr));
      allPlayers.add(player); // add one player to the 'allPlayers' list.
      out.append(String.format("%s is successfully added to this Mansion.", playerNameStr));

      /* populate hashmap: petBlessings */
      this.model.getPetBlessingsMap().put(player, false);

    } // end of for loop; finished adding all the Players.

    System.out.println("GAME STARTING SOON!!");

    /* third, the actual game play. */
    String input = "";
    boolean exit = false;
//    int round = 1;
    int maxTurns = totalTurns; // the max turns reached will stop the game.
    String playerEndGame = ""; // a statement telling whoever ends this game.
    while (!exit) { /// from here the scan.next should only have 'move actions'
      /* condition check when max turns reached; need to end the game */

      /* check whoever turn it is, and let it make the move */
      /* via switch() statements */
      // note: actual game play soon
      for (int i = 0; i < totalPlayers; i++) {

        /* the current player. */
        Player currPlayer = allPlayers.get(i);

//        if (currPlayer.getPlayerTurn()) { // if his turn is -> true.
        while (currPlayer.getPlayerTurn()) { // while still this player's turn
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
                System.out.println(String
                    .format("The pet is about to move to your location for some blessings!!"));

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
                // end game if target killed
                int currHealth = this.model.getTargetHealth();
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
                System.out
                    .println("The pet DFS moved to room: " + this.model.getPet().getPetLocation());
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
                System.out
                    .println("The pet DFS moved to room: " + this.model.getPet().getPetLocation());
                break;
              case "pick": /* String pickUp() */
                currPlayer.pickUp();
                this.out.append(String.format("%s is trying to pick up an item. \n",
                    currPlayer.getPlayerName()));
                // flip its boolean turn value:
                currPlayer.flipTurn(); // now val is false.
                currPlayer.setPlayerTurn(false); // now val is false.
                System.out
                    .println("The pet DFS moved to room: " + this.model.getPet().getPetLocation());
                break;
              case "look around": /* String lookAround(String playerName) */
                System.out.println(currPlayer.lookAround());
                this.out.append(String.format("%s is looking around on another player.\n",
                    currPlayer.getPlayerName()));
                // flip its boolean turn value:
                currPlayer.flipTurn(); // now val is false.
                currPlayer.setPlayerTurn(false); // now val is false.
                System.out
                    .println("The pet DFS moved to room: " + this.model.getPet().getPetLocation());
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
                System.out
                    .println("game has been ended earlier by player: " + playerEndGame + "! :)))");

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
            int move = this.helperRandNum(3); // TODO if pc lookaround: change 2 to 3.
            input = this.helperGetComputerMove(move);
            System.out
                .println("computer player, " + currPlayer.getPlayerName() + ", chose to " + input);
            switch (input) { // for a computer

              // : attempt on target

              /*
               * : Computer-controlled players always choose to make an attempt on the target
               * character's life (if they cannot be seen by others) using the item in their
               * inventory that does the most damage.
               */
              case "attempt":

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

                // end game if target killed
                int currHealth = this.model.getTargetHealth();
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

                System.out
                    .println("The pet DFS moved to room: " + this.model.getPet().getPetLocation());
                break;

              case "move": // to move the computer.
                // : let computer move to a random room.
//                  this.mansion
                ArrayList<String> neighborRooms = this.model.getAllNeighborsMap()
                    .get(currPlayer.getPlayerRoom());
                int roomsize = neighborRooms.size();// todoO:fix room size 0?
                int randNum = this.helperRandNum(roomsize);
                if (randNum - 1 >= 0) {
                  randNum -= 1;
                }
                String randRoom = neighborRooms.get(randNum); // pc will pick a random move.
                currPlayer.movePlayer(randRoom);
                this.out.append(String.format("%s is trying to move to another room.\n",
                    currPlayer.getPlayerName()));
                // flip its boolean turn value:
                currPlayer.flipTurn(); // now val is false.
                currPlayer.setPlayerTurn(false); // now val is false.
                System.out
                    .println("The pet DFS moved to room: " + this.model.getPet().getPetLocation());
                break;
              case "pick": /* String pickUp() */
                currPlayer.pickUp();
                this.out.append(String.format("%s is trying to pick up an item. \n",
                    currPlayer.getPlayerName()));
                // flip its boolean turn value:
                currPlayer.flipTurn(); // now val is false.
                currPlayer.setPlayerTurn(false); // now val is false.
                System.out
                    .println("The pet DFS moved to room: " + this.model.getPet().getPetLocation());
                break;

              default:
                System.out.println("Input error !!");
                maxTurns += 1;
                break;
            }
          }

        }
        maxTurns -= 1;
        if (maxTurns == 0) { // : check max turns before proceeding.
          exit = true;
          System.out
              .println("Maximum turns reached ! Target just run away !! Maybe another day ... ");
          this.out.append("Target escaped.");
          break;
        }
        // end while (currPlayer.getPlayerTurn()); because some actions don't cost a
        // turn.
//        } // end for().

//        if (("quit".equals(input))) {//: add the quit option
//          System.out.println("game has been ended earlier by player: " + playerEndGame + "! :)))");
//
//        }

        if (i + 1 == totalPlayers) { // once one round reached:
          // 1. reset blessings.
          for (Player player : this.model.getAllPlayers()) {
            this.model.getPetBlessingsMap().put(player, false);
          } // after each round, the blessings will all be gone and have to be regained.
          // 2. reset player turns map.
          for (int j = 0; j < totalPlayers; j++) {
            allPlayers.get(j).flipTurn(); // flip them all back to true.
            allPlayers.get(j).setPlayerTurn(true);
          }
          System.out.println("One round of game has just been finished :)))");
          this.out.append("Just finished one round.");
        } // finished of round checking.

      } // end of for loop; (and nothing between this loop and the while loop).
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
    return "wrong answer";
  }

  // newly added:

  public int getTotalTurns() {
    return totalTurns;
  }


  public void setTotalTurns(int totalTurns) {
    this.totalTurns = totalTurns;
  }

  public void setTotalPlayers(int totalPlayers) {
    this.totalPlayers = totalPlayers;
  }

  private String lstGetHTML(ArrayList<String> lst) {
    StringBuilder sb = new StringBuilder();
    sb.append("<html>");
    for (int i = 0; i < lst.size() - 2; i++) {
      sb.append(lst.get(i));
      sb.append("<br>");
    }
    sb.append(lst.get(lst.size() - 1));
    sb.append("<br>");
    sb.append("</html>");

    return sb.toString();
  }

  private int incrementTrack() {
    // if (tracker + 1 < this.messageLst.size()) {
    this.tracker++;
    // }
    return this.tracker;
  }

  public ArrayList<ArrayList<String>> getMessageLst() { // tdo: keep update this list.
    this.messageLst = new ArrayList<>();
    ArrayList<String> welcomeLst = this.model.getWelcomeMessage();
    welcomeLst.add("please input the total number of turns allowed for this game:");
    this.messageLst.add(welcomeLst);

    this.messageLst.add(new ArrayList<String>(
        Arrays.asList("Please input the total number of players for this game: ")));

    for (int i = 0; i < this.totalPlayers; i++) {
      this.messageLst
          .add(new ArrayList<String>(Arrays.asList("Is this a human player or maybe a computer?")));
      this.messageLst.add(new ArrayList<String>(Arrays.asList("Please give this player a name: ")));
      this.messageLst.add(new ArrayList<String>(
          Arrays.asList("Please input the name of the room to first drop the player:")));
      this.messageLst.add(new ArrayList<String>(Arrays
          .asList("please input the total amount items allowed for this player to possess:")));
    }
    welcomeLst = this.model.getBeforeGameMessage(this.getTotalTurns());
    welcomeLst.add("please type YES! and \'Confirm\'");
    this.messageLst.add(welcomeLst);

    // this.currentTurn = 1;
    // for (int i = 0; i < this.model.getAllPlayers().size(); i++) {
    //
    // ArrayList<String> lst = new ArrayList<>();
    // lst.add("Target current health is: " + this.model.getTargetHealth());
    // lst.add("Current turn: TURN " + this.currentTurn++);
    //// lst.add("Please pick one of the following actions!");
    // lst.add("Actions -> Move Pet, Attempt, Move, Look Around, Pick, Display");
    //
    // lst.add(String.format("%S, this is your turn!",
    // this.model.getAllPlayers().get(i).getPlayerName()));
    // this.messageLst.add(lst);
    // System.out.println(lst);
    // }

    return this.messageLst;
  }

  /**
   * set the label message for current player.
   */
  private void helperNewPromptNextPlayer() {
    this.view.clearInputString();

    ArrayList<String> lst = new ArrayList<>();
    lst.add("Target current health is: " + this.model.getTargetHealth());
    lst.add("Current turn: TURN " + (this.currentTurn));
    lst.add("Actions -> Move Pet, Attempt, Move, Look Around, Pick, Display");

    for (int i = 0; i < this.model.getAllPlayers().size(); i++) {
      if (this.model.getAllPlayers().get(i).getPlayerTurn()) {
        this.currPlayer = this.model.getAllPlayers().get(i);
        break;
      }
    }



    lst.add(String.format("%S, this is your turn!", this.currPlayer.getPlayerName()));
    this.view.getMyLabel().setText(this.lstGetHTML(lst));
  }

} // end of Controller.java
