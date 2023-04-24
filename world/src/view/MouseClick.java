package view;

import controller.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseClick extends MouseAdapter {
 /*field*/
 private Controller controller;
 /*constructor*/
 public MouseClick(Controller controller) {
  this.controller = controller;
 }

 @Override public void mouseClicked(MouseEvent e) {
//  super.mouseClicked(e);
//  //  1.to capture a click on the game board
//  int x = e.getX(); //get x coordinate
//  int y = e.getY(); //get y coordinate
//  //  2. to pass to the controller
//  //: convert x y to row and col
//  //x from 0 - 300. Y from 0  to 300.
//  int row = -1;
//  int col = -1;
//
//  if (x>=0 && x<= 310 && y >=0 && y <=330){
//
//   if (x < 105){
//    col = 0;
//   }else if (x >105 && x <= 205){
//    col = 1;
//   }else {
//    col = 2;
//   }
//
//   if (y < 130){
//    row = 0;
//   } else if (y > 130 && y < 230) {
//    row = 1;
//   } else {
//    row = 2;
//   }
//   controller.handleCellClick(row,col);
//   //   System.out.println("row is :" + row);
//   //   System.out.println("col is :" + col);
//  }
//  //   System.out.println("mouse clicked! X: " + x);
//  //   System.out.println("Y: " + y);


 } //end of mouseClicked().



}
