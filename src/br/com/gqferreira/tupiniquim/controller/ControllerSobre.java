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
import br.com.gqferreira.tupiniquim.view.DialogSobre;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JToggleButton;

/**
 * Classe
 *
 * @author Gustavo Ferreira www.gqferreira.com.br Copyright 2013 gustavo.
 */
public class ControllerSobre implements Controller, ActionListener, MouseListener {

    private DialogSobre dialog;

    @Override
    public void notifyError(Exception retorno) {
    }

    @Override
    public void notifyFinish(Object retorno) {
    }

    @Override
    public void notifyNextStep(Object retorno) {
    }

    public ControllerSobre(DialogSobre dialog) {
        this.dialog = dialog;

        this.dialog.getBtCreditos().addActionListener(this);
        this.dialog.getBtFechar().addActionListener(this);
        this.dialog.getBtLicencas().addActionListener(this);
        this.dialog.getLbDetalhes().addMouseListener(this);
        this.dialog.getLbSite().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.dialog.getBtCreditos()) {
            if (!((JToggleButton) e.getSource()).isSelected()) {
                this.dialog.getBtCreditos().setSelected(false);
                mostraSobre();
            } else {
                mostraCreditos();
                this.dialog.getBtLicencas().setSelected(false);
            }
        } else if (e.getSource() == this.dialog.getBtLicencas()) {
            if (!((JToggleButton) e.getSource()).isSelected()) {
                this.dialog.getBtLicencas().setSelected(false);
                mostraSobre();
            } else {
                mostraLicencas();
                this.dialog.getBtCreditos().setSelected(false);
            }
        } else if (e.getSource() == this.dialog.getBtFechar()) {
            this.dialog.dispose();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == this.dialog.getLbSite()) {
            try {
                Desktop.getDesktop().browse(new URI("http://".concat(Model.lang.getString("site"))));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private void mostraCreditos() {
        this.dialog.remove(this.dialog.getPaInformacoes());
        this.dialog.remove(this.dialog.getPaLicencas());
        this.dialog.add(this.dialog.getPaCreditos(), BorderLayout.CENTER);
        this.dialog.getPaCreditos().setVisible(false);
        this.dialog.getPaCreditos().setVisible(true);
    }

    private void mostraLicencas() {
        this.dialog.remove(this.dialog.getPaCreditos());
        this.dialog.remove(this.dialog.getPaInformacoes());
        this.dialog.add(this.dialog.getPaLicencas(), BorderLayout.CENTER);
        this.dialog.getPaLicencas().setVisible(false);
        this.dialog.getPaLicencas().setVisible(true);
    }

    private void mostraSobre() {
        this.dialog.remove(this.dialog.getPaCreditos());
        this.dialog.remove(this.dialog.getPaLicencas());
        this.dialog.add(this.dialog.getPaInformacoes(), BorderLayout.CENTER);
        this.dialog.getPaInformacoes().setVisible(false);
        this.dialog.getPaInformacoes().setVisible(true);
    }
}
