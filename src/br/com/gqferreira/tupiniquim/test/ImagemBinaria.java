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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

/**
 * Classe
 *
 * @author Gustavo Ferreira www.gqferreira.com.br Copyright 2013 gustavo.
 */
public class ImagemBinaria extends JFrame {

    private JPanel paImagem;
    private JPanel paComandos;
    private JPanel paConteudoComandos;
    private JButton btAbrirArquivoBinario;
    private JButton btCriarArquivoBinario;
    private JSlider slTolerancia;
    private JScrollPane spRolagem;
    private String conteudoBinario;
    private int[][] conteudoBinarioInteiro;
    private JButton btAbrirImagem;
    private File imagem;
    private File arquivoSalvo;
    private File arquivoLido;
    private JLabel lbCaminhoImagem;
    private Dimension tamanhoImagem;
    private BufferedImage image;
    private int terminou = 0;

    public static void main(String[] args) throws IOException {

        new ImagemBinaria().setVisible(true);
    }

    public ImagemBinaria() {
        {
            //Set Look & Feel
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.setLayout(new BorderLayout());
        this.setTitle("Imagem binaria");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 500);

        this.setLocationRelativeTo(null);

        this.paComandos = new JPanel(new FlowLayout());
        this.paComandos.setPreferredSize(new Dimension(700, 100));
        this.paConteudoComandos = new JPanel(new FlowLayout());
        this.paConteudoComandos.setPreferredSize(new Dimension(650, 100));
        this.paImagem = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, this.getWidth(), this.getHeight());
                if (image != null) {
                    g.drawImage(image, 0, 0, null);
                }
            }
        };

        this.btAbrirArquivoBinario = new JButton("Abrir imagem binaria");
        this.btCriarArquivoBinario = new JButton("Tornar imagem binaria");
        this.btAbrirImagem = new JButton("Abrir imagem");
        this.lbCaminhoImagem = new JLabel("[Escolha uma imagem]");
        this.slTolerancia = new JSlider(0, 255);

        this.btAbrirArquivoBinario.setPreferredSize(new Dimension(200, 25));
        this.btCriarArquivoBinario.setPreferredSize(new Dimension(200, 25));
        this.btAbrirImagem.setPreferredSize(new Dimension(200, 25));
        this.lbCaminhoImagem.setPreferredSize(new Dimension(400, 20));
        this.slTolerancia.setPreferredSize(new Dimension(200, 50));
        this.slTolerancia.setToolTipText("Tolerancia de cor");

        this.paConteudoComandos.add(btAbrirImagem);
        this.paConteudoComandos.add(lbCaminhoImagem);
        this.paConteudoComandos.add(btAbrirArquivoBinario);
        this.paConteudoComandos.add(slTolerancia);
        this.paConteudoComandos.add(btCriarArquivoBinario);
        this.paComandos.add(paConteudoComandos);

        this.spRolagem = new JScrollPane();
        this.spRolagem.setViewportView(paImagem);

        this.add(paComandos, BorderLayout.NORTH);
        this.add(spRolagem, BorderLayout.CENTER);

        this.btAbrirArquivoBinario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fromBinary();
            }
        });

        this.btCriarArquivoBinario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    toBinary();
                } catch (IOException ex) {
                    Logger.getLogger(ImagemBinaria.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        this.btAbrirImagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser(imagem);
                chooser.showOpenDialog(null);
                if (chooser.getSelectedFile() != null) {
                    ImagemBinaria.this.lbCaminhoImagem.setText(chooser.getSelectedFile().getAbsolutePath());
                    ImagemBinaria.this.imagem = chooser.getSelectedFile();
                }
            }
        });
    }

    /**
     * FUNCIONANDO.
     * Transformando para binario. Pede imagem. Gera arquivo de texto e
     * renderiza imagem binaria
     */
    private void toBinary() throws IOException {


        if (imagem != null) {
            BufferedImage img = ImageIO.read(imagem);
            this.tamanhoImagem = new Dimension(img.getWidth(), img.getHeight());

            //Mudando o tamanho do painel e criando o vetor bidimensional
            this.paImagem.setVisible(false);
            this.paImagem.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
            this.paImagem.setVisible(true);

            conteudoBinarioInteiro = new int[img.getHeight()][img.getWidth()];

            for (int y = 0; y < img.getHeight(); ++y) {
                for (int x = 0; x < img.getWidth(); ++x) {
                    Color cor = new Color(img.getRGB(x, y));
                    /* 
                     * A escala eh em cinza e vai de 0 a 255
                     * 255:  BRANCO
                     * 0:    PRETO
                     * 
                     * Se ultrapassar a tolerancia, a cor eh PRETO
                     */
                    if (((cor.getRed() + cor.getBlue() + cor.getGreen()) / 3) > this.slTolerancia.getValue()) {
                        //Ultrapassou a tolerancia.
                        conteudoBinarioInteiro[y][x] = 1;
                    } else {
                        //Nao ultrapassou
                        conteudoBinarioInteiro[y][x] = 0;
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < tamanhoImagem.getHeight(); y++) {
                for (int x = 0; x < tamanhoImagem.getWidth(); x++) {
                    sb.append(String.valueOf(conteudoBinarioInteiro[y][x]).concat(","));
                }
                sb.append("\n");
            }

            conteudoBinario = sb.toString();
            reenderizar();
        }
    }

    /**
     * Importando de um arquivo.
     *
     * Corrigido. Conferir saida para arquivo 'caca.txt' NAO MEXA MAIS
     */
    private void fromBinary() {
        JFileChooser chooser = new JFileChooser(arquivoLido);
        chooser.showOpenDialog(null);
        if (chooser.getSelectedFile() != null) {
            try {
                int comprimento = 0, altura = 0;
                StringBuilder sb = new StringBuilder();
                arquivoLido = chooser.getSelectedFile();
                FileReader arq = new FileReader(arquivoLido);
                BufferedReader lerArq = new BufferedReader(arq);
                String linha = lerArq.readLine(); // le a primeira linha
                sb.append(linha.concat("\n"));
                altura++;
                comprimento = linha.split(",").length;

                // a variavel "linha" recebe o valor "null" quando o processo
                // de repeticao atingir o final do arquivo texto
                while ((linha = lerArq.readLine()) != null) {
                    sb.append(linha.concat("\n"));
                    altura++;
                }

                conteudoBinarioInteiro = new int[altura][comprimento];

                String[] linhasLiterais = sb.toString().split("\n");
                for (int y = 0; y < linhasLiterais.length; y++) {
                    String[] binario = linhasLiterais[y].split(",");
                    for (int x = 0; x < binario.length; x++) {
                        conteudoBinarioInteiro[y][x] = Integer.parseInt(binario[x]);
                    }
                }

                arq.close();

                tamanhoImagem = new Dimension(comprimento, altura);
                conteudoBinario = sb.toString();

                reenderizar();

            } catch (Exception ex) {
                Logger.getLogger(ImagemBinaria.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void terminou() {
        terminou++;
        if (terminou == 3) {
            terminou = 0;
            int escolha = JOptionPane.showConfirmDialog(null, "Deseja salvar a imagem em um arquivo?", "Salvar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (escolha == JOptionPane.YES_OPTION) {
                JFileChooser chooser = new JFileChooser(arquivoSalvo);
                chooser.showOpenDialog(null);
                if (chooser.getSelectedFile() != null) {
                    try {
                        if (chooser.getSelectedFile().getAbsoluteFile().toString().lastIndexOf(".") >= 0) {
                            arquivoSalvo = new File(chooser.getSelectedFile().getAbsoluteFile().toString());
                        } else {
                            arquivoSalvo = new File(chooser.getSelectedFile().getAbsoluteFile().toString().concat(".txt"));
                        }

                        PrintWriter print = new PrintWriter(arquivoSalvo);
                        print.print(conteudoBinario);
                        print.flush();
                        print.close();

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(ImagemBinaria.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    /**
     * Corrigido. Conferir saida para arquivo 'caca.txt' Na duvida, DESCOMENTE
     * as linhas
     */
    private void reenderizar() {
        image = new BufferedImage((int) tamanhoImagem.getWidth(), (int) tamanhoImagem.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.dispose();
        final int time = 260;

        new Thread(new Runnable() {
            @Override
            public void run() {
                int q = 0;
                for (int y = 0; y < tamanhoImagem.getHeight() / 3; y++) {
                    for (int x = 0; x < tamanhoImagem.getWidth(); x++) {
                        try {
                            Graphics2D g = image.createGraphics();
                            if (conteudoBinarioInteiro[y][x] == 0) {
                                //0: PRETO
                                g.setColor(Color.BLACK);
                            } else {
                                //1: BRANCO
                                g.setColor(Color.WHITE);
                            }
                            if (q > time){
                                q = 0;
                                Thread.sleep(1);
                            }
                            //Coloca o pixel
                            g.fillRect(x, y, 1, 1);
                            g.dispose();
                            ImagemBinaria.this.paImagem.repaint();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                terminou();
                System.gc();
            }
        }).start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                int q = 0;
                for (int y = (int)(tamanhoImagem.getHeight()/3); y < (tamanhoImagem.getHeight()/3)*2; y++) {
                    for (int x = 0; x < tamanhoImagem.getWidth(); x++) {
                        try {
                            Graphics2D g = image.createGraphics();
                            if (conteudoBinarioInteiro[y][x] == 0) {
                                //0: PRETO
                                g.setColor(Color.BLACK);
                            } else {
                                //1: BRANCO
                                g.setColor(Color.WHITE);
                            }
                            if (q > time){
                                q = 0;
                                Thread.sleep(1);
                            }
                            //Coloca o pixel
                            g.fillRect(x, y, 1, 1);
                            g.dispose();
                            ImagemBinaria.this.paImagem.repaint();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                terminou();
                System.gc();
            }
        }).start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                int q = 0;
                for (int y = (int)(tamanhoImagem.getHeight()/3)*2; y < tamanhoImagem.getHeight(); y++) {
                    for (int x = 0; x < tamanhoImagem.getWidth(); x++) {
                        try {
                            Graphics2D g = image.createGraphics();
                            if (conteudoBinarioInteiro[y][x] == 0) {
                                //0: PRETO
                                g.setColor(Color.BLACK);
                            } else {
                                //1: BRANCO
                                g.setColor(Color.WHITE);
                            }
                            if (q > time){
                                q = 0;
                                Thread.sleep(1);
                            }
                            //Coloca o pixel
                            g.fillRect(x, y, 1, 1);
                            g.dispose();
                            ImagemBinaria.this.paImagem.repaint();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                terminou();
                System.gc();
            }
        }).start();
    }
}
