package br.com.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaBinario implements PersistenciaStrategy{

    @Override
    public void salvar(List<Produto> produtos, String nomeArquivo) {
        if(!produtos.isEmpty()){
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
                oos.writeObject(produtos);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            }
        }else{
            System.out.println("Lista vazia!");
        }
    }

    @Override @SuppressWarnings("unchecked") // Suprime avisos de operações não verificadas, esta anotação é usada para silenciar aviso do compilador.
    public List<Produto> carregar(String nomeArquivo)  {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            List<Produto> produtos = new ArrayList<>();
            produtos = (List<Produto>) ois.readObject();
            return  produtos;
        } catch (ClassNotFoundException | IOException e){
            System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            return null;
        }

    }

}
