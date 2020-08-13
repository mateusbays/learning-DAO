package com.example.demo.models.DAO;

import com.example.demo.models.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutoDAO {

    private SqlConnection sqlConnection;


    public ProdutoDAO() {
        sqlConnection = new SqlConnection();
    }

    public void insert(Product product) {
        try {
            String query = "INSERT INTO produto (productName, productQTT, productID)" + "VALUES(?,?,?)";
            PreparedStatement ps = sqlConnection.getConnection().prepareStatement(query);

            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getProductQuantity());
            ps.setInt(3, product.getProductId());

            // execute the preparedstatement
            ps.execute();
            ps.close();
            System.out.print("Produto inserido ao banco \n");
        } catch (SQLException ex) {
            System.out.println("Erro no SELECT." + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Erro geral." + ex.getMessage());
        }
    }

    public ArrayList<Product> findAll() throws SQLException {
        try {
            String query = "SELECT * FROM produto";
            return getProducts(query);

        } catch (SQLException ex) {
            System.out.println("Erro no SELECT." + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Erro geral." + ex.getMessage());
        }
        return null;
    }

    public Product findOneById(int id) {
        try {
            String query = "SELECT * FROM produto where productID = " + id;
            List<Product> produtos = getProducts(query);
            if (produtos.size() > 0) {
                return produtos.get(0);
            }
            return null;
        } catch (SQLException ex) {
            System.out.println("Erro no SELECT." + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Erro geral." + ex.getMessage());
        }
        return null;
    }

    public void update(Product product) {
        try {
            String query = "UPDATE produto SET productQTT = ? WHERE productID = ?";
            PreparedStatement ps = sqlConnection.getConnection().prepareStatement(query);

            ps.setInt(1, product.getProductQuantity());
            ps.setInt(2, product.getProductId());

            // execute the preparedstatement
            ps.executeUpdate();
            ps.close();
            System.out.print("Produto atualizado \n");
        } catch (SQLException ex) {
            System.out.println("Erro no SELECT." + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Erro geral." + ex.getMessage());
        }
    }

    private ArrayList<Product> getProducts(String query) throws SQLException {
        PreparedStatement ps = sqlConnection.getConnection().prepareStatement(query);
        ResultSet rs = ps.executeQuery(query);

        ArrayList<Product> productlist = new ArrayList<Product>();

        while (rs.next()) {
            Product product = new Product();
            product.setProductName(rs.getString("productName"));
            product.setProductId(rs.getInt("productID"));
            product.setProductQuantity(rs.getInt("productQTT"));
            productlist.add(product);
        }
        ps.close();
        return productlist;
    }

}
