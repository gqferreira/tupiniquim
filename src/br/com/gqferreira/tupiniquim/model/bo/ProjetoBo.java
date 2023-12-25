package br.com.gqferreira.tupiniquim.model.bo;

import br.com.gqferreira.tupiniquim.Controller;
import br.com.gqferreira.tupiniquim.Model;
import br.com.gqferreira.tupiniquim.model.dao.ProjetoDao;
import br.com.gqferreira.tupiniquim.view.Componente;
import com.thoughtworks.xstream.XStream;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ProjetoBo {

    /**
     * Metodo responsavel por abrir o modelo conceitual do projeto.
     *
     * @param Controller observer
     * @param String caminho
     * @return List<ComponenteVo>
     */
    public List<Componente> abrirConceitual(final Controller observer, final String caminho) throws FileNotFoundException, IOException {
        ProjetoDao dao = new ProjetoDao();
        return dao.abrirConceitual(caminho);
    }

    public void exportarImagemConceitual(final Controller observer, BufferedImage image, String caminho) {
        new ProjetoDao().exportarImagemConceitual(image, caminho);
    }

    /**
     * Metodo responsavel por gerenciar o salvamento assincrono do modelo.
     *
     * @param Controller observer
     * @param String caminho
     */
    public static void salvar(final Controller observer, final String caminho) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    ProjetoDao.salvar(Model.listComponentes(), caminho);

                } catch (Exception ex) {
                    observer.notifyError(ex);
                }
                observer.notifyFinish("");
            }
        }).start();
    }

    synchronized public String serializarComponentes(List<Componente> componentes) {
        XStream xstream = new XStream();
        return xstream.toXML(componentes);
    }
    synchronized public List<Componente> deserializarcomponentes(String xml){
        XStream xstream = new XStream();
        return (List<Componente>)xstream.fromXML(xml);
    }
}
