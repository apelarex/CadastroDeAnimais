package br.com.petshop;

public class Animal {
	private String nome;
	private int idade;
    private double peso;
    private String tamanho;
   
	public Animal(String nome, int idade, double peso , String tamanho) {
		this.nome = nome;
		this.idade = idade;
		this.peso = peso;
		this.tamanho = tamanho;
		
	}

	public String getNome() {
		return nome;
	}
	public int getIdade() {
		return idade;
	}
	
	public double getpeso() {
		return peso;
	}
	
	public String gettamanho() {
		return tamanho;
	} 
	
	
	public void exibirInfo() {
		System.out.println("Nome: " + nome + ", Idade: " + idade);
		System.out.println("Peso;" + peso);
		System.out.println("tamanho;" + tamanho);
	}
}

