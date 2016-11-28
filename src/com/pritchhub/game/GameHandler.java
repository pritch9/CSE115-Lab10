package com.pritchhub.game;

import java.awt.Color;
import java.awt.Point;

import javax.swing.SwingUtilities;

import com.pritchhub.gui.CButton;
import com.pritchhub.gui.GFrame;

public class GameHandler {

	/*
	 * This class handles everything with game play.
	 */
	
	// stores the first selected button. resets when another button is selected
	private CButton _current;
	
	private GFrame _gFrame;

	public GameHandler() {
		this._gFrame = new GFrame(this);
	}
	
	/**
	 * Creates a new game with a JFrame at the location of the original frame
	 */
	public void newGame(){
		Point _prevPoint = this._gFrame.getLocation();
		this._gFrame.dispose();
		this._gFrame = new GFrame(this);
		this._gFrame.setLocation(_prevPoint);
	}
	
	/**
	 * Run when a button is clicked.  
	 * @param The clicked button
	 */
	public void whenClick(CButton selection) {
		// if there is not a button already selected, run this.
		if (_current == null) {
			_current = selection;
			SwingUtilities.invokeLater(() -> _current.select());
			return; // exits method
		}
		// Next check if the two buttons are next to each other.
		if(_current.getAdjacents().values().contains(selection)){
			// The two buttons are next to eachother, so we check if they can be switched
			if(doTheThing(selection, _current)){
				switcher(selection);
			} else if(doTheThing(_current, selection)){
				switcher(selection);
			} else {
				System.out.println("The board has no match");
			}
		} else {
			System.out.println("Those aren't next to eachother!");
		}
		// clean up everything
		clean();
	}
	
	/**
	 * This is the main method for game handling.  This checks to see if one button can be moved into another.
	 * @param button1
	 * @param button2
	 * @return true if button2 can be moved into button1's spot
	 */
	private boolean doTheThing(CButton button1, CButton button2) {
		// trying button2 as if it was in button1's spot
		for(CButton c : button1.getAdjacents().values()){
			if(c.equals(button2) || !c.getColor().equals(button2.getColor())){
				continue;
			}
			// So now we have a CLabel next to button1 that is not button2.  We are also not considering labels with the wrong color.
			int x = button1.getAdjacentDirection(c);
			if(c.getAdjacent(x) != null){
				// comparing the ahead adjacent with button2.  c, the adjacent button is already checked if it is the same color as button2
				if(compare(c.getAdjacent(x), button2)){
					return true;
				}
			}
			if(button1.getAdjacent(-x) != null){
				// the three buttons were comparing are the opposite adjacent the adjacent and button2.
				CButton opp = button1.getAdjacent(-x);
				// if the opposite button is button2, we have to check button1 as the opposite, because that is the button that will be moved into button2's spot
				if(opp.equals(button2)){
					opp = button1;
				}
				// check if c is equal to button2
				if(compare(opp, c)){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * method that switches the colors of the two buttons
	 * @param selection
	 */
	private void switcher(CButton selection) {
		Color pee = _current.getColor();
		Color peytonManning = selection.getColor();

		_current.setColor(peytonManning);
		selection.setColor(pee);
		nextAction("The board has a match");
	}
	
	/**
	 *  deselects and nullifies the current selection
	 */
	private void clean() {
		if (_current != null){
			SwingUtilities.invokeLater(() -> {
				_current.deselect();
				_current = null;
			});
		}
	}
	
	/**
	 *  Used for the next action in lab 11
	 * @param string
	 */
	private void nextAction(String s) {
		System.out.println(s);
	}
	
	/**
	 * simple caparing method
	 * @param button one
	 * @param button two
	 * @return true if the colors are the same
	 */
	private boolean compare(CButton one, CButton two){
		return one.getColor().equals(two.getColor());
	}
	
	/**
	 * 
	 * @return current Game Frame
	 */
	public GFrame getGFrame(){
		return this._gFrame;
	}

	/**
	 * accessor method for the current selection
	 * @return _current
	 */
	public CButton getSelection() {
		return this._current;
	}
	
}
