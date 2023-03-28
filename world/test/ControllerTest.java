//package test;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import world.ControllerMock;
//import world.MansionMockModel;
//
///**
// * junit test for COntroller class.
// */
//public class ControllerTest {
//  /* mock model */ MansionMockModel mansionMock;
//  /* mock controller */ ControllerMock controllerMock;
//  private FileReader in;
//  private StringBuilder out = new StringBuilder();
//  private StringBuilder outMock = new StringBuilder();
//
//  /**
//   * set up the world text file to read. set up the all objects after
//   */
//  @Before
//  public void setup() {
//    String filePath = "C:\\Users\\dongpingchen\\Documents"
//        + "\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt";
//    File file = new File(filePath);
//    try {
//      in = new FileReader(file);
//    } catch (FileNotFoundException e) {
//      e.printStackTrace();
//    }
//    mansionMock = new MansionMockModel();
//    controllerMock = new ControllerMock(in, outMock);
//  }
//
//  /**
//   * mock model testing with heavy player moves quit with a "quit option"
//   * <p>
//   * this is a holitic testing that includes all the possible moves the players
//   * can pick: such moves as 'move', 'pick', 'look around', 'display' and 'quit'.
//   * <p>
//   * notes: I do feel like for only 5 move type inputs from the players, it is
//   * best to put them all together to test the final outcome; rather than writing
//   * several test cases to tests. This way, if this holistic testing is passing
//   * the test. It first means all the separate tests (if I wrote) should also pass
//   * the test, and second it means that these separate moves when put them
//   * together like this is also working, which is what this milestone 2 wants.
//   *
//   * @throws IOException when getting a wrong input/output.
//   */
//  @Test
//  public void mockModelTest() throws IOException {
//
//    controllerMock.playGame(mansionMock);
//    String expectedOut = "playgame() started.\n" + "File path entered correctly.\n"
//        + "FileReader parsed in this file path.\n" + "Mock model read this file\n"
//        + "The Mansion has been just drawn.\n"
//        + "Finished initializing all the players in this game.\n"
//        + "There are 3 players for this game.\n" + "-> "
//        + "A human player just entered this game.\n" + "PlayerName: Sam\n"
//        + "Player initial room: Fate\n" + "Total items allowed for this player: 5\n"
//        + "Sam is successfully added to this Mansion. <-\n"
//        + "-> A human player just entered this game.\n" + "PlayerName: Amy\n"
//        + "Player initial room: The Enlightenment\n" + "Total items allowed for this player: 4\n"
//        + "Amy is successfully added to this Mansion. <-\n"
//        + "-> A computer player just entered this game.\n" + "PlayerName: computer_player_1\n"
//        + "Player initial room: The Myths\n" + "Total items allowed for this player: 10\n"
//        + "computer_player_1 is successfully added to this Mansion. <-\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'move' to another room.\n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is trying to 'move' to another room.\n"
//        + "Computer Player, computer_player_1 ,is having "
//        + "the turn and picking the move. And this is a random move"
//        + " that the compuer player will pick.\n"
//        + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'pick up' an item. \n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is 'looking around' on another player.\n"
//        + "Computer Player, computer_player_1 ,is having the turn "
//        + "and picking the move. And this is a random move "
//        + "that the compuer player will pick.\n"
//        + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'pick up' an item.\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'move' to another room.\n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is trying to 'pick up' an item. \n"
//        + "Computer Player, computer_player_1 ,is having the "
//        + "turn and picking the move. And this is a random move that"
//        + " the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is 'looking around' on another player.\n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is trying to 'move' to another room.\n"
//        + "Computer Player, computer_player_1 ,is having the turn"
//        + " and picking the move. And this is a random move that"
//        + " the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'pick up' an item. \n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is 'looking around' on another player.\n"
//        + "Computer Player, computer_player_1 ,is having the turn "
//        + "and picking the move. And this is a random move that the compuer player will pick.\n"
//        + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'move' to another room.\n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is trying to 'move' to another room.\n"
//        + "Computer Player, computer_player_1 ,is having the turn and "
//        + "picking the move. And this is a random move that the compuer " + "player will pick.\n"
//        + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'pick up' an item. \n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is trying to 'pick up' an item. \n"
//        + "Computer Player, computer_player_1 ,is having the "
//        + "turn and picking the move. And this is a random move that "
//        + "the compuer player will pick.\n" + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'move' to another room.\n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is trying to 'move' to another room.\n"
//        + "Computer Player, computer_player_1 ,is having the turn "
//        + "and picking the move. And this is a random move that the "
//        + "compuer player will pick.\n" + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'pick up' an item. \n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is trying to 'pick up' an item. \n"
//        + "Computer Player, computer_player_1 ,is having the turn "
//        + "and picking the move. And this is a random move that the "
//        + "compuer player will pick.\n" + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'pick up' an item. \n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is trying to 'pick up' an item. \n"
//        + "Computer Player, computer_player_1 ,is having the turn "
//        + "and picking the move. And this is a random move that the "
//        + "compuer player will pick.\n" + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'pick up' an item. \n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is trying to 'move' to another room.\n"
//        + "Computer Player, computer_player_1 ,is having the turn "
//        + "and picking the move. And this is a random move that the "
//        + "compuer player will pick.\n" + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Sam is trying to 'pick up' an item. \n"
//        + "Human Player, Amy, is having this turn and picking the move.\n"
//        + "Amy is trying to 'move' to another room.\n"
//        + "Computer Player, computer_player_1 ,is having the turn"
//        + " and picking the move. And this is a random move that the "
//        + "compuer player will pick.\n" + "-->One round of game has been just finished.<--\n"
//        + "Human Player, Sam, is having this turn and picking the move.\n"
//        + "Current player, Sam, chose to close this game. :)))\n" + "Game has been Ended!";
//    String actualOut = outMock.toString();
//    Assert.assertEquals(expectedOut, actualOut);
//  }
//}
