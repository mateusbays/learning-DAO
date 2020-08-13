package com.example.demo.models;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

//@SpringBootApplication
public class ControlStore {
    static ArrayList<Product> productList = new ArrayList();
    private static Object SQLException;

    public static void main(String[] args) {
        //SpringApplication.run(ControlStore.class, args);
        try {
            int option;
            Scanner ler = new Scanner(System.in);

            System.out.println("Oque voce deseja fazer: \n 1 - Inserir produto  \n 2 - Vender produto \n 3 - Mostrar saldo do estoque \n 4 - Sair  ");

            option = ler.nextInt();

            if (option == 1) {
                inserirProduto(args, productList, ler);
                return;
            } else if (option == 2) {
                vender(args, ler);
            } else if (option == 3) {
                mostrarSaldo(args);
            } else if (option == 4) {
                System.exit(1);
            } else {
                main(args);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Desculpa deu erro, tente dnv");
            main(args);
        }
    }

    private static void mostrarSaldo(String[] args) throws SQLException {
        /*productsList.stream().forEach(
                produto -> System.out.println("Nome do produto: " + produto.getProductName() + "\n - Codigo do produto: " + produto.getProductId() + "\n - Quantidade do produto: " + produto.getProductQuantity())
        )*/;
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/controlstore", "root", "denner");

            String query2 = "SELECT * from produto";

            Statement st = conn.createStatement();

            ResultSet linha = st.executeQuery(query2);

            while (linha.next())
            {
                String productName = linha.getString("productName");
                int productID = linha.getInt("productID");
                int productQTT = linha.getInt("productQTT");

                System.out.format("Código do produto : %s \n ,  Nome do produto: %s \n,  Quantidade: %s \n", productID, productName, productQTT);
            }
            st.close();


            conn.close();


        main(args);
    }

    private static void vender(String[] args, Scanner ler) throws SQLException {
        System.out.println("Digite o codigo do produto que deseja vender: ");
        int codigo = ler.nextInt();

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/controlstore", "root", "denner");
        String query = "SELECT * FROM produto WHERE productID = " + codigo;
        Statement st = conn.createStatement();
        ResultSet linha = st.executeQuery(query);

        if (linha.getRow() != '0') {
            System.out.println("Digite a quantidade que deseja vender: ");
            int quantidade = ler.nextInt();
            while (linha.next())
            {
                int qtd = linha.getInt("productQTT");
        if (qtd > quantidade) {
            String query2 = "UPDATE produto SET productQTT =" +(qtd-quantidade)+ " WHERE productID =" + codigo;
            Statement st2 = conn.createStatement();
            st2.executeUpdate(query2);
            System.out.println("Quantidade removida do sistema: " + quantidade);
            main(args);
            return;
        } else if (qtd == quantidade) {
            String query3 = "DELETE FROM produto WHERE productID =" + codigo;
            Statement st3 = conn.createStatement();

            if (st3.execute(query3) == false) {
                System.out.println("Produto removido do sistema");
                main(args);
            }
        } else {
            System.out.println("Vocë tem menos produtos do que deseja vender, reinicie o processo.");
            main(args);
        }
    }
        } else {
            System.out.println("Produto nao encontrado, deseja inserir?\n 1 - Sim \n 2 - Nao ");
            int inserir = ler.nextInt();
            if (inserir == 1) {
                inserirProduto(args, productList, ler);
                return;
            } else {
                main(args);
            }
        }
    }

    private static void inserirProduto(String[] args, ArrayList<Product> productList, Scanner ler) throws SQLException {


        Product produto = new Product();


        System.out.println("Qual o nome do produto que deseja inserir? ");
        produto.setProductName(ler.next());
        System.out.println("Qual o codigo do produto que deseja inserir? ");
        int codigo = ler.nextInt();
        produto.setProductId(codigo);
      
        System.out.println("Qual a qtd do produto que deseja inserir? ");
        int  quantidade = ler.nextInt();
        produto.setProductQuantity(quantidade);

        System.out.println(verificarCodigo(codigo));
        if(verificarCodigo(codigo)){
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/controlstore", "root", "denner");
                String query1 = "INSERT INTO produto (productName, productQTT, productID)" + "VALUES(?,?,?)";

                PreparedStatement preparedStmt = conn.prepareStatement(query1);
                preparedStmt.setString(1, produto.getProductName());
                preparedStmt.setInt(2, produto.getProductQuantity());
                preparedStmt.setInt(3, produto.getProductId());

                // execute the preparedstatement
                preparedStmt.execute();
                System.out.print("Produto inserido ao banco");
                conn.close();
                main(args);
                return;
            } catch (Exception e) {
                System.err.println("Produto nao inserido ao banco! \n ");
                System.err.println(e.getMessage());
            }
        }else{
            System.out.println("O produto já existe no banco, deseja somar a quantidade do produto? \n 1 - SIM \n 2 - NÃO \n");
            int escolha = ler.nextInt();

            switch(escolha){
                case 1:
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/controlstore", "root", "denner");
                    String query1 = "SELECT productQTT FROM produto WHERE productID ="+codigo;

                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery(query1);

                    while(rs.next()) {
                        int qtd = rs.getInt("productQTT") + quantidade;

                        String query2 = "UPDATE produto SET productQTT =" + qtd + " WHERE productID =" + codigo;

                        Statement st2 = conn.createStatement();
                        st2.executeUpdate(query2);

                        System.out.print("Produto inserido ao banco \n");
                    }
                    conn.close();
                    main(args);
                    return;
                case 2:
                    main(args);
                    return;
            }

        }
    }

    private static boolean verificarCodigo(int codigo) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/controlstore", "root", "denner");

        String query3 = "SELECT * FROM produto WHERE productID = "+codigo;

        Statement st = conn.createStatement();

        ResultSet linha = st.executeQuery(query3);
        if(linha.next()){
            st.close();
            conn.close();
            return false;
        }else{
            conn.close();
            return true;
        }
    }

}












