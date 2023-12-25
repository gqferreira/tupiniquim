package br.com.gqferreira.tupiniquim.model.vo;

/**
 * Classe usada para serializar o xml principal do projeto
 * @author Gustavo Ferreira
 */
public class Projeto {
    private String conceitual;
    private String logico;
    private String fisico;

    public String getConceitual() {
        return conceitual;
    }

    public void setConceitual(String conceitual) {
        this.conceitual = conceitual;
    }

    public String getLogico() {
        return logico;
    }

    public void setLogico(String logico) {
        this.logico = logico;
    }

    public String getFisico() {
        return fisico;
    }

    public void setFisico(String fisico) {
        this.fisico = fisico;
    }

    public Projeto(String conceitual, String logico, String fisico) {
        this.conceitual = conceitual;
        this.logico = logico;
        this.fisico = fisico;
    }    
}
