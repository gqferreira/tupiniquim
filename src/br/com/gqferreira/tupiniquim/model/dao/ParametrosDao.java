package br.com.gqferreira.tupiniquim.model.dao;

import br.com.gqferreira.tupiniquim.model.vo.ParametrosVo;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Classe utilizada para manipular o arquivo de parametros do sistema
 * @author Gustavo Ferreira
 */
public class ParametrosDao {

    /**
     * Metodoq que serializa os parametros do sistema em um arquivo xml
     * @param ParametrosVo parametros
     * @throws FileNotFoundException 
     */
    public void salvar(ParametrosVo parametros) throws FileNotFoundException{
        XStream xstream = new XStream();
        xstream.toXML(parametros, new FileOutputStream(new File("parameters.conf")));
    }
    
    /**
     * Metodo que le o arquivo de parametros e retorna um objeto
     * @return ParametrosVo
     */
    public ParametrosVo ler(){
        XStream xstream = new XStream();
        return (ParametrosVo) xstream.fromXML(new File("parameters.conf"));
    }
    
    /**
     * Metodo que verifica se o arquivo de configuracao existe
     * @return boolean
     */
    public boolean isConfiguracaoExiste(){
        return new File("parameters.conf").exists();
    }
}
