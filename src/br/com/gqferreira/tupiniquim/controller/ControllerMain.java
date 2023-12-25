package br.com.gqferreira.tupiniquim.controller;

import br.com.gqferreira.tupiniquim.Controller;
import br.com.gqferreira.tupiniquim.Erro;
import br.com.gqferreira.tupiniquim.Model;
import br.com.gqferreira.tupiniquim.model.bo.ParametrosBo;
import br.com.gqferreira.tupiniquim.model.bo.ProjetoBo;
import br.com.gqferreira.tupiniquim.model.vo.ParametrosVo;
import br.com.gqferreira.tupiniquim.view.Componente;
import br.com.gqferreira.tupiniquim.view.DialogManual;
import br.com.gqferreira.tupiniquim.view.DialogSobre;
import br.com.gqferreira.tupiniquim.view.FrameMain;
import br.com.gqferreira.tupiniquim.view.PanelConceitual;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Classe de controller para controlar as acoes gerais do sistema.
 *
 * @author gustavo
 */
public class ControllerMain implements ActionListener, WindowListener, Controller {

    private FrameMain frameMain;
    private PanelConceitual paConceitual;
    private ControllerConceitual controllerConceitual;

    public ControllerMain(FrameMain frameMain) {
        this.frameMain = frameMain;

        //Menus

        this.frameMain.getMiAbrir().addActionListener(this);
        this.frameMain.getMiNovo().addActionListener(this);
        this.frameMain.getMiExportarPng().addActionListener(this);
        this.frameMain.getMiSair().addActionListener(this);
        this.frameMain.getMiSalvar().addActionListener(this);
        this.frameMain.getMiSalvarComo().addActionListener(this);
        this.frameMain.addWindowListener(this);

        this.frameMain.getMiDesfazer().addActionListener(this);
        this.frameMain.getMiRefazer().addActionListener(this);
        this.frameMain.getMiConfiguracoes().addActionListener(this);
        this.frameMain.getMiSobre().addActionListener(this);
        this.frameMain.getMiConteudoAjuda().addActionListener(this);

        this.frameMain.getTgbConceitual().addActionListener(this);
        this.frameMain.getTgbFisico().addActionListener(this);
        this.frameMain.getTgbLogico().addActionListener(this);

        //Atualizando o menu de projetos recentes
        this.atualizaMenuProjetosrecentes();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Salvando
         */
        if (e.getSource() == this.frameMain.getMiSalvar()) {
            if (!Model.path.isEmpty()) {
                try {
                    this.frameMain.getLbSalvando().setVisible(true);
                    ProjetoBo.salvar(this, Model.path);

                    if (!Model.path.endsWith(".odm")) {
                        //Coloca a extensao .odm caso ainda nao tenha
                        Model.path = Model.path.concat(".odm");
                    }
                    this.frameMain.setTitle(Model.lang.getString("titulo").concat(" - ").concat(Model.path));
                    new ParametrosBo().atualizaUltimosProjetos(Model.path);
                    atualizaMenuProjetosrecentes();

                } catch (FileNotFoundException ex) {
                    Erro.deal(ex);
                }
            }
        } /**
         * Salvar como
         */
        else if (e.getSource() == this.frameMain.getMiSalvarComo()) {
            JFileChooser chooser = new JFileChooser(Model.path);
            chooser.showOpenDialog(null);
            if (chooser.getSelectedFile() != null) {
                try {
                    Model.path = chooser.getSelectedFile().getAbsolutePath();
                    this.frameMain.getLbSalvando().setVisible(true);
                    ProjetoBo.salvar(this, Model.path);

                    if (!Model.path.endsWith(".odm")) {
                        //Coloca a extensao .odm caso ainda nao tenha
                        Model.path = Model.path.concat(".odm");
                    }
                    this.frameMain.setTitle(Model.lang.getString("titulo").concat(" - ").concat(Model.path));
                    new ParametrosBo().atualizaUltimosProjetos(Model.path);
                    atualizaMenuProjetosrecentes();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } /**
         * Criando um novo projeto
         */
        else if (e.getSource() == this.frameMain.getMiNovo()) {
            //Primeiro, verificar se ha um projeto aberto. Avisar o usuario
            //sobre possiveis perdas de dados
            boolean prosseguir = true;
            if (Model.listComponentes().size() > 0) {
                prosseguir = false;
                int i = JOptionPane.showConfirmDialog(null, Model.lang.getString("alertaNovoProjeto"), Model.lang.getString("titulo"), JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    prosseguir = true;
                }
            }
            if (prosseguir) {
                JFileChooser chooser = new JFileChooser(Model.path);
                chooser.showOpenDialog(null);
                if (chooser.getSelectedFile() != null) {
                    Model.clearComponentes();
                    Model.stackComponents.reset();
                    Model.limparPilhas();
                    Model.path = chooser.getSelectedFile().getAbsolutePath();

                    if (this.paConceitual != null) {
                        this.frameMain.remove(this.paConceitual);
                    }
                    Model.abriuProjeto = false;
                    this.paConceitual = new PanelConceitual();
                    this.controllerConceitual = new ControllerConceitual(paConceitual);
                    this.paConceitual.setControllerConceitual(controllerConceitual);
                    this.frameMain.add(this.paConceitual, "0,0,f,f");
                    this.frameMain.repaint();

                    if (!Model.path.endsWith(".odm")) {
                        //Coloca a extensao .odm caso ainda nao tenha
                        Model.path = Model.path.concat(".odm");
                    }
                    this.frameMain.setTitle(Model.lang.getString("titulo").concat(" - ").concat(Model.path));
                }
            }
        } /**
         * Sair, fechar a ferramenta
         */
        else if (e.getSource() == this.frameMain.getMiSair()) {
            int i = JOptionPane.showConfirmDialog(null, Model.lang.getString("confirmacaoSaida"), Model.lang.getString("tituloConfirmacaoSaida"), JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } /**
         * Abrir um projeto
         */
        else if (e.getSource() == this.frameMain.getMiAbrir()) {
            //Primeiro, verificar se ha um projeto aberto. Avisar o usuario
            //sobre possiveis perdas de dados
            boolean prosseguir = true;
            if (Model.listComponentes().size() > 0) {
                prosseguir = false;
                int i = JOptionPane.showConfirmDialog(null, Model.lang.getString("alertaAbrirProjeto"), Model.lang.getString("titulo"), JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    prosseguir = true;
                }
            }
            if (prosseguir) {
                JFileChooser chooser = new JFileChooser(Model.path);
                chooser.showOpenDialog(null);
                if (chooser.getSelectedFile() != null) {
                    try {
                        Model.clearComponentes();
                        Model.limparPilhas();
                        Model.stackComponents.reset();
                        Model.path = chooser.getSelectedFile().getAbsolutePath();

                        if (this.paConceitual != null) {
                            this.frameMain.remove(this.paConceitual);
                        }
                        Model.abriuProjeto = true;
                        this.paConceitual = new PanelConceitual();
                        this.controllerConceitual = new ControllerConceitual(paConceitual);
                        ControllerMain.this.paConceitual.setControllerConceitual(controllerConceitual);
                        this.frameMain.add(this.paConceitual, "0,0,f,f");
                        this.frameMain.repaint();
                        Model.graphics = this.paConceitual.getGraphics();

                        new ParametrosBo().atualizaUltimosProjetos(Model.path);
                        atualizaMenuProjetosrecentes();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } /**
         * Exportar png
         */
        else if (e.getSource() == this.frameMain.getMiExportarPng()) {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            if (chooser.getSelectedFile() != null) {

                int menorX = 0;
                int menorY = 0;
                int maiorX = 0;
                int maiorY = 0;
                for (Componente co : Model.listComponentes()) {

                    if (menorX == 0) {
                        menorX = co.getX();
                    }
                    if (menorY == 0) {
                        menorY = co.getY();
                    }
                    if (maiorX == 0) {
                        maiorX = co.getX();
                    }
                    if (maiorY == 0) {
                        maiorY = co.getY();
                    }

                    //---

                    if (co.getX() < menorX) {
                        menorX = co.getX();
                    }
                    if (co.getY() < menorY) {
                        menorY = co.getY();
                    }
                    if (co.getX() + co.getWidth() > maiorX) {
                        maiorX = co.getX() + co.getWidth();
                    }
                    if (co.getY() + co.getHeight() > maiorY) {
                        maiorY = co.getY() + co.getHeight();
                    }
                }

                BufferedImage imagem = null;
                BufferedImage novaImagem = null;
                try {
                    String file = "TMP".concat(String.valueOf(new Date().getTime())).concat("png");

                    //Crio o buffer do tamanho do painel
                    imagem = new BufferedImage(this.paConceitual.getPalco().getWidth(), this.paConceitual.getPalco().getHeight(), BufferedImage.TYPE_INT_ARGB);
                    //Crio o grafico
                    Graphics graphics = imagem.createGraphics();
                    //Desenho um retangulo branco
                    graphics.fillRect(0, 0, this.paConceitual.getPalco().getWidth(), this.paConceitual.getPalco().getHeight());
                    //Transfiro o grafico do painel para o objeto
                    this.paConceitual.getPalco().paintComponents(graphics);
                    //Dispacho e finaliza a renderizacao no objeto
                    graphics.dispose();
                    //Salvo em um local temporario
                    ImageIO.write(imagem, "png", new File(file));

                    //=============================

                    //Crio um novo buffer, usando as novas dimensoes
                    novaImagem = new BufferedImage((maiorX - menorX) + 20, (maiorY - menorY) + 20, BufferedImage.TYPE_INT_ARGB);
                    //Importo a imagem
                    Image image = new ImageIcon(file).getImage();
                    //Crio o grafico
                    Graphics novographics = novaImagem.createGraphics();
                    //Desenho um retangulo branco
                    novographics.fillRect(0, 0, (maiorX - menorX) + 20, (maiorY - menorY) + 20);
                    //Desenho a imagem, nas posicoes desejadas
                    novographics.drawImage(image, (menorX * -1) + 10, (menorY * -1) + 10, null);
                    //Dispacho e finalizo a renderizacao no objeto
                    novographics.dispose();

                    //Excluo a imagem temporaria
                    new File(file).delete();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                new ProjetoBo().exportarImagemConceitual(this, novaImagem, chooser.getSelectedFile().getAbsolutePath() + ".png");
                JOptionPane.showMessageDialog(null, Model.lang.getString("alertaImagemExportada"), Model.lang.getString("titulo"), JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == this.frameMain.getMiDesfazer()) {
            if (this.paConceitual != null) {
                this.controllerConceitual.desfazer();
            }
        } else if (e.getSource() == this.frameMain.getMiRefazer()) {
            if (this.paConceitual != null) {
                this.controllerConceitual.refazer();
            }
        } else if (e.getSource() == this.frameMain.getMiSobre()) {
            new DialogSobre().setVisible(true);
        } else if (e.getSource() == this.frameMain.getMiConteudoAjuda()) {
            new DialogManual().setVisible(true);
        } else if (e.getSource() == this.frameMain.getTgbConceitual()) {
            trocaConceitual();
        } else if (e.getSource() == this.frameMain.getTgbFisico()) {
            trocaFisico();
        } else if (e.getSource() == this.frameMain.getTgbLogico()) {
            trocaLogico();
        }
    }

    private void trocaConceitual() {
        JOptionPane.showMessageDialog(null, "Conceitual");
    }

    private void trocaLogico() {
        JOptionPane.showMessageDialog(null, "Logico");
    }

    private void trocaFisico() {
        JOptionPane.showMessageDialog(null, "Fisico");
    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        int i = JOptionPane.showConfirmDialog(null, Model.lang.getString("confirmacaoSaida"), Model.lang.getString("tituloConfirmacaoSaida"), JOptionPane.YES_NO_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void notifyError(Exception ex) {
        Erro.deal(ex);
    }

    @Override
    public void notifyFinish(Object retorno) {
        this.frameMain.getLbSalvando().setVisible(false);
    }

    @Override
    public void notifyNextStep(Object retorno) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Metodo responsavel por atualizar o menu de projetos recentes
     */
    private void atualizaMenuProjetosrecentes() {
        //Primeiro retira todos os itens do menu
        this.frameMain.getMeRecentes().removeAll();
        //Recupera os parametros
        ParametrosVo parametros = new ParametrosBo().getParametros();
        //roda cada um dos projetos
        for (String proj : parametros.getUltimosProjetos()) {
            final String projFinal = proj.toString();
            JMenuItem mi = new JMenuItem(projFinal);
            mi.addActionListener(new ActionListener() {
                String projeto = projFinal;

                @Override
                public void actionPerformed(ActionEvent e) {
                    //Primeiro, verificar se ha um projeto aberto. Avisar o usuario
                    //sobre possiveis perdas de dados
                    boolean prosseguir = true;
                    if (Model.listComponentes().size() > 0) {
                        prosseguir = false;
                        int i = JOptionPane.showConfirmDialog(null, Model.lang.getString("alertaAbrirProjeto"), Model.lang.getString("titulo"), JOptionPane.YES_NO_OPTION);
                        if (i == JOptionPane.YES_OPTION) {
                            prosseguir = true;
                        }
                    }
                    if (prosseguir) {
                        Model.limparPilhas();
                        Model.clearComponentes();
                        Model.stackComponents.reset();
                        Model.path = projeto;

                        if (ControllerMain.this.paConceitual != null) {
                            ControllerMain.this.frameMain.remove(ControllerMain.this.paConceitual);
                        }
                        Model.abriuProjeto = true;
                        ControllerMain.this.paConceitual = new PanelConceitual();
                        ControllerMain.this.controllerConceitual = new ControllerConceitual(paConceitual);
                        ControllerMain.this.paConceitual.setControllerConceitual(controllerConceitual);
                        ControllerMain.this.frameMain.add(ControllerMain.this.paConceitual, "0,0,f,f");
                        ControllerMain.this.frameMain.repaint();

                        if (!Model.path.endsWith(".odm")) {
                            //Coloca a extensao .odm caso ainda nao tenha
                            Model.path = Model.path.concat(".odm");
                        }
                        ControllerMain.this.frameMain.setTitle(Model.lang.getString("titulo").concat(" - ").concat(Model.path));
                        atualizaMenuProjetosrecentes();
                    }
                }
            });
            this.frameMain.getMeRecentes().add(mi);
        }
    }
}