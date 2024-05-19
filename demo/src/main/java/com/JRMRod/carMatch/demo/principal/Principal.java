package com.JRMRod.carMatch.demo.principal;

import com.JRMRod.carMatch.demo.modelos.Dados;
import com.JRMRod.carMatch.demo.modelos.Modelos;
import com.JRMRod.carMatch.demo.services.ConsomeApi;
import com.JRMRod.carMatch.demo.services.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Comparator;
import java.util.Scanner;

public class Principal {
    private Scanner in = new Scanner(System.in);
    private ConsomeApi consumer = new ConsomeApi();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";
    public void exibeMenu() throws JsonProcessingException {
        var menu = """
                ***OPÇÕES***
                Carros
                Caminhoes
                Motos
                
                Escolha uma das opções para continuar:
                """;
        System.out.println(menu);

        String veiculoPesquisado = in.nextLine();

        String endereco = ENDERECO + veiculoPesquisado.toLowerCase().trim() + "/marcas";
        String json = consumer.obtemDados(endereco);

        var marcas = conversor.obterLista(json, Dados.class);

        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .map(e -> "Código:" + e.codigo() + " Nome: "+ e.nome()+"\n")
                .forEach(System.out::println);

        System.out.println("Digite o código da marca desejada: ");
        String codigoMarca = in.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumer.obtemDados(endereco);

        var modeloLista = conversor.obterDados(json, Modelos.class);

        System.out.println("Modelos dessa marca: ");

        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .map(e -> "Código: " + e.codigo() + " Nome: "+ e.nome() + "\n")
                .forEach(System.out::println);
    }
}
