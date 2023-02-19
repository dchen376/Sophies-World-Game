//package test.world;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;
//import world.Mansion;
//
//import java.io.File;
//import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.Arrays;
//
///**
//* Mansion Tester.
//*
//* @author <Authors name>
//* @since <pre>Feb 17, 2023</pre>
//* @version 1.0
//*/
//public class MansionTest {
// private Mansion ms;
//
// public MansionTest() {
// }
//
// @Before
// public void setup() {
//  this.ms = new Mansion();
//  File file = new File("C:\\\\Users\\\\dongpingchen\\\\Desktop\\\\cs 5010 PDP\\\\project\\\\part2- implementation\\\\World\\\\src\\\\world.txt");
//
//  try {
//   FileReader input = new FileReader(file);
//   this.ms.readFile(input);
//   input.close();
//  } catch (Exception var3) {
//   var3.getStackTrace();
//   Assert.fail("failed! Exception was thrown.");
//  }
//
// }
//
// @Test
// public void testReadFile() {
//  File file = new File("C:\\\\Users\\\\dongpingchen\\\\Desktop\\\\cs 5010 PDP\\\\project\\\\part2- implementation\\\\World\\\\src\\\\world.txt");
//
//  try {
//   FileReader input = new FileReader(file);
//   this.ms.readFile(input);
//   input.close();
//  } catch (Exception var3) {
//   var3.getStackTrace();
//   Assert.fail("failed! Exception was thrown.");
//  }
//
// }
//
// @Test
// public void testDrawWorld() {
//  this.ms.drawWorld();
// }
//
// @Test
// public void testGetRoomsNames() {
//  ArrayList<String> correctAns = new ArrayList();
//  correctAns.add("The Garden of Eden");
//  correctAns.add("The Top Hat");
//  correctAns.add("The Myths");
//  correctAns.add("The Natural Philosophers");
//  correctAns.add("Democritus");
//  correctAns.add("Fate");
//  correctAns.add("Socrates");
//  correctAns.add("Athens");
//  correctAns.add("Plato");
//  correctAns.add("The Major's Cabin");
//  correctAns.add("Aristotle");
//  correctAns.add("Hellenism");
//  correctAns.add("The Postcards");
//  correctAns.add("Two Cultures");
//  correctAns.add("The Middle Ages");
//  correctAns.add("The Renaissance");
//  correctAns.add("The Baroque");
//  correctAns.add("Descartes");
//  correctAns.add("Spinoza");
//  correctAns.add("Locke");
//  correctAns.add("Hume");
//  correctAns.add("Berkeley");
//  correctAns.add("Bjerkely");
//  correctAns.add("The Enligntenment");
//  correctAns.add("Kant");
//  correctAns.add("Romanticism");
//  correctAns.add("Hegel");
//  correctAns.add("Kierkegaard");
//  correctAns.add("Marx");
//  correctAns.add("Darwin");
//  correctAns.add("Freud");
//  correctAns.add("Our Own Time");
//  correctAns.add("The Garden Party");
//  correctAns.add("Counterpoint");
//  correctAns.add("The Big Bang");
//  ArrayList<String> ans = this.ms.getRoomsNames();
//
//  for(int i = 0; i < ans.size(); ++i) {
//   if (((String)ans.get(i)).toString().compareTo(((String)correctAns.get(i)).toString()) != 0) {
//    Assert.fail("Room info is wrongly stored! ");
//   }
//  }
//
// }
//
// @Test
// public void testGetItemsNames() {
//  ArrayList<ArrayList<String>> correctAns = new ArrayList();
//  ArrayList<String> subAns = new ArrayList();
//  subAns.add("apple");
//  subAns.add("Eve");
//  correctAns.add(subAns);
//  correctAns.add(new ArrayList(Arrays.asList("morning dress")));
//  correctAns.add(new ArrayList(Arrays.asList("Sacred history book")));
//  correctAns.add(new ArrayList(Arrays.asList("physics book")));
//  correctAns.add(new ArrayList(Arrays.asList("atomic theory book")));
//  correctAns.add(new ArrayList(Arrays.asList("crystall ball")));
//  correctAns.add(new ArrayList(Arrays.asList("plato's dialogues")));
//  correctAns.add(new ArrayList(Arrays.asList("fish")));
//  correctAns.add(new ArrayList(Arrays.asList("cat")));
//  correctAns.add(new ArrayList(Arrays.asList("beer")));
//  correctAns.add(new ArrayList(Arrays.asList("daisy flower")));
//  correctAns.add(new ArrayList(Arrays.asList("an antique cup")));
//  correctAns.add(new ArrayList(Arrays.asList("glues")));
//  correctAns.add(new ArrayList(Arrays.asList("some fire matches")));
//  correctAns.add(new ArrayList(Arrays.asList("Cross of Mathilde")));
//  correctAns.add(new ArrayList(Arrays.asList("book about Florence")));
//  correctAns.add(new ArrayList(Arrays.asList("A broken dagger")));
//  correctAns.add(new ArrayList(Arrays.asList("A diary in French")));
//  correctAns.add(new ArrayList(Arrays.asList("A moldy Cheese")));
//  correctAns.add(new ArrayList(Arrays.asList("some candles")));
//  correctAns.add(new ArrayList(Arrays.asList("A bottle of Scotch whisky")));
//  correctAns.add(new ArrayList(Arrays.asList("A glass of Irish Whisky")));
//  correctAns.add(new ArrayList(Arrays.asList("a painting of a lake")));
//  correctAns.add(new ArrayList(Arrays.asList("A math book of Trigonometry")));
//  correctAns.add(new ArrayList(Arrays.asList("A germany map")));
//  correctAns.add(new ArrayList(Arrays.asList("A pair of leather boots")));
//  correctAns.add(new ArrayList(Arrays.asList("A teapot")));
//  correctAns.add(new ArrayList(Arrays.asList("Luther rose")));
//  correctAns.add(new ArrayList(Arrays.asList("A book titled The Communist Manifesto")));
//  correctAns.add(new ArrayList(Arrays.asList("some Rocks seems collected near beaches")));
//  correctAns.add(new ArrayList(Arrays.asList("A half smoked Cigar")));
//  correctAns.add(new ArrayList(Arrays.asList("A later modern period history book")));
//  correctAns.add(new ArrayList(Arrays.asList("some pothos plants")));
//  correctAns.add(new ArrayList(Arrays.asList("a music book")));
//  correctAns.add(new ArrayList(Arrays.asList("an old, dirty, broken glasses")));
//  ArrayList<ArrayList<String>> ans = this.ms.getItemsNames();
//
//  for(int i = 0; i < correctAns.size(); ++i) {
//   for(int j = 0; j < ((ArrayList)correctAns.get(i)).size(); ++j) {
//    if (((String)((ArrayList)ans.get(i)).get(j)).toString().compareTo(((String)((ArrayList)correctAns.get(i)).get(j)).toString()) != 0) {
//     Assert.fail("item info is wrongly stored! ");
//    }
//   }
//  }
//
// }
//
// @Test
// public void testGetCoordinates() {
//  ArrayList<ArrayList<ArrayList<Integer>>> ans = this.ms.getCoordinates();
//  ArrayList<Integer> intAns1 = (ArrayList)((ArrayList)ans.get(0)).get(0);
//  int a = (Integer)intAns1.get(0);
//  Assert.assertEquals(18L, (long)a);
//  int b = (Integer)intAns1.get(1);
//  Assert.assertEquals(0L, (long)b);
//  ArrayList<Integer> intAns2 = (ArrayList)((ArrayList)ans.get(0)).get(1);
//  int c = (Integer)intAns2.get(0);
//  Assert.assertEquals(22L, (long)c);
//  int d = (Integer)intAns2.get(1);
//  Assert.assertEquals(5L, (long)d);
// }
//
// @Test
// public void testGetTargetName() {
//  int compare = this.ms.getTargetName().compareTo("Albert Knag");
//  Assert.assertEquals(3L, (long)compare);
// }
//
// @Test
// public void testGetTargetTotalHealth() {
//  Assert.assertEquals((long)this.ms.getTargetTotalHealth(), 100L);
// }
//
// @Test
// public void testGetItemsDamage() {
//  ArrayList<ArrayList<String>> ans = this.ms.getItemsDamage();
//  Assert.assertEquals(3L, (long)Integer.parseInt((String)((ArrayList)ans.get(0)).get(0)));
//  Assert.assertEquals(4L, (long)Integer.parseInt((String)((ArrayList)ans.get(0)).get(1)));
// }
//}
