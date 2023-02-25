package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import world.Mansion;
import world.Room;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Room Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 17, 2023</pre>
 */
public class RoomTest {
 private Room room;
 private Mansion m;

 public RoomTest() {
 }

 @Before public void setup() {
  this.m = new Mansion();
  File file = new File(
      "C:\\Users\\dongpingchen\\Documents\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt");

  try {
   FileReader input = new FileReader(file);
   this.m.readFile(input);
   input.close();
  } catch (Exception var3) {
   var3.getStackTrace();
   Assert.fail("failed! Exception was thrown.");
  }

  this.room = new Room(this.m.getItemsRoomMap(), this.m.getRoomNameIndexMap(),
      this.m.getListOfRoomCoordinates(), this.m.getAllRoomsNamesLst(), this.m.getAllNeighborsMap());

 }

 @Test public void testGetRoomIndex() {
  int index = this.room.getRoomIndex("The Garden of Eden");
  Assert.assertEquals(0L, (long) index);
 }

 @Test public void testGetNeighbors() {
  ArrayList<String> lst = this.room.getNeighbors("Locke");
  Assert.assertEquals("Hume", lst.get(0));
 }

 @Test public void testDisplayRoomInfo() {
  String ans = this.room.displayRoomInfo("Locke");
  Assert.assertEquals(
      "Room name: Locke. Items in this room: some candles. The room's neighbors: Hume.", ans);
 }
}
