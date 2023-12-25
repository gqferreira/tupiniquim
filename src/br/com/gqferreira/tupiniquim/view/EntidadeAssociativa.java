/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.controller.ControllerConceitual;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gustavo
 */
public class EntidadeAssociativa extends Entidade {

    private List<Entidade> entidades = new ArrayList<Entidade>();

    public EntidadeAssociativa(ControllerConceitual controllerConceitual) {
        super(controllerConceitual);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
        g.drawPolygon(new int[]{this.getWidth() / 2, this.getWidth() - 1, this.getWidth() / 2, 0}, new int[]{0, this.getHeight() / 2, this.getHeight() - 1, this.getHeight() / 2}, 4);
        
        drawText(g, this.getWidth(), this.getHeight(), nome, true, true);

        if (this.selecionado) {
            g.fillRect(0, 0, 8, 8);
            g.fillRect(this.getWidth() - 8, 0, 8, 8);
            g.fillRect(0, this.getHeight() - 8, 8, 8);
            g.fillRect(this.getWidth() - 8, this.getHeight() - 8, 8, 8);
        }
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
        if (qtde <= 2) {
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
     * Essa funcao desliga uma determinada entidade. Utilizada quando a ligacao eh
     * removida.
     */
    public void desligaEntidade(Entidade ent) {
        ent.removerLigacao(this);
        ent.setSelecionado(false);
        this.entidades.remove(ent);
    }

    /**
     * Essa funcao remove todas as entidades. Utilizada quando a entidade eh
     * removida.
     */
    public void removeEntidades() {
        for (Entidade ent : this.entidades) {
            ent.removerLigacao(this);
            ent.setSelecionado(false);
        }
    }

    /**
     * Metodo utilizado para recuperar a lista de todas as entidades
     *
     * @return List<Entidade>
     */
    public List<Entidade> listEntidades() {
        return this.entidades;
    }
}
