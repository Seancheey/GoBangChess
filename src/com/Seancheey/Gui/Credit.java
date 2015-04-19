package com.Seancheey.Gui;
import javax.swing.*;

import java.awt.event.*;
import  java.awt.*;
import com.Seancheey.Data.*;
public class Credit extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private static int labelNum=8;
	private static JLabel[] label=new JLabel[labelNum];
	private static JLabel title=new JLabel("Credit");
	private static int py=500;
	private static JButton exitbutton=new JButton("exit");
	
	public Credit() {
		setLayout(null);
		setBackground(Color.BLACK);
		this.setSize(Mainclass.Gui.getSize());
		//title
		title.setForeground(Color.WHITE);
		title.setFont(Data.titleFont);
		title.setSize(title.getPreferredSize());
		title.setLocation((int)((Data.frameSize-title.getWidth())/2),py);
		add(title);
		//label
		label[0]=new JLabel("Designer:");
		label[1]=new JLabel("Sean Shan");
		label[2]=new JLabel("Spectator:");
		label[3]=new JLabel("MaomiHZ");
		label[4]=new JLabel(" ");
		label[5]=new JLabel("email: adls371@163.com");
		label[6]=new JLabel("---Gobang--");
		label[7]=new JLabel("HFLS Developer Team :)");
		for(int ba=0;ba<labelNum;ba++){
			label[ba].setForeground(Color.WHITE);
			label[ba].setFont(Data.bigFont);
			label[ba].setSize(label[ba].getPreferredSize());
			label[ba].setLocation	(	(int)((Data.frameSize-label[ba].getWidth())/2),
										py+title.getHeight()+50+ba*(label[0].getHeight()+20)
									);
			add(label[ba]);
		}
		//button
		exitbutton.setFont(Data.titleFont);
		exitbutton.setSize(exitbutton.getPreferredSize());
		exitbutton.setLocation	(	Mainclass.Gui.getWidth()-exitbutton.getWidth()-10,
									Mainclass.Gui.getHeight()-exitbutton.getHeight()-20
								);
		exitbutton.setBorderPainted(false);
		exitbutton.setBackground(Color.BLACK);
		exitbutton.setForeground(Color.WHITE);
		exitbutton.addActionListener(this);
		add(exitbutton);
	}
	
	public static void initialize(){
		py=500;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		title.setLocation((int)((Data.frameSize-title.getWidth())/2),py);
		for(int ba=0;ba<labelNum;ba++)
			label[ba].setLocation	(	(int)((Data.frameSize-label[ba].getWidth())/2),
					py+title.getHeight()+50+ba*(label[0].getHeight()+20)
				);
		try {
			Thread.sleep(20);
			py-=2;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Mainclass.Gui.repaint();
	}
	
	public void actionPerformed(ActionEvent e) {
		Mainclass.Gui.add(Mainclass.Menu);
		Mainclass.Gui.remove(Mainclass.Credit);
		Decoration.resetAllChesses();
		Mainclass.Gui.repaint();
	}

}
