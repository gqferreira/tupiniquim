/*
 * Copyright (C) 2013 Gustavo Ferreira.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.model.Cardinalidade;
import br.com.gqferreira.tupiniquim.model.XDimension;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Classe
 *
 * @author Gustavo Ferreira www.gqferreira.com.br Copyright 2013 gustavo.
 */
public abstract class Ligacao extends JPanel {

    protected Componente pai;
    protected Componente filho;
    //Define qual area o atributo esta localizado. Ajuda a tracar a linha
    protected int area;
    //Define se o atributo esta orientado a esquerda ou a direita. Ajuda a tracar a linha
    protected boolean esquerda;
    //Define se o atributo esta orientado acima ou abaixo. Ajuda a tracar a linha
    protected boolean acima;
    //Define as possiveis linhas de ligacao.
    protected List<XDimension> linhas = new ArrayList<XDimension>();
    protected Cardinalidade cardinalidade;
    //Variavel que indica que o mouse esta sobre a cardinalidade
    protected boolean sobreCardinalidade;
    //Variavel que indica que o mouse esta pronto para movimentar a primeira linha
    protected boolean sobrePrimeiraLinha;
    protected String debug = "";
    //Essa ligacao eh utilizado caso o componente pratique autorelacionamento.
    //Essa ligacao serve para validar a area de colisao com a entidade e evitar
    //que uma ligacao use a mesma area, ocasionando em uma sobreposicao de area
    protected Ligacao irma;

    @Override
    public void paintComponent(Graphics gx) {
        Graphics2D g = (Graphics2D) gx.create();

        short contador = 0;
        for (XDimension linha : linhas) {
            if (contador == 0 && sobrePrimeiraLinha){
                g.setColor(new Color(190,190,0));
            }
            else{
                g.setColor(Color.BLACK);
            }
            g.fillRect(linha.getX(), linha.getY(), linha.getWidth(), linha.getHeight());
            contador ++;
        }

        if (this.cardinalidade != null) {
            g.drawString(this.cardinalidade.toString(), this.cardinalidade.getX(), this.cardinalidade.getY());
            if (this.sobreCardinalidade) {
                g.setColor(new Color(255, 255, 255, 180));
                g.fillRect(this.cardinalidade.getX(), this.cardinalidade.getY() - this.cardinalidade.getH() + 5, this.cardinalidade.getW(), this.cardinalidade.getH());
            }
        }

        boolean isDebug = false;
        if (isDebug) {
            //Linha tracejada
            float dash1[] = {5.0f};
            BasicStroke dashed = new BasicStroke(1.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);

            g.setStroke(dashed);

            g.setColor(new Color(130, 230, 230));
            g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);

            //DEBUG
            g.drawString(debug, 10, 20);
        }

    }

    /**
     * Porque nao utilizar as posicoes e tamanhos do atributo que ja estah nessa
     * classe? Confirmar se eh por isso: Se o atributo estiver sofrendo um drag,
     * a sua posicao eh erronea para posicionar a ligacao pois a posicao do
     * atributo serah em relacao ao painel que agrupa todos os componentes para
     * arrastar.
     *
     * @param pai
     * @param filho
     * @param compFilho
     */
    public void ligacao(XDimension xdimensionPai, XDimension xdimensionFilho) {
        calcularPosicaoTamanhoLigacao(xdimensionPai, xdimensionFilho, this.filho);
        calcularLinha(xdimensionPai, xdimensionFilho);
        this.setVisible(false);
        this.setVisible(true);
        
    }
    
    protected abstract void calcularPosicaoTamanhoLigacao(XDimension pai, XDimension filho, Componente atrObj);

    protected abstract void calcularLinha(XDimension pai, XDimension filho);

    public Ligacao(Componente pai, Componente filho) {
        this.pai = pai;
        this.filho = filho;
        this.setOpaque(false);
    }
    
    public boolean isSobreCardinalidade() {
        return sobreCardinalidade;
    }
    
    public boolean isSobrePrimeiraLinha(){
        return sobrePrimeiraLinha;
    }
    
    public int getArea(){
        return this.area;
    }
    
    public Componente getPai() {
        return pai;
    }

    public Componente getFilho() {
        return filho;
    }

    public Cardinalidade getCardinalidade() {
        return cardinalidade;
    }
    
    @Override
    public String toString() {
        return "X: ".concat(String.valueOf(this.getX())).concat(", Y: ").concat(String.valueOf(this.getY())).concat(", W: ").concat(String.valueOf(this.getWidth())).concat(", H: ").concat(String.valueOf(this.getHeight()));
    }
}
