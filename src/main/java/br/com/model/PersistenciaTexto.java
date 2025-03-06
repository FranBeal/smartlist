package br.com.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaTexto implements PersistenciaStrategy{
    @Override
    public void salvar(List<Produto> produtos, String nomeArquivo) {

        if(produtos != null && !produtos.isEmpty()){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, false))) {
                for (Produto produto : produtos) {
                    writer.write(produto.getNome() + " - " + produto.getQuantidade() + " - "+produto.getPreco());
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            }
        }else{
            System.out.println("Lista vazia!");
        }
    }

    @Override
    public List<Produto> carregar(String nomeArquivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            List<Produto> produtos = new ArrayList<>();
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(" - ");
                produtos.add(new Produto(partes[0], Integer.parseInt(partes[1]), Double.parseDouble(partes[2])));
            }
            System.out.println("Lista do Arquivo de Texto");
            System.out.println(this.toString());
            return produtos;
        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: "+e.getMessage());
            return null;
        }
    }

}
