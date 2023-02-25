package world;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * this is the dirver for milestone 2.
 */
public class controllerDriver {

  // "C:\\Users\\dongpingchen\\Documents\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt";
  // //windows
  // "/Users/dongping/Documents/GitHub/PDP---Milestone-Mansion-Game-/world.txt";
  // //mac
  public static void main(String[] args) throws IOException {
    Mansion mansion = new Mansion(); // model
    System.out.println("please enter the file path to read: \n");
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder strb = new StringBuilder();
    Controller controller = new Controller(bf, strb); // controller
    controller.playGame(mansion);
  } // end of main()

}
