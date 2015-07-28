package com.andreluisgomes.exemplo;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import com.andreluisgomes.dao.FaturaDAO;
import com.andreluisgomes.emial.EnviadorEmail;
import com.andreluisgomes.modelo.Fatura;

/**
 * Created by agomes on 27/07/15.
 */
public class ExemploStreams {

    public static void main(String[] args){
        
    	/***
    	 * Testes da nova API de Streams
    	 * 
    	 * Essa API traz uma forma mais funcional de trabalhar com nossas coleções. Ela possui diversos métodos, como o filter, map e reduce, que recebem uma interface funcional como parâmetro, nos possibilitando tirar proveito dos novos recursos de lambda e method reference.
    	 */
    	
    	EnviadorEmail enviador = new EnviadorEmail();

        List<Fatura> faturasVencidas = new FaturaDAO().buscarfaturasVencidas();

        faturasVencidas.stream().filter(f -> f.getValor() < 250).forEach(f -> enviador.enviar(f.getEmailDevedor(),f.resumo()));

        /***
         * devemos utilizá-lo quando precisamos aplicar transformações em nossa lista sem a necessidade de variáveis intermediárias
         * 
         * .average() devolve um Optional
         */
        OptionalDouble media = faturasVencidas.stream().mapToDouble(Fatura::getValor).average();
        
        System.out.println(media);
        
        //Collect
        List<Fatura> collectFaturas = faturasVencidas.stream().filter(f -> f.getValor() < 350).collect(Collectors.toList());
        
        collectFaturas.forEach(f -> enviador.enviar(f.getEmailDevedor(),f.resumo()));
    }
}