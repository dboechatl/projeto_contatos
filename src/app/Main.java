package app;
import dao.ContatoDao;
import db.ConnectionFactory;
import model.Contato;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContatoDao dao = new ContatoDao();
        Scanner scanner = new Scanner(System.in);

        while (true) {
        	System.out.println("");
            System.out.println("Menu:");
            System.out.println("1. Adicionar contato");
            System.out.println("2. Listar todos os contatos");
            System.out.println("3. Buscar contato por inicial");
            System.out.println("4. Buscar contato por ID");
            System.out.println("5. Atualizar contato");
            System.out.println("6. Remover contato");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            System.out.println("");

            switch (option) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Endereço: ");
                    String endereco = scanner.nextLine();
                    Contato contato = new Contato(nome, email, endereco);
                    dao.addContato(contato);
                    break;
                case 2:
                    List<Contato> contatos = dao.getAllContatos();
                    contatos.forEach(c -> System.out.println(c.getId() + ": " + c.getNome() + ", " + c.getEmail() + ", " + c.getEndereco()));
                    break;
                case 3:
                    System.out.print("Inicial do nome: ");
                    char initial = scanner.nextLine().charAt(0);
                    List<Contato> contatosByInitial = dao.getContatosByInitial(initial);
                    contatosByInitial.forEach(c -> System.out.println(c.getId() + ": " + c.getNome() + ", " + c.getEmail() + ", " + c.getEndereco()));
                    break;
                case 4:
                    System.out.print("ID do contato: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Contato contatoById = dao.getContatoById(id);
                    if (contatoById != null) {
                        System.out.println(contatoById.getId() + ": " + contatoById.getNome() + ", " + contatoById.getEmail() + ", " + contatoById.getEndereco());
                    } else {
                        System.out.println("Contato não encontrado.");
                    }
                    break;
                case 5:
                    System.out.print("ID do contato a ser atualizado: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Novo nome (deixe vazio para não alterar): ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Novo email (deixe vazio para não alterar): ");
                    String novoEmail = scanner.nextLine();
                    System.out.print("Novo endereço (deixe vazio para não alterar): ");
                    String novoEndereco = scanner.nextLine();

                    Contato contatoParaAtualizar = dao.getContatoById(updateId);
                    if (contatoParaAtualizar != null) {
                        if (!novoNome.isEmpty()) {
                            contatoParaAtualizar.setNome(novoNome);
                        }
                        if (!novoEmail.isEmpty()) {
                            contatoParaAtualizar.setEmail(novoEmail);
                        }
                        if (!novoEndereco.isEmpty()) {
                            contatoParaAtualizar.setEndereco(novoEndereco);
                        }
                        dao.updateContato(contatoParaAtualizar);
                        System.out.println("Contato atualizado com sucesso.");
                    } else {
                        System.out.println("Contato não encontrado.");
                    }
                    break;
                case 6:
                    System.out.print("ID do contato a ser removido: ");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();
                    dao.deleteContato(deleteId);
                    System.out.println("Contato removido com sucesso.");
                    break;
                case 7:
                    System.out.println("Saindo...");
                    ConnectionFactory.closeConnection();
                    scanner.close();
                    System.exit(0);
                    scanner.nextLine();
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
