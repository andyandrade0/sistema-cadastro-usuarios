package cadastrausuariofatec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Conexão com o banco MySQL
public class Conexao {

    // objetos do JDBC
    public Connection con = null;
    public Statement stmt = null;
    public ResultSet resultset = null;

    // dados de acesso ao banco
    private final String servidor = "jdbc:mysql://127.0.0.1:3306/bd_cadastrousuarios";
    private final String usuario = "root";
    private final String senha = "root";
    private final String driver = "com.mysql.cj.jdbc.Driver";

    // abre a conexão e cria o Statement
    public Connection abrirConexao() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(servidor, usuario, senha);
            stmt = con.createStatement();
            System.out.println("Conexão aberta com sucesso!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Erro ao acessar banco de dados, por favor verifique! " + e.getMessage());
        }
        return con;
    }

    // fecha a conexão (ignora se ela nunca foi aberta)
    public void fecharConexao() {
        if (con == null) {
            return;
        }
        try {
            con.close();
            System.out.println("Conexão finalizada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao encerrar conexão! " + e.getMessage());
        }
    }
}
