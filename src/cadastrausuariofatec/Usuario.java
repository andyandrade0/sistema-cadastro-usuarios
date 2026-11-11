package cadastrausuariofatec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Dados do usuário e CRUD no banco (consultas parametrizadas)
public class Usuario {

    private String usuario;
    private String nome;
    private String senha;

    // usuário logado, usado em todas as telas
    static String nomeUsuario;
    static String usuarioSistema;

    // resultado de cada operação no banco
    private boolean resultUsuario;
    private boolean resultCadastro;
    private boolean resultAlteracao;
    private boolean resultExclusao;

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    // confere usuário e senha (login) e guarda o usuário logado
    public boolean verificaUsuario(String usuario, String senha) {
        resultUsuario = false;
        Conexao banco = new Conexao();
        Connection con = banco.abrirConexao();
        if (con == null) {
            return false; // banco indisponível
        }
        String sql = "SELECT * FROM usuario WHERE usuario = ? AND senha = MD5(?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultUsuario = true;
                    setUsuario(rs.getString("usuario"));
                    setNome(rs.getString("nome"));
                    nomeUsuario = getNome();
                    usuarioSistema = getUsuario();
                }
            }
        } catch (SQLException ec) {
            System.out.println("Erro ao consultar usuário " + ec.getMessage());
        } finally {
            banco.fecharConexao();
        }
        return resultUsuario;
    }

    // sobrecarga: confere só se o usuário existe (cadastro)
    public boolean verificaUsuario(String usuario) {
        resultUsuario = false;
        Conexao banco = new Conexao();
        Connection con = banco.abrirConexao();
        if (con == null) {
            return false; // banco indisponível
        }
        String sql = "SELECT 1 FROM usuario WHERE usuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                resultUsuario = rs.next();
            }
        } catch (SQLException ec) {
            System.out.println("Erro ao consultar usuário " + ec.getMessage());
        } finally {
            banco.fecharConexao();
        }
        return resultUsuario;
    }

    // insere um usuário (senha em MD5)
    public boolean cadastraUsuario(String nome, String usuario, String senha) {
        resultCadastro = false;
        Conexao banco = new Conexao();
        Connection con = banco.abrirConexao();
        if (con == null) {
            return false;
        }
        String sql = "INSERT INTO usuario (nome, usuario, senha) VALUES (?, ?, MD5(?))";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, usuario);
            ps.setString(3, senha);
            ps.executeUpdate();
            resultCadastro = true;
        } catch (SQLException ec) {
            System.out.println("Erro ao inserir o usuário " + ec.getMessage());
        } finally {
            banco.fecharConexao();
        }
        return resultCadastro;
    }

    // altera nome e senha do usuário
    public boolean alteraUsuario(String nome, String usuario, String senha) {
        resultAlteracao = false;
        Conexao banco = new Conexao();
        Connection con = banco.abrirConexao();
        if (con == null) {
            return false;
        }
        String sql = "UPDATE usuario SET nome = ?, senha = MD5(?) WHERE usuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, senha);
            ps.setString(3, usuario);
            ps.executeUpdate();
            resultAlteracao = true;
        } catch (SQLException ec) {
            System.out.println("Erro ao atualizar usuário " + ec.getMessage());
        } finally {
            banco.fecharConexao();
        }
        return resultAlteracao;
    }

    // exclui o usuário
    public boolean excluiUsuario(String usuario) {
        resultExclusao = false;
        Conexao banco = new Conexao();
        Connection con = banco.abrirConexao();
        if (con == null) {
            return false;
        }
        String sql = "DELETE FROM usuario WHERE usuario = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.executeUpdate();
            resultExclusao = true;
        } catch (SQLException ec) {
            System.out.println("Erro ao excluir usuário " + ec.getMessage());
        } finally {
            banco.fecharConexao();
        }
        return resultExclusao;
    }
}
