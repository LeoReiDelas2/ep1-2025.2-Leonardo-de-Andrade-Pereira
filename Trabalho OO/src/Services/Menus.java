package Services;

import utils.InputHandler;

import java.util.Scanner;

public class Menus
{
    public int exibirMenuPrincipal(Scanner scanner, Hospital hospital) {
        System.out.println("\n=== Sistema de Gerenciamento Hospitalar ===");
        System.out.println("1 - Menu de Cadastros");
        System.out.println("2 - Menu de Operações (Agendamentos, etc.)");
        System.out.println("3 - Menu de Relatórios");
        System.out.println("0 - Sair do Sistema");
        System.out.println("========================================");

        int escolha = InputHandler.digitarIntIntervalo("Digite a sua opção: ", scanner, 0, 3);

        switch (escolha) {
            case 1:
                menuCadastros(scanner, hospital);
                break;
            case 2:
                System.out.println("--> nada");
                break;
            case 3:
                System.out.println("--> nada");
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
        return escolha;
    }
    public static void menuCadastros(Scanner scanner, Hospital hospital) {
        while (true) {
            System.out.println("\n--- Menu de Cadastros ---");
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Cadastrar Médico");
            System.out.println("3. Cadastrar Plano de Saúde");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("-------------------------");
            int escolha = InputHandler.digitarIntIntervalo("Digite a sua opção: ", scanner, 0, 3);
            switch (escolha) {
                case 0:
                    return;
                case 1:
                    hospital.cadastrarPaciente(scanner);
                    break;
                case 2:
                    hospital.cadastrarMedico(scanner);
                    break;
                case 3:
                    hospital.cadastrarPlanoDeSaude(scanner);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

}
