package br.com.gqferreira.tupiniquim;

import br.com.gqferreira.tupiniquim.view.Componente;
import java.util.ArrayList;

/**
 * Pilha de componentes. Esta pilha contem somente componentes selecionados
 * @author Gustavo Ferreira
 */
public class PilhaComponentesSelecionados {

    private ArrayList<Componente> componentes = new ArrayList<Componente>();
    private int top = 0;

    
    /**
     * Add the component
     */
    public void toPush(Componente componente) {
        componentes.add(top, componente);
        componente.setSelecionado(true);
        top++;
    }

    /**
     * Remove a component
     *
     * @return Componente
     */
    public Componente toPop() {
        if (top > 0) {
            Componente c = componentes.get(top-1);
            c.setSelecionado(false);
            
            componentes.remove(c);
            top--;
            return c;
        } else {
            return null;
        }
    }

    /**
     * Get the last component but don't remove it
     * @return Componente
     */
    public Componente getLast() {
        if (top > 0) {
            return componentes.get(top-1);
        } else {
            return null;
        }
    }
    
    /**
     * Remove all components
     *
     * @return Componente[]
     */
    public Componente[] reset() {
        top = 0;
        int i = 0;
        
        Componente[] compVet = new Componente[componentes.size()];
        for (Componente c : componentes) {
            c.setSelecionado(false);
            compVet[i] = c;
            i++;
        }
        for (Componente c : compVet){
            componentes.remove(c);
        }
        return compVet;
    }
    
    /**
     * Get all components but don't remove
     * @return Componente[]
     */
    public Componente[] getAll() {
        int i = 0;
        
        Componente[] compVet = new Componente[componentes.size()];
        for (Componente c : componentes) {
            compVet[i] = c;
            i++;
        }
        return compVet;
    }
}
