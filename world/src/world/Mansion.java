package world;

import java.io.File;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout; //for display Jframe
import java.awt.Color;
import java.util.HashMap;

public class Mansion implements MansionBuilder {

 //private fields: (x24)
 //world attributes:
 private String worldName;
 private String targetName;
 // private int numPlayers;
 //target attributes:
 private int targetHealth;
 private int targetLocation;
 //room attributes:
 private ArrayList<String> allRoomsNamesLst;
 private ArrayList<ArrayList<ArrayList<Integer>>> listOfRoomCoordinates; //room Coords store in arraylist.
 private HashMap<String, Integer> roomNameIndexMap; //<room name, room Index>
 private HashMap<String, ArrayList<String>> allNeighborsMap;

 private int totalRooms;
 //item attributes
 private int totalItems;
 private HashMap<String, Integer> totalItemsAllowedMap;
 private HashMap<String, Integer> itemsDamageMap; // <item, damage>
 private HashMap<String, Integer> itemRoomMap; //<Item, room index>
 //graph info
 private Graphics graph;
 private static final int BUFFER_SIZE = 4096;
 //objects info: Item, room, target, orderplayer, player.
 private Item item;
 private Room room;
 private Target target;

 /*for consistently needed to update maps: */
 private HashMap<String, String> playersTargetNameRoomMap; // update each time the players/target move
 //related maps below (update first, need to change the second map)
 private HashMap<String, ArrayList<String>> playersItemsMap; //*just 'put' new items into this arrlst.
 private HashMap<String, Integer> itemsRoomMap; //*remove the items in this hashmap once the player pick it.
 private HashMap<String, Integer> turnsMap; //defaults are true -> 1.
 //2. constructor

 public Mansion() {
  //initialize variables:
  //  this.numPlayers = numPlayers;
  //  this.playersNameLst = playersNameLst;
  this.totalItemsAllowedMap = new HashMap<>();
  //  this.turnsMap = turns;
  //  this.computerOrHumanLst = computerOrHuman;
  this.roomNameIndexMap = new HashMap<>();
  this.targetHealth = targetHealth;
  this.targetLocation = 0;
  //  this.playerItemsMap = playerItemsMap;
  this.itemsDamageMap = new HashMap<>();
  //  this.playersNameRoomMap = new HashMap<>();

  this.listOfRoomCoordinates = new ArrayList<ArrayList<ArrayList<Integer>>>();
  this.roomNameIndexMap = new HashMap<>();
  this.itemsDamageMap = new HashMap<>();
  this.itemRoomMap = new HashMap<>();
  this.allNeighborsMap = new HashMap<>();
  //  this.playersRoomNamesLst = playersRoomNames;
  this.allRoomsNamesLst = new ArrayList<>(); // for room attributes.
  //  this.allPlayersLst = new ArrayList<>();

  //initialize objects: (x5)
  this.item = new Item(getItemsDamageMap(), getItemRoomMap());
  this.target = new Target(targetName, targetHealth, targetLocation, itemRoomMap,
      this.itemsDamageMap);
  this.room = new Room(this.itemRoomMap, roomNameIndexMap, listOfRoomCoordinates, allRoomsNamesLst,
      allNeighborsMap);

  /*info needs to be updated constantly (maps)*/
  this.playersTargetNameRoomMap = new HashMap<>();// update each time the players/target move
  //related maps below (update first, need to change the second map)
  this.playersItemsMap = new HashMap<>(); //*just 'put' new items into this arrlst.
  this.itemsRoomMap = new HashMap<>();
  //*remove the items in this hashmap once the player pick it.
  this.turnsMap = new HashMap<>();

 }//end of the constructor

 //3. methods:

 //getters:
 // public ArrayList<String> getPlayersNameLst() {
 //  return this.playersNameLst;
 // }

 /**
  * this method will read the provided .text file
  * and assign the appropriate values to each defined variables in this class.
  *
  * @paramReadable
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
  text.append(buffer.toString()); //this gives the stringBuilder 'text'.

  /**
   * 1st part: parse first 3 line:
   *
   * 35 36 Sophie's World
   * 100 Albert Knag
   * 35
   */
  String[] lines = text.toString().split("\n"); ///split the text by lines.
  String[] eachLine = lines[0].toString().split(" "); ///put each line into an arraylist.
  totalRooms = Integer.parseInt(eachLine[0]); ///read 1st line info: int total rooms
  totalItems = Integer.parseInt(eachLine[1]); ///read 1st line info: int total items
  /**
   * set the world's name.
   */
  StringBuilder eachLineStringBuilder = new StringBuilder();
  for (int i = 2; i < eachLine.length; i++) { ///parsing the first line info after '35 36 ': Sophie's World.
   eachLineStringBuilder.append(eachLine[i]).append(" ");
  }
  eachLineStringBuilder.deleteCharAt(eachLine.length - 1); ///deleting the last appended val: " ".
  //initialize the world's name.
  worldName = eachLineStringBuilder.toString(); ///assign 'worldName' to 'Sophie's World'.
  eachLineStringBuilder.setLength(0); ///reset the stringbuilder.

  /**
   * set the target's health.
   * set the target's name.
   */
  eachLine = lines[1].toString().split(" ");/// parse 2nd line: 100 Albert Knag
  targetHealth = Integer.parseInt(eachLine[0]); ///100
  target.setTargetHealth(targetHealth);
  for (int i = 1; i < eachLine.length; i++) {
   eachLineStringBuilder.append(eachLine[i]).append(" ");
  }
  eachLineStringBuilder.deleteCharAt(eachLine.length - 1); ///Albert Knag
  targetName = eachLineStringBuilder.toString(); ///assign 'targetName' to 'Albert Knag'.
  eachLineStringBuilder.setLength(0); //reset the stringbuilder.
  target.setTargetName(targetName);

  /**
   * 2nd part: parse room information
   *
   * 18 0 22 5 The Garden of Eden
   * 15 4 18 7 The Top Hat
   * 14 7 17 10 The Myths
   * 13 10 16 15 The Natural Philosophers
   * 21 5 24 12 Democritus
   * 21 12 23 22 Fate
   * 23 12 26 18 Socrates
   * 21 18 24 22 Athens
   * 16 10 21 24 Plato
   * 10 16 16 31 The Major's Cabin
   * 21 22 26 34 Aristotle
   * 16 24 21 33 Hellenism
   * 8 30 13 33 The Postcards
   * 3 16 10 30 Two Cultures
   * 24 4 29 12 The Middle Ages
   * 26 12 34 18 The Renaissance
   * 24 18 36 22 The Baroque
   * 34 9 40 18 Descartes
   * 26 22 40 37 Spinoza
   * 8 5 12 15 Locke
   * 0 15 10 16 Hume
   * 3 6 5 15 Berkeley
   * 0 0 3 15 Bjerkely
   * 0 16 3 25 The Enligntenment
   * 0 25 2 29 Kant
   * 0 29 1 37 Romanticism
   * 1 30 4 34 Hegel
   * 1 33 5 40 Kierkegaard
   * 4 30 6 34 Marx
   * 6 33 8 36 Darwin
   * 5 36 12 40 Freud
   * 8 33 11 36 Our Own Time
   * 11 33 15 36 The Garden Party
   * 13 36 15 39 Counterpoint
   * 15 33 26 40 The Big Bang
   */

  ///cut the first 3 lines of the text as they already been parsed:
  lines = Arrays.copyOfRange(lines, 3, lines.length);

  ///start reading from the 4th line: (room information)
  for (int i = 0; i < totalRooms; i++) { //totalRooms
   ArrayList<Integer> coordinateLeftTop = new ArrayList<>(); ///(x1,y1) upper left.
   ArrayList<Integer> coordinateRightBot = new ArrayList<>();///(x2, y2) lower right.
   ArrayList<ArrayList<Integer>> singleRoomCoordinates = new ArrayList<ArrayList<Integer>>();

   ///parse and add (x1,y1) into 'singleRoomCoordinates':
   eachLine = lines[i].toString().split(" ");
   int x1 = Integer.parseInt(eachLine[0]);
   int y1 = Integer.parseInt(eachLine[1]);
   coordinateLeftTop.add(x1);
   coordinateLeftTop.add(y1);
   singleRoomCoordinates.add(coordinateLeftTop);///now singleRoomCoordinates is: {x1, y1}
   ///parse and add(x2,y2) into 'singleRoomCoordinates':
   int x2 = Integer.parseInt(eachLine[2]);
   int y2 = Integer.parseInt(eachLine[3]);
   coordinateRightBot.add(x2);
   coordinateRightBot.add(y2);
   singleRoomCoordinates.add(coordinateRightBot); //now singleRoomCoordinates is: {{x1,y1},{x2,y2}}
   ///add to the final list: 'listOfRoomCoordinates'
   listOfRoomCoordinates.add(singleRoomCoordinates); /// { {{x1,y1},{x2,y2}}, ..., ... }

   /// parsing the final string names of rooms:
   for (int j = 4; j < eachLine.length; j++) {
    eachLineStringBuilder.append(eachLine[j]).append(" ");
   }
   String strRoomName = eachLineStringBuilder.toString().trim();
   eachLineStringBuilder.setLength(0);//reset the stringbuilder.
   ///add to the final arraylist: 'roomNames'
   this.roomNameIndexMap.put(strRoomName, i);
   this.allRoomsNamesLst.add(strRoomName);
  }

  /**
   * 3rd part: parse the items information.
   *
   * 36
   * 0 3 apple
   * 0 4 Eve
   * 1 8 morning dress
   * 2 7 Sacred history book
   * 3 6 physics book
   * 4 4 atomic theory book
   * 5 2 crystall ball
   * 6 4 plato's dialogues
   * 7 8 fish
   * 8 0 cat
   * 9 9 beer
   * 10 7 daisy flower
   * 11 6 an antique cup
   * 12 4 glues
   * 13 3 some fire matches
   * 14 67 Cross of Mathilde
   * 15 1 book about Florence
   * 16 2 A broken dagger
   * 17 4 A diary in French
   * 18 12 A moldy Cheese
   * 19 32 some candles
   * 20 11 A bottle of Scotch whisky
   * 21 3 A glass of Irish Whisky
   * 22 16 a painting of a lake
   * 23 21 A math book of Trigonometry
   * 24 2 A germany map
   * 25 3 A pair of leather boots
   * 26 7 A teapot
   * 27 9 Luther rose
   * 28 4 A book titled The Communist Manifesto
   * 29 26 some Rocks seems collected near beaches
   * 30 1 A half smoked Cigar
   * 31 2 A later modern period history book
   * 32 4 some pothos plants
   * 33 21 a music book
   * 34 3 an old, dirty, broken glasses
   */

  /**
   * I need hashMap() for parsing items:
   *
   // Create a HashMap object called capitalCities
   HashMap<String, String> capitalCities = new HashMap<String, String>();

   // Add keys and values (Country, City)
   capitalCities.put("England", "London");
   capitalCities.put("Germany", "Berlin");
   capitalCities.put("Norway", "Oslo");
   capitalCities.put("USA", "Washington DC");
   System.out.println(capitalCities);       *
   */

  ///cut the lines again to only left with the rooms information to parse:
  lines = Arrays.copyOfRange(lines, totalRooms + 1, lines.length);

  ///iterate over evey lines for reading items information:
  for (int i = 0; i < lines.length; i++) {
   eachLine = lines[i].toString().split(" ");
   int itemRoom = Integer.parseInt(eachLine[0]); ///parse the item index as Integer.
   int itemDamage = Integer.parseInt(eachLine[1]); ///parse the damage done by item as String.

   for (int j = 2; j < eachLine.length; j++) {
    eachLineStringBuilder.append(eachLine[j])
        .append(" "); ///parse the rest as String: the item name.
   }
   String strItemName = eachLineStringBuilder.toString().trim();
   eachLineStringBuilder.setLength(0); ///reset the string.

   /// (1) PUT to hashmap 'itemDamgeMap' during each iteration:
   itemsDamageMap.put(strItemName, itemDamage);
   /// (2) Put to hashmap ' itemRoomMap':
   itemRoomMap.put(strItemName, itemRoom);

   ///(3) update allNeighborsMap:
   this.getRoom().getAllNeighborsMap();

  } //end of the for-loop for reading the information of items.
 } //end of method readFile().

 @Override public void drawWorld() {
  BufferedImage img = new BufferedImage(1300, 1300, BufferedImage.TYPE_INT_RGB);
  graph = img.createGraphics();

  for (int i = 0; i < this.totalRooms; i++) {
   int x1;
   int y1;
   int x2;
   int y2;
   int width;
   int height;
   //get each room's name:
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
   graph.drawString(name, x1 * 30, y1 * 30 + 30);// (height * 30 / 2) + y2);
  }

  //JFrame for display the image created above.
  JFrame editorFrame = new JFrame(worldName);
  editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  ImageIcon imageIcon = new ImageIcon(img);
  JLabel jLabel = new JLabel();
  jLabel.setIcon(imageIcon);
  editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);
  editorFrame.pack();
  editorFrame.setLocationRelativeTo(null);
  editorFrame.setVisible(true);

  //save image:
  try {
   //   BufferedImage bi = getMyImage();  // retrieve image
   File outputfile = new File("saved.png");
   ImageIO.write(img, "png", outputfile);
  } catch (IOException e) {
   // handle exception
  }

 }

 //--------------------getters:-------------------------

 // public ArrayList<Player> getAllPlayersLst() {
 //  return allPlayersLst;
 // }
 //
 // public ArrayList<String> getPlayersRoomNamesLst() {
 //  return playersRoomNamesLst;
 // }

 public HashMap<String, Integer> getTotalItemsAllowedMap() {
  return totalItemsAllowedMap;
 }

 // public int getNumPlayers() {
 //  return numPlayers;
 // }

 public Graphics getGraph() {
  return graph;
 }

 public static int getBufferSize() {
  return BUFFER_SIZE;
 }

 public int getTotalItems() {
  return totalItems;
 }

 public int getTotalRooms() {
  return totalRooms;
 }

 public Item getItem() {
  return item;
 }

 public Room getRoom() {
  return room;
 }

 public String getWorldName() {
  return worldName;
 }

 public Target getTarget() {
  return target;
 }

 //
 // public HashMap<String, Integer> getTurnsMap() {
 //  return turnsMap;
 // }
 //
 // public ArrayList<Character> getComputerOrHumanLst() {
 //  return computerOrHumanLst;
 // }

 public ArrayList<String> getAllRoomsNamesLst() {
  return allRoomsNamesLst;
 }
 //
 // public HashMap<String, String> getPlayersNameRoomMap() {
 //  return playersNameRoomMap;
 // }
 //
 // public HashMap<String, ArrayList<String>> getPlayerItemsMap() {
 //  return playerItemsMap;
 // }

 @Override public ArrayList<ArrayList<ArrayList<Integer>>> getListOfRoomCoordinates() {
  return listOfRoomCoordinates;
 }

 @Override public HashMap<String, ArrayList<String>> getAllNeighborsMap() {
  return allNeighborsMap;
 }

 @Override public int getTargetHealth() {
  return targetHealth;
 }

 @Override public int getTargetLocation() {
  return targetLocation;
 }

 /**
  * get the target's name.
  */
 @Override public String getTargetName() {
  return this.targetName;
 }

 /**
  * this method gets the names of all the rooms.
  *
  * @return an arrayList of names of the rooms
  */
 @Override public HashMap<String, Integer> getRoomNameIndexMap() {
  return this.roomNameIndexMap;
 }

 /**
  * get the items names
  *
  * @return an array-list of array-list of all the names in each room
  */
 @Override public HashMap<String, Integer> getItemRoomMap() {///
  return this.itemRoomMap;
 }

 /**
  * get the damage-value of each item in the world.
  */
 @Override public HashMap<String, Integer> getItemsDamageMap() {///
  return this.itemsDamageMap;
 }

 public HashMap<String, String> getPlayersTargetNameRoomMap() {
  return playersTargetNameRoomMap;
 }

 public HashMap<String, ArrayList<String>> getPlayersItemsMap() {
  return playersItemsMap;
 }

 public HashMap<String, Integer> getItemsRoomMap() {
  return itemsRoomMap;
 }

 public HashMap<String, Integer> getTurnsMap() {
  return turnsMap;
 }

 //========================SETTERS:====================================//

}//end of Mansion class.
