package com.example.demo.models.DAO;

import com.example.demo.models.DAO.ProdutoDAO;
import com.example.demo.models.Product;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    static ProdutoDAO dao = new ProdutoDAO();

    public static void main(String[] args) {
        try {

            Scanner ler = new Scanner(System.in);

            System.out.println("Oque voce deseja fazer: \n 1 - Inserir produto  \n 2 - Vender produto \n 3 - Mostrar saldo do estoque \n 4 - Sair  ");
            int option = ler.nextInt();

            if (option == 1) {
                Product product = new Product();

                System.out.println("Digite o nome do produto que deseja inserir: ");
                product.setProductName(ler.next());
                System.out.println("Digite o id do produto que deseja inserir: ");
                product.setProductId(ler.nextInt());
                System.out.println("Digite a quantidade do produto que deseja inserir: ");
                product.setProductQuantity(ler.nextInt());

                dao.insert(product);
                main(args);
            } else if (option == 2) {

                System.out.println("Digite o código do produto que deseja vender: ");
                int id = ler.nextInt();
                Product product = dao.findOneById(id);
                if (product == null) {
                    System.out.println("Produto não encontrado: " + id);
                    main(args);
                    return;
                }
                System.out.println("Produto:  " + product);

                int quantidadeParaSerVendida = 5;

                if (product.getProductQuantity() < quantidadeParaSerVendida) {
                    System.out.println("Sem estoque disponivel: " + product.getProductQuantity());
                    main(args);
                    return;
                }

                int quantidade = product.getProductQuantity() - quantidadeParaSerVendida;
                product.setProductQuantity(quantidade);
                dao.update(product);
                System.out.println("Vendido: " + id + " quantidade: " + quantidade);
                main(args);
                return;
            } else if (option == 3) {
                ArrayList<Product> lista = dao.findAll();

                lista.forEach(product -> {
                    System.out.print("Produto ID:" + product.getProductId() + "\n");
                    System.out.print("Produto Name:" + product.getProductName() + "\n");
                    System.out.print("Produto Quantidade:" + product.getProductQuantity() + "\n\n");
                });
                main(args);
            } else if (option == 4) {
                System.exit(1);
            } else {
                main(args);
            }
        } catch (Exception e) {
            System.out.println("Desculpa deu erro, tente dnv \n" + e.getMessage());
            main(args);
        }
    }
}
