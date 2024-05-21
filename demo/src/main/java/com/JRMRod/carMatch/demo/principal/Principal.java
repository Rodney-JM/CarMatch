package com.JRMRod.carMatch.demo.principal;

import com.JRMRod.carMatch.demo.modelos.Dados;
import com.JRMRod.carMatch.demo.modelos.Modelos;
import com.JRMRod.carMatch.demo.modelos.Veiculo;
import com.JRMRod.carMatch.demo.services.ConsomeApi;
import com.JRMRod.carMatch.demo.services.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        System.out.println("\nDigite um trecho do nome do carro a ser buscado");

        var nomeVeiculo = in.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("Modelos filtrados: ");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("Digite por favor o código do modelo para buscar os valores de avaliação");

        var codigoModelo = in.nextLine();
        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumer.obtemDados(endereco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);

        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumer.obtemDados(enderecoAnos);

            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("Todos os veículos filtrados com avaliações por ano:");
        veiculos.forEach(System.out::println);
    }
}
