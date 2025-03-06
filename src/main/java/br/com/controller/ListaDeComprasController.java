package br.com.controller;

import br.com.model.*;
import br.com.view.ListaDeComprasView;

public class ListaDeComprasController {
    private ListaDeCompras model;
    private ListaDeComprasView view;

    public ListaDeComprasController(ListaDeCompras model, ListaDeComprasView view) {
        this.model = model;
        this.view = view;
    }

    public void iniciar() {
        int opcao;
        do {
            view.exibirMenu();
            opcao = view.lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                adicionarProduto();
                break;
            case 2:
                removerProduto();
                break;
            case 3:
                exibirLista();
                break;
            case 4:
                salvarEmAqrTexto();
                break;
            case 5:
                carregarDeArqTexto();
                break;
            case 6:
                salvarEmArquivoBinario();
                break;
            case 7:
                carregarDeArquivoBinario();
                break;
            case 8:
                salvarEmArquivoJson();
                break;
            case 9:
                carregarDeArquivoJson();
                break;
            case 10:
                filtrarPorQuantidadeMinima();
                break;
            case 11:
                calcularValorTotal();
                break;
            case 12:
                imprimirLista();
                break;
            case 0:
                view.exibirMensagem("Saindo...");
                break;
            default:
                view.exibirMensagem("Opção inválida!");
        }
    }

    private void adicionarProduto() {
        String nome = view.lerNomeProduto();
        int quantidade = view.lerQuantidade();
        double preco = view.lerPreco();
        model.adicionarProduto(new Produto(nome, quantidade, preco));
    }

    private void removerProduto() {
        String nome = view.lerNomeProduto();
        model.removerProduto(nome);
    }

    private void exibirLista(){
        view.exibirMensagem(model.toString());
    }

    private void salvarEmAqrTexto() {
        model.setEstrategiaPersistencia(new PersistenciaTexto());
        model.salvar("lista_compras.txt");
    }

    private void carregarDeArqTexto() {
        model.setEstrategiaPersistencia(new PersistenciaTexto());
        model.carregar("lista_compras.txt"); //ou "D:/dev/lista_compras.txt"
    }

    private void salvarEmArquivoBinario(){
        model.setEstrategiaPersistencia(new PersistenciaBinario());
        model.salvar("lista_compras.bin");
    }

    private void carregarDeArquivoBinario(){
        model.setEstrategiaPersistencia(new PersistenciaBinario());
        model.carregar("lista_compras.bin");
    }

    private void salvarEmArquivoJson(){
        model.setEstrategiaPersistencia(new PersistenciaJson());
        model.salvar("lista_compras.json");
    }

    private void carregarDeArquivoJson(){
        model.setEstrategiaPersistencia(new PersistenciaJson());
        model.carregar("lista_compras.json");
    }

    private void filtrarPorQuantidadeMinima(){
        int quantidadeMinima = view.lerQuantidadeMinima();
        System.out.println(model.filtrarPorQuantidadeMinima(quantidadeMinima).toString());
    }

    private void calcularValorTotal(){
        System.out.println("Valor Total R$ "+model.calcularValorTotal());
    }

    private void imprimirLista(){
        model.imprimirLista();
        System.out.println("Valor Total R$ "+model.calcularValorTotal());
    }

}
