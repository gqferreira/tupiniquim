package br.com.gqferreira.tupiniquim.test;

import java.awt.BorderLayout;
import java.text.DateFormatSymbols;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpinnerChangeListenerSample {
  public static void main(String args[]) {
    JFrame frame = new JFrame("JSpinner Sample");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    DateFormatSymbols symbols = new DateFormatSymbols(Locale.FRENCH);

    String days[] = symbols.getWeekdays();
    SpinnerModel model1 = new SpinnerListModel(days);
    JSpinner spinner1 = new JSpinner(model1);

    ChangeListener listener = new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        System.out.println("Source: " + e.getSource());
      }
    };

    spinner1.addChangeListener(listener);

    JLabel label1 = new JLabel("French Days/List");
    JPanel panel1 = new JPanel(new BorderLayout());
    panel1.add(label1, BorderLayout.WEST);
    panel1.add(spinner1, BorderLayout.CENTER);
    frame.add(panel1, BorderLayout.NORTH);

    frame.setSize(200, 90);
    frame.setVisible(true);
  }
}