package br.com.gqferreira.tupiniquim.model.bo;

import br.com.gqferreira.tupiniquim.model.dao.ParametrosDao;
import br.com.gqferreira.tupiniquim.model.vo.ParametrosVo;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de negocio para manipular os parametros do sistema
 *
 * @author Gustavo Ferreira
 */
public class ParametrosBo {

    /**
     * Metodo que trata a persistencia do arquivo de parametros do sistema
     * @param String projeto
     * @throws FileNotFoundException 
     */
    public void atualizaUltimosProjetos(String projeto) throws FileNotFoundException {
        //Primeiro passo eh verificar se existe um arquivo de configuracao. Se nao
        //existir, cria um com o projeto informado. Se existir, faz todo o processo
        //abaixo
        if (!new ParametrosDao().isConfiguracaoExiste()){
            List<String> projetosRecentes = new ArrayList<String>();
            projetosRecentes.add(projeto);
            ParametrosVo parametros = new ParametrosVo(projetosRecentes);
            new ParametrosDao().salvar(parametros);
            return;
        }
        
        //Resgatando o conteudo do arquivo de parametros
        ParametrosVo parametros = new ParametrosDao().ler();
        //Resgatando os ultimos projetos salvos
        List<String> ultimosProjetos = parametros.getUltimosProjetos();

        //Deve-se verificar se ja nao tem uma entrada na lista de projetos recentes
        //repetindo com o que se deseja acrescentar nesse momento. Se houver, interrompe
        //e nada faz
        boolean repetiu = false;
        for (String proj : ultimosProjetos) {
            if (proj.equals(projeto)) {
                //Encontrou
                repetiu = true;
                break;
            }
        }
        if (!repetiu) {
            //Cria um novo vetor para condicionar a nova lista de projetos
            //recentemente abertos
            List<String> novosUltimosProjetos = new ArrayList<String>();
            //Adiciona o atual no topo da lista
            novosUltimosProjetos.add(projeto);
            //Percorre todos os projetos do vetor antigo.
            //Se ja possui cinco projetos, o ultimo ficara de fora para lugar ao novo,
            //se nao, apenas empurra e adiciona mais um
            for (int i = 0; i < (ultimosProjetos.size() > 5 ? ultimosProjetos.size() - 1 : ultimosProjetos.size()); i++) {
                //Adiciona
                novosUltimosProjetos.add(ultimosProjetos.get(i));
            }
            //Substitui a antiga lista de projetos
            parametros.setUltimosProjetos(novosUltimosProjetos);
            //Manda serializar em formato de arquivo
            new ParametrosDao().salvar(parametros);
        }
    }
    
    public ParametrosVo getParametros(){
        return new ParametrosDao().ler();
    }
}
