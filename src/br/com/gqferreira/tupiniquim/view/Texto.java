/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.Model;
import br.com.gqferreira.tupiniquim.controller.ControllerConceitual;
import br.com.gqferreira.tupiniquim.model.InterfaceParameters;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

/**
 *
 * @author gustavo
 */
public class Texto extends Componente implements InterfaceParameters {

    public Texto(ControllerConceitual controllerConceitual) {
        super(controllerConceitual);
        this.selecionado = true;
        this.nome = "Obs.:";
        this.setLayout(new FlowLayout());
        this.tf = new JTextField();
        this.tf.setVisible(false);
        this.add(tf);

        this.tf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nome = "Obs.: " + tf.getText();
                tf.setVisible(false);
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);

        g.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 20, 20);
        g.setColor(new Color(230, 230, 255));
        g.fillRoundRect(1, 1, this.getWidth() - 3, this.getHeight() - 3, 17, 17);

        g.setColor(Color.BLACK);
        
        drawText(g, this.getWidth(), this.getHeight(), nome, false, false);

        if (this.selecionado) {
            g.fillRect(0, 0, 8, 8);
            g.fillRect(this.getWidth() - 8, 0, 8, 8);
            g.fillRect(0, this.getHeight() - 8, 8, 8);
            g.fillRect(this.getWidth() - 8, this.getHeight() - 8, 8, 8);
        }
    }

    @Override
    public void setSelecionado(boolean select) {
        this.selecionado = select;
        this.repaint();
        if (!this.selecionado) {
            this.setVisible(false);
            this.tf.setVisible(false);
            this.setVisible(true);
        }
    }
    
    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public boolean isSelecionado() {
        return this.selecionado;
    }

    @Override
    public void duploClique() {
        Object[] parameters = new Object[1];
        parameters[0] = nome.toString();
        new TextDialog(parameters, Model.TextDialogType.OBSERVACAO, this).setVisible(true);
    }

    @Override
    public void returnParameters(Object[] parameters) {
        if (!this.nome.equals((String) parameters[0])){
            this.nome = (String) parameters[0];
            this.repaint();
            Model.modelEditado();
        }        
    }
}
