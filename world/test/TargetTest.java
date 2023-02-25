package test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import world.Mansion;
import world.Target;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/**
 * Target Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Feb 17, 2023</pre>
 */
public class TargetTest {
 private Target target;
 private Mansion m;

 //constructor
 public TargetTest() {
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

  this.target = new Target(m.getTargetName(), m.getTargetHealth(), m.getTargetLocation(),
      m.getItemsRoomMap(), m.getItemsDamageMap());
 }

 /**
  * Method: testGetTargetHealth()
  */
 @Test public void testGetTargetHealth() throws Exception {
  Assert.assertEquals(100, target.getTargetHealth());
 }

 /**
  * Method: testGetTargetLocation()
  */
 @Test public void testGetTargetLocation() throws Exception {
  Assert.assertEquals(0, target.getTargetLocation());
 }

 @Test public void testMoveTarget() {
  target.moveTarget();
  Assert.assertEquals(1, target.getTargetLocation());
 }

}
