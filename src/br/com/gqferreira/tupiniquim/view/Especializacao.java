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

import br.com.gqferreira.tupiniquim.controller.ControllerConceitual;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe
 *
 * @author Gustavo Ferreira www.gqferreira.com.br Copyright 2013 gustavo.
 */
public class Especializacao extends Componente {

    private Entidade altoNivel;
    private List<Entidade> especialistas = new ArrayList<Entidade>();

    public Especializacao(ControllerConceitual controllerConceitual, Entidade altoNivel) {
        super(controllerConceitual);
        this.altoNivel = altoNivel;
        this.selecionado = true;
        this.setLayout(new FlowLayout());
    }

    public Entidade getAltoNivel() {
        return altoNivel;
    }

    public void setAltoNivel(Entidade altoNivel) {
        this.altoNivel = altoNivel;
    }

    public List<Entidade> getEspecialistas() {
        return especialistas;
    }

    public void setEspecialistas(List<Entidade> especialistas) {
        this.especialistas = especialistas;
    }

    public void adicionaEspecialista(Entidade entidade) {
        this.especialistas.add(entidade);
    }

    /**
     * Essa funcao remove a entidade informada.
     *
     * @param Entidade ent
     */
    public void removeEntidade(Entidade ent) {
        ent.setSelecionado(false);
        ent.setVisible(false);
        Ligacao l = ent.getLigacaoEspecializacaoBaixoNivel();
        l.setVisible(false);
        ent.setLigacaoEspecializacaoBaixoNivel(null);
        this.especialistas.remove(ent);
    }

    /**
     * Essa funcao remove todas as entidades
     *
     */
    public void removeEntidades() {
        for (Entidade ent : especialistas) {
            ent.setSelecionado(false);
            Ligacao l = ent.getLigacaoEspecializacaoBaixoNivel();
            if (l != null) {
                l.setVisible(false);
            }
            ent.setLigacaoEspecializacaoBaixoNivel(null);
        }        
        this.especialistas.clear();
    }

    @Override
    public void setSelecionado(boolean select) {
        this.selecionado = select;
        this.setVisible(false);
        this.setVisible(true);
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
    }
}
