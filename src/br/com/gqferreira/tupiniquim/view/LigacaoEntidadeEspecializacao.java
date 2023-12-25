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
public class LigacaoEntidadeEspecializacao extends Ligacao {

    private int area;
    private Boolean esquerda;
    private Boolean acima;

    public LigacaoEntidadeEspecializacao(Componente pai, Componente entidade) {
        super(pai, entidade);
    }

    @Override
    protected void calcularPosicaoTamanhoLigacao(XDimension pai, XDimension filho, Componente atrObj) {
        int x = 0, y = 0, w = 0, h = 0;

        //AREA 1
        //if (pai.getX() + pai.getWidth() <= filho.getX() + (filho.getWidth()/2) && filho.getX()-(filho.getWidth()/2) >= pai.getX()+pai.getWidth()) {
        if (pai.getX() + pai.getWidth() < filho.getX() + (filho.getWidth() / 2)) {
            debug = "AREA 1";
            area = 1;
            x = pai.getX();
            y = pai.getY() + pai.getHeight();
            w = (filho.getX() + filho.getWidth()) - pai.getX();
            h = filho.getY() - y;
        } //AREA 2
        else if (pai.getX() <= filho.getX() + (filho.getWidth() / 2) && filho.getX() + (filho.getWidth() / 2) <= pai.getX() + pai.getWidth()) {
            debug = "AREA 2";
            area = 2;
            if (filho.getX() + (filho.getWidth() / 2) < pai.getX() + (pai.getWidth() / 2)) {
                esquerda = true;
            } else if (filho.getX() + (filho.getWidth() / 2) > pai.getX() + (pai.getWidth() / 2)) {
                esquerda = false;
            } else {
                esquerda = null;
            }
            x = pai.getX();
            y = pai.getY() + pai.getHeight();
            w = pai.getWidth();
            h = filho.getY() - y;
        } //AREA 4
        else if (filho.getX() + (filho.getWidth() / 2) < pai.getX()) {
            area = 3;
            debug = "AREA 3";
            x = filho.getX();
            y = pai.getY() + pai.getHeight();
            w = (pai.getX() + pai.getWidth()) - x;
            h = filho.getY() - y;
        }

        this.setBounds(x, y, w, h);
    }

    @Override
    protected void calcularLinha(XDimension pai, XDimension filho) {
        this.linhas.clear();
        switch (this.area) {
            case 1: {
                linhas.add(new XDimension((filho.getX() + (filho.getWidth() / 2)) - (pai.getX() + pai.getWidth()), 1, (pai.getX() + pai.getWidth()) - this.getX(), 0));
                linhas.add(new XDimension(1, this.getHeight(), this.getWidth() - (filho.getWidth() / 2), 0));
            }
            ;
            break;
            case 2: {
                if (esquerda == null) {
                    linhas.add(new XDimension(1, this.getHeight(), this.getWidth() / 2, 0));
                } else if (esquerda) {
                    linhas.add(new XDimension(1, this.getHeight() / 2, 0, 0));
                    linhas.add(new XDimension((filho.getX() - this.getX()) + filho.getWidth() / 2, 1, 0, this.getHeight() / 2));
                    linhas.add(new XDimension(1, this.getHeight() / 2, (filho.getX() - this.getX()) + filho.getWidth() / 2, this.getHeight() / 2));
                } else {
                    int xTerceiraLinha = this.getWidth() - 1 - ((this.getX() + this.getWidth()) - (filho.getX() + filho.getWidth())) - filho.getWidth() / 2;
                    linhas.add(new XDimension(1, this.getHeight() / 2, this.getWidth() - 1, 0));
                    linhas.add(new XDimension(this.getWidth() - xTerceiraLinha, 1, xTerceiraLinha, this.getHeight() / 2));
                    linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, this.getHeight() / 2));
                }
            }
            ;
            break;
            case 3: {
                int xPrimeiraLinha = pai.getX() - filho.getX();
                int xTerceiraLinha = (filho.getX() - this.getX()) + filho.getWidth() / 2;
                linhas.add(new XDimension(1, this.getHeight(), xTerceiraLinha, 0));
                linhas.add(new XDimension(xPrimeiraLinha - xTerceiraLinha + 1, 1, xTerceiraLinha, 0));                
            }
            ;
            break;
        }
    }
}