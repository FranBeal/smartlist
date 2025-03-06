package br.com.model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ListaDeCompras {
    private static ListaDeCompras instancia;
    private List<Produto> produtos;
    private PersistenciaStrategy estrategiaPersistencia;

    public ListaDeCompras() {
        produtos = new ArrayList<>();
    }

    public static ListaDeCompras getInstancia() {
        if (instancia == null) {
            instancia = new ListaDeCompras();
        }
        return instancia;
    }

    // Adiciona um produto à lista
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    // Remove um produto da lista pelo nome
    public void removerProduto(String nome) {
        produtos.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
    }


    public void setEstrategiaPersistencia(PersistenciaStrategy estrategia) {
        this.estrategiaPersistencia = estrategia;
    }

    public void salvar(String nomeArquivo)  {
        estrategiaPersistencia.salvar(produtos, nomeArquivo);
    }

    public void carregar(String nomeArquivo) {
        if(!Files.exists(Paths.get(nomeArquivo)))
            System.out.println("Arquivo não encontrado!");
        else
            produtos = estrategiaPersistencia.carregar(nomeArquivo);
    }

    // Filtra produtos com quantidade mínima usando streams
    public List<Produto> filtrarPorQuantidadeMinima(int quantidadeMinima) {
        return produtos.stream()
                .filter(p -> p.getQuantidade() >= quantidadeMinima)
                .collect(Collectors.toList());
    }

    // Calcula o valor total da lista usando streams
    public double calcularValorTotal() {
        return produtos.stream()
                .mapToDouble(p -> p.getQuantidade() * p.getPreco())
                .sum();
    }

    // Imprime a lista de produtos em ordem alfabética
    public void imprimirLista() {
        produtos.stream()
                .sorted(Comparator.comparing(p -> p.getNome().toLowerCase())) // Ordena por nome
                .forEach(System.out::println);
    }

    @Override
    public String toString() {
        if (produtos.isEmpty()) {
            return "Lista de compras vazia.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("--- Lista de Compras ---\n");

        // Itera sobre os produtos e adiciona cada um ao StringBuilder
        for (int i = 0; i < produtos.size(); i++) {
            sb.append((i + 1)).append(". ").append(produtos.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}
