//package test.world;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;
//import world.Item;
//
//import java.io.File;
//import java.io.FileReader;
//
///**
//* Item Tester.
//*
//* @author <Authors name>
//* @since <pre>Feb 17, 2023</pre>
//* @version 1.0
//*/
//public class ItemTest {
// private Item item;
//
// public ItemTest() {
// }
//
// @Before
// public void setup() {
//  this.item = new Item();
//  File file = new File("C:\\\\Users\\\\dongpingchen\\\\Desktop\\\\cs 5010 PDP\\\\project\\\\part2- implementation\\\\World\\\\src\\\\world.txt");
//
////  try {
////   FileReader input = new FileReader(file);
////   this.item.readFile(input);
////   input.close();
////  } catch (Exception var3) {
////   var3.getStackTrace();
////   Assert.fail("failed! Exception was thrown.");
////  }
//
// }
//
// @Test
// public void testGetDamageAmount() {
//  int damage = this.item.getDamageAmount("apple");
//  Assert.assertEquals(3L, (long)damage);
// }
//
// @Test
// public void testGetItemLocation() {
//  int location = this.item.getItemLocation("apple");
//  Assert.assertEquals(0L, (long)location);
// }
//}
