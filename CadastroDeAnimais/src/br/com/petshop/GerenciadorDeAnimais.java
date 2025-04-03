package br.com.petshop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorDeAnimais {
	private List<Animal> gerenciadorAnimais; // Renomeado para evitar confusão
	private ArrayList<Animal> animais;
	private Scanner scanner;
	private final String ARQUIVO_CSV = "GerenciadorDeAnimais.csv";

	public GerenciadorDeAnimais() {
		animais = new ArrayList<>();
		this.scanner = new Scanner(System.in);
		carregarGerenciadorDeAnimais();
	}

	public void cadastrarCachorro() {
		System.out.print("Nome do cachorro: ");
		String nomeCachorro = scanner.nextLine();
		System.out.print("Idade do cachorro: ");
		int idadeCachorro = scanner.nextInt();
		scanner.nextLine(); // Limpar o buffer
		System.out.print("Raça do cachorro: ");
		String raca = scanner.nextLine();
		System.out.print("Peso do cachorro: ");
		Double peso = scanner.nextDouble();
		scanner.nextLine(); // Limpar o buffer
		System.out.print("Tamanho do cachorro (Pequeno/Médio/Grande): ");
		String tamanho = scanner.nextLine();
		animais.add(new Cachorro(nomeCachorro, idadeCachorro, raca, peso, tamanho));
		System.out.println("Cachorro cadastrado com sucesso!");
	}

	public void cadastrarGato() {
		System.out.print("Nome do gato: ");
		String nomeGato = scanner.nextLine();
		System.out.print("Idade do gato: ");
		int idadeGato = scanner.nextInt();
		scanner.nextLine(); // Limpar o buffer
		System.out.print("Cor da pelagem do gato: ");
		String corPelo = scanner.nextLine();
		System.out.print("Peso do gato (kg): ");
		Double peso = scanner.nextDouble();
		scanner.nextLine(); // Limpar o buffer
		System.out.print("Tamanho do gato (Pequeno/Médio/Grande): ");
		String tamanho = scanner.nextLine();
		animais.add(new Gato(nomeGato, idadeGato, corPelo, peso, tamanho));
		System.out.println("Gato cadastrado com sucesso!");
	}

	public void cadastrarAves() {
		System.out.print("Nome da Ave: ");
		String nomeAve = scanner.nextLine();
		System.out.print("Idade da Ave: ");
		int idadeAve = scanner.nextInt();
		scanner.nextLine(); // Limpar o buffer
		System.out.print("Peso da Ave: ");
		Double pesoAve = scanner.nextDouble();
		scanner.nextLine(); // Limpar o buffer
		System.out.print("Tamanho da Ave (Pequeno/Médio/Grande): ");
		String tamanho = scanner.nextLine();
		animais.add(new Aves(nomeAve, idadeAve, pesoAve, tamanho));
		System.out.println("Ave cadastrada com sucesso!");
	}

	public void exibirAnimais() {
		System.out.println("\n=== Lista de Animais ===");
		for (Animal animal : animais) {
			animal.exibirInfo();
			System.out.println("------------------------");
		}
	}

	private void carregarGerenciadorDeAnimais() {
		// Implementar a lógica para carregar os dados do arquivo CSV
	}
}