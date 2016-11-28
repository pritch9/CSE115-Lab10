package com.pritchhub.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.pritchhub.game.GameHandler;
import com.pritchhub.gui.CButton;

public class ClickListener implements ActionListener{
	
	private GameHandler _gameHandler;
	public ClickListener(GameHandler gameHandler){
		_gameHandler = gameHandler;
	}
	
	/**
	 * activates the when click method in the game handler
	 */
	public void actionPerformed(ActionEvent e){
		_gameHandler.whenClick((CButton) e.getSource());
	}

}
