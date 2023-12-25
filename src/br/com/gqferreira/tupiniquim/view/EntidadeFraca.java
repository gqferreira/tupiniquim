/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.controller.ControllerConceitual;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author gustavo
 */
public class EntidadeFraca extends Entidade {

    public EntidadeFraca(ControllerConceitual controllerConceitual) {
        super(controllerConceitual);
    }


    @Override
    public void paintComponent(Graphics g) {        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        g.drawRect(3, 3, this.getWidth() - 7, this.getHeight() - 7);
        //g.drawString(nome, 15, 20);
        drawText(g, this.getWidth(), this.getHeight(), nome, true, true);
        
        if (this.selecionado){
            g.fillRect(0, 0, 8, 8);
            g.fillRect(this.getWidth()-8, 0, 8, 8);
            g.fillRect(0, this.getHeight()-8, 8, 8);
            g.fillRect(this.getWidth()-8, this.getHeight()-8, 8, 8);
        }
    }
}
