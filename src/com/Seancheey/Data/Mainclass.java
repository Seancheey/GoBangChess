package com.Seancheey.Data;

import com.Seancheey.Gui.Credit;
import com.Seancheey.Gui.Field;
import com.Seancheey.Gui.Gui;
import com.Seancheey.Gui.Menu;

import javax.swing.*;

/*
Alpha0.9
改善先后手选项bug
完成存档读档功能
修复第二次电脑先行游戏时为玩家先行的bug
重新组合代码
 */
public class Mainclass {
    public static JFrame Gui;
    public static JPanel Field, Menu, Credit;

    public static void main(String[] args) {
        Gui = new Gui();
        Field = new Field();
        Menu = new Menu();
        Credit = new Credit();

        Menu.setLocation(0, 0);
        Field.setLocation(0, 0);
        Gui.add(Menu);
        Gui.repaint();
    }
}
