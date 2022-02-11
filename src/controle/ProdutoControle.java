/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import modelo.Produto;
import util.BancoDados;

/**
 *
 * @author sala303b
 */
public class ProdutoControle {

    public static boolean Cadastrar(Produto p) {
        try {
            Connection conn = BancoDados.getConexao(); //conectar com o bando de dados e enviar os dados salvos da classe Contato.
            String sql = "INSERT INTO tb_produto ";
            sql += " (nome, descricao, preco, "
                    + "nome_categoria, id_categoria) ";
            sql += " VALUES (?,?,?,?,?);";
            PreparedStatement ps = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescricao());
            ps.setDouble(3, p.getPreco());
            ps.setString(4, p.getNomeCategoria());
            ps.setLong(5, p.getIdCategoria());
            int linhasafetadas = ps.executeUpdate();
            if (linhasafetadas > 0) {
                final ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    final int lastId = rs.getInt(1);
                    System.out.println("O numero do id é:"
                            + lastId);
                    return true;

                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Produto BuscarPorId(long idproduto) {
        try {
            Connection conn = BancoDados.getConexao();
            String sql = "SELECT * FROM tb_produto WHERE id = ?; ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, idproduto);
            final ResultSet rs = ps.executeQuery();

            Produto p = new Produto();
            if (rs.next()) {
                p.setId(rs.getLong("id"));
                p.setIdCategoria(rs.getLong("id_categoria"));
                p.setNome(rs.getString("nome"));
                p.setNomeCategoria(rs.getString("nome_categoria"));
                p.setPreco(rs.getDouble("preco"));
                p.setDescricao(rs.getString("descricao"));
                p.setDataCadastro(rs.getDate("data_cadastro"));
            }
            return p;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean Atualizar(Produto p) {
        try {
            Connection conn = BancoDados.getConexao(); //conectar com o bando de dados e enviar os dados salvos da classe Contato.
            String sql = "UPDATE tb_produto ";
            sql += " SET nome = ?, ";
            sql += " descricao = ?, ";
            sql += " preco = ?, ";
            sql += " idcategoria = ?, ";
            sql += " nomecategoria = ? ";
            sql += " WHERE id = ?; ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescricao());
            ps.setDouble(3, p.getPreco());
            ps.setLong(4, p.getIdCategoria());
            ps.setString(5, p.getNomeCategoria());
            ps.setLong(6, p.getId());
            int linhasafetadas = ps.executeUpdate();
            if (linhasafetadas > 0) {
                System.out.println("atualizou!");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
            return false;
        }
    }

    public static boolean Excluir(long idContato) {
        return true;
    }

    public List<Produto> ListarTodos() {
        return null;
    }

}
