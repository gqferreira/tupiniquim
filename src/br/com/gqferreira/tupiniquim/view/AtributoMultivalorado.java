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
public class AtributoMultivalorado extends Atributo {

    public AtributoMultivalorado(int x, int y, Graphics g, Componente componentePai, LigacaoAtributo ligacao, ControllerConceitual controllerConceitual) {
        super(componentePai, ligacao, controllerConceitual);
        //Esse objeto Graphics eh do palco, ser utilizado somente por causa do getFontMetrics no construtor
        //ja que nesse ponto o objeto Atributo nao pode retornar um objeto Graphics valido
        this.setBounds(x, y, 30 + (g.getFontMetrics().stringWidth(nome)), ALTURA_ATRIBUTO);
    }

    public AtributoMultivalorado(int x, int y, Graphics g, Componente componentePai, LigacaoAtributo ligacao, ControllerConceitual controllerConceitual, String nome) {
        super(componentePai, ligacao, controllerConceitual);
        this.nome = nome;
        //Esse objeto Graphics eh do palco, ser utilizado somente por causa do getFontMetrics no construtor
        //ja que nesse ponto o objeto Atributo nao pode retornar um objeto Graphics valido
        this.setBounds(x, y, 30 + (g.getFontMetrics().stringWidth(nome)), ALTURA_ATRIBUTO);
    }

    @Override
    public void paintComponent(Graphics g) {

        this.setSize(30 + (g.getFontMetrics().stringWidth(nome)), ALTURA_ATRIBUTO);
        g.setColor(Color.BLACK);

        if (this.esquerda) {
            g.drawOval(0, 0, 17, 17);
            g.drawString(nome, 23, 14);
            g.drawLine(2, 2, 15, 15);
            g.drawLine(15, 2, 2, 15);
        } else {
            g.drawOval(this.getWidth() - 18, 0, 17, 17);
            g.drawString(nome, 0, 14);
            g.drawLine(this.getWidth() - 16, 2, this.getWidth() - 3, 14);
            g.drawLine(this.getWidth() - 2, 2, this.getWidth() - 15, 14);
        }

        if (this.selecionado) {
            g.fillRect(0, 0, 8, 8);
            g.fillRect(this.getWidth() - 8, 0, 8, 8);
            g.fillRect(0, this.getHeight() - 8, 8, 8);
            g.fillRect(this.getWidth() - 8, this.getHeight() - 8, 8, 8);
        }
    }
}
