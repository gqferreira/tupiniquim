/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.controller.ControllerConceitual;
import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Gustavo Ferreira
 */
public abstract class Componente extends JPanel implements Serializable{

    protected boolean selecionado = false;
    public String nome = "";
    protected JTextField tf;
    protected String observacao;
    
    transient protected ControllerConceitual controllerConceitual;

    public Componente(ControllerConceitual controllerConceitual) {
        this.controllerConceitual = controllerConceitual;
    }
    
    public JTextField getTf() {
        return tf;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
        
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }

    public boolean isSelecionado() {
        return this.selecionado;
    }
    
    public void setSelecionado(boolean selecionado) {
        this.selecionado = selecionado;
    }
    
    public abstract void duploClique();
    
    /**
     * Funcao que escreve no objeto Graphics o texto informado. Faz o controle de quebra de texto
     * e centralização vertical.
     * @param g
     * @param width
     * @param height
     * @param texto
     * @param middle
     * @param center 
     */
    protected void drawText(Graphics g, int width, int height, String texto, boolean middle, boolean center) {
        //Primeiro passo eh separar as palavras do texto, afinal, quebrar no 
        //meio das palavras nao eh legal
        String palavras[] = texto.split(" ");
        int margem = 5;
        int comprimento = 0;
        int posBloco = 0;

        if (middle) {
            //Segundo passo eh contar a quantidade de linhas que sera gerado
            comprimento = margem;
            int qtdeLinhas=0;
            for (int i = 0; i < palavras.length; i++) {
                if (g.getFontMetrics().stringWidth(palavras[i] + " " + (i + 1 >= palavras.length ? "" : palavras[i + 1])) + comprimento >= width - (margem * 2)) {
                    comprimento = margem;
                    qtdeLinhas++;
                } else {
                    comprimento += g.getFontMetrics().stringWidth(palavras[i] + " ");
                }
            }
            int alturaTotal = g.getFontMetrics().getHeight() * qtdeLinhas;
            posBloco = (height / 2) - (alturaTotal / 2) + (g.getFontMetrics().getHeight()/4);
        } else {
            posBloco = g.getFontMetrics().getHeight();
        }

        //variaveis calculadas, vamos comecar a colocar as palavras
        comprimento = center ? (width/2)-(g.getFontMetrics().stringWidth(palavras[0])/2) : margem;
        for (int i = 0; i < palavras.length; i++) {
            g.drawString(palavras[i] + " ", comprimento, posBloco);
            if (g.getFontMetrics().stringWidth(palavras[i] + " " + (i + 1 >= palavras.length ? "" : palavras[i + 1])) + comprimento >= width - (margem * 2)) {
                comprimento = margem;
                posBloco += g.getFontMetrics().getHeight();
            } else {
                comprimento += g.getFontMetrics().stringWidth(palavras[i] + " ");
            }
        }
    }
}
