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
package br.com.gqferreira.tupiniquim.model;

import br.com.gqferreira.tupiniquim.Model;
import java.io.Serializable;

/**
 * Classe
 *
 * @author Gustavo Ferreira www.gqferreira.com.br Copyright 2013 gustavo.
 */

public class Cardinalidade implements Serializable{

     public enum Tipo {

        ZERO("0"), UM("1"), MUITOS("N");

        private String valor;
        Tipo(String valor) {
            this.valor = valor;
        }
        
        public String getValor(){
            return this.valor;
        }
    }
    
    private Tipo minimo;
    private Tipo maximo;
    private int x;
    private int y;
    private int w;
    private int h;

    public Cardinalidade(Tipo minimo, Tipo maximo) {
        this.minimo = minimo;
        this.maximo = maximo;
        calcularTamanho();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    
    public Tipo getMinimo() {
        return minimo;
    }

    public void setMinimo(Tipo minimo) {
        this.minimo = minimo;
        calcularTamanho();
    }

    public Tipo getMaximo() {
        return maximo;
    }

    public void setMaximo(Tipo maximo) {
        this.maximo = maximo;
        calcularTamanho();
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
    
    private void calcularTamanho(){
        if (Model.graphics == null){
            System.out.println("NULO");
        }
        this.w = Model.graphics.getFontMetrics().stringWidth(this.toString());
        this.h = Model.graphics.getFontMetrics().getHeight();
    }

    @Override
    public String toString() {
        return "(".concat(this.minimo.getValor()).concat(",").concat(this.maximo.getValor()).concat(")");        
        //return "(".concat(this.minimo.getValor()).concat(",").concat(this.maximo.getValor()).concat(") [X: ").concat(String.valueOf(this.getX())).concat(" Y: ").concat(String.valueOf(this.getY())).concat("]");
    }
}
