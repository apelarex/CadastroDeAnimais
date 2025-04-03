package br.com.petshop;

public class Animal {
	private String nome;
	private int idade;
    private int peso;
    private int tamanho;
   
	public Animal(String nome, int idade, int peso , int tamanho) {
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
	
	public int getpeso() {
		return peso;
	}
	
	public int gettamanho() {
		return tamanho;
	} 
	
	
	public void exibirInfo() {
		System.out.println("Nome: " + nome + ", Idade: " + idade);
		System.out.println("Peso;" + peso);
		System.out.println("tamanho;" + tamanho);
	}
}

