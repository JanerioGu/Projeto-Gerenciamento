import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static final String ARQUIVO = "produtos.txt";
    private static final String ARQUIVO2 = "pedidos.txt";

    private static ArrayList<Pedido> pedidos = new ArrayList<>();
    private static ArrayList<Produto> produtos = new ArrayList<>();

    public static void main(String[] args) {
        carregarProdutos();
        carregarPedidos(); // Adicionado para carregar os pedidos no início

        Scanner scanner = new Scanner(System.in);
        int opcao;

        while (true) {
            System.out.println("Escolha uma opcao:");
            System.out.println("1. Produtos");
            System.out.println("2. Pedidos");
            System.out.println("3. Sair");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    while (true) {
                        System.out.println("1. Incluir produto:");
                        System.out.println("2. Editar quantidade estoque produto:");
                        System.out.println("3. Editar preço unitário produto:");
                        System.out.println("4. Excluir produto:");
                        System.out.println("5. Listagem produtos:");
                        System.out.println("6. Voltar ao menu principal:");
                        int opcaoProduto = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcaoProduto) {
                            case 1:
                                incluirProduto(scanner);
                                salvar();
                                break;
                            case 2:
                                editarQuantidade(scanner);
                                salvar();
                                break;
                            case 3:
                                editarPreco(scanner);
                                salvar();
                                break;
                            case 4:
                                excluirProduto(scanner);
                                salvar();
                                break;
                            case 5:
                                listarProdutos();
                                break;
                            case 6:
                                break;
                            default:
                                System.out.println("Opção invalida.");
                                break;
                        }
                        if (opcaoProduto == 6) break;
                    }
                    break;
                case 2:
                    int opcaoPedido;
                    while (true) {
                        System.out.println("Escolha uma opcao:");
                        System.out.println("1. Novo Pedido");
                        System.out.println("2. Listagem Pedidos");
                        System.out.println("3. Voltar ao Menu Principal");
                        opcaoPedido = scanner.nextInt();
                        switch (opcaoPedido) {
                            case 1:
                                novoPedido(scanner);
                                salvarPedidos();
                                break;
                            case 2:
                                listagemPedidos();
                                break;
                            case 3:
                                salvarPedidos();
                                break;
                        }
                        if (opcaoPedido == 3) {
                            break;
                        }
                    }
                    break;
                case 3:
                    salvar();
                    salvarPedidos(); // Chama o método para salvar os pedidos no arquivo ao sair
                    System.exit(0);
                default:
                    System.out.println("Opção invalida.");
                    break;
            }
        }
    }

    private static void carregarProdutos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] line = linha.split(";");
                int codigoProduto = Integer.parseInt(line[0]);
                String nomeProduto = line[1];
                double precoUnitario = Double.parseDouble(line[2]);
                int quantidadeEstoque = Integer.parseInt(line[3]);
                Produto produto = new Produto(codigoProduto, nomeProduto, precoUnitario, quantidadeEstoque);
                produtos.add(produto);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
    }

    private static void carregarPedidos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO2))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] line = linha.split(";");
                int numeroPedido = Integer.parseInt(line[0]);
                int codigoProduto = Integer.parseInt(line[1]);
                double precoUnitario = Double.parseDouble(line[2]);
                int quantidade = Integer.parseInt(line[3]);
                Pedido pedido = new Pedido(numeroPedido, codigoProduto, precoUnitario, quantidade);
                pedidos.add(pedido);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar arquivo: " + e.getMessage());
        }
    }

    private static void salvar() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Produto produto : produtos) {
                writer.write(produto.getCodigoProduto() + ";" + produto.getNomeProduto() + ";" + produto.getPrecoUnitario() + ";" + produto.getQuantidadeEstoque() + ";");
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    private static void salvarPedidos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO2))) {
            for (Pedido pedido : pedidos) {
                writer.write(pedido.getNumero() + ";" + pedido.getCodigo() + ";" + pedido.getPreco() + ";" + pedido.getQuantidade());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    private static void incluirProduto(Scanner scanner) {
        int codigoProduto = produtos.size() + 1;
        System.out.println("Insira o nome do produto: ");
        String nomeProduto = scanner.nextLine();

        System.out.println("Digite o preço unitario do produto: ");
        double precoUnitario = Double.parseDouble(scanner.nextLine());

        System.out.println("Digite a quantidade em estoque do Produto: ");
        int quantidadeEstoque = Integer.parseInt(scanner.nextLine());

        Produto produto = new Produto(codigoProduto, nomeProduto, precoUnitario, quantidadeEstoque);
        produtos.add(produto);
        System.out.println("Produto incluido com sucesso!");
    }

    private static void editarQuantidade(Scanner scanner) {
        System.out.print("Digite o ID do produto que deseja alterar a quantidade: ");
        int codigoProduto = Integer.parseInt(scanner.nextLine());
        for (Produto produto : produtos) {
            if (produto.getCodigoProduto() == codigoProduto) {
                System.out.print("Digite a nova quantidade em estoque: ");
                int novaQuantidade = Integer.parseInt(scanner.nextLine());
                produto.setQuantidadeEstoque(novaQuantidade);
                System.out.println("Quantidade alterada com sucesso!");
                return;
            }
        }
        System.out.println("ID do produto não encontrado!");
    }

    private static void excluirProduto(Scanner scanner) {
        System.out.print("Digite o ID do produto: ");
        int codigoProduto = Integer.parseInt(scanner.nextLine());
        for (Produto produto : produtos) {
            if (produto.getCodigoProduto() == codigoProduto) {
                produtos.remove(produto);
                System.out.println("Produto excluido com sucesso!");
                return;
            }
        }
        System.out.println("ID do produto não encontrado!");
    }

    private static void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            System.out.println("Lista de produtos:");
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
    }

    private static void editarPreco(Scanner scanner) {
        System.out.print("Digite o ID do produto que deseja alterar o preço: ");
        int codigoProduto = Integer.parseInt(scanner.nextLine());
        for (Produto produto : produtos) {
            if (produto.getCodigoProduto() == codigoProduto) {
                System.out.print("Digite o novo preço unitário: ");
                double novoPreco = Double.parseDouble(scanner.nextLine());
                produto.setPrecoUnitario(novoPreco);
                System.out.println("Preço unitário alterado com sucesso!");
                return;
            }
        }
        System.out.println("ID do produto não encontrado!");
    }

    private static void novoPedido(Scanner scanner) {
        int numeroPedido = 1;
        for (Pedido pedido : pedidos) {
            if (pedido.getNumero() >= numeroPedido) {
                numeroPedido = pedido.getNumero() + 1;
            }
        }

        System.out.println("Digite o codigo do produto desejado: ");
        int codigoProd = scanner.nextInt();
        System.out.println("Digite a quantidade desejada: ");
        int quantidade = scanner.nextInt();

        double valor = 0;
        for (Produto produto : produtos) {
            if (produto.getCodigoProduto() == codigoProd) {
                if (produto.getQuantidadeEstoque() >= quantidade) {
                    produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
                    valor = produto.getPrecoUnitario();
                    Pedido pedido = new Pedido(numeroPedido, codigoProd, valor, quantidade);
                    pedidos.add(pedido);
                    System.out.println("Pedido adicionado!");
                    return;
                } else {
                    System.out.println("Indisponível! Estoque atual: " + produto.getQuantidadeEstoque());
                    return;
                }
            }
        }
        System.out.println("Produto não encontrado!");
    }

    private static void listagemPedidos() {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println("Pedido número: " + pedido.getNumero());
                System.out.println("Produto                                         Preço unitário   Quantidade   Subtotal");
                System.out.println("------------------------------------------------------------------------------------------");

                double totalPedido = 0;
                for (Produto produto : produtos) {
                    if (pedido.getCodigo() == produto.getCodigoProduto()) {
                        double subtotal = produto.getPrecoUnitario() * pedido.getQuantidade();
                        totalPedido += subtotal;
                        System.out.printf("%-48s %15.1f %10d %12.1f\n", produto.getNomeProduto(), produto.getPrecoUnitario(), pedido.getQuantidade(), subtotal);
                    }
                }
                System.out.println("------------------------------------------------------------------------------------------");
                System.out.printf("Valor total pedido: %.1f\n", totalPedido);
                System.out.println();
            }
        }
    }
}
