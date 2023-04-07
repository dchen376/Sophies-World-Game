package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import model.mansion.Mansion;
import model.room.Room;

/**
 * Test class for Room class.
 */
public class RoomTest {
  private Room room;
  private Mansion m;

  @Before
  public void setup() {
    this.m = new Mansion();
    File file = new File("C:\\Users\\dongpingchen" + "\\Documents\\GitHub\\"
        + "PDP---Milestone-Mansion-Game-\\world.txt");

    try {
      FileReader input = new FileReader(file);
      this.m.readFile(input);
      input.close();
    } catch (FileNotFoundException var3) {
      var3.getStackTrace();
      Assert.fail("failed! Exception was thrown.");
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.room = new Room(this.m);

  }

  @Test
  public void testGetNeighbors() {
    ArrayList<String> lst = this.room.getNeighbors("Locke");
    Assert.assertEquals("Hume", lst.get(0));
  }

  @Test
  public void testDisplayRoomInfo() {
    String ans = this.room.displayRoomInfo("Locke");
    Assert.assertEquals(
        "Room name: Locke. Items in this room: some candles. The room's neighbors: Hume.", ans);
  }
}
