/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.controller.ControllerConceitual;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author gustavo
 */
public class AtributoChave extends Atributo {

    public AtributoChave(int x, int y, Graphics g, Componente componentePai, LigacaoAtributo ligacao, ControllerConceitual controllerConceitual) {
        super(componentePai, ligacao, controllerConceitual);
        //Esse objeto Graphics eh do palco, ser utilizado somente por causa do getFontMetrics no construtor
        //ja que nesse ponto o objeto Atributo nao pode retornar um objeto Graphics valido
        this.setBounds(x, y, 30 + (g.getFontMetrics().stringWidth(nome)), ALTURA_ATRIBUTO);
    }
        
    public AtributoChave(int x, int y, Graphics g, Componente componentePai, LigacaoAtributo ligacao, ControllerConceitual controllerConceitual, String nome) {
        super(componentePai, ligacao, controllerConceitual);
        this.nome = nome;
        //Esse objeto Graphics eh do palco, ser utilizado somente por causa do getFontMetrics no construtor
        //ja que nesse ponto o objeto Atributo nao pode retornar um objeto Graphics valido
        this.setBounds(x, y, 30 + (g.getFontMetrics().stringWidth(nome)), ALTURA_ATRIBUTO);
    }
    
    @Override
    public void paintComponent(Graphics g2) {

        Graphics2D g = (Graphics2D)g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
        RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setRenderingHint(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        this.setSize(30 + (g.getFontMetrics().stringWidth(nome)), ALTURA_ATRIBUTO);
        g.setColor(Color.BLACK);

        if (this.esquerda) {
            g.fillOval(0, 0, 17, 17);
            g.drawString(nome, 23, 14);
        } else {
            g.fillOval(this.getWidth()-18, 0, 17, 17);
            g.drawString(nome, 0, 14);
        }
        
        if (this.selecionado) {
            g.fillRect(0, 0, 8, 8);
            g.fillRect(this.getWidth() - 8, 0, 8, 8);
            g.fillRect(0, this.getHeight() - 8, 8, 8);
            g.fillRect(this.getWidth() - 8, this.getHeight() - 8, 8, 8);
        }
    }
}
