package Services;

import entities.Consultas;
import entities.Internacao;
import entities.Medico;
import entities.Paciente;
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
                menuOperacoes(scanner, hospital);
                break;
            case 3:
                menuRelatorios(scanner, hospital);
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
    public static void menuOperacoes(Scanner scanner, Hospital hospital)
    {
        while (true)
        {
            System.out.println("\n--- Menu de Operações ---");
            System.out.println("1. Agendar consulta de Paciente");
            System.out.println("2. Concluir consulta de Paciente");
            System.out.println("3. Cancelar consulta de Paciente");
            System.out.println("4. Realizar uma internação");
            System.out.println("5. Registrar Alta de Paciente");
            System.out.println("6. Cancelar internação");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("-------------------------");
            int escolha = InputHandler.digitarIntIntervalo("Digite a sua opção: ", scanner, 0, 4);
            switch (escolha) {
                case 0:
                    return;
                case 1:
                    hospital.agendarConsulta(scanner);
                    break;
                case 2:
                    hospital.concluirConsulta(scanner);
                    break;
                case 3:
                    hospital.cancelarConsulta(scanner);
                    break;
                case 4:
                    hospital.registrarInternacao(scanner);
                    break;
                case 5:
                    hospital.registrarAltoOficial(scanner);
                    break;
                case 6:
                    hospital.cancelarInternacao(scanner);
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
        }

    }
    }
    public static void menuRelatorios(Scanner scanner, Hospital hospital) {
        while (true) {
            System.out.println("\n=== MENU DE RELATÓRIOS ===");
            System.out.println("1. Listar Pacientes");
            System.out.println("2. Listar Médicos");
            System.out.println("3. Listar Consultas");
            System.out.println("4. Listar Internações");
            System.out.println("5. Listar Planos de Saúde");
            System.out.println("-------------------------");
            System.out.println("6. Histórico de um Paciente");
            System.out.println("7. Consultas de um Médico");
            System.out.println("8. Internações Ativas");
            System.out.println("-------------------------");
            System.out.println("9. Estatísticas do Sistema");
            System.out.println("10. Especialidades mais procuradas");
            System.out.println("11. Médicos que mais atenderam");
            System.out.println("0. Voltar");
            System.out.println("=========================");
            int escolha = InputHandler.digitarIntIntervalo("Escolha uma opção: ", scanner, 0, 11);
            switch (escolha) {
                case 0:
                    return;
                case 1:
                    Relatorios.listarPacientes(Hospital.getPacientes());
                    break;
                case 2:
                    Relatorios.listarMedicos(Hospital.getMedicos());
                    break;
                case 3:
                    Relatorios.listarConsultas(Hospital.getHistoricu());
                    break;
                case 4:
                    Relatorios.listarInternacoes(Hospital.getInternacoes());
                    break;
                case 5:
                    Relatorios.listarPlanos(hospital.getPlanoDeSaude());
                    break;
                case 6:
                    Relatorios.historicoPaciente(scanner, Hospital.getPacientes(), Hospital.getHistoricu());
                    break;
                case 7:
                    Relatorios.consultasMedico(scanner, Hospital.getMedicos(), Hospital.getHistoricu());
                    break;
                case 8:
                    Relatorios.internacoesAtivas(Hospital.getInternacoes());
                    break;
                case 9:
                    Relatorios.estatisticas(
                            Hospital.getPacientes(),
                            Hospital.getMedicos(),
                            Hospital.getHistoricu(),
                            Hospital.getInternacoes(),
                            hospital.getPlanoDeSaude(),
                            hospital.getQuartos()
                    );
                    break;
                case 10:
                    Relatorios.especialidadeMaisProcurada(Hospital.getHistoricu(), Hospital.getEspecialidades());
                    break;
                case 11:
                    Relatorios.medicoMaisAtendeu(Hospital.getHistoricu(), Hospital.getMedicos());
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
        }
    }

}
