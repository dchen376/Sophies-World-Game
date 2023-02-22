package world;

import java.io.*;
import java.util.Scanner;

public class controllerDriver {

 public static void main(String[] args) throws IOException {
  Mansion mansion = new Mansion();
  StringBuilder strb = new StringBuilder();
//  String worldInput = "C:\\Users\\dongpingchen\\Documents\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt"; //windows
//  String worldInput = "/Users/dongping/Documents/GitHub/PDP---Milestone-Mansion-Game-/world.txt"; //mac
//  File worldFile = new File(worldInput);

//  Controller controller = null;
//   FileReader mansionFr = new FileReader(worldFile);
//   mansion.readFile(mansionFr);
   BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));
   Controller controller = new Controller(bf, strb);
//   mansionFr.close();
//   playersFr.close();

//  mansion.drawWorld();

  controller.playGame();


 } //end of main()

}
