package br.com.gqferreira.tupiniquim.test;

public class Funcionario {

    public String nome;
    public String departamento;
    public Double salario;
    public String DataEntradaNoBanco;
    public String RG;
    public Double ValorAumento;
    public Double GanhoAnual;

    public Double recebeAumento(Double Valor){

        Double ValorAumento = salario + Valor;
        return ValorAumento;

    }

    Double calculaGanhoAnual(){
        //-- ValorAumento atualmente é null pois no método 'recebeAumento' você
        //não atribui valor para essa variável, você apenas criou uma outra
        //(visivel no apenas localmente).
        Double GanhoAnual = ValorAumento * 12; 
        return GanhoAnual;
    }

    public static void main(String[] args ){

        Double x;
//        
//        Funcionario f1 = new Funcionario();
//
//        f1.nome = "Hugo";
//        f1.salario = 100.;
//        f1.recebeAumento(50.);
//
//        System.out.println("salario atual:" + f1.salario);
//        System.out.println("ganho anual:" + f1.calculaGanhoAnual());
    }
    
}