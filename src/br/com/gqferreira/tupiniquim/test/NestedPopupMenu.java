package br.com.gqferreira.tupiniquim.test;


import javax.swing.*;
import java.awt.event.*;

public class NestedPopupMenu {

    JPopupMenu Pmenu;
    JMenuItem Mitem;

    public static void main(String[] args) {
        NestedPopupMenu n = new NestedPopupMenu();
    }

    public NestedPopupMenu() {
        JFrame frame = new JFrame("Creating a Popup Menu with Nested Menus");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Pmenu = new JPopupMenu();
        Mitem = new JMenuItem("Line Up Icon");
        Pmenu.add(Mitem);
        Mitem = new JMenuItem("Refresh");
        Pmenu.add(Mitem);
        Mitem = new JMenuItem("Properties");
        Pmenu.add(Mitem);
        Pmenu.add(createMenu("New"));
        frame.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent Me) {
                if (Me.isPopupTrigger()) {
                    Pmenu.show(Me.getComponent(), Me.getX(), Me.getY());
                }
            }
        });
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    public JMenu createMenu(String title) {
        JMenu m = new JMenu(title);
        m.add("Folder");
        m.add("Text Document");
        m.add("Shortcut");
        return m;
    }
}