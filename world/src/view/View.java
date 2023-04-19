package view;

import controller.Controller;
import model.mansion.MansionBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame implements ViewBuilder{
 //fields
 private MansionBuilder model;
 private JButton exitButton;
 private JButton confirmButton;

 private JTextField input = new JTextField();
 private JLabel label = new JLabel("Welcome to the game! Click \"Confirm\" to Start!!");



 /**
  * Constructor.
  * @param model the model.
  * @throws HeadlessException throws when environment that doesn't support keyboard or mouse input.
  */
 public View(MansionBuilder model) throws HeadlessException {
  super(); // invoke the JFrame constructor
  this.model = model;
  // set JFrame
  setSize(500, 500);
  setLocation(500, 500);
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.setLayout(new FlowLayout(FlowLayout.CENTER)); // set the layout manager
  this.setTitle("Sophie's World ");

  // add Jpanel -> JFrame
  GamePanel gameBoard = new GamePanel(model);
  this.add(gameBoard);

  add(this.label);
  add(this.input);

  this.confirmButton = new JButton("Confirm");
  this.confirmButton.setActionCommand("Confirm Button");
  this.confirmButton.setPreferredSize(new Dimension(140, 1300));
  this.confirmButton.setBackground(Color.GREEN);
  this.confirmButton.setOpaque(true);
  this.add(confirmButton);



  // exit button
  this.exitButton = new JButton("Exit");
  this.exitButton.setActionCommand("Exit Button");
  this.exitButton.setPreferredSize(new Dimension(70, 1300));
  this.exitButton.setBackground(Color.RED);
  this.exitButton.setOpaque(true);
  this.add(exitButton); //1300 1300

 } // end constructor.


 @Override public void refresh() {
  this.repaint(); // : to parse in this method somewhere
 }

 @Override public void makeVisible() {
  setVisible(true); // set it visible to display.
  pack();

 }

 @Override public void setButtonListener(ActionListener actionListener) {
  exitButton.addActionListener(actionListener);
  confirmButton.addActionListener(actionListener);
 }

 @Override public void addClickListener(Controller listener) {
  this.addMouseListener(new MouseClick(listener));

 }



 @Override
 public String getInputString() {
  return input.getText();
 }


 @Override
 public void clearInputString() {
  input.setText("");
 }


}
