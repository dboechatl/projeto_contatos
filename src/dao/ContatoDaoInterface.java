package dao;

import model.Contato;
import java.util.List;

public interface ContatoDaoInterface {
    void addContato(Contato contato);
    List<Contato> getAllContatos();
    List<Contato> getContatosByInitial(char initial);
    Contato getContatoById(long id);
    void updateContato(Contato contato);
    void deleteContato(long id);
}

