/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.Model;
import br.com.gqferreira.tupiniquim.controller.ControllerMain;
import info.clearthought.layout.TableLayout;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
/**
 *
 * @author Gustavo Ferreira
 */
public class FrameMain extends JFrame {

    private JMenuBar mbMenu;
    private JToggleButton tgbConceitual;
    private JToggleButton tgbLogico;
    private JToggleButton tgbFisico;
    private JLabel lbSalvando;
    private JPanel paStatusBar;
    private JMenu meArquivo;
    private JMenuItem miAbrir;
    private JMenuItem miNovo;
    private JMenu meRecentes;
    private JMenuItem miSalvar;
    private JMenuItem miSalvarComo;
    private JMenuItem miSair;
    private JMenuItem miExportarPng;
    private JMenu meEditar;
    private JMenuItem miDesfazer;
    private JMenuItem miRefazer;
    private JMenuItem miConfiguracoes;
    private JMenu meVer;
    private JMenu meAjuda;
    private JMenuItem miSobre;
    private JMenuItem miConteudoAjuda;
    private ButtonGroup bgTipoModelagem;
    private ControllerMain controller;

    public JMenu getMeArquivo() {
        return meArquivo;
    }

    public JMenuItem getMiAbrir() {
        return miAbrir;
    }

    public JMenuItem getMiNovo() {
        return miNovo;
    }
    
    public JMenu getMeRecentes() {
        return meRecentes;
    }

    public JMenuItem getMiSalvar() {
        return miSalvar;
    }

    public JMenuItem getMiSalvarComo() {
        return miSalvarComo;
    }

    public JMenuItem getMiSair() {
        return miSair;
    }

    public JMenuItem getMiExportarPng() {
        return miExportarPng;
    }

    public JMenu getMeEditar() {
        return meEditar;
    }

    public JMenu getMeVer() {
        return meVer;
    }

    public JMenu getMeAjuda() {
        return meAjuda;
    }

    public JLabel getLbSalvando() {
        return lbSalvando;
    }

    public JToggleButton getTgbConceitual() {
        return tgbConceitual;
    }

    public JToggleButton getTgbLogico() {
        return tgbLogico;
    }

    public JToggleButton getTgbFisico() {
        return tgbFisico;
    }    

    public JMenuItem getMiDesfazer() {
        return miDesfazer;
    }

    public JMenuItem getMiRefazer() {
        return miRefazer;
    }

    public JMenuItem getMiConfiguracoes() {
        return miConfiguracoes;
    }

    public JMenuItem getMiSobre() {
        return miSobre;
    }

    public JMenuItem getMiConteudoAjuda() {
        return miConteudoAjuda;
    }

    
    public FrameMain() {
        initGui();
        controller = new ControllerMain(this);
    }

    private void initGui() {

        {
            //Set Look & Feel
            try {
                //javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/icon.png")).getImage());

        this.setTitle(Model.lang.getString("titulo"));

        /**
         * Layout
         */
        this.setSize(800, 600);
        TableLayout thisLayout = new TableLayout(new double[][]{{TableLayout.FILL}, {TableLayout.FILL, 30.0}});
        thisLayout.setHGap(5);
        thisLayout.setVGap(5);
        getContentPane().setLayout(thisLayout);
        this.setLocationRelativeTo(null);
        {
            paStatusBar = new JPanel();
            FlowLayout paStatusBarLayout = new FlowLayout();
            getContentPane().add(paStatusBar, "0,1,f,f");
            paStatusBar.setLayout(paStatusBarLayout);
            paStatusBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            paStatusBarLayout.setAlignment(FlowLayout.RIGHT);
            {
                lbSalvando = new JLabel();
                paStatusBar.add(lbSalvando);
                lbSalvando.setText(Model.lang.getString("salvando"));
                lbSalvando.setIcon(new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/loading.gif")));
                lbSalvando.setVisible(false);
            }
        }
        
        {
            tgbConceitual = new JToggleButton();
            tgbConceitual.setBorderPainted(false);
            tgbConceitual.setIcon(new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual.png")));
        }
        {
            tgbLogico = new JToggleButton();
            tgbLogico.setBorderPainted(false);
            tgbLogico.setIcon(new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/logico.png")));
        }
        {
            tgbFisico = new JToggleButton();
            tgbFisico.setBorderPainted(false);
            tgbFisico.setIcon(new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/fisico.png")));
        }
        {
            bgTipoModelagem = new ButtonGroup();
            bgTipoModelagem.add(tgbConceitual);
            bgTipoModelagem.add(tgbFisico);
            bgTipoModelagem.add(tgbLogico);
        }

        /**
         * Instanciacao dos menus padroes
         */
        meArquivo = new JMenu(Model.lang.getString("meArquivo"));

        miAbrir = new JMenuItem(Model.lang.getString("miAbrir"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/abrir.png")));
        miAbrir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        meArquivo.add(miAbrir);
        
        miNovo = new JMenuItem(Model.lang.getString("miNovo"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/novo.png")));
        miNovo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        meArquivo.add(miNovo);

        meRecentes = new JMenu(Model.lang.getString("meRecentes"));
        meRecentes.setIcon(new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/recente.png")));
        meArquivo.add(meRecentes);

        miSalvar = new JMenuItem(Model.lang.getString("miSalvar"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/salvar.png")));
        miSalvar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        meArquivo.add(miSalvar);

        miSalvarComo = new JMenuItem(Model.lang.getString("miSalvarComo"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/salvarComo.png")));
        meArquivo.add(miSalvarComo);

        miExportarPng = new JMenuItem(Model.lang.getString("miExportarPng"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/image.png")));
        miExportarPng.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        meArquivo.add(miExportarPng);

        meArquivo.add(new JSeparator());

        miSair = new JMenuItem(Model.lang.getString("miSair"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/sair.png")));
        meArquivo.add(miSair);

        //////////////////
        
        meAjuda = new JMenu(Model.lang.getString("meAjuda"));
        miSobre = new JMenuItem(Model.lang.getString("miSobre"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/sobre.png")));
        meAjuda.add(miSobre);
        
        miConteudoAjuda = new JMenuItem(Model.lang.getString("miConteudoAjuda"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/manual.png")));
        meAjuda.add(miConteudoAjuda);
        
        /////////////////
        
        meEditar = new JMenu(Model.lang.getString("meEditar"));
        
        miDesfazer = new JMenuItem(Model.lang.getString("miDesfazer"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/undo.png")));
        miDesfazer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        meEditar.add(miDesfazer);
        
        miRefazer = new JMenuItem(Model.lang.getString("miRefazer"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/redo.png")));
        miRefazer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        meEditar.add(miRefazer);
        
        miConfiguracoes = new JMenuItem(Model.lang.getString("miConfiguracoes"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/configuracao.png")));
        meEditar.add(miConfiguracoes);
       
        //////////////////
        
        meVer = new JMenu(Model.lang.getString("meVer"));

        mbMenu = new JMenuBar();
        mbMenu.add(meArquivo);
        mbMenu.add(meEditar);
        //mbMenu.add(meVer);
        mbMenu.add(meAjuda);
        //mbMenu.add(tgbConceitual);
        //mbMenu.add(tgbFisico);
        //mbMenu.add(tgbLogico);
        
        //Adicionando a barra de menus no frame
        this.setJMenuBar(mbMenu);

    }
}
