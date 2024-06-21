public class Pedido {
    private int numeroPedido;
    private int codigoProduto;
    private double precoUnitario;
    private int quantidade;
    
        public Pedido(int numero, int codigo, double preco, int quantidade) {
            this.numeroPedido = numero;
            this.codigoProduto = codigo;
            this.precoUnitario = preco;
            this.quantidade = quantidade;
        }

        public int getNumero() {
            return numeroPedido;
        }
    
        public int getCodigo() {
            return codigoProduto;
        }
    
        public double getPreco() {
            return precoUnitario;
        }

        public void setPreco(double preco) {
            this.precoUnitario = preco;
        }
    
        public int getQuantidade() {
            return quantidade;
        }
    
        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
    }
