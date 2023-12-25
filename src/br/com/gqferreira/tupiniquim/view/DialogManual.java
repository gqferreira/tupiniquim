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
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 * Classe
 *
 * @author Gustavo Ferreira www.gqferreira.com.br Copyright 2013 gustavo.
 */
public class DialogManual extends JDialog implements TreeSelectionListener{

    private JTree tree;
    private JScrollPane spRolagem;
    private JSplitPane splitPane;
    private JEditorPane epConteudoHtml;
    private JScrollPane spRolagemConteudo;
    //private final String caminhoBase="file:/home/gustavo/conteudoManualTupiniquim";
    private final String caminhoBase="br/com/gqferreira/tupiniquim/manual";

    public DialogManual() {
        this.setLayout(new GridLayout(1, 0));
        this.setTitle(Model.lang.getString("manualTitulo"));
        this.setSize(new Dimension(600, 500));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        
        //Cria o editorPane para exibir html e a barra de rolagem
        HTMLEditorKit hed = new HTMLEditorKit();
        StyleSheet ss = hed.getStyleSheet();
        ss.addRule("h1 {color: #777777; font-weight: bold; text-align: center; }");
        ss.addRule(".imagem {text-align: center; padding: 10px; }");
        ss.addRule("tr td {border: solid 1px #CCCCCC; }");
        ss.addRule("p {text-align: justify; padding: 10px; }");
        Document doc = hed.createDefaultDocument();
        
        epConteudoHtml = new JEditorPane();
        epConteudoHtml.setContentType("text/html");
        epConteudoHtml.setBackground(Color.WHITE);
        epConteudoHtml.setEditorKit(hed);
        epConteudoHtml.setDocument(doc);

        epConteudoHtml.setEditable(false);
        spRolagemConteudo = new JScrollPane(epConteudoHtml);

        //Cria o no principal
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(Model.lang.getString("manualNoPrincipal"));

        //Cria a arvore
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        
        //Cria a barra de rolagem
        spRolagem = new JScrollPane(tree);
        //Add the scroll panes to a split pane.
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setTopComponent(spRolagem);
        splitPane.setBottomComponent(spRolagemConteudo);
        splitPane.setDividerLocation(200);
        this.add(splitPane);

        //Cria o no do menu arquivo
        DefaultMutableTreeNode treeMenuArquivo = new DefaultMutableTreeNode(Model.lang.getString("manualMenuArquivo"));
        top.add(treeMenuArquivo);
        
        DefaultMutableTreeNode treeMenuArquivoNovo = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualMenuArquivoNovo"),caminhoBase.concat("/menuArquivo/novo.html")));
        DefaultMutableTreeNode treeMenuArquivoRecentes = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualMenuArquivoRecentes"), caminhoBase.concat("/menuArquivo/recentes.html")));
        DefaultMutableTreeNode treeMenuArquivoSalvar = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualMenuArquivoSalvar"), caminhoBase.concat("/menuArquivo/salvar.html")));
        DefaultMutableTreeNode treeMenuArquivoSalvarComo = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualMenuArquivoSalvarComo"), caminhoBase.concat("/menuArquivo/salvarComo.html")));
        DefaultMutableTreeNode treeMenuArquivoExportarPng = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualMenuArquivoExportarPng"), caminhoBase.concat("/menuArquivo/exportarPng.html")));
        treeMenuArquivo.add(treeMenuArquivoNovo);
        treeMenuArquivo.add(treeMenuArquivoRecentes);
        treeMenuArquivo.add(treeMenuArquivoSalvar);
        treeMenuArquivo.add(treeMenuArquivoSalvarComo);
        treeMenuArquivo.add(treeMenuArquivoExportarPng);
        
        //----

        //Cria o no da barra de ferramentas
        DefaultMutableTreeNode treeBarraFerramentas = new DefaultMutableTreeNode(Model.lang.getString("manualBarraFerramentas"));
        top.add(treeBarraFerramentas);
        
        DefaultMutableTreeNode treeBarraFerramentasDescricao = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualBarraFerramentasDescricao"), caminhoBase.concat("/barraFerramentas/descricao.html")));
        treeBarraFerramentas.add(treeBarraFerramentasDescricao);

        //----

        //Cria o no da barra de propriedades
        DefaultMutableTreeNode treeBarraPropriedades = new DefaultMutableTreeNode(Model.lang.getString("manualBarraPropriedades"));
        //top.add(treeBarraPropriedades);

        DefaultMutableTreeNode treeBarraPropriedadesPosicionamento = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualBarraPropriedadesPosicionamento"), caminhoBase.concat("/barraPropriedades/posicionamento.html")));
        DefaultMutableTreeNode treeBarraPropriedadesTextoObservacao = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualBarraPropriedadesTextoObservacao"), caminhoBase.concat("/barraPropriedades/textoObservacao.html")));
        DefaultMutableTreeNode treeBarraPropriedadesTipoDado = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualBarraPropriedadesTipoDado"), caminhoBase.concat("/barraPropriedades/tipoDado.html")));
        treeBarraPropriedades.add(treeBarraPropriedadesPosicionamento);
        treeBarraPropriedades.add(treeBarraPropriedadesTextoObservacao);
        treeBarraPropriedades.add(treeBarraPropriedadesTipoDado);
        
        //----

        //Cria o no do palco de desenho
        DefaultMutableTreeNode treePalcoDesenho = new DefaultMutableTreeNode(Model.lang.getString("manualPalcoDesenho"));
        top.add(treePalcoDesenho);
        
        DefaultMutableTreeNode treePalcoDesenhoEditarTexto = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualPalcoDesenhoEditarTexto"), caminhoBase.concat("/palcoDesenho/editarTexto.html")));
        DefaultMutableTreeNode treePalcoDesenhoRedimensionar = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualPalcoDesenhoRedimensionar"), caminhoBase.concat("/palcoDesenho/redimensionar.html")));
        DefaultMutableTreeNode treePalcoDesenhoMover = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualPalcoDesenhoMover"), caminhoBase.concat("/palcoDesenho/mover.html")));
        DefaultMutableTreeNode treePalcoDesenhoCardinalidade = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualPalcoDesenhoCardinalidade"), caminhoBase.concat("/palcoDesenho/cardinalidade.html")));
        DefaultMutableTreeNode treePalcoDesenhoEditarAnotacoes = new DefaultMutableTreeNode(new Item(Model.lang.getString("manualPalcoDesenhoEditarAnotacoes"), caminhoBase.concat("/palcoDesenho/editarAnotacoes.html")));
        treePalcoDesenho.add(treePalcoDesenhoEditarTexto);
        treePalcoDesenho.add(treePalcoDesenhoRedimensionar);
        treePalcoDesenho.add(treePalcoDesenhoMover);
        treePalcoDesenho.add(treePalcoDesenhoCardinalidade);
        treePalcoDesenho.add(treePalcoDesenhoEditarAnotacoes);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        //Pega 
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (node == null) {
            return;
        }
        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            DialogManual.Item item = (DialogManual.Item) nodeInfo;
            displayURL(item.url);
        } else {
            //displayURL(helpURL);
        }
    }
    
    private void displayURL(URL url) {
        try {
            if (url != null) {
                epConteudoHtml.setPage(url);
            } else { //null url
                epConteudoHtml.setText("File Not Found");
            }
        } catch (IOException e) {
            System.err.println("Attempted to read a bad URL: " + url);
        }
    }
    
    private class Item {

        public String label;
        public URL url;

        public Item(String label, String filename) {
            try {
                this.label = label;
                this.url = getClass().getClassLoader().getResource(filename);
                //this.url = new URL(filename);
                if (this.url == null) {
                    System.err.println("Couldn't find file: "
                            + filename);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public String toString() {
            return label;
        }
    }
}
