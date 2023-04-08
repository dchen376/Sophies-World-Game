package view;

import controller.Controller;

import java.awt.event.ActionListener;

public interface ViewBuilder {
 /**
  * Set up the controller to handle click events in this view.
  *
  * @param listener the controller
  */
 void addClickListener(Controller listener);

 /**
  * Refresh the view to reflect any changes in the game state.
  */
 void refresh();

 /**
  * Make the view visible to start the game session.
  */
 void makeVisible();

 /**
  * to set up the listener for the action.
  * @param actionListener the listener to be set up
  */
 void setListener(ActionListener actionListener);
}
