package br.com.gqferreira.tupiniquim.model.vo;

import br.com.gqferreira.tupiniquim.model.XDimension;

/**
 *
 * @author Gustavo Ferreira
 */
public class RelacionamentoVo extends ComponenteVo{
    
    private String titulo;
    private AtributoVo[] atributos;
    private EntidadeVo[] entidades;
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public AtributoVo[] getAtributos() {
        return atributos;
    }

    public void setAtributos(AtributoVo[] atributos) {
        this.atributos = atributos;
    }

    public XDimension getXdimension() {
        return xdimension;
    }

    public void setXdimension(XDimension xdimension) {
        this.xdimension = xdimension;
    }

    public EntidadeVo[] getEntidades() {
        return entidades;
    }

    public void setEntidades(EntidadeVo[] entidades) {
        this.entidades = entidades;
    }

    public RelacionamentoVo(String titulo, AtributoVo[] atributos, EntidadeVo[] entidades) {
        this.titulo = titulo;
        this.atributos = atributos;
        this.entidades = entidades;
    }
}
