package com.pritchhub.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.pritchhub.gui.CButton;

public class HoverListener implements MouseListener{
	
	/*
	 * this class is just for visual purposes.  Nothing really important 
	 */

	@Override
	public void mouseClicked(MouseEvent e) {/* nothing */}
	
	@Override
	public void mouseEntered(MouseEvent mrPoopyButthole) {
		CButton s = (CButton) mrPoopyButthole.getComponent();
		if(!s.selected()){
			s.hover();
		}
	}

	@Override
	public void mouseExited(MouseEvent kMichael) {
		CButton s = (CButton) kMichael.getComponent();
		if(!s.selected()){
			s.deHover();
		}
	}

	@Override
	public void mousePressed(MouseEvent queen) {/* nothing */}

	@Override
	public void mouseReleased(MouseEvent imASniper) {/* nothing */}

}
