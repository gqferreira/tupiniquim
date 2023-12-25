/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.Model;
import br.com.gqferreira.tupiniquim.Model.TextDialogType;
import br.com.gqferreira.tupiniquim.model.InterfaceParameters;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * Classe que exibe um dialog pedindo por algum texto, a resposta sera notificada
 * pela Interface informada.
 * @author Gustavo Ferreira
 */
public class TextDialog extends JDialog implements ActionListener, KeyListener {

    private InterfaceParameters caller;
    private JTextPane tpTexto;
    private JPanel paBotoes;
    private JButton btOk;
    private JButton btCancelar;
    private JScrollPane spRolagem;
    private JLabel lbHelp;

    /**
     * 
     * @param Object[] parameters
     * @param TextDialogType type
     * @param InterfaceParameters caller 
     */
    public TextDialog(Object[] parameters, TextDialogType type, InterfaceParameters caller) {
        //Interface que deve receber a resposta
        this.caller = caller;

        this.setSize(300, 200);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        //Tipo usado para definir o titulo do dialog
        switch (type) {
            case OBSERVACAO: {
                this.setTitle(Model.lang.getString("textDialogObservacao"));
            }
            break;
        }

        this.setLayout(new BorderLayout(5, 5));

        //Campo de texto
        this.tpTexto = new JTextPane();
        this.tpTexto.setText((String) parameters[0]);
        this.tpTexto.addKeyListener(this);

        //Scrool para o painel de texto
        this.spRolagem = new JScrollPane();
        this.spRolagem.setViewportView(this.tpTexto);
        this.add(this.spRolagem, BorderLayout.CENTER);

        //Painel para agrupar os botoes
        this.paBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        this.paBotoes.setPreferredSize(new Dimension(0, 30));
        this.add(this.paBotoes, BorderLayout.SOUTH);

        //Botao cancelar
        this.btCancelar = new JButton(Model.lang.getString("botaoCancelar"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/cancel.png")));
        this.btCancelar.setPreferredSize(new Dimension(100, 25));
        this.btCancelar.addActionListener(this);

        //Botao concluir
        this.btOk = new JButton(Model.lang.getString("botaoOk"), new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/ok.png")));
        this.btOk.setPreferredSize(new Dimension(100, 25));
        this.btOk.addActionListener(this);
        
        this.lbHelp = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/help.png")));
        this.lbHelp.setToolTipText(Model.lang.getString("textDialogHelp"));
        this.lbHelp.setPreferredSize(new Dimension(16, 25));
        
        this.paBotoes.add(this.lbHelp);
        this.paBotoes.add(this.btCancelar);
        this.paBotoes.add(this.btOk);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btCancelar) {
            //Clicou no botao cancelar, somente fecha o dialog
            this.dispose();
        } else {
            //Clicou em concluir, notifica a Interface e fecha o dialog
            this.caller.returnParameters(new Object[]{this.tpTexto.getText().trim()});
            this.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //Quando for pressionado a combinacao CTR+[ENTER] sera fechado o dialog
        //e notificado a Interface, identico ao pressionar do botao 'ok'
        if (e.getModifiers() == Event.CTRL_MASK) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                e.consume();
                this.caller.returnParameters(new Object[]{this.tpTexto.getText().trim()});
                this.dispose();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
