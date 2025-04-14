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

### 3. Implementação das Classes para trabalhar com Arquivos de Texto na SmartList

#### Classe `ListaDeCompras`

```java
public class ListaDeCompras {

    //código omitido

    public void salvarEmArquivoTexto(String nomeArquivo)  {
        if(!produtos.isEmpty()){
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


    public void carregarDeArquivoTexto(String nomeArquivo)  {
        produtos.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(" - ");
                produtos.add(new Produto(partes[0], Integer.parseInt(partes[1]), Double.parseDouble(partes[2])));
            }
            System.out.println("Lista do Arquivo de Texto");
                        System.out.println(this.toString());

        } catch (IOException e) {
            System.out.println("Erro ao carregar o arquivo: "+e.getMessage());
        }
    }

	//Código omitido
}
```

#### Classe `ListaDeComprasView`

Adaptar o método `exibirMenu()` para mostrar as novas funcionalidades:

```java
public void exibirMenu() {
    System.out.println("\n--- Gerenciador de Lista de Compras ---");
    System.out.println("1. Adicionar Produto");
    System.out.println("2. Remover Produto");
    System.out.println("3. Imprimir Lista");
    System.out.println("4. Salvar Lista em Arquivo de Texto");
    System.out.println("5. Carregar Lista de Arquivo de Texto");
    System.out.println("0. Sair");
    System.out.print("Escolha uma opção: ");
}
```

#### Classe `ListaDeComprasController`

Adequar o método `processarOpcao()`:

```java
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
        case 0:
            view.exibirMensagem("Saindo...");
            break;
        default:
            view.exibirMensagem("Opção inválida!");
    }
}
```
Ainda na classe  `ListaDeComprasController`, implementar os métodos `salvarEmAqrTexto()` e `carregarDeArqTexto()`:

```java
private void salvarEmAqrTexto() {
    model.salvarEmArquivoTexto("lista_compras.txt"); //ou "D:/dev/lista_compras.txt"

}

private void carregarDeArqTexto() {
    model.carregarDeArquivoTexto("lista_compras.txt"); //ou "D:/dev/lista_compras.txt"

}
```
#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. 

Realizar testes como: adicionar produtos na lista, salvar em arquivo texto, verificar se o arquivo foi criado, abrir o arquivo com o bloco de notas para ver o conteúdo, por fim, carregar a lista a partir do arquivo texto.

--- 
## Abertura e Criação de Arquivos Binários

Para manipular arquivos binários em Java, é necessário primeiro abrir ou criar o arquivo. Isso pode ser feito usando classes como `File`, `FileInputStream` e `FileOutputStream`.

### Criação de um Novo Arquivo para Gravação

```java
File file = new File("novo_arquivo.bin");
FileOutputStream outputStream = new FileOutputStream(file);
```

### Gravação de Arquivos Binários
A gravação de arquivos binários pode ser feita de duas formas principais:

- **Gravação de tipos primitivos (int, double, boolean, etc.) e Strings:** Usa-se a classe `DataOutputStream` em conjunto com `FileOutputStream`.
- **Gravação de objetos inteiros:** Usa-se a classe `ObjectOutputStream` junto com `FileOutputStream`.

### 1. Gravação de Bytes (Tipos Primitivos e String):
```java
String nome = "Maria";
int idade = 22;

try (DataOutputStream data = new DataOutputStream(new FileOutputStream("arquivo.bin"))) {
    data.writeUTF(nome);
    data.writeInt(idade);
} catch (IOException e) {
    e.printStackTrace();
}
```
A classe `DataOutputStream` pode ser usada para escrever dados primitivos e strings em um fluxo de saída binário. Alguns métodos úteis incluem:
- writeChar()
- writeDouble()
- writeFloat()
- writeInt()
- writeUTF()

### 2. Gravação de Objetos Inteiros (usando ObjectOutputStream):
```java
// A variável "pessoas" é um List<Pessoa>
try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pessoas.bin"))) { 
    oos.writeObject(pessoas); 
} catch (IOException e) {  
    e.printStackTrace();
}
```
Para gravar um objeto inteiro, a classe do objeto deve implementar a interface `Serializable`. Isso permite que os objetos dessa classe sejam serializados. O método `writeObject()` escreve o objeto no fluxo.

### Leitura de Arquivos Binários
Para abrir um arquivo binário existente para leitura, usa-se:
```java
File file = new File("arquivo.bin");
FileInputStream inputStream = new FileInputStream(file);
```

Tipos de leitura:

#### 1. Leitura de Bytes:
```java
StringBuilder sb = new StringBuilder();

try (DataInputStream data = new DataInputStream(new FileInputStream("arquivo.bin"))) {
    sb.append(data.readUTF()).append("\n")
      .append(data.readInt()).append("\n");
} catch (IOException e) {
    e.printStackTrace();
}

return sb.toString();
```
#### 2. Leitura de Objetos:
```java
// A variável "pessoas" é um List<Pessoa>
try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("pessoas.bin"))) {
    pessoas = (List<Pessoa>) ois.readObject();
} catch (IOException | ClassNotFoundException e) {
    e.printStackTrace();
}
```

---
## Serialização de Objetos

A serialização é o processo de transformar um objeto em uma sequência de bytes, tornando possível:
- Armazená-lo em um arquivo
- Transmiti-lo pela rede
- Persisti-lo em um banco de dados

### Por que serializar?
A serialização permite que um objeto seja recuperado posteriormente, mesmo após o programa ser encerrado. Além disso, é útil para troca de informações entre sistemas.

#### Salvando e Recuperando um Objeto em Arquivo

Quando se serializa um objeto, é possível:
- Gravar o objeto → Ele será armazenado como bytes no disco.
- Ler o objeto → Ele será convertido de volta no objeto original (desserialização).

### Serialização em Java

A serialização só é possível se a classe implementar a interface `Serializable`, do pacote `java.io`.
```java
import java.io.Serializable;

public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L; // Identificador de versão

    private String nome;
    private int idade;
    private transient String senha; // Atributo transient

    public Pessoa(String nome, int idade, String senha) {
        this.nome = nome;
        this.idade = idade;
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "Pessoa{nome='" + nome + "', idade=" + idade + ", senha='[TRANSIENT]'}";
    }
}
```
#### Considerações Importantes sobre Serialização

- **transient:** Um atributo marcado como transient não será serializado. É útil para informações sensíveis ou que não precisam ser armazenadas.
- **serialVersionUID:** Um identificador único para a versão da classe serializada. Se a classe mudar e o serialVersionUID não for compatível, pode ocorrer erro ao desserializar.
Se não for definido, o Java gerará um automaticamente, o que pode causar incompatibilidades futuras.

### 4. Implementação das Classes para trabalhar com Arquivos de Binários na SmartList

Para a SmartList, o tipo de persitência adotado será objetos inteiros. Para isso, modificar a classe Produto para implementar a classe `Serealizable`. 

#### Classe Produto

```java
package br.com.model;
import java.io.Serializable;

public class Produto implements Serializable{
    //Código omitido
}
```

#### Classe ListaDeCompras
```java
public class ListaDeCompras {
    //Código omitido

    public void salvarEmArquivoBinario(String nomeArquivo) {
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

    @SuppressWarnings("unchecked") // Suprime avisos de operações não verificadas, esta anotação é usada para silenciar aviso do compilador.
    public void carregarDeArquivoBinario(String nomeArquivo) {
        produtos.clear();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            produtos = (List<Produto>) ois.readObject();
        } catch (ClassNotFoundException | IOException e){
            System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
        }
    }
    //Código omitido
}
```

#### Classe ListaDeComprasView

```java
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
        System.out.println("7.Carregar Lista de Arquivo Binário");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    //Código omitido
}
```
#### Classe ListaDeComprasController

```java
public class ListaDeComprasController {
    //Código omitido

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
            case 0:
                view.exibirMensagem("Saindo...");
                break;
            default:
                view.exibirMensagem("Opção inválida!");
        }
    }

    //Código omitido

    private void salvarEmArquivoBinario(){
        model.salvarEmArquivoBinario("lista_compras.bin");
    }

    private void carregarDeArquivoBinario(){
        model.carregarDeArquivoBinario("lista_compras.bin");
    }
}
```
#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. 

Realizar testes como: adicionar produtos na lista, salvar em arquivo binário, verificar se o arquivo foi criado, por fim, carregar a lista a partir do arquivo binário.

---

### Detalhes importantes sobre a classe `File`:

Quando se faz várias chamadas como `new File("meuarquivo.dat")` no código-fonte, não está criando o arquivo no disco, apenas instanciando um objeto Java que representa o caminho (meuarquivo.dat). Esse objeto não altera o arquivo físico.

1. `new File()` não cria o arquivo no disco. O exemplo abaixo só cria um objeto em memória que aponta para o caminho meuarquivo.dat:
```java
File arquivo = new File("meuarquivo.dat");
```
O arquivo físico só é criado se você chamar métodos como:
```java
arquivo.createNewFile(); // Cria o arquivo vazio (se não existir)
```
2. Várias instâncias `new File()` referem-se ao mesmo arquivo físico. Se você criar múltiplos objetos `File` com o mesmo caminho, todos representarão o mesmo arquivo no disco:
```java
File arquivo1 = new File("meuarquivo.dat");
File arquivo2 = new File("meuarquivo.dat");
```
- `arquivo1` e `arquivo2` são objetos diferentes na memória, mas ambos apontam para o mesmo arquivo físico.
- Qualquer operação (leitura/escrita) feita por um afetará o outro (já que o alvo é o mesmo).

3. Quando o arquivo físico é realmente criado/modificado?
- Criação:
```java
arquivo.createNewFile(); // Método explícito
new FileOutputStream("meuarquivo.dat"); // Cria/sobrescreve o arquivo
```
- Modificação:
Só ocorre quando você usa classes de I/O (`FileWriter`, `FileInputStream`, etc.).
- Exemplo:
```java
File arquivo1 = new File("dados.txt");
File arquivo2 = new File("dados.txt");

System.out.println(arquivo1 == arquivo2); // false (objetos diferentes na memória)
System.out.println(arquivo1.equals(arquivo2)); // true (mesmo caminho)

// O arquivo físico só é criado aqui:
arquivo1.createNewFile(); // Agora "dados.txt" existe no disco!

// Ambos os objetos continuam referindo-se ao mesmo arquivo:
System.out.println(arquivo2.exists()); // true (arquivo1 criou o arquivo físico)
```
#### Resumindo:
- A classe `File` é apenas uma representação abstrata do caminho no sistema de arquivos;
- `new File()` é apenas uma "referência" ao caminho do arquivo, sem impacto no disco;
- Várias instâncias com o mesmo caminho referem-se ao mesmo arquivo físico;
- O arquivo só é criado/modificado quando você usa operações de I/O explícitas.
---
## JSON (JavaScript Object Notation)

JSON é um formato de troca de dados que é fácil para humanos lerem e escreverem, e fácil para máquinas interpretarem e gerarem. Ele é muito utilizado para transmitir dados entre um servidor e uma aplicação web, ou entre diferentes partes de um sistema. Possui uma estrutura hierárquica que permite organizar objetos e arrays aninhados.

### Sintaxe do JSON

O JSON é composto por:

#### Objetos
- Um objeto é uma coleção de pares chave-valor, onde cada chave é uma string e o valor pode ser uma string, número, booleano, array, outro objeto ou `null`.
- Os objetos são delimitados por chaves `{}`.
- Exemplo:
  ```json
  {
    "nome": "Maria",
    "idade": 22,
    "estudante": false,
    "endereco": {
      "rua": "Rua das Araras",
      "numero": 998,
      "cidade": "Hermoso do Sul"
    }
  }
  ```

#### Arrays
- Um array é uma lista ordenada de valores, que podem ser strings, números, booleanos, objetos, outros arrays ou `null`.
- Os arrays são delimitados por colchetes `[]`.
- Exemplo:
```json
[
  {"nome": "Maria", "idade": 22},
  {"nome": "João", "idade": 34},
  {"nome": "Ana", "idade": 27}
]
```
---

### 5.Trabalhando com JSON no Projeto SmartList

Para trabalhar com JSON neste projeto, será utilizada a biblioteca `Jackson`. Ela possui métodos para converter objetos Java para JSON (serialização) e JSON para objetos Java (desserialização).

#### Adicionando a Dependência do `Jackson`
Para usar a biblioteca `Jackson`, adicione a seguinte dependência no arquivo `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.18.2</version>
    </dependency>
</dependencies>
```

Após adicionar uma dependência no arquivo `pom.xml`, é necessário realizar algumas etapas para garantir que a dependência seja corretamente baixada e disponibilizada para uso no projeto:
- **Salvar o Arquivo `pom.xml`:** Após adicionar a dependência, salve o arquivo `pom.xml`. Isso garante que as alterações sejam registradas.
- **Atualizar o Projeto no Maven:** O Maven precisa atualizar o projeto para baixar a nova dependência e configurar o classpath corretamente. No IntelliJ IDEA, abra o arquivo `pom.xml` e pressione CTRL+SHIFT+O, ou clique na opção destacada na imagem abaixo:
  
   ![image](https://github.com/user-attachments/assets/3e573f87-60ec-4235-90be-6640ac91337e)


### Configurando a Classe Produto
Para que o `Jackson` consiga realizar a desserialização (converter JSON para um objeto Java), a classe Produto deve ter um construtor padrão (sem argumentos). Esse construtor é necessário para o Jackson criar uma instância da classe e preencher os campos usando os setters ou acessar diretamente os campos (mesmo que sejam privados) usando reflexão.

#### Classe Produto

```java
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

    // Getters e Setters (omitidos)
}
```

#### Classe ListaDeCompras

```java
public class ListaDeCompras {
    private List<Produto> produtos;

    //código omitido

    public void salvarEmArquivoJson(String nomeArquivo) {
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

    public void carregarDeArquivoJson(String nomeArquivo)  {
        produtos.clear();
        try  {
            ObjectMapper objectMapper = new ObjectMapper();
            produtos = objectMapper.readValue(new File(nomeArquivo), objectMapper.getTypeFactory().constructCollectionType(List.class, Produto.class));
        } catch (IOException e){
            System.out.println("Erro ao salvar o arquivo: "+e.getMessage());
        }
    }
    //Código omitido
}
```

#### Classe ListaDeCompraView
```java
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
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    //Código omitido
}
```

#### Classe ListaDeComprasController

```java
public class ListaDeComprasController {
    //Código omitido

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
            case 0:
                view.exibirMensagem("Saindo...");
                break;
            default:
                view.exibirMensagem("Opção inválida!");
        }
    }

    //Código omitido

    private void salvarEmArquivoJson(){
        model.salvarEmArquivoJson("lista_compras.json");
    }

    private void carregarDeArquivoJson(){
        model.carregarDeArquivoJson("lista_compras.json");
    }
}
```

#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. 

Realizar testes como: adicionar produtos na lista, salvar em arquivo json, verificar se o arquivo foi criado, abrir o arquivo com o bloco de notas para ver o conteúdo (que deve ser semelhante ao json abaixo), por fim, carregar a lista a partir do arquivo json.

```json
[
  {
    "nome": "Arroz",
    "quantidade": 2,
    "preco": 10.5
  },
  {
    "nome": "Feijão",
    "quantidade": 3,
    "preco": 8.0
  }
]
```
--- 
## Manipulando Coleções com Streams em Java

Uma Stream em Java representa uma sequência de elementos, permitindo a execução de operações em cadeia. Elas não armazenam dados diretamente, mas sim fornecem um mecanismo para processar dados em um fluxo contínuo. Em vez de iterar sobre cada elemento individualmente, as streams permitem aplicar uma sequência de operações a um conjunto de dados, resultando em um novo conjunto. Além disso, elas simplificam a manipulação e transformação de dados e permitem agrupar elementos com base em critérios específicos. 

A API Streams, introduzida no Java 8, é inspirada em conceitos da programação funcional. Alguns desses conceitos são: 
    • **Tratamento de dados de forma declarativa:** em vez de especificar passo a passo como iterar sobre uma coleção e realizar operações, as Streams permitem declarar o que deseja fazer com os dados, e a biblioteca se encarrega de como realizar essa operação. Desta forma, é possível encadear diversas operações em um fluxo de dados de forma declarativa, facilitando a criação de pipelines de processamento complexos, mas legíveis. 
    • **Imutabilidade:** As operações em Streams geralmente retornam novas coleções, em vez de modificar a original. Isso ajuda a evitar efeitos colaterais e torna o código mais seguro e previsível. 
    • **Funções de alta ordem:** Streams utilizam funções de alta ordem (como `map`, `filter`, `reduce`) para transformar e manipular dados. Essas funções aceitam outras funções como parâmetro, permitindo uma grande flexibilidade na criação de pipelines de processamento. Mais informações sobre programação funcional [aqui](https://www.alura.com.br/artigos/programacao-funcional-o-que-e?srsltid=AfmBOopwAbT_H2BnFmwFSfLpw2yFoLjEJJCYEz6BkjQqgIrVsgeBumqD) e [aqui](https://medium.com/@marcelomg21/programação-funcional-teoria-e-conceitos-975375cfb010). 

#### Entendendo uma Stream

Analisando o exemplo a seguir:

![image](https://github.com/user-attachments/assets/a8696504-7ba5-4c1a-acce-c5d39207547a)

- **1. Criação de uma Stream:** A chamada a `numbers.stream()` transforma a lista de números em uma Stream, que é uma sequência de elementos sobre a qual podemos aplicar operações.
- **2. Composição de funções:**
    - `filter`: função de alta ordem que recebe um predicado (uma função que retorna um booleano) e retorna um novo Stream contendo apenas os elementos que satisfazem o predicado. Neste caso, os números pares. 
    - `map`: função também de alta ordem que recebe uma função que transforma cada elemento da Stream. Neste caso, cada número é multiplicado por 2. 

- **3. Coleta dos resultados:** A operação `collect` transforma o Stream de volta em uma lista, coletando os resultados das operações anteriores.

#### Observações adicionais:
    • Em vez de escrever loops explícitos, foi dito o que deveria ser feito (forma declarativa). 
    • Código mais fácil de entender e manter. 
    • Pode-se encadear diversas operações em fluxo contínuo. 
    • Foram usadas funções de alta ordem para processar e manipular os dados. 
    • O resultado é uma nova lista, mantendo a lista original (imutabilidade). 
    • As operações intermediárias (`filter` e `map`) são executadas apenas quando a operação terminal é invocada. 

### Exemplos com e sem Stream:

| Com                             | Sem                             |
|---------------------------------|---------------------------------|
| ![image](https://github.com/user-attachments/assets/233e844d-faf0-44ee-b4ba-6f7d84b6d3cb) |  ![image](https://github.com/user-attachments/assets/a469e1c1-203a-432e-bc0b-eaeac8ffcfee)|

### operador `→`

Chamado de operador lambda, é uma característica da programação funcional. É usado para criar expressões lambda, que são funções anônimas. 

As funções anônimas, são funções que não possuem um nome explícito. São definidas diretamente no local onde são utilizadas, geralmente como parte de uma expressão maior. Muito usadas quando se trata de operações simples ou quando a função é utilizada apenas uma vez. No exemplo, o operador `→` permitiu definir as funções de filtragem e de mapeamento, utilizando expressão lambda.

#### Estrutura básica de uma expressão Lambda

| Sintaxe Geral                                      | Exemplos                                                                 |
|----------------------------------------------------|--------------------------------------------------------------------------|
| `(param1, param2, …) -> { corpo_da_expressão }`    |                                                                          |
| - **Parâmetros:** Dentro de parênteses, são as entradas da função lambda. | - **Lambda sem parâmetros:** `() -> System.out.println("Olá")`           |
| - **Operador:** `->` separa os parâmetros do corpo da expressão.          | - **Lambda com um parâmetro:** `(a) -> a * a`                            |
| - **Corpo da Expressão:** É o código que será executado.                  | - **Lambda com múltiplos parâmetros:** `(a, b) -> a + b`                 |

#### Criando e utilizando Streams

Streams podem ser criados a partir de diversas fontes de dados, como listas, arrays ou arquivos. O método stream() é usado para converter uma coleção em uma stream:
- **1. Criação da Stream:** realizando com o método `stream()` em uma coleção como `List` ou `Set`.
- **2. Operações Intermediárias:** Filtram, mapeam e transformam os dados do stream.
- **3. Operações Terminais:** Realizam ações como coletar os dados modificados em uma nova coleção.

#### Métodos comuns em Streams - Operações intermediárias:

- **filter()**: filtra elementos com base em uma condição.
	#### Sintaxe:
	`stream.filter(elemento -> condição);`
	#### Exemplo:
	![image](https://github.com/user-attachments/assets/8468ba54-e03a-4ea2-aaf4-06d307fb94d5)

- **map()**: aplica uma função a cada elemento e retorna uma Stream com os resultados.
  	#### Sintaxe:
  	`stream.map(elemento -> transformacao);`
  	#### Exemplo: 
  	![image](https://github.com/user-attachments/assets/a8d22846-eba3-44e0-a409-d47fe1735ac6)

- **sorted()**: ordena os elementos da Stream.
	#### Sintaxe:
	- `stream.sorted()` // sem parâmetros
   	- `stream.sorted((e1, e2) -> comparador)` // com comparador que define a lógica de comparação
   	- `stream.sorted(Comparator.comparing(Class::method))` // com método de referência para o comparador
  	#### Exemplo:
  	![image](https://github.com/user-attachments/assets/871f4656-b022-42e7-87d3-4b546ddf6193)

#### Métodos comuns em Streams - Operações terminais:

- **collect()**: coleta os elementos em uma coleção ou outra estrutura de dados, usando coletores.
	#### Sintaxe:
	- `Collectors.toList()`
 	- `Collectors.toSet()`
	- `Collectors.joining(delimiter,prefix,sufix)`
   	#### Exemplo:
  	![image](https://github.com/user-attachments/assets/eadcf886-c2d3-4f8b-af20-7ee38ace61eb)

- **forEach()**: executa uma ação para cada elemento.
	#### Sintaxe:
	`stream.forEach(elemento->ação)`
	#### Exemplo:
	![image](https://github.com/user-attachments/assets/dece7adf-4ed3-4ba3-a370-a358e2a681a6)

- **reduce()**: reduz os elementos para um único valor, ideal para operações como soma e multiplicação.
	#### Sintaxe:
	- Pode usar um operador binário para combinar 2 elementos `(stream.reduce((a,b)->a+b))`
 	- e usar também um valor inicial para a acumulação `(stream.reduce(0, (a,b)->a+b))` 
	#### Exemplo:
	![image](https://github.com/user-attachments/assets/492af860-a89d-480f-9526-e9156af3b418)

#### Operador `::`

Conhecido como referência a método, é usado para referenciar métodos de forma direta para serem usados em operações de Stream, sem precisar escrever a expressão lambda completa. 

##### Quando usar?

- Referências a métodos estáticos - `Class::staticMethod`
![image](https://github.com/user-attachments/assets/83051dc9-11e7-4973-b4c1-b2ac5d68c8da)

- Referências a métodos de instância - `instance::instanceMethod` ou `Class::instanceMethod`
![image](https://github.com/user-attachments/assets/621fd2a3-c9d8-4649-8e49-149b0137e6f7)

- Referências a construtores - `Class::new`
![image](https://github.com/user-attachments/assets/cb559422-feee-42e3-9150-4a642eac16f3)

#### Exemplo de uso:
| ![image](https://github.com/user-attachments/assets/cdce95b7-9a78-4684-ad54-4fd081db6791) | ![image](https://github.com/user-attachments/assets/b972fc3a-82b0-432f-8333-cbbfb4163cb2)| 
| ------------------------------------------------------------------------------------------| -----------------------------------------------------------------------------------------| 

Aplicando os conceitos de Stream para manipulações coleções no desenvolvimento das funcionalidades: 10. Filtrar Produtos por Quantidade Mínima, 12.Calcular Valor Total da Lista e 
13.Imprimir Lista em Ordem Alfabética:

#### Classe ListaDeCompras

```java
public class ListaDeCompras {
    private List<Produto> produtos;

    //Código omitido

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


    //código omitido
}
```

#### Classe ListaDeComprasView
```java
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

    //Código omitido

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
```

#### Classe ListaDeComprasController

```java
public class ListaDeComprasController {
    // Código omitido

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

    //Código omitido

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
```

#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. 

Realizar testes como: adicionar produtos na lista, acessar a funcionalidade 10, informar uma quantidade mínima e verificar o resultado; acessar as funcionalidades 11 e 12 e verificar os resultados.

## Melhorando/Refatorando o Código

É possível melhorar o códogo da SmartList construído até o momento. Uma dessas melhorias é a aplicação do padrão de projeto `Singleton`, para garantir que apenas uma instância da lista de compras seja criada durante a execução do programa. Para isso, as seguintes modificações devem ser feitas:

#### Classe ListaDeCompras
```java
public class ListaDeCompras {
    private static ListaDeCompras instancia;
    private List<Produto> produtos;

    public ListaDeCompras() {
        produtos = new ArrayList<>();
    }

    public static ListaDeCompras getInstancia() {
        if (instancia == null) {
            instancia = new ListaDeCompras();
        }
        return instancia;
    }
    //Código omitido
}
```

#### Classe Main
```java
public class Main {
    public static void main(String[] args) {
        //ListaDeCompras model = new ListaDeCompras();
        ListaDeCompras model = ListaDeCompras.getInstancia();
        
        //Código omitido
    }
}
```

Outra melhoria possível é permitir que o usuário escolha dinamicamente o método de persistência (texto, binário, JSON ), para isso, pode-se usar o padrão Strategy para encapsular cada método em uma classe separada. Para isso, será criada a interface `PersistenciaStrategy` a seguir:

#### Interface PersistenciaStrategy

```java
package br.com.model;

import java.util.List;

public interface PersistenciaStrategy {
    void salvar(List<Produto> produtos, String caminhoArquivo);
    List<Produto> carregar(String caminhoArquivo);
}
```

Na sequência, as classes que implementam a interface:

#### Classe PersistenciaTexto

```java
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
```

#### Classe PersistenciaBinario

```java
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
```

#### Classe PersistenciaJson

```java
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
```

Com as novas classes, adequar as classes `ListaDeCompras` e `ListaDeComprasController`.

#### Classe ListaDeCompras

```java
public class ListaDeCompras {
    private static ListaDeCompras instancia;
    private List<Produto> produtos;
    private PersistenciaStrategy estrategiaPersistencia;

    // Código omitido


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
    
    // Código omitido
}
```

#### Classe ListaDeCompras

```java
public class ListaDeComprasController {
    
	//Código omitido

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

    //Código omitido
}
```
#### Testando o programa

Acessar o menu Run > Run 'Main.java' ou pressione Shift + F10. 

Realizar testes para verificar se as funcionalidades continuam funcionando como o esperado.


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



