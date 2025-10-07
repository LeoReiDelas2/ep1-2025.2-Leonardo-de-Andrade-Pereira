package Services;
import entities.*;
import enums.StatusConsulta;
import utils.InputHandler;
import utils.Searcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Relatorios
{
    public static void listarPacientes(List<Paciente> pacientes) {
        System.out.println("\n=== LISTA DE PACIENTES ===");

        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }

        System.out.println("Total: " + pacientes.size() + "\n");
        for (Paciente p : pacientes) {
            System.out.println(p);
            System.out.println();
        }
    }
    public static void listarMedicos(List<Medico> medicos) {
        System.out.println("\n=== LISTA DE MÉDICOS ===");

        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico cadastrado.");
            return;
        }

        System.out.println("Total: " + medicos.size() + "\n");
        for (Medico m : medicos) {
            System.out.println(m);
            System.out.println();
        }
    }
    public static void listarConsultas(List<Consultas> consultas) {
        System.out.println("\n=== LISTA DE CONSULTAS ===");

        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta cadastrada.");
            return;
        }

        System.out.println("Total: " + consultas.size() + "\n");
        for (Consultas c : consultas) {
            System.out.println(c);
            System.out.println();
        }
    }
    public static void listarInternacoes(List<Internacao> internacoes) {
        System.out.println("\n=== LISTA DE INTERNAÇÕES ===");

        if (internacoes.isEmpty()) {
            System.out.println("Nenhuma internação cadastrada.");
            return;
        }

        System.out.println("Total: " + internacoes.size() + "\n");
        for (Internacao i : internacoes) {
            System.out.println(i);
            System.out.println();
        }
    }
    public static void listarPlanos(List<PlanoDeSaude> planos) {
        System.out.println("\n=== LISTA DE PLANOS DE SAÚDE ===");
        if (planos.isEmpty()) {
            System.out.println("Nenhum plano cadastrado.");
            return;
        }
        System.out.println("Total: " + planos.size() + "\n");
        for (PlanoDeSaude p : planos) {
            System.out.println(p);
            System.out.println();
        }
    }
    public static void historicoPaciente(Scanner scanner, List<Paciente> pacientes, List<Consultas> consultas) {
        System.out.println("\n=== HISTÓRICO DO PACIENTE ===");
        String cpf = InputHandler.lerTextoNaoVazio("Digite o CPF do paciente: ", scanner);

        Paciente paciente = Searcher.getPaciente(cpf, pacientes);
        if (paciente == null) {
            System.out.println("Paciente não encontrado.");
            return;
        }
        System.out.println("\n" + paciente);
        System.out.println();
        System.out.println("--- CONSULTAS ---");
        int totalConsultas = 0;
        for (Consultas c : consultas) {
            if (c.getPaciente().getCpf().equals(cpf)) {
                System.out.println(c);
                System.out.println();
                totalConsultas++;
            }
        }
        if (totalConsultas == 0) {
            System.out.println("Nenhuma consulta registrada.\n");
        }
        // Internações do paciente
        System.out.println("--- INTERNAÇÕES ---");
        List<Internacao> internacoes = paciente.getInternacoes();
        if (internacoes == null || internacoes.isEmpty()) {
            System.out.println("Nenhuma internação registrada.\n");
        } else {
            for (Internacao i : internacoes) {
                System.out.println(i);
                System.out.println();
            }
        }
    }
    public static void consultasMedico(Scanner scanner, List<Medico> medicos, List<Consultas> consultas) {
        System.out.println("\n=== CONSULTAS DO MÉDICO ===");
        String crm = InputHandler.lerTextoNaoVazio("Digite o CRM do médico: ", scanner);
        Medico medico = Searcher.getMedicoBusca(crm, medicos);
        if (medico == null) {
            System.out.println("Médico não encontrado.");
            return;
        }
        System.out.println("\nMédico: Dr(a). " + medico.getNome());
        System.out.println("CRM: " + medico.getCrm());
        System.out.println();
        int total = 0;
        for (Consultas c : consultas) {
            if (c.getMedico().getCrm().equals(crm)) {
                System.out.println(c);
                System.out.println();
                total++;
            }
        }
        if (total == 0) {
            System.out.println("Nenhuma consulta registrada para este médico.");
        } else {
            System.out.println("Total: " + total + " consultas");
        }
    }
    public static void internacoesAtivas(List<Internacao> internacoes) {
        System.out.println("\n=== INTERNAÇÕES ATIVAS ===");
        int total = 0;
        for (Internacao i : internacoes) {
            if (i.isAtiva()) {
                System.out.println(i);
                System.out.println();
                total++;
            }
        }
        if (total == 0) {
            System.out.println("Nenhuma internação ativa no momento.");
        } else {
            System.out.println("Total: " + total + " internações ativas");
        }
    }
    public static void estatisticas(List<Paciente> pacientes, List<Medico> medicos,
                                    List<Consultas> consultas, List<Internacao> internacoes,
                                    List<PlanoDeSaude> planos, List<Quarto> quartos) {
        System.out.println("\n=== ESTATISTICAS DO SISTEMA ===");
        System.out.println("Pacientes: " + pacientes.size());
        System.out.println("Médicos: " + medicos.size());
        System.out.println("Planos de Saúde: " + planos.size());
        System.out.println();
        int agendadas = 0, concluidas = 0, canceladas = 0;
        for (Consultas c : consultas) {
            switch (c.getStatusConsulta()) {
                case AGENDADO: agendadas++; break;
                case CONCLUIDO: concluidas++; break;
                case CANCELADO: canceladas++; break;
            }
        }
        System.out.println("Consultas:");
        System.out.println("  Total: " + consultas.size());
        System.out.println("  Agendadas: " + agendadas);
        System.out.println("  Concluídas: " + concluidas);
        System.out.println("  Canceladas: " + canceladas);
        System.out.println();
        int ativas = 0;
        for (Internacao i : internacoes) {
            if (i.isAtiva()) ativas++;
        }
        System.out.println("Internações:");
        System.out.println("  Total: " + internacoes.size());
        System.out.println("  Ativas: " + ativas);
        System.out.println("  Finalizadas: " + (internacoes.size() - ativas));
        System.out.println();
        int ocupados = 0;
        for (Quarto q : quartos) {
            if (q.isOcupado()) ocupados++;
        }
        System.out.println("Quartos:");
        System.out.println("  Total: " + quartos.size());
        System.out.println("  Ocupados: " + ocupados);
        System.out.println("  Disponíveis: " + (quartos.size() - ocupados));
    }
    public static void medicoMaisAtendeu(List<Consultas> consultas, List<Medico> medicos) {
        System.out.println("\n=== MÉDICO QUE MAIS ATENDEU ===");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta registrada no sistema.");
            return;
        }
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico cadastrado no sistema.");
            return;
        }
        Medico medicoMaisConsultas = null;
        int maxConsultas = 0;
        for (Medico medico : medicos) {
            int contadorConsultas = 0;

            for (Consultas consulta : consultas) {
                if (consulta.getMedico().getCrm().equals(medico.getCrm())) {
                    contadorConsultas++;
                }
            }
            if (contadorConsultas > maxConsultas) {
                maxConsultas = contadorConsultas;
                medicoMaisConsultas = medico;
            }
        }
        if (medicoMaisConsultas != null && maxConsultas > 0) {
            System.out.println("Médico: Dr(a). " + medicoMaisConsultas.getNome());
            System.out.println("CRM: " + medicoMaisConsultas.getCrm());
            System.out.println("Total de Consultas: " + maxConsultas);
        } else {
            System.out.println("Nenhum médico realizou consultas ainda.");
        }
    }
    public static void especialidadeMaisProcurada(List<Consultas> consultas, List<Especialidade> especialidades) {
        System.out.println("\n=== ESPECIALIDADE MAIS PROCURADA ===");
        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta registrada no sistema.");
            return;
        }
        if (especialidades.isEmpty()) {
            System.out.println("Nenhuma especialidade cadastrada no sistema.");
            return;
        }
        String especialidadeMaisProcurada = null;
        int maxConsultas = 0;
        boolean temConsultas = false;
        for (Especialidade especialidade : especialidades) {
            int contadorConsultas = 0;
            for (Consultas consulta : consultas) {
                if (consulta.getEspecialidadeDaConsulta().getNome().equals(especialidade.getNome())) {
                    contadorConsultas++;
                }
            }
            if (contadorConsultas > maxConsultas) {
                maxConsultas = contadorConsultas;
                especialidadeMaisProcurada = especialidade.getNome();
            }
            if (contadorConsultas > 0) {
                System.out.println(especialidade.getNome() + ": " + contadorConsultas + " consultas");
                temConsultas = true;
            }
        }
        System.out.println();
        if (!temConsultas) {
            System.out.println("Nenhuma especialidade foi procurada ainda.");
        } else if (especialidadeMaisProcurada != null) {
            System.out.println("A mais procurada é: " + especialidadeMaisProcurada + " (" + maxConsultas + " consultas)");
        }
    }
}
