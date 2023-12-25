/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.Model;
import br.com.gqferreira.tupiniquim.controller.ControllerConceitual;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;

/**
 *
 * @author gustavo
 */
public class Relacionamento extends Componente implements ActionListener, KeyListener {

    private List<Atributo> atributos = new ArrayList<Atributo>();
    private List<Entidade> entidades = new ArrayList<Entidade>();

    public Relacionamento(ControllerConceitual controllerConceitual) {
        super(controllerConceitual);
        this.setOpaque(false);
        this.selecionado = true;
        this.nome = "rel";
        this.setLayout(new FlowLayout());
        this.tf = new JTextField(this.nome);
        this.tf.setPreferredSize(new Dimension(90, 25));
        this.tf.setVisible(false);
        this.add(tf);

        this.tf.addActionListener(this);
        this.tf.addKeyListener(this);
    }

    private void terminaDigitacao() {
        Model.modelEditado();
        nome = tf.getText();
        tf.setVisible(false);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawPolygon(new int[]{this.getWidth() / 2, this.getWidth() - 1, this.getWidth() / 2, 0}, new int[]{0, this.getHeight() / 2, this.getHeight() - 1, this.getHeight() / 2}, 4);
        drawText(g, this.getWidth(), this.getHeight(), nome, true, true);

        if (this.selecionado) {
            g.fillRect(0, 0, 8, 8);
            g.fillRect(this.getWidth() - 8, 0, 8, 8);
            g.fillRect(0, this.getHeight() - 8, 8, 8);
            g.fillRect(this.getWidth() - 8, this.getHeight() - 8, 8, 8);
        }
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

    /**
     * Metodo que adiciona um atributo
     *
     * @param Atributo at
     */
    public void adicionaAtributo(Atributo at) {
        boolean encontrou = false;
        for (Atributo a : this.atributos) {
            if (a == at) {
                encontrou = true;
                break;
            }
        }
        if (!encontrou) {
            this.atributos.add(at);
        }
    }

    /**
     * Essa funcao remove o atributo informado.
     *
     * @param Atributo at
     */
    public void removeAtributo(Atributo at) {
        at.setSelecionado(false);
        at.setVisible(false);
        Ligacao l = at.getLigacao();
        l.setVisible(false);
        this.atributos.remove(at);
    }

    /**
     * Metodo que adiciona uma entidade
     *
     * @param ent
     */
    public void adicionaEntidade(Entidade ent) {
        //Pode ter a mesma entidade duas vezes (relacionamento unario)
        //mais nao mais que isso
        short qtde = 0;
        for (Entidade e : this.entidades) {
            if (e == ent) {
                qtde++;
            }
        }
        if (qtde < 2) {
            this.entidades.add(ent);
        }
    }

    /**
     * Essa funcao remove a entidade informada.
     *
     * @param Entidade ent
     */
    public void removeEntidade(Entidade ent) {
        ent.setSelecionado(false);
        ent.setVisible(false);
        ent.removerLigacao(this);
        this.entidades.remove(ent);
    }

    /**
     * Essa funcao remove todos os atributos. Utilizada quando a entidade eh
     * removida.
     */
    public void removeAtributos() {
        for (Atributo a : this.atributos) {
            Ligacao l = a.getLigacao();
            l.setVisible(false);
            a.setSelecionado(false);
            a.setVisible(false);
            a = null;
        }
        this.atributos.clear();
    }

    /**
     * Essa funcao remove todos os atributos. Utilizada quando a entidade eh
     * removida.
     */
    public void removeEntidades() {
        for (Entidade ent : this.entidades) {
            ent.removerLigacao(this);
            ent.setSelecionado(false);
        }
        this.entidades.clear();
    }

    /**
     * Essa funcao desliga uma determinada entidade. Utilizada quando a ligacao eh
     * removida.
     */
    public void desligaEntidade(Entidade ent) {
        ent.removerLigacao(this);
        ent.setSelecionado(false);
        this.entidades.remove(ent);
    }

    /**
     * Metodo que verifica se o relaciona possui ligacao repetida com alguma
     * entidade, indicando se tratar de um auto relacionamento
     *
     * @return
     */
    public boolean isAutoRelacionamento() {
        short qtde = 0;
        for (Entidade ent1 : this.entidades) {
            for (Entidade ent2 : this.entidades) {
                if (ent1 == ent2) {
                    qtde++;
                }
            }
        }
        return qtde > this.entidades.size();
    }

    /**
     * Metodo utilizada para recuperar a lista de todos os atributos
     *
     * @return List<Atributo>
     */
    public List<Atributo> listAtributos() {
        return this.atributos;
    }

    /**
     * Metodo utilizado para recuperar a lista de todos os atributos
     *
     * @return List<Atributo>
     */
    public List<Entidade> listEntidades() {
        return this.entidades;
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
            Relacionamento.this.tf.setText(Relacionamento.this.tf.getText().trim());
            terminaDigitacao();
        }
    }
}
