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
public class Entidade extends Componente implements ActionListener, KeyListener {

    private List<Atributo> atributos = new ArrayList<Atributo>();
    //Objeto que representa graficamente uma ligacao. Essa ligacao eh entre um relacionamento
    protected List<Ligacao> ligacoes = new ArrayList<Ligacao>();
    //Objeto que representa graficamente uma ligacao. Essa ligacao eh entre uma especializacao (Alto nivel)
    protected Ligacao ligacaoEspecializacao;
    //Objeto que representa graficamente uma ligacao. Esse ligacao eh entre uma especializacao. (Baixo nivel)
    protected Ligacao ligacaoEspecializacaoBaixoNivel;

    public Entidade(ControllerConceitual controllerConceitual) {
        super(controllerConceitual);
        this.selecionado = true;
        this.nome = "entidade";
        this.setLayout(new FlowLayout());
        this.tf = new JTextField(this.nome);
        this.tf.setPreferredSize(new Dimension(90, 25));
        this.tf.setVisible(false);
        this.add(tf);

        this.tf.addActionListener(this);
        this.tf.addKeyListener(this);

    }

    /**
     * Uma entidade por ter varios relacionamentos/associativas. Para saber qual
     * ligacao retornar, eh preciso dizer de qual relacionamento se trata. Esse
     * metodo retorna apenas uma ligacao, se desejar pegar mais de uma ligacao
     * atrelada a um mesmo componente (auto-relacionamento), use o metodo
     * getLigacoes(Componente)
     *
     * @param comp
     * @return
     */
    public Ligacao getLigacao(Componente comp) {
        for (Ligacao l : this.ligacoes) {
            if (l.getPai() == comp) {
                return l;
            }
        }
        return null;
    }

    /**
     * Uma entidade por ter varios relacionamentos/associativas. Para saber qual
     * ligacao retornar, eh preciso dizer de qual relacionamento se trata. Esse
     * metodo pode retornar varias ligacoes em casos de auto-relacionamento
     *
     * @param comp
     * @return
     */
    public List<Ligacao> getLigacoes(Componente comp) {
        List<Ligacao> ligacoes = new ArrayList<Ligacao>();
        for (Ligacao l : this.ligacoes) {
            if (l.getPai() == comp) {
                ligacoes.add(l);
            }
        }
        return ligacoes;
    }

    /**
     * Utilizado para tirar da lista uma determinada ligacao entre um
     * relacionamento/associativa
     *
     * @param comp
     */
    public void removerLigacao(Componente comp) {

        Ligacao excluirEssa = null;
        for (Ligacao l : this.ligacoes) {
            if (l.getPai() == comp) {
                excluirEssa = l;
                break;
            }
        }
        excluirEssa.setVisible(false);
        this.ligacoes.remove(excluirEssa);
    }

    public void adicionaLigacao(Ligacao ligacao) {
        this.ligacoes.add(ligacao);
    }

    public List<Ligacao> listLigacoesRelacionamento() {
        return this.ligacoes;
    }

    public Ligacao getLigacaoEspecializacao() {
        return ligacaoEspecializacao;
    }

    public void setLigacaoEspecializacao(Ligacao ligacaoEspecializacao) {
        this.ligacaoEspecializacao = ligacaoEspecializacao;
    }

    public Ligacao getLigacaoEspecializacaoBaixoNivel() {
        return ligacaoEspecializacaoBaixoNivel;
    }

    public void setLigacaoEspecializacaoBaixoNivel(Ligacao ligacaoEspecializacaoBaixoNivel) {
        this.ligacaoEspecializacaoBaixoNivel = ligacaoEspecializacaoBaixoNivel;
    }

    private void terminaDigitacao() {
        Model.modelEditado();
        nome = tf.getText();
        tf.setVisible(false);
        repaint();
    }

    /**
     * Esse metodo retorna um List de LigacoesAtributo, sao as ligacoes dos
     * atributos da entidade. Usado pela propria LigacaoAtributo para conhecer
     * as posicoes das outras e evitar sobreposicoes de linhas
     *
     * @return
     */
    public List<LigacaoAtributo> listLigacoes() {
        List<LigacaoAtributo> ligacoes = new ArrayList<LigacaoAtributo>();
        for (Atributo atr : atributos) {
            ligacoes.add((LigacaoAtributo) atr.getLigacao());
        }
        return ligacoes;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        //g.drawString(nome, 15, 20);
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

    /**
     * Funcao utilizada para recuperar a lista de todos os atributos
     *
     * @return List<Atributo>
     */
    public List<Atributo> listAtributos() {
        return this.atributos;
    }

    /**
     * Funcao que adiciona um atributo
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
     * Essa funcao remove o atributo informado.
     *
     * @param Atributo at
     */
    public void removeAtributo(Atributo at) {
        at.setSelecionado(false);
        at.setVisible(false);
        Ligacao l = at.getLigacao();
        l.setVisible(false);
        l = null;
        this.atributos.remove(at);
        at = null;
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
            Entidade.this.tf.setText(Entidade.this.tf.getText().trim());
            terminaDigitacao();
        }
    }
}
