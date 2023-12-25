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
package br.com.gqferreira.tupiniquim.controller;

import br.com.gqferreira.tupiniquim.Controller;
import br.com.gqferreira.tupiniquim.Model;
import br.com.gqferreira.tupiniquim.model.InterfaceParameters;
import br.com.gqferreira.tupiniquim.view.Atributo;
import br.com.gqferreira.tupiniquim.view.Componente;
import br.com.gqferreira.tupiniquim.view.Entidade;
import br.com.gqferreira.tupiniquim.view.Especializacao;
import br.com.gqferreira.tupiniquim.view.PainelPropriedades;
import br.com.gqferreira.tupiniquim.view.Relacionamento;
import br.com.gqferreira.tupiniquim.view.TextDialog;
import br.com.gqferreira.tupiniquim.view.Texto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Classe
 *
 * @author Gustavo Ferreira www.gqferreira.com.br Copyright 2013 gustavo.
 */
public class ControllerPropriedades implements Controller, ActionListener, KeyListener, ChangeListener, InterfaceParameters {

    private PainelPropriedades prop;
    private ControllerConceitual controllerConceitual;
    //Componente a que se refere as propriedades
    private Componente comp;
    //Variavel de controle utilizada pelo evento ActionPerformed.
    //Quando o usuario clica sobre um atributo, o metodo 'clicouSobreComponente'
    //posiciona o combo 'tipo de dado' para a posicao 0 (zero). Isso dispara o evento
    //actionPerformed que so deveria ser disparado se o usuario alterasse o valor
    //do combo. Para isso serve essa variavel.
    private boolean calibrouComponentes;

    public ControllerPropriedades(PainelPropriedades prop, ControllerConceitual controllerConceitual) {
        this.prop = prop;
        this.controllerConceitual = controllerConceitual;

        calibracaoPadrao();
        
        this.prop.getSpnAltura().addChangeListener(this);
        prop.getSpnComprimento().addChangeListener(this);
        prop.getSpnPosicaoX().addChangeListener(this);
        prop.getSpnPosicaoY().addChangeListener(this);
        prop.getCbTipoDeDado().addActionListener(this);
        prop.getBtAlterarTexto().addActionListener(this);
        prop.getTfTexto().addKeyListener(this);
        prop.getTpObservacao().addKeyListener(this);
    }

    public void clicouSobreComponente(MouseEvent e) {
        calibrouComponentes = false;

        if (e == null){
            calibracaoPadrao();
        }
        else if (e.getSource() instanceof Componente) {

            prop.getSpnAltura().setEnabled(true);
            prop.getSpnComprimento().setEnabled(true);
            prop.getSpnPosicaoX().setEnabled(true);
            prop.getSpnPosicaoY().setEnabled(true);
            prop.getTfTexto().setEnabled(true);

            comp = (Componente) e.getSource();
            //Ativando o painel de propriedades
            if (e.getSource() instanceof Entidade || e.getSource() instanceof Especializacao || e.getSource() instanceof Relacionamento) {

                /*
                 * ENTIDADE
                 * ESPECIALIZACAO
                 * RELACIONAMENTO
                 */

                //Decidindo pelo botao ou pelo campo de texto. CAMPO DE TEXTO
                prop.getPaTexto().setVisible(false);
                prop.getPaTexto().removeAll();
                prop.getPaTexto().add(prop.getTfTexto());
                prop.getPaTexto().setVisible(true);

                //Liberando ou nao o combo de tipo de dado. NAO LIBERA
                prop.getCbTipoDeDado().setSelectedIndex(0);
                prop.getCbTipoDeDado().setEnabled(false);

                //Liberando ou nao o campo de observacao. LIBERA SIM
                prop.getTpObservacao().setText(null);
                prop.getTpObservacao().setEnabled(true);

                //Liberando ou nao os spiners modificadores de tamanho. LIBERA SIM
                prop.getSpnAltura().setEnabled(true);
                prop.getSpnComprimento().setEnabled(true);

                /*
                 * View preparada, basta agora indicar os valores
                 */

                prop.getTfTexto().setText(comp.getNome());
                prop.getSpnAltura().setValue(comp.getHeight());
                prop.getSpnComprimento().setValue(comp.getWidth());
                prop.getSpnPosicaoX().setValue(comp.getX());
                prop.getSpnPosicaoY().setValue(comp.getY());
                prop.getTpObservacao().setText(comp.getObservacao());
            } else if (e.getSource() instanceof Atributo) {

                /*
                 * ATRIBUTO
                 */

                //Decidindo pelo botao ou pelo campo de texto. CAMPO DE TEXTO
                prop.getPaTexto().setVisible(false);
                prop.getPaTexto().removeAll();
                prop.getPaTexto().add(prop.getTfTexto());
                prop.getPaTexto().setVisible(true);

                //Liberando ou nao o combo de tipo de dado. LIBERA SIM
                prop.getCbTipoDeDado().setSelectedIndex(0);
                prop.getCbTipoDeDado().setEnabled(true);

                //Liberando ou nao o campo de observacao. LIBERA SIM
                prop.getTpObservacao().setText(null);
                prop.getTpObservacao().setEnabled(true);

                //Liberando ou nao os spiners modificadores de tamanho. NAO LIBERA
                prop.getSpnAltura().setEnabled(false);
                prop.getSpnComprimento().setEnabled(false);

                /*
                 * View preparada, basta agora indicar os valores
                 */
                prop.getTfTexto().setText(comp.getNome());
                prop.getSpnAltura().setValue(comp.getHeight());
                prop.getSpnComprimento().setValue(comp.getWidth());
                prop.getSpnPosicaoX().setValue(comp.getX());
                prop.getSpnPosicaoY().setValue(comp.getY());
                prop.getTpObservacao().setText(comp.getObservacao());
                prop.getCbTipoDeDado().setSelectedItem(((Atributo) comp).getTipo());

            } else if (e.getSource() instanceof Texto) {

                /*
                 * TEXTO
                 */

                //Decidindo pelo botao ou pelo campo de texto. BOTAO
                prop.getPaTexto().setVisible(false);
                prop.getPaTexto().removeAll();
                prop.getPaTexto().add(prop.getBtAlterarTexto());
                prop.getPaTexto().setVisible(true);

                //Liberando ou nao o combo de tipo de dado. NAO LIBERA
                prop.getCbTipoDeDado().setSelectedIndex(0);
                prop.getCbTipoDeDado().setEnabled(false);

                //Liberando ou nao os spiners modificadores de tamanho. LIBERA SIM
                prop.getSpnAltura().setEnabled(true);
                prop.getSpnComprimento().setEnabled(true);

                //Liberando ou nao o campo de observacao. NAO LIBERA
                prop.getTpObservacao().setText(null);
                prop.getTpObservacao().setEnabled(false);

                /*
                 * View preparada, basta agora indicar os valores
                 */
                prop.getSpnAltura().setValue(comp.getHeight());
                prop.getSpnComprimento().setValue(comp.getWidth());
                prop.getSpnPosicaoX().setValue(comp.getX());
                prop.getSpnPosicaoY().setValue(comp.getY());
            }
        } else {
            calibracaoPadrao();
        }

        calibrouComponentes = true;
    }

    private void calibracaoPadrao() {
        prop.getPaTexto().setVisible(false);
        prop.getPaTexto().removeAll();
        prop.getPaTexto().add(prop.getTfTexto());
        prop.getTfTexto().setEnabled(false);
        prop.getPaTexto().setVisible(true);

        //Liberando ou nao o combo de tipo de dado. NAO LIBERA
        prop.getCbTipoDeDado().setSelectedIndex(0);
        prop.getCbTipoDeDado().setEnabled(false);

        //Liberando ou nao o campo de observacao. NAO LIBERA
        prop.getTpObservacao().setText(null);
        prop.getTpObservacao().setEnabled(false);

        //Liberando ou nao os spiners modificadores de tamanho. LIBERA SIM
        prop.getSpnAltura().setEnabled(true);
        prop.getSpnComprimento().setEnabled(true);

        prop.getSpnAltura().setValue(0);
        prop.getSpnComprimento().setValue(0);
        prop.getSpnPosicaoX().setValue(0);
        prop.getSpnPosicaoY().setValue(0);
        prop.getSpnAltura().setEnabled(false);
        prop.getSpnComprimento().setEnabled(false);
        prop.getSpnPosicaoX().setEnabled(false);
        prop.getSpnPosicaoY().setEnabled(false);
    }

    @Override
    public void notifyError(Exception retorno) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyFinish(Object retorno) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyNextStep(Object retorno) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (calibrouComponentes) {
            if (e.getSource() == this.prop.getCbTipoDeDado()) {
                if (this.comp != null) {
                    if (this.comp instanceof Atributo) {
                        if (e.getSource() == this.prop.getCbTipoDeDado()) {
                            ((Atributo) this.comp).setTipo((Atributo.TipoDado) this.prop.getCbTipoDeDado().getSelectedItem());
                        }
                    }
                }
            } else if (e.getSource() == this.prop.getBtAlterarTexto()) {
                Object[] parameters = new Object[1];
                parameters[0] = this.comp.getNome();
                new TextDialog(parameters, Model.TextDialogType.OBSERVACAO, this).setVisible(true);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (this.comp != null) {
            if (e.getSource() == this.prop.getTfTexto()) {
                this.comp.setNome(this.prop.getTfTexto().getText());
                this.comp.repaint();
            } else if (e.getSource() == this.prop.getTpObservacao()) {
                this.comp.setObservacao(this.prop.getTpObservacao().getText());
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (calibrouComponentes) {
            if (e.getSource() == prop.getSpnAltura()) {
                Componente comp = Model.stackComponents.getLast();
                if (comp != null) {
                    Model.stackComponents.reset();
                    Model.stackComponents.toPush(comp);
                    comp.setSize(comp.getWidth(), (Integer) prop.getSpnAltura().getValue());
                    controllerConceitual.corrigeLigacoes(comp, 0, 0);
                }
            } else if (e.getSource() == prop.getSpnComprimento()) {
                Componente comp = Model.stackComponents.getLast();
                if (comp != null) {
                    Model.stackComponents.reset();
                    Model.stackComponents.toPush(comp);
                    comp.setSize((Integer) prop.getSpnComprimento().getValue(), comp.getHeight());
                    controllerConceitual.corrigeLigacoes(comp, 0, 0);
                }
            } else if (e.getSource() == prop.getSpnPosicaoX()) {
                Componente comp = Model.stackComponents.getLast();
                if (comp != null) {
                    Model.stackComponents.reset();
                    Model.stackComponents.toPush(comp);
                    comp.setLocation((Integer) prop.getSpnPosicaoX().getValue(), comp.getY());
                    controllerConceitual.corrigeLigacoes(comp, 0, 0);
                }
            } else if (e.getSource() == prop.getSpnPosicaoY()) {
                Componente comp = Model.stackComponents.getLast();
                if (comp != null) {
                    Model.stackComponents.reset();
                    Model.stackComponents.toPush(comp);
                    comp.setLocation(comp.getX(), (Integer) prop.getSpnPosicaoY().getValue());
                    controllerConceitual.corrigeLigacoes(comp, 0, 0);
                }
            }
        }
    }

    @Override
    public void returnParameters(Object[] parameters) {
        if (!this.comp.nome.equals((String) parameters[0])) {
            this.comp.nome = (String) parameters[0];
            this.comp.repaint();
            Model.modelEditado();
        }
    }
}
