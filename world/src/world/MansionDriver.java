package world;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class MansionDriver {
  public static void main(String[] args) {

    ArrayList<String> playersRoomNames = new ArrayList<String>(
        Arrays.asList("The Top Hat", "Socrates"));

    Mansion ms = new Mansion();

    // C:\Users\dongpingchen\Documents\GitHub\milestone-2---controller\world.txt

    // Create a Scanner object
    Scanner myObj = new Scanner(System.in);

    // first, to drive the Mansion object:
    System.out.println("First, we will test the Mansion class!!");
    System.out.println("Please enter the text file path to read: ");
    String userInput = "C:\\Users\\dongpingchen\\Documents\\GitHub\\milestone-2---controller\\world.txt";
    // String userInput = myObj.nextLine();
    File file = new File(userInput);
    try {
      // Creates a reader using the FileReader
      FileReader input = new FileReader(file);
      // Reads the 'readable'
      ms.readFile(input);
      // Closes the reader
      input.close();
    } catch (Exception e) {
      e.getStackTrace();
    }
    ms.drawWorld();
    System.out.println("All the room names are: " + ms.getAllRoomsNamesLst());
    System.out.println("All the coordinates are of each room: " + ms.getListOfRoomCoordinates());
    System.out.println("The target's name is: " + ms.getTargetName());
    System.out.println("The target's total health is: " + ms.getTargetHealth());
    System.out.println("The damage of each item is: " + ms.getItemsDamageMap());

    // second, to drive the item object:
    System.out.println();
    System.out.println("Second, we will test the Item class!!");

    System.out.println("Please input the name of the item for getting its damage info: ");
    String itemName = myObj.nextLine();
    System.out.println(
        "The damage of the " + itemName + " item is: " + ms.getItem().getDamageAmount(itemName));
    System.out.println("Please input the name of the item for getting its location: ");
    String itemLocation = myObj.nextLine();
    System.out.println("The location of the " + itemLocation + " item is: "
        + ms.getItem().getItemLocation(itemLocation));

    // third, to drive the Target object:
    System.out.println();
    System.out.println("Third, we will test the Target class!!");
    System.out.println("The current health of the target is: " + ms.getTarget().getTargetHealth());
    System.out.println(
        "After moving the target, his/her current postion is: " + ms.getTarget().moveTarget());

    // fourth, to drive the room object:
    System.out.println();
    System.out.println("Forth, we will test the Room class!!");
    System.out.println("please enter the room name below to find out its neighboring rooms: ");
    String roomName = myObj.nextLine();
    System.out.println(
        "The neighboring rooms for " + roomName + " is: " + ms.getRoom().getNeighbors(roomName));
    System.out.println("please enter the room name below to get its room infomation: ");
    roomName = myObj.nextLine();
    System.out.println(ms.getRoom().displayRoomInfo(roomName));

    // finished testing.
    System.out.println();
    System.out.println("******** ******* ********");
    System.out.println("You've reached the end of the testing for this driver's class.");
    System.out.println("GREAT Job!!!");
    System.out.println();
    System.out.println("YAY~!");
    System.out.println("******** ******* ********");

  } // end of the main() method.
} // end of the driver class.
