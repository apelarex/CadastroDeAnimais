package br.com.petshop;

import java.util.Scanner;

public class PetShop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorDeAnimais gerenciador  = new GerenciadorDeAnimais();

        int opcao;

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar Cachorro");
            System.out.println("2 - Cadastrar Gato");
            System.out.println("3 - Cadastrar Aves");
            System.out.println("4 - Exibir Animais");
            System.out.println("5 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    gerenciador.cadastrarCachorro();
                    break;
                case 2:
                    gerenciador.cadastrarGato();
                    break;
                case 3:
                    gerenciador.cadastrarAve();
                    break;
                case 4:
                    gerenciador.exibirAnimais();
                    break;
                case 5:
                    System.out.println("Encerrando o programa...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 5);

        scanner.close();
    }
}



















































a