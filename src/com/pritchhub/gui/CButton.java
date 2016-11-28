package com.pritchhub.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;

import com.pritchhub.game.GameHandler;
import com.pritchhub.listeners.ClickListener;
import com.pritchhub.listeners.HoverListener;

public class CButton extends JButton implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1357504432307821239L;
	
	// Game specific object references
	private GameHandler _gameHandler;
	private GFrame _gFrame;
	
	// color of button variable
	private Color _color;
	
	// coordinates
	private int _x;
	private int _y;
	
	// animation stuff
	private int _rY;
	private int _rVel;
	private Timer _t;
	private Timer _t1;
	private Color _temp;
	
	// Storing button specific data for faster performance
	private HashMap<Integer, CButton> _adjacents;
	
	// Borders
	private Border _selectionBorder;
	private Border _hoverBorder;
	private Border _emptyBorder;
	
	// Selection stuff
	private Boolean _selected = false;
	//private String _debug = "";

	public CButton(Color color, GFrame gFrame, GameHandler gameHandler, int x, int y) {
		// We gotta instantiate all the variables
		this._color = color;
		this._gFrame = gFrame;
		this._gameHandler = gameHandler;
		// create the borders now for less resource consumption later
		this._selectionBorder = BorderFactory.createLineBorder(Color.RED, 3);
		this._hoverBorder = BorderFactory.createLineBorder(Color.DARK_GRAY, 3);
		this._emptyBorder = BorderFactory.createEmptyBorder();
		// store coordinates
		this._x = x;
		this._y = y;
		// instantiate all the animation variables
		this._rY = 5;
		this._rVel = 5;
		_t = new Timer(10, this);
		_t1 = new Timer(10, this);
		// Listener classes
 		this.addActionListener(new ClickListener(this._gameHandler));
 		this.addMouseListener(new HoverListener());
 		// initiate the visible components
		initComponent();
	}

	private void initComponent() {
		this.setBorder(this._emptyBorder);
		this.setPreferredSize(new Dimension(75, 75));
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.repaint();
	}

	/**
	 * Makes the button appear selected
	 */
	public void select() {
		SwingUtilities.invokeLater(()->this.setBorder(_selectionBorder));
		this._selected = true;
	}

	/**
	 * return the button back to the normal non-selected appearance
	 */
	public void deselect() {
		SwingUtilities.invokeLater(()->this.setBorder(_emptyBorder));
		this._selected = false;
	}

	/**
	 * @return Color of the button
	 */
	public Color getColor() {
		return this._color;
	}

	/**
	 * Mutator method for the color of the button <br />
	 * <i>starts animation</i>
	 * @param newColor
	 */
	public void setColor(Color newColor) {
		this._temp = newColor;
		_t.start();
	}
	
	/**
	 * Loads the adjacent button information to save resources later
	 */
	public void setAdjacents(){
		this._adjacents = new HashMap<Integer, CButton>();
		// ignore if there is no CButton
		try{
			this._adjacents.put(-1, this._gFrame.getCButton(_x, _y+1));
		} catch (Exception e){}
		try{
			this._adjacents.put(1, this._gFrame.getCButton(_x, _y-1));
		} catch (Exception e){}
		try{
			this._adjacents.put(2, this._gFrame.getCButton(_x+1, _y));
		} catch (Exception e){}
		try{
			this._adjacents.put(-2, this._gFrame.getCButton(_x-1, _y));
		} catch (Exception e){}
	}
	
	/**
	 * 
	 * @return x coordinate
	 */
	public int getXCord(){
		return this._x;
	}
	
	/**
	 * 
	 * @return y coordinate
	 */
	public int getYCord(){
		return this._y;
	}
	
	
	/**
	 * For game purposes, this method returns the direction the parameter is from the called CButton
	 * @param button
	 * @return the direction the button is the called CButton
	 */
	public int getAdjacentDirection(CButton to){
		for(int c : _adjacents.keySet()){
			if(_adjacents.get(c).equals(to)){
				return c;
			}
		}
		return 0;
	}
	
	/**
	 * Accessor method for getting a CButton adjacent in a certain direction
	 * @param integer direction
	 * @return CButton in direction
	 */
	public CButton getAdjacent(int x){
		return this._adjacents.get(x);
	}

	/**
	 * Accessor for getting the HashMap storing the adjacent CButtons
	 * @return hashmap of all the adjacent CButtons
	 */
	public HashMap<Integer, CButton> getAdjacents() {
		return _adjacents;
	}

	/**
	 * Needed for animation purposes
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(_rY > 74){
			_t.stop();
			this._color = this._temp;
			_rVel = -_rVel;
			_t1.start();
		}
		if(_rY < 4){
			_t1.stop();
			_rVel = -_rVel;
		}
		_rY += _rVel;
		repaint();
	}

	/**
	 * Method of JButton to paint the rounded rectangle with current color
	 */
	@Override
	public void paintComponent(Graphics guap) {
		super.paintComponent(guap);
		//guap.setColor(this._color);
		guap.fillRoundRect(5, _rY, 64, 64, 5, 5);
		guap.setColor(Color.BLACK);
		guap.drawRoundRect(5, _rY, 64, 64, 5, 5);
		//guap.drawString(_debug, 40, 40);
	}

	/**
	 * Called when mouse is hovering over, different from clicking
	 */
	public void hover() {
		SwingUtilities.invokeLater(() -> this.setBorder(_hoverBorder));
	}
	
	/**
	 * Called when mouse is no longer hovering over
	 */
	public void deHover(){
		if(!this._selected)
			SwingUtilities.invokeLater(() -> this.setBorder(_emptyBorder));
	}

	/**
	 * Method to check if the button is already selected
	 * @return true if button is selected
	 */
	public boolean selected() {
		return this._selected;
	}
	
	/*public void debug(String s){
		_debug = s;
		repaint();
	}

	public void showAdjacents() {
		for(int x : this._adjacents.keySet()){
			getAdjacent(x).debug(x +"");
		}
	}*/
}
