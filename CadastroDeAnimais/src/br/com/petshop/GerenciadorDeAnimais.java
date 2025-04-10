package br.com.petshop;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class GerenciadorDeAnimais {
	private List<Animal> gerenciadorAnimais;
	private ArrayList<Animal> ListaAnimais ;
	private Scanner scanner;
	private final String ARQUIVO_CSV = "GerenciadorDeAnimais.csv";

	public GerenciadorDeAnimais() {
		ListaAnimais = new ArrayList<>();
		this.scanner = new Scanner(System.in);
	 
	}

	public void cadastrarCachorro() {
		System.out.print("Nome do cachorro: ");
		String nomeCachorro = scanner.nextLine();
		System.out.print("Idade do cachorro: ");
		int idadeCachorro = scanner.nextInt();
		scanner.nextLine(); 
		System.out.print("Raça do cachorro: ");
		String raca = scanner.nextLine();
		System.out.print("Peso do cachorro (kg): ");
		Double peso = scanner.nextDouble();
		System.out.print("Tamanho do cachorro (Pequeno/Médio/Grande) : ");
		String tamanho = scanner.nextLine();
		scanner.nextLine();
		ListaAnimais.add(new Cachorro(nomeCachorro, idadeCachorro, raca, peso, tamanho));
		System.out.println("Cachorro cadastrado com sucesso!");
	}

	public void cadastrarGato() {
		System.out.print("Nome do gato: ");
		String nomeGato = scanner.nextLine();
		System.out.print("Idade do gato: ");
		int idadeGato = scanner.nextInt();
		scanner.nextLine(); 
		System.out.print("Cor da pelagem do gato: ");
		String corPelo = scanner.nextLine();
		System.out.print("Peso do gato (kg): ");
		Double peso = scanner.nextDouble();
		System.out.print("Tamanho do gato (Pequeno/Médio/Grande): ");
		String tamanho = scanner.nextLine();
		scanner.nextLine();
		ListaAnimais.add(new Gato(nomeGato, idadeGato, corPelo, peso, tamanho));
		System.out.println("Gato cadastrado com sucesso!");
	}

	public void cadastrarAves() {
		System.out.print("Nome da Ave: ");
		String nomeAve = scanner.nextLine();
		System.out.print("Idade da Ave: ");
		int idadeAve = scanner.nextInt();
		scanner.nextLine(); 
		System.out.print("Tamanho da Ave (Pequeno/Médio/Grande): ");
		String tamanho = scanner.nextLine();
		System.out.print("Peso da Ave (kg): ");
		Double pesoAve = scanner.nextDouble(); 
		System.out.print("TipoDeBico(Curvo/reto): ");
		String TipoDeBico = scanner.nextLine();
		scanner.nextLine();
		ListaAnimais.add(new Aves(nomeAve, idadeAve, pesoAve, tamanho, TipoDeBico));
		System.out.println("Ave cadastrada com sucesso!");
	}

	public void exibirAnimais () {
		if (ListaAnimais  .isEmpty()) {
			System.out.println("⚠ Nenhum animal cadastrado!");
		} else {
			System.out.println("\n🚘 LISTA DE animais  CADASTRADOS ");
			for (Animal v : ListaAnimais ) {
				v.exibirInfo();
				System.out.println("--------------------------");
			}
		}
	}


	public void localizarAnimais() {
		System.out.print("Digite a nome  do animal que deseja localizar: ");
		String busca = scanner.nextLine();
		boolean encontrado = false;

		System.out.println("\n RESULTADO DA BUSCA:");
		for (Animal v : ListaAnimais) {
			if (v.getNome().equalsIgnoreCase(busca)) {
				v.exibirInfo();
				encontrado = true;
			}
		}

		if (!encontrado) {
			System.out.println("⚠ Nenhum animal  encontrado com o nome  '" + busca + "'.");
		}
	}
}
