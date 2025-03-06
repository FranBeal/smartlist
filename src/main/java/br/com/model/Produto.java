package br.com.model;

import java.io.Serializable;

public class Produto implements Serializable {
    private String nome;
    private int quantidade;
    private double preco;

    // Construtor padrão (sem argumentos)
    public Produto() {
    }

    // Construtor com argumentos
    public Produto(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return nome + " - " + quantidade + " unidades - R$" + preco;
    }
}
