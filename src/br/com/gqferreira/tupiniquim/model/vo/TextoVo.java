package br.com.gqferreira.tupiniquim.model.vo;

import br.com.gqferreira.tupiniquim.model.XDimension;

/**
 *
 * @author Gustavo Ferreria
 */
public class TextoVo extends ComponenteVo{
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public XDimension getXdimension() {
        return xdimension;
    }

    public void setXdimension(XDimension xdimension) {
        this.xdimension = xdimension;
    }

    public TextoVo(String nome, XDimension xdimension) {
        this.nome = nome;
        this.xdimension = xdimension;
    }   
}
