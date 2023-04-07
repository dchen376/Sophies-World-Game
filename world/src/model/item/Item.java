package model.item;

import model.mansion.Mansion;

/**
 * this is the item class.
 */
public class Item {
  // private fields:
  private Mansion mansion;


  /**
   * constructor.
   * @param mansion the main object for the game
   */
  public Item(Mansion mansion) {
    this.mansion = mansion;
  }

  /**
   * getter.
   * @param itemName the item's name
   * @return the damage of the item
   */
  public int getDamageAmount(String itemName) {
    return this.mansion.getItemsDamageMap().get(itemName);
  }

  /**
   * getter.
   * @param itemName the item name.
   * @return the location of the item as int.
   */
  public int getItemLocation(String itemName) {
    return this.mansion.getItemsRoomMap().get(itemName);
  }

}
