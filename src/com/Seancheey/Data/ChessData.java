package com.Seancheey.Data;

import com.Seancheey.Gui.WinFrame;

import java.awt.*;
import java.util.Random;

public class ChessData {
    public final static int WHITE = 111, BLACK = 110;

    public static int[][] chessField = new int[Data.boardsize][Data.boardsize],
            scoreField = new int[Data.boardsize][Data.boardsize],
            progressField = new int[Data.boardsize][Data.boardsize],
            checkFormer = new int[Data.boardsize][Data.boardsize],
            checkChanged = new int[Data.boardsize][Data.boardsize];

    public static int progress = 0, nextChess = BLACK, computer = Data.Option_com;
    public static Point currentChess;

    public static void begin() {
        clear();
        if (Data.Option_com == BLACK) {
            putChess(BLACK, Data.boardsize / 2, Data.boardsize / 2);
        }
    }

    public static boolean putChess(int chessType, int x, int y) {
        if (chessField[x][y] == 0) {
            //��������
            chessField[x][y] = chessType;
            progress++;
            progressField[x][y] = progress;
            currentChess = new Point(x, y);
            Mainclass.Gui.repaint();
            //����Ƿ�Ӯ��
            int win = checkWin();
            if (win != 0) {
                announceWin(win);
            } else
                //���ûӮ��������һ���Ĵ���
                if (Data.Option_mode == Data.PVP) {
                    if (nextChess == BLACK)
                        nextChess = defineOpp(BLACK);
                    else if (nextChess == WHITE)
                        nextChess = defineOpp(WHITE);
                } else if (Data.Option_mode == Data.PVC && chessType == nextChess) {
                    think(computer);
                }
            return true;
        } else return false;
    }

    public static void clear() {
        progress = 0;
        currentChess = null;
        for (int y = 0; y < Data.boardsize; y++) {
            for (int x = 0; x < Data.boardsize; x++) {
                chessField[x][y] = 0;
                progressField[x][y] = 0;
            }
        }
    }

    public static void recalculateScore() {
        clearScoreField();
        for (int x = 0; x < Data.boardsize; x++) {
            for (int y = 0; y < Data.boardsize; y++) {
                for (int length = 5; length <= 6; length++) {
                    for (int shape = 1; shape <= 4; shape++) {
                        estimate(BLACK, length, shape, x, y);
                    }
                    for (int shape = 1; shape <= 4; shape++) {
                        estimate(WHITE, length, shape, x, y);
                    }
                }
            }
        }
        debugScoreField();
        System.out.println("Score:\n");
        listScoreField();
    }

    public static void drawback() {
        //Ѱ�����µ�����progress,��ɾ��
        int maxpX = 0, maxpY = 0, maxp = 0;
        for (int times = 0; times < 2; times++) {
            for (int x = 0; x < Data.boardsize; x++) {
                for (int y = 0; y < Data.boardsize; y++) {
                    if (progressField[x][y] > maxp) {
                        maxp = progressField[x][y];
                        maxpX = x;
                        maxpY = y;
                    }
                }
            }
            if (maxp != 0) {
                if (Data.Option_output == true)
                    System.out.println("Delete the chess in " + maxpX + " " + maxpY);
                chessField[maxpX][maxpY] = 0;
                progressField[maxpX][maxpY] = 0;
                maxp = 0;
                progress--;
            } else {
                if (Data.Option_output == true)
                    System.out.println("There is no chess on the field!");
            }
        }
        //ɾ����ʶ��
        currentChess = null;
    }

    public static String getProgress() {
        return String.valueOf(progress / 2);
    }

    public static void refreshParameters() {
        chessField = new int[Data.boardsize][Data.boardsize];
        scoreField = new int[Data.boardsize][Data.boardsize];
        progressField = new int[Data.boardsize][Data.boardsize];
        checkFormer = new int[Data.boardsize][Data.boardsize];
        checkChanged = new int[Data.boardsize][Data.boardsize];
    }

    public static int defineOpp(int chessType) {
        if (chessType == WHITE) {
            return BLACK;
        } else if (chessType == BLACK) {
            return WHITE;
        } else {
            return -1;
        }
    }

    public static String convertToName(int player) {
        if (player == WHITE) {
            return "WHITE";
        } else if (player == BLACK) {
            return "BLACK";
        } else {
            return "Unknown";
        }
    }

    public static void think(int player) {
        int opp = defineOpp(player);
        //���÷���
        clearScoreField();

        if (canWin(player) == false) {
            //����ȫ��������
            for (int x = 0; x < Data.boardsize; x++) {
                for (int y = 0; y < Data.boardsize; y++) {
                    for (int length = 5; length <= 6; length++) {
                        for (int shape = 1; shape <= 4; shape++) {
                            estimate(player, length, shape, x, y);
                            if (Data.Option_output == true)
                                checkScoreChange(player, shape, x, y);
                        }
                        for (int shape = 1; shape <= 4; shape++) {
                            estimate(opp, length, shape, x, y);
                            if (Data.Option_output == true)
                                checkScoreChange(opp, shape, x, y);
                        }
                    }

                }
            }
            //Ѱ�ҷ�ֵ��ߵĵ�
            debugScoreField();
            Point maxP = findMaxScorePoint();
            //����
            if (Data.Option_output == true)
                System.out.println("Computer put chess at " + maxP.x + " " + maxP.y);

            putChess(player, maxP.x, maxP.y);
        }

    }

    private static void announceWin(int player) {
        new WinFrame(player);
    }

    private static void listScoreField() {
        for (int y = 0; y < Data.boardsize; y++) {
            for (int x = 0; x < Data.boardsize; x++) {
                if (scoreField[x][y] <= 9) {
                    System.out.print(scoreField[x][y] + "   ");
                } else if (scoreField[x][y] > 9 && scoreField[x][y] <= 99) {
                    System.out.print(scoreField[x][y] + "  ");
                } else if (scoreField[x][y] > 99) {
                    System.out.print(scoreField[x][y] + " ");
                } else {
                    System.out.print("??? ");
                }

            }
            System.out.println();
        }
    }

    static void estimate(int chessType, int length, int shape, int x, int y) {
        int c[] = sequence(length, shape, x, y);
        if (c != null)
            addScore(giveScore(chessType, length, c), length, shape, x, y);
    }

    private static void addScore(int addedScore, int length, int shape, int x, int y) {
        if (addedScore != 0) {
            if (shape == 1) {
                for (int plus = 0; plus < length; plus++) {
                    scoreField[x + plus][y] += addedScore;
                }
            } else if (shape == 2) {
                for (int plus = 0; plus < length; plus++) {
                    scoreField[x][y + plus] += addedScore;
                }
            } else if (shape == 3) {
                for (int plus = 0; plus < length; plus++) {
                    scoreField[x + plus][y + plus] += addedScore;
                }
            } else if (shape == 4) {
                for (int plus = 0; plus < length; plus++) {
                    scoreField[x - plus][y + plus] += addedScore;
                }
            }
        }
    }

    private static int giveScore(int chessType, int length, int[] c) {
        int r = chessType;

        if (length == 6) {
            if (c[0] == 0 && c[1] == r && c[2] == r && c[3] == r && c[4] == r && c[5] == 0) {
                return 432;//+OOOO+
            } else if (c[0] == 0 && c[1] == r && c[2] == r && c[3] == r && c[4] == 0 && c[5] == 0) {
                return 72;//+OOO++
            } else if (c[0] == 0 && c[1] == 0 && c[2] == r && c[3] == r && c[4] == r && c[5] == 0) {
                return 72;//++OOO+
            } else if (c[0] == 0 && c[1] == r && c[2] == r && c[3] == 0 && c[4] == r && c[5] == 0) {
                return 72;//+OO+O+
            } else if (c[0] == 0 && c[1] == r && c[2] == 0 && c[3] == r && c[4] == r && c[5] == 0) {
                return 72;//+O+OO+
            } else if (c[0] == 0 && c[1] == 0 && c[2] == r && c[3] == r && c[4] == 0 && c[5] == 0) {
                return 9;//++OO++
            } else if (c[0] == 0 && c[1] == 0 && c[2] == r && c[3] == 0 && c[4] == r && c[5] == 0) {
                return 9;//++O+O+
            } else if (c[0] == 0 && c[1] == r && c[2] == 0 && c[3] == r && c[4] == 0 && c[5] == 0) {
                return 9;//+O+O++
            } else if (c[0] == 0 && c[1] == 0 && c[2] == 0 && c[3] == 0 && c[4] == r && c[5] == 0) {
                return 9;//++++O+
            } else if (c[0] == 0 && c[1] == 0 && c[2] == 0 && c[3] == r && c[4] == 0 && c[5] == 0) {
                return 9;//+++O++
            } else if (c[0] == 0 && c[1] == 0 && c[2] == r && c[3] == 0 && c[4] == 0 && c[5] == 0) {
                return 9;//++O+++
            } else if (c[0] == 0 && c[1] == r && c[2] == 0 && c[3] == 0 && c[4] == 0 && c[5] == 0) {
                return 9;//+O++++
            }
        } else if (length == 5) {
            if (c[0] == 0 && c[1] == r && c[2] == r && c[3] == r && c[4] == r) {
                return 210;//+OOOO
            } else if (c[0] == r && c[1] == 0 && c[2] == r && c[3] == r && c[4] == r) {
                return 210;//O+OOO
            } else if (c[0] == r && c[1] == r && c[2] == 0 && c[3] == r && c[4] == r) {
                return 210;//OO+OO
            } else if (c[0] == r && c[1] == r && c[2] == r && c[3] == 0 && c[4] == r) {
                return 210;//OOO+O
            } else if (c[0] == r && c[1] == r && c[2] == r && c[3] == r && c[4] == 0) {
                return 210;//OOOO+
            } else if (c[0] == 0 && c[1] == 0 && c[2] == 0 && c[3] == 0 && c[4] == r) {
                return 1;//++++O
            } else if (c[0] == r && c[1] == 0 && c[2] == 0 && c[3] == 0 && c[4] == 0) {
                return 1;//O++++
            }
        }
        return 0;
    }

    static Point findMaxScorePoint() {
        int theMax = 0, maxNum = 0;
        //��һ��Ѱ����߷�����ֵ
        for (int x = 0; x < Data.boardsize; x++) {
            for (int y = 0; y < Data.boardsize; y++) {
                if (scoreField[x][y] > theMax) {
                    theMax = scoreField[x][y];
                }
            }
        }
        //�ڶ�������ܹ��ж��ٸ���߷ֵĵ�
        for (int x = 0; x < Data.boardsize; x++) {
            for (int y = 0; y < Data.boardsize; y++) {
                if (scoreField[x][y] == theMax) {
                    maxNum++;
                }
            }
        }
        Point[] bestp = new Point[maxNum];
        int num = 0;
        //�����齫������߷ֵĵ���������
        for (int x = 0; x < Data.boardsize; x++) {
            for (int y = 0; y < Data.boardsize; y++) {
                if (scoreField[x][y] == theMax) {
                    bestp[num] = new Point(x, y);
                    num++;
                }
            }
        }
        //��ӡ��õļ�����
        if (Data.Option_output == true) {
            System.out.print("The best score is in: ");
            for (int ba = 0; ba < maxNum; ba++) {
                System.out.print("x:" + bestp[ba].x + " y:" + bestp[ba].y + "  ");
            }
        }
        System.out.println();
        //���ѡ�񲢷��ص�
        Random r = new Random();
        return bestp[r.nextInt(maxNum)];
    }

    private static void debugScoreField() {
        for (int x = 0; x < Data.boardsize; x++) {
            for (int y = 0; y < Data.boardsize; y++) {
                if (chessField[x][y] == BLACK) {
                    scoreField[x][y] = 0;
                    if (Data.Option_output == true)
                        System.out.println("find a bug to black");
                }
                if (chessField[x][y] == WHITE) {
                    scoreField[x][y] = 0;
                    if (Data.Option_output == true)
                        System.out.println("find a bug to white");
                }
            }
        }
        if (Data.Option_output == true) {
            System.out.println("After debug:");
            listScoreField();
        }
    }


    private static int checkWin() {
        int[] operate = new int[5];
        for (int x = 0; x < Data.boardsize; x++) {
            for (int y = 0; y < Data.boardsize; y++) {
                try {
                    for (int ba = 0; ba < 5; ba++) {
                        operate[ba] = chessField[x + ba][y];
                    }
                    if (check5inrow(operate) == WHITE) {
                        return WHITE;
                    }
                    if (check5inrow(operate) == BLACK) {
                        return BLACK;
                    }
                } catch (Exception e) {
                }
                try {
                    for (int ba = 0; ba < 5; ba++) {
                        operate[ba] = chessField[x][y + ba];
                    }
                    if (check5inrow(operate) == WHITE) {
                        return WHITE;
                    }
                    if (check5inrow(operate) == BLACK) {
                        return BLACK;
                    }
                } catch (Exception e) {
                }
                try {
                    for (int ba = 0; ba < 5; ba++) {
                        operate[ba] = chessField[x + ba][y + ba];
                    }
                    if (check5inrow(operate) == WHITE) {
                        return WHITE;
                    }
                    if (check5inrow(operate) == BLACK) {
                        return BLACK;
                    }
                } catch (Exception e) {
                }
                try {
                    for (int ba = 0; ba < 5; ba++) {
                        operate[ba] = chessField[x - ba][y + ba];
                    }
                    if (check5inrow(operate) == WHITE) {
                        return WHITE;
                    }
                    if (check5inrow(operate) == BLACK) {
                        return BLACK;
                    }
                } catch (Exception e) {
                }
            }
        }
        return 0;
    }

    private static int check5inrow(int[] in) {
        int t = 0;
        for (int x = 0; x < 5; x++) {
            if (x == 0) {
                t = in[x];
            }
            if (t != in[x]) {
                return 0;
            }
            if (in[x] == 0) {
                return 0;
            }
        }
        return t;
    }

    private static void checkScoreChange(int player, int shape, int px, int py) {
        //����Ƿ��б仯���о�����仯ֵ������chechChanged����
        boolean changed = false;
        for (int x = 0; x < Data.boardsize; x++) {
            for (int y = 0; y < Data.boardsize; y++) {
                if (checkFormer[x][y] != scoreField[x][y]) {
                    changed = true;
                    checkChanged[x][y] = scoreField[x][y] - checkFormer[x][y];
                } else {
                    checkChanged[x][y] = 0;
                }
            }
        }
        //����б仯������仯�������Former=Current
        if (changed) {
            System.out.println("Change: " + convertToName(player) + ", shape:" + shape + ", X:" + px + ", Y:" + py);
            //listScoreField();

            for (int x = 0; x < Data.boardsize; x++) {
                for (int y = 0; y < Data.boardsize; y++) {
                    checkFormer[x][y] = scoreField[x][y];
                }
            }
        } else {
            //System.out.println(convertToName(player)+" in shape "+shape+" in X:"+px+" Y:"+py+" has no changes");
        }

    }

    public static void clearScoreField() {
        for (int x = 0; x < Data.boardsize; x++) {
            for (int y = 0; y < Data.boardsize; y++) {
                scoreField[x][y] = 0;
            }
        }
    }

    private static boolean canWin(int player) {
        int[] c;
        boolean can = false;
        all:
        for (int y = 0; y < Data.boardsize; y++) {
            for (int x = 0; x < Data.boardsize; x++) {
                for (int shape = 1; shape <= 4; shape++) {
                    c = sequence(5, shape, x, y);
                    if (c != null)
                        if (giveScore(player, 5, c) == 210) {
                            can = true;
                            for (int plus = 0; plus < 5; plus++) {
                                switch (shape) {
                                    case 1:
                                        if (chessField[x + plus][y] == 0) {
                                            putChess(player, x + plus, y);
                                            break all;
                                        }
                                        break;
                                    case 2:
                                        if (chessField[x][y + plus] == 0) {
                                            putChess(player, x, y + plus);
                                            break all;
                                        }
                                        break;
                                    case 3:
                                        if (chessField[x + plus][y + plus] == 0) {
                                            putChess(player, x + plus, y + plus);
                                            break all;
                                        }
                                        break;
                                    case 4:
                                        if (chessField[x - plus][y + plus] == 0) {
                                            putChess(player, x - plus, y + plus);
                                            break all;
                                        }
                                        break;
                                }
                            }
                            break all;
                        }
                }
            }
        }

        return can;
    }

    private static int[] sequence(int length, int shape, int x, int y) {
        int c[] = new int[length];
        boolean outOfBounds = false;
        switch (shape) {
            case 1:
                for (int ba = 0; ba < length; ba++) {
                    if (x + ba < Data.boardsize) {
                        c[ba] = chessField[x + ba][y];
                    } else if (x + ba >= Data.boardsize) {
                        outOfBounds = true;
                    }
                }
                break;
            case 2:
                for (int no = 0; no < length; no++) {
                    if (y + no < Data.boardsize) {
                        c[no] = chessField[x][y + no];
                    } else if (y + no >= Data.boardsize) {
                        outOfBounds = true;
                    }
                }
                break;
            case 3:
                for (int no = 0; no < length; no++) {
                    if (x + no < Data.boardsize && y + no < Data.boardsize) {
                        c[no] = chessField[x + no][y + no];
                    } else if (x + no >= Data.boardsize || y + no >= Data.boardsize) {
                        outOfBounds = true;
                    }
                }
                break;
            case 4:
                for (int no = 0; no < length; no++) {
                    if (x - no >= 0 && y + no < Data.boardsize) {
                        c[no] = chessField[x - no][y + no];
                    } else if (x - no < 0 || y + no >= Data.boardsize) {
                        outOfBounds = true;
                    }
                }
                break;
        }
        if (outOfBounds == false) {
            return c;
        } else {
            return null;
        }
    }
}