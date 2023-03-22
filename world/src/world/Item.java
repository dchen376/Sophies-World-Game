package world;

import java.util.HashMap;

/**
 * this is the item class.
 */
public class Item {
  // private fields:
  private HashMap<String, Integer> itemsDamageMap;
  private HashMap<String, Integer> itemsRoomMap;

  /**
   * constructor.
   * @param itemsDamageMap hashmap for items and damages
   * @param itemRoomMap hashmap for item and room
   */
  public Item(HashMap<String, Integer> itemsDamageMap, HashMap<String, Integer> itemRoomMap) {
    this.itemsDamageMap = itemsDamageMap;
    this.itemsRoomMap = itemRoomMap;
  }

  /**
   * getter.
   * @param itemName the item's name
   * @return the damage of the item
   */
  public int getDamageAmount(String itemName) {
    return itemsDamageMap.get(itemName);
  }

  /**
   * getter.
   * @param itemName the item name.
   * @return the location of the item as int.
   */
  public int getItemLocation(String itemName) {
    return itemsRoomMap.get(itemName);
  }

}
