package Services;

import entities.*;
import enums.StatusConsulta;
import utils.InputHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utils.Searcher.*;

public class Hospital {
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Consultas> consultas;
    private List<Internacao> internacoes;
    private List<PlanoDeSaude> planoDeSaude;
    private List<Quarto> quartos;
    private List<Especialidade> especialidades;
    private List<String> consultorios;

    public Hospital() {
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.consultas = new ArrayList<>();
        this.internacoes = new ArrayList<>();
        this.planoDeSaude = new ArrayList<>();
        this.quartos = new ArrayList<>();
        this.especialidades = new ArrayList<>();
        this.consultorios = new ArrayList<>();
        this.consultorios.add("Consultório 1");
        this.consultorios.add("Consultório 2");
        this.consultorios.add("Consultório 3");
        this.consultorios.add("Consultório 4");
        this.consultorios.add("Consultório 5");
    }
    public void carregarEspecialidadesPadrao() {
        this.especialidades.add(new Especialidade("Cardiologia"));
        this.especialidades.add(new Especialidade("Pediatria"));
        this.especialidades.add(new Especialidade("Ortopedia"));
        this.especialidades.add(new Especialidade("Clínica Geral"));
        this.especialidades.add(new Especialidade("Dermatologia"));
    }

    private Especialidade getOrCreateEspecialidadePorNome(String nome) {
        String nomeArrumado = nome.trim();
        for (Especialidade esp : this.especialidades) {
            if (esp.getNome().equalsIgnoreCase(nomeArrumado)) {
                return esp;
            }
        }
        System.out.println("-> Nova especialidade '" + nomeArrumado + "' criada no sistema.");
        Especialidade novaEspecialidade = new Especialidade(nomeArrumado);
        this.especialidades.add(novaEspecialidade);
        return novaEspecialidade;
    }

    public void cadastrarMedico(Scanner scanner)
    {
        try {
            System.out.println("\n--- Cadastro de Novo Médico ---");
            System.out.print("Digite o nome do médico: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o CRM do médico: ");
            String crm = scanner.nextLine();
            System.out.print("Digite o custo padrão da consulta: ");
            double custo = scanner.nextDouble();
            scanner.nextLine();
            Medico novoMedico = new Medico(crm, custo, nome);
            System.out.println("\n--- Adicionar Especialidades ---");
            while (true) {
                System.out.print("Digite o nome de uma especialidade para adicionar (ou digite 'fim' para terminar): ");
                String nomeEspecialidade = scanner.nextLine();
                if (nomeEspecialidade.equalsIgnoreCase("fim")) {
                    break;
                }
                if (nomeEspecialidade.isBlank()) {
                    System.out.println("Nome da especialidade não pode ser vazio.");
                    continue;
                }
                Especialidade especialidade = getOrCreateEspecialidadePorNome(nomeEspecialidade);
                novoMedico.adicionarEspecialidade(especialidade);
            }
            this.medicos.add(novoMedico);
            System.out.println("\n--- Médico Cadastrado com Sucesso! ---");
            System.out.println(novoMedico);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. O custo da consulta deve ser um número.");
            scanner.nextLine();
        }
    }
    public void cadastrarPaciente(Scanner scanner)
    {
        try {
            System.out.println("\n--- Cadastro de Novo Paciente ---");
            System.out.print("Digite o nome do paciente: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o CPF do paciente: ");
            String cpf = scanner.nextLine();
            System.out.print("Digite a idade do paciente: ");
            Integer idade = scanner.nextInt();
            scanner.nextLine();
            System.out.print("O paciente possui plano de saúde? (S/N) ");
            String planoSaude = scanner.nextLine();
            Paciente novoPaciente = null;
            int contador = 1;
            if (planoSaude.equalsIgnoreCase("S")){
                if (this.planoDeSaude.isEmpty()) {
                    System.out.println("Não há um plano cadastrado, é necessário cadastrar um plano primeiro." +
                            "\nO paciente será cadastrado como um paciente comum.");
                    novoPaciente = new Paciente(nome, cpf, idade);
                }
                else{
                    for (PlanoDeSaude plano : this.planoDeSaude) {
                        System.out.println(contador + " - " + plano);
                        contador++;
                    }
                    System.out.println("Digite o número do plano do paciente: ");
                    Integer numeroPlano = scanner.nextInt();
                    scanner.nextLine();
                    PlanoDeSaude planoescolhido = this.planoDeSaude.get(numeroPlano-1);
                    novoPaciente = new PacienteEspecial(nome, cpf, idade, planoescolhido);
                }
            }
            else {
                novoPaciente = new Paciente(nome, cpf, idade);
            }
            this.pacientes.add(novoPaciente);
            System.out.println("\n--- Paciente Cadastrado com Sucesso! ---");
            System.out.println(novoPaciente);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. A idade deve ser um número.");
            scanner.nextLine();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Erro: Opção de plano de saúde inválida. Tente o cadastro novamente.");
        }

    }
    public void cadastrarPlanoDeSaude(Scanner scanner)
    {
        System.out.println("\n--- Cadastro de Novo Plano de Saúde ---");
        String nome;
        while (true) {
            nome = InputHandler.lerTextoNaoVazio("Digite o nome do plano: ", scanner);
            boolean existe = false;
            for (PlanoDeSaude p : this.planoDeSaude) {
                if (p.getNome().equalsIgnoreCase(nome)) {
                    existe = true;
                    break;
                }
            }
            if (existe) {
                System.out.println("Erro: Já existe um plano com este nome. Escolha outro.");
                continue;
            }
            break;
        }
        boolean isEspecial = InputHandler.lerSimNao("Este é um plano especial (internação curta gratuita)? (S/N): ", scanner);
        PlanoDeSaude novoPlano = new PlanoDeSaude(nome, isEspecial);
        System.out.println("\n--- Configurar Coberturas de Desconto ---");
        if (this.especialidades.isEmpty()) {
            System.out.println("ERRO: Nenhuma especialidade cadastrada. O plano será criado sem coberturas.");
        } else {
            while (true) {
                System.out.println("\nEspecialidades disponíveis:");
                for (int i = 0; i < this.especialidades.size(); i++) {
                    System.out.println((i + 1) + " - " + this.especialidades.get(i).getNome());
                }
                int escolha = InputHandler.digitarIntIntervalo(
                        "Digite o número da especialidade para adicionar cobertura (ou 0 para finalizar): ",
                        scanner,
                        0,
                        this.especialidades.size()
                );
                if (escolha == 0) {
                    break;
                }
                Especialidade espEscolhida = this.especialidades.get(escolha - 1);
                double desconto = InputHandler.lerDoubleIntervalo(
                        "Digite o desconto para " + espEscolhida.getNome() + " (0.0 a 1.0, ex: 0.2): ",
                        0.0,
                        1.0,
                        scanner
                );
                novoPlano.adicionarCobertura(espEscolhida, desconto);
            }
        }
        this.planoDeSaude.add(novoPlano);
        System.out.println("\n--- Plano de Saúde '" + novoPlano.getNome() + "' cadastrado com sucesso! ---");
    }
    public void agendarConsulta(Scanner scanner)
    {
        System.out.println("\n--- Agendamento de Nova Consulta ---");
        String cpfPaciente = InputHandler.lerTextoNaoVazio("Digite o CPF do paciente: ", scanner);
        Paciente pacienteEncontrado = getPaciente(cpfPaciente, this.pacientes);
        if (pacienteEncontrado == null) {
            System.out.println("Erro: Paciente com CPF " + cpfPaciente + " não encontrado.");
            return;
        }
        String crmMedico = InputHandler.lerTextoNaoVazio("Digite o crm do medico: ", scanner);
        Medico medicoEncontrado = getMedico(crmMedico, this.medicos);
        if (medicoEncontrado == null) {
            System.out.println("Erro: Médico com CRM " + crmMedico + " não encontrado.");
            return;
        }
        System.out.println("Médico selecionado: " + medicoEncontrado.getNome());
        List<Especialidade> especialidadesDoMedico = medicoEncontrado.getEspecialidades();
        if  (especialidadesDoMedico.isEmpty()) {
            System.out.println("Erro: O médico selecionado não tem especialidades cadastradas. ");
            return;
        }
        System.out.println("\nEspecialidades do(a) Dr(a). " + medicoEncontrado.getNome());
        Integer contador = 1;
        for (Especialidade especial : especialidadesDoMedico) {
            System.out.println(contador + " - Especialidade: " + especial.getNome());
            contador++;
        }
        Integer escolhaesp =  InputHandler.digitarIntIntervalo("Escolha a especialidade para a consulta: "
                , scanner
                , 1
                , especialidadesDoMedico.size());
        Especialidade especialidadeescolhida = especialidadesDoMedico.get(escolhaesp - 1);
        System.out.println("\nLocais disponíveis para consulta: ");
        Integer contador1 = 1;
        for(String consultorios1 : consultorios){
            System.out.println(contador1 + " - Consulta: " + consultorios1);
            contador1++;
        }
        Integer escolhaLocal = InputHandler.digitarIntIntervalo("Digite o número do lacal de consulta: ", scanner, 1, consultorios.size());
        String LocalEscolhido = this.consultorios.get(escolhaLocal - 1);
        LocalDateTime dataHora = InputHandler.lerDataHora("Digite a data e a hora da consulta (dessa forma: -dia/mês/ano hora:minutos-): ", scanner);
        if (IsHorarioOcupado(medicoEncontrado, LocalEscolhido, dataHora)) {
            return;
        }
        Consultas novaconsultas = new Consultas(dataHora, null, LocalEscolhido, medicoEncontrado, pacienteEncontrado, StatusConsulta.AGENDADO, especialidadeescolhida);
        this.consultas.add(novaconsultas);
        System.out.println("\n--- Consulta Agendada com Sucesso! ---");
        System.out.println("Paciente: " + pacienteEncontrado.getNome());
        System.out.println("Médico: " + medicoEncontrado.getNome());
        System.out.println("Local: " + LocalEscolhido);
        System.out.println("Data/Hora: " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm")));

    }

    private boolean IsHorarioOcupado(Medico medico, String local, LocalDateTime dataHora) {
        for (Consultas consultaExistente : this.consultas) {
            if (consultaExistente.getDataHora().equals(dataHora)) {
                if (consultaExistente.getMedico().equals(medico)) {
                    System.out.println("Erro: O médico " + medico.getNome() + " já possui uma consulta neste horário.");
                    return true;
                }
                if (consultaExistente.getLocal().equalsIgnoreCase(local)) {
                    System.out.println("Erro: O local '" + local + "' já está ocupado neste horário.");
                    return true;
                }
            }
        }
        return false;
    }

}
