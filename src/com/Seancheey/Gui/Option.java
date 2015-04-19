package com.Seancheey.Gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import com.Seancheey.Data.*;
public class Option extends JFrame implements ActionListener,WindowListener{
	
	private static final long serialVersionUID = 1L;
	private static JButton[] board=new JButton[3];
	private static JButton output,mode,sequence;
	private static JLabel[] label=new JLabel[4];
	
	public Option() {
		super("Option");
		setSize(300,300);
		setLocationRelativeTo(Mainclass.Gui);
		setVisible(true);
		setLayout(null);
		setResizable(false);
		setAlwaysOnTop(true);
		setIconImage(Data.weiqi);
		addWindowListener(this);
		Mainclass.Gui.setEnabled(false);
		
		//标签
		label[0]=new JLabel("Borad:");
		label[1]=new JLabel("Output:");
		label[2]=new JLabel("Mode:");
		label[3]=new JLabel("First:");
		label[0].setLocation(30,10);
		label[1].setLocation(150,10);
		label[2].setLocation(150,90);
		label[3].setLocation(150,160);
		for(int ba=0;ba<4;ba++){
			label[ba].setFont(Data.bigFont);
			label[ba].setSize(label[ba].getPreferredSize());
			add(label[ba]);
		}
		//board按钮
		board[0]=new JButton("11");
		board[1]=new JButton("15");
		board[2]=new JButton("19");
		for(int ba=0;ba<3;ba++){
			board[ba].setSize(60,40);
			board[ba].addActionListener(this);
			board[ba].setBackground(Color.YELLOW);
			board[ba].setLocation(30,50+ba*70);
			board[ba].setFont(Data.bigFont);
			board[ba].setBorderPainted(false);
			add(board[ba]);
		}
		//output按钮
		if(Data.Option_output==true){
			output=new JButton("TRUE");
		}else{
			output=new JButton("FALSE");
		}
		output.setSize(90,40);
		output.addActionListener(this);
		output.setLocation(140,40);
		output.setBackground(Color.WHITE);
		add(output);
		//mode按钮
		if(Data.Option_mode==Data.PVP){
			mode=new JButton("P V P");
		}else{
			mode=new JButton("P V C");
		}
		mode.setSize(90,40);
		mode.addActionListener(this);
		mode.setLocation(140,120);
		mode.setBackground(Color.WHITE);
		add(mode);
		//sequence按钮
		if(ChessData.nextChess==ChessData.BLACK){
			sequence=new JButton("YOU");
		}else{
			sequence=new JButton("COM");
		}
		sequence.setSize(90, 40);
		sequence.addActionListener(this);
		sequence.setLocation(140,190);
		sequence.setBackground(Color.WHITE);
		add(sequence);
		resetColor();
	}

	private static void resetColor(){
		for(int ba=0;ba<3;ba++){
			board[ba].setBackground(Color.YELLOW);
		}
		if(Data.boardsize==11){
			board[0].setBackground(Color.ORANGE);
		}else if(Data.boardsize==15){
			board[1].setBackground(Color.ORANGE);
		}else if(Data.boardsize==19){
			board[2].setBackground(Color.ORANGE);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==board[0]){
			Data.boardsize=11;
			board[0].setBackground(Color.ORANGE);
		}else 
		if(e.getSource()==board[1]){
			Data.boardsize=15;
			board[1].setBackground(Color.ORANGE);
		}else 
		if(e.getSource()==board[2]){
			Data.boardsize=19;
			board[2].setBackground(Color.ORANGE);
		}else 
		if(e.getSource()==output){
			if(Data.Option_output==true){
				Data.Option_output=false;
				output.setText("FALSE");
			}else 
			if(Data.Option_output==false){
				Data.Option_output=true;
				output.setText("TRUE");
			}
		}else 
		if(e.getSource()==mode){
			if(Data.Option_mode==Data.PVP){
				Data.Option_mode=Data.PVC;
				ChessData.nextChess=ChessData.BLACK;
				mode.setText("P V C");
			}else 
			if(Data.Option_mode==Data.PVC){
				Data.Option_mode=Data.PVP;
				mode.setText("P V P");
			}
		}else
		if(e.getSource()==sequence){
			if(ChessData.nextChess==ChessData.BLACK){
				sequence.setText("COM");
				ChessData.computer=ChessData.BLACK;
				ChessData.nextChess=ChessData.WHITE;
				Data.Option_com=ChessData.BLACK;
			}else 
			if(ChessData.nextChess==ChessData.WHITE){
				sequence.setText("YOU");
				ChessData.computer=ChessData.WHITE;
				ChessData.nextChess=ChessData.BLACK;
				Data.Option_com=ChessData.WHITE;
			}
		}
		resetColor();
		Field.refreshParameters();
		ChessData.refreshParameters();
	}

	public void windowClosing(WindowEvent arg0) {
		Mainclass.Gui.setEnabled(true);
	}
	public void windowOpened(WindowEvent arg0) {
		Mainclass.Gui.setEnabled(false);
	}
	public void windowIconified(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowActivated(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
}
