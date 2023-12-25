/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.Model;
import br.com.gqferreira.tupiniquim.controller.ControllerConceitual;
import br.com.gqferreira.tupiniquim.controller.ControllerPropriedades;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

/**
 *
 * @author Gustavo
 */
public class PanelConceitual extends JPanel{
    private JToolBar tbFerramentas;
    private JToolBar tbPropriedades;
    private JButton btEntidade;
    private JButton btEntidadeFraca;
    private JButton btEntidadeAssociativa;
    private JButton btRelacionamento;
    private JButton btEspecializacaoTotal;
    private JButton btEspecializacaoParcial;
    private JButton btAtributo;
    private JButton btAtributoChave;
    private JButton btAtributoDerivado;
    private JButton btAtributoComposto;
    private JButton btAtributoMultivalorado;
    private JButton btAutoRelacionamento;
    private JButton btLigacao;
    private JButton btNotacaoLiteral;
    private JButton btCursor;
    private JScrollPane spRolagemPalco;
    private Palco palco;
    private ControllerPropriedades propCtr;
    private ControllerConceitual controllerConceitual;
    private PainelPropriedades prop;

    public JToolBar getTbFerramentas() {
        return tbFerramentas;
    }

    public JToolBar getTbPropriedades() {
        return tbPropriedades;
    }

    public JButton getBtEntidade() {
        return btEntidade;
    }

    public JButton getBtEntidadeFraca() {
        return btEntidadeFraca;
    }

    public JButton getBtEntidadeAssociativa() {
        return btEntidadeAssociativa;
    }

    public JButton getBtRelacionamento() {
        return btRelacionamento;
    }

    public JButton getBtEspecializacaoTotal() {
        return btEspecializacaoTotal;
    }

    public JButton getBtEspecializacaoParcial() {
        return btEspecializacaoParcial;
    }

    public JButton getBtAtributo() {
        return btAtributo;
    }

    public JButton getBtAtributoChave() {
        return btAtributoChave;
    }

    public JButton getBtAtributoDerivado() {
        return btAtributoDerivado;
    }

    public JButton getBtAtributoComposto() {
        return btAtributoComposto;
    }

    public JButton getBtAtributoMultivalorado() {
        return btAtributoMultivalorado;
    }

    public JButton getBtAutoRelacionamento() {
        return btAutoRelacionamento;
    }

    public JButton getBtLigacao() {
        return btLigacao;
    }

    public JButton getBtNotacaoLiteral() {
        return btNotacaoLiteral;
    }

    public JButton getBtCursor() {
        return btCursor;
    }

    public Palco getPalco() {
        return palco;
    }

    public void setControllerConceitual(ControllerConceitual controllerConceitual) {
        this.controllerConceitual = controllerConceitual;
        propCtr = new ControllerPropriedades(prop, this.controllerConceitual);
    }

    public ControllerPropriedades getPropCtr() {
        return propCtr;
    }
    
    
    public PanelConceitual(){
        initGui();  
        //new ControllerConceitual(this);
    }
    public void initGui(){
        this.setLayout(new BorderLayout(5, 5));
        
        tbFerramentas = new JToolBar(Model.lang.getString("tituloToolBarFerramentas"));
        this.add(tbFerramentas, BorderLayout.NORTH);
        
        /*
         * PROPRIEDADES
         */
        tbPropriedades = new JToolBar(Model.lang.getString("tituloToolBarPropriedades"), JToolBar.VERTICAL);
        tbPropriedades.setLayout(new BorderLayout(0,0));
        prop = new PainelPropriedades();
                
        tbPropriedades.add(prop, BorderLayout.CENTER);
        tbPropriedades.setPreferredSize(new Dimension(200, 500));
        this.add(tbPropriedades, BorderLayout.WEST);
        
        btCursor = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/cursor.png")));
        btCursor.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btCursor);
        
        btEntidade = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/entidade.png")));
        btEntidade.setToolTipText(Model.lang.getString("btEntidadeTip"));
        btEntidade.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btEntidade);
        
        btEntidadeFraca = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/entidadeFraca.png")));
        btEntidadeFraca.setToolTipText(Model.lang.getString("btEntidadeFracaTip"));
        btEntidadeFraca.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btEntidadeFraca);
        
        btEntidadeAssociativa = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/entidadeAssociativa.png")));
        btEntidadeAssociativa.setToolTipText(Model.lang.getString("btEntidadeAssociativaTip"));
        btEntidadeAssociativa.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btEntidadeAssociativa);
       
        btRelacionamento = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/relacionamento.png")));
        btRelacionamento.setToolTipText(Model.lang.getString("btRelacionamentoTip"));
        btRelacionamento.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btRelacionamento);
        
        btEspecializacaoTotal = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/especializacaoTotal.png")));
        btEspecializacaoTotal.setToolTipText(Model.lang.getString("btEspecializacaoTotalTip"));
        btEspecializacaoTotal.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btEspecializacaoTotal);
        
        btEspecializacaoParcial = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/especializacaoParcial.png")));
        btEspecializacaoParcial.setToolTipText(Model.lang.getString("btEspecializacaoParcialTip"));
        btEspecializacaoParcial.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btEspecializacaoParcial);
        
        btAtributo = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/atributo.png")));
        btAtributo.setToolTipText(Model.lang.getString("btAtributoTip"));
        btAtributo.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btAtributo);
        
        btAtributoChave = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/atributoChave.png")));
        btAtributoChave.setToolTipText(Model.lang.getString("btAtributoChaveTip"));
        btAtributoChave.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btAtributoChave);
        
        btAtributoDerivado = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/atributoDerivado.png")));
        btAtributoDerivado.setToolTipText(Model.lang.getString("btAtributoDerivadoTip"));
        btAtributoDerivado.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btAtributoDerivado);
        
        btAtributoComposto = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/atributoComposto.png")));
        btAtributoComposto.setToolTipText(Model.lang.getString("btAtributoCompostoTip"));
        btAtributoComposto.setPreferredSize(new Dimension(30, 30));
        //tbFerramentas.add(btAtributoComposto);        
        
        btAtributoMultivalorado = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/atributoMultivalorado.png")));
        btAtributoMultivalorado.setToolTipText(Model.lang.getString("btAtributoMultivaloradoTip"));
        btAtributoMultivalorado.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btAtributoMultivalorado);
        
        btAutoRelacionamento = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/autoRelacionamento.png")));
        btAutoRelacionamento.setToolTipText(Model.lang.getString("btAutorelacionamentoTip"));
        btAutoRelacionamento.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btAutoRelacionamento);
        
        btLigacao = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/ligacao.png")));
        btLigacao.setToolTipText(Model.lang.getString("btLigacaoTip"));
        btLigacao.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btLigacao);
        
        btNotacaoLiteral = new JButton("", new ImageIcon(getClass().getClassLoader().getResource("br/com/gqferreira/tupiniquim/view/imagens/conceitual/notacaoLiteral.png")));
        btNotacaoLiteral.setToolTipText(Model.lang.getString("btNotacaoLiteralTip"));
        btNotacaoLiteral.setPreferredSize(new Dimension(30, 30));
        tbFerramentas.add(btNotacaoLiteral);
        
        palco = new Palco();
        palco.setPreferredSize(new Dimension(4000, 2000));
        
        spRolagemPalco = new JScrollPane();
        spRolagemPalco.setViewportView(palco);
        spRolagemPalco.getVerticalScrollBar().setUnitIncrement(13); 
        this.add(spRolagemPalco, BorderLayout.CENTER);
    }
}
