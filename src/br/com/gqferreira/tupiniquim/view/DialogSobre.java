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
import br.com.gqferreira.tupiniquim.controller.ControllerSobre;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/**
 * Classe
 *
 * @author Gustavo Ferreira www.gqferreira.com.br Copyright 2013 gustavo.
 */
public class DialogSobre extends JDialog {

    private JLabel lbLogo;
    private JPanel paInformacoes;
    private JPanel paCreditos;
    private JPanel paLicencas;
    private JLabel lbNome;
    private JLabel lbVersao;
    private JLabel lbDescricao;
    private JLabel lbSite;
    private JLabel lbCopy;
    private JLabel lbGarantias;
    private JLabel lbDetalhes;
    
    private JToggleButton btCreditos;
    private JToggleButton btLicencas;
    private JButton btFechar;
    private JButton btConhecaOBrasil;
    
    private JPanel paBotoes;
    private JPanel paBotoesEquerda;
    
    private JEditorPane epCreditos;
    private JEditorPane epLicencas;

    public DialogSobre(){
        initComponents();
        new ControllerSobre(this);
    }

    
    private void initComponents() {
                
        this.setModal(true);
        this.setTitle(Model.lang.getString("tituloSobre"));
        this.setResizable(false);
        this.setSize(new Dimension(550, 700));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(5, 5));

        this.lbLogo = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/tupiniquim.png")));
        this.lbLogo.setPreferredSize(new Dimension(550, 350));
        this.add(this.lbLogo, BorderLayout.NORTH);
        
        this.paInformacoes = new JPanel(new FlowLayout());
        this.paInformacoes.setOpaque(false);
        this.paInformacoes.setComponentOrientation(ComponentOrientation.UNKNOWN);
                
        this.lbNome = new JLabel(Model.lang.getString("nome_sistema"), JLabel.CENTER);
        this.lbNome.setPreferredSize(new Dimension(500, 20));
        this.paInformacoes.add(this.lbNome);
        
        this.lbVersao = new JLabel(Model.lang.getString("versao"), JLabel.CENTER);
        this.lbVersao.setPreferredSize(new Dimension(500, 20));
        this.paInformacoes.add(this.lbVersao);
        
        this.lbDescricao = new JLabel("<html><center>".concat(Model.lang.getString("descricao")).concat("<center></html>"), JLabel.CENTER);
        this.lbDescricao.setPreferredSize(new Dimension(500, 60));
        this.paInformacoes.add(this.lbDescricao);
        
        this.lbSite = new JLabel("<html><center><a href=".concat(Model.lang.getString("site")).concat(">").concat(Model.lang.getString("site")).concat("</a>"), JLabel.CENTER);
        this.lbSite.setPreferredSize(new Dimension(500, 20));
        this.lbSite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.paInformacoes.add(this.lbSite);
        
        this.lbCopy = new JLabel(Model.lang.getString("copy"), JLabel.CENTER);
        this.lbCopy.setPreferredSize(new Dimension(500, 20));
        this.paInformacoes.add(this.lbCopy);
        
        this.lbGarantias = new JLabel(Model.lang.getString("garantias"), JLabel.CENTER);
        this.lbGarantias.setPreferredSize(new Dimension(500, 40));
        this.paInformacoes.add(this.lbGarantias);
        
        this.lbDetalhes = new JLabel("<html><center>".concat(Model.lang.getString("detalhes")).concat("<center></html>"), JLabel.CENTER);
        this.lbDetalhes.setPreferredSize(new Dimension(500, 50));
        this.lbDetalhes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.paInformacoes.add(this.lbDetalhes);
        
        this.add(this.paInformacoes, BorderLayout.CENTER);
        
        this.paBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5,5));
        this.paBotoes.setOpaque(false);
        this.paBotoesEquerda = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        this.paBotoesEquerda.setOpaque(false);
        this.paBotoesEquerda.setPreferredSize(new Dimension(475, 40));
        
        this.btCreditos = new JToggleButton(Model.lang.getString("sobreBtCreditos"));
        this.btLicencas = new JToggleButton(Model.lang.getString("sobreBtLicencas"));
        this.btFechar = new JButton(Model.lang.getString("sobreBtFechar"));
                
        this.paBotoesEquerda.add(this.btCreditos);
        this.paBotoesEquerda.add(this.btLicencas);
        this.paBotoes.add(this.paBotoesEquerda);
        this.paBotoes.add(btFechar);
        
        this.add(this.paBotoes, BorderLayout.SOUTH);
        
        this.paCreditos = new JPanel();
        this.epCreditos = new JEditorPane();
        this.epCreditos.setContentType("text/html");
        this.paCreditos.add(this.epCreditos);
        this.epCreditos.setEditable(false);
        this.paCreditos.setOpaque(false);
        this.epCreditos.setOpaque(false);
        this.epCreditos.setText(Model.lang.getString("creditos"));
        
        this.paLicencas = new JPanel();
        this.epLicencas = new JEditorPane();
        this.epLicencas.setContentType("text/html");
        this.paLicencas.add(this.epLicencas);
        this.epLicencas.setEditable(false);
        this.paLicencas.setOpaque(false);
        this.epLicencas.setOpaque(false);
        this.epLicencas.setText(Model.lang.getString("licenca"));        
    }

    public JLabel getLbSite() {
        return lbSite;
    }

    public JLabel getLbDetalhes() {
        return lbDetalhes;
    }

    public JToggleButton getBtCreditos() {
        return btCreditos;
    }

    public JToggleButton getBtLicencas() {
        return btLicencas;
    }

    public JButton getBtFechar() {
        return btFechar;
    }

    public JButton getBtConhecaOBrasil() {
        return btConhecaOBrasil;
    }

    public JPanel getPaInformacoes() {
        return paInformacoes;
    }

    public JPanel getPaCreditos() {
        return paCreditos;
    }

    public JPanel getPaLicencas() {
        return paLicencas;
    }    
}
