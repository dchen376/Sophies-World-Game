import org.junit.Test;
import org.junit.Before;
import world.Controller;
import world.ControllerMock;
import world.Mansion;
import world.MansionMockModel;

import java.io.*;
import java.util.Scanner;

/**
 * Junit tests for controller class.
 *
 * @author <DongPing Chen>
 * @version 1.0
 * @since <pre>Feb 22, 2023</pre>
 */
public class ControllerTest {
 private FileReader in;
 private StringBuilder out = new StringBuilder();
 private StringBuilder out_mock = new StringBuilder();
 /* model */ private Mansion mansion;
 /* controller */private Controller controller;

 /* mock model */ private MansionMockModel mansion_mock;
 /* mock controller */ private ControllerMock controller_mock;

 /**
  * set up the world text file to read.
  * set up the all objects after
  */
 @Before public void setup() {
  String filePath = "C:\\Users\\dongpingchen\\Documents\\GitHub\\milestone-2---controller\\world.txt";
  File file = new File(filePath);
  try {
   in = new FileReader(file);
  } catch (FileNotFoundException e) {
   e.printStackTrace();
  }
  mansion = new Mansion();
  controller = new Controller(in, out);
  mansion_mock = new MansionMockModel();
  controller_mock = new ControllerMock(in, out_mock);
 }

 /**
  * mock model testing
  *
  * @throws IOException
  */
 public void mockModelTest() throws IOException {
  controller_mock.playGame(mansion_mock);


  //make all the move(s):
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
