/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.controller.ControllerConceitual;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author gustavo
 */
public class AtributoDerivado extends Atributo {

    public AtributoDerivado(int x, int y, Graphics g, Componente componentePai, LigacaoAtributo ligacao, ControllerConceitual controllerConceitual) {
        super(componentePai, ligacao, controllerConceitual);
        //Esse objeto Graphics eh do palco, ser utilizado somente por causa do getFontMetrics no construtor
        //ja que nesse ponto o objeto Atributo nao pode retornar um objeto Graphics valido
        this.setBounds(x, y, 30 + (g.getFontMetrics().stringWidth(nome)), ALTURA_ATRIBUTO);
    }

    public AtributoDerivado(int x, int y, Graphics g, Componente componentePai, LigacaoAtributo ligacao, ControllerConceitual controllerConceitual, String nome) {
        super(componentePai, ligacao, controllerConceitual);
        this.nome = nome;
        //Esse objeto Graphics eh do palco, ser utilizado somente por causa do getFontMetrics no construtor
        //ja que nesse ponto o objeto Atributo nao pode retornar um objeto Graphics valido
        this.setBounds(x, y, 30 + (g.getFontMetrics().stringWidth(nome)), ALTURA_ATRIBUTO);
    }
    
    @Override
    public void paintComponent(Graphics g2) {

        Graphics2D g = (Graphics2D)g2;
        
        this.setSize(30 + (g.getFontMetrics().stringWidth(nome)), ALTURA_ATRIBUTO);
        g.setColor(Color.BLACK);

        //Linha tracejada
        float dash1[] = {2.2f};
        BasicStroke dashed = new BasicStroke(1.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f, dash1, 0.0f);

        g.setStroke(dashed);

        if (this.esquerda) {
            g.drawOval(0, 0, 17, 17);
            g.drawString(nome, 23, 14);
        } else {
            g.drawOval(this.getWidth() - 18, 0, 17, 17);
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
