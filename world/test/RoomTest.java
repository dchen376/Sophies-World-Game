import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import world.Room;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/** 
* Room Tester. 
* 
* @author <Authors name> 
* @since <pre>Feb 17, 2023</pre> 
* @version 1.0 
*/ 
public class RoomTest {
 private Room room;


 public RoomTest() {
 }

 @Before
 public void setup() {
//  this.room = new Room();
//  File file = new File("C:\\\\Users\\\\dongpingchen\\\\Desktop\\\\cs 5010 PDP\\\\project\\\\part2- implementation\\\\World\\\\src\\\\world.txt");

//  try {
//   FileReader input = new FileReader(file);
//   this.room.readFile(input);
//   input.close();
//  } catch (Exception var3) {
//   var3.getStackTrace();
//   Assert.fail("failed! Exception was thrown.");
//  }

 }

 @Test
 public void testGetRoomIndex() {
  int index = this.room.getRoomIndex("The Garden of Eden");
  Assert.assertEquals(0L, (long)index);
 }

 @Test
 public void testGetNeighbors() {
  ArrayList<String> lst = this.room.getNeighbors("Locke");
  Assert.assertEquals("Hume", lst.get(0));
 }

 @Test
 public void testDisplayRoomInfo() {
  String ans = this.room.displayRoomInfo("Locke");
  Assert.assertEquals("Room name: Locke. Items in this room: some candles. The room's neighbors: Hume", ans);
 }
} 
