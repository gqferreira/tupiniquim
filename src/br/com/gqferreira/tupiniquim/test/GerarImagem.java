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

package br.com.gqferreira.tupiniquim.test;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;

/**
 * Classe
 * @author Gustavo Ferreira
 * www.gqferreira.com.br
 * Copyright 2013 gustavo.
 */
public class GerarImagem extends JFrame{

    private JButton btPrintar;
    private JTextPane tpTexto;
    
    public static void main(String[] args){
        new GerarImagem().setVisible(true);
    }
    
    public GerarImagem() throws HeadlessException {
        
        this.setSize(500, 500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        this.btPrintar = new JButton("PRINTAR");
        this.tpTexto = new JTextPane();
        
        this.add(this.btPrintar, BorderLayout.NORTH);
        this.add(this.tpTexto, BorderLayout.CENTER);
        
        this.btPrintar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage imagem = null;
                BufferedImage novaImagem = null;
                try {
                    String file = "TMP".concat(String.valueOf(new Date().getTime())).concat("png");
                    
                    //Crio o buffer do tamanho do painel
                    imagem = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                    //Crio o grafico
                    Graphics graphics = imagem.createGraphics();
                    //Desenho um retangulo branco
                    graphics.fillRect(0, 0, getWidth(), getHeight());
                    //Transfiro o grafico do painel para o objeto
                    paintComponents(graphics);
                    //Dispacho e finaliza a renderizacao no objeto
                    graphics.dispose();
                    //Salvo em um local temporario
                    ImageIO.write(imagem, "png", new File(file));
                    
                    //=============================
                                        
                    //Crio um novo buffer, usando as novas dimensoes
                    novaImagem = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
                    //Importo a imagem
                    Image image = new ImageIcon(file).getImage();
                    //Crio o grafico
                    Graphics novographics = novaImagem.createGraphics();
                    //Desenho um retangulo branco
                    novographics.fillRect(0, 0, 100, 100);
                    //Desenho a imagem, nas posicoes desejadas
                    novographics.drawImage(image,0, 0, null);
                    //Dispacho e finalizo a renderizacao no objeto
                    novographics.dispose();
                    
                    ImageIO.write(novaImagem, "png", new File("nova.png"));
                    
                    //Excluo a imagem temporaria
                    new File(file).delete();
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
