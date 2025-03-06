package br.com.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaJson implements PersistenciaStrategy{
    @Override
    public void salvar(List<Produto> produtos, String nomeArquivo)  {
        if(!produtos.isEmpty()){
            try  {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // Formata o JSON para ser legível
                objectMapper.writeValue(new File(nomeArquivo), produtos);
            } catch (IOException e) {
                System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            }
        }else{
            System.out.println("Lista vazia!");
        }
    }

    @Override
    public List<Produto> carregar(String nomeArquivo)  {
        try  {
            List<Produto> produtos = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            produtos = objectMapper.readValue(new File(nomeArquivo), objectMapper.getTypeFactory().constructCollectionType(List.class, Produto.class));
            return produtos;
        } catch (IOException e){
            System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
            return null;
        }

    }
}
