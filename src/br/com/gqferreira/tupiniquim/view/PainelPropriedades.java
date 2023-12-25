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

import br.com.gqferreira.tupiniquim.Model;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

/**
 * Classe
 * @author Gustavo Ferreira
 * www.gqferreira.com.br
 * Copyright 2013 gustavo.
 */
public class PainelPropriedades extends JPanel{

    private JPanel paPosicaoXAltura;
    private JPanel paPosicaoYComprimento;
    private JLabel lbTexto;
    private JTextField tfTexto;
    private JLabel lbPosicaoX;
    private JLabel lbPosicaoY;
    private JSpinner spnPosicaoX;
    private JSpinner spnPosicaoY;
    private JLabel lbAltura;
    private JLabel lbComprimento;
    private JSpinner spnAltura;
    private JSpinner spnComprimento;
    private JLabel lbObservacao;
    private JTextPane tpObservacao;
    private JLabel lbTipoDeDado;
    private JComboBox cbTipoDeDado;
    private JButton btAlterarTexto;
    private JScrollPane spRolagemObservacao;
    private JPanel paTexto;
    
    public PainelPropriedades() {
        initGui();
    }
    
    private void initGui(){
        this.setLayout(new FlowLayout(SwingConstants.HORIZONTAL, 5, 5));
        this.setPreferredSize(new Dimension(200, 500));
        
        paTexto = new JPanel();
        
        btAlterarTexto = new JButton(Model.lang.getString("btAlterarTexto"));
        btAlterarTexto.setPreferredSize(new Dimension(180, 25));
        paTexto.add(btAlterarTexto);
        
        lbTexto = new JLabel(Model.lang.getString("lbTexto"));
        lbPosicaoX = new JLabel(Model.lang.getString("lbPosicaoX"));
        lbPosicaoY = new JLabel(Model.lang.getString("lbPosicaoY"));
        lbAltura = new JLabel(Model.lang.getString("lbAltura"));
        lbComprimento = new JLabel(Model.lang.getString("lbComprimento"));
        lbObservacao = new JLabel(Model.lang.getString("lbObservacao"));        
        lbTipoDeDado = new JLabel(Model.lang.getString("lbTipoDeDado"));
        
        paPosicaoXAltura = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));     
        paPosicaoXAltura.setPreferredSize(new Dimension(90, 90));
        
        paPosicaoYComprimento = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        paPosicaoYComprimento.setPreferredSize(new Dimension(90, 90));
                        
        tfTexto = new JTextField();
        tfTexto.setPreferredSize(new Dimension(180, 25));
        
        spnPosicaoX = new JSpinner();
        spnPosicaoX.setPreferredSize(new Dimension(80, 25));
        
        spnPosicaoY = new JSpinner();
        spnPosicaoY.setPreferredSize(new Dimension(80, 25));
        
        spnAltura = new JSpinner();
        spnAltura.setPreferredSize(new Dimension(80, 25));
        
        spnComprimento = new JSpinner();
        spnComprimento.setPreferredSize(new Dimension(80, 25));
        
        tpObservacao = new JTextPane();
        tpObservacao.setBackground(Color.WHITE);
        
        spRolagemObservacao = new JScrollPane();
        spRolagemObservacao.setPreferredSize(new Dimension(180, 200));
        spRolagemObservacao.setViewportView(tpObservacao);
        
        cbTipoDeDado = new JComboBox(new Atributo.TipoDado[]{Atributo.TipoDado.BIGINT, Atributo.TipoDado.BOOLEAN, Atributo.TipoDado.CHAR, Atributo.TipoDado.DATE, Atributo.TipoDado.DATETIME, Atributo.TipoDado.DOUBLE, Atributo.TipoDado.INT, Atributo.TipoDado.TEXT, Atributo.TipoDado.TIME, Atributo.TipoDado.VARCHAR});
        cbTipoDeDado.setPreferredSize(new Dimension(180, 25));
        
        this.add(lbTexto);
        this.add(paTexto);
        //this.add(tfTexto);
        //this.add(btAlterarTexto);
        
        paPosicaoXAltura.add(lbPosicaoX);
        paPosicaoXAltura.add(spnPosicaoX);
        paPosicaoXAltura.add(lbAltura);
        paPosicaoXAltura.add(spnAltura);
        
        this.add(paPosicaoXAltura);
        
        paPosicaoYComprimento.add(lbPosicaoY);
        paPosicaoYComprimento.add(spnPosicaoY);
        paPosicaoYComprimento.add(lbComprimento);
        paPosicaoYComprimento.add(spnComprimento);
        
        this.add(paPosicaoYComprimento);
        
        this.add(lbObservacao);
        this.add(spRolagemObservacao);
        this.add(lbTipoDeDado);
        this.add(cbTipoDeDado);
    }

    public JTextField getTfTexto() {
        return tfTexto;
    }

    public JSpinner getSpnPosicaoX() {
        return spnPosicaoX;
    }

    public JSpinner getSpnPosicaoY() {
        return spnPosicaoY;
    }

    public JSpinner getSpnAltura() {
        return spnAltura;
    }

    public JSpinner getSpnComprimento() {
        return spnComprimento;
    }

    public JTextPane getTpObservacao() {
        return tpObservacao;
    }

    public JComboBox getCbTipoDeDado() {
        return cbTipoDeDado;
    }

    public JButton getBtAlterarTexto() {
        return btAlterarTexto;
    } 

    public JPanel getPaTexto() {
        return paTexto;
    }
    
}
