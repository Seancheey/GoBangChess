package com.Seancheey.Gui;

import com.Seancheey.Data.ChessData;
import com.Seancheey.Data.Data;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Decoration extends JPanel {


    private static final long serialVersionUID = 1L;
    public static int size = (int) (Data.frameSize * 0.5);
    private static MovingChess[] mc = new MovingChess[5];

    Decoration() {
        this.setSize(size + 1, size + 1);
        this.setLocation(10, (int) (Data.frameSize * 0.16));
        this.setLayout(null);
        this.setBackground(Color.ORANGE);
        createChesses();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        for (int x = 0; x < 11; x++) {
            g.drawLine((int) (x * size * 0.1), 0, (int) (x * size * 0.1), (int) (Data.frameSize * 0.5));
        }
        for (int y = 0; y < 11; y++) {
            g.drawLine(0, (int) (y * size * 0.1), (int) (Data.frameSize * 0.5), (int) (y * size * 0.1));
        }
        //绘制棋子
        for (int ba = 0; ba < 5; ba++) {
            if (mc[ba].chessType == ChessData.WHITE) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.BLACK);
            }
            g.fillOval((int) (mc[ba].px), (int) (mc[ba].py), (int) (mc[ba].chessSize), (int) (mc[ba].chessSize));
        }
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
        }
        for (int ba = 0; ba < 5; ba++) {
            mc[ba].move();
        }

        //判定棋子如果出Panel就变向
        for (int ba = 0; ba < 5; ba++) {
            if (mc[ba].px > size - mc[ba].chessSize || mc[ba].px < 0) {
                mc[ba].vx *= -1;
            }
            if (mc[ba].py > size - mc[ba].chessSize || mc[ba].py < 0) {
                mc[ba].vy *= -1;
            }
        }
    }

    public static void resetAllChesses() {
        for (int ba = 0; ba < 5; ba++) {
            mc[ba].resetPosition();
            mc[ba].resetVelocity();
            mc[ba].resetType();
        }
    }

    private void createChesses() {
        for (int ba = 0; ba < 5; ba++) {
            mc[ba] = new MovingChess();
        }
    }

}

class MovingChess {
    public double px, py, vx, vy, chessSize, chessType;
    private Random r = new Random();

    MovingChess() {
        chessSize = Decoration.size * 0.1;
        px = r.nextDouble() * (Decoration.size - chessSize);
        py = r.nextDouble() * (Decoration.size - chessSize);
        vx = (r.nextDouble() - 0.5) * 4;
        vy = (r.nextDouble() - 0.5) * 4;
        if (r.nextInt(2) == 0) {
            chessType = ChessData.WHITE;
        } else {
            chessType = ChessData.BLACK;
        }
    }

    public void move() {
        px += vx;
        py += vy;
    }

    public void resetPosition() {
        px = r.nextDouble() * (Decoration.size - chessSize);
        py = r.nextDouble() * (Decoration.size - chessSize);
    }

    public void resetVelocity() {
        vx = (r.nextDouble() - 0.5) * 3;
        vy = (r.nextDouble() - 0.5) * 3;
    }

    public void resetType() {
        if (r.nextInt(2) == 0) {
            chessType = ChessData.WHITE;
        } else {
            chessType = ChessData.BLACK;
        }
    }
}