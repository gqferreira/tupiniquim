/*
 * To change component2 template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.Model;
import br.com.gqferreira.tupiniquim.model.Cardinalidade;
import br.com.gqferreira.tupiniquim.model.XDimension;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Classe que representa graficamente a ligacao entre dois componentes
 *
 * @author Gustavo Ferreira
 */
public class LigacaoRelacionamentoEntidade extends Ligacao {

    //Usado para casos excepcionais onde o atributo eh maior que o tamanho
    //do painel de ligacao, outra estrategia serah tomada
    //private boolean entidadeGrande = false;
    //Indica quando a entidade esta localizado acima do relacionamento
    private Boolean acima = null;
    private int pontoConexao = 0;
    private Boolean cacula = null;
    //Variaveis utilizadas para a movimentacao da primeira linha
    private boolean ultimoEsquerda = false;
    private boolean ultimoAcima = false;
    private int ultimaArea = 0;
    private int ultimoWidth = 0;
    private int ultimoHeight = 0;
    private int xPrimeiraLinha = 0;
    private int yPrimeiraLinha = 0;

    public LigacaoRelacionamentoEntidade(Componente pai, Componente entidade) {
        super(pai, entidade);
        LigacaoRelacionamentoEntidade.this.cardinalidade = new Cardinalidade(Cardinalidade.Tipo.UM, Cardinalidade.Tipo.UM);
    }

    /**
     * Define qual eh a ligacao irma dessa, usado em auto-relacionamentos
     */
    public void setIrma(Ligacao irma, boolean cacula) {
        this.irma = irma;
        //pontoConexao = cacula ? 25 : -25;
        this.cacula = cacula;
    }

    /**
     * Metodo que recebe a posicao x do mouse em relacao a area da ligacao. Essa
     * informacao eh fornecida pela ControllerConceitual quando ocorre o evento
     * de drag sobre a ligacao
     *
     * @param xPrimeiraLinha
     */
    public void setXPrimeiraLinha(int xPrimeiraLinha) {
        //Se o componente pai estiver a esquerda
        if (esquerda) {
            //Somente se o valor para x for compativel com o tamanho do componente pai
            if (xPrimeiraLinha > 0 && xPrimeiraLinha < pai.getWidth()) {
                this.xPrimeiraLinha = xPrimeiraLinha;
            }
        } //Se o componente pai estiver a direita, porem somente se o valor para X
        //for compativel com o tamanho do componente pai
        else if (xPrimeiraLinha < this.getWidth() && xPrimeiraLinha > (this.getWidth() - pai.getWidth())) {
            this.xPrimeiraLinha = xPrimeiraLinha;
        }

        /*
         * As validacoes para saber se a posicao x eh valida em relacao ao tamanho
         * do componente pai sao diferentes para direita e essquerda pelo fato
         * de que essa posicao interfere em como o painel expande.
         */
    }

    /**
     * Metodo que recebe a posicao y do mouse em relacao a area da ligacao. Essa
     * informacao eh fornecida pela ControllerConceitual quando ocorre o evento
     * de drag sobre a ligacao
     *
     * @param yPrimeiraLinha
     */
    public void setYPrimeiraLinha(int yPrimeiraLinha) {
        //Se o componente pai estiver alinhado com o filho, nao permite movimenta
        //da ligacao
        if (acima != null) {
            //Se o componente pai estiver acima
            if (acima) {
                //Somente se o valor para y for compativel com o tamanho do componente pai
                if (yPrimeiraLinha > 0 && yPrimeiraLinha < pai.getHeight()) {
                    this.yPrimeiraLinha = yPrimeiraLinha;
                }
            } //Se o componente pai estiver abaixo, porem somente se o valor para y
            //for compativel com o tamanho do componente pai
            else if (yPrimeiraLinha < this.getHeight() && yPrimeiraLinha > (this.getHeight() - pai.getHeight())) {
                this.yPrimeiraLinha = yPrimeiraLinha;
            }
        }

        /*
         * As validacoes para saber se a posicao y eh valida em relacao ao tamanho
         * do componente pai sao diferentes para direita e essquerda pelo fato
         * de que essa posicao interfere em como o painel expande.
         */
    }

    @Override
    protected void calcularPosicaoTamanhoLigacao(XDimension pai, XDimension entidade, Componente atrObj) {
        /* 
         * Eh preciso atentar-se ao fato de o atributo estar por cima ou por baixo
         * do atributo. Se o componente se interseccionar, nao cria a "linha"
         */
        boolean interseccao = false;
        if ((entidade.getX() >= pai.getX() && entidade.getX() <= (pai.getX() + pai.getWidth())) || ((entidade.getX() + entidade.getWidth()) >= pai.getX() && (entidade.getX() + entidade.getWidth()) <= (pai.getX() + pai.getWidth()))) {
            //Interseccionou pelo X
            if ((entidade.getY() >= pai.getY() && entidade.getY() <= (pai.getY() + pai.getHeight())) || ((entidade.getY() + entidade.getHeight()) >= pai.getY() && (entidade.getY() + entidade.getHeight()) <= (pai.getY() + pai.getHeight()))) {
                //Interseccionou pelo Y
                interseccao = true;
                System.out.println("Interseccao");
            }
        }
        if (!interseccao) {

            int x = 0, y = 0, w = 0, h = 0;
            //Esse tipo de ligacao so permite quatro areas
            //Verificando se eh a area 1
            if (entidade.getY() + entidade.getHeight() < pai.getY()) {
                //AREA 1
                pontoConexao = 17;
                int acrescimento = cacula != null ? (pontoConexao * (pai.getHeight() / 2)) / (pai.getWidth() / 2) : 0;

                this.area = 1;
                if (entidade.getX() + entidade.getWidth() / 2 < pai.getX() + pai.getWidth() / 2) {
                    this.esquerda = true;

                    x = entidade.getX();
                    y = entidade.getY() + entidade.getHeight();
                    w = pai.getX() + pai.getWidth() - entidade.getX();
                    h = pai.getY() - (entidade.getY() + entidade.getHeight()) + acrescimento;
                } else {
                    this.esquerda = false;

                    x = pai.getX();
                    y = entidade.getY() + entidade.getHeight();
                    w = (entidade.getX() + entidade.getWidth()) - pai.getX();
                    h = pai.getY() - y + acrescimento;
                }
            } else if (entidade.getY() > pai.getY() + pai.getHeight()) {
                //AREA 3
                pontoConexao = 17;
                int acrescimento = cacula != null ? (pontoConexao * (pai.getHeight() / 2)) / (pai.getWidth() / 2) : 0;
                this.area = 3;

                if (entidade.getX() + entidade.getWidth() / 2 < pai.getX() + pai.getWidth() / 2) {
                    this.esquerda = true;

                    x = entidade.getX();
                    y = pai.getY() + pai.getHeight() - acrescimento;
                    w = (pai.getX() + pai.getWidth()) - entidade.getX();
                    h = entidade.getY() - y;
                } else {
                    this.esquerda = false;
                    x = pai.getX();
                    y = pai.getY() + pai.getHeight() - acrescimento;
                    w = (entidade.getX() + entidade.getWidth()) - pai.getX();
                    h = entidade.getY() - y;
                }
            } else if (entidade.getX() > pai.getX() + pai.getWidth()) {
                //AREA 2
                pontoConexao = 8;
                int acrescimento = cacula != null ? (pontoConexao * (pai.getWidth() / 2)) / (pai.getHeight() / 2) : 0;
                this.area = 2;

                if (entidade.getY() < pai.getY()) {
                    this.acima = true;
                    x = pai.getX() + pai.getWidth() - acrescimento;
                    y = entidade.getY();
                    w = entidade.getX() - x;
                    h = (pai.getY() + pai.getHeight()) - y;
                } else if (entidade.getY() + entidade.getHeight() > pai.getY() + pai.getHeight()) {
                    this.acima = false;
                    x = pai.getX() + pai.getWidth() - acrescimento;
                    y = pai.getY();
                    w = entidade.getX() - x;
                    h = (entidade.getY() + entidade.getHeight()) - y;
                } else {
                    this.acima = null;
                    x = pai.getX() + pai.getWidth() - acrescimento;
                    y = pai.getY();
                    w = entidade.getX() - x;
                    h = pai.getHeight();
                }
            } else if (entidade.getX() + entidade.getWidth() < pai.getX()) {

                //AREA 4
                this.area = 4;
                pontoConexao = 8;
                int acrescimento = cacula != null ? (pontoConexao * (pai.getWidth() / 2)) / (pai.getHeight() / 2) : 0;

                if (entidade.getY() < pai.getY()) {
                    this.acima = true;
                    x = entidade.getX() + entidade.getWidth();
                    y = entidade.getY();
                    w = pai.getX() - x + acrescimento;
                    h = (pai.getY() + pai.getHeight()) - y;
                } else if (entidade.getY() + entidade.getHeight() > pai.getY() + pai.getHeight()) {
                    this.acima = false;
                    x = entidade.getX() + entidade.getWidth();
                    y = pai.getY();
                    w = pai.getX() - x + acrescimento;
                    h = (entidade.getY() + entidade.getHeight()) - y;
                } else {
                    this.acima = null;
                    x = entidade.getX() + entidade.getWidth();
                    y = pai.getY();
                    w = pai.getX() - (entidade.getX() + entidade.getWidth()) + acrescimento;
                    h = pai.getHeight();
                }


            } else {
                System.out.println("NULL");
            }
            this.setBounds(x, y, w, h);
        }
    }

    @Override
    protected void calcularLinha(XDimension pai, XDimension entidade) {

        this.linhas.clear();
        switch (this.area) {
            case 1: {
                if (!this.esquerda) {

                    int xTerceiraLinha = 0;

                    //Se for uma ligacao que possui uma 'irma', ou seja, uma outra ligacao
                    //a qual se deve manter distancia proporcional. Usado em auto-relacionamento
                    if (cacula != null) {
                        if (cacula) {
                            //Se for a linha 'cacula' (leia "cassula", a mais nova), possui desvio de 10 pixels nas linhas
                            //para nao coincidir a sua irma.
                            xPrimeiraLinha = (this.getWidth() - (entidade.getWidth() / 2)) + pontoConexao;
                            xTerceiraLinha = (pai.getWidth() / 2) + pontoConexao;

                            linhas.add(new XDimension(1, (this.getHeight() / 2) + 10, xPrimeiraLinha, 0));
                            linhas.add(new XDimension(xPrimeiraLinha - xTerceiraLinha, 1, xTerceiraLinha, (this.getHeight() / 2) + 10));
                            linhas.add(new XDimension(1, (this.getHeight() / 2) - 10, xTerceiraLinha, (this.getHeight() / 2) + 10));
                        } else {
                            //Posicoes padroes
                            xPrimeiraLinha = (this.getWidth() - (entidade.getWidth() / 2)) - pontoConexao;
                            xTerceiraLinha = (pai.getWidth() / 2) - pontoConexao;

                            linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, 0));
                            linhas.add(new XDimension(xPrimeiraLinha - xTerceiraLinha, 1, xTerceiraLinha, this.getHeight() / 2));
                            linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, this.getHeight() / 2));
                        }
                    } //Se nao possui, as posicoes volta para o padrao
                    else {
                        //Mudou a area? Recalcula.
                        if (ultimaArea != area || ultimoEsquerda != this.esquerda) {
                            xPrimeiraLinha = this.getWidth() - (entidade.getWidth() / 2);
                            //Atualiza a 'ultimaArea' e 'ultimaEsquerda'
                            ultimaArea = area;
                            ultimoEsquerda = this.esquerda;
                            ultimoWidth = this.getWidth();
                        } else {
                            int x = ultimoWidth - xPrimeiraLinha;
                            xPrimeiraLinha = this.getWidth() - x;
                            ultimoWidth = this.getWidth();
                        }
                        xTerceiraLinha = pai.getWidth() / 2;

                        linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, 0));
                        linhas.add(new XDimension(xPrimeiraLinha - xTerceiraLinha, 1, xTerceiraLinha, this.getHeight() / 2));
                        linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, this.getHeight() / 2));
                    }

                    this.cardinalidade.setX(xTerceiraLinha - this.cardinalidade.getW() - 3);
                    this.cardinalidade.setY(this.getHeight() - this.cardinalidade.getH() + 5);
                } else {

                    int xTerceiraLinha = 0;

                    //Se for uma ligacao que possui uma 'irma', ou seja, uma outra ligacao
                    //a qual se deve manter distancia proporcional. Usado em auto-relacionamento
                    if (cacula != null) {
                        if (cacula) {
                            //Se for a linha 'cacula' (leia "cassula", a mais nova), possui desvio de 10 pixels nas linhas
                            //para nao coincidir a sua irma.
                            xPrimeiraLinha = (entidade.getWidth() / 2) - pontoConexao;
                            xTerceiraLinha = (this.getWidth() - (pai.getWidth() / 2)) - pontoConexao;

                            linhas.add(new XDimension(1, (this.getHeight() / 2) + 10, xPrimeiraLinha, 0)); //Primeira linha, partindo da entidade
                            linhas.add(new XDimension(xTerceiraLinha - xPrimeiraLinha, 1, xPrimeiraLinha, (this.getHeight() / 2) + 10)); //Linha horizontal
                            linhas.add(new XDimension(1, (this.getHeight() / 2) - 10, xTerceiraLinha, (this.getHeight() / 2) + 10)); //Terceira linha, partindo da entidade
                        } else {
                            //Posicoes padroes
                            xPrimeiraLinha = (entidade.getWidth() / 2) + pontoConexao;
                            xTerceiraLinha = (this.getWidth() - (pai.getWidth() / 2)) + pontoConexao;

                            linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, 0));
                            linhas.add(new XDimension(xTerceiraLinha - xPrimeiraLinha, 1, xPrimeiraLinha, this.getHeight() / 2));
                            linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, this.getHeight() / 2));
                        }
                    } //Se nao possui, as posicoes volta para o padrao
                    else {
                        //Mudou a area? Recalcula.
                        if (ultimaArea != area || ultimoEsquerda != this.esquerda) {
                            xPrimeiraLinha = entidade.getWidth() / 2;
                            //Atualiza a 'ultimaArea' e 'ultimaEsquerda'
                            ultimaArea = area;
                            ultimoEsquerda = this.esquerda;
                        }

                        xTerceiraLinha = this.getWidth() - (pai.getWidth() / 2);

                        linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, 0));
                        linhas.add(new XDimension(xTerceiraLinha - xPrimeiraLinha, 1, xPrimeiraLinha, this.getHeight() / 2));
                        linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, this.getHeight() / 2));
                    }

                    this.cardinalidade.setX(xTerceiraLinha + 3);
                    this.cardinalidade.setY(this.getHeight() - this.cardinalidade.getH() + 5);
                }
            }
            break;
            case 2: {
                if (this.acima == null) {

                    int yTerceiraLinha = 0;

                    //Se for uma ligacao que possui uma 'irma', ou seja, uma outra ligacao
                    //a qual se deve manter distancia proporcional. Usado em auto-relacionamento
                    if (cacula != null) {
                        if (cacula) {
                            yPrimeiraLinha = (entidade.getHeight() / 2) + (entidade.getY() - this.getY()) + pontoConexao;
                            yTerceiraLinha = (pai.getHeight() / 2) + pontoConexao;

                            linhas.add(new XDimension((this.getWidth() / 2) - 10, 1, (this.getWidth() / 2) + 10, yPrimeiraLinha)); //Primeira linha, a partir da entidade
                            linhas.add(new XDimension(1, (yPrimeiraLinha < yTerceiraLinha ? yTerceiraLinha - yPrimeiraLinha : yPrimeiraLinha - yTerceiraLinha), (this.getWidth() / 2) + 10, (yPrimeiraLinha < yTerceiraLinha ? yPrimeiraLinha : yTerceiraLinha)));//Linha horizontal
                            linhas.add(new XDimension((this.getWidth() / 2) + 10, 1, 0, yTerceiraLinha)); //Terceira linha, a partir da entidade
                        } else {
                            yPrimeiraLinha = (entidade.getHeight() / 2) + (entidade.getY() - this.getY()) - pontoConexao;
                            yTerceiraLinha = (pai.getHeight() / 2) - pontoConexao;

                            linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, yPrimeiraLinha));
                            linhas.add(new XDimension(1, (yPrimeiraLinha < yTerceiraLinha ? yTerceiraLinha - yPrimeiraLinha : yPrimeiraLinha - yTerceiraLinha), this.getWidth() / 2, (yPrimeiraLinha < yTerceiraLinha ? yPrimeiraLinha : yTerceiraLinha)));
                            linhas.add(new XDimension(this.getWidth() / 2, 1, 0, yTerceiraLinha));
                        }
                    } else {
                        yPrimeiraLinha = (entidade.getHeight() / 2) + (entidade.getY() - this.getY());
                        yTerceiraLinha = pai.getHeight() / 2;
                        linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, yPrimeiraLinha));
                        linhas.add(new XDimension(1, (yPrimeiraLinha < yTerceiraLinha ? yTerceiraLinha - yPrimeiraLinha : yPrimeiraLinha - yTerceiraLinha), this.getWidth() / 2, (yPrimeiraLinha < yTerceiraLinha ? yPrimeiraLinha : yTerceiraLinha)));
                        linhas.add(new XDimension(this.getWidth() / 2, 1, 0, yTerceiraLinha));
                    }

                    this.cardinalidade.setX(10);
                    this.cardinalidade.setY(yTerceiraLinha - 5);

                } else if (this.acima == true) {
                    //Se for uma ligacao que possui uma 'irma', ou seja, uma outra ligacao
                    //a qual se deve manter distancia proporcional. Usado em auto-relacionamento
                    if (cacula != null) {
                        if (cacula) {
                            linhas.add(new XDimension((this.getWidth() / 2) - 10, 1, (this.getWidth() / 2) + 10, (entidade.getHeight() / 2) + pontoConexao));//Primeira linha, a partir da entidade
                            linhas.add(new XDimension(1, (this.getHeight() - (pai.getHeight() / 2)) - (entidade.getHeight() / 2), (this.getWidth() / 2) + 10, (entidade.getHeight() / 2) + pontoConexao));//Linha horizontal
                            linhas.add(new XDimension((this.getWidth() / 2) + 10, 1, 0, (this.getHeight() - (pai.getHeight() / 2) + pontoConexao))); //Terceira linha, a partir da entidade
                        } else {
                            linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, (entidade.getHeight() / 2) - pontoConexao));
                            linhas.add(new XDimension(1, (this.getHeight() - (pai.getHeight() / 2)) - (entidade.getHeight() / 2), this.getWidth() / 2, (entidade.getHeight() / 2) - pontoConexao));
                            linhas.add(new XDimension(this.getWidth() / 2, 1, 0, (this.getHeight() - (pai.getHeight() / 2)) - pontoConexao));
                        }
                    } else {
                        //Mudou a area? Recalcula.
                        if (ultimaArea != area || ultimoAcima != this.acima) {
                            yPrimeiraLinha = entidade.getHeight() / 2;
                            //Atualiza a 'ultimaArea' e 'ultimoAcima'
                            ultimaArea = area;
                            ultimoAcima = this.acima;
                            ultimoHeight = this.getHeight();
                        }
                        linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, yPrimeiraLinha));
                        linhas.add(new XDimension(1, (this.getHeight() - (pai.getHeight() / 2)) - yPrimeiraLinha, this.getWidth() / 2, yPrimeiraLinha));
                        linhas.add(new XDimension(this.getWidth() / 2, 1, 0, this.getHeight() - (pai.getHeight() / 2)));
                    }

                    this.cardinalidade.setX(10);
                    this.cardinalidade.setY(this.getHeight() - (pai.getHeight() / 2) - 5);
                } else if (this.acima == false) {

                    //Se for uma ligacao que possui uma 'irma', ou seja, uma outra ligacao
                    //a qual se deve manter distancia proporcional. Usado em auto-relacionamento
                    if (cacula != null) {
                        if (cacula) {
                            linhas.add(new XDimension((this.getWidth() / 2) + 10, 1, (this.getWidth() / 2) - 10, (this.getHeight() - (entidade.getHeight() / 2) + pontoConexao)));//Primeira linha, partindo da entidade
                            linhas.add(new XDimension(1, (this.getHeight() - (pai.getHeight() / 2)) - (entidade.getHeight() / 2), (this.getWidth() / 2) - 10, (pai.getHeight() / 2) + pontoConexao));//Linha horizontal
                            linhas.add(new XDimension((this.getWidth() / 2) - 10, 1, 0, (pai.getHeight() / 2) + pontoConexao)); //Terceira linha, partindo da entidade
                        } else {
                            linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, (this.getHeight() - (entidade.getHeight() / 2) - pontoConexao)));
                            linhas.add(new XDimension(1, (this.getHeight() - (pai.getHeight() / 2)) - (entidade.getHeight() / 2), this.getWidth() / 2, (pai.getHeight() / 2) - pontoConexao));
                            linhas.add(new XDimension(this.getWidth() / 2, 1, 0, (pai.getHeight() / 2) - pontoConexao));
                        }
                    } else {
                        //Mudou a area? Recalcula.
                        if (ultimaArea != area || ultimoAcima != this.acima) {
                            yPrimeiraLinha = this.getHeight() - (entidade.getHeight() / 2);
                            //Atualiza a 'ultimaArea' e 'ultimoAcima'
                            ultimaArea = area;
                            ultimoAcima = this.acima;
                            ultimoHeight = this.getHeight();
                        } else {
                            //Primeiro, descobrir o tanto que aumentou
                            int y = this.getHeight() - this.ultimoHeight;
                            //Incrementa a diferenca. Se a diferenca for negativa, sera subtraida
                            yPrimeiraLinha += y;
                            this.ultimoHeight = this.getHeight();
                        }

                        linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, yPrimeiraLinha));
                        linhas.add(new XDimension(1, (yPrimeiraLinha - (pai.getHeight() / 2)), this.getWidth() / 2, pai.getHeight() / 2));
                        linhas.add(new XDimension(this.getWidth() / 2, 1, 0, pai.getHeight() / 2));
                    }

                    this.cardinalidade.setX(10);
                    this.cardinalidade.setY((pai.getHeight() / 2) - 5);
                }
            }
            break;
            case 3: {
                if (!this.esquerda) {

                    int xTerceiraLinha = 0;

                    //Se for uma ligacao que possui uma 'irma', ou seja, uma outra ligacao
                    //a qual se deve manter distancia proporcional. Usado em auto-relacionamento
                    if (cacula != null) {
                        if (cacula) {
                            //Se for a linha 'cacula' (leia "cassula", a mais nova), possui desvio de 10 pixels nas linhas
                            //para nao coincidir a sua irma.
                            xPrimeiraLinha = (this.getWidth() - (entidade.getWidth() / 2)) - pontoConexao;
                            xTerceiraLinha = (pai.getWidth() / 2) - pontoConexao;

                            linhas.add(new XDimension(1, (this.getHeight() / 2) - 10, xPrimeiraLinha, (this.getHeight() / 2) + 10)); //Primeira linha, a partir da entidade
                            linhas.add(new XDimension(xPrimeiraLinha - xTerceiraLinha, 1, xTerceiraLinha, (this.getHeight() / 2) + 10)); //linha horizontal
                            linhas.add(new XDimension(1, (this.getHeight() / 2) + 10, xTerceiraLinha, 0)); //Terceira linha, a partir da entidade
                        } else {
                            //Posicoes padroes
                            xPrimeiraLinha = (this.getWidth() - (entidade.getWidth() / 2)) + pontoConexao;
                            xTerceiraLinha = (pai.getWidth() / 2) + pontoConexao;

                            linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, this.getHeight() / 2));
                            linhas.add(new XDimension(xPrimeiraLinha - xTerceiraLinha, 1, xTerceiraLinha, this.getHeight() / 2));
                            linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, 0));
                        }
                    } //Se nao possui, as posicoes volta para o padrao
                    else {
                        //Mudou a area? Recalcula.
                        if (ultimaArea != area || ultimoEsquerda != this.esquerda) {
                            xPrimeiraLinha = this.getWidth() - (entidade.getWidth() / 2);
                            //Atualiza a 'ultimaArea' e 'ultimaEsquerda'
                            ultimaArea = area;
                            ultimoEsquerda = this.esquerda;
                            ultimoWidth = this.getWidth();
                        } else {
                            int x = ultimoWidth - xPrimeiraLinha;
                            xPrimeiraLinha = this.getWidth() - x;
                            ultimoWidth = this.getWidth();
                        }

                        xTerceiraLinha = pai.getWidth() / 2;

                        linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, this.getHeight() / 2));
                        linhas.add(new XDimension(xPrimeiraLinha - xTerceiraLinha, 1, xTerceiraLinha, this.getHeight() / 2));
                        linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, 0));
                    }

                    this.cardinalidade.setX(xTerceiraLinha - this.cardinalidade.getW() - 3);
                    this.cardinalidade.setY(this.cardinalidade.getH());
                } else {

                    int xTerceiraLinha = 0;

                    //Se for uma ligacao que possui uma 'irma', ou seja, uma outra ligacao
                    //a qual se deve manter distancia proporcional. Usado em auto-relacionamento
                    if (cacula != null) {
                        if (cacula) {
                            //Se for a linha 'cacula' (leia "cassula", a mais nova), possui desvio de 10 pixels nas linhas
                            //para nao coincidir a sua irma.
                            xPrimeiraLinha = (entidade.getWidth() / 2) + pontoConexao;
                            xTerceiraLinha = (this.getWidth() - (pai.getWidth() / 2)) + pontoConexao;

                            linhas.add(new XDimension(1, (this.getHeight() / 2) - 10, xPrimeiraLinha, (this.getHeight() / 2) + 10)); //Primeira linha, a partir da entidade
                            linhas.add(new XDimension(xTerceiraLinha - xPrimeiraLinha, 1, xPrimeiraLinha, (this.getHeight() / 2) + 10)); //Linha horizontal
                            linhas.add(new XDimension(1, (this.getHeight() / 2) + 10, xTerceiraLinha, 0)); //Terceira linha, a partir da entidade
                        } else {
                            //Posicoes padroes
                            xPrimeiraLinha = (entidade.getWidth() / 2) - pontoConexao;
                            xTerceiraLinha = (this.getWidth() - (pai.getWidth() / 2)) - pontoConexao;

                            linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, this.getHeight() / 2));
                            linhas.add(new XDimension(xTerceiraLinha - xPrimeiraLinha, 1, xPrimeiraLinha, this.getHeight() / 2));
                            linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, 0));
                        }
                    } //Se nao possui, as posicoes volta para o padrao
                    else {
                        //Mudou a area? Recalcula.
                        if (ultimaArea != area || ultimoEsquerda != this.esquerda) {
                            xPrimeiraLinha = entidade.getWidth() / 2;
                            //Atualiza a 'ultimaArea' e 'ultimaEsquerda'
                            ultimaArea = area;
                            ultimoEsquerda = this.esquerda;
                        }

                        xTerceiraLinha = this.getWidth() - (pai.getWidth() / 2);

                        linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, this.getHeight() / 2));
                        linhas.add(new XDimension(xTerceiraLinha - xPrimeiraLinha, 1, xPrimeiraLinha, this.getHeight() / 2));
                        linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, 0));
                    }

                    this.cardinalidade.setX(xTerceiraLinha + 3);
                    this.cardinalidade.setY(this.cardinalidade.getH());
                }
            }
            break;
            case 4: {
                if (this.acima == null) {

                    int yTerceiraLinha = 0;

                    //Se for uma ligacao que possui uma 'irma', ou seja, uma outra ligacao
                    //a qual se deve manter distancia proporcional. Usado em auto-relacionamento
                    if (cacula != null) {
                        if (cacula) {
                            yPrimeiraLinha = ((entidade.getHeight() / 2) + (entidade.getY() - this.getY())) - pontoConexao;
                            yTerceiraLinha = (pai.getHeight() / 2) - pontoConexao;

                            linhas.add(new XDimension((this.getWidth() / 2) + 10, 1, 0, yPrimeiraLinha));
                            linhas.add(new XDimension(1, (yPrimeiraLinha < yTerceiraLinha ? yTerceiraLinha - yPrimeiraLinha : yPrimeiraLinha - yTerceiraLinha), (this.getWidth() / 2) + 10, (yPrimeiraLinha < yTerceiraLinha ? yPrimeiraLinha : yTerceiraLinha)));
                            linhas.add(new XDimension((this.getWidth() / 2) - 10, 1, (this.getWidth() / 2) + 10, yTerceiraLinha));
                        } else {
                            yPrimeiraLinha = ((entidade.getHeight() / 2) + (entidade.getY() - this.getY())) + pontoConexao;
                            yTerceiraLinha = (pai.getHeight() / 2) + pontoConexao;

                            linhas.add(new XDimension(this.getWidth() / 2, 1, 0, yPrimeiraLinha));
                            linhas.add(new XDimension(1, (yPrimeiraLinha < yTerceiraLinha ? yTerceiraLinha - yPrimeiraLinha : yPrimeiraLinha - yTerceiraLinha), this.getWidth() / 2, (yPrimeiraLinha < yTerceiraLinha ? yPrimeiraLinha : yTerceiraLinha)));
                            linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, yTerceiraLinha));
                        }
                    } else {

                        yPrimeiraLinha = (entidade.getHeight() / 2) + (entidade.getY() - this.getY());
                        yTerceiraLinha = pai.getHeight() / 2;

                        linhas.add(new XDimension(this.getWidth() / 2, 1, 0, yPrimeiraLinha));
                        linhas.add(new XDimension(1, (yPrimeiraLinha < yTerceiraLinha ? yTerceiraLinha - yPrimeiraLinha : yPrimeiraLinha - yTerceiraLinha), this.getWidth() / 2, (yPrimeiraLinha < yTerceiraLinha ? yPrimeiraLinha : yTerceiraLinha)));
                        linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, yTerceiraLinha));
                    }

                    this.cardinalidade.setX(this.getWidth() - this.cardinalidade.getW() - 10);
                    this.cardinalidade.setY(yTerceiraLinha - 5);

                } else if (this.acima == true) {

                    //Se for uma ligacao que possui uma 'irma', ou seja, uma outra ligacao
                    //a qual se deve manter distancia proporcional. Usado em auto-relacionamento
                    if (cacula != null) {
                        if (cacula) {
                            linhas.add(new XDimension((this.getWidth() / 2) - 10, 1, 0, (entidade.getHeight() / 2) + pontoConexao)); //Primeira linha, a partir da entidade
                            linhas.add(new XDimension(1, (this.getHeight() - (pai.getHeight() / 2)) - (entidade.getHeight() / 2), (this.getWidth() / 2) - 10, (entidade.getHeight() / 2) + pontoConexao));//Linha horizontal
                            linhas.add(new XDimension((this.getWidth() / 2) + 10, 1, (this.getWidth() / 2) - 10, (this.getHeight() - (pai.getHeight() / 2) + pontoConexao)));//Terceira linha, a partir da entidade
                        } else {
                            linhas.add(new XDimension(this.getWidth() / 2, 1, 0, (entidade.getHeight() / 2) - pontoConexao));
                            linhas.add(new XDimension(1, (this.getHeight() - (pai.getHeight() / 2)) - (entidade.getHeight() / 2), this.getWidth() / 2, (entidade.getHeight() / 2) - pontoConexao));
                            linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, (this.getHeight() - (pai.getHeight() / 2)) - pontoConexao));
                        }
                    } else {
                        //Mudou a area? Recalcula.
                        if (ultimaArea != area || ultimoAcima != this.acima) {
                            yPrimeiraLinha = entidade.getHeight() / 2;
                            //Atualiza a 'ultimaArea' e 'ultimoAcima'
                            ultimaArea = area;
                            ultimoAcima = this.acima;
                            ultimoHeight = this.getHeight();
                        }

                        linhas.add(new XDimension(this.getWidth() / 2, 1, 0, yPrimeiraLinha));
                        linhas.add(new XDimension(1, (this.getHeight() - (pai.getHeight() / 2)) - yPrimeiraLinha, this.getWidth() / 2, yPrimeiraLinha));
                        linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, this.getHeight() - (pai.getHeight() / 2)));
                    }

                    this.cardinalidade.setX(this.getWidth() - this.cardinalidade.getW() - 10);
                    this.cardinalidade.setY(this.getHeight() - (pai.getHeight() / 2) - 5);

                } else if (this.acima == false) {

                    //Se for uma ligacao que possui uma 'irma', ou seja, uma outra ligacao
                    //a qual se deve manter distancia proporcional. Usado em auto-relacionamento
                    if (cacula != null) {
                        if (cacula) {
                            linhas.add(new XDimension((this.getWidth() / 2) + 10, 1, 0, (this.getHeight() - (entidade.getHeight() / 2) + pontoConexao))); //Primeira linha, a partir da entidade
                            linhas.add(new XDimension(1, (this.getHeight() - (pai.getHeight() / 2)) - (entidade.getHeight() / 2), (this.getWidth() / 2) + 10, (pai.getHeight() / 2) + pontoConexao));//Linha horizontal
                            linhas.add(new XDimension((this.getWidth() / 2) - 10, 1, (this.getWidth() / 2) + 10, (pai.getHeight() / 2) + pontoConexao)); //Terceira linha, a partir da entidade
                        } else {
                            linhas.add(new XDimension(this.getWidth() / 2, 1, 0, (this.getHeight() - (entidade.getHeight() / 2) - pontoConexao)));
                            linhas.add(new XDimension(1, (this.getHeight() - (pai.getHeight() / 2)) - (entidade.getHeight() / 2), this.getWidth() / 2, (pai.getHeight() / 2) - pontoConexao));
                            linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, (pai.getHeight() / 2) - pontoConexao));
                        }
                    } else {
                        //Mudou a area? Recalcula.
                        if (ultimaArea != area || ultimoAcima != this.acima) {
                            yPrimeiraLinha = this.getHeight() - (entidade.getHeight() / 2);
                            //Atualiza a 'ultimaArea' e 'ultimoAcima'
                            ultimaArea = area;
                            ultimoAcima = this.acima;
                            ultimoHeight = this.getHeight();
                        } else {
                            //Primeiro, descobrir o tanto que aumentou
                            int y = this.getHeight() - this.ultimoHeight;
                            //Incrementa a diferenca. Se a diferenca for negativa, sera subtraida
                            yPrimeiraLinha += y;
                            this.ultimoHeight = this.getHeight();
                        }
                        linhas.add(new XDimension(this.getWidth() / 2, 1, 0, yPrimeiraLinha));
                        linhas.add(new XDimension(1, (yPrimeiraLinha - (pai.getHeight() / 2)), this.getWidth() / 2, pai.getHeight() / 2));
                        linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, pai.getHeight() / 2));
                    }

                    this.cardinalidade.setX(this.getWidth() - this.cardinalidade.getW() - 10);
                    this.cardinalidade.setY((pai.getHeight() / 2) - 5);
                }
            }
            break;
        }
    }

    public void mouseHover(Point p) {
        //Resolvendo cardinalidade
        {
            if (p.getX() - this.getX() > this.cardinalidade.getX() && p.getY() - this.getY() < this.cardinalidade.getY() + 5 && p.getX() - this.getX() < (this.cardinalidade.getX() + this.cardinalidade.getW()) && p.getY() - this.getY() - 5 > (this.cardinalidade.getY() - this.cardinalidade.getH())) {
                this.sobreCardinalidade = true;
                this.repaint();
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setToolTipText(Model.lang.getString("submenuLigacaoTooltip"));
            } else {
                this.sobreCardinalidade = false;
                this.repaint();
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                setToolTipText(null);
            }
        }
        //Resolvendo primeira linha
        {
            switch (this.area) {
                case 1: {
                    if (((p.getX() - this.getX()) > this.linhas.get(0).getX() - 5 && (p.getX() - this.getX()) < this.linhas.get(0).getX() + 5) && (p.getY() - this.getY() <= this.linhas.get(0).getHeight())) {
                        this.sobrePrimeiraLinha = true;
                        this.repaint();
                        setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                    } else {
                        this.sobrePrimeiraLinha = false;
                        this.repaint();
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
                break;
                case 2: {
                    if (((p.getY() - this.getY()) > this.linhas.get(0).getY() - 5 && (p.getY() - this.getY()) < this.linhas.get(0).getY() + 5) && (p.getX() - this.getX() > this.linhas.get(0).getX())) {
                        this.sobrePrimeiraLinha = true;
                        this.repaint();
                        setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                    } else {
                        this.sobrePrimeiraLinha = false;
                        this.repaint();
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
                break;
                case 3: {
                    if (((p.getX() - this.getX()) > this.linhas.get(0).getX() - 5 && (p.getX() - this.getX()) < this.linhas.get(0).getX() + 5) && (p.getY() - this.getY() >= this.linhas.get(0).getY())) {
                        this.sobrePrimeiraLinha = true;
                        this.repaint();
                        setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                    } else {
                        this.sobrePrimeiraLinha = false;
                        this.repaint();
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
                break;
                case 4: {
                    if (((p.getY() - this.getY()) > this.linhas.get(0).getY() - 5 && (p.getY() - this.getY()) < this.linhas.get(0).getY() + 5) && (p.getX() - this.getX() < this.linhas.get(0).getWidth())) {
                        this.sobrePrimeiraLinha = true;
                        this.repaint();
                        setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                    } else {
                        this.sobrePrimeiraLinha = false;
                        this.repaint();
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            }
        }
    }

    /**
     * Metodo que identifica a cor do pixel e informa se eh preto
     *
     * @param x
     * @param y
     * @return
     */
    private boolean identificaCor(int x, int y) {

        BufferedImage imagem = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = imagem.createGraphics();
        this.paintAll(graphics);
        graphics.dispose();

        if (imagem.getRGB(x, y) == -11776948) {
            return true;
        }

        return false;
    }
}