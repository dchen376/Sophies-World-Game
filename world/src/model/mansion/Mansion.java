package model.mansion;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.item.Item;
import model.pet.Pet;
import model.player.Player;
import model.room.Room;
import model.target.Target;

/**
 * this is the model class.
 */
public class Mansion implements MansionBuilder {

  private static final int BUFFER_SIZE = 4096; // note declare else where
  // fields
  /* player attributes */
  Set<String> evidenceSet = new HashSet<String>(); // note: declare else where
  private BufferedImage img;
  /* objects info */
  private Pet pet;
  private Item item;
  private Room room;
  private Target target;
  private ArrayList<Player> allPlayers;
  private HashMap<String, String> playersNameRoomMap; // update each time the players/target
  private HashMap<String, ArrayList<String>> playersItemsMap; // *just 'put' new items into this
  private HashMap<String, Integer> turnsMap; // defaults are true -> 1.
  /* graph info */
  private Graphics graph;
  /* world attributes */
  private String worldName;
  /* pet attributes */
  private HashMap<Player, Boolean> petBlessingsMap;
  private HashMap<Integer, Boolean> dfsCheckMap;
  private String petName;
  private int petLocation;

  /* target attributes */
  private String targetName;
  private int targetHealth;
  private int targetLocation;

  /* room attributes */
  private HashMap<String, ArrayList<String>> allNeighborsMap;
  private ArrayList<String> allRoomsNamesLst;
  private ArrayList<ArrayList<ArrayList<Integer>>> listOfRoomCoordinates;
  private HashMap<String, Integer> roomNameIndexMap; // <room name, room Index>
  private int totalRooms;

  /* item attributes */
  private int totalItems;
  private HashMap<String, Integer> totalItemsAllowedMap;
  private HashMap<String, Integer> itemsDamageMap; // <item, damage>
  private HashMap<String, Integer> itemsRoomMap; // <Item, room index>

  // added for milestone4-view:

  /**
   * Constructor.
   */
  public Mansion() {
    this.allNeighborsMap = new HashMap<>();
    // initialize variables:
    this.allPlayers = new ArrayList<>();
    this.totalItemsAllowedMap = new HashMap<>();
    this.roomNameIndexMap = new HashMap<>();
    this.targetHealth = -100; // just a default value for error checking.
    this.targetLocation = 0;
    this.itemsDamageMap = new HashMap<>();
    this.listOfRoomCoordinates = new ArrayList<ArrayList<ArrayList<Integer>>>();
    this.itemsDamageMap = new HashMap<>();
    this.itemsRoomMap = new HashMap<>();
    this.allRoomsNamesLst = new ArrayList<>(); // for room attributes.
    // initialize objects: (x5)
    this.petLocation = 0;
    this.pet = new Pet(this, this.petName, this.petLocation);
    this.target = new Target(this, this.targetName, this.targetHealth, this.targetLocation);
    this.item = new Item(this);
    this.room = new Room(this);
    /* info needs to be updated constantly (maps) */
    this.playersNameRoomMap = new HashMap<>(); // update each time the players/target move
    // related maps below (update first, need to change the second map)
    this.playersItemsMap = new HashMap<>(); // *just 'put' new items into this arrlst.
    // *remove the items in this hashmap once the player pick it.
    this.turnsMap = new HashMap<>();
    this.dfsCheckMap = new HashMap<>();// return false if not checked, so default are all false.
    this.petBlessingsMap = new HashMap<>();
  } // end of the constructor

  /**
   * Read the text file.
   */
  @Override
  public void readFile(Readable readable) { // todo: fix the readFile()
    StringBuilder text = new StringBuilder();
    CharBuffer buffer = CharBuffer.allocate(BUFFER_SIZE);
    try {
      readable.read(buffer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    /*
     * makes a buffer ready for re-reading the data that it already contains: It
     * leaves the limit unchanged and sets the position to zero.
     */
    buffer.flip();
    text.append(buffer.toString()); // this gives the stringBuilder 'text'.

    /*
     * 1st part: parse first 4 line: 35 36 Sophie's World 100 Albert Knag the
     * Mysterious cat 35 //note: not correct!
     */
    /* 1st line parsing */
    String[] lines = text.toString().split("\n"); /// split the text by lines.
    String[] eachLine = lines[0].toString().split(" "); /// put each line into an arraylist.
    totalRooms = Integer.parseInt(eachLine[0]); /// read 1st line info: int total rooms
    totalItems = Integer.parseInt(eachLine[1]); /// read 1st line info: int total items
    StringBuilder eachLineStringBuilder = new StringBuilder();
    for (int i = 2; i < eachLine.length; i++) { /// parsing the first line info after '35 36 ':
      eachLineStringBuilder.append(eachLine[i]).append(" ");
    }
//    eachLineStringBuilder.deleteCharAt(eachLine.length - 1); /// deleting the last appended val: "
    worldName = eachLineStringBuilder.toString().trim(); /// assign 'worldName' to 'Sophie's World'.
    eachLineStringBuilder.setLength(0); /// reset the stringbuilder.
    /* 2nd line parsing */
    eachLine = lines[1].toString().split(" "); // parse 2nd line: 100 Albert Knag
    targetHealth = Integer.parseInt(eachLine[0]); /// 100
    target.setTargetHealth(targetHealth);
    for (int i = 1; i < eachLine.length; i++) {
      eachLineStringBuilder.append(eachLine[i]).append(" ");
    }
    eachLineStringBuilder.deleteCharAt(eachLine.length - 1); /// Albert Knag
    targetName = eachLineStringBuilder.toString().trim(); /// assign 'targetName' to 'Albert Knag'.
    eachLineStringBuilder.setLength(0); // reset the stringbuilder.
//    System.out.println("target nameee is: " + targetName);
    target.setTargetName(targetName);

    /* 3rd line parsing */
    String parsePet = lines[2].toString().trim();

    this.petName = parsePet;
    this.pet.setPetName(this.petName);

    /* parse the 4th line: 35 (rooms) */
    String totalRoomStr = lines[3].toString().trim();
    int totalRoom = Integer.parseInt(totalRoomStr);
    for (int i = 0; i < totalRoom; i++) {
      this.dfsCheckMap.put(i, false); // false means not checke.d
    }

    /*
     * 2nd part: parse room information
     */
    /// cut the first 4 lines of the text as they already been parsed:
    lines = Arrays.copyOfRange(lines, 4, lines.length);
    /// start reading from the 4th line: (room information)
    for (int i = 0; i < totalRooms; i++) { // totalRooms
      ArrayList<Integer> coordinateLeftTop = new ArrayList<>(); /// (x1,y1) upper left.
      ArrayList<Integer> coordinateRightBot = new ArrayList<>(); // (x2, y2) lower right.
      ArrayList<ArrayList<Integer>> singleRoomCoordinates = new ArrayList<ArrayList<Integer>>();
      /// parse and add (x1,y1) into 'singleRoomCoordinates':
      eachLine = lines[i].toString().split(" ");
      int x1 = Integer.parseInt(eachLine[0]);
      int y1 = Integer.parseInt(eachLine[1]);
      coordinateLeftTop.add(x1);
      coordinateLeftTop.add(y1);
      singleRoomCoordinates.add(coordinateLeftTop); // now singleRoomCoordinates is: {x1, y1}
      /// parse and add(x2,y2) into 'singleRoomCoordinates':
      int x2 = Integer.parseInt(eachLine[2]);
      int y2 = Integer.parseInt(eachLine[3]);
      coordinateRightBot.add(x2);
      coordinateRightBot.add(y2);
      singleRoomCoordinates.add(coordinateRightBot); // now singleRoomCoordinates is:
      listOfRoomCoordinates.add(singleRoomCoordinates); /// { {{x1,y1},{x2,y2}}, ..., ... }
      /// parsing the final string names of rooms:
      for (int j = 4; j < eachLine.length; j++) {
        eachLineStringBuilder.append(eachLine[j]).append(" ");
      }
      String strRoomName = eachLineStringBuilder.toString().trim().toLowerCase();
      eachLineStringBuilder.setLength(0); // reset the stringbuilder.
      /// add to the final arraylist: 'roomNames'
      this.roomNameIndexMap.put(strRoomName, i);
      this.allRoomsNamesLst.add(strRoomName);
    }
    /*
     * 3rd part: parse the items information.
     */
    /// cut the lines again to only left with the rooms information to parse:
    lines = Arrays.copyOfRange(lines, totalRooms + 1, lines.length);

    /// terate over evey lines for reading items information:
    for (int i = 0; i < lines.length; i++) {
      eachLine = lines[i].toString().split(" ");
      int itemRoom = Integer.parseInt(eachLine[0]); /// parse the item index as Integer.
      int itemDamage = Integer.parseInt(eachLine[1]); /// parse the damage done by item as String.
      for (int j = 2; j < eachLine.length; j++) {
        eachLineStringBuilder.append(eachLine[j]).append(" "); /// parse the rest as String: the
      }
      String strItemName = eachLineStringBuilder.toString().trim().toLowerCase();
      eachLineStringBuilder.setLength(0); /// reset the string.
      /// (1) PUT to hashmap 'itemDamgeMap' during each iteration:
      itemsDamageMap.put(strItemName.toLowerCase(), itemDamage);
      /// (2) Put to hashmap ' itemRoomMap':
      itemsRoomMap.put(strItemName.toLowerCase(), itemRoom);
      /// (3) update allNeighborsMap:
      this.allNeighborsMap = this.getRoom().getAllNeighborsMap();
    } // end of the for-loop for reading the information of items.
  } // end of method readFile().

  /**
   * draw the World and save it as a buffered Image.
   */
  @Override
  public void drawWorld() {
    this.img = new BufferedImage(1300, 1300, BufferedImage.TYPE_INT_RGB);
    graph = img.createGraphics();
    for (int i = 0; i < this.totalRooms; i++) {
      int x1;
      int y1;
      int x2;
      int y2;
      int width;
      int height;
      // get each room's name:
      String name = this.allRoomsNamesLst.get(i);
      ArrayList<ArrayList<Integer>> lst = this.listOfRoomCoordinates.get(i);
      x1 = lst.get(0).get(0);
      y1 = lst.get(0).get(1);
      x2 = lst.get(1).get(0);
      y2 = lst.get(1).get(1);
      width = x2 - x1;
      height = y2 - y1;
      graph.drawRect(x1 * 30, y1 * 30, width * 30, height * 30);
      graph.setColor(Color.blue);
      graph.fillRect(x1 * 30, y1 * 30, width * 30, height * 30);
      graph.setColor(Color.black);
      graph.drawRect(x1 * 30, y1 * 30, width * 30, height * 30);
      graph.setColor(Color.red);
      graph.drawString(name, x1 * 30, y1 * 30 + 30); // (height * 30 / 2) + y2);
    }
    // JFrame for display the image created above.
    JFrame editorFrame = new JFrame(worldName);
    editorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ImageIcon imageIcon = new ImageIcon(img);
    JLabel label = new JLabel();
    label.setIcon(imageIcon);
    editorFrame.getContentPane().add(label, BorderLayout.CENTER);
    editorFrame.pack();
    editorFrame.setLocationRelativeTo(null);
    editorFrame.setVisible(true);
    // save image:
    try {
      // BufferedImage bi = getMyImage(); // retrieve image
      File outputfile = new File("saved.png");
      ImageIO.write(img, "png", outputfile);
    } catch (IOException e) {
      // handle exception
    }
  }

  @Override
  public ArrayList<String> getWelcomeMessage() {
    ArrayList<String> welcome = new ArrayList<>();
    welcome.add("Welcome!!");
    welcome.add(String.format("Welcome to the game: %s", this.worldName));
    welcome.add("Welcome, Players!!");
    welcome.add("Our target in this game is: " + this.getTargetName());
    welcome.add("Target's initial health: " + this.getTargetHealth());
    welcome.add("Our magical pet is: " + this.getPetName());
    welcome.add("HOORAYYYY!!!");
    return welcome;
  }

  @Override
  public ArrayList<String> getBeforeGameMessage(int turns) {
    ArrayList<String> welcome = new ArrayList<>();
    welcome.add(String.format("All Right!! Game Staring SOON!!! :P "));
    welcome.add(String.format("There are %d players in this game!", this.getAllPlayers().size()));
    welcome.add(String.format("Target and Pet both starting at the first starting room: %s",
        this.allRoomsNamesLst.get(0)));
    for (Player player : this.getAllPlayers()) {
      welcome.add(String.format("Player, %s, chose to start at room: %s", player.getPlayerName(),
          player.getPlayerRoom()));
    }
    welcome.add(String.format("there are %d turns for this game! GOOD LUCK!!!", turns));
    welcome.add("Game STARTING NOW!");
    return welcome;
  }

  /* below are only getters methods. */
  @Override
  public HashMap<Player, Boolean> getPetBlessingsMap() {
    return petBlessingsMap;
  }

  @Override
  public HashMap<Integer, Boolean> getDfsCheckMap() {
    return dfsCheckMap;
  }

  // getters & setters.

  /**
   * getter.
   */
  @Override
  public ArrayList<Player> getAllPlayers() {
    return allPlayers;
  }

  /**
   * getter.
   * 
   * @return hashmap
   */
  @Override
  public HashMap<String, Integer> getTotalItemsAllowedMap() {
    // return this.getAllPlayers().get(0).getTotalItemsAllowedMap();
    return totalItemsAllowedMap;
  }

  /**
   * getter.
   * 
   * @return graphics for the graph
   */
  @Override
  public Graphics getGraph() {
    return graph;
  }

  /**
   * getter.
   * 
   * @return total items
   */
  @Override
  public int getTotalItems() {
    return totalItems;
  }

  /**
   * getter.
   * 
   * @return total rooms
   */
  @Override
  public int getTotalRooms() {
    return totalRooms;
  }

  /**
   * getter.
   * 
   * @return item
   */
  @Override
  public Item getItem() {
    return item;
  }

  /**
   * getter.
   * 
   * @return room
   */
  @Override
  public Room getRoom() {
    return room;
  }

  /**
   * getter.
   * 
   * @return name of the world
   */
  @Override
  public String getWorldName() {
    return worldName;
  }

  /**
   * getter.
   * 
   * @return get target location
   */
  @Override
  public Target getTarget() {
    return target;
  }

  /**
   * getter.
   * 
   * @return arraylist
   */
  @Override
  public ArrayList<String> getAllRoomsNamesLst() {
    return allRoomsNamesLst;
  }

  /**
   * getter.
   * 
   * @return hashmap
   */
  @Override
  public HashMap<String, String> getPlayersNameRoomMap() {
    return playersNameRoomMap;
  }

  /**
   * getter.
   * 
   * @return hashmap
   */
  @Override
  public HashMap<String, ArrayList<String>> getPlayersItemsMap() {
    return playersItemsMap;
  }

  /**
   * getter.
   * 
   * @return hashmap
   */
  @Override
  public HashMap<String, Integer> getTurnsMap() {
    return turnsMap;
  }

  /**
   * getter.
   * 
   * @return arraylist for all the rooms' coordinates
   */
  @Override
  public ArrayList<ArrayList<ArrayList<Integer>>> getListOfRoomCoordinates() {
    return listOfRoomCoordinates;
  }

  /**
   * getter.
   * 
   * @return hashmap
   */
  @Override
  public HashMap<String, ArrayList<String>> getAllNeighborsMap() {
    return allNeighborsMap;
  }

  /**
   * getter.
   * 
   * @return target's health
   */
  @Override
  public int getTargetHealth() {
    return this.target.getTargetHealth();
  }

  /**
   * getter.
   * 
   * @return target location
   */
  @Override
  public int getTargetLocation() {
    return this.target.getTargetLocation();
  }

  /**
   * getter.
   * 
   * @return target name
   */
  @Override
  public String getTargetName() {
    return this.targetName;
  }

  /**
   * getter.
   * 
   * @return hashmap
   */
  @Override
  public HashMap<String, Integer> getRoomNameIndexMap() {
    return this.roomNameIndexMap;
  }

  /**
   * getter.
   * 
   * @return hashmap
   */
  @Override
  public HashMap<String, Integer> getItemsRoomMap() {
    return this.itemsRoomMap;
  }

  /**
   * getter.
   * 
   * @return hashmap
   */
  @Override
  public HashMap<String, Integer> getItemsDamageMap() {
    return this.itemsDamageMap;
  }

  /* getter */
  @Override
  public String getPetName() {
    return petName;
  }

  // setter
  @Override
  public void setPetName(String petName) {
    this.petName = petName;
  }

  // getter
  @Override
  public int getPetLocation() {
    return petLocation;
  }

  // setter
  @Override
  public void setPetLocation(int petLocation) {
    this.petLocation = petLocation;
  }

  // getter
  @Override
  public Pet getPet() {
    return pet;
  }

  /* getter */
  @Override
  public Set<String> getEvidenceSet() {
    return evidenceSet;
  }

  @Override
  public BufferedImage getImg() {
    return img;
  }



  //newly added:
@Override
  public void setAllPlayers(ArrayList<Player> allPlayers) {
    this.allPlayers = allPlayers;
  }
} // end of Mansion.java
