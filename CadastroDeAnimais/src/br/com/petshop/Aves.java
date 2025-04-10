package br.com.petshop;

public class Aves extends Animal {
	private String TipoDeBico;

	public Aves(String nome, int idade, double peso, String tamanho, String TipoDeBico) {

		super(nome, idade, peso, tamanho);
		this.TipoDeBico = TipoDeBico;

	};

	public void miar() {
		System.out.println(getNome() + "esta voando: esta voando ");
	}

	public void exibirInfo() {
		super.exibirInfo();
		System.out.println("TipoDeBico;" + TipoDeBico);
	}
}