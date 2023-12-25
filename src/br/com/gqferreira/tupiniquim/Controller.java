package br.com.gqferreira.tupiniquim;

/**
 * Interface para padronizar todas as controllers
 * @author Gustavo Ferreira
 */
public interface Controller {
    
    public void notifyError(Exception retorno);
    public void notifyFinish(Object retorno);
    public void notifyNextStep(Object retorno);
}
