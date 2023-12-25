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
public class LigacaoAtributo extends Ligacao {

    public LigacaoAtributo(Componente pai, Componente atributo) {
       super(pai, atributo);
    }

    /**
     * Retorna o atributo que esta acondicionado nesse objeto
     * @return Atributo
     */
    public Atributo getAtributo(){
        return (Atributo)this.filho;
    }
    

    @Override
    protected void calcularPosicaoTamanhoLigacao(XDimension pai, XDimension atributo, Componente atrObj) {
        /* 
         * Eh preciso atentar-se ao fato de o atributo estar por cima ou por baixo
         * do atributo. Se o componente se interseccionar, nao cria a "linha"
         */

        boolean interseccao = false;
        if ((atributo.getX() >= pai.getX() && atributo.getX() <= (pai.getX() + pai.getWidth())) || ((atributo.getX() + atributo.getWidth()) >= pai.getX() && (atributo.getX() + atributo.getWidth()) <= (pai.getX() + pai.getWidth()))) {
            //Interseccionou pelo X
            if ((atributo.getY() >= pai.getY() && atributo.getY() <= (pai.getY() + pai.getHeight())) || ((atributo.getY() + atributo.getHeight()) >= pai.getY() && (atributo.getY() + atributo.getHeight()) <= (pai.getY() + pai.getHeight()))) {
                //Interseccionou pelo Y
                interseccao = true;
            }
        }
        if (!interseccao) {

            int x = 0, y = 0, w = 0, h = 0;

            // 1 - Verificar se o atributo esta na area 1 em relacao a entidade
            if (((atributo.getX() + atributo.getWidth()) <= pai.getX() + Atributo.MARGEM_LIGACAO)
                    && ((atributo.getY() + atributo.getHeight()) < pai.getY())) {

                //AREA 1
                this.area = 1;
                x = atributo.getX() + atributo.getWidth();
                y = atributo.getY();
                w = (pai.getX() + pai.getWidth()) - x;
                h = pai.getY() - atributo.getY();

                ((Atributo)atrObj).setEsquerda(false);
                this.esquerda = false;
            } // 2 - Verificar se o atributo esta na area 2 em relacao a entidade
            else if (((atributo.getX() + atributo.getWidth()) > pai.getX() + Atributo.MARGEM_LIGACAO)
                    && (atributo.getX() < pai.getX() + pai.getWidth() - Atributo.MARGEM_LIGACAO)
                    && ((atributo.getY() + atributo.getHeight()) < pai.getY())) {

                //AREA 2
                this.area = 2;
                x = atributo.getX();
                y = atributo.getY() + atributo.getHeight();
                w = (pai.getX() + pai.getWidth()) - atributo.getX();
                h = pai.getY() - (atributo.getY() + atributo.getHeight());

                if (atributo.getX() >= pai.getX()) {
                    ((Atributo)atrObj).setEsquerda(true);
                    this.esquerda = true;
                } else {
                    ((Atributo)atrObj).setEsquerda(false);
                    this.esquerda = false;
                }
            } // 5 - Verificar se o atributo esta na area 5 em relacao a entidade
            else if (((pai.getX() + pai.getWidth()) < atributo.getX())
                    && ((pai.getY() + pai.getHeight()) >= atributo.getY())
                    && ((atributo.getY()) >= pai.getY() - atributo.getHeight())) {

                //AREA 5
                this.area = 5;
                if (atributo.getY() > pai.getY()) {
                    //O 'y' do atributo esta menor que do pai. Signifca que ele esta
                    //desloca acima do pai.
                    y = pai.getY();
                } else {
                    y = atributo.getY();
                }

                x = pai.getX() + pai.getWidth();
                w = atributo.getX() - (pai.getX() + pai.getWidth());

                if (atributo.getY() + atributo.getHeight() < pai.getY() + pai.getHeight()) {
                    //O 'y' do atributo esta menor que o 'heigth-y' do pai. Significa que o atributo
                    //ainda nao ultrapassou o limite vertical do pai
                    h = (pai.getY() + pai.getHeight()) - y;
                } else {
                    h = ((pai.getY() + pai.getHeight()) - y) + atributo.getHeight();
                }

                ((Atributo)atrObj).setEsquerda(true);
                this.esquerda = true;
            } // 3 - Verificar se o atributo esta na area 3 em relacao a entidade
            else if (((pai.getX() + pai.getWidth()) <= atributo.getX() + Atributo.MARGEM_LIGACAO)
                    && ((atributo.getY() + atributo.getHeight()) < pai.getY())) {

                //AREA 3                
                this.area = 3;
                x = pai.getX();
                y = atributo.getY();
                w = atributo.getX() - pai.getX();
                h = pai.getY() - atributo.getY();

                ((Atributo)atrObj).setEsquerda(true);
                this.esquerda = true;
            } // 4 - Verificar se o atributo esta na area 4 em relacao a entidade
            else if (((atributo.getX() + atributo.getWidth()) < pai.getX())
                    && ((atributo.getY() + atributo.getHeight()) >= pai.getY())
                    && ((atributo.getY() + atributo.getHeight() < pai.getY() + pai.getHeight() + atributo.getHeight()))) {

                //AREA 4
                this.area = 4;
                if (atributo.getY() > pai.getY()) {
                    //O 'y' do atributo esta menor que do pai. Signifca que ele esta
                    //desloca acima do pai.
                    y = pai.getY();
                } else {
                    y = atributo.getY();
                }

                x = atributo.getX() + atributo.getWidth();
                w = pai.getX() - (atributo.getX() + atributo.getWidth());

                if (atributo.getY() + atributo.getHeight() < pai.getY() + pai.getHeight()) {
                    //O 'y' do atributo esta menor que o 'heigth-y' do pai. Significa que o atributo
                    //ainda nao ultrapassou o limite vertical do pai
                    h = (pai.getY() + pai.getHeight()) - y;
                } else {
                    h = ((pai.getY() + pai.getHeight()) - y) + atributo.getHeight();
                }

                ((Atributo)atrObj).setEsquerda(false);
                this.esquerda = false;
            } // 6 - Verificar se o atributo esta na area 6 em relacao a entidade
            else if (((atributo.getX() + atributo.getWidth()) <= pai.getX() + Atributo.MARGEM_LIGACAO)
                    && (atributo.getY() + atributo.getHeight() > pai.getY())) {

                //AREA 6
                this.area = 6;
                x = atributo.getX() + atributo.getWidth();
                y = pai.getY() + pai.getHeight();
                w = (pai.getX() + pai.getWidth()) - (atributo.getX() + atributo.getWidth());
                h = atributo.getY() - (pai.getY() + pai.getHeight()) + atributo.getHeight();

                ((Atributo)atrObj).setEsquerda(false);
                this.esquerda = false;
            } // 7 - Verificar se o atributo esta na area 7 em relacao a entidade
            else if (((atributo.getX() + atributo.getWidth()) > pai.getX() + Atributo.MARGEM_LIGACAO)
                    && (atributo.getX() < pai.getX() + pai.getWidth() - Atributo.MARGEM_LIGACAO)
                    && (atributo.getY() > (pai.getY() + pai.getHeight()))) {

                //AREA 7
                this.area = 7;
                x = atributo.getX();
                y = pai.getY() + pai.getHeight();
                w = (pai.getX() + pai.getWidth()) - atributo.getX();
                h = atributo.getY() - (pai.getY() + pai.getHeight());

                if (atributo.getX() >= pai.getX()) {
                    ((Atributo)atrObj).setEsquerda(true);
                    this.esquerda = true;
                } else {
                    ((Atributo)atrObj).setEsquerda(false);
                    this.esquerda = false;
                }
            } // 8 - Verificar se o atributo esta na area 8 em relacao a entidade
            else if (((pai.getX() + pai.getWidth()) <= atributo.getX() + Atributo.MARGEM_LIGACAO)
                    && (atributo.getY() > (pai.getY() + pai.getHeight()))) {

                //AREA 8
                this.area = 8;
                x = pai.getX();
                y = pai.getY() + pai.getHeight();
                w = atributo.getX() - pai.getX();
                h = (atributo.getY() + atributo.getHeight()) - (pai.getY() + pai.getHeight());

                ((Atributo)atrObj).setEsquerda(true);
                this.esquerda = true;
            } else {
                System.out.println("NULL");
            }
            this.setBounds(x, y, w, h);
            this.filho.repaint();            
        }
    }

    @Override
    protected void calcularLinha(XDimension pai, XDimension atributo) {        
        switch (this.area) {
            case 1: {
                this.linhas.clear();
                //Essa variavel deve ser trabalhada depois para nao bater em cima de outras linhas.
                int meioEntidade = this.getWidth()-(pai.getWidth()/2);
                this.linhas.add(new XDimension(meioEntidade, 1, 0, 8));
                this.linhas.add(new XDimension(1, this.getHeight()-8, meioEntidade, 8));
            }
            break;
            case 2: {
                this.linhas.clear();
                //Area 2 eh sempre linha reta
                //Deve se verificar se o atributo esta orientado a esquerda ou a direita.
                if (this.esquerda){
                    //Esta a esquerda                    
                    this.linhas.add(new XDimension(1, this.getHeight(), 8, 0));
                }
                else{
                    //Esta a direita
                    this.linhas.add(new XDimension(1, this.getHeight(), (atributo.getX()+atributo.getWidth()-8)-this.getX(), 0));
                }
            }
            break;
            case 3: {
                this.linhas.clear();
                //Essa variavel deve ser trabalhada depois para nao bater em cima de outras linhas.
                int meioEntidade = (pai.getWidth()/2);
                this.linhas.add(new XDimension(this.getWidth()-meioEntidade, 1, meioEntidade, 8));
                this.linhas.add(new XDimension(1, this.getHeight()-8, meioEntidade, 8));
            }
            break;
            case 4: {
                this.linhas.clear();
                if (atributo.getY() < pai.getY()) {
                    //O 'y' do atributo esta menor que do pai. Signifca que ele esta
                    //desloca acima do pai.
                    this.linhas.add(new XDimension(this.getWidth()/2, 1, 0, atributo.getHeight()/2));  
                    this.linhas.add(new XDimension(1, this.getHeight()/2, this.getWidth()/2, atributo.getHeight()/2));
                    this.linhas.add(new XDimension(this.getWidth()/2, 1, this.getWidth()/2, (atributo.getHeight()/2)+this.getHeight()/2));
                }
                else if (atributo.getY() + atributo.getHeight() > pai.getY() + pai.getHeight()) {
                    //O 'y' do atributo esta menor que o 'heigth-y' do pai. Significa que o atributo
                    //ainda nao ultrapassou o limite vertical do pai
                    this.linhas.add(new XDimension(this.getWidth()/2, 1, 0,  atributo.getY()+(atributo.getHeight()/2) - this.getY()));  
                    this.linhas.add(new XDimension(1, this.getHeight()/2, (this.getWidth()/2)-1, (atributo.getY()+(atributo.getHeight()/2) - this.getY())-this.getHeight()/2));  
                    this.linhas.add(new XDimension(this.getWidth()/2, 1, this.getWidth()/2, (atributo.getY()+(atributo.getHeight()/2) - this.getY())-this.getHeight()/2));  
                    
                }
                else{
                    //Nada, o atributo esta no meio
                    this.linhas.add(new XDimension(this.getWidth(), 1, 0, atributo.getY()+(atributo.getHeight()/2) - this.getY()));
                }
            }
            break;
            case 5: {
                this.linhas.clear();                
                if (atributo.getY() < pai.getY()) {
                    //O 'y' do atributo esta menor que do pai. Signifca que ele esta
                    //desloca acima do pai.                    
                    this.linhas.add(new XDimension(this.getWidth()/2, 1, this.getWidth()/2, atributo.getY()+(atributo.getHeight()/2) - this.getY()));                    
                    this.linhas.add(new XDimension(1, (this.getHeight()/2), this.getWidth()/2, atributo.getY()+(atributo.getHeight()/2) - this.getY()));
                    this.linhas.add(new XDimension(this.getWidth()/2, 1, 0, (atributo.getY()+(atributo.getHeight()/2) - this.getY()) + (this.getHeight()/2) - 1));
                }
                else if (atributo.getY() + atributo.getHeight() > pai.getY() + pai.getHeight()) {
                    //O 'y' do atributo esta menor que o 'heigth-y' do pai. Significa que o atributo
                    //ainda nao ultrapassou o limite vertical do pai
                    this.linhas.add(new XDimension(this.getWidth()/2, 1, this.getWidth()/2, atributo.getY()+(atributo.getHeight()/2) - this.getY()));                    
                    this.linhas.add(new XDimension(1, (this.getHeight()/2), this.getWidth()/2, (atributo.getY()+(atributo.getHeight()/2) - this.getY()) - this.getHeight()/2));
                    this.linhas.add(new XDimension(this.getWidth()/2, 1, 0, (atributo.getY()+(atributo.getHeight()/2) - this.getY()) - this.getHeight()/2));
                }
                else{
                    //Nada, o atributo esta no meio
                    this.linhas.add(new XDimension(this.getWidth(), 1, 0, atributo.getY()+(atributo.getHeight()/2) - this.getY()));
                }
            }
            break;
            case 6: {
                this.linhas.clear();
                //Essa variavel deve ser trabalhada depois para nao bater em cima de outras linhas.
                int meioEntidade = this.getWidth()-(pai.getWidth()/2);
                this.linhas.add(new XDimension(meioEntidade, 1, 0, this.getHeight()-8));
                this.linhas.add(new XDimension(1, this.getHeight()-8, meioEntidade-1, 0));
            }
            break;
            case 7: {
                this.linhas.clear();
                //Area 7 eh sempre linha reta
                //Deve se verificar se o atributo esta orientado a esquerda ou a direita.
                if (this.esquerda){
                    //Esta a esquerda                    
                    this.linhas.add(new XDimension(1, this.getHeight(), 8, 0));
                }
                else{
                    //Esta a direita
                    this.linhas.add(new XDimension(1, this.getHeight(), (atributo.getX()+atributo.getWidth()-8)-this.getX(), 0));
                }
            }
            break;
            case 8: {
                this.linhas.clear();
                //Essa variavel deve ser trabalhada depois para nao bater em cima de outras linhas.
                int meioEntidade = (pai.getWidth()/2);
                this.linhas.add(new XDimension(this.getWidth()-meioEntidade, 1, meioEntidade, this.getHeight()-8));
                this.linhas.add(new XDimension(1, this.getHeight()-8, meioEntidade, 0));
            }
            break;
        }
    }
}