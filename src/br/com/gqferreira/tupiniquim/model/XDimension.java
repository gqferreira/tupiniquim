package br.com.gqferreira.tupiniquim.model;

import java.io.Serializable;

/**
 * Classe para auxiliar a definir tamanho e posicao de componentes
 * @author Gustavo Ferreira
 */
public class XDimension implements Serializable{
    private int width;
    private int height;
    private int x;
    private int y;

    public XDimension(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
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

    @Override
    public String toString() {
        return "Width:"+this.width+", Heigth:"+this.height+", X:"+this.getX()+", Y:"+this.getY();
    }
}
