package br.com.gqferreira.tupiniquim.model.vo;

import java.util.List;

/**
 * Classe de Valor para armazenar dados importantes ao sistema
 * @author Gustavo Ferreira
 */
public class ParametrosVo {
    public List<String> ultimosProjetos;

    public ParametrosVo(List<String> ultimosProjetos) {
        this.ultimosProjetos = ultimosProjetos;
    }

    public List<String> getUltimosProjetos() {
        return ultimosProjetos;
    }

    public void setUltimosProjetos(List<String> ultimosProjetos) {
        this.ultimosProjetos = ultimosProjetos;
    }
    
}
