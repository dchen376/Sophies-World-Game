package model.item;

import model.mansion.Mansion;
import model.mansion.MansionBuilder;

/**
 * this is the item class.
 */
public class Item {
  // private fields:
  private MansionBuilder mansionBuilder;


  /**
   * constructor.
   * @param mansion the main object for the game
   */
  public Item(MansionBuilder mansionBuilder) {
    this.mansionBuilder = mansionBuilder;
  }

  /**
   * getter.
   * @param itemName the item's name
   * @return the damage of the item
   */
  public int getDamageAmount(String itemName) {
    return this.mansionBuilder.getItemsDamageMap().get(itemName);
  }

  /**
   * getter.
   * @param itemName the item name.
   * @return the location of the item as int.
   */
  public int getItemLocation(String itemName) {
    return this.mansionBuilder.getItemsRoomMap().get(itemName);
  }

}
