package br.com.petshop;

public class Cachorro extends Animal {
	private String raca;

	public Cachorro(String nome, int idade, String raca, int peso, int tamanho) {
		super(nome, idade, peso, tamanho);
		this.raca = raca;

	}

	public void latir() {
		System.out.println(getNome() + "está latindo: Au! Au!");
	}

	public void exibirInfo() {
		super.exibirInfo();
		System.out.println("Raça: " + raca);

	}
}