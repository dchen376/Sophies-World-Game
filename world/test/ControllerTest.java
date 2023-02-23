import org.junit.Test;
import org.junit.Before;
import world.Controller;

import java.util.Scanner;

/**
 * Junit tests for controller class.
 * @author <DongPing Chen>
 * @version 1.0
 * @since <pre>Feb 22, 2023</pre>
 */
public class ControllerTest {

 /* declare fields & controllers.*/
 private final Scanner in; //input from user of the controller class
 private final Appendable out;

 private Controller controller1;
 private Controller controller2;
 private Controller mock_Controller;

 public ControllerTest(Readable in, Appendable out) {
this.in = new Scanner(in);
 this.out = out;
 }

 /* initialize controllers. */

 /**
  * Testing setups
  *
  * @throws Exception
  */
 @Before public void setup() throws Exception {
  controller1 = new Controller();
  controller2 =; mock_Controller =;
 }







 /*TODO methods testing*/

 /**
  * Method: playGame()
  */
 @Test public void testPlayGame() throws Exception {
  //TODO: Test goes here...
 }

 /**
  * Method: helperRandNum(int j)
  */
 @Test public void testHelperRandNum() throws Exception {
  //TODO: Test goes here...
/* 
try { 
   Method method = Controller.getClass().getMethod("helperRandNum", int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
 }

 /**
  * Method: helperGetComputerMove(int j)
  */
 @Test public void testHelperGetComputerMove() throws Exception {
  //TODO: Test goes here...
/* 
try { 
   Method method = Controller.getClass().getMethod("helperGetComputerMove", int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
 }


 /*TODO: mock test*/

} 
