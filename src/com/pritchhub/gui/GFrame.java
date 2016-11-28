package com.pritchhub.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.pritchhub.game.GameHandler;

public class GFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4263298736480102563L;
	// Storage of all references for the Buttons
	private ArrayList<CButton> _labels;
	
	// Gamehandler instance
	private GameHandler _gameHandler;
	
	public GFrame(GameHandler gameHandler){
		super("Will Pritchard's Lab 10");
		// instantiate shit
		this._gameHandler = gameHandler;
		this._labels = new ArrayList<CButton>();
		
		initComponents();
		initFrame();
	}

	private void initComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// create all buttons.  Nested for loop to iterate in a grid
		for(int cap = 0; cap < 5; cap++){
			for(int cap1 = 0; cap1 < 5; cap1++){
				CButton label = new CButton(alphabetSoup(), this, this._gameHandler, cap1, cap);
				c.gridx = cap1;
				c.gridy = cap;
				_labels.add(label);
				panel.add(label, c);
			}
		}
		
		// set the adjacents for saving resources later
		for(CButton sd : this._labels){
			sd.setAdjacents();
		}
		
		// reset button for new game
		JButton reset = new JButton("New Game");
		reset.addActionListener((e) -> {
			this._gameHandler.newGame();
		});
		panel.setBackground(Color.WHITE);
		panel.setOpaque(true);
		this.add(panel);
		JPanel buttons = new JPanel();
		buttons.add(reset, BorderLayout.CENTER);
		this.add(buttons, BorderLayout.SOUTH);
	}

	/**
	 * All the frame stuff
	 */
	private void initFrame() {
		this.setResizable(false);
		this.setMinimumSize(new Dimension(200,200));
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * Random Color Generator
	 * @return random color
	 */
	private Color alphabetSoup(){
		int poop = (int) (Math.random() * 7);
		switch(poop){
		case 0:
			return Color.BLUE;
		case 1:
			return Color.CYAN;
		case 2:
			return Color.GREEN;
		case 3:
			return Color.MAGENTA;
		case 4:
			return Color.ORANGE;
		case 5:
			return Color.PINK;
		case 6: 
			return Color.RED;
		case 7:
			return Color.YELLOW;
		default:
			return Color.BLACK;
		}
	}
	
	/**
	 * Gets a CButton with a given coordinate
	 * @param x
	 * @param y
	 * @return CButton at x,y
	 * @throws NullPointerException for when button doesn't exist
	 */
	public CButton getCButton(int x, int y) throws NullPointerException{
		for(CButton c : _labels){
			if(c.getXCord() == x && c.getYCord() == y){
				return c;
			}
		}
		throw new NullPointerException();
	}

}
