# Manipulação de Arquivos, Serialização de Objetos e Streams em Java

## Introdução

Este documento tem como objetivo guiar os estudantes na construção de um projeto Java chamado **SmartList**, que é um gerenciador de lista de compras. O projeto foi desenvolvido para ensinar conceitos importantes como manipulação de arquivos (texto, binário e JSON), serialização de objetos e o uso de **Streams** para manipulação de coleções. Além disso, serão abordados padrões de projeto como **MVC (Model-View-Controller)**, **Singleton** e **Strategy**.

O projeto é estruturado em uma aplicação Java desktop com interação por linha de comando, onde os estudantes poderão adicionar, remover, salvar e carregar produtos de uma lista de compras, além de aplicar filtros e cálculos sobre os dados.

---
## Objetivos do Projeto

O projeto **SmartList** tem como objetivo prático ensinar os seguintes conceitos:

1. **Leitura e Escrita em Arquivos de Texto**
2. **Leitura e Escrita em Arquivos Binários**
3. **Serialização de Objetos**
4. **Leitura e Escrita em Arquivos JSON**
5. **Manipulação de Coleções com Streams**
6. **Padrões de Projeto (MVC, Singleton e Strategy)**

---
## Requisitos Técnicos

Para executar o projeto, é necessário:

- **Linguagem de Programação:** Java
- **JDK:** Versão 21 ou superior
- **IDE:** IntelliJ IDEA (recomendado)
- **Maven:** Ferramenta de automação e gerenciamento de projetos Java. Usada principalmente para gerenciar dependências, compilar código, rodar testes e empacotar a aplicação. Ele utiliza um arquivo XML chamado pom.xml (Project Object Model) para definir configurações do projeto e dependências externas. 

---
## Estrutura do Projeto

O projeto será organizado em três pacotes principais, seguindo o padrão **MVC (Model-View-Controller)**:

1. **Pacote `model`:** Contém as classes que representam os dados e a lógica de negócio.
2. **Pacote `view`:** Responsável pela exibição dos dados e interação com o usuário.
3. **Pacote `controller`:** Faz a mediação entre a `view` e o `model`, processando as entradas do usuário e atualizando a `view` com os resultados.

Esta estrutura organiza uma aplicação em três componentes principais: Model, View e Controller. Cada um desses componentes é responsável por uma função específica, o que facilita a manutenção e o desenvolvimento da aplicação, além de promover a separação de responsabilidades.

---
## Funcionalidades do Projeto

O projeto **SmartList** oferece as seguintes funcionalidades:

1. **Adicionar Produto**
2. **Remover Produto**
3. **Imprimir Lista**
4. **Salvar Lista em Arquivo de Texto**
5. **Carregar Lista de Arquivo de Texto**
6. **Salvar Lista em Arquivo Binário**
7. **Carregar Lista de Arquivo Binário**
8. **Salvar Lista em Arquivo JSON**
9. **Carregar Lista de Arquivo JSON**
10. **Filtrar Produtos por Quantidade Mínima**
11. **Calcular Valor Total da Lista**
12. **Imprimir Lista em Ordem Alfabética**

---
## Passo a Passo do Projeto

### 1. Configuração do Projeto no IntelliJ IDEA

1. Abra o IntelliJ IDEA e crie um novo projeto.
2. Dê o nome de **SmartList** ao projeto.
3. Em **Build system**, selecione **Maven**.
4. Em **JDK**, selecione a versão 21 ou superior.
5. Clique em **Create** para criar o projeto.

### 2. Implementação das Classes

#### Classe `Produto`

A classe `Produto` representa um item da lista de compras e contém os atributos `nome`, `quantidade` e `preco`.

```java
package br.com.model;

public class Produto {
    private String nome;
    private int quantidade;
    private double preco;

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
```

#### Classe `ListaDeCompras`

A classe `ListaDeCompras` gerencia uma lista de produtos e oferece métodos para adicionar, remover e manipular os produtos.

```java
package br.com.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ListaDeCompras {
    private List<Produto> produtos;

    public ListaDeCompras() {
        produtos = new ArrayList<>();
    }

    // Adiciona um produto à lista
    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
    }

    // Remove um produto da lista pelo nome
    public void removerProduto(String nome) {
        produtos.removeIf(p -> p.getNome().equalsIgnoreCase(nome));
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
```
#### Classe `ListaDeComprasView`

A classe `ListaDeComprasView` é responsável por exibir o menu e capturar as entradas do usuário.

```java
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
        System.out.println("3. Exibir a Lista de Compras");
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
}
```

#### Classe `ListaDeComprasController`

A classe `ListaDeComprasController` faz a mediação entre a `view` e o `model`, processando as entradas do usuário e atualizando a `view` com os resultados.

```java
package br.com.controller;

import br.com.model.ListaDeCompras;
import br.com.view.ListaDeComprasView;
import br.com.model.Produto;

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
}
```

#### Classe `Main`

A classe `Main` é o ponto de entrada do programa, onde o `model`, `view` e `controller` são inicializados.

```java
package br.com;

import br.com.controller.ListaDeComprasController;
import br.com.model.ListaDeCompras;
import br.com.view.ListaDeComprasView;

public class Main {
    public static void main(String[] args) {
        ListaDeCompras model = new ListaDeCompras();
        ListaDeComprasView view = new ListaDeComprasView();
        ListaDeComprasController controller = new ListaDeComprasController(model, view);

        controller.iniciar();
    }
}
```
#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. Um resultado igual o da figura abaixo deverá ser apresentado.

![image](https://github.com/user-attachments/assets/109ac8cc-3271-44cf-89ff-84859a39277b)

Realizar teste como: adicionar produtos, remover e exibir a lista.

---
# Manipulação de Arquivos em Java

A manipulação de arquivos permite a leitura e gravação de dados em arquivos de texto e binários, trabalhando com fluxo de dados (*streams*). Um fluxo representa uma sequência contínua de dados que pode ser lida ou escrita de forma sequencial.

- **Fluxos de entrada** permitem ler dados de uma fonte, como um arquivo.
- **Fluxos de saída** permitem escrever dados para um destino.

## Tipos de Fluxos

### 1. Fluxos de Caracteres
Usados para manipular dados de texto, permitindo a leitura e gravação caractere por caractere. Principais classes:

- **`Reader`**: Classe abstrata que serve como superclasse para todas as classes que representam um fluxo de entrada de caracteres.
- **`Writer`**: Classe abstrata que serve como superclasse para todas as classes que representam um fluxo de saída de caracteres.

### 2. Fluxos de Bytes
Usados para manipular dados binários, como imagens e sons. Permitem manipular bytes individualmente. Principais classes:

- **`InputStream`**: Classe abstrata que serve como superclasse para todas as classes que representam um fluxo de entrada de bytes.
- **`OutputStream`**: Classe abstrata que serve como superclasse para todas as classes que representam um fluxo de saída de bytes.

## Abertura e Criação de Arquivos Texto
Para manipular arquivo de texto em Java, é necessário primeiro abrir ou criar o arquivo. Isso pode ser feito usando classes como `File`, `FileReader` e `FileWriter`.

### Criação de um Novo Arquivo para Gravação
```java
File file = new File("novo_arquivo.txt");
FileWriter writer = new FileWriter(file);
```
A classe `File` permite manipular arquivos e diretórios no sistema, possibilitando:

- Criar e excluir arquivos;
- Verificar a existência de um arquivo;
- Trabalhar em conjunto com `FileReader` e `FileWriter` para leitura e gravação de dados.

### Métodos do `FileWriter`

| Método | Descrição |
|---------|-------------|
| `void write(String text)` | Escreve uma string no arquivo. |
| `void write(char c)` | Escreve um caractere no arquivo. |
| `void write(char[] c)` | Escreve um array de caracteres no arquivo. |
| `void flush()` | Libera os dados do buffer para o arquivo. |
| `void close()` | Fecha o arquivo. |

## Gravação de Arquivos de Texto
A gravação pode ser feita caractere por caractere ou linha por linha.

### 1. Gravação de Caracteres
```java
FileWriter writer = new FileWriter("arquivo.txt");
writer.write("Olá, mundo!");
writer.close();
```

### 2. Gravação de Linhas (Usando `BufferedWriter`)
```java
BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("arquivo.txt"));
bufferedWriter.write("Linha 1");
bufferedWriter.newLine();
bufferedWriter.write("Linha 2");
bufferedWriter.close();
```
A classe `BufferedWriter` é usada em conjunto com `FileWriter` para melhorar o desempenho da gravação, pois armazena dados em buffer antes de escrever no arquivo físico. Isso melhora o desempenho, pois reduz o número de operações de gravação no disco, que são relativamente lentas.

O método `newLine()` insere uma quebra de linha de forma independente do sistema operacional.

### Uso do `flush()`
```java
BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("arquivo.txt"));
bufferedWriter.write("Escrevendo no arquivo");
bufferedWriter.newLine();
bufferedWriter.flush();
bufferedWriter.close();
```
O `flush()` força o `BufferedWriter` a escrever imediatamente todos os dados que estão no buffer para o arquivo, independentemente de o buffer estar cheio ou não. Se `flush()` não for chamado, os dados podem permanecer no buffer e não serem gravados no arquivo até que o buffer esteja cheio ou o `BufferedWriter` seja fechado. Em algumas situações, como quando se faz necessário que os dados sejam gravados imediatamente, ou quando o programa pode terminar antes que o buffer esteja cheio, é necessário usar `flush()`. No exemplo acima, existe o `bufferedWriter.close()` que também chama o `flush()`, mas é uma boa prática usar o flush antes do close, garantindo que tudo seja escrito, e que não haja perda de dados, caso o programa seja finalizado de maneira não usual. Embora o `close()` garanta que os dados sejam gravados, o `flush()` oferece controle explícito sobre o momento da gravação, o que pode ser fundamental em determinadas situações. Portanto, é uma boa prática incluir `flush()` no código, especialmente quando é preciso que os dados sejam gravados imediatamente.

## Sobrescrever vs. Adicionar ao Final (`append`)
Para adicionar conteúdo ao final de um arquivo existente, usa-se o construtor `FileWriter(String nomeDoArquivo, boolean append)` com `append = true`.

```java
FileWriter writer = new FileWriter("arquivo.txt", true);
```
Caso contrário, o arquivo será sobrescrito.

## Leitura de Arquivos Texto

### 1. Leitura de Caracteres (`FileReader`)
```java
FileReader reader = new FileReader("arquivo.txt");
int character;
while ((character = reader.read()) != -1) {
    System.out.print((char) character);
}
reader.close();
```
O `read()` lê um caractere e retorna como um inteiro. Retorna `-1` quando chega ao final do arquivo.

### 2. Leitura de Linhas (`BufferedReader`)
```java
BufferedReader bufferedReader = new BufferedReader(new FileReader("arquivo.txt"));
String line;
while ((line = bufferedReader.readLine()) != null) {
    System.out.println(line);
}
bufferedReader.close();
```
O `BufferedReader` é uma classe que tem como sua principal função ler texto de um fluxo de entrada de caracteres, armazenando-os em buffer para otimizar a leitura. A leitura de arquivos diretamente do disco pode ser uma operação lenta. O `BufferedReader` resolve esse problema lendo grandes blocos de dados do arquivo e armazenando-os em um buffer na memória. Quando é solicitada a leitura de caracteres, o `BufferedReader` primeiro verifica se os caracteres já estão no buffer. Se estiverem, ele os retorna imediatamente, sem precisar acessar o disco. Isso reduz significativamente o número de operações de leitura no disco, resultando em uma leitura mais rápida e eficiente.

## Fechamento de Arquivos

É indispensável fechar os arquivos após a conclusão das operações de leitura ou gravação para liberar recursos do sistema e garantir que todos os dados sejam corretamente gravados. Isso pode ser feito de duas maneiras:

### 1. Fechamento Manual
```java
FileReader reader = new FileReader("arquivo.txt");
reader.close();

FileWriter writer = new FileWriter("arquivo.txt");
writer.close();
```

### 2. Fechamento Automático (`try-with-resources`)
O `try-with-resources` garante que os arquivos sejam fechados automaticamente, mesmo em caso de exceção.


#### Exemplo com `FileReader`
```java
try (FileReader reader = new FileReader("arquivo.txt")) {
    int character;
    while ((character = reader.read()) != -1) {
        System.out.print((char) character);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

#### Exemplo com `FileWriter`
```java
try (FileWriter writer = new FileWriter("arquivo.txt")) {
    writer.write("Olá, mundo!");
} catch (IOException e) {
    e.printStackTrace();
}
```

Quando se utiliza o `try-with-resources`, o método `close()` é chamado automaticamente no final do bloco try, garantindo que os recursos sejam liberados corretamente. Como o `close()` do `BufferedWriter` já inclui uma chamada ao `flush()`, geralmente não é necessário chamar `flush()` explicitamente dentro do bloco try. 

---
## Conclusão

Neste projeto, aprendemos a manipular arquivos de texto, binários e JSON, além de serializar objetos em Java. Também exploramos o uso de **Streams** para manipulação de coleções e aplicamos padrões de projeto como **MVC**, **Singleton** e **Strategy** para organizar e melhorar a estrutura do código.

### Recapitulando os Conceitos

1. **Manipulação de Arquivos:**
   - **Arquivos de Texto:** Leitura e escrita de dados em formato de texto.
   - **Arquivos Binários:** Leitura e escrita de dados em formato binário, ideal para grandes volumes de dados.
   - **Arquivos JSON:** Leitura e escrita de dados em formato JSON, amplamente utilizado para troca de dados entre sistemas.

2. **Serialização de Objetos:**
   - Transformação de objetos em uma sequência de bytes para armazenamento ou transmissão.
   - Uso da interface `Serializable` para marcar classes que podem ser serializadas.

3. **Streams:**
   - Manipulação de coleções de forma declarativa e funcional.
   - Operações como `filter`, `map`, `sorted` e `reduce` para processar dados de forma eficiente.

4. **Padrões de Projeto:**
   - **MVC (Model-View-Controller):** Separação de responsabilidades entre model (dados), view (interface) e controller (lógica).
   - **Singleton:** Garantia de que apenas uma instância de uma classe seja criada durante a execução do programa.
   - **Strategy:** Encapsulamento de cada método de persistência (texto, binário, JSON) em uma classe separada.

---

## Próximo Passo

- **Banco de Dados:** Integração com bancos de dados para persistência de dados.

Bons estudos!



