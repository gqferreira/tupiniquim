package br.com.gqferreira.tupiniquim;

import br.com.gqferreira.tupiniquim.view.Componente;
import java.awt.Graphics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Gustavo Ferreira
 */
public class Model {

    /*
     * Essa variavel determina a ordem dos arquivos temporarios.
     * Esses arquivos temporarios sao utilizados como salva-guarda
     * do modelo e para permitir desfazer e refazer
     */
    private static long idModeloTemporario = 0;
    //Usado para todas as classes que precisar realizar calculos de tamanho de string
    public static Graphics graphics;
    public static final ResourceBundle lang = ResourceBundle.getBundle("br.com.gqferreira.tupiniquim.languages.language", new Locale("pt", "BR"));
    public static PilhaComponentesSelecionados stackComponents = new PilhaComponentesSelecionados();
    public static boolean resizeStarted;
    public static boolean dragStarded;
    public static boolean selectStarted;
    public static boolean dragPanelCalculated;
    //Usado para auxiliar varios processo de 'drag'. Indica a posicao x e y
    //no momento que iniciou o drag, nao alterando durante o drag.
    public static int y;
    public static int x;
    //Identifica a posicao e tamanho da selecao.
    //Usado para calcular os componentes que devem ser selecionados
    public static int xSelecao;
    public static int ySelecao;
    public static int wSelecao;
    public static int hSelecao;
    //Usado para calcular o tamanho do painel que agrupa elementos que estao sendo movimentados
    public static int menorX;
    public static int menorY;
    public static int maiorX;
    public static int maiorY;
    //Lista com todos os componentes da tela, independente de estarem selecionados ou nao
    private static List<Componente> componentes = new ArrayList<Componente>();
    //Encapsulamento dos objetos da model, os componentes. Qualquer componente
    //que for adicionado ao projeto deve passar por esse metodo    
    //Quando eh pressionado sobre uma ligacao (que deve ser transparente) os
    //componentes que estao em baixo devem receber o mesmo tratamento como se
    //tivesse sido clicado sobre ele. Para isso eh armazenado temporariamente nessa
    //variavel para tratamento no drag.
    public static Componente componenteAbaixoDaLigacao;

    public static void addComponente(Componente componente) {
        componentes.add(componente);
    }

    /**
     * Limpa a lista de componentes que o model conhece
     */
    public static void clearComponentes() {
        componentes.clear();
    }

    public static List<Componente> listComponentes() {
        return componentes;
    }

    /**
     * Utilizado para definir os componentes assim que for aberto um projeto
     */
    public static void setComponentes(List<Componente> componentes) {
        Model.componentes = componentes;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(componentes);
            atualSerializado = bos.toByteArray();
            os.close();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //Caminho onde o projeto esta sendo salvo, ele eh definido quando o usuario
    //salva os arquivos pela primeira vez, depois os arquivos sao sempre sobrescritos
    public static String path = "";
    //Deterimna se eh um projeto que foi aberto ou criado
    public static boolean abriuProjeto = false;

    //Qual ferramenta o usuario selecionou na barra de ferramentas? Soh pode ser uma dessas.
    public enum Opcao {

        CURSOR(0),
        ENTIDADE(1),
        ENTIDADE_FRACA(2),
        RELACIONAMENTO(3),
        ENTIDADE_ASSOCIATIVA(4),
        ESPECIALIZACAO_TOTAL(5),
        ESPECIALIZACAO_PARCIAL(6),
        TEXTO(7),
        ATRIBUTO_COMUM(8),
        ATRIBUTO_CHAVE(9),
        ATRIBUTO_DERIVADO(10),
        ATRIBUTO_COMPOSTO(11),
        ATRIBUTO_MULTIVALORADO(12),
        LIGACAO(13),
        AUTO_RELACIONAMENTO(14);
        private int value;

        Opcao(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
    public static Opcao opcao = Opcao.CURSOR;

    //Define qual o tipo de cursor dependendo da posicao 
    //que o mouse estiver parado em uma das pontas do componente
    public enum Direcao {

        SUPERIOR_ESQUERDA(),
        SUPERIOR_DIREITA(),
        INFERIOR_ESQUERDA(),
        INFERIOR_DIREITA();
    }
    public static Direcao direcao;

    //Define o tipo do dialog para permitir um titulo generico
    public enum TextDialogType {

        OBSERVACAO();
    }
    //Pilhas para CTR+Z e CTR+Y
    //Armazena o ultimo estado do model antes de iniciar um resize ou drag
    public static byte[] atualSerializado;
    public static List<byte[]> pilhaPositivoSerializado = new ArrayList<byte[]>();
    public static List<byte[]> pilhaNegativoSerializado = new ArrayList<byte[]>();

    /**
     * Utilizado para limpar todas as pilhas de 'redo' e 'undo'. Usado quando o
     * usuario abre um outro projeto ou cria um novo
     */
    public static void limparPilhas() {
        pilhaNegativoSerializado.clear();
        pilhaPositivoSerializado.clear();
    }

    /**
     * Metodo utilizado quando o usuario deseja desfazer a ultima acao
     */
    public static boolean desfazer() {

        if (pilhaPositivoSerializado.size() > 0) {
            int ultimoEstadoIndex = pilhaPositivoSerializado.size() - 1;
            byte[] ultimoEstado = pilhaPositivoSerializado.get(ultimoEstadoIndex);
            pilhaPositivoSerializado.remove(ultimoEstadoIndex);
            pilhaNegativoSerializado.add(atualSerializado);
            atualSerializado = ultimoEstado;
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(atualSerializado);
                ObjectInputStream in = new ObjectInputStream(bis);
                componentes = (List<Componente>) in.readObject();
                bis.close();
                in.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //Limpa selecao
            stackComponents.reset();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo utilizado quando o usuario deseja refazer a ultima acao
     */
    public static boolean refazer() {

        if (pilhaNegativoSerializado.size() > 0) {
            int ultimoEstadoIndex = pilhaNegativoSerializado.size() - 1;
            byte[] ultimoEstado = pilhaNegativoSerializado.get(ultimoEstadoIndex);
            pilhaNegativoSerializado.remove(ultimoEstadoIndex);
            pilhaPositivoSerializado.add(atualSerializado);
            atualSerializado = ultimoEstado;
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(atualSerializado);
                ObjectInputStream in = new ObjectInputStream(bis);
                componentes = (List<Componente>) in.readObject();
                bis.close();
                in.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //Limpa selecao
            stackComponents.reset();
            return true;
        } else {
            return false;
        }
    }

    public static void copiarModelo() {

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            os.writeObject(componentes);
            atualSerializado = bos.toByteArray();
            os.close();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }        
    }

    /**
     * Toda vez que o modelo de dados eh alterado, esse metodo eh chamada para
     * limpar a pilha negativa e adicionar o antigo model na pilha positiva.
     * Esse tal de 'estadoModel' contem um model que era real antes de iniciar
     * um drag ou resize. Como esse metodo so eh chamado ao final de um resize (
     * alem de remove e add), o model precisa ficar em cache pois quando o
     * metodo for chamado o model ja sera o atual e nao o anterior.
     */
    public static void modelEditado() {
        try {
            pilhaPositivoSerializado.add(atualSerializado);

            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream os = new ObjectOutputStream(bos);
                os.writeObject(componentes);
                atualSerializado = bos.toByteArray();
                os.close();
                bos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            pilhaNegativoSerializado.clear();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retorna um numero do tipo long. Esse numero eh auto incremento e define o
     * id modelo, um meio de identicalo como unico e compor o caminho do arquivo
     * temporario
     *
     * @return
     */
    public static long getNextIdModeloTemporario() {
        return ++idModeloTemporario;
    }
}
