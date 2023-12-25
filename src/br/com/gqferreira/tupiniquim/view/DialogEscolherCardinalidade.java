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
import br.com.gqferreira.tupiniquim.model.Cardinalidade;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Classe
 *
 * @author Gustavo Ferreira www.gqferreira.com.br Copyright 2013 gustavo.
 */
public class DialogEscolherCardinalidade extends JDialog {

    private Cardinalidade cardinalidade;
    private JComboBox cbCardinalidade;
    private JButton btDefinir;
    private JLabel lbEscolhaCardinalidade;

    public static void main(String[] args) {
        new DialogEscolherCardinalidade(null).setVisible(true);
    }

    public DialogEscolherCardinalidade(Cardinalidade cardinalidade) {
        this.cardinalidade = cardinalidade;

        this.setTitle(Model.lang.getString("tituloCardinalidade"));
        this.setModal(true);
        this.setSize(200, 110);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout(5, 5));

        this.lbEscolhaCardinalidade = new JLabel(Model.lang.getString("lbEscolhaCardinalidade"));
        this.btDefinir = new JButton(Model.lang.getString("btDefinirCardinalidade"));

        String[] cardinalidades = {"(0,1)", "(0,N)", "(1,1)", "(1,N)"};
        this.cbCardinalidade = new JComboBox(cardinalidades);

        this.add(lbEscolhaCardinalidade, BorderLayout.NORTH);
        this.add(cbCardinalidade, BorderLayout.CENTER);
        this.add(btDefinir, BorderLayout.SOUTH);
        
        this.selecionaCombo();

        this.btDefinir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (DialogEscolherCardinalidade.this.cbCardinalidade.getSelectedIndex()) {
                    case 0: {
                        //(0,1)
                        DialogEscolherCardinalidade.this.cardinalidade.setMinimo(Cardinalidade.Tipo.ZERO);
                        DialogEscolherCardinalidade.this.cardinalidade.setMaximo(Cardinalidade.Tipo.UM);
                    }
                    ;
                    break;
                    case 1: {
                        //(0,N)
                        DialogEscolherCardinalidade.this.cardinalidade.setMinimo(Cardinalidade.Tipo.ZERO);
                        DialogEscolherCardinalidade.this.cardinalidade.setMaximo(Cardinalidade.Tipo.MUITOS);
                    }
                    ;
                    break;
                    case 2: {
                        //(1,1)
                        DialogEscolherCardinalidade.this.cardinalidade.setMinimo(Cardinalidade.Tipo.UM);
                        DialogEscolherCardinalidade.this.cardinalidade.setMaximo(Cardinalidade.Tipo.UM);
                    }
                    ;
                    break;
                    case 3: {
                        //(1,N)
                        DialogEscolherCardinalidade.this.cardinalidade.setMinimo(Cardinalidade.Tipo.UM);
                        DialogEscolherCardinalidade.this.cardinalidade.setMaximo(Cardinalidade.Tipo.MUITOS);
                    }
                    ;
                    break;
                }

                dispose();
            }
        });
    }

    /**
     * Metodo que realiza tarefa de controle para definir qual posicao do combo
     * ser automaticamente selecionada
     */
    private void selecionaCombo() {
        //Cardinalidade MINIMA ou eh zero ou 1
        if (DialogEscolherCardinalidade.this.cardinalidade.getMinimo() == Cardinalidade.Tipo.ZERO) {
            //0
            //Cardinalidade MAXIMA ou eh 1 ou N
            if (DialogEscolherCardinalidade.this.cardinalidade.getMaximo() == Cardinalidade.Tipo.UM){
                //(0,1)
                DialogEscolherCardinalidade.this.cbCardinalidade.setSelectedIndex(0);
            }
            else{
                //(0,N)
                DialogEscolherCardinalidade.this.cbCardinalidade.setSelectedIndex(1);
            }
        } else {
            //1
            //Cardinalidade MAXIMA ou eh 1 ou N
            if (DialogEscolherCardinalidade.this.cardinalidade.getMaximo() == Cardinalidade.Tipo.UM){
                //(1,1)
                DialogEscolherCardinalidade.this.cbCardinalidade.setSelectedIndex(2);
            }
            else{
                //(1,N)
                DialogEscolherCardinalidade.this.cbCardinalidade.setSelectedIndex(3);
            }
        }
    }
}
