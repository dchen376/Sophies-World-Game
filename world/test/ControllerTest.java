package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import world.Controller;
import world.ControllerMock;
import world.Mansion;
import world.MansionMockModel;
import java.io.*;

/**
 * Junit tests for controller class.
 *
 * @author <DongPing Chen>
 * @version 1.0
 * @since <pre>Feb 22, 2023</pre>
 */
public class ControllerTest {
 private FileReader in;
 private StringBuilder out = new StringBuilder();
 private StringBuilder out_mock = new StringBuilder();
 /* model */  Mansion mansion;
 /* controller */ Controller controller;

 /* mock model */  MansionMockModel mansion_mock;
 /* mock controller */  ControllerMock controller_mock;

 /**
  * set up the world text file to read.
  * set up the all objects after
  */
 @Before public void setup() {
  String filePath = "C:\\Users\\dongpingchen\\Documents\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt";
  File file = new File(filePath);
  try {
   in = new FileReader(file);
  } catch (FileNotFoundException e) {
   e.printStackTrace();
  }
//  mansion = new Mansion();
//  controller = new Controller(in, out);
  mansion_mock = new MansionMockModel();
  controller_mock = new ControllerMock(in, out_mock);
 }

 /**
  * mock model testing.
  *
  * @throws IOException
  */
 @Test public void mockModelTest() throws IOException {

  controller_mock.playGame(mansion_mock);
  String expected_out = "playgame() started.\n" + "File path entered correctly.\n" + "FileReader parsed in this file path.\n" + "Mock model read this file\n" + "The Mansion has been just drawn.\n" + "Finished initializing all the players in this game.\n" + "There are 3 players for this game.\n" + "-> A human player just entered this game.\n" + "PlayerName: Sam\n" + "Player initial room: Fate\n" + "Total items allowed for this player: 5\n" + "Sam is successfully added to this Mansion. <-\n" + "-> A human player just entered this game.\n" + "PlayerName: Amy\n" + "Player initial room: The Enlightenment\n" + "Total items allowed for this player: 4\n" + "Amy is successfully added to this Mansion. <-\n" + "-> A computer player just entered this game.\n" + "PlayerName: computer_player_1\n" + "Player initial room: The Myths\n" + "Total items allowed for this player: 10\n" + "computer_player_1 is successfully added to this Mansion. <-\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'move' to another room.\n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is trying to 'move' to another room.\n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'pick up' an item. \n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is 'looking around' on another player.\n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'pick up' an item.\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'move' to another room.\n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is trying to 'pick up' an item. \n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is 'looking around' on another player.\n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is trying to 'move' to another room.\n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'pick up' an item. \n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is 'looking around' on another player.\n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'move' to another room.\n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is trying to 'move' to another room.\n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'pick up' an item. \n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is trying to 'pick up' an item. \n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'move' to another room.\n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is trying to 'move' to another room.\n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'pick up' an item. \n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is trying to 'pick up' an item. \n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'pick up' an item. \n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is trying to 'pick up' an item. \n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'pick up' an item. \n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is trying to 'move' to another room.\n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Sam is trying to 'pick up' an item. \n" + "Human Player, Amy, is having this turn and picking the move.\n" + "Amy is trying to 'move' to another room.\n" + "Computer Player, computer_player_1 ,is having the turn and picking the move. And this is a random move that the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n" + "Human Player, Sam, is having this turn and picking the move.\n" + "Current player, Sam, chose to close this game. :)))\n" + "Game has been Ended!";
  String actual_out = out_mock.toString();
  Assert.assertEquals(expected_out, actual_out);
 }

 /**
  * normal inputs testing.
  */
 @Test public void testPlayGame() throws Exception {
  //TODO: Test goes here...
 }


}
