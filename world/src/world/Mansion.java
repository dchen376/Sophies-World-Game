package world;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * this is the model class.
 */
public class Mansion implements MansionBuilder {
  private static final int BUFFER_SIZE = 4096;
  /**
   * private fields & private objects.
   */
  private HashMap<String, ArrayList<String>> allNeighborsMap;
  private String worldName;
  private String targetName;
  private int targetHealth;
  private int targetLocation;
  /* room attributes */
  private ArrayList<String> allRoomsNamesLst;
  private ArrayList<ArrayList<ArrayList<Integer>>> listOfRoomCoordinates;
  private HashMap<String, Integer> roomNameIndexMap; // <room name, room Index>
  private int totalRooms;
  /* item attributes */
  private int totalItems;
  private HashMap<String, Integer> totalItemsAllowedMap;
  private HashMap<String, Integer> itemsDamageMap; // <item, damage>
  private HashMap<String, Integer> itemsRoomMap; // <Item, room index>
  /* graph info */
  private Graphics graph;
  /* objects info */
  private Item item;
  private Room room;
  private Target target;
  private HashMap<String, String> playersTargetNameRoomMap; // update each time the players/target
  private HashMap<String, ArrayList<String>> playersItemsMap; // *just 'put' new items into this
  private HashMap<String, Integer> turnsMap; // defaults are true -> 1.
  private ArrayList<Player> allPlayers;

  /**
   * Constructor.
   */
  public Mansion() {
    this.allNeighborsMap = new HashMap<>();
    // initialize variables:
    this.allPlayers = new ArrayList<>();
    this.totalItemsAllowedMap = new HashMap<>();
    this.roomNameIndexMap = new HashMap<>();
    this.targetHealth = targetHealth;
    this.targetLocation = 0;
    this.itemsDamageMap = new HashMap<>();
    this.listOfRoomCoordinates = new ArrayList<ArrayList<ArrayList<Integer>>>();
    this.itemsDamageMap = new HashMap<>();
    this.itemsRoomMap = new HashMap<>();
    this.allRoomsNamesLst = new ArrayList<>(); // for room attributes.
    // initialize objects: (x5)
    this.item = new Item(this.getItemsDamageMap(), this.getItemsRoomMap());
    this.target = new Target(targetName, targetHealth, targetLocation, itemsRoomMap,
        this.itemsDamageMap);
    this.room = new Room(this.itemsRoomMap, this.roomNameIndexMap, this.listOfRoomCoordinates,
        this.allRoomsNamesLst, this.allNeighborsMap);
    /* info needs to be updated constantly (maps) */
    this.playersTargetNameRoomMap = new HashMap<>(); // update each time the players/target move
    // related maps below (update first, need to change the second map)
    this.playersItemsMap = new HashMap<>(); // *just 'put' new items into this arrlst.
    // *remove the items in this hashmap once the player pick it.
    this.turnsMap = new HashMap<>();
  } // end of the constructor

  /**
   * Read the text file.
   */
  public void readFile(Readable readable) {
    StringBuilder text = new StringBuilder();
    CharBuffer buffer = CharBuffer.allocate(BUFFER_SIZE);
    try {
      readable.read(buffer);
    } catch (IOException e) {
      e.printStackTrace();
    }
    buffer.flip();
    text.append(buffer.toString()); // this gives the stringBuilder 'text'.
    /*
     * 1st part: parse first 3 line: 35 36 Sophie's World 100 Albert Knag 35
     */
    String[] lines = text.toString().split("\n"); /// split the text by lines.
    String[] eachLine = lines[0].toString().split(" "); /// put each line into an arraylist.
    totalRooms = Integer.parseInt(eachLine[0]); /// read 1st line info: int total rooms
    totalItems = Integer.parseInt(eachLine[1]); /// read 1st line info: int total items
    StringBuilder eachLineStringBuilder = new StringBuilder();
    for (int i = 2; i < eachLine.length; i++) { /// parsing the first line info after '35 36 ':
      eachLineStringBuilder.append(eachLine[i]).append(" ");
    }
    eachLineStringBuilder.deleteCharAt(eachLine.length - 1); /// deleting the last appended val: "
    worldName = eachLineStringBuilder.toString(); /// assign 'worldName' to 'Sophie's World'.
    eachLineStringBuilder.setLength(0); /// reset the stringbuilder.
    eachLine = lines[1].toString().split(" "); // parse 2nd line: 100 Albert Knag
    targetHealth = Integer.parseInt(eachLine[0]); /// 100
    target.setTargetHealth(targetHealth);
    for (int i = 1; i < eachLine.length; i++) {
      eachLineStringBuilder.append(eachLine[i]).append(" ");
    }
    eachLineStringBuilder.deleteCharAt(eachLine.length - 1); /// Albert Knag
    targetName = eachLineStringBuilder.toString(); /// assign 'targetName' to 'Albert Knag'.
    eachLineStringBuilder.setLength(0); // reset the stringbuilder.
    target.setTargetName(targetName);

    /*
     * 2nd part: parse room information
     */
    /// cut the first 3 lines of the text as they already been parsed:
    lines = Arrays.copyOfRange(lines, 3, lines.length);
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
      String strRoomName = eachLineStringBuilder.toString().trim();
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
      String strItemName = eachLineStringBuilder.toString().trim();
      eachLineStringBuilder.setLength(0); /// reset the string.
      /// (1) PUT to hashmap 'itemDamgeMap' during each iteration:
      itemsDamageMap.put(strItemName, itemDamage);
      /// (2) Put to hashmap ' itemRoomMap':
      itemsRoomMap.put(strItemName, itemRoom);
      /// (3) update allNeighborsMap:
      this.allNeighborsMap = this.getRoom().getAllNeighborsMap();
    } // end of the for-loop for reading the information of items.
  } // end of method readFile().

  /**
   * draw the World and save it as a buffered Image.
   */
  @Override
  public void drawWorld() {
    BufferedImage img = new BufferedImage(1300, 1300, BufferedImage.TYPE_INT_RGB);
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


  /**
   * all the getter methods below are below.
   */
  public ArrayList<Player> getAllPlayers() {
    return allPlayers;
  }

  /**
   * getter.
   * @return hashmap
   */
  public HashMap<String, Integer> getTotalItemsAllowedMap() {
    // return this.getAllPlayers().get(0).getTotalItemsAllowedMap();
    return totalItemsAllowedMap;
  }

  /**
   * getter.
   * @return graphics for the graph
   */
  public Graphics getGraph() {
    return graph;
  }

  /**
   * getter.
   * @return total items
   */
  public int getTotalItems() {
    return totalItems;
  }

  /**
   * getter.
   * @return total rooms
   */
  public int getTotalRooms() {
    return totalRooms;
  }

  /**
   * getter.
   * @return item
   */
  public Item getItem() {
    return item;
  }

  /**
   * getter.
   * @return room
   */
  public Room getRoom() {
    return room;
  }

  /**
   * getter.
   * @return name of the world
   */
  public String getWorldName() {
    return worldName;
  }

  /**
   * getter.
   * @return get target location
   */
  public Target getTarget() {
    return target;
  }

  /**
   * getter.
   * @return arraylist
   */
  public ArrayList<String> getAllRoomsNamesLst() {
    return allRoomsNamesLst;
  }

  /**
   * getter.
   * @return hashmap
   */
  public HashMap<String, String> getPlayersTargetNameRoomMap() {
    return playersTargetNameRoomMap;
  }

  /**
   * getter.
   * @return hashmap
   */
  public HashMap<String, ArrayList<String>> getPlayersItemsMap() {
    return playersItemsMap;
  }

  /**
   * getter.
   * @return hashmap
   */
  public HashMap<String, Integer> getTurnsMap() {
    return turnsMap;
  }

  /**
   * getter.
   * @return arraylist for all the rooms' coordinates
   */
  @Override
  public ArrayList<ArrayList<ArrayList<Integer>>> getListOfRoomCoordinates() {
    return listOfRoomCoordinates;
  }

  /**
   * getter.
   * @return hashmap
   */
  @Override
  public HashMap<String, ArrayList<String>> getAllNeighborsMap() {
    return allNeighborsMap;
  }

  /**
   * getter.
   * @return target's health
   */
  @Override
  public int getTargetHealth() {
    return targetHealth;
  }

  /**
   * getter.
   * @return target location
   */
  @Override
  public int getTargetLocation() {
    return targetLocation;
  }

  /**
   * getter.
   * @return target name
   */
  @Override
  public String getTargetName() {
    return this.targetName;
  }

  /**
   * getter.
   * @return hashmap
   */
  @Override
  public HashMap<String, Integer> getRoomNameIndexMap() {
    return this.roomNameIndexMap;
  }

  /**
   * getter.
   * @return hashmap
   */
  @Override
  public HashMap<String, Integer> getItemsRoomMap() {
    return this.itemsRoomMap;
  }

  /**
   * getter.
   * @return hashmap
   */
  @Override
  public HashMap<String, Integer> getItemsDamageMap() {
    return this.itemsDamageMap;
  }
} // end of Mansion class.
