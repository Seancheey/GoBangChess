package com.Seancheey.Gui;

import com.Seancheey.Data.ChessData;
import com.Seancheey.Data.Data;
import com.Seancheey.Data.Mainclass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Read extends JFrame implements WindowListener {
    private static final long serialVersionUID = 1L;
    private static final slot[] slots = new slot[3];

    public Read() {
        super("Read");
        for (int ba = 0; ba < 3; ba++) {
            slots[ba] = new slot(ba);
        }
        setVisible(true);
        setLayout(null);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setIconImage(Data.weiqi);
        addWindowListener(this);
        add(new panel());
    }

    private static class panel extends JPanel {
        private static final long serialVersionUID = 1L;

        panel() {
            setSize(500, 500);
            setLayout(null);
            for (int ba = 0; ba < 3; ba++) {
                add(slots[ba]);
                slots[ba].setLocation(10, 2 + ba * 140);
            }
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int ba = 0; ba < 3; ba++) {
                g.drawRect(9, 20 + ba * 140, 461, 120);
            }
        }
    }

    private class slot extends JPanel {
        private static final long serialVersionUID = 1L;
        private JButton read;
        private JLabel title, statusbar;

        slot(int name) {
            setLayout(null);
            setSize(460, 120);
            //titleLabel
            title = new JLabel("Read" + name + ":");
            title.setFont(Data.bigFont);
            title.setLocation(10, 10);
            title.setSize(title.getPreferredSize());
            add(title);
            //button
            read = new JButton("Read");
            read.setSize(70, 50);
            read.setBackground(Color.WHITE);
            read.setLocation(380, 65);
            read.addActionListener(new ActionHandler());
            add(read);
            //statusbar
            statusbar = new JLabel("");
            statusbar.setLocation(210, 50);
            statusbar.setFont(Data.bigFont);
            statusbar.setSize(statusbar.getPreferredSize());
            add(statusbar);
        }

        public JButton getButton() {
            return read;
        }

        public void showStatusbar(String text) {
            statusbar.setText(text);
            statusbar.setSize(statusbar.getPreferredSize());
            add(statusbar);
            Thread shutdownTimer = new Thread() {
                public void run() {
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

    public void windowActivated(WindowEvent arg0) {
    }

    public void windowClosed(WindowEvent arg0) {
    }

    public void windowClosing(WindowEvent arg0) {
        Mainclass.Gui.setEnabled(true);
    }

    public void windowDeactivated(WindowEvent arg0) {
    }

    public void windowDeiconified(WindowEvent arg0) {
    }

    public void windowIconified(WindowEvent arg0) {
    }

    public void windowOpened(WindowEvent arg0) {
        Mainclass.Gui.setEnabled(false);
    }

    public static class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            RandomAccessFile file = null;
            //open file
            try {
                if (e.getSource() == slots[0].getButton()) {
                    file = new RandomAccessFile("save1.txt", "r");
                } else if (e.getSource() == slots[1].getButton()) {
                    file = new RandomAccessFile("save2.txt", "r");
                } else {
                    file = new RandomAccessFile("save3.txt", "r");
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            //readData
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
                Data.Option_mode = file.readInt();
                Data.Option_com = file.readInt();
                Data.boardsize = file.readInt();
                for (int y = 0; y < Data.boardsize; y++) {
                    for (int x = 0; x < Data.boardsize; x++) {
                        ChessData.chessField[x][y] = file.readInt();
                    }
                }
                for (int y = 0; y < Data.boardsize; y++) {
                    for (int x = 0; x < Data.boardsize; x++) {
                        ChessData.progressField[x][y] = file.readInt();
                    }
                }
                ChessData.progress = file.readInt();
                ChessData.nextChess = file.readInt();
                ChessData.computer = file.readInt();
                try {
                    ChessData.currentChess.x = file.readInt();
                    ChessData.currentChess.y = file.readInt();
                } catch (Exception e2) {
                }
                file.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if (e.getSource() == slots[0].getButton()) {
                slots[0].showStatusbar("Finished");
            } else if (e.getSource() == slots[1].getButton()) {
                slots[1].showStatusbar("Finished");
            } else {
                slots[2].showStatusbar("Finished");
            }
        }
    }
}