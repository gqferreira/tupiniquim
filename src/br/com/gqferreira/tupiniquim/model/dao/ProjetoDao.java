package br.com.gqferreira.tupiniquim.model.dao;

import br.com.gqferreira.tupiniquim.Erro;
import br.com.gqferreira.tupiniquim.view.Componente;
import com.thoughtworks.xstream.XStream;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;

/**
 * Classe do tipo DAO da camada Model. Responsavel pelos processos de leitura e
 * gravacao dos arquivos do projeto
 *
 * @author Gustavo Ferreira
 */
public class ProjetoDao {

    /**
     * Metodo que serializa em padrao xml o arquivo da modelagem conceitual
     *
     * @param componentes
     * @param caminho
     * @throws FileNotFoundException
     */
    synchronized public static void salvar(List<Componente> componentes, String caminho) throws FileNotFoundException, ZipException, IOException {
        //Primeiro coloca o xml no diretorio path
        XStream xstream = new XStream();
        xstream.toXML(componentes, new FileOutputStream("conceptual.cpm"));
            
        //Processo de criacao do arquivo compactado
        if (!caminho.endsWith(".odm")) {
            //Coloca a extensao .odm caso ainda nao tenha
            caminho = caminho.concat(".odm");
        }

        FileOutputStream destino = new FileOutputStream(new File(caminho));
        ZipOutputStream saida = new ZipOutputStream(new BufferedOutputStream(destino));
        //O arquivo que vai ser compactado
        File file = new File("conceptual.cpm");
        FileInputStream streamDeEntrada = new FileInputStream(file);
        BufferedInputStream origem = new BufferedInputStream(streamDeEntrada, 2048);
        //Adicionando a entrada do arquivo no pacote
        ZipEntry entry = new ZipEntry(file.getName());
        saida.putNextEntry(entry);

        //Rodando os bytes e copiando-os para o zip
        int cont = 0;
        byte[] dados = new byte[2048];
        while ((cont = origem.read(dados, 0, 2048)) != -1) {
            saida.write(dados, 0, cont);
        }
        origem.close();
        saida.close();

        //Excluindo o arquivo xml original.
        File conceptual = new File("conceptual.cpm");
        conceptual.delete();
    }

    /**
     * Metodo que faz a leitura do arquivo que contem a modelagem conceitual
     *
     * @param caminho
     * @return
     * @throws FileNotFoundException
     */
    public List<Componente> abrirConceitual(String caminho) throws FileNotFoundException, IOException {

        ZipFile zip = null;
        File arquivo = null;
        InputStream is = null;
        OutputStream os = null;
        byte[] buffer = new byte[2048];

        //Abrindo o arquivo compactado
        zip = new ZipFile(caminho);
        //Listando os arquivos do xml
        Enumeration e = zip.entries();

        //Rodando cada arquivo...
        while (e.hasMoreElements()) {
            //Obtendo a entrada
            ZipEntry entrada = (ZipEntry) e.nextElement();
            //Definindo o local onde ira parar o arquivo descompactado            
            arquivo = new File("conceptual.cpm");

            is = zip.getInputStream(entrada);
            os = new FileOutputStream(arquivo);
            int bytesLidos;
            if (is == null) {
                throw new ZipException("Erro ao ler a entrada do zip: " + entrada.getName());
            }
            //Fazendo as leituras e copiando para o arquivo destinatario
            while ((bytesLidos = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesLidos);
            }
            is.close();
            os.close();
        }

        //Lendo o xml
        XStream xstream = new XStream();
        List<Componente> componentes = (List<Componente>) xstream.fromXML(new FileInputStream("conceptual.cpm"));
        //Excluindo o xml
        new File("conceptual.cpm").delete();

        return componentes;
    }

    /**
     * Metodo que faz a persistencia do arquivo de imagem gerado do modelo
     * conceitual.
     *
     * @param image
     * @param caminho
     */
    public void exportarImagemConceitual(BufferedImage image, String caminho) {
        try {
            ImageIO.write(image, "png", new File(caminho));
        } catch (IOException ex) {
            Erro.deal(ex);
        }
    }
}
