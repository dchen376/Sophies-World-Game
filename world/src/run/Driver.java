package run;

import controller.Controller;
import model.mansion.Mansion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * this is the dirver for milestone 2.
 */
public class Driver {

  /* jar command line */
  // java -jar
  // "C:\Users\dongpingchen\Documents\GitHub\
  // PDP---Milestone-Mansion-Game-\out\artifacts\world_jar\world.jar"

  /* windows */
//   C:\\Users\\dongpingchen\\Documents\\GitHub\\PDP---Milestone-Mansion-Game-\\world.txt

  /* mac */
  // /Users/dongping/Documents/GitHub/PDP---Milestone-Mansion-Game-/world.txt

/* linux*/
// /home/dongping/Documents/github repo/milestone/PDP---Milestone-Mansion-Game-/text files/world.txt
  /**
   * the main() method for driver.
   * @param args the system arguments.
   * @throws IOException if is the wrong file.
   */
  public static void main(String[] args) throws IOException {
    Mansion mansion = new Mansion(); // model
    System.out.println("please enter the file path to read: \n");
//    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
//    FileReader fr = new FileReader("/home/dongping/Documents/github repo/milestone/PDP---Milestone-Mansion-Game-/text files/world.txt");
//    BufferedReader bf = new BufferedReader(fr);

    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder strb = new StringBuilder();
    Controller controller = new Controller(bf, strb); // controller
    controller.playGame(mansion);


    //mac: /Users/dongping/Desktop/milestone 3/world.txt

  } // end of main()

}