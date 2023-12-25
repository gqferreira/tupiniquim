/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.controller;

import br.com.gqferreira.tupiniquim.Controller;
import br.com.gqferreira.tupiniquim.Erro;
import br.com.gqferreira.tupiniquim.Model;
import br.com.gqferreira.tupiniquim.model.XDimension;
import br.com.gqferreira.tupiniquim.model.bo.ProjetoBo;
import br.com.gqferreira.tupiniquim.view.Atributo;
import br.com.gqferreira.tupiniquim.view.AtributoChave;
import br.com.gqferreira.tupiniquim.view.AtributoComposto;
import br.com.gqferreira.tupiniquim.view.AtributoComum;
import br.com.gqferreira.tupiniquim.view.AtributoDerivado;
import br.com.gqferreira.tupiniquim.view.AtributoMultivalorado;
import br.com.gqferreira.tupiniquim.view.Componente;
import br.com.gqferreira.tupiniquim.view.DialogEscolherCardinalidade;
import br.com.gqferreira.tupiniquim.view.Entidade;
import br.com.gqferreira.tupiniquim.view.EntidadeAssociativa;
import br.com.gqferreira.tupiniquim.view.EntidadeComum;
import br.com.gqferreira.tupiniquim.view.EntidadeFraca;
import br.com.gqferreira.tupiniquim.view.Especializacao;
import br.com.gqferreira.tupiniquim.view.EspecializacaoParcial;
import br.com.gqferreira.tupiniquim.view.EspecializacaoTotal;
import br.com.gqferreira.tupiniquim.view.Ligacao;
import br.com.gqferreira.tupiniquim.view.LigacaoAltoNivelEspecializacao;
import br.com.gqferreira.tupiniquim.view.LigacaoAtributo;
import br.com.gqferreira.tupiniquim.view.LigacaoEntidadeEspecializacao;
import br.com.gqferreira.tupiniquim.view.LigacaoRelacionamentoAtributo;
import br.com.gqferreira.tupiniquim.view.LigacaoRelacionamentoEntidade;
import br.com.gqferreira.tupiniquim.view.PainelPropriedades;
import br.com.gqferreira.tupiniquim.view.PanelConceitual;
import br.com.gqferreira.tupiniquim.view.Relacionamento;
import br.com.gqferreira.tupiniquim.view.Texto;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

/**
 * Classe responsavel por controlar eventos de view do painel conceitual
 *
 * @author Gustavo Ferreira
 */
public class ControllerConceitual implements Controller, ActionListener, MouseListener, MouseMotionListener {

    private PanelConceitual conceitual;
    private JPanel paGlassSelecao;
    private JPanel paDrag;
    //Usado quando o usuario vai fazer uma ligacao e precisa clicar sobre dois 
    //componentes para so depois adicionar a ligacao.
    private Componente ultimoComponenteClicado = null;
    private PainelPropriedades prop;

    public ControllerConceitual(final PanelConceitual conceitual) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Erro.deal(ex);
                }
                ControllerConceitual.this.conceitual = conceitual;
                //Registrando os listeners do palco
                ControllerConceitual.this.conceitual.getPalco().addMouseListener(ControllerConceitual.this);
                ControllerConceitual.this.conceitual.getPalco().addMouseMotionListener(ControllerConceitual.this);

                //Se o projeto foi aberto e nao criado, deve-se popular os objetos
                if (Model.abriuProjeto) {
                    //Popula os componentes na tela.
                    ControllerConceitual.this.restaurarComponentes(true);
                }

                //Instanciando e sobrescrevendo o paintComponent do painel que servira
                //para desenhar o tracejado 
                ControllerConceitual.this.paGlassSelecao = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics gx) {
                        super.paintComponent(gx);
                        Graphics2D g = (Graphics2D) gx.create();

                        //Linha tracejada
                        float dash1[] = {5.0f};
                        BasicStroke dashed = new BasicStroke(1.0f,
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                10.0f, dash1, 0.0f);

                        g.setStroke(dashed);

                        g.setColor(Color.BLACK);

                        //Desenhando o retangulo, usando o estilo de linha tracejada configurada anteriormente
                        if ((int) conceitual.getPalco().getMousePosition().getX() < Model.x && (int) conceitual.getPalco().getMousePosition().getY() < Model.y) {
                            Model.xSelecao = (int) conceitual.getPalco().getMousePosition().getX();
                            Model.ySelecao = (int) conceitual.getPalco().getMousePosition().getY();
                            Model.wSelecao = Model.x - (int) conceitual.getPalco().getMousePosition().getX();
                            Model.hSelecao = Model.y - (int) conceitual.getPalco().getMousePosition().getY();
                            g.drawRect(Model.xSelecao, Model.ySelecao, Model.wSelecao, Model.hSelecao);
                        } else if ((int) conceitual.getPalco().getMousePosition().getX() < Model.x && (int) conceitual.getPalco().getMousePosition().getY() > Model.y) {
                            Model.xSelecao = (int) conceitual.getPalco().getMousePosition().getX();
                            Model.ySelecao = Model.y;
                            Model.wSelecao = Model.x - (int) conceitual.getPalco().getMousePosition().getX();
                            Model.hSelecao = (int) conceitual.getPalco().getMousePosition().getY() - Model.y;
                            g.drawRect(Model.xSelecao, Model.ySelecao, Model.wSelecao, Model.hSelecao);
                        } else if ((int) conceitual.getPalco().getMousePosition().getX() > Model.x && (int) conceitual.getPalco().getMousePosition().getY() < Model.y) {
                            Model.xSelecao = Model.x;
                            Model.ySelecao = (int) conceitual.getPalco().getMousePosition().getY();
                            Model.wSelecao = (int) conceitual.getPalco().getMousePosition().getX() - Model.x;
                            Model.hSelecao = Model.y - (int) conceitual.getPalco().getMousePosition().getY();
                            g.drawRect(Model.xSelecao, Model.ySelecao, Model.wSelecao, Model.hSelecao);
                        } else {
                            Model.xSelecao = Model.x;
                            Model.ySelecao = Model.y;
                            Model.wSelecao = (int) conceitual.getPalco().getMousePosition().getX() - Model.x;
                            Model.hSelecao = (int) conceitual.getPalco().getMousePosition().getY() - Model.y;
                            g.drawRect(Model.xSelecao, Model.ySelecao, Model.wSelecao, Model.hSelecao);
                        }
                    }
                };

                ControllerConceitual.this.conceitual.getPalco().add(paGlassSelecao);
                ControllerConceitual.this.paGlassSelecao.setOpaque(false);
                ControllerConceitual.this.paGlassSelecao.setVisible(false);

                ControllerConceitual.this.paDrag = new JPanel(null);
                ControllerConceitual.this.paDrag.setOpaque(true);
                ControllerConceitual.this.paDrag.setBackground(Color.red);
                ControllerConceitual.this.paDrag.addMouseMotionListener(ControllerConceitual.this);

                //Exemplo de keystroke mais complexo
                //KeyStroke d = KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK);

                {//DELETE (DEL)
                    //Capturando o InputMap do painel, opcao para quando a janela onde o painel
                    //estiver esteja focada.
                    InputMap imap = ControllerConceitual.this.conceitual.getPalco().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                    //Registrando o stroke (tecla) no map
                    imap.put(KeyStroke.getKeyStroke((char) 127), "conceitual.delete");
                    //Capturando o ActionMap do painelChave
                    ActionMap amap = ControllerConceitual.this.conceitual.getPalco().getActionMap();
                    //Mapeia o registro do stroke para o Action desejado
                    amap.put("conceitual.delete", deletarComponentes);
                }

                {//DESFAZER (CTR+Z)
                    //Capturando o InputMap do painel, opcao para quando a janela onde o painel
                    //estiver esteja focada.
                    InputMap imap = ControllerConceitual.this.conceitual.getPalco().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                    //Registrando o stroke (tecla) no map
                    imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK), "conceitual.undo");
                    //Capturando o ActionMap do painelChave
                    ActionMap amap = ControllerConceitual.this.conceitual.getPalco().getActionMap();
                    //Mapeia o registro do stroke para o Action desejado
                    amap.put("conceitual.undo", desfazer);
                }

                {//REFAZER (CTR+Y)
                    //Capturando o InputMap do painel, opcao para quando a janela onde o painel
                    //estiver esteja focada.
                    InputMap imap = ControllerConceitual.this.conceitual.getPalco().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                    //Registrando o stroke (tecla) no map
                    imap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK), "conceitual.redo");
                    //Capturando o ActionMap do painelChave
                    ActionMap amap = ControllerConceitual.this.conceitual.getPalco().getActionMap();
                    //Mapeia o registro do stroke para o Action desejado
                    amap.put("conceitual.redo", refazer);
                }

                //Registrando o listener para todos os botoes da barra de ferramentas
                for (Component c : ControllerConceitual.this.conceitual.getTbFerramentas().getComponents()) {
                    if (c instanceof JButton) {
                        ((JButton) c).addActionListener(ControllerConceitual.this);
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    Model.graphics = ControllerConceitual.this.conceitual.getGraphics();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void notifyError(Exception retorno) {
        Erro.deal(retorno);
    }

    @Override
    public void notifyFinish(Object retorno) {
    }

    @Override
    public void notifyNextStep(Object retorno) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Clicou em algo fora do componente, ele passa a nao ser considerado 'ativo' ou em 'edicao'
        Model.stackComponents.reset();
        this.ultimoComponenteClicado = null;

        if (e.getSource() == this.conceitual.getBtCursor()) {
            //Primeiro botao da barra de ferramentas, o cursor!
            Cursor cursor = Cursor.getDefaultCursor();
            this.conceitual.getPalco().setCursor(cursor);
            Model.opcao = Model.Opcao.CURSOR;
        } else {
            //Clicou em algum botao da barra de ferramentas, muda o cursor pois espera-se que o usuario ira
            //clicar no ponto do palco que deseja posicionar o componente
            Cursor cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
            this.conceitual.getPalco().setCursor(cursor);

            //Definindo nas Swap qual sera o tipo de componente que sera colocado quando 
            //for clicado no palco
            if (e.getSource() == this.conceitual.getBtEntidade()) {
                Model.opcao = Model.Opcao.ENTIDADE;
            } else if (e.getSource() == this.conceitual.getBtEntidadeFraca()) {
                Model.opcao = Model.Opcao.ENTIDADE_FRACA;
            } else if (e.getSource() == this.conceitual.getBtRelacionamento()) {
                Model.opcao = Model.Opcao.RELACIONAMENTO;
            } else if (e.getSource() == this.conceitual.getBtEntidadeAssociativa()) {
                Model.opcao = Model.Opcao.ENTIDADE_ASSOCIATIVA;
            } else if (e.getSource() == this.conceitual.getBtEspecializacaoParcial()) {
                Model.opcao = Model.Opcao.ESPECIALIZACAO_PARCIAL;
            } else if (e.getSource() == this.conceitual.getBtEspecializacaoTotal()) {
                Model.opcao = Model.Opcao.ESPECIALIZACAO_TOTAL;
            } else if (e.getSource() == this.conceitual.getBtNotacaoLiteral()) {
                Model.opcao = Model.Opcao.TEXTO;
            } else if (e.getSource() == this.conceitual.getBtAtributo()) {
                Model.opcao = Model.Opcao.ATRIBUTO_COMUM;
            } else if (e.getSource() == this.conceitual.getBtAtributoChave()) {
                Model.opcao = Model.Opcao.ATRIBUTO_CHAVE;
            } else if (e.getSource() == this.conceitual.getBtAtributoDerivado()) {
                Model.opcao = Model.Opcao.ATRIBUTO_DERIVADO;
            } else if (e.getSource() == this.conceitual.getBtAtributoComposto()) {
                Model.opcao = Model.Opcao.ATRIBUTO_COMPOSTO;
            } else if (e.getSource() == this.conceitual.getBtAtributoMultivalorado()) {
                Model.opcao = Model.Opcao.ATRIBUTO_MULTIVALORADO;
            } else if (e.getSource() == this.conceitual.getBtLigacao()) {
                Model.opcao = Model.Opcao.LIGACAO;
            } else if (e.getSource() == this.conceitual.getBtAutoRelacionamento()) {
                Model.opcao = Model.Opcao.AUTO_RELACIONAMENTO;
            }

        }
    }

    @Override
    //Clicou no elemento. Apertou o botao do mouse e soltou.
    public void mouseClicked(final MouseEvent e) {

        if (e.getSource() == this.conceitual.getPalco()) {
            /*
             * #### CLICOU NO PALCO
             */
            clicouSobrePalco(e.getPoint());
        } else if (e.getSource().getClass().getSuperclass().getSuperclass() == Componente.class || e.getSource().getClass().getSuperclass() == Componente.class) {
            /*
             * ### CLICOU EM UM COMPONENTE, UM ELEMENTO DO PALCO
             */
            clicouSobreComponente(e.getSource(), e.getPoint(), e.getClickCount(), e.getButton());
        } else if (e.getSource() instanceof Ligacao && e.getButton() == 3) {

            //Clicou sobre uma ligacao, serah apresentado um menu suspenso para 
            //permitir remocao da linha e configuracao de cardinalidade

            if (((Ligacao) e.getSource()).isSobreCardinalidade()) {
                // build poup menu
                JPopupMenu popup = new JPopupMenu();

                // New project menu item
                JMenuItem menuItem = new JMenuItem(Model.lang.getString("miAlterarCardinalidade"));
                menuItem.addActionListener(new ActionListener() {
                    Ligacao lig = (Ligacao) e.getSource();

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new DialogEscolherCardinalidade(lig.getCardinalidade()).setVisible(true);
                        lig.repaint();
                    }
                });
                popup.add(menuItem);

                // New project menu item
                menuItem = new JMenuItem(Model.lang.getString("miRemoverLigacao"));
                menuItem.addActionListener(new ActionListener() {
                    Ligacao lig = (Ligacao) e.getSource();

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Componente pai = lig.getPai();
                        if (pai instanceof EntidadeAssociativa) {
                            ((EntidadeAssociativa) pai).desligaEntidade((Entidade) lig.getFilho());
                        } else if (pai instanceof Relacionamento) {
                            ((Relacionamento) pai).desligaEntidade((Entidade) lig.getFilho());
                        }
                    }
                });
                popup.add(menuItem);

                popup.show(ControllerConceitual.this.conceitual.getPalco(), (int) ControllerConceitual.this.conceitual.getPalco().getMousePosition().getX(), (int) ControllerConceitual.this.conceitual.getPalco().getMousePosition().getY());
            }
            //Ok, essa ligacao nao esta com a cardinalidade destacada, mas e se houver
            //outras por baixo que coincidam com a posicao da cardinalidade com a do mouse?

        } else if (e.getSource() instanceof Ligacao && e.getButton() == 1 && e.getClickCount() == 2) {
            Componente c = verificarComponentePosicao(this.conceitual.getPalco().getMousePosition());
            if (c != null) {
                c.duploClique();
            }
        } else if (e.getSource() instanceof Ligacao && e.getButton() == 1 && e.getClickCount() == 1) {
            //O usuario clicou uma vez sobre a ligacao. Se nao houver nada por baixo da ligacao
            //considera-se que clicou sobre o palco, se houver algum componente, interrompe
            Componente c = verificarComponentePosicao(this.conceitual.getPalco().getMousePosition());
            if (c == null) {
                clicouSobrePalco(this.conceitual.getPalco().getMousePosition());
            } else {
                Point pComponente = new Point((int) this.conceitual.getPalco().getMousePosition().getX() - c.getX(), (int) this.conceitual.getPalco().getMousePosition().getY() - c.getY());
                clicouSobreComponente(c, pComponente, e.getClickCount(), e.getButton());
            }
        }
    }

    private void clicouSobreComponente(Object source, Point point, int clickCount, int button) {

        /*
         * ### CLICOU EM UM COMPONENTE, UM ELEMENTO DO PALCO
         */

        //Vamos verificar o seguinte, se tiver prestes a inserir um atributo 
        //e clicar sobre um COMPONENTE, adiciona o atributo atrelado a esse
        //componente
        Componente componente = null;
        switch (Model.opcao) {
            case AUTO_RELACIONAMENTO: {
                if (source instanceof Entidade) {

                    //auto relacionamento.
                    //Colocar automaticamente um relacionamento e DUAS ligacoes

                    //Processo de criar o componente do tipo Relacionamento e posiciona-lo no palco
                    Relacionamento relacionamento = new Relacionamento(this);
                    relacionamento.setBounds(((Entidade) source).getX(), ((Entidade) source).getY() - 80, 100, 50);

                    /*Adicionando no relacionamento a entidade duas vezes,
                     * o objeto eh o mesmo mas estara referenciado em duas
                     * posicoes da lista de entidades do relacionamento                                     
                     */
                    relacionamento.adicionaEntidade((Entidade) source);
                    relacionamento.adicionaEntidade((Entidade) source);

                    /*
                     * LIGACAO 1 (PRIMEIRA)
                     */

                    //Instanciando a LigacaoRelacionamentoEntidade, esse objeto pede que a Entidade seja passada
                    LigacaoRelacionamentoEntidade ligacao1 = new LigacaoRelacionamentoEntidade(relacionamento, (Entidade) source);
                    ligacao1.addMouseMotionListener(this);
                    ligacao1.addMouseListener(this);

                    //Adicionando a LigacaoRelacionamentoEntidade ao objeto Entidade
                    ((Entidade) source).adicionaLigacao(ligacao1);

                    ligacao1.setVisible(false);
                    //Colocando no palco a ligacao.
                    this.conceitual.getPalco().add(ligacao1);
                    ligacao1.setVisible(true);

                    /*
                     * LIGACAO 2 (SEGUNDA)
                     */

                    //Instanciando a LigacaoRelacionamentoEntidade, esse objeto pede que a Entidade seja passada
                    LigacaoRelacionamentoEntidade ligacao2 = new LigacaoRelacionamentoEntidade(relacionamento, ((Entidade) source));
                    ligacao2.addMouseMotionListener(this);
                    ligacao2.addMouseListener(this);

                    //Adicionando a LigacaoRelacionamentoEntidade ao objeto Entidade
                    ((Entidade) source).adicionaLigacao(ligacao2);

                    ligacao2.setVisible(false);
                    //Colocando no palco a ligacao.
                    this.conceitual.getPalco().add(ligacao2);
                    ligacao2.setVisible(true);

                    relacionamento.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    relacionamento.setSelecionado(true);
                    relacionamento.addMouseMotionListener(this);
                    relacionamento.addMouseListener(this);
                    //Limpa a selecao de componentes
                    Model.stackComponents.reset();
                    //Registra na Swap qual o componente ativo
                    Model.stackComponents.toPush(relacionamento);

                    //Adiciona no palco o componente
                    this.conceitual.getPalco().add(relacionamento);
                    this.conceitual.getPalco().repaint();

                    //Fala para as ligacoes que elas sao 'irmas'
                    ligacao1.setIrma(ligacao2, true);
                    ligacao2.setIrma(ligacao1, false);

                    //Tracando a linha
                    ligacao1.ligacao(new XDimension(relacionamento.getWidth(), relacionamento.getHeight(), relacionamento.getX(), relacionamento.getY()), new XDimension(((Entidade) source).getWidth(), ((Entidade) source).getHeight(), ((Entidade) source).getX(), ((Entidade) source).getY()));
                    //Tracando a linha
                    ligacao2.ligacao(new XDimension(relacionamento.getWidth(), relacionamento.getHeight(), relacionamento.getX(), relacionamento.getY()), new XDimension(((Entidade) source).getWidth(), ((Entidade) source).getHeight(), ((Entidade) source).getX(), ((Entidade) source).getY()));

                    //Atualizando a lista de componentes que estao presentes no palco, a nivel de model
                    ControllerConceitual.this.atualizaListaComponentes();

                    ultimoComponenteClicado = null;
                    Model.opcao = Model.Opcao.CURSOR;
                    //Volta o cursor para uma seta comum
                    Cursor cursor = Cursor.getDefaultCursor();
                    this.conceitual.getPalco().setCursor(cursor);
                }
            }
            ;
            break;
            case LIGACAO: {
                if (source instanceof Entidade || source instanceof Relacionamento || source instanceof Especializacao) {
                    //So pode fazer ligacao clicando sobre entidade e/ou relacionamento   
                    if (ultimoComponenteClicado == null) {
                        //Primeiro componente selecionado
                        if (source instanceof Entidade) {
                            ultimoComponenteClicado = (Entidade) source;
                        } else if (source instanceof Especializacao) {
                            ultimoComponenteClicado = (Especializacao) source;
                        } else {
                            ultimoComponenteClicado = (Relacionamento) source;
                        }
                    } else {
                        //Esse foi o segundo componente clicado, hora de
                        //adicionar de fato a ligacao

                        /*
                         * Se o usuario escolher um relacionamento, so podera ligar em uma entidade.
                         * Se o usuario escolher uma especializacao, so poder ligar em uma entidade.
                         * O usuario pode selecionar duas entidades para criar automaticamente as ligacoes 
                         * e sera adicionado um relacionamento. Se as entidades selecionadas forem a mesma,
                         * significa um auto-relacionamento
                         */

                        //Ligacao com RELACIONAMENTO
                        if (ultimoComponenteClicado instanceof Relacionamento || source instanceof Relacionamento) {
                            //Significa que ao menos um dos selecionados sao entidades, elimina possibilidade de criar
                            //ligacao entre duas especializacoes ou dois relacionamentos
                            if (ultimoComponenteClicado instanceof Entidade || source instanceof Entidade) {
                                //O usuario selecionou uma entidade e um relacionamento, faz UMA ligacao
                                //Verificando qual dos dois eh o relacionamento
                                Relacionamento rel;
                                Entidade ent;
                                if (source instanceof Relacionamento) {
                                    rel = (Relacionamento) source;
                                    ent = (Entidade) ultimoComponenteClicado;
                                } else {
                                    rel = (Relacionamento) ultimoComponenteClicado;
                                    ent = (Entidade) source;
                                }

                                //Verifica se o relacionamento eh do tipo auto-relacionamento.
                                //Se for, nao pode ter ligacao com mais ninguem a nao ser atributos
                                if (rel.isAutoRelacionamento()) {
                                    ultimoComponenteClicado = null;
                                    Model.opcao = Model.Opcao.CURSOR;
                                    this.conceitual.getBtCursor().requestFocus();
                                    //Volta o cursor para uma seta comum
                                    Cursor cursor = Cursor.getDefaultCursor();
                                    this.conceitual.getPalco().setCursor(cursor);
                                    JOptionPane.showMessageDialog(null, Model.lang.getString("autoRelacionamentoTravado"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
                                } else {
                                    rel.adicionaEntidade(ent);
                                    //Instanciando a LigacaoRelacionamentoEntidade, esse objeto pede que a Entidade seja passada
                                    LigacaoRelacionamentoEntidade ligacao = new LigacaoRelacionamentoEntidade(rel, ent);
                                    ligacao.addMouseMotionListener(this);
                                    ligacao.addMouseListener(this);
                                    //Tracando a linha
                                    ligacao.ligacao(new XDimension(rel.getWidth(), rel.getHeight(), rel.getX(), rel.getY()), new XDimension(ent.getWidth(), ent.getHeight(), ent.getX(), ent.getY()));
                                    //Adicionando a LigacaoRelacionamentoEntidade ao objeto Entidade
                                    ent.adicionaLigacao(ligacao);

                                    ligacao.setVisible(false);
                                    //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                                    this.conceitual.getPalco().add(ligacao);
                                    ligacao.setVisible(true);

                                    ultimoComponenteClicado = null;
                                    Model.opcao = Model.Opcao.CURSOR;
                                    this.conceitual.getBtCursor().requestFocus();
                                    //Volta o cursor para uma seta comum
                                    Cursor cursor = Cursor.getDefaultCursor();
                                    this.conceitual.getPalco().setCursor(cursor);
                                }
                            }
                        } //Ligacao com ESPECIALIZACAO
                        else if (ultimoComponenteClicado instanceof Especializacao || source instanceof Especializacao) {
                            //O usuario selecionou uma entidade e uma especializacao, faz UMA ligacao
                            //Verificando qual dos dois eh a especializacao
                            Especializacao esp;
                            Entidade ent;
                            if (source instanceof Especializacao) {
                                esp = (Especializacao) source;
                                ent = (Entidade) ultimoComponenteClicado;
                            } else {
                                esp = (Especializacao) ultimoComponenteClicado;
                                ent = (Entidade) source;
                            }

                            esp.adicionaEspecialista(ent);
                            //Instanciando a LigacaoEntidadeEspecializacao, esse objeto pede que a Entidade seja passada
                            LigacaoEntidadeEspecializacao ligacao = new LigacaoEntidadeEspecializacao(esp, ent);
                            ligacao.addMouseMotionListener(this);
                            ligacao.addMouseListener(this);
                            //Tracando a linha
                            ligacao.ligacao(new XDimension(esp.getWidth(), esp.getHeight(), esp.getX(), esp.getY()), new XDimension(ent.getWidth(), ent.getHeight(), ent.getX(), ent.getY()));
                            //Adicionando a LigacaoEntidadeEspecializacao ao objeto Entidade
                            ent.setLigacaoEspecializacaoBaixoNivel(ligacao);

                            ligacao.setVisible(false);
                            //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                            this.conceitual.getPalco().add(ligacao);
                            ligacao.setVisible(true);

                            ultimoComponenteClicado = null;
                            Model.opcao = Model.Opcao.CURSOR;
                            this.conceitual.getBtCursor().requestFocus();
                            //Volta o cursor para uma seta comum
                            Cursor cursor = Cursor.getDefaultCursor();
                            this.conceitual.getPalco().setCursor(cursor);
                        } //Ligacao entre DUAS ENTIDADES
                        else if (ultimoComponenteClicado instanceof Entidade && source instanceof Entidade) {
                            //Verificando se um e somente um dos componentes eh uma entidade associativa,
                            //pois se for, adiciona a mesma ligacao utilizada para relacionamentos

                            /*
                             * ENTIDADE ASSOCIATIVA
                             */
                            if (ultimoComponenteClicado instanceof EntidadeAssociativa || source instanceof EntidadeAssociativa && !(ultimoComponenteClicado instanceof EntidadeAssociativa && source instanceof EntidadeAssociativa)) {

                                EntidadeAssociativa assoc;
                                Entidade ent;
                                if (source instanceof EntidadeAssociativa) {
                                    assoc = (EntidadeAssociativa) source;
                                    ent = (Entidade) ultimoComponenteClicado;
                                } else {
                                    assoc = (EntidadeAssociativa) ultimoComponenteClicado;
                                    ent = (Entidade) source;
                                }

                                assoc.adicionaEntidade(ent);
                                //Instanciando a LigacaoRelacionamentoEntidade, esse objeto pede que a Entidade seja passada
                                LigacaoRelacionamentoEntidade ligacao = new LigacaoRelacionamentoEntidade(assoc, ent);
                                ligacao.addMouseMotionListener(this);
                                ligacao.addMouseListener(this);
                                //Tracando a linha
                                ligacao.ligacao(new XDimension(assoc.getWidth(), assoc.getHeight(), assoc.getX(), assoc.getY()), new XDimension(ent.getWidth(), ent.getHeight(), ent.getX(), ent.getY()));
                                //Adicionando a LigacaoRelacionamentoEntidade ao objeto Entidade
                                ent.adicionaLigacao(ligacao);

                                ligacao.setVisible(false);
                                //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                                this.conceitual.getPalco().add(ligacao);
                                ligacao.setVisible(true);

                                ultimoComponenteClicado = null;
                                Model.opcao = Model.Opcao.CURSOR;
                                this.conceitual.getBtCursor().requestFocus();
                                //Volta o cursor para uma seta comum
                                Cursor cursor = Cursor.getDefaultCursor();
                                this.conceitual.getPalco().setCursor(cursor);
                            } /**
                             * AUTO RELACIONAMENTO
                             */
                            else if (ultimoComponenteClicado == source) {
                                //Foi selecionado duas vezes a mesma entidade,
                                //auto relacionamento.
                                //Colocar automaticamente um relacionamento e DUAS ligacoes

                                //Processo de criar o componente do tipo Relacionamento e posiciona-lo no palco
                                Relacionamento relacionamento = new Relacionamento(this);
                                //relacionamento.setBounds((int) point.getX(), (int) point.getY(), 100, 50);
                                relacionamento.setBounds(((Entidade) source).getX(), ((Entidade) source).getY() - 80, 100, 50);

                                /*Adicionando no relacionamento a entidade duas vezes,
                                 * o objeto eh o mesmo mas estara referenciado em duas
                                 * posicoes da lista de entidades do relacionamento                                     
                                 */
                                relacionamento.adicionaEntidade((Entidade) ultimoComponenteClicado);
                                relacionamento.adicionaEntidade((Entidade) ultimoComponenteClicado);

                                /*
                                 * LIGACAO 1 (PRIMEIRA)
                                 */

                                //Instanciando a LigacaoRelacionamentoEntidade, esse objeto pede que a Entidade seja passada
                                LigacaoRelacionamentoEntidade ligacao1 = new LigacaoRelacionamentoEntidade(relacionamento, ultimoComponenteClicado);
                                ligacao1.addMouseMotionListener(this);
                                ligacao1.addMouseListener(this);

                                //Adicionando a LigacaoRelacionamentoEntidade ao objeto Entidade
                                ((Entidade) ultimoComponenteClicado).adicionaLigacao(ligacao1);

                                ligacao1.setVisible(false);
                                //Colocando no palco a ligacao.
                                this.conceitual.getPalco().add(ligacao1);
                                ligacao1.setVisible(true);

                                /*
                                 * LIGACAO 2 (SEGUNDA)
                                 */

                                //Instanciando a LigacaoRelacionamentoEntidade, esse objeto pede que a Entidade seja passada
                                LigacaoRelacionamentoEntidade ligacao2 = new LigacaoRelacionamentoEntidade(relacionamento, ultimoComponenteClicado);
                                ligacao2.addMouseMotionListener(this);
                                ligacao2.addMouseListener(this);

                                //Adicionando a LigacaoRelacionamentoEntidade ao objeto Entidade
                                ((Entidade) ultimoComponenteClicado).adicionaLigacao(ligacao2);

                                ligacao2.setVisible(false);
                                //Colocando no palco a ligacao.
                                this.conceitual.getPalco().add(ligacao2);
                                ligacao2.setVisible(true);

                                relacionamento.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                relacionamento.setSelecionado(true);
                                relacionamento.addMouseMotionListener(this);
                                relacionamento.addMouseListener(this);
                                //Limpa a selecao de componentes
                                Model.stackComponents.reset();
                                //Registra na Swap qual o componente ativo
                                Model.stackComponents.toPush(relacionamento);

                                //Adiciona no palco o componente
                                this.conceitual.getPalco().add(relacionamento);
                                this.conceitual.getPalco().repaint();

                                //Fala para as ligacoes que elas sao 'irmas'
                                ligacao1.setIrma(ligacao2, true);
                                ligacao2.setIrma(ligacao1, false);

                                //Tracando a linha
                                ligacao1.ligacao(new XDimension(relacionamento.getWidth(), relacionamento.getHeight(), relacionamento.getX(), relacionamento.getY()), new XDimension(ultimoComponenteClicado.getWidth(), ultimoComponenteClicado.getHeight(), ultimoComponenteClicado.getX(), ultimoComponenteClicado.getY()));
                                //Tracando a linha
                                ligacao2.ligacao(new XDimension(relacionamento.getWidth(), relacionamento.getHeight(), relacionamento.getX(), relacionamento.getY()), new XDimension(ultimoComponenteClicado.getWidth(), ultimoComponenteClicado.getHeight(), ultimoComponenteClicado.getX(), ultimoComponenteClicado.getY()));

                                //Atualizando a lista de componentes que estao presentes no palco, a nivel de model
                                ControllerConceitual.this.atualizaListaComponentes();

                                ultimoComponenteClicado = null;
                                Model.opcao = Model.Opcao.CURSOR;
                                //Volta o cursor para uma seta comum
                                Cursor cursor = Cursor.getDefaultCursor();
                                this.conceitual.getPalco().setCursor(cursor);
                            } else {
                                //O usuario selecionou duas entidades diferentes
                                //Cria automaticamente o relacionamento e faz
                                //as DUAS ligacoes

                                //Processo de criar o componente do tipo Relacionamento e posiciona-lo no palco
                                Relacionamento relacionamento = new Relacionamento(this);
                                relacionamento.setBounds((int) point.getX(), (int) point.getY(), 100, 50);

                                /*Adicionando no relacionamento a entidade duas vezes,
                                 * o objeto eh o mesmo mas estara referenciado em duas
                                 * posicoes da lista de entidades do relacionamento                                     
                                 */
                                relacionamento.adicionaEntidade((Entidade) ultimoComponenteClicado);
                                relacionamento.adicionaEntidade((Entidade) source);

                                /*
                                 * LIGACAO 1 (PRIMEIRA)
                                 */

                                //Instanciando a LigacaoRelacionamentoEntidade, esse objeto pede que a Entidade seja passada
                                LigacaoRelacionamentoEntidade ligacao1 = new LigacaoRelacionamentoEntidade(relacionamento, ultimoComponenteClicado);
                                ligacao1.addMouseMotionListener(this);
                                ligacao1.addMouseListener(this);
                                //Tracando a linha
                                ligacao1.ligacao(new XDimension(relacionamento.getWidth(), relacionamento.getHeight(), relacionamento.getX(), relacionamento.getY()), new XDimension(ultimoComponenteClicado.getWidth(), ultimoComponenteClicado.getHeight(), ultimoComponenteClicado.getX(), ultimoComponenteClicado.getY()));
                                //Adicionando a LigacaoRelacionamentoEntidade ao objeto Entidade
                                ((Entidade) ultimoComponenteClicado).adicionaLigacao(ligacao1);

                                ligacao1.setVisible(false);
                                //Colocando no palco a ligacao.
                                this.conceitual.getPalco().add(ligacao1);
                                ligacao1.setVisible(true);

                                /*
                                 * LIGACAO 2 (SEGUNDA)
                                 */

                                //Instanciando a LigacaoRelacionamentoEntidade, esse objeto pede que a Entidade seja passada
                                LigacaoRelacionamentoEntidade ligacao2 = new LigacaoRelacionamentoEntidade(relacionamento, (Entidade) source);
                                ligacao2.addMouseMotionListener(this);
                                ligacao2.addMouseListener(this);
                                //Tracando a linha
                                ligacao2.ligacao(new XDimension(relacionamento.getWidth(), relacionamento.getHeight(), relacionamento.getX(), relacionamento.getY()), new XDimension(((Entidade) source).getWidth(), ((Entidade) source).getHeight(), ((Entidade) source).getX(), ((Entidade) source).getY()));
                                //Adicionando a LigacaoRelacionamentoEntidade ao objeto Entidade
                                ((Entidade) source).adicionaLigacao(ligacao2);

                                ligacao2.setVisible(false);
                                //Colocando no palco a ligacao.
                                this.conceitual.getPalco().add(ligacao2);
                                ligacao2.setVisible(true);

                                relacionamento.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                                relacionamento.setSelecionado(true);
                                relacionamento.addMouseMotionListener(this);
                                relacionamento.addMouseListener(this);
                                //Limpa a selecao de componentes
                                Model.stackComponents.reset();
                                //Registra na Swap qual o componente ativo
                                Model.stackComponents.toPush(relacionamento);

                                //Adiciona no palco o componente
                                this.conceitual.getPalco().add(relacionamento);
                                this.conceitual.getPalco().repaint();

                                //Atualizando a lista de componentes que estao presentes no palco, a nivel de model
                                ControllerConceitual.this.atualizaListaComponentes();

                                ultimoComponenteClicado = null;
                                Model.opcao = Model.Opcao.CURSOR;
                                this.conceitual.getBtCursor().requestFocus();
                                //Volta o cursor para uma seta comum
                                Cursor cursor = Cursor.getDefaultCursor();
                                this.conceitual.getPalco().setCursor(cursor);
                            }
                        }
                    }
                }
            }
            break;
            case ESPECIALIZACAO_PARCIAL: {
                if (source instanceof Entidade) {
                    //So pode haver uma especializacao por entidade
                    if (((Entidade) source).getLigacaoEspecializacao() == null) {
                        //So pode fazer ligacao clicando sobre entidade
                        componente = new EspecializacaoParcial(this, (Entidade) source);
                        componente.setBounds((((Entidade) source).getX() + ((Entidade) source).getWidth() / 2) - 20, ((Entidade) source).getY() + ((Entidade) source).getHeight() + 20, 40, 30);

                        Especializacao pai = (Especializacao) componente;
                        Entidade filho = (Entidade) source;

                        LigacaoAltoNivelEspecializacao ligacao = new LigacaoAltoNivelEspecializacao((Especializacao) componente, (Entidade) source);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(pai.getWidth(), pai.getHeight(), pai.getX(), pai.getY()), new XDimension(filho.getWidth(), filho.getHeight(), filho.getX(), filho.getY()));
                        //Adicionando a setLigacaoEspecializacao ao objeto Entidade
                        filho.setLigacaoEspecializacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    } else {
                        Model.opcao = Model.Opcao.CURSOR;
                        //Volta o cursor para uma seta comum
                        Cursor cursor = Cursor.getDefaultCursor();
                        this.conceitual.getPalco().setCursor(cursor);
                        this.conceitual.getBtCursor().requestFocus();
                        JOptionPane.showMessageDialog(null, Model.lang.getString("entidadeJaEspecializada"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    Model.opcao = Model.Opcao.CURSOR;
                    //Volta o cursor para uma seta comum
                    Cursor cursor = Cursor.getDefaultCursor();
                    this.conceitual.getPalco().setCursor(cursor);
                    this.conceitual.getBtCursor().requestFocus();
                    JOptionPane.showMessageDialog(null, Model.lang.getString("especializacaoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
                }
            }
            break;
            case ESPECIALIZACAO_TOTAL: {
                if (source instanceof Entidade) {
                    //So pode haver uma especializacao por entidade
                    if (((Entidade) source).getLigacaoEspecializacao() == null) {
                        //So pode fazer ligacao clicando sobre entidade
                        componente = new EspecializacaoTotal(this, (Entidade) source);
                        componente.setBounds((((Entidade) source).getX() + ((Entidade) source).getWidth() / 2) - 20, ((Entidade) source).getY() + ((Entidade) source).getHeight() + 20, 40, 30);

                        Especializacao pai = (Especializacao) componente;
                        Entidade filho = (Entidade) source;

                        LigacaoAltoNivelEspecializacao ligacao = new LigacaoAltoNivelEspecializacao((Especializacao) componente, (Entidade) source);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(pai.getWidth(), pai.getHeight(), pai.getX(), pai.getY()), new XDimension(filho.getWidth(), filho.getHeight(), filho.getX(), filho.getY()));
                        //Adicionando a setLigacaoEspecializacao ao objeto Entidade
                        filho.setLigacaoEspecializacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    } else {
                        Model.opcao = Model.Opcao.CURSOR;
                        //Volta o cursor para uma seta comum
                        Cursor cursor = Cursor.getDefaultCursor();
                        this.conceitual.getPalco().setCursor(cursor);
                        this.conceitual.getBtCursor().requestFocus();
                        JOptionPane.showMessageDialog(null, Model.lang.getString("entidadeJaEspecializada"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    Model.opcao = Model.Opcao.CURSOR;
                    //Volta o cursor para uma seta comum
                    Cursor cursor = Cursor.getDefaultCursor();
                    this.conceitual.getPalco().setCursor(cursor);
                    this.conceitual.getBtCursor().requestFocus();
                    JOptionPane.showMessageDialog(null, Model.lang.getString("especializacaoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
                }
            }
            break;
            case ATRIBUTO_COMUM: {//Peralah.... so podera adicionar em cima de:, 
                //Entidade Associativa, Entidade Fraca, Entidade Comum, Relacionamento e 
                //Atributo Composto
                if (source.getClass().getSuperclass() != Entidade.class && source.getClass() != Relacionamento.class && source != AtributoComposto.class) {
                    //Usuario insistente...
                    JOptionPane.showMessageDialog(null, Model.lang.getString("atributoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
                } else {
                    //Obtendo o objeto do componente pai, eh o componente que foi clicado para receber um atributo.
                    //Pode ser uma entidade comum, associativa, fraca, atributo composto ou relacionamento.
                    Componente c = (Componente) source;
                    //Instancia o atributo. O atributo precisa receber um objeto LigacaoAtributo, porem ainda nao foi instanciado,
                    //sera informado via 'set' logo apos a instanciacao da LigacaoAtributo.
                    componente = new AtributoComum(c.getX() + c.getWidth() + 50, c.getY(), this.conceitual.getPalco().getGraphics(), (Componente) source, null, this);

                    //Adicionando no componente pai o atributo que ele ganhou.
                    if (c.getClass().getSuperclass() == Entidade.class) {
                        ((Entidade) c).adicionaAtributo((Atributo) componente);
                        //Instanciando a LigacaoAtributo, esse objeto pede que o Atributo seja passado
                        LigacaoAtributo ligacao = new LigacaoAtributo(c, (Atributo) componente);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(c.getWidth(), c.getHeight(), c.getX(), c.getY()), new XDimension(componente.getWidth(), componente.getHeight(), componente.getX(), componente.getY()));
                        //Adicionando a LigacaoAtributo ao objeto Atributo
                        ((Atributo) componente).setLigacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    } else if (c.getClass() == AtributoComposto.class) {
                    } else if (c.getClass() == Relacionamento.class) {
                        ((Relacionamento) c).adicionaAtributo((Atributo) componente);
                        //Instanciando a LigacaoRelacionamentoAtributo, esse objeto pede que o Atributo seja passado
                        LigacaoRelacionamentoAtributo ligacao = new LigacaoRelacionamentoAtributo(c, (Atributo) componente);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(c.getWidth(), c.getHeight(), c.getX(), c.getY()), new XDimension(componente.getWidth(), componente.getHeight(), componente.getX(), componente.getY()));
                        //Adicionando a LigacaoRelacionamentoAtributo ao objeto Atributo
                        ((Atributo) componente).setLigacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    }

                }
            }
            break;
            case ATRIBUTO_CHAVE: {
                //Peralah.... so podera adicionar em cima de:, 
                //Entidade Associativa, Entidade Fraca, Entidade Comum, Relacionamento e 
                //Atributo Composto
                if (source.getClass().getSuperclass() != Entidade.class && source.getClass() != Relacionamento.class && source != AtributoComposto.class) {
                    //Usuario insistente...
                    JOptionPane.showMessageDialog(null, Model.lang.getString("atributoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
                } else {
                    /**
                     * Adicionando a LigacaoAtributo
                     */
                    //Obtendo o objeto do componente pai, eh o componente que foi clicado para receber um atributo.
                    //Pode ser uma entidade comum, associativa, fraca, atributo composto ou relacionamento.
                    Componente c = (Componente) source;
                    //Instancia o atributo. O atributo precisa receber um objeto LigacaoAtributo, porem ainda nao foi instanciado,
                    //sera informado via 'set' logo apos a instanciacao da LigacaoAtributo.
                    componente = new AtributoChave(c.getX() + c.getWidth() + 50, c.getY(), this.conceitual.getPalco().getGraphics(), (Componente) source, null, this);

                    //Adicionando no componente pai o atributo que ele ganhou.
                    if (c.getClass().getSuperclass() == Entidade.class) {
                        ((Entidade) c).adicionaAtributo((Atributo) componente);
                        //Instanciando a LigacaoAtributo, esse objeto pede que o Atributo seja passado
                        LigacaoAtributo ligacao = new LigacaoAtributo(c, (Atributo) componente);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(c.getWidth(), c.getHeight(), c.getX(), c.getY()), new XDimension(componente.getWidth(), componente.getHeight(), componente.getX(), componente.getY()));
                        //Adicionando a LigacaoAtributo ao objeto Atributo
                        ((Atributo) componente).setLigacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    } else if (c.getClass() == Relacionamento.class) {
                        ((Relacionamento) c).adicionaAtributo((Atributo) componente);
                        //Instanciando a LigacaoRelacionamentoAtributo, esse objeto pede que o Atributo seja passado
                        LigacaoRelacionamentoAtributo ligacao = new LigacaoRelacionamentoAtributo(c, (Atributo) componente);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(c.getWidth(), c.getHeight(), c.getX(), c.getY()), new XDimension(componente.getWidth(), componente.getHeight(), componente.getX(), componente.getY()));
                        //Adicionando a LigacaoRelacionamentoAtributo ao objeto Atributo
                        ((Atributo) componente).setLigacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    }
                }
            }
            break;
            case ATRIBUTO_DERIVADO: {
                //Peralah.... so podera adicionar em cima de:, 
                //Entidade Associativa, Entidade Fraca, Entidade Comum, Relacionamento e 
                //Atributo Composto
                if (source.getClass().getSuperclass() != Entidade.class && source.getClass() != Relacionamento.class && source != AtributoComposto.class) {
                    //Usuario insistente...
                    JOptionPane.showMessageDialog(null, Model.lang.getString("atributoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
                } else {
                    /**
                     * Adicionando a LigacaoAtributo
                     */
                    //Obtendo o objeto do componente pai, eh o componente que foi clicado para receber um atributo.
                    //Pode ser uma entidade comum, associativa, fraca, atributo composto ou relacionamento.
                    Componente c = (Componente) source;
                    //Instancia o atributo. O atributo precisa receber um objeto LigacaoAtributo, porem ainda nao foi instanciado,
                    //sera informado via 'set' logo apos a instanciacao da LigacaoAtributo.
                    componente = new AtributoDerivado(c.getX() + c.getWidth() + 50, c.getY(), this.conceitual.getPalco().getGraphics(), (Componente) source, null, this);

                    //Adicionando no componente pai o atributo que ele ganhou.
                    if (c.getClass().getSuperclass() == Entidade.class) {
                        ((Entidade) c).adicionaAtributo((Atributo) componente);
                        //Instanciando a LigacaoAtributo, esse objeto pede que o Atributo seja passado
                        LigacaoAtributo ligacao = new LigacaoAtributo(c, (Atributo) componente);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(c.getWidth(), c.getHeight(), c.getX(), c.getY()), new XDimension(componente.getWidth(), componente.getHeight(), componente.getX(), componente.getY()));
                        //Adicionando a LigacaoAtributo ao objeto Atributo
                        ((Atributo) componente).setLigacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    } else if (c.getClass() == Relacionamento.class) {
                        ((Relacionamento) c).adicionaAtributo((Atributo) componente);
                        //Instanciando a LigacaoRelacionamentoAtributo, esse objeto pede que o Atributo seja passado
                        LigacaoRelacionamentoAtributo ligacao = new LigacaoRelacionamentoAtributo(c, (Atributo) componente);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(c.getWidth(), c.getHeight(), c.getX(), c.getY()), new XDimension(componente.getWidth(), componente.getHeight(), componente.getX(), componente.getY()));
                        //Adicionando a LigacaoRelacionamentoAtributo ao objeto Atributo
                        ((Atributo) componente).setLigacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    }
                }
            }
            break;
            case ATRIBUTO_COMPOSTO: {
                //Peralah.... so podera adicionar em cima de:, 
                //Entidade Associativa, Entidade Fraca, Entidade Comum, Relacionamento e 
                //Atributo Composto
                if (source.getClass().getSuperclass() != Entidade.class && source.getClass() != Relacionamento.class && source != AtributoComposto.class) {
                    //Usuario insistente...
                    JOptionPane.showMessageDialog(null, Model.lang.getString("atributoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
                } else {
                    /**
                     * Adicionando a LigacaoAtributo
                     */
                    //Obtendo o objeto do componente pai, eh o componente que foi clicado para receber um atributo.
                    //Pode ser uma entidade comum, associativa, fraca, atributo composto ou relacionamento.
                    Componente c = (Componente) source;
                    //Instancia o atributo. O atributo precisa receber um objeto LigacaoAtributo, porem ainda nao foi instanciado,
                    //sera informado via 'set' logo apos a instanciacao da LigacaoAtributo.
                    componente = new AtributoComposto(c.getX() + c.getWidth() + 50, c.getY(), this.conceitual.getPalco().getGraphics(), (Componente) source, null, this);

                    //Adicionando no componente pai o atributo que ele ganhou.
                    if (c.getClass().getSuperclass() == Entidade.class) {
                        ((Entidade) c).adicionaAtributo((Atributo) componente);
                        //Instanciando a LigacaoAtributo, esse objeto pede que o Atributo seja passado
                        LigacaoAtributo ligacao = new LigacaoAtributo(c, (Atributo) componente);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(c.getWidth(), c.getHeight(), c.getX(), c.getY()), new XDimension(componente.getWidth(), componente.getHeight(), componente.getX(), componente.getY()));
                        //Adicionando a LigacaoAtributo ao objeto Atributo
                        ((Atributo) componente).setLigacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    } else if (c.getClass() == Relacionamento.class) {
                        ((Relacionamento) c).adicionaAtributo((Atributo) componente);
                        //Instanciando a LigacaoRelacionamentoAtributo, esse objeto pede que o Atributo seja passado
                        LigacaoRelacionamentoAtributo ligacao = new LigacaoRelacionamentoAtributo(c, (Atributo) componente);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(c.getWidth(), c.getHeight(), c.getX(), c.getY()), new XDimension(componente.getWidth(), componente.getHeight(), componente.getX(), componente.getY()));
                        //Adicionando a LigacaoRelacionamentoAtributo ao objeto Atributo
                        ((Atributo) componente).setLigacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    }
                }
            }
            break;
            case ATRIBUTO_MULTIVALORADO: {
                //Peralah.... so podera adicionar em cima de:, 
                //Entidade Associativa, Entidade Fraca, Entidade Comum, Relacionamento e 
                //Atributo Composto
                if (source.getClass().getSuperclass() != Entidade.class && source.getClass() != Relacionamento.class && source != AtributoComposto.class) {
                    //Usuario insistente...
                    JOptionPane.showMessageDialog(null, Model.lang.getString("atributoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
                } else {
                    /**
                     * Adicionando a LigacaoAtributo
                     */
                    //Obtendo o objeto do componente pai, eh o componente que foi clicado para receber um atributo.
                    //Pode ser uma entidade comum, associativa, fraca, atributo composto ou relacionamento.
                    Componente c = (Componente) source;
                    //Instancia o atributo. O atributo precisa receber um objeto LigacaoAtributo, porem ainda nao foi instanciado,
                    //sera informado via 'set' logo apos a instanciacao da LigacaoAtributo.
                    componente = new AtributoMultivalorado(c.getX() + c.getWidth() + 50, c.getY(), this.conceitual.getPalco().getGraphics(), (Componente) source, null, this);

                    //Adicionando no componente pai o atributo que ele ganhou.
                    if (c.getClass().getSuperclass() == Entidade.class) {
                        ((Entidade) c).adicionaAtributo((Atributo) componente);
                        //Instanciando a LigacaoAtributo, esse objeto pede que o Atributo seja passado
                        LigacaoAtributo ligacao = new LigacaoAtributo(c, (Atributo) componente);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(c.getWidth(), c.getHeight(), c.getX(), c.getY()), new XDimension(componente.getWidth(), componente.getHeight(), componente.getX(), componente.getY()));
                        //Adicionando a LigacaoAtributo ao objeto Atributo
                        ((Atributo) componente).setLigacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    } else if (c.getClass() == Relacionamento.class) {
                        ((Relacionamento) c).adicionaAtributo((Atributo) componente);
                        //Instanciando a LigacaoRelacionamentoAtributo, esse objeto pede que o Atributo seja passado
                        LigacaoRelacionamentoAtributo ligacao = new LigacaoRelacionamentoAtributo(c, (Atributo) componente);
                        ligacao.addMouseMotionListener(this);
                        ligacao.addMouseListener(this);
                        //Tracando a linha
                        ligacao.ligacao(new XDimension(c.getWidth(), c.getHeight(), c.getX(), c.getY()), new XDimension(componente.getWidth(), componente.getHeight(), componente.getX(), componente.getY()));
                        //Adicionando a LigacaoRelacionamentoAtributo ao objeto Atributo
                        ((Atributo) componente).setLigacao(ligacao);
                        //Colocando no palco a ligacao. O objeto sera colocado um pouco abaixo.
                        this.conceitual.getPalco().add(ligacao);
                    }
                }
            }
            break;
        }
        if (componente != null) {

            // *****
            // Insere um componente
            // *****

            componente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            componente.setSelecionado(true);
            componente.addMouseMotionListener(this);
            componente.addMouseListener(this);
            Model.stackComponents.reset();
            //Registra na Swap qual o componente ativo
            Model.stackComponents.toPush(componente);
            //Volta para o ponto de cursor (primeiro componente da barra de ferramentas), ou seja,
            //aguardando o usuario interagir novamente e escolher outro componente
            Model.opcao = Model.Opcao.CURSOR;
            //Adiciona no palco o componente
            this.conceitual.getPalco().add(componente);
            this.conceitual.getPalco().repaint();
            //Volta o cursor para uma seta comum
            Cursor cursor = Cursor.getDefaultCursor();
            this.conceitual.getPalco().setCursor(cursor);
            this.conceitual.getBtCursor().requestFocus();
            //Atualizando a lista de componentes que estao presentes no palco, a nivel de model
            ControllerConceitual.this.atualizaListaComponentes();
        } else if ((clickCount == 2) && (button == MouseEvent.BUTTON1)) {
            //Duplo clique em cima de um componente.
            componente = (Componente) source;
            componente.duploClique();
        }
    }

    private void clicouSobrePalco(Point point) {

        //Se clicou no palco, nenhum componente deve continuar no estado 'selecionado'
        Model.stackComponents.reset();
        Componente componente = null;

        switch (Model.opcao) {
            case ENTIDADE: {
                //Processo de criar o componente do tipo Entidade e posiciona-lo no palco
                componente = new EntidadeComum(this);
                componente.setBounds((int) point.getX(), (int) point.getY(), 100, 50);
            }
            break;
            case ENTIDADE_FRACA: {
                //Processo de criar o componente do tipo Entidade Fraca e posiciona-lo no palco
                componente = new EntidadeFraca(this);
                componente.setBounds((int) point.getX(), (int) point.getY(), 100, 50);
            }
            break;
            case RELACIONAMENTO: {
                //Processo de criar o componente do tipo Relacionamento e posiciona-lo no palco
                componente = new Relacionamento(this);
                componente.setBounds((int) point.getX(), (int) point.getY(), 100, 50);
            }
            break;
            case ENTIDADE_ASSOCIATIVA: {
                //Processo de criar o componente do tipo Entidade Associativa e posiciona-lo no palco
                componente = new EntidadeAssociativa(this);
                componente.setBounds((int) point.getX(), (int) point.getY(), 100, 50);
            }
            break;
            case ESPECIALIZACAO_PARCIAL: {
                JOptionPane.showMessageDialog(null, Model.lang.getString("especializacaoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
            }
            break;
            case ESPECIALIZACAO_TOTAL: {
                JOptionPane.showMessageDialog(null, Model.lang.getString("especializacaoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
            }
            break;
            case TEXTO: {
                //Processo de criar o componente do tipo Entidade Associativa e posiciona-lo no palco
                componente = new Texto(this);
                componente.setBounds((int) point.getX(), (int) point.getY(), 100, 50);
            }
            break;
            case LIGACAO: {
                //componente = new LigacaoAtributo(componente, null)
            }
            break;
            case ATRIBUTO_COMUM: {
                JOptionPane.showMessageDialog(null, Model.lang.getString("atributoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
            }
            break;
            case ATRIBUTO_CHAVE: {
                JOptionPane.showMessageDialog(null, Model.lang.getString("atributoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
            }
            break;
            case ATRIBUTO_DERIVADO: {
                JOptionPane.showMessageDialog(null, Model.lang.getString("atributoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
            }
            break;
            case ATRIBUTO_COMPOSTO: {
                JOptionPane.showMessageDialog(null, Model.lang.getString("atributoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
            }
            break;
            case ATRIBUTO_MULTIVALORADO: {
                JOptionPane.showMessageDialog(null, Model.lang.getString("atributoClicarEntidade"), Model.lang.getString("tituloAtencao"), JOptionPane.WARNING_MESSAGE);
            }
            break;
        }

        //Acima foi tentado a instanciacao do componente, seja qual for sua especializacao
        //Abaixo eh verificado se a instanciacao ocorreu. Se ocorreu, adiciona os listeners 
        //e exibe-o
        if (componente != null) {
            componente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            componente.setSelecionado(true);
            componente.addMouseMotionListener(this);
            componente.addMouseListener(this);
            //Registra na Swap qual o componente ativo
            Model.stackComponents.toPush(componente);

            //Adiciona no palco o componente
            this.conceitual.getPalco().add(componente);
            this.conceitual.getPalco().repaint();

            //Atualizando a lista de componentes que estao presentes no palco, a nivel de model
            ControllerConceitual.this.atualizaListaComponentes();
        }
        //Volta o cursor para uma seta comum
        Cursor cursor = Cursor.getDefaultCursor();
        this.conceitual.getPalco().setCursor(cursor);

        //Volta para o ponto de cursor (primeiro componente da barra de ferramentas), ou seja,
        //aguardando o usuario interagir novamente e escolher outro componente
        Model.opcao = Model.Opcao.CURSOR;

        //Deixo selecionado o botao do cursor
        this.conceitual.getBtCursor().requestFocus();
    }

    @Override
    //Afundou o dedo no botao do mouse mas ainda nao soltou o.
    public void mousePressed(MouseEvent e) {

        //Manda copiar o modelo
        Model.copiarModelo();

        //Clicou sobre um atributo
        if (e.getSource().getClass().getSuperclass() == Atributo.class) {
            //Clicou sobre o atributo, ira aparecer aqueles quadradinhos pretos 
            //mas nao permite resizing
            //Se o atributo ja estiver selecionado, nao faz
            //nada, nem retire a selecao dos outros para permitir que o usuario possa arrastar
            //um grupo de componentes.
            Componente c = ((Componente) e.getSource());
            if (!c.isSelecionado()) {
                Model.stackComponents.reset();
                Model.stackComponents.toPush(c);
            }

            Model.dragStarded = true;
            if (c.getMousePosition() != null) {
                Model.y = (int) c.getMousePosition().getY();
                Model.x = (int) c.getMousePosition().getX();
            }

        } //Clicou sobre uma ligacao
        else if (e.getSource() instanceof Ligacao) {

            //Verificar se ha algum componente por traz que o usuario
            //deseja movimenta-lo
            Componente c = verificarComponentePosicao(this.conceitual.getPalco().getMousePosition());
            if (c != null) {
                Model.componenteAbaixoDaLigacao = c;
                //Clicou sobre o componente, ira aparecer aqueles quadradinhos pretos para
                //redimensionar o componente. Se o componente ja estiver selecionado, nao faz
                //nada, nem retire a selecao dos outros para permitir que o usuario possa arrastar
                //um grupo de componentes.
                if (!c.isSelecionado()) {
                    Model.stackComponents.reset();
                    Model.stackComponents.toPush(c);
                }

                //Se clicou nos 'quadradinhos pretos', mantem essa variavel como true para sinalizar
                //ao drag que nao se trata de um reposicionamento e sim um redimensionamento

                Point pComponente = new Point((int) this.conceitual.getPalco().getMousePosition().getX() - c.getX(), (int) this.conceitual.getPalco().getMousePosition().getY() - c.getY());

                if (pComponente.getX() < 8 && pComponente.getY() < 8) {
                    //Ponto superior esquerdo.
                    Model.resizeStarted = true;
                    Model.direcao = Model.Direcao.SUPERIOR_ESQUERDA;
                    Model.y = (c.getY() + c.getHeight());
                    Model.x = (c.getX() + c.getWidth());
                } else if (pComponente.getX() < 8 && pComponente.getY() > c.getHeight() - 8) {
                    //Ponto inferior esquerdo                    
                    Model.resizeStarted = true;
                    Model.direcao = Model.Direcao.INFERIOR_ESQUERDA;
                    Model.y = c.getY() + c.getHeight();
                    Model.x = (c.getX() + c.getWidth());
                } else if (pComponente.getX() > c.getWidth() - 8 && pComponente.getY() < 8) {
                    //Ponto superior direito
                    Model.resizeStarted = true;
                    Model.direcao = Model.Direcao.SUPERIOR_DIREITA;
                    Model.y = (c.getY() + c.getHeight());
                    Model.x = (c.getX() + c.getWidth());
                } else if (pComponente.getX() > c.getWidth() - 8 && pComponente.getY() > c.getHeight() - 8) {
                    //Ponto inferior direito     
                    Model.resizeStarted = true;
                    Model.direcao = Model.Direcao.INFERIOR_DIREITA;
                    Model.y = c.getY() + c.getHeight();
                    Model.x = (c.getX() + c.getWidth());
                } else {
                    //Nao, eh reposicionamento mesmo
                    Model.dragStarded = true;
                    Model.y = (int) pComponente.getY();
                    Model.x = (int) pComponente.getX();
                }

                //Se iniciou um processo de resize, deixa somente esse componente selecionado
                if (Model.resizeStarted) {
                    Model.stackComponents.reset();
                    Model.stackComponents.toPush(c);
                }
            } //Nao tinha ninguem, simplesmente permita criar as linhas de selecao
            else {
                Model.stackComponents.reset();
                Model.selectStarted = true;

                Ligacao ligacao = (Ligacao) e.getSource();

                Model.y = (int) ligacao.getMousePosition().getY() + ligacao.getY();
                Model.x = (int) ligacao.getMousePosition().getX() + ligacao.getX();
            }

        } //Clicou sobre o palco
        else if (e.getSource() == this.conceitual.getPalco()) {
            //Pressionou em cima do palco, permitira colocar um glass por cima no
            //drag para permitir as linhas de selecao. Desfaz qualquer selecao anterior
            Model.stackComponents.reset();
            Model.selectStarted = true;
            if (this.conceitual.getPalco().getMousePosition() != null) {
                Model.y = (int) this.conceitual.getPalco().getMousePosition().getY();
                Model.x = (int) this.conceitual.getPalco().getMousePosition().getX();
            }
        } //Clicou sobre o componente
        else if (e.getSource().getClass().getSuperclass() == Componente.class || e.getSource().getClass().getSuperclass().getSuperclass() == Componente.class) {
            //Clicou sobre o componente, ira aparecer aqueles quadradinhos pretos para
            //redimensionar o componente. Se o componente ja estiver selecionado, nao faz
            //nada, nem retire a selecao dos outros para permitir que o usuario possa arrastar
            //um grupo de componentes.
            Componente c = ((Componente) e.getSource());
            if (!c.isSelecionado()) {
                Model.stackComponents.reset();
                Model.stackComponents.toPush(c);
            }

            //Se clicou nos 'quadradinhos pretos', mantem essa variavel como true para sinalizar
            //ao drag que nao se trata de um reposicionamento e sim um redimensionamento
            if (e.getX() < 8 && e.getY() < 8) {
                //Ponto superior esquerdo.
                Model.resizeStarted = true;
                Model.direcao = Model.Direcao.SUPERIOR_ESQUERDA;
                Model.y = (c.getY() + c.getHeight());
                Model.x = (c.getX() + c.getWidth());
            } else if (e.getX() < 8 && e.getY() > c.getHeight() - 8) {
                //Ponto inferior esquerdo                    
                Model.resizeStarted = true;
                Model.direcao = Model.Direcao.INFERIOR_ESQUERDA;
                Model.y = c.getY() + c.getHeight();
                Model.x = (c.getX() + c.getWidth());
            } else if (e.getX() > c.getWidth() - 8 && e.getY() < 8) {
                //Ponto superior direito
                Model.resizeStarted = true;
                Model.direcao = Model.Direcao.SUPERIOR_DIREITA;
                Model.y = (c.getY() + c.getHeight());
                Model.x = (c.getX() + c.getWidth());
            } else if (e.getX() > c.getWidth() - 8 && e.getY() > c.getHeight() - 8) {
                //Ponto inferior direito     
                Model.resizeStarted = true;
                Model.direcao = Model.Direcao.INFERIOR_DIREITA;
                Model.y = c.getY() + c.getHeight();
                Model.x = (c.getX() + c.getWidth());
            } else {
                //Nao, eh reposicionamento mesmo
                Model.dragStarded = true;
                Model.y = (int) e.getY();
                Model.x = (int) e.getX();
            }

            //Se iniciou um processo de resize, deixa somente esse componente selecionado
            if (Model.resizeStarted) {
                Model.stackComponents.reset();
                Model.stackComponents.toPush(c);
            }
        }

        /*
         * Tratatamento do painel de propriedades. Essa atividade eh delegada para
         * a controller do painel de propriedades. Essa controller (ControllerConceitual)
         * mantem interacao com a ControllerPropriedades
         */
        this.conceitual.getPropCtr().clicouSobreComponente(e);

    }

    @Override
    //Largou o dedao do mouse, o botao (do mouse) voltou a posicao normal
    public void mouseReleased(MouseEvent e) {
        this.paGlassSelecao.setVisible(false);
        Model.componenteAbaixoDaLigacao = null;

        //Bah... se o cara redimensionou e deixou o componente com a largura ou altura invalida (por exemplo, numero negativo)
        //Concerta essa caca, coloca um tamanho default aeh.
        Componente c = Model.stackComponents.getLast();
        if (c != null) {
            if (c.getWidth() < 10) {
                c.setBounds(c.getX(), c.getY(), 100, c.getHeight());
            }
            if (c.getHeight() < 10) {
                c.setBounds(c.getX(), c.getY(), c.getWidth(), 50);
            }
        }

        if (Model.dragStarded && Model.dragPanelCalculated) {
            //Estava arrastando componente(s). Tirar todas do painel de agrupamento
            for (Componente co : Model.stackComponents.getAll()) {
                co.setBounds(co.getX() + this.paDrag.getX(), co.getY() + this.paDrag.getY(), co.getWidth(), co.getHeight());
                this.conceitual.getPalco().add(co);
            }
            paDrag.setVisible(false);
        }
        //Toda vez que um ou mais componentes forem redimensionados ou arrastados,
        //o model antigo deve ser empilhado para permitir o redo.
        if (Model.resizeStarted || Model.dragPanelCalculated) {
            Model.modelEditado();
        }
        Model.dragStarded = false;
        Model.dragPanelCalculated = false;
        Model.resizeStarted = false;
        Model.selectStarted = false;

        //Se o usuario acabou de soltar o botao do mouse e ha um e apenas
        //um componente selecionado, pode ter sido arrastado. Atualize suas
        //propriedades na barra de propriedades.
        if (Model.stackComponents.getAll().length == 1) {
            /*
             * Tratatamento do painel de propriedades. Essa atividade eh delegada para
             * a controller do painel de propriedades. Essa controller (ControllerConceitual)
             * mantem interacao com a ControllerPropriedades
             */
            this.conceitual.getPropCtr().clicouSobreComponente(e);
        }
        else{
            this.conceitual.getPropCtr().clicouSobreComponente(null);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    //Clicou, segurou e arrastou...
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() instanceof Componente || Model.componenteAbaixoDaLigacao != null) {
            try {
                Componente c;
                if (Model.componenteAbaixoDaLigacao != null) {
                    c = Model.componenteAbaixoDaLigacao;
                } else {
                    c = (Componente) e.getSource();
                }
                //O mouse pressed jah sinalizou se o usuario pressionou em cima dos 'quadradinhos pretos'. Se sim, nao sera feito o 'drag and drop' e sim um 'resizing'
                if (Model.resizeStarted) {
                    switch (Model.direcao) {
                        case SUPERIOR_ESQUERDA: {
                            //Ponto superior esquerdo. 
                            c.setBounds((int) this.conceitual.getPalco().getMousePosition().getX(), (int) this.conceitual.getPalco().getMousePosition().getY(), (Model.x - (int) this.conceitual.getPalco().getMousePosition().getX()), (Model.y - (int) this.conceitual.getPalco().getMousePosition().getY()));
                        }
                        break;
                        case INFERIOR_ESQUERDA: {
                            //Ponto inferior esquerdo
                            c.setBounds((int) this.conceitual.getPalco().getMousePosition().getX(), c.getY(), (Model.x - (int) this.conceitual.getPalco().getMousePosition().getX()), (int) this.conceitual.getPalco().getMousePosition().getY() - c.getY());
                        }
                        break;
                        case SUPERIOR_DIREITA: {
                            //Ponto superior direito
                            c.setBounds(c.getX(), (int) this.conceitual.getPalco().getMousePosition().getY(), (int) this.conceitual.getPalco().getMousePosition().getX() - c.getX(), (Model.y - (int) this.conceitual.getPalco().getMousePosition().getY()));
                        }
                        break;
                        case INFERIOR_DIREITA: {
                            //Ponto inferior direito
                            c.setBounds(c.getX(), c.getY(), (int) this.conceitual.getPalco().getMousePosition().getX() - c.getX(), (int) this.conceitual.getPalco().getMousePosition().getY() - c.getY());
                        }
                        break;
                    }
                    //Ao redimensionar os componentes, suas ligacoes devem ser atualizadas
                    corrigeLigacoes(c, 0, 0);

                } else if (Model.dragStarded) {
                    if (!Model.dragPanelCalculated) {
                        Model.dragPanelCalculated = true;
                        int menorX = 0;
                        int menorY = 0;
                        int maiorX = 0;
                        int maiorY = 0;
                        for (Componente co : Model.stackComponents.getAll()) {

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
                        Model.menorY = menorY;
                        Model.menorX = menorX;

                        Model.maiorX = maiorX;
                        Model.maiorY = maiorY;

                        this.conceitual.getPalco().add(paDrag, 0);
                        this.paDrag.setBounds(Model.menorX, Model.menorY, Model.maiorX - Model.menorX, Model.maiorY - Model.menorY);
                        this.paDrag.setVisible(true);
                        this.paDrag.setBackground(Color.BLUE);
                        this.paDrag.setOpaque(false);
                        this.paDrag.repaint();

                        /* Imagine que eu clique no ultimo pixel direito do componente, sera acionado o MOUSE_PRESSED. Depois eu
                         * arraste um pixel para a direita, entrarah no MOUSED_DRAGED, porem, quando chegar nesse trecho, 
                         * o mouse nao estara em cima do 'paDrag' pois o mouse na realidade esta em cima do 'paPalco' devido
                         * a esse pixel a mais no movimento. Eh preciso validar isso
                         */
                        Point paDragPoint = this.paDrag.getMousePosition();
                        if (paDragPoint == null) {
                            //Primeiro precisamos descobrir se o mouse extrapolou para a direita, esquerda, superior ou inferior.
                            if (this.conceitual.getPalco().getMousePosition().getX() > this.paDrag.getX() + this.paDrag.getWidth()) {
                                //Extrapolou pela direita
                                Model.x = (int) this.paDrag.getWidth();
                                Model.y = (int) this.conceitual.getPalco().getMousePosition().getY() - this.paDrag.getY();
                            } else if (this.conceitual.getPalco().getMousePosition().getX() < this.paDrag.getX()) {
                                //Extrapolou pela esquerda
                                Model.x = 0;
                                Model.y = (int) this.conceitual.getPalco().getMousePosition().getY() - this.paDrag.getY();
                            } else if (this.conceitual.getPalco().getMousePosition().getY() > this.paDrag.getY() + this.paDrag.getHeight()) {
                                //Extrapolou por baixo
                                Model.x = (int) this.conceitual.getPalco().getMousePosition().getX() - this.paDrag.getX();
                                Model.y = (int) this.paDrag.getHeight();
                            } else if (this.conceitual.getPalco().getMousePosition().getY() < this.paDrag.getY()) {
                                //Extrapolou por cima
                                Model.x = (int) this.conceitual.getPalco().getMousePosition().getX() - this.paDrag.getX();
                                Model.y = 0;
                            } else {
                                System.out.println("Extrapolou, mouse em cima do painel");
                            }
                        } else {
                            //Tive que usar uma variavel para armazenar o Pointer, pois as vezes
                            //passava pelo 'if is null' porem dava null nesse ponto.
                            Model.x = (int) paDragPoint.getX();
                            Model.y = (int) paDragPoint.getY();
                        }

                        for (Componente co : Model.stackComponents.getAll()) {
                            co.setBounds(co.getX() - menorX, co.getY() - menorY, co.getWidth(), co.getHeight());
                            this.paDrag.add(co);
                        }
                    }

                    //Mudando a posicao do componente (paDrag)
                    int x = (int) this.conceitual.getPalco().getMousePosition().getX();
                    int y = (int) this.conceitual.getPalco().getMousePosition().getY();

                    x -= Model.x; //Para pegar em qualquer parte sem dar um pulo no componente
                    y -= Model.y; //Para pegar em qualquer parte sem dar um pulo no componente
                    this.paDrag.setBounds(x, y, this.paDrag.getWidth(), this.paDrag.getHeight());

                    /* ===============================================================
                     * NESSE PONTO TODOS OS COMPONENTES JA FORAM MOVIMENTADOS. Agora =
                     * deve-se calcular as linhas de ligacao                         =
                     * ===============================================================
                     */

                    for (Componente comp : Model.stackComponents.getAll()) {
                        corrigeLigacoes(comp, x, y);
                    }

                }
            } catch (Exception ex) {
                Erro.deal(ex);
                //Esse erro pode ser ignorado ja que se ocorre quando o mouse esta fora do palco, gerando um mousePosition invalido. Nada
                //preocupante, o maximo que vai acontecer eh o componente nao se movimentar, o que eh correto.
            }
        } else if (e.getSource() == this.conceitual.getPalco() || e.getSource() instanceof Ligacao) {
            //O drag está ocorrendo sobre o palco ou alguma ligacao. Se for a ligacao, verificar se o usuario
            //esta movendo de lugar a primeira linha.

            if (e.getSource() instanceof LigacaoRelacionamentoEntidade) {
                LigacaoRelacionamentoEntidade ligacao = (LigacaoRelacionamentoEntidade) e.getSource();

                if (ligacao.isSobrePrimeiraLinha()) {
                    //O drag esta ocorrendo e o usuario esta com o mouse posicionado sobre a linha
                    //esta movendo a linha, mova!
                    ligacao.setXPrimeiraLinha(e.getX());
                    ligacao.setYPrimeiraLinha(e.getY());
                    corrigeLigacoes(ligacao.getPai(), 0, 0);
                }
            } else {
                //O drag esta ocorrendo sobre o palco, renderezar as linhas de selecao                      
                this.paGlassSelecao.repaint();
                calcularSelecao();
                if (!this.paGlassSelecao.isVisible()) {
                    this.paGlassSelecao.setBounds(0, 0, (int) this.conceitual.getPalco().getSize().getWidth(), (int) this.conceitual.getPalco().getSize().getHeight());
                    this.paGlassSelecao.setVisible(true);
                }
            }
        }
    }

    @Override
    //Quando o mouse dah um 'roleh' em cima do elemento. Eh um tipo de 'hover'
    public void mouseMoved(MouseEvent e) {
        try {
            //Verificando se o evento foi disparado sobre uma ligacao
            if (e.getSource() instanceof Ligacao) {
                boolean ligacaoUsouEvento = false;
                //Verificando se foi sobre uma ligacao usada entre relacionamento
                //e entidade pois essa ligacao possui cardinalidade...
                //Essa ligacao tambem permite movimentar a posicao da primeira linha
                if (e.getSource() instanceof LigacaoRelacionamentoEntidade) {
                    //Pega o objeto dessa ligacao que disparou o evento (e.getSource())
                    LigacaoRelacionamentoEntidade lig = (LigacaoRelacionamentoEntidade) e.getSource();
                    //Chama o metodo mouseHover dessa ligacao. Se ela entender que precisa do evento, variaveis
                    //irao mudar dependendo se for movimentacao da primeira linha
                    //ou interacao com a cardinalidade
                    lig.mouseHover(this.conceitual.getPalco().getMousePosition());
                    //Verificando se de fato passou por cima da posicao de 
                    //cardinalidade da ligacao
                    if (lig.isSobreCardinalidade()) {
                        ligacaoUsouEvento = true;
                    } //Nao estava sobre a cardinalidade mas e se estiver sobre a primeira linha?
                    else if (lig.isSobrePrimeiraLinha()) {
                        ligacaoUsouEvento = true;
                    }
                }

                //Se o evento nao foi usado pela ligacao que de fato ocorreu o 
                //evento de mouse, deve-se vasculhar todas as outras ligacoes
                //que estiverem na mesma posicao
                if (!ligacaoUsouEvento) {
                    //Nao... passou o mouse sobre uma parte 'transparente' da
                    //ligacao, rodar as outras ligacoes para descobrir se alguma
                    //outra 'merece' esse hover.
                    for (Componente c : Model.listComponentes()) {
                        //Iterando cada entidade a procura de suas ligacoes
                        if (c instanceof Entidade) {
                            //Listando as ligacoes dessa entidade
                            List<Ligacao> ligacoes = ((Entidade) c).listLigacoesRelacionamento();
                            //Rodando cada ligacao para verificar se ela recebe
                            //o evento. Se receber, para tudo!
                            for (Ligacao ligacao : ligacoes) {
                                //Chama o metodo mouseHover dessa ligacao. Se ela entender que precisa do evento, variaveis
                                //irao mudar dependendo se for movimentacao da primeira linha
                                //ou interacao com a cardinalidade
                                ((LigacaoRelacionamentoEntidade) ligacao).mouseHover(this.conceitual.getPalco().getMousePosition());
                                if (ligacao.isSobreCardinalidade()) {
                                    //Deu certo! O evento era pra ser dessa ligacao mesmo
                                    ligacaoUsouEvento = true;
                                    //this.conceitual.getPalco().setComponentZOrder(ligacao, this.conceitual.getPalco().getComponentCount()-1);
                                    this.conceitual.getPalco().setComponentZOrder(ligacao, 0);
                                    break;
                                } //Nao estava sobre a cardinalidade mas e se estiver sobre a primeira linha?
                                else if (ligacao.isSobrePrimeiraLinha()) {
                                    //Deu certo! O evento era pra ser dessa ligacao mesmo
                                    ligacaoUsouEvento = true;
                                    this.conceitual.getPalco().setComponentZOrder(ligacao, 0);
                                    break;
                                }
                            }
                            if (ligacaoUsouEvento) {
                                break;
                            }
                        }
                    }
                }

                //Eae? Alguma ligacao que possui cardinalidade recebeu o evento?
                if (!ligacaoUsouEvento) {
                    //Nao, nenhuma ligacao utilizou, vamos passar a vez para os
                    //componentes e vez para os componentes
                    Componente c = verificarComponentePosicao(this.conceitual.getPalco().getMousePosition());
                    if (c != null) {
                        this.conceitual.getPalco().setComponentZOrder(c, 0);
                    }
                }
            } else if (e.getSource().getClass().getSuperclass() == Entidade.class || e.getSource().getClass() == Relacionamento.class || e.getSource().getClass() == EspecializacaoParcial.class || e.getSource().getClass() == EspecializacaoTotal.class || e.getSource().getClass() == Texto.class) {
                Componente c = (Componente) e.getSource();
                Point cPoint = c.getMousePosition();

                if (cPoint.getX() < 8 && cPoint.getY() < 8) {
                    //Ponto superior esquerdo.
                    c.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
                } else if (cPoint.getX() < 8 && cPoint.getY() > c.getHeight() - 8) {
                    //Ponto inferior esquerdo
                    c.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
                } else if (cPoint.getX() > c.getWidth() - 8 && cPoint.getY() < 8) {
                    //Ponto superior direito
                    c.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
                } else if (cPoint.getX() > c.getWidth() - 8 && cPoint.getY() > c.getHeight() - 8) {
                    //Ponto inferior direito
                    c.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
                } else {
                    ((Componente) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }
        } catch (Exception ex) {
            /*
             * Quando esse erro ocorre?
             * As vezes o getMousePosition().getX() ou o getMousePosition().getY() 
             * podem disparar um nullPointerException
             */
            //Erro.deal(ex);
        }
    }
    /**
     * Acao utilizada para deletar os componentes selecionados ao pressionar do
     * botao [DEL].
     */
    Action deletarComponentes = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {

            //Manda copiar o modelo
            Model.copiarModelo();

            Componente[] componentes = Model.stackComponents.reset();
            for (Componente c : componentes) {

                //Verificar se eh um atributo pois atributos tem uma Ligacao
                if (c.getClass().getSuperclass() == Atributo.class) {
                    //Eh um atributo

                    Componente pai = ((Atributo) c).getComponentePai();
                    if (pai instanceof Entidade) {
                        //Diz para a entidade dona desse atributo que ele deve
                        //ser removido
                        ((Entidade) pai).removeAtributo((Atributo) c);
                    } else if (pai instanceof Relacionamento) {
                        //Diz para o relacionamento dono desse atributo que ele deve
                        //ser removido
                        ((Relacionamento) pai).removeAtributo((Atributo) c);
                    }
                    conceitual.getPalco().remove(c);
                } else if (c instanceof Entidade) {
                    Entidade ent = (Entidade) c;

                    if (c instanceof EntidadeAssociativa) {
                        ((EntidadeAssociativa) ent).removeEntidades();
                    }
                    //Removo a entidade
                    conceitual.getPalco().remove(ent);

                    //Ligacao entre a entidade e uma especializacao. Nesse caso, trata-se da entidade estar se espcializando
                    Ligacao ligacaoEspecializacao = ent.getLigacaoEspecializacao();
                    //Ligacao entre a entidade e uma especializacao. Nesse caso, trata-se da entidade ser especializada de outra
                    Ligacao ligacaoEspecializacaoBaixoNivel = ent.getLigacaoEspecializacaoBaixoNivel();

                    //Se possuir entidades especializadas, as especializacoes dever ser removidas juntas
                    if (ligacaoEspecializacao != null) {

                        //Deseleciono a especializacao e a entidade
                        ligacaoEspecializacao.getPai().setSelecionado(false);
                        ent.setSelecionado(false);

                        //Peco para a escpecializacao  "esquecer" todas as especialistas
                        ((Especializacao) ligacaoEspecializacao.getPai()).removeEntidades();

                        //Removo a especializacao
                        conceitual.getPalco().remove(ligacaoEspecializacao.getPai());

                        //Oculto a ligacao e depois removo-a
                        ligacaoEspecializacao.setVisible(false);
                        conceitual.getPalco().remove(ligacaoEspecializacao);
                    } else {

                        //Removendo os atributos
                        ent.removeAtributos();

                        if (ent.listLigacoesRelacionamento().size() > 0) {
                            //Possui uma ligacao com um relacionamento/entidade associativa. O relacionamento/entidade associativa devera 
                            //"esquecer" essa entidade e a ligacao devera ser removida

                            //As entidades nao podem ser removidas a cada iteracao do laco, pois ira alter o laco                            
                            List<Relacionamento> componentesPaiRelacionamento = new ArrayList<Relacionamento>();
                            List<EntidadeAssociativa> componentesPaiAssociativa = new ArrayList<EntidadeAssociativa>();

                            for (Ligacao l : ent.listLigacoesRelacionamento()) {
                                if (l.getPai() instanceof Relacionamento) {
                                    //Relacionamento 
                                    componentesPaiRelacionamento.add((Relacionamento) l.getPai());
                                } else {
                                    //Entidade associativa                                    
                                    componentesPaiAssociativa.add((EntidadeAssociativa) l.getPai());
                                }
                            }

                            for (Relacionamento componente : componentesPaiRelacionamento) {
                                componente.removeEntidade(ent);
                            }
                            for (EntidadeAssociativa componente : componentesPaiAssociativa) {
                                componente.removeEntidade(ent);
                            }
                        }
                        if (ligacaoEspecializacaoBaixoNivel != null) {
                            //Possui uma ligacao com um entidade SUPERIOR ligado atravez de uma especializacao
                            //A especializacao devera "esquercer" essa entidade especializada
                            ((Especializacao) ligacaoEspecializacaoBaixoNivel.getPai()).removeEntidade(ent);
                        }
                    }
                } else if (c.getClass() == Relacionamento.class) {
                    Relacionamento rel = (Relacionamento) c;
                    rel.removeAtributos();
                    rel.removeEntidades();
                    rel.setVisible(false);
                    conceitual.getPalco().remove(rel);
                } else if (c.getClass() == AtributoComposto.class) {
                } else if (c instanceof Especializacao) {

                    //Peco a entidade de ALTO NIVEL e ordeno-a que retire a sua ligacao, pois a especializacao
                    //serah pulverizada
                    Ligacao ligacao = ((Especializacao) c).getAltoNivel().getLigacaoEspecializacao();
                    ligacao.setVisible(false);
                    conceitual.getPalco().remove(ligacao);

                    //Mando a especializacao "esquecer" as suas entidades especialistas
                    ((Especializacao) c).removeEntidades();

                    //Falo pra super classe que ela nao tem mais ligacao com especializacao alguma
                    ((Especializacao) c).getAltoNivel().setLigacaoEspecializacao(null);

                    //Retiro a especializacao da tela
                    conceitual.getPalco().remove(c);
                } else if (c instanceof Texto) {
                    conceitual.getPalco().remove(c);
                }

                c = null;
                ControllerConceitual.this.atualizaListaComponentes();
            }
        }
    };

    public void desfazer() {
        if (Model.desfazer()) {
            //Isso tira tudo, inclusive os paineis 'paDrag' e 'paGlassSelecao'
            ControllerConceitual.this.conceitual.getPalco().removeAll();
            ControllerConceitual.this.conceitual.getPalco().repaint();

            //Reinserindo os paineis 
            ControllerConceitual.this.conceitual.getPalco().add(paDrag);
            ControllerConceitual.this.conceitual.getPalco().add(paGlassSelecao);

            restaurarComponentes(false);
        }
        ControllerConceitual.this.conceitual.getPalco().repaint();
    }

    public void refazer() {
        if (Model.refazer()) {
            //Isso tira tudo, inclusive os paineis 'paDrag' e 'paGlassSelecao'
            ControllerConceitual.this.conceitual.getPalco().removeAll();
            ControllerConceitual.this.conceitual.getPalco().repaint();

            //Reinserindo os paineis 
            ControllerConceitual.this.conceitual.getPalco().add(paDrag);
            ControllerConceitual.this.conceitual.getPalco().add(paGlassSelecao);

            restaurarComponentes(false);
        }

        ControllerConceitual.this.conceitual.getPalco().repaint();
    }
    Action desfazer = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            desfazer();
        }
    };
    public Action refazer = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            refazer();
        }
    };

    /**
     * Metodo que verifica se em uma determinada posicao existe um componente.
     * Se houver, retorna-o. Utilizado quando eh pressionado o mouse sobre uma
     * ligacao, pois deve-se verificar se ha algum componente por baixo jah que
     * a ligacao eh transparente e nao deve atrapalhar a selecao e movimentacao
     * dos componentes.
     *
     * @param p
     * @return
     */
    private Componente verificarComponentePosicao(Point p) {

        for (Component c : this.conceitual.getPalco().getComponents()) {
            if (c instanceof Componente) {
                if ((p.getX() > c.getX() && p.getX() < (c.getX() + c.getWidth())) && p.getY() > c.getY() && p.getY() < (c.getY() + c.getHeight())) {
                    return (Componente) c;
                }
            }
        }
        return null;
    }

    /**
     * Metodo responsavel em calcular a area de selecao e selecionar os
     * componentes que estiverem nesse perimetro.
     */
    private void calcularSelecao() {
        Model.stackComponents.reset();
        for (Component c : this.conceitual.getPalco().getComponents()) {
            if (c instanceof Componente) {
                if (((Model.xSelecao + Model.wSelecao) >= c.getX() && (Model.ySelecao + Model.hSelecao) >= c.getY()) && (Model.xSelecao <= c.getX() + c.getWidth() && Model.ySelecao <= c.getY() + c.getHeight())) {
                    Model.stackComponents.toPush((Componente) c);
                }
            }
        }
    }

    /**
     * Metodo que atualiza a lista de componentes que se encontra no palco. Essa
     * lista eh utilizada pela camada model no momento de salvar, entre outros.
     * Toda vez que um componente eh <b>adicionado ou removido</b> do palco,
     * esse metodo eh chamado. Ao ser chamado, remove todos os componentes da
     * lista e adiciona-os novamente
     */
    private void atualizaListaComponentes() {
        //Diz ao model que ele serah alterado. Isso permite
        //o empilhamento do model para undo e redo
        Model.modelEditado();

        //Exclui todos os componentes da lista que a model conhece
        Model.clearComponentes();
        //Roda todos os elementos que estao visiveis no palco e insere na lista
        //de componentes que a model conhece.
        for (Component c : this.conceitual.getPalco().getComponents()) {
            if (c instanceof Componente) {
                //As vezes o componente foi ocultado mas no pode ser removido do
                //palco por falta de acesso ao palco. Se estiver oculto, elimina-o
                //de uma vez, se nao, adiciona ao Model.
                if (c.isVisible()) {
                    //Adiciona
                    Model.addComponente((Componente) c);
                } else {
                    this.conceitual.getPalco().remove(c);
                }
            }
        }
    }

    /**
     * Metodo responsavel por se comunicar com a camada model, requisitar os
     * objetos e reenderizar a view com os componentes salvos do projeto
     */
    private void restaurarComponentes(boolean primeiraRestauracao) {
        try {
            Model.stackComponents.reset();

            if (primeiraRestauracao) {
                Model.clearComponentes();
                Model.setComponentes(new ProjetoBo().abrirConceitual(this, Model.path));
            }
            for (Componente c : Model.listComponentes()) {
                this.conceitual.getPalco().add(c);
                c.addMouseMotionListener(this);
                c.addMouseListener(this);
                c.setSelecionado(false);

                //Se for entidade, adiciona as suas ligacoes
                if (c instanceof Entidade) {
                    Entidade ent = ((Entidade) c);

                    //Verificando se possui uma ligacao com especializacao
                    if (ent.getLigacaoEspecializacao() != null) {
                        ent.getLigacaoEspecializacao().addMouseListener(this);
                        ent.getLigacaoEspecializacao().addMouseMotionListener(this);
                        this.conceitual.getPalco().add(ent.getLigacaoEspecializacao());
                    }

                    //Verificando se possui uma ligacao com alguma SUPER entidade
                    if (ent.getLigacaoEspecializacaoBaixoNivel() != null) {
                        ent.getLigacaoEspecializacaoBaixoNivel().addMouseListener(this);
                        ent.getLigacaoEspecializacaoBaixoNivel().addMouseMotionListener(this);
                        this.conceitual.getPalco().add(ent.getLigacaoEspecializacaoBaixoNivel());
                    }

                    //Se houver ligacao com relacionamentos/associativas, coloca
                    //no palco
                    for (Ligacao l : ent.listLigacoesRelacionamento()) {
                        l.addMouseListener(this);
                        l.addMouseMotionListener(this);
                        this.conceitual.getPalco().add(l);
                    }
                } //Se for atributo, adiciona as suas ligacoes
                else if (c instanceof Atributo) {
                    this.conceitual.getPalco().add(((Atributo) c).getLigacao());
                }
            }
            this.conceitual.getPalco().repaint();

        } catch (IOException ex) {
            Erro.deal(ex);
            JOptionPane.showMessageDialog(null, Model.lang.getString("alertaWorkspaceIncompleta"), Model.lang.getString("titulo"), JOptionPane.WARNING_MESSAGE);
        }
    }

    public void corrigeLigacoes(Componente comp, int x, int y) {
        /*
         * ============= A T R I B U T O =============
         */
        if (comp instanceof Atributo) {
            //Descobrir o componente pai
            Componente compPai = ((Atributo) comp).getComponentePai();
            //Descobrir se o componente pai tambem esta sendo arrastado
            //Se estah havendo um drag e o componente pai estiver selecionado, eh
            //obvio que ele tambem esta sendo arrastado
            //Rodando os elementos para criar os paineis de ligacao
            boolean paiSelecionado = false;
            for (Componente comp2 : Model.stackComponents.getAll()) {
                if (compPai == comp2) {
                    paiSelecionado = true;
                    break;
                }
            }
            //Se estiver, calcula o X e Y com base na sua posicao dentro da selecao
            XDimension dimPai;
            if (paiSelecionado) {
                dimPai = new XDimension(compPai.getWidth(), compPai.getHeight(), x + compPai.getX(), y + compPai.getY());
            } //Se nao, apenas recupera o X e Y do componente pai.
            else {
                dimPai = new XDimension(compPai.getWidth(), compPai.getHeight(), compPai.getX(), compPai.getY());
            }
            //Acessar o objeto Ligacao passando o X e Y do componente pai e o X e Y  do atributo (posicoes relacionais)
            ((Atributo) comp).getLigacao().ligacao(dimPai, new XDimension(comp.getWidth(), comp.getHeight(), x + comp.getX(), y + comp.getY()));
        } /*
         * ============= E N T I D A D E =============
         */ else if (comp instanceof Entidade) {

            //Listando todos os atributos do componente que esta sendo arrastado.
            List<Atributo> atributos;
            atributos = ((Entidade) comp).listAtributos();

            //Primeiro passo eh consultar todos os atributos

            /*
             * === ATRIBUTOS ===
             */

            for (Atributo atr : atributos) {
                //A entidade ja sabemos que esta sendo arrastada, agora deve-se verificar se
                //o atributo tambem estah. Se o atributo tambem estiver sendo arrastado, termina
                //aqui pois ele jah teve sua Ligacao corrigida logo acima.
                boolean atrSelecionado = false;
                for (Componente comp2 : Model.stackComponents.getAll()) {
                    if (atr == comp2) {
                        atrSelecionado = true;
                        break;
                    }
                }
                if (!atrSelecionado) {
                    XDimension dimPai = new XDimension(comp.getWidth(), comp.getHeight(), x + comp.getX(), y + comp.getY());
                    //Acessar o objeto Ligacao passando o X e Y do componente pai e o X e Y  do atributo (posicoes relacionais)
                    ((Atributo) atr).getLigacao().ligacao(dimPai, new XDimension(atr.getWidth(), atr.getHeight(), atr.getX(), atr.getY()));
                }
            }

            //Listando todos as entidades do componente que esta sendo arrastado, se for uma entidade associativa.

            /*
             * === ASSOCIATIVA ===
             */

            if (comp instanceof EntidadeAssociativa) {
                List<Entidade> entidades;
                entidades = ((EntidadeAssociativa) comp).listEntidades();

                //Primeiro passo eh consultar todos as entidades
                for (Entidade ent : entidades) {
                    //A entidade associativa ja sabemos que esta sendo arrastada, agora deve-se verificar se
                    //a entidade comum tambem estah. Se a entidade comum tambem estiver sendo arrastado, termina
                    //aqui pois ele jah teve sua Ligacao corrigida logo acima.
                    boolean entSelecionada = false;
                    for (Componente comp2 : Model.stackComponents.getAll()) {
                        if (ent == comp2) {
                            entSelecionada = true;
                            break;
                        }
                    }
                    if (!entSelecionada) {
                        XDimension dimPai = new XDimension(comp.getWidth(), comp.getHeight(), x + comp.getX(), y + comp.getY());
                        //Acessar o objeto Ligacao passando o X e Y do componente pai e o X e Y  do atributo (posicoes relacionais)
                        ((Entidade) ent).getLigacao(comp).ligacao(dimPai, new XDimension(ent.getWidth(), ent.getHeight(), ent.getX(), ent.getY()));
                    }
                }
            }
            for (Ligacao l : ((Entidade) comp).listLigacoesRelacionamento()) {
                Componente pai = l.getPai();
                Componente filho = l.getFilho();

                XDimension dimPai;
                if (pai.isSelecionado()) {
                    dimPai = new XDimension(pai.getWidth(), pai.getHeight(), x + pai.getX(), y + pai.getY());
                } //Se nao, apenas recupera o X e Y do componente pai.
                else {
                    dimPai = new XDimension(pai.getWidth(), pai.getHeight(), pai.getX(), pai.getY());
                }

                XDimension dimFilho = new XDimension(filho.getWidth(), filho.getHeight(), x + filho.getX(), y + filho.getY());

                //Acessar o objeto Ligacao passando o X e Y do componente pai e o X e Y  do atributo (posicoes relacionais)
                l.ligacao(dimPai, dimFilho);
            }
            //Agora atualizar a ligacao entre a entidade e a especializacao se houver

            /*
             * === ESPECIALIZACAO ===
             */

            if (((Entidade) comp).getLigacaoEspecializacao() != null) {

                Componente pai = ((Entidade) comp).getLigacaoEspecializacao().getPai();
                Componente filho = ((Entidade) comp).getLigacaoEspecializacao().getFilho();

                XDimension dimPai;
                if (pai.isSelecionado()) {
                    dimPai = new XDimension(pai.getWidth(), pai.getHeight(), x + pai.getX(), y + pai.getY());
                } //Se nao, apenas recupera o X e Y do componente pai.
                else {
                    dimPai = new XDimension(pai.getWidth(), pai.getHeight(), pai.getX(), pai.getY());
                }

                XDimension dimFilho = new XDimension(filho.getWidth(), filho.getHeight(), x + filho.getX(), y + filho.getY());

                //Acessar o objeto Ligacao passando o X e Y do componente pai e o X e Y  do atributo (posicoes relacionais)
                ((Entidade) comp).getLigacaoEspecializacao().ligacao(dimPai, dimFilho);
            }
            //Agora atualizar a ligacao entre a entidade e a especializacao se houver (BAIXO NIVEL)

            /*
             * === ESPECIALIZACAO (baixo nivel) ===
             */

            if (((Entidade) comp).getLigacaoEspecializacaoBaixoNivel() != null) {

                Componente pai = ((Entidade) comp).getLigacaoEspecializacaoBaixoNivel().getPai();
                Componente filho = ((Entidade) comp).getLigacaoEspecializacaoBaixoNivel().getFilho();

                XDimension dimPai;
                if (pai.isSelecionado()) {
                    dimPai = new XDimension(pai.getWidth(), pai.getHeight(), x + pai.getX(), y + pai.getY());
                } //Se nao, apenas recupera o X e Y do componente pai.
                else {
                    dimPai = new XDimension(pai.getWidth(), pai.getHeight(), pai.getX(), pai.getY());
                }

                XDimension dimFilho = new XDimension(filho.getWidth(), filho.getHeight(), x + filho.getX(), y + filho.getY());

                //Acessar o objeto Ligacao passando o X e Y do componente pai e o X e Y  do atributo (posicoes relacionais)
                ((Entidade) comp).getLigacaoEspecializacaoBaixoNivel().ligacao(dimPai, dimFilho);
            }
        } /*
         * ============= R E L A C I O N A M E N T O =============
         */ else if (comp instanceof Relacionamento) {
            //Listando todos os atributos do componente que esta sendo arrastado.
            List<Atributo> atributos;
            atributos = ((Relacionamento) comp).listAtributos();

            //Primeiro passo eh consultar todos os atributos
            for (Atributo atr : atributos) {
                //O relacionamento ja sabemos que esta sendo arrastado, agora deve-se verificar se
                //o atributo tambem estah. Se o atributo tambem estiver sendo arrastado, termina
                //aqui pois ele jah teve sua Ligacao corrigida logo acima.
                boolean atrSelecionado = false;
                for (Componente comp2 : Model.stackComponents.getAll()) {
                    if (atr == comp2) {
                        atrSelecionado = true;
                        break;
                    }
                }
                if (!atrSelecionado) {
                    XDimension dimPai = new XDimension(comp.getWidth(), comp.getHeight(), x + comp.getX(), y + comp.getY());
                    //Acessar o objeto Ligacao passando o X e Y do componente pai e o X e Y  do atributo (posicoes relacionais)
                    ((Atributo) atr).getLigacao().ligacao(dimPai, new XDimension(atr.getWidth(), atr.getHeight(), atr.getX(), atr.getY()));
                }
            }

            //Listando todas as entidades do componente que esta sendo arrastado.
            List<Entidade> entidades = ((Relacionamento) comp).listEntidades();

            //Segundo passo eh consultar todas as entidades LIGADAS A ESSE RELACIONAMENTO
            for (Entidade ent : entidades) {
                //O relacionamento ja sabemos que esta sendo arrastado, agora deve-se verificar se
                //a entidade tambem estah. Se a entidade tambem estiver sendo arrastado, termina
                //aqui pois ele jah teve sua Ligacao corrigida logo acima.
                boolean entSelecionado = false;
                //Pra cada componente do modelo selecionado...
                for (Componente comp2 : Model.stackComponents.getAll()) {
                    //... verificar se eh uma entidade ligada ao relacionamento
                    if (ent == comp2) {
                        entSelecionado = true;
                        break;
                    }
                }
                if (!entSelecionado) {

                    //LEMBRE-SE, pode haver duas ligacoes entre a entidade e o relacionamento, indicando um auto-relacionamento

                    //O 'pai' eh o relacionamento
                    XDimension dimPai = new XDimension(comp.getWidth(), comp.getHeight(), x + comp.getX(), y + comp.getY());

                    for (Ligacao ligacao : ((Entidade) ent).getLigacoes(comp)) {
                        //Acessar o objeto Ligacao passando o X e Y do componente pai e o X e Y  da entidade (posicoes relacionais)
                        ligacao.ligacao(dimPai, new XDimension(ent.getWidth(), ent.getHeight(), ent.getX(), ent.getY()));
                    }
                }
            }
        } /*
         * ============= E S P E C I A L I Z A C A O =============
         */ else if (comp instanceof Especializacao) {
            //Corrigindo ligacao entre a entidade de ALTO NIVEL
            Entidade filhoAlto = ((Especializacao) comp).getAltoNivel();
            Especializacao pai = (Especializacao) comp;
            //Se estou arrastando a especializacao, ja considera a posicao relativa dela
            //A entidade pode ou nao estar sendo movimentada, validar para calcular posicao relativa
            XDimension dimFilho;
            if (filhoAlto.isSelecionado()) {
                dimFilho = new XDimension(filhoAlto.getWidth(), filhoAlto.getHeight(), x + filhoAlto.getX(), y + filhoAlto.getY());
            } else {
                dimFilho = new XDimension(filhoAlto.getWidth(), filhoAlto.getHeight(), filhoAlto.getX(), filhoAlto.getY());
            }

            XDimension dimPai = new XDimension(pai.getWidth(), pai.getHeight(), x + pai.getX(), y + pai.getY());

            //Acessar o objeto Ligacao passando o X e Y do componente pai e o X e Y  da entidade (posicoes relacionais)
            filhoAlto.getLigacaoEspecializacao().ligacao(dimPai, dimFilho);

            //Corrigindo ligacao entre a entidade de BAIXO NIVEL
            //Primeiro passo eh consultar todas as entidades                            
            for (Entidade ent : pai.getEspecialistas()) {
                //A especializacao ja sabemos que esta sendo arrastada, agora deve-se verificar se
                //a entidade tambem estah. Se a entidade tambem estiver sendo arrastado, termina
                //aqui pois ele jah teve sua Ligacao corrigida logo acima.
                boolean atrSelecionado = false;
                for (Componente comp2 : Model.stackComponents.getAll()) {
                    if (ent == comp2) {
                        atrSelecionado = true;
                        break;
                    }
                }
                if (!atrSelecionado) {
                    XDimension dimPai2 = new XDimension(pai.getWidth(), pai.getHeight(), x + pai.getX(), y + pai.getY());
                    //Acessar o objeto Ligacao passando o X e Y do componente pai e o X e Y  da entidade (posicoes relacionais)
                    ent.getLigacaoEspecializacaoBaixoNivel().ligacao(dimPai2, new XDimension(ent.getWidth(), ent.getHeight(), ent.getX(), ent.getY()));
                }
            }
        }
    }
}
