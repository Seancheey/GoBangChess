package com.Seancheey.Gui;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.Seancheey.Data.*;

public class Menu extends JPanel{
	private static final long serialVersionUID = 1L;


	private static JLabel	TitleLabel=new JLabel("Gobang");
	private static int buttonNum=3;
	private static JButton[] button=new JButton[buttonNum];

	public Menu(){
		setLayout(null);
		setVisible(true);
		this.setSize(Mainclass.Gui.getSize());
		setBackground(Color.ORANGE);
		Decoration dcra=new Decoration();
		this.add(dcra);


		button[0]=new JButton("Start Game");
		button[1]=new JButton("Option");
		button[2]=new JButton("Credit");
		for(int ba=0;ba<buttonNum;ba++){
			button[ba].setFont(Data.textFont);
			button[ba].setSize(button[ba].getPreferredSize());
			button[ba].setLocation((int)(Data.frameSize*0.62),110+ba*140);
			button[ba].addActionListener(new jbhandler());
			button[ba].setBackground(Color.ORANGE);
			button[ba].setBorderPainted(false);
			add(button[ba]);
		}

		TitleLabel.setFont(Data.titleFont);
		TitleLabel.setSize(TitleLabel.getPreferredSize());
		TitleLabel.setLocation((int)((double)Mainclass.Gui.getWidth()/2-125),25);
		add(TitleLabel);

		repaint();
	}
	
	

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		Mainclass.Gui.repaint();
	}


	class jbhandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==button[0]){
				Mainclass.Gui.add(Mainclass.Field);
				ChessData.begin();
				Mainclass.Gui.remove(Mainclass.Menu);
				Mainclass.Gui.repaint();
			}else 
				if(e.getSource()==button[1]){
					new Option();
				}else
					if(e.getSource()==button[2]){
						Credit.initialize();
						Mainclass.Gui.add(Mainclass.Credit);
						Mainclass.Gui.remove(Mainclass.Menu);
						Mainclass.Gui.repaint();
					}
		}
	}
}
