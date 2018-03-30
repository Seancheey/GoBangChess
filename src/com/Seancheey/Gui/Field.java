package com.Seancheey.Gui;

import com.Seancheey.Data.ChessData;
import com.Seancheey.Data.Data;
import com.Seancheey.Data.Mainclass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Field extends JPanel {

    private static final long serialVersionUID = 1L;

    public static double FieldPanelWidth = (Mainclass.Gui.getWidth() * 0.85),
            FieldWidth = (FieldPanelWidth * 0.9),
            Interval = (FieldPanelWidth - FieldWidth) / 2,
            GridInterval = (FieldWidth / (Data.boardsize - 1));
    private static final int FBNum = 5;
    private static JButton[] FB = new JButton[FBNum];
    private static JLabel[] LL = new JLabel[4];
    public static JFrame Save, Read;

    public Field() {
        setLayout(null);
        this.setSize(Mainclass.Gui.getSize());
        addMouseListener(new MouseHandler());
        FB[0] = new JButton("Menu");
        FB[1] = new JButton("Drawback");
        FB[2] = new JButton("Help");
        FB[3] = new JButton("Save");
        FB[4] = new JButton("Read");

        for (int ba = 0; ba < FBNum; ba++) {
            FB[ba].setSize(95, 30);
            FB[ba].setBorderPainted(true);
            FB[ba].setBackground(Color.WHITE);
            FB[ba].setLocation(110 * ba + (int) Interval, (int) (FieldPanelWidth));
            FB[ba].addActionListener(new FieldFunctionButtonHandler());
            add(FB[ba]);
        }

        LL[0] = new JLabel("Progress: ");
        LL[1] = new JLabel(ChessData.getProgress());
        LL[2] = new JLabel("Next Chess: ");
        for (int x = 0; x < 3; x++) {
            LL[x].setLocation((int) (FieldPanelWidth + 5), 30 + x * 30);
            LL[x].setSize(80, 35);
            add(LL[x]);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.ORANGE);
        setLayout(null);
        g.setColor(Color.BLACK);
        //创建棋盘网格
        for (int i = 0; i < Data.boardsize; i++) {
            g.drawLine((int) (Interval + i * GridInterval), (int) (Interval), (int) (Interval + i * GridInterval), (int) (Interval + FieldWidth));
            g.drawLine((int) (Interval), (int) (Interval + i * GridInterval), (int) (Interval + FieldWidth), (int) (Interval + i * GridInterval));
        }


        //绘制棋子
        for (int bx = 0; bx < Data.boardsize; bx++) {
            for (int by = 0; by < Data.boardsize; by++) {
                if (ChessData.chessField[bx][by] == ChessData.WHITE || ChessData.chessField[bx][by] == ChessData.BLACK) {
                    if (ChessData.chessField[bx][by] == ChessData.WHITE) {
                        g.setColor(Color.WHITE);
                    } else if (ChessData.chessField[bx][by] == ChessData.BLACK) {
                        g.setColor(Color.BLACK);
                    }
                    int absx = (int) (Interval + (bx - 0.5) * GridInterval),
                            absy = (int) (Interval + (by - 0.5) * GridInterval);

                    g.fillOval(absx, absy, (int) GridInterval, (int) GridInterval);
                }
            }
        }

        //绘制上一个落子标识
        if (ChessData.currentChess != null) {
            g.setColor(Color.RED);
            g.fillOval((int) (Interval + ChessData.currentChess.x * GridInterval - GridInterval / 6),
                    (int) (Interval + ChessData.currentChess.y * GridInterval - GridInterval / 6),
                    (int) (GridInterval / 3),
                    (int) (GridInterval / 3)
            );
        }

        //重算progress
        LL[1].setText(ChessData.getProgress() + " x 2");

        //绘制下一个要摆放的棋子
        if (ChessData.nextChess == ChessData.BLACK) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillOval((int) (FieldPanelWidth + 5), 150, (int) Field.GridInterval, (int) Field.GridInterval);

        //更新
        Mainclass.Gui.repaint();
    }

    public static void refreshParameters() {
        FieldPanelWidth = (Mainclass.Gui.getWidth() * 0.85);
        FieldWidth = (FieldPanelWidth * 0.9);
        Interval = (FieldPanelWidth - FieldWidth) / 2;
        GridInterval = (FieldWidth / (Data.boardsize - 1));
        if (Data.Option_output == true)
            System.out.println("refresh OK");
    }

    //功能键反应
    private class FieldFunctionButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent functionButtonPressed) {
            if (functionButtonPressed.getSource() == FB[0]) {
                Mainclass.Gui.add(Mainclass.Menu);
                Mainclass.Gui.remove(Mainclass.Field);
                Decoration.resetAllChesses();
                Mainclass.Gui.repaint();
            } else if (functionButtonPressed.getSource() == FB[1]) {
                ChessData.drawback();
            } else if (functionButtonPressed.getSource() == FB[2]) {
                ChessData.think(ChessData.nextChess);
            } else if (functionButtonPressed.getSource() == FB[3]) {
                new Save();
            } else if (functionButtonPressed.getSource() == FB[4]) {
                new Read();
            }
        }
    }

    //get鼠标在棋盘中的点击位置
    private class MouseHandler implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            if (e.getX() >= Interval &&
                    e.getY() >= Interval &&
                    e.getX() <= (Data.boardsize * GridInterval + Interval) &&
                    e.getY() <= (Data.boardsize * GridInterval + Interval)) {
                int MouseX = e.getX(), MouseY = e.getY(),
                        ChessX = 100, ChessY = 100;
                double Min = 10000.0, distance;
                //寻找最近的下棋位置
                for (int x = 0; x < Data.boardsize; x++) {
                    for (int y = 0; y < Data.boardsize; y++) {
                        distance = Math.sqrt(Math.pow(Interval + x * GridInterval - MouseX, 2) + Math.pow(Interval + y * GridInterval - MouseY, 2));
                        if (distance < Min) {
                            Min = distance;
                            ChessX = x;
                            ChessY = y;
                        }
                    }
                }
                if (Data.Option_output == true)
                    System.out.println("You put chess in " + ChessX + " " + ChessY);
                //放置棋子
                if (ChessData.putChess(ChessData.nextChess, ChessX, ChessY)) {
                    Mainclass.Gui.repaint();//System.out.println("Successfully put chess");
                } else {
                    if (Data.Option_output == true)
                        System.out.println("putting chess failed");
                }
                ;
            }
        }

        public void mouseEntered(MouseEvent arg0) {
        }

        public void mouseExited(MouseEvent arg0) {
        }

        public void mousePressed(MouseEvent arg0) {
        }

        public void mouseReleased(MouseEvent arg0) {
        }
    }
}