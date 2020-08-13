package com.example.demo.models.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {

    private String hostname;
    private int port;
    private String database;
    private String username;
    private String password;

    private Connection connection;

    public SqlConnection(){
        try{
            hostname = "localhost";
            port = 3306;
            database = "controlstore";
            username = "root";
            password = "denner";

            String dburl = "jdbc:mysql://"+hostname+":"+port+"/"+database;

            connection = DriverManager.getConnection(dburl, username, password);

            System.out.println("Conexão realizada com sucesso");
        }
        catch(SQLException er){
            System.err.println("Erro na conexao com o Banco: "+er.getMessage());
        }catch(Exception er){
            System.err.println("Erro geral"+er.getMessage());
        }
    }

    public Connection getConnection(){
        return this.connection;
    }

    public void closeSqlConnection() throws SQLException {
        try{
            connection.close();
        }catch(SQLException ex){
            System.out.println("Erro no encerramento da conexao"+ex.getMessage());
        }

    }

}
