package br.com.petshop;

public class Gato extends Animal {
	private String corPelo;
	public Gato(String nome, int idade, String corPelo , double peso, String tamanho) {
		super(nome, idade, peso, tamanho );
		this.corPelo = corPelo;

	};
	public void miar() {
		System.out.println(getNome()+"esta miando: miauu miauu");
}
	public void exibirInfo() {
		super.exibirInfo();
		System.out.println("cor do pelo:" + corPelo);
		
		
		
		
	}

}
