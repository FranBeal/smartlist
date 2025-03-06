package br.com.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ListaDeComprasView {
    private Scanner scanner;

    public ListaDeComprasView() {
        scanner = new Scanner(System.in);
    }

    public void exibirMenu() {
        System.out.println("\n--- Gerenciador de Lista de Compras ---");
        System.out.println("1. Adicionar Produto");
        System.out.println("2. Remover Produto");
        System.out.println("3. Imprimir Lista");
        System.out.println("4. Salvar Lista em Arquivo de Texto");
        System.out.println("5. Carregar Lista de Arquivo de Texto");
        System.out.println("6. Salvar Lista em Arquivo Binário");
        System.out.println("7. Carregar Lista de Arquivo Binário");
        System.out.println("8. Salvar Lista em Arquivo JSON");
        System.out.println("9. Carregar Lista de Arquivo JSON");
        System.out.println("10. Filtrar Produtos por Quantidade Mínima ");
        System.out.println("11. Calcular Valor Total da Lista");
        System.out.println("12. Imprimir Lista em Ordem Alfabética");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public int lerOpcao() {
        int opcao = scanner.nextInt();
        scanner.nextLine();
        return opcao;
    }

    public String lerNomeProduto() {
        String nome = "";
        boolean nomeValido = false;
        while(!nomeValido) {
            System.out.print("Nome do Produto: ");
            nome = scanner.nextLine().trim();

            if (nome.isEmpty()) {
                System.out.println("Erro: informe o nome do produto");
            }else{
                nomeValido = true;
            }
        }
        return nome;
    }

    public int lerQuantidade() {
        int quantidade = 0;
        boolean quantidadeValida = false;
        while (!quantidadeValida) {
            try {
                System.out.print("Quantidade: ");
                quantidade = scanner.nextInt();
                scanner.nextLine();
                quantidadeValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Erro: Quantidade deve ser um número inteiro. Tente novamente.");
                scanner.nextLine(); // Limpa o buffer do scanner
            }
        }
        return quantidade;
    }

    public double lerPreco() {
        System.out.print("Preço: ");
        return scanner.nextDouble();
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public int lerQuantidadeMinima() {
        int quantidade = 0;
        boolean quantidadeValida = false;
        while (!quantidadeValida) {
            try {
                System.out.print("Quantidade mínima: ");
                quantidade = scanner.nextInt();
                scanner.nextLine();
                quantidadeValida = true;
            } catch (InputMismatchException e) {
                System.out.println("Erro: Quantidade mínima deve ser um número inteiro. Tente novamente.");
                scanner.nextLine(); // Limpa o buffer do scanner
            }
        }
        return quantidade;
    }
}
