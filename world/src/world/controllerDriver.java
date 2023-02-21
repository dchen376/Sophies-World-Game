package world;

import java.io.*;
import java.util.Scanner;

public class controllerDriver {

 public static void main(String[] args) {
  Mansion mansion = new Mansion();
//  Player player = new player();
  StringBuilder strb = new StringBuilder();

//  String worldInput = "C:\\Users\\dongpingchen\\Documents\\GitHub\\milestone-2---controller\\world.txt"; //windows
  String worldInput = "/Users/dongping/Documents/GitHub/PDP---Milestone-Mansion-Game-/world.txt"; //windows

  File worldFile = new File(worldInput);
//  String playerInput = "C:\\Users\\dongpingchen\\Documents\\GitHub\\milestone-2---controller\\players.txt";
//  File playersFile = new File(playerInput);

  try {
   FileReader mansionFr = new FileReader(worldFile);
   mansion.readFile(mansionFr);
//   FileReader playersFr = new FileReader(playersFile);
   BufferedReader bf= new BufferedReader(new InputStreamReader(System.in));

   Controller controller = new Controller(bf , mansionFr, strb);
   mansionFr.close();
//   playersFr.close();
  } catch (IOException e) {
   e.printStackTrace();
  }

  mansion.drawWorld();



 } //end of main()

}
