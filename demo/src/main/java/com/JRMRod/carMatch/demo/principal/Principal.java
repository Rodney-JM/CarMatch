package com.JRMRod.carMatch.demo.principal;

import com.JRMRod.carMatch.demo.modelos.DadosMarcas;
import com.JRMRod.carMatch.demo.services.ConsomeApi;
import com.JRMRod.carMatch.demo.services.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Arrays;
import java.util.Scanner;

public class Principal {
    private Scanner in = new Scanner(System.in);
    private ConsomeApi consumer = new ConsomeApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    public void exibeMenu() throws JsonProcessingException {
        System.out.println("Qual dos seguintes veículos você deseja buscar?");
        System.out.println("Carros\nCaminhoes\nMotos");

        String veiculoPesquisado = in.nextLine();

        String enderecoMarcas = ENDERECO + veiculoPesquisado.toLowerCase().trim() + "/marcas";
        String marcasJson = consumer.obtemDados(enderecoMarcas);

        DadosMarcas[] dadosMarcas = conversor.obterDados(marcasJson, DadosMarcas[].class);


    }
}
