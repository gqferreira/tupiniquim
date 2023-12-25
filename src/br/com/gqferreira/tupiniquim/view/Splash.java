/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.Erro;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Gustavo
 */
public class Splash extends JFrame{
    
    public Splash(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setSize(new Dimension(500,300));
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/icon.png")).getImage());
        
        JLabel lbFundo = new JLabel();
        lbFundo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/tupiniquim.png")));
        this.setLayout(new BorderLayout());
        this.add(lbFundo);
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    dispose();
                    Thread.sleep(100);
                    FrameMain f = new FrameMain();
                    f.setVisible(true);
                    //f.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    
                } catch (InterruptedException ex) {
                    Erro.deal(ex);
                }
            }
        }).start();
    }
}
