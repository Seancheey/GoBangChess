package com.Seancheey.Gui;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import com.Seancheey.Data.*;
public class Save extends JFrame implements WindowListener{
	private static final long serialVersionUID = 1L;
	private static final slot[] slots=new slot[3];
	public Save(){
		super("Save");
		for(int ba=0;ba<3;ba++){
			slots[ba]=new slot(ba);
		}
		setVisible(true);
		setLayout(null);
		setSize(500,500);
		setLocationRelativeTo(null);
		setIconImage(Data.weiqi);
		addWindowListener(this);
		add(new panel());
	}
	
	private static class panel extends JPanel{
		private static final long serialVersionUID = 1L;
		panel(){
			setSize(500,500);
			setLayout(null);
			for(int ba=0;ba<3;ba++){
				add(slots[ba]);
				slots[ba].setLocation(10,2+ba*140);
			}
		}
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			for(int ba=0;ba<3;ba++){
				g.drawRect(9, 20+ba*140, 461, 120);
			}
		}
	}
	public class slot extends JPanel{
		private static final long serialVersionUID = 1L;
		private JButton save;
		private JLabel title,statusbar;
		slot(int name){
			setLayout(null);
			setSize(460,120);
			//titleLabel
			title=new JLabel("Save"+name+":");
			title.setFont(Data.bigFont);
			title.setLocation(10,10);
			title.setSize(title.getPreferredSize());
			add(title);
			//button
			save=new JButton("Save");
			save.setSize(70,50);
			save.setBackground(Color.WHITE);
			save.setLocation(380,65);
			save.addActionListener(new ActionHandler());
			add(save);
			//status bar
			statusbar=new JLabel("");
			statusbar.setLocation(210,50);
			statusbar.setFont(Data.bigFont);
			statusbar.setSize(statusbar.getPreferredSize());
			add(statusbar);
		}
		public JButton getButton(){
			return save;
		}
		public void showStatusbar(String text){
			statusbar.setText(text);
			statusbar.setSize(statusbar.getPreferredSize());
			add(statusbar);
			Thread shutdownTimer=new Thread(){
				public void run(){
					try {
						sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					statusbar.setText("");
				}
			};
			shutdownTimer.start();
		}
	}
	
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {
		Mainclass.Gui.setEnabled(true);
	}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {
		Mainclass.Gui.setEnabled(false);
	}
	
	public static class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			RandomAccessFile file = null;
			//open file
			try{
				if(e.getSource()==slots[0].getButton()){
					file=new RandomAccessFile("save1.txt","rw");
				}else if(e.getSource()==slots[1].getButton()){
					file=new RandomAccessFile("save2.txt","rw");
				}else{
					file=new RandomAccessFile("save3.txt","rw");
				}
			}catch(IOException exception){
				exception.printStackTrace();
			}
			//writeData
			/*
			Data部分
				Option_mode(int)
				Option_com(int)
				boardSize(int)
			棋盘部分
				chessField[boardSize][boardSize](int[][])
				progressField[boardSize][boardSize](int[][])
				porgress(int)
				nextChess(int)
				computer(int)
				currentChess(Point(分成X和Y))
			 */
			try {
				file.seek(0);
				file.writeInt(Data.Option_mode);
				file.writeInt(Data.Option_com);
				file.writeInt(Data.boardsize);
				for(int y=0;y<Data.boardsize;y++){
					for(int x=0;x<Data.boardsize;x++){
						file.writeInt(ChessData.chessField[x][y]);
					}
				}
				for(int y=0;y<Data.boardsize;y++){
					for(int x=0;x<Data.boardsize;x++){
						file.writeInt(ChessData.progressField[x][y]);
					}
				}
				file.writeInt(ChessData.progress);
				file.writeInt(ChessData.nextChess);
				file.writeInt(ChessData.computer);
				if(ChessData.currentChess!=null){
					file.writeInt(ChessData.currentChess.x);
					file.writeInt(ChessData.currentChess.y);
				}
				file.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(e.getSource()==slots[0].getButton()){
				slots[0].showStatusbar("Finished");
			}else if(e.getSource()==slots[1].getButton()){
				slots[1].showStatusbar("Finished");
			}else{
				slots[2].showStatusbar("Finished");
			}
		}
	}
}