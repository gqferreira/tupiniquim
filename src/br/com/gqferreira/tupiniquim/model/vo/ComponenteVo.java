/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.gqferreira.tupiniquim.model.vo;

import br.com.gqferreira.tupiniquim.model.XDimension;

/**
 * Interface para reaproveitar pelas outras VOs. Todo componente
 * possui coordenadas de tamanho e posicao.
 * @author Gustavo Ferreira
 */
public abstract class ComponenteVo{    
    public XDimension xdimension = new XDimension(0, 0, 0, 0);
}
