package com.Seancheey.Data;

import com.Seancheey.Gui.Credit;
import com.Seancheey.Gui.Field;
import com.Seancheey.Gui.Gui;
import com.Seancheey.Gui.Menu;

import javax.swing.*;

/*
Alpha0.9
�����Ⱥ���ѡ��bug
��ɴ浵��������
�޸��ڶ��ε���������ϷʱΪ������е�bug
������ϴ���
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
