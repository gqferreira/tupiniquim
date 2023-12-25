/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.Model;
import br.com.gqferreira.tupiniquim.controller.ControllerConceitual;
import br.com.gqferreira.tupiniquim.model.XDimension;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author gustavo
 */
public class Atributo extends Componente implements ActionListener, KeyListener {

    public enum TipoDado {

        INT(Model.lang.getString("tipoInt")), BIGINT(Model.lang.getString("tipoBigint")), DOUBLE(Model.lang.getString("tipoDouble")), VARCHAR(Model.lang.getString("tipoVarchar")), TEXT(Model.lang.getString("tipoText")), CHAR(Model.lang.getString("tipoChar")), BOOLEAN(Model.lang.getString("tipoBoolean")), DATE(Model.lang.getString("tipoDate")), TIME(Model.lang.getString("tipoTime")), DATETIME(Model.lang.getString("tipoDatetime"));

        private String tipo;
        TipoDado(String tipo) {
            this.tipo = tipo;
        }
        
        public String getTipo(){
            return this.tipo;
        }

        @Override
        public String toString() {
            return this.tipo;
        }
    }
    
    //Componente pai, ou seja, em quem esse atributo esta ligado
    protected Componente componentePai;
    //Objeto que representa graficamente a ligacao
    protected Ligacao ligacao;
    //Define a posicao do circulo
    protected boolean esquerda = true;
    //Variavel que determina qual a margem a ser respeitada pela ligacao
    public static final int MARGEM_LIGACAO = 20;
    //Define a altura do atributo visual
    protected static final int ALTURA_ATRIBUTO = 18;
    //Define o tipo de dado. Dicionario de dados
    protected TipoDado tipo;
    
    public Atributo(Componente componentePai, Ligacao ligacao, ControllerConceitual conceitual) {
        super(conceitual);
        this.componentePai = componentePai;
        this.ligacao = ligacao;
        this.selecionado = true;
        this.nome = "atributo";
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.tf = new JTextField(this.nome);
        this.tf.setPreferredSize(new Dimension(120, 25));
        this.tf.setVisible(false);
        this.add(tf);
        this.setOpaque(false);

        this.tf.addActionListener(this);
        this.tf.addKeyListener(this);
        this.tipo = TipoDado.VARCHAR;
    }

    public void setEsquerda(boolean esquerda) {
        this.esquerda = esquerda;
    }

    public boolean isEsquerda() {
        return this.esquerda;
    }

    private void terminaDigitacao() {
        Model.modelEditado();
        nome = tf.getText();
        tf.setVisible(false);
        repaint();
        //Por que essa Thread? Porque libera a Thread principal, permitindo a atualizacao
        //da view. A atualizacao da view deve ocorrer antes desse trecho pois
        //eh preciso utilizar o tamanho real do atributo.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Atributo.class.getName()).log(Level.SEVERE, null, ex);
                }
                Atributo.this.ligacao.ligacao(new XDimension(Atributo.this.componentePai.getWidth(), Atributo.this.componentePai.getHeight(), Atributo.this.componentePai.getX(), Atributo.this.componentePai.getY()), new XDimension(Atributo.this.getWidth(), Atributo.this.getHeight(), Atributo.this.getX(), Atributo.this.getY()));
            }
        }).start();
    }

    public Componente getComponentePai() {
        return this.componentePai;
    }

    public Ligacao getLigacao() {
        return this.ligacao;
    }

    public TipoDado getTipo() {
        return tipo;
    }   

    public void setTipo(TipoDado tipo) {
        this.tipo = tipo;
    }

    public void setLigacao(Ligacao ligacao) {
        this.ligacao = ligacao;
    }

    @Override
    public void setSelecionado(boolean select) {
        this.selecionado = select;
        this.repaint();
        if (!this.selecionado) {
            this.setVisible(false);
            this.tf.setVisible(false);
            this.setVisible(true);
        }
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public boolean isSelecionado() {
        return this.selecionado;
    }

    @Override
    public void duploClique() {
        this.setVisible(false);
        this.getTf().setText(this.nome);
        this.getTf().setVisible(true);
        this.setVisible(true);
        this.getTf().requestFocus();
        this.getTf().setSelectionStart(0);
        this.getTf().setSelectionEnd(this.getNome().length());
    }

    @Override
    public String toString() {
        return this.getClass().toString().concat("[").concat("x: ").concat(String.valueOf(this.getX())).concat(", y: ").concat(String.valueOf(this.getY())).concat(", width: ").concat(String.valueOf(this.getWidth())).concat(", height: ").concat(String.valueOf(this.getHeight())).concat("]");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        terminaDigitacao();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getModifiers() == Event.CTRL_MASK) {
            if (e.getKeyCode() == KeyEvent.VK_V) {
                e.consume();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == ' ') {
            Atributo.this.tf.setText(Atributo.this.tf.getText().trim());
            terminaDigitacao();
        }
    }
}
