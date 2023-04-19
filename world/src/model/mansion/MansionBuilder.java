package model.mansion;

import model.item.Item;
import model.pet.Pet;
import model.player.Player;
import model.room.Room;
import model.target.Target;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * MansionBuilder Interface.
 */
public interface MansionBuilder {



  /**
   * read the provided .txt file.
   *
   * @param readable to read the file
   */
  void readFile(Readable readable);

  /**
   * draw the world.
   */
  void drawWorld();

  /**
   * the welcome message.
   * @param turns the # of turns
   * @return the welcome message.
   */
  ArrayList<String> welcomeMessage(int turns);

  /**
   * get the blessings from the pet.
   * it's a hashmap.
   * @return the hashmap
   */
  HashMap<Player, Boolean> getPetBlessings();

  /**
   * get the dfs check hash map.
   * @return the hashmap
   */
  HashMap<Integer, Boolean> getDfsCheckMap();

  /**
   * get all the players.
   * @return an arraylist.
   */
  ArrayList<Player> getAllPlayers();

  /**
   * get the hashmap for total items allowed for each player.
   * @return hashmap.
   */
  HashMap<String, Integer> getTotalItemsAllowedMap();

  /**
   * get the graph.
   * @return graph
   */
  Graphics getGraph();

  /**
   * get the total items.
   * @return total items
   */
  int getTotalItems();

  /**
   * total rooms
   * @return the total rooms.
   */
  int getTotalRooms();

  /*getter*/
  Item getItem();

  /*getter*/
  Room getRoom();

  /*getter*/
  String getWorldName();

  /*getter*/
  Target getTarget();

  /*getter*/
  ArrayList<String> getAllRoomsNamesLst();

  /*getter*/
  HashMap<String, String> getPlayersNameRoomMap();

  /*getter*/
  HashMap<String, ArrayList<String>> getPlayersItemsMap();

  /*getter*/
  HashMap<String, Integer> getTurnsMap();

  /*getter*/
  ArrayList<ArrayList<ArrayList<Integer>>> getListOfRoomCoordinates();

  /*getter*/
  HashMap<String, ArrayList<String>> getAllNeighborsMap();

  /*getter*/
  int getTargetHealth();

  /*getter*/
  int getTargetLocation();

  /*getter*/
  String getTargetName();

  /*getter*/
  HashMap<String, Integer> getRoomNameIndexMap();

  /*getter*/
  HashMap<String, Integer> getItemsRoomMap();

  /*getter*/
  HashMap<String, Integer> getItemsDamageMap();

  /* getter */
  String getPetName();

  // setter
  void setPetName(String petName);

  // getter
  int getPetLocation();

  // setter
  void setPetLocation(int petLocation);

  // getter
  Pet getPet();

  /* getter */
  Set<String> getEvidenceSet();

 BufferedImage getImg();
}
