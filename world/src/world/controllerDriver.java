package world;

import java.io.*;
import java.util.Scanner;

public class controllerDriver {

  //"C:\\Users\\dongpingchen\\Documents\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt"; //windows
  //"/Users/dongping/Documents/GitHub/PDP---Milestone-Mansion-Game-/world.txt"; //mac
 public static void main(String[] args) throws IOException {
  Mansion mansion = new Mansion(); //model
  BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
  StringBuilder strb = new StringBuilder();
  Controller controller = new Controller(bf, strb); //controller
  controller.playGame(mansion);
 } //end of main()

}
