import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cadastro {
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static int ultimoId = 0; // controla o id automático

    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            boolean sair = false;
            while (!sair) {
                mostrarMenu();
                System.out.print("Escolha uma opção: ");
                String opcao = input.nextLine().trim();

                switch (opcao) {
                    case "1":
                        cadastrarUsuario(input);
                        break;
                    case "2":
                        listarUsuarios();
                        break;
                    case "3":
                        buscarPorId(input);
                        break;
                    case "4":
                        removerPorId(input);
                        break;
                    case "0":
                        sair = true;
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } // Scanner é fechado automaticamente
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Listar usuários");
        System.out.println("3 - Buscar usuário por ID");
        System.out.println("4 - Remover usuário por ID");
        System.out.println("0 - Sair");
    }

    private static void cadastrarUsuario(Scanner input) {
        System.out.println("\n--- CADASTRO ---");

        System.out.print("Nome: ");
        String nome = input.nextLine().trim();

        System.out.print("Email: ");
        String email = input.nextLine().trim();

        System.out.print("Senha: ");
        String senha = input.nextLine(); // em produção: não armazene em plain text

        String cpf;
        while (true) {
            System.out.print("CPF (somente números ou com pontuação): ");
            cpf = input.nextLine().trim();
            if (ValidadorCPF.isValidCPF(cpf)) {
                cpf = cpf.replaceAll("\\D", ""); // armazenamos só dígitos
                break;
            } else {
                System.out.println("CPF inválido. Deseja tentar novamente? (s/n)");
                String r = input.nextLine().trim().toLowerCase();
                if (!r.equals("s") && !r.equals("sim")) {
                    System.out.println("Cadastro cancelado.");
                    return;
                }
            }
        }

        System.out.print("Telefone: ");
        String telefone = input.nextLine().trim();

        System.out.print("Endereço: ");
        String endereco = input.nextLine().trim();

        int novoId = ++ultimoId;
        Usuario u = new Usuario(novoId, nome, email, senha, cpf, telefone, endereco);
        usuarios.add(u);

        System.out.println("Usuário cadastrado com sucesso! ID = " + novoId);
    }

    private static void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        for (Usuario u : usuarios) {
            // Formata o CPF para mostrar ao usuário
            String cpfFormat = ValidadorCPF.formatCPF(u.getCpf());
            System.out.println("ID: " + u.getId() +
                    " | Nome: " + u.getNome() +
                    " | Email: " + u.getEmail() +
                    " | CPF: " + cpfFormat);
        }
    }

    private static void buscarPorId(Scanner input) {
        System.out.print("Informe o ID do usuário: ");
        String linha = input.nextLine().trim();
        try {
            int id = Integer.parseInt(linha);
            Usuario u = encontrarPorId(id);
            if (u == null) {
                System.out.println("Usuário não encontrado.");
            } else {
                System.out.println("Usuário encontrado: " + u);
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static void removerPorId(Scanner input) {
        System.out.print("Informe o ID do usuário a remover: ");
        String linha = input.nextLine().trim();
        try {
            int id = Integer.parseInt(linha);
            Usuario u = encontrarPorId(id);
            if (u == null) {
                System.out.println("Usuário não encontrado.");
            } else {
                usuarios.remove(u);
                System.out.println("Usuário com ID " + id + " removido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    private static Usuario encontrarPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) return u;
        }
        return null;
    }
}
