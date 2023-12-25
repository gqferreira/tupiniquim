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

import br.com.gqferreira.tupiniquim.model.XDimension;

/**
 * Classe
 *
 * @author Gustavo Ferreira www.gqferreira.com.br Copyright 2013 gustavo.
 */
public class LigacaoAltoNivelEspecializacao extends Ligacao {

    private int area;
    private Boolean esquerda;

    public LigacaoAltoNivelEspecializacao(Componente pai, Componente entidade) {
        super(pai, entidade);
    }

    @Override
    protected void calcularPosicaoTamanhoLigacao(XDimension pai, XDimension filho, Componente atrObj) {
        int x = 0, y = 0, w = 0, h = 0;
        if (pai.getY() > filho.getY() + filho.getHeight()) {
            //Esta abaixo, area 1
            this.area = 1;
            //Agora deve-se verificar se esta na esquerda ou na direita
            if (pai.getX() + (pai.getWidth() / 2) > (filho.getX() + (filho.getWidth() / 2))) {
                //Esta na direita
                this.esquerda = false;
                x = filho.getX();
                y = filho.getY() + filho.getHeight();
                w = (pai.getX() + (pai.getWidth() / 2)) - x;
                h = pai.getY() - y;                
            } else if (pai.getX() + (pai.getWidth() / 2) < (filho.getX() + (filho.getWidth() / 2))) {
                //Esta na esquerda
                this.esquerda = true;
                x = pai.getX() + (pai.getWidth() / 2);
                y = filho.getY() + filho.getHeight();
                w = filho.getX() + filho.getWidth() - x;
                h = pai.getY() - y;
            } else {
                //Esta exatamente no meio
                this.esquerda = null;
                x = filho.getX();
                y = filho.getY() + filho.getHeight();
                w = filho.getWidth();
                h = pai.getY() - y;
            }
        } else if (pai.getY() <= filho.getY() + filho.getHeight()) {
            if (pai.getX() > filho.getX()) {
                //Area 2
                this.area = 2;
                x = filho.getX() + filho.getWidth();
                y = filho.getY();
                w = (pai.getX() + (pai.getWidth() / 2)) - (filho.getX() + filho.getWidth());
                h = filho.getHeight();
            }
            else{
                //Area 3
                this.area = 3;
                x = pai.getX()+(pai.getWidth()/2);
                y = filho.getY();
                w = filho.getX()-x;
                h = filho.getHeight();
            }
        }

        this.setBounds(x, y, w, h);
    }

    @Override
    protected void calcularLinha(XDimension pai, XDimension filho) {
        this.linhas.clear();
        if (this.area == 1) {            
            if (this.esquerda == null) {
                //Centro
                linhas.add(new XDimension(1, this.getHeight(), this.getWidth() / 2, 0));                
            } else if (this.esquerda == true) {
                //Esquerda
                int xTerceiraLinha = (this.getWidth() - (filho.getWidth() / 2));
                linhas.add(new XDimension(1, this.getHeight() / 2, 0, this.getHeight() / 2));
                linhas.add(new XDimension(xTerceiraLinha + 1, 1, 0, this.getHeight() / 2));
                linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, 0));
            } else {
                //Direita
                linhas.add(new XDimension(1, this.getHeight() / 2, this.getWidth() - 1, this.getHeight() / 2));
                linhas.add(new XDimension(this.getWidth() - (filho.getWidth() / 2), 1, filho.getWidth() / 2, this.getHeight() / 2));
                linhas.add(new XDimension(1, this.getHeight() / 2, filho.getWidth() / 2, 0));
            }
        }
        else if (this.area == 2){
            linhas.add(new XDimension(this.getWidth(), 1, 0, pai.getY()-this.getY()));
        }
        else{
            linhas.add(new XDimension(this.getWidth(), 1, 0, pai.getY()-this.getY()));
        }
    }
}