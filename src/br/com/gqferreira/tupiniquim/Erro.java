package br.com.gqferreira.tupiniquim;

/**
 * Classe para tratar todos os erros do sistema
 * @author Gustavo Ferreira
 */
public class Erro {

    @SuppressWarnings("unused")
    private Erro() {
    }
    
    public static void deal(Exception ex){
        ex.printStackTrace();
    }
}
