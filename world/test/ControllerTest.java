package test;

import org.junit.Test;
import org.junit.Before;
import world.Controller;
import world.ControllerMock;
import world.Mansion;
import world.MansionMockModel;
import java.io.*;

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
 /* model */  Mansion mansion;
 /* controller */ Controller controller;

 /* mock model */  MansionMockModel mansion_mock;
 /* mock controller */  ControllerMock controller_mock;

 /**
  * set up the world text file to read.
  * set up the all objects after
  */
 @Before public void setup() {
  String filePath = "C:\\Users\\dongpingchen\\Documents\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt";
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

  //parse in values:
////  mansion.readFile(in);
//mansion_mock.readFile(in);

 }

 /**
  * mock model testing.
  *
  * @throws IOException
  */
 @Test public void mockModelTest() throws IOException {

  controller_mock.playGame(mansion_mock);

  //make all the move(s):
 }







 /*TODO methods testing*/

 /**
  * normal inputs testing.
  */
 @Test public void testPlayGame() throws Exception {
  //TODO: Test goes here...
 }


} 
