package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * this is the item class.
 */
public class ItemMock {
 // private fields:
 private MansionMockModel mansion;


 /**
  * constructor.
  * @param mansion the main object for the game
  */
 public ItemMock(MansionMockModel mansion) {
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
