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
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;
import modelo.Produto;
import util.BancoDados;

/**
 *
 * @author sala303b
 */
public class CategoriaControle {

    public static Categoria BuscarPorID(long idcategoria) {
        try {
            Connection conn = BancoDados.getConexao();
            String sql = "SELECT * FROM tb_categoria WHERE id = ?; ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, idcategoria);
            final ResultSet rs = ps.executeQuery();

            Categoria c = new Categoria();
            if (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setNomeCategoria(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                c.setDataCadastro(rs.getDate("datacadastro"));
            }
            return c;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean Cadastrar(Categoria cat) {
        try {
            Connection conn = BancoDados.getConexao(); //conectar com o bando de dados e enviar os dados salvos da classe Contato.
            String sql = "INSERT INTO tb_categoria ";
            sql += " (nome, descricao ) VALUES (?,?);";
            PreparedStatement ps = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cat.getNomeCategoria());
            ps.setString(2, cat.getDescricao());
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

    public static boolean Atualizar(Categoria cat) {
        try {
            Connection conn = BancoDados.getConexao(); //conectar com o bando de dados e enviar os dados salvos da classe Contato.
            String sql = "UPDATE tb_categoria ";
            sql += " SET nome = ?, ";
            sql += " descricao = ? ";
            sql += " WHERE id = ?; ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, cat.getNomeCategoria());
            ps.setString(2, cat.getDescricao());
            ps.setLong(3, cat.getId());
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

    public static List<Categoria> ListarCategorias() {
        try {

            Connection conn = BancoDados.getConexao();
            String sql = "SELECT * FROM tb_categoria; ";
            PreparedStatement ps = conn.prepareStatement(sql);
            List<Categoria> lista = new ArrayList();
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNomeCategoria(rs.getString("nome"));
                c.setDescricao(rs.getString("descricao"));
                c.setDataCadastro(rs.getDate("datacadastro"));
                lista.add(c);
            }
            return lista;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
