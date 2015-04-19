package com.Seancheey.Gui;
import javax.swing.*;
import com.Seancheey.Data.*;
public class Gui extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public Gui() {
		super("Gobang - By Sean");
		setVisible(true);
		setSize(Data.frameSize,Data.frameSize);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		this.setIconImage(Data.weiqi);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
