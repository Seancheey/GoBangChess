package com.Seancheey.Data;

import java.awt.*;

public class Data {
    public final static int PVP = 9090, PVC = 9091;

    public static int Option_mode = PVC,
            Option_com = ChessData.WHITE;

    public static boolean Option_output = false;

    public static int boardsize = 19,
            frameSize = 750;

    public final static Font bigFont = new Font("serif", Font.BOLD, 21),
            textFont = new Font("Bradley Hand ITC", Font.BOLD, 30),
            titleFont = new Font("Bradley Hand ITC", Font.BOLD, 50);

    public final static Image weiqi = Toolkit.getDefaultToolkit().createImage("MenuImage.jpg"),
            icon = Toolkit.getDefaultToolkit().getImage("Icon.jpg");

}