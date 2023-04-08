package view;

import model.mansion.Mansion;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

 /*field*/
  private Mansion model;

  /**
   * constructor.
   */
  public GamePanel(Mansion model) {
    setPreferredSize(new Dimension(3 * 100 + 30, 3 * 100 + 50));
    // addMouseListener(this);
    this.model = model;
  }

  @Override
  public void paintComponent(Graphics g) {
//  super.paintComponent(g);
//  Graphics2D g2 = (Graphics2D) g;
//  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//  g2.setStroke(new BasicStroke(5));
//
//  // Draws the board
//  for (int row = 0; row < 3; row++) {
//   for (int col = 0; col < 3; col++) {
//    int x = col * 100;
//    int y = row * 100;
//    g2.drawRect(x, y, 100, 100);
//    //    System.out.println(x);
//    //    System.out.println("and y is");
//    //    System.out.println(y);
//   }
//  }
//
//  // : Draw the X's and O's on the board based on the current state of the model
//
//  int cellSize = 100;
//  for (int row = 0; row < 3; row++) {
//   for (int col = 0; col < 3; col++) {
//    int x = col * cellSize;
//    int y = row * cellSize;
//    String cellValue = " ";
//    //     System.out.println("let check!");
//    Player player = model.getMarkAt(row, col);
//    if (player != null) {
//     cellValue = player.toString();
//    }
//    if (cellValue.equals("X")) {  // draw 'X'
//     g.setColor(Color.blue);
//     g.drawLine(x, y, x + cellSize, y + cellSize);
//     g.drawLine(x + cellSize, y, x, y + cellSize);
//    } else if (cellValue.equals("O")) { //draw 'Y'
//     g.setColor(Color.GREEN);
//     g.drawOval(x, y, cellSize, cellSize);
//    }
//   }
//  } //end 2nd for loop.
//
//  //draws game status.
//  // Set the font and color for the messages
//  g.setFont(new Font("Arial", Font.PLAIN, 20));
//  g.setColor(Color.BLACK);
//  //  g.drawString("this is the game state", 20, 330);
//
//  // Draw the game status
//  if (model.isGameOver()) {
//   if (model.getWinner() == null) {
//    g.setColor(Color.cyan);
//    g.drawString("It's a draw!", 20, 330);
//    g.setFont(new Font("Arial", Font.PLAIN, 100));
//    g.drawString("DRAW ", 30, 150);
//   } else if (model.getWinner().equals(Player.X)) {
//    g.setColor(Color.blue);
//    g.drawString("Player 'X' Won!!", 20, 330);
//    g.setFont(new Font("Arial", Font.PLAIN, 100));
//    g.drawString("X !!!", 50, 150);
//   } else if (model.getWinner().equals(Player.O)) {
//    g.setColor(Color.GREEN);
//    g.drawString("Player 'O' Won!!", 20, 330);
//    g.setFont(new Font("Arial", Font.PLAIN, 100));
//    g.drawString("O !!!", 50, 150);
//   }
//  } else {
//   if (model.getTurn().equals(Player.X)) {
//    g.setColor(Color.blue);
//    g.drawString("Player X's turn!", 20, 330);
//   } else if (model.getTurn().equals(Player.O)) {
//    g.setColor(Color.green);
//    g.drawString("Player O's turn!", 20, 330);
//
//   }
//  }
//
//  //1) whose turn
//  //2) if a winner, display
//
  } // end of paintComponent().

}
