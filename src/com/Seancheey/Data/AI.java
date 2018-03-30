package com.Seancheey.Data;

import java.awt.*;

public class AI {
    public final static int HARD = 10003, MEDIUM = 10002, LOW = 10001;
    private final static int WHITE = 111, BLACK = 110;
    private int level, side, delayTime = 500;

    public AI(int level, int side) {
        if (level == HARD || level == MEDIUM || level == LOW)
            this.level = level;
        else
            System.out.println("com.Seancheey.Data---AI---IncorrectLevelInput");
        if (side == WHITE || side == BLACK)
            this.side = side;
        else
            System.out.println("com.Seancheey.Data---AI---IncorrectSideInput");
    }

    public void setDelayTime(int delay) {
        delayTime = delay;
    }

    public Point getBestOption() {
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (level == LOW)
            return lowOption();
        else if (level == MEDIUM)
            return mediumOption();
        else if (level == HARD)
            return hardOption();
        else
            return null;
    }

    private Point lowOption() {
        int opp = ChessData.defineOpp(side);
        //重置分数
        ChessData.clearScoreField();
        //遍历全场并给分
        for (int x = 0; x < Data.boardsize; x++) {
            for (int y = 0; y < Data.boardsize; y++) {
                for (int length = 5; length <= 6; length++) {
                    for (int shape = 1; shape <= 4; shape++) {
                        ChessData.estimate(side, length, shape, x, y);
                    }
                    for (int shape = 1; shape <= 4; shape++) {
                        ChessData.estimate(opp, length, shape, x, y);
                    }
                }
            }
        }
        //寻找分值最高的点
        Point maxP = ChessData.findMaxScorePoint();

        return maxP;
    }

    private Point mediumOption() {
        return null;
    }

    private Point hardOption() {
        return null;
    }
}
