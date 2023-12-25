/*
 * To change component2 template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.view;

import br.com.gqferreira.tupiniquim.model.XDimension;

/**
 * Classe que representa graficamente a ligacao entre dois componentes
 *
 * @author Gustavo Ferreira
 */
public class LigacaoRelacionamentoAtributo extends Ligacao {

    private static final int pontoConexao = 20;
    //Usado para casos excepcionais onde o atributo eh maior que o tamanho
    //do painel de ligacao, outra estrategia serah tomada
    private boolean atributoGrande = false;

    public LigacaoRelacionamentoAtributo(Componente pai, Componente atributo) {
        super(pai, atributo);
    }

    @Override
    protected void calcularPosicaoTamanhoLigacao(XDimension pai, XDimension atributo, Componente atrObj) {
        /* 
         * Eh preciso atentar-se ao fato de o atributo estar por cima ou por baixo
         * do atributo. Se o componente se interseccionar, nao cria a "linha"
         */

        atributoGrande = false;
        boolean interseccao = false;
        if ((atributo.getX() >= pai.getX() && atributo.getX() <= (pai.getX() + pai.getWidth())) || ((atributo.getX() + atributo.getWidth()) >= pai.getX() && (atributo.getX() + atributo.getWidth()) <= (pai.getX() + pai.getWidth()))) {
            //Interseccionou pelo X
            if ((atributo.getY() >= pai.getY() && atributo.getY() <= (pai.getY() + pai.getHeight())) || ((atributo.getY() + atributo.getHeight()) >= pai.getY() && (atributo.getY() + atributo.getHeight()) <= (pai.getY() + pai.getHeight()))) {
                //Interseccionou pelo Y
                interseccao = true;
                System.out.println("Interseccao");
            }
        }
        if (!interseccao) {

            int x = 0, y = 0, w = 0, h = 0;
            //Esse tipo de ligacao so permite quatro areas
            //Verificando se eh a area 1
            if (atributo.getY() + atributo.getHeight() < pai.getY()) {

                //AREA 1
                this.area = 1;
                int acrescimento = (pontoConexao * (pai.getHeight() / 2)) / (pai.getWidth() / 2);

                if ((atributo.getX() + atributo.getWidth() - 8) > (pai.getX() + (pai.getWidth() / 2)) + pontoConexao) {
                    
                    //O circulo esta a esquerda
                    ((Atributo) this.filho).esquerda = true;
                    this.esquerda = true;
                    //Verificando se o atributo eh muito grante para comportar a linha de ligacao
                    if (atributo.getX() < pai.getX() && atributo.getX() + atributo.getWidth() > pai.getX() + pai.getWidth() / 2 - pontoConexao) {
                        x = atributo.getX();
                        w = atributo.getX() + atributo.getWidth() - x;
                        y = atributo.getY() + atributo.getHeight();
                        h = pai.getY() - y + acrescimento;
                        atributoGrande = true;
                    } else {
                        x = pai.getX();
                        w = (atributo.getX() + atributo.getWidth()) - pai.getX();
                        y = atributo.getY() + atributo.getHeight();
                        h = pai.getY() - y + acrescimento;
                    }

                } else {
                    //Esta na direita
                    ((Atributo) this.filho).esquerda = false;
                    this.esquerda = false;

                    x = atributo.getX();
                    w = (pai.getX() + pai.getWidth()) - x;
                    y = atributo.getY() + atributo.getHeight();
                    h = pai.getY() - y + acrescimento;

                }
            } else if (atributo.getY() > (pai.getY() + pai.getHeight())) {
                //AREA 3
                this.area = 3;
                int acrescimento = (pontoConexao * (pai.getHeight() / 2)) / (pai.getWidth() / 2);

                //Agora deve-se verificar se o atributo esta a esquerda ou a direita
                //O ponto de conexao no relacionamento eh o centro mais 10 pixels                
                if (atributo.getX() + atributo.getWidth() - 8 > pai.getX() + (pai.getWidth() / 2) - pontoConexao) {
                    //Esta na esquerda do relacionamento
                    ((Atributo) this.filho).esquerda = true;
                    this.esquerda = true;
                    //Verificando se o atributo eh muito grante para comportar a linha de ligacao
                    if (atributo.getX() < pai.getX() && atributo.getX() + atributo.getWidth() > pai.getX() + pai.getWidth() / 2 - pontoConexao) {
                        x = atributo.getX();
                        w = atributo.getX() + atributo.getWidth() - x;
                        y = pai.getY() + pai.getHeight() - acrescimento;
                        h = atributo.getY() - y;
                        atributoGrande = true;
                    } else {
                        x = pai.getX();
                        w = atributo.getX() + atributo.getWidth() - x;
                        y = pai.getY() + pai.getHeight() - acrescimento;
                        h = atributo.getY() - y;
                    }
                } else {
                    //Esta na direita
                    ((Atributo) this.filho).esquerda = false;
                    this.esquerda = false;

                    x = atributo.getX();
                    w = pai.getX() + pai.getWidth() - x;
                    y = pai.getY() + pai.getHeight() - acrescimento;
                    h = atributo.getY() - y;
                }

            } else if (atributo.getX() + atributo.getWidth() < pai.getX()) {
                //AREA 4
                this.area = 4;
                int acrescimento = (pontoConexao * (pai.getWidth() / 2)) / (pai.getHeight() / 2);
                ((Atributo) this.filho).esquerda = false;
                this.esquerda = false;
                //Primeiro deve se verificar se o atributo encontra-se acima
                //ou abaixo do ponto de conexao
                if (atributo.getY() + atributo.getHeight() - 8 < (pai.getY() + (pai.getHeight() / 2) - pontoConexao)) {
                    this.acima = true;
                    x = atributo.getX() + atributo.getWidth();
                    w = pai.getX() - x + acrescimento;
                    y = atributo.getY();
                    h = (pai.getY() + pai.getHeight()) - y;
                } else {
                    //Encontra-se abaixo
                    this.acima = false;
                    x = atributo.getX() + atributo.getWidth();
                    w = pai.getX() - x + acrescimento;
                    y = pai.getY();
                    h = atributo.getY() + atributo.getHeight() - pai.getY();
                }
            } else if (atributo.getX() > pai.getX() + pai.getWidth()) {
                //AREA 2
                this.area = 2;
                int acrescimento = (pontoConexao * (pai.getWidth() / 2)) / (pai.getHeight() / 2);

                ((Atributo) this.filho).esquerda = true;
                this.esquerda = false;
                //Primeiro deve se verificar se o atributo encontra-se acima
                //ou abaixo do ponto de conexao
                if (atributo.getY() + atributo.getHeight() - 8 <= (pai.getY() + (pai.getHeight() / 2) + pontoConexao)) {
                    this.acima = true;
                    x = pai.getX() + pai.getWidth() - acrescimento;
                    w = (atributo.getX()) - x;
                    y = atributo.getY();
                    h = (pai.getY() + pai.getHeight()) - y;
                } else {
                    this.acima = false;
                    x = pai.getX() + pai.getWidth() - acrescimento;
                    w = (atributo.getX()) - x;
                    y = pai.getY();
                    h = atributo.getY() + atributo.getHeight() - y;
                }
            } else {
                System.out.println("NULL");
            }
            this.setBounds(x, y, w, h);
            ((Atributo) this.filho).repaint();
        }
    }

    @Override
    protected void calcularLinha(XDimension pai, XDimension atributo) {

        this.linhas.clear();
        switch (this.area) {
            case 1: {

                if (this.esquerda) {
                    int xPrimeiraLinha = this.getWidth() - atributo.getWidth() + 8;
                    int xTerceiraLinha = pai.getWidth() / 2 + pontoConexao;
                    
                    if (atributoGrande) {
                        xPrimeiraLinha = 8;
                        xTerceiraLinha = (pai.getX() + (pai.getWidth() / 2) + pontoConexao) - this.getX() + 1;

                        //Aumenta para direita
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, 0));
                        this.linhas.add(new XDimension((xTerceiraLinha) - (xPrimeiraLinha) + 1, 1, xPrimeiraLinha, this.getHeight() / 2));
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, this.getHeight()/2));
                    }//Definindo se a linha do meio (horizontal) deve aumentar para a direita ou para esquerda
                    else if (xPrimeiraLinha > xTerceiraLinha) {
                        //Aumenta para a direita
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, 0));
                        this.linhas.add(new XDimension((xPrimeiraLinha) - (xTerceiraLinha) + 1, 1, (pai.getWidth() / 2) + pontoConexao, this.getHeight() / 2));
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, this.getHeight() / 2));
                    } else {
                        //Aumenta para esquerda
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, 0));
                        this.linhas.add(new XDimension((xTerceiraLinha) - (xPrimeiraLinha) + 1, 1, xPrimeiraLinha, this.getHeight() / 2));
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, this.getHeight() / 2));
                    }
                } else {
                    int xPrimeiraLinha = atributo.getWidth() - 8;
                    int xTerceiraLinha = this.getWidth() - (pai.getWidth() / 2) + pontoConexao;

                    //Aumenta para direita
                    this.linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, 0));
                    this.linhas.add(new XDimension((xTerceiraLinha) - (xPrimeiraLinha) + 1, 1, xPrimeiraLinha, this.getHeight() / 2));
                    this.linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, this.getHeight() / 2));
                }
            }
            break;
            case 2: {
                if (this.acima) {
                    this.linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, 8));
                    this.linhas.add(new XDimension(1, this.getHeight() - (pai.getHeight() / 2) + pontoConexao - 8 + 1, this.getWidth() / 2, 8));
                    this.linhas.add(new XDimension(this.getWidth() / 2, 1, 0, this.getHeight() - (pai.getHeight() / 2) + pontoConexao));
                } else {
                    this.linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, this.getHeight() - 8));
                    this.linhas.add(new XDimension(1, (this.getHeight() - 8) - (pai.getHeight() / 2 + pontoConexao), this.getWidth() / 2, pai.getHeight() / 2 + pontoConexao));
                    this.linhas.add(new XDimension(this.getWidth() / 2, 1, 0, pai.getHeight() / 2 + pontoConexao));
                }
            }
            break;
            case 3: {
                if (this.esquerda) {
                    int xPrimeiraLinha = this.getWidth() - atributo.getWidth() + 8;
                    int xTerceiraLinha = pai.getWidth() / 2 - pontoConexao;

                    if (atributoGrande) {
                        xPrimeiraLinha = 8;
                        xTerceiraLinha = (pai.getX() + (pai.getWidth() / 2) - pontoConexao) - this.getX() + 1;

                        //Aumenta para direita
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, this.getHeight() / 2));
                        this.linhas.add(new XDimension((xTerceiraLinha) - (xPrimeiraLinha) + 1, 1, xPrimeiraLinha, this.getHeight() / 2));
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, 0));
                    } //Definindo se a linha do meio (horizontal) deve aumentar para a direita ou para esquerda
                    else if (xPrimeiraLinha > xTerceiraLinha) {
                        //Aumenta para a direita
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, this.getHeight() / 2));
                        this.linhas.add(new XDimension((xPrimeiraLinha) - (xTerceiraLinha) + 1, 1, (pai.getWidth() / 2) - pontoConexao, this.getHeight() / 2));
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, 0));
                    } else {
                        //Aumenta para esquerda
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, atributo.getX() - pai.getX() + 8, this.getHeight() / 2));
                        this.linhas.add(new XDimension((xTerceiraLinha) - (xPrimeiraLinha) + 1, 1, xPrimeiraLinha, this.getHeight() / 2));
                        this.linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, 0));
                    }
                } else {
                    int xPrimeiraLinha = atributo.getWidth() - 8;
                    int xTerceiraLinha = this.getWidth() - (pai.getWidth() / 2) - pontoConexao;

                    //Aumenta para direita
                    this.linhas.add(new XDimension(1, this.getHeight() / 2, xPrimeiraLinha, this.getHeight() / 2));
                    this.linhas.add(new XDimension((xTerceiraLinha) - (xPrimeiraLinha) + 1, 1, xPrimeiraLinha, this.getHeight() / 2));
                    this.linhas.add(new XDimension(1, this.getHeight() / 2, xTerceiraLinha, 0));
                }
            }
            break;
            case 4: {
                if (this.acima) {
                    this.linhas.add(new XDimension(this.getWidth() / 2, 1, 0, 8));
                    this.linhas.add(new XDimension(1, this.getHeight() - (pai.getHeight() / 2) - pontoConexao - 8 + 1, this.getWidth() / 2, 8));
                    this.linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, this.getHeight() - (pai.getHeight() / 2) - pontoConexao));
                } else {
                    int yPrimeiraLinha = this.getHeight() - 8;
                    int yTerceiraLinha = (pai.getY() + (pai.getHeight() / 2)) - this.getY() - pontoConexao;
                    this.linhas.add(new XDimension(this.getWidth() / 2, 1, 0, yPrimeiraLinha));
                    this.linhas.add(new XDimension(1, yPrimeiraLinha - yTerceiraLinha, this.getWidth() / 2, (pai.getY() + (pai.getHeight() / 2)) - this.getY() - pontoConexao));
                    this.linhas.add(new XDimension(this.getWidth() / 2, 1, this.getWidth() / 2, yTerceiraLinha));
                }
            }
            break;
        }
    }
}