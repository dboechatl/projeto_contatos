package dao;
import model.Contato;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionFactory;

public class ContatoDao implements ContatoDaoInterface {

    @Override
    public void addContato(Contato contato) {
        String sql = "INSERT INTO contato (nome, email, endereco) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(sql)) {
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getEndereco());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar contato", e);
        }
    }

    @Override
    public List<Contato> getAllContatos() {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contato";
        try (PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Contato contato = new Contato();
                contato.setId(rs.getLong("id"));
                contato.setNome(rs.getString("nome"));
                contato.setEmail(rs.getString("email"));
                contato.setEndereco(rs.getString("endereco"));
                contatos.add(contato);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contatos", e);
        }
        return contatos;
    }

    @Override
    public List<Contato> getContatosByInitial(char initial) {
        List<Contato> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contato WHERE nome LIKE ?";
        try (PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(sql)) {
            stmt.setString(1, initial + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Contato contato = new Contato();
                    contato.setId(rs.getLong("id"));
                    contato.setNome(rs.getString("nome"));
                    contato.setEmail(rs.getString("email"));
                    contato.setEndereco(rs.getString("endereco"));
                    contatos.add(contato);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contatos pela inicial", e);
        }
        return contatos;
    }

    @Override
    public Contato getContatoById(long id) {
        String sql = "SELECT * FROM contato WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Contato contato = new Contato();
                    contato.setId(rs.getLong("id"));
                    contato.setNome(rs.getString("nome"));
                    contato.setEmail(rs.getString("email"));
                    contato.setEndereco(rs.getString("endereco"));
                    return contato;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar contato pela ID", e);
        }
        return null;
    }

    @Override
    public void updateContato(Contato contato) {
        String sql = "UPDATE contato SET nome = ?, email = ?, endereco = ? WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(sql)) {
            stmt.setString(1, contato.getNome());
            stmt.setString(2, contato.getEmail());
            stmt.setString(3, contato.getEndereco());
            stmt.setLong(4, contato.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar contato", e);
        }
    }

    @Override
    public void deleteContato(long id) {
        String sql = "DELETE FROM contato WHERE id = ?";
        try (PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar contato", e);
        }
    }
}
