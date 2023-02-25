package world;

import java.util.HashMap;

public class Item {
 //private fields:
 private HashMap<String, Integer> itemsDamageMap;
 private HashMap<String, Integer> itemsRoomMap;

 //constructors: (good)
 public Item(HashMap<String, Integer> itemsDamageMap, HashMap<String, Integer> itemRoomMap) {
  this.itemsDamageMap = itemsDamageMap;
  this.itemsRoomMap = itemRoomMap;
 }

 //methods:
 public int getDamageAmount(String itemName) {
  return itemsDamageMap.get(itemName);
 }

 public int getItemLocation(String itemName) {
  return itemsRoomMap.get(itemName);
 }

}
