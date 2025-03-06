package br.com.model;

import java.util.List;

public interface PersistenciaStrategy {
    void salvar(List<Produto> produtos, String caminhoArquivo);
    List<Produto> carregar(String caminhoArquivo);
}
