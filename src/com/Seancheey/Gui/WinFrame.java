package com.Seancheey.Gui;

import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.Seancheey.Data.*;

public class WinFrame extends JFrame implements WindowListener{
	private static final long serialVersionUID = 1L;
	
	public WinFrame(int player) {
		super("Win");
		Mainclass.Gui.setEnabled(false);
		setSize(300,100);
		setLocationRelativeTo(Mainclass.Gui);
		setVisible(true);
		setResizable(false);
		setIconImage(Data.weiqi);
		JLabel winLabel=new JLabel(ChessData.convertToName(player)+" Win!");
		winLabel.setFont(Data.textFont);
		add(winLabel);
		addWindowListener(this);
	}
	public void windowClosing(WindowEvent arg0) {
		ChessData.begin();
		Mainclass.Gui.setEnabled(true);
	}
	public void windowOpened(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowActivated(WindowEvent arg0) {}	
	public void windowDeactivated(WindowEvent arg0) {}
}
