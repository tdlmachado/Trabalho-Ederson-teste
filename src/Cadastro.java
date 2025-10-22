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

                // Puxa a função mostrarMenu e exibe as opções disponiveis
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
        }
    }

    // Função para o Menu
    private static void mostrarMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1 - Cadastrar usuário");
        System.out.println("2 - Listar usuários");
        System.out.println("3 - Buscar usuário por ID");
        System.out.println("4 - Remover usuário por ID");
        System.out.println("0 - Sair");
    }

    // Switch 1 para cadastro de usuário
    private static void cadastrarUsuario(Scanner input) {
        System.out.println("\n--- CADASTRO ---");

        System.out.print("Nome: ");
        String nome = input.nextLine().trim();

        System.out.print("Email: ");
        String email = input.nextLine().trim();

        System.out.print("Senha: ");
        String senha = input.nextLine();

        // Cria a variavel Cpf para podermos usar na função de Validador
        String cpf;
        while (true) {
            System.out.print("CPF (somente números ou com pontuação): ");
            cpf = input.nextLine().trim();

            // Se for verdadeiro ele retira todos os caracteres que não forem numeros, e substitui por espaço em branco
            if (ValidadorCPF.isValidCPF(cpf)) {
                cpf = cpf.replaceAll("\\D", "");
                break;
            } else {
                // Se não ele imprime uma mensagem de erro, e questiona o usuário se ele quer digitar novamente
                System.out.println("CPF inválido. Deseja tentar novamente? (s/n)");
                String r = input.nextLine().trim().toLowerCase();
                if (!r.equals("s") && !r.equals("sim")) {
                    System.out.println("Cadastro cancelado.");
                    // Caso ele digite 'n' o sistema retorna para o menu inicial
                    return;
                }
            }
        }

        System.out.print("Telefone: ");
        String telefone = input.nextLine().trim();

        System.out.print("Endereço: ");
        String endereco = input.nextLine().trim();

        // Cria um novo id para o cadastro
        int novoId = ++ultimoId;

        // Cria um usuário com as informações digitadas e o adiciona a lista de usuarios
        Usuario u = new Usuario(novoId, nome, email, senha, cpf, telefone, endereco);
        usuarios.add(u);

        System.out.println("Usuário cadastrado com sucesso! ID = " + novoId);
    }

    // Função para mostrar os cadastros realizados
    private static void listarUsuarios() {
        System.out.println("\n--- LISTA DE USUÁRIOS ---");
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }
        for (Usuario u : usuarios) {
            // Formata tanto o CPF quanto o Numero para a exibição ficar correta
            String cpfFormat = ValidadorCPF.formatCPF(u.getCpf());
            String telefoneFormat = FormatadorCelular.formatar(u.getTelefone());

            System.out.println("ID: " + u.getId() +
                    " | Nome: " + u.getNome() +
                    " | Email: " + u.getEmail() +
                    " | CPF: " + cpfFormat +
                    " | Telefone: " + telefoneFormat);
        }
    }

    // Função para buscar o usuário cadastrado pelo id dele
    private static void buscarPorId(Scanner input) {
        System.out.print("Informe o ID do usuário: ");
        String linha = input.nextLine().trim();
        try {
            // Faz a conversão da String linha pra int linha, para que o sistema verifique somente o numero como id.
            int id = Integer.parseInt(linha);
            Usuario u = encontrarPorId(id);
            if (u == null) {
                System.out.println("Usuário não encontrado.");
            } else {
                System.out.println("Usuário encontrado: " + u);
            }
            // Captura o erro caso a conversão de String para int falhe
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    // Função para remover um cadastro pelo id dele
    private static void removerPorId(Scanner input) {
        System.out.print("Informe o ID do usuário a remover: ");
        String linha = input.nextLine().trim();
        try {
            int id = Integer.parseInt(linha);
            Usuario u = encontrarPorId(id);
            if (u == null) {
                System.out.println("Usuário não encontrado.");
            } else {

                // Caso o id seja valido ele remove as informações dele da lista de usuário e retorna que o id for removido com sucesso
                usuarios.remove(u);
                System.out.println("Usuário com ID " + id + " removido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
        }
    }

    // Função para mostrar as informações do usuário, segundo o id dele
    private static Usuario encontrarPorId(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) return u;
        }
        return null;
    }
}
