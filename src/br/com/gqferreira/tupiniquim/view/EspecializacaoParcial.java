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
public class EspecializacaoParcial extends Especializacao {

    public EspecializacaoParcial(ControllerConceitual controllerConceitual, Entidade altoNivel) {
        super(controllerConceitual, altoNivel);      
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawPolygon(new int[]{this.getWidth() / 2, this.getWidth() - 1, 0}, new int[]{0, this.getHeight()-1, this.getHeight() - 1}, 3);
        g.drawString("P", this.getWidth()-10, 13);

        if (this.selecionado) {
            g.fillRect(0, 0, 8, 8);
            g.fillRect(this.getWidth() - 8, 0, 8, 8);
            g.fillRect(0, this.getHeight() - 8, 8, 8);
            g.fillRect(this.getWidth() - 8, this.getHeight() - 8, 8, 8);
        }
    }
}
