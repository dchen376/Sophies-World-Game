package world;

import java.io.*;
import java.util.Scanner;

public class controllerDriver {

 public static void main(String[] args) {
  Mansion ms = new Mansion();
//  Player player = new player();
  StringBuilder strb = new StringBuilder();

  String worldInput = "C:\\Users\\dongpingchen\\Documents\\GitHub\\milestone-2---controller\\world.txt";
  File worldFile = new File(worldInput);
//  String playerInput = "C:\\Users\\dongpingchen\\Documents\\GitHub\\milestone-2---controller\\players.txt";
//  File playersFile = new File(playerInput);

  try {
   FileReader mansionFr = new FileReader(worldFile);
   ms.readFile(mansionFr);
//   FileReader playersFr = new FileReader(playersFile);
//   player
   Controller controller = new Controller(playersFr, strb , mansionFr);
   mansionFr.close();
//   playersFr.close();
  } catch (IOException e) {
   e.printStackTrace();
  }

  ms.drawWorld();



 } //end of main()

}
