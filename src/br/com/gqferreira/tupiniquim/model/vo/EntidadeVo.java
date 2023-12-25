package br.com.gqferreira.tupiniquim.model.vo;

import br.com.gqferreira.tupiniquim.model.XDimension;

/**
 * Classe para criacao de objeto de valor para Entidade.
 * @author Gustavo Ferreira
 */
public class EntidadeVo extends ComponenteVo{
    public enum Tipo{
        COMUM, FRACA, ASSOCIATIVA
    }
    
    private String nome;
    private AtributoVo[] atributos;    
    private Tipo tipo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public AtributoVo[] getAtributos() {
        return atributos;
    }

    public void setAtributos(AtributoVo[] atributos) {
        this.atributos = atributos;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public XDimension getXdimension() {
        return xdimension;
    }

    public void setXdimension(XDimension xdimension) {
        this.xdimension = xdimension;
    }

    public EntidadeVo(String nome, AtributoVo[] atributos, Tipo tipo, XDimension xdimension) {
        this.nome = nome;
        this.atributos = atributos;
        this.tipo = tipo;
        this.xdimension = xdimension;
    }
}
