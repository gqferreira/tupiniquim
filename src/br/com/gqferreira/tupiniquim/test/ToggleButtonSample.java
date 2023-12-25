package br.com.gqferreira.tupiniquim.test;


import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JToggleButton;

public class ToggleButtonSample {

    public static void main(String args[]) {
        JFrame f = new JFrame("JToggleButton Sample");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = f.getContentPane();
        content.add(new JToggleButton("North"), BorderLayout.NORTH);
        content.add(new JToggleButton("East"), BorderLayout.EAST);
        content.add(new JToggleButton("West"), BorderLayout.WEST);
        content.add(new JToggleButton("Center"), BorderLayout.CENTER);
        content.add(new JToggleButton("South"), BorderLayout.SOUTH);
        f.setSize(300, 200);
        f.setVisible(true);
    }
}
