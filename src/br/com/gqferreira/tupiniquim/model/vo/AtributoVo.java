package br.com.gqferreira.tupiniquim.model.vo;

import br.com.gqferreira.tupiniquim.model.XDimension;

/**
 * Classe para criacao de objeto de valor para Atributo.
 * @author Gustavo Ferreira
 */
public class AtributoVo extends ComponenteVo{
    
    public enum Tipo{
        COMUM, CHAVE, DERIVADO, COMPOSTO, MULTIVALORADO
    }
    
    private String nome;
    private ComponenteVo[] atributos;    
    private Tipo tipo;

    public AtributoVo(String nome, ComponenteVo[] atributos, Tipo tipo, XDimension xdimension) {
        this.nome = nome;
        this.atributos = atributos;
        this.tipo = tipo;
        this.xdimension = xdimension;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ComponenteVo[] getAtributos() {
        return atributos;
    }

    public void setAtributos(ComponenteVo[] atributos) {
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
}
