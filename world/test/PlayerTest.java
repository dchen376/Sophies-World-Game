package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import world.Mansion;
import world.Player;

/**
 * test class for the Player class.
 */
public class PlayerTest {
  /* model */ Mansion m;
  Player player1;
  Player player2;
  private FileReader in;
  private StringBuilder out = new StringBuilder();

  /**
   * set up for the tests.
   */
  @Before
  public void setup() {
    String filePath = "C:\\Users\\dongpingchen\\Documents\\GitHub\\"
        + "PDP---Milestone-Mansion-Game-\\world.txt";
    File file = new File(filePath);
    try {
      in = new FileReader(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    this.m = new Mansion();
    m.readFile(in);
    m.drawWorld();

    player1 = new Player(m, "human", "Amy", "The Enlightenment", 4);
    m.getAllPlayers().add(player1);

    player2 = new Player(m, "human", "Sam", "Fate", 6);
    m.getAllPlayers().add(player1);
  }

  @Test
  public void getTotalItemsAllowedMap() {
    String expected = "{Amy=4, Sam=6}";
    String actual = m.getTotalItemsAllowedMap().toString();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void getPlayersItemsMap() {
    String expected = "{}";
    String actual = m.getPlayersItemsMap().toString();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void getItemsRoomMap() {
    String expected = "{glues=12, atomic theory book=4, physics book=3,"
        + " a music book=33, A diary in French=17, A half smoked Cigar=30,"
        + " an old, dirty, broken glasses=34, an antique cup=11,"
        + " a painting of a lake=22, apple=0, Sacred history book=2,"
        + " A broken dagger=16, cat=8, A pair of leather boots=25,"
        + " daisy flower=10, some pothos plants=32, some fire matches=13,"
        + " beer=9, A math book of Trigonometry=23, " + "A book titled The Communist Manifesto=28, "
        + "some Rocks seems collected near beaches=29, crystall ball=5, "
        + "morning dress=1, A teapot=26, A germany map=24, Luther rose=27, "
        + "book about Florence=15, plato's dialogues=6, "
        + "A bottle of Scotch whisky=20, Eve=0, fish=7, some candles=19, "
        + "Cross of Mathilde=14, A later modern period history book=31, "
        + "A glass of Irish Whisky=21, A moldy Cheese=18}";
    String actual = m.getItemsRoomMap().toString();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void checkTurnsMap() {
    boolean expected = true;
    Boolean actual = player1.checkTurnsMap();
    Assert.assertEquals(expected, actual);

  }

  @Test
  public void getPlayerItemsLst() {
    player1.pickUp();
    String expected = "[A math book of Trigonometry]";
    String actual = player1.getPlayerItemsLst().toString();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void getPlayerTotalAllowedItem() {
    int expected = 4;
    int actual = player1.getPlayerTotalAllowedItem();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void getPlayerTurn() {
    boolean expected = true;
    Boolean actual = player1.getPlayerTurn();
    Assert.assertEquals(expected, actual);

  }

  @Test
  public void getComputerOrHuman() {
    String expected = "human";
    String actual = player1.getComputerOrHuman();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void getPlayerRoom() {
    String expected = "The Enlightenment";
    String actual = player1.getPlayerRoom();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void getPlayerName() {
    String expected = "Amy";
    String actual = player1.getPlayerName();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void getItemsDamageMap() {
    String expected = "{glues=4, atomic theory book=4, physics book=6, a music book=21, A diary in French=4, A half smoked Cigar=1, an old, dirty, broken glasses=3, an antique cup=6, a painting of a lake=16, apple=3, Sacred history book=7, A broken dagger=2, cat=0, A pair of leather boots=3, daisy flower=7, some pothos plants=4, some fire matches=3, beer=9, A math book of Trigonometry=21, A book titled The Communist Manifesto=4, some Rocks seems collected near beaches=26, crystall ball=2, morning dress=8, A teapot=7, A germany map=2, Luther rose=9, book about Florence=1, plato's dialogues=4, A bottle of Scotch whisky=11, Eve=4, fish=8, some candles=32, Cross of Mathilde=67, A later modern period history book=2, A glass of Irish Whisky=3, A moldy Cheese=12}";
    String actual = m.getItemsDamageMap().toString();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void pickUp() {
    String expected = "A math book of Trigonometry";
    String actual = player1.pickUp();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void lookAround() {
    String expected = "[You are: Amy and is at room The Enlightenment, and is having items: []., The pet is not anywhere in your visibility yet; it is not in any of the neighboring rooms., The pet is not anywhere in your visibility yet; it is not in any of the neighboring rooms., The pet is not anywhere in your visibility yet; it is not in any of the neighboring rooms., The Target is not anywhere in your visibility yet; it is not in any of the neighboring rooms., The Target is not anywhere in your visibility yet; it is not in any of the neighboring rooms., The Target is not anywhere in your visibility yet; it is not in any of the neighboring rooms.]";
    String actual = player1.lookAround();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void displayPlayerInfo() {
    String expected = "The player is at room: "
        + "The Enlightenment. The item(s) in the room: [A math book of Trigonometry]";
    String actual = player1.displayPlayerInfo(player1.getPlayerName());
    Assert.assertEquals(expected, actual);
  }

}