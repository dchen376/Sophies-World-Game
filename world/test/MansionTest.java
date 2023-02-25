package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import world.Mansion;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Mansion Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 17, 2023</pre>
 */
public class MansionTest {
 private Mansion ms;

 public MansionTest() {
 }

 @Before public void setup() {
  this.ms = new Mansion();
  File file = new File(
      "C:\\Users\\dongpingchen\\Documents\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt");

  try {
   FileReader input = new FileReader(file);
   this.ms.readFile(input);
   input.close();
  } catch (Exception var3) {
   var3.getStackTrace();
   Assert.fail("failed! Exception was thrown.");
  }

 }

 @Test public void testReadFile() {
  File file = new File(
      "C:\\Users\\dongpingchen\\Documents\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt");

  try {
   FileReader input = new FileReader(file);
   this.ms.readFile(input);
   input.close();
  } catch (Exception var3) {
   var3.getStackTrace();
   Assert.fail("failed! Exception was thrown.");
  }

 }

 @Test public void testDrawWorld() {
  this.ms.drawWorld();
 }

 @Test public void testAllRoomsNamesLst() {

  Assert.assertEquals("The Garden of Eden", this.ms.getAllRoomsNamesLst().get(0));

 }

 @Test public void testGetRoomNameIndexMap() throws Exception {
  Assert.assertEquals(ms.getRoomNameIndexMap().toString(),
      "{The Postcards=12, The Middle Ages=14, Kierkegaard=27, Freud=30, Bjerkely=22, Berkeley=21, The Natural Philosophers=3, Spinoza=18, Counterpoint=33, Darwin=29, Two Cultures=13, Locke=19, The Garden Party=32, Plato=8, The Big Bang=34, Marx=28, Aristotle=10, The Enlightenment=23, Kant=24, The Renaissance=15, Democritus=4, The Major's Cabin=9, Hume=20, Our Own Time=31, Athens=7, Fate=5, Descartes=17, The Myths=2, The Top Hat=1, The Baroque=16, The Garden of Eden=0, Socrates=6, Romanticism=25, Hegel=26, Hellenism=11}");
 }

 @Test public void testGetItemsRoomMap() {
  this.ms.getItemsRoomMap().get("apple");
  int ans = ms.getItemsRoomMap().get("apple");
  Assert.assertEquals(0, ans);
 }

 @Test public void testGetCoordinates() {
  ArrayList<ArrayList<ArrayList<Integer>>> ans = this.ms.getListOfRoomCoordinates();
  ArrayList<Integer> intAns1 = (ArrayList) ((ArrayList) ans.get(0)).get(0);
  int a = (Integer) intAns1.get(0);
  Assert.assertEquals(18L, (long) a);
  int b = (Integer) intAns1.get(1);
  Assert.assertEquals(0L, (long) b);
  ArrayList<Integer> intAns2 = (ArrayList) ((ArrayList) ans.get(0)).get(1);
  int c = (Integer) intAns2.get(0);
  Assert.assertEquals(22L, (long) c);
  int d = (Integer) intAns2.get(1);
  Assert.assertEquals(5L, (long) d);
 }

 @Test public void testGetTargetName() {
  int compare = this.ms.getTargetName().compareTo("Albert Knag");
  Assert.assertEquals(3L, (long) compare);
 }

 @Test public void testGetTargetTotalHealth() {
  Assert.assertEquals((long) this.ms.getTargetHealth(), 100L);
 }

 @Test public void testGetItemsDamage() {
  HashMap<String, Integer> ans = this.ms.getItemsDamageMap();
  Assert.assertEquals(ans.toString(),
      "{glues=4, atomic theory book=4, physics book=6, a music book=21, A diary in French=4, A half smoked Cigar=1, an old, dirty, broken glasses=3, an antique cup=6, a painting of a lake=16, apple=3, Sacred history book=7, A broken dagger=2, cat=0, A pair of leather boots=3, daisy flower=7, some pothos plants=4, some fire matches=3, beer=9, A math book of Trigonometry=21, A book titled The Communist Manifesto=4, some Rocks seems collected near beaches=26, crystall ball=2, morning dress=8, A teapot=7, A germany map=2, Luther rose=9, book about Florence=1, plato's dialogues=4, A bottle of Scotch whisky=11, Eve=4, fish=8, some candles=32, Cross of Mathilde=67, A later modern period history book=2, A glass of Irish Whisky=3, A moldy Cheese=12}");
 }
}
