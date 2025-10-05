package Services;
import entities.*;
import enums.StatusConsulta;
import utils.Arquivos;
import utils.InputHandler;
import utils.Searcher;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static utils.Searcher.*;
public class Hospital
{
    private static List<Paciente> pacientes;
    private static List<Medico> medicos;
    private static List<Consultas> consultas;
    private static List<Internacao> internacoes;
    private List<PlanoDeSaude> planoDeSaude;
    private List<Quarto> quartos;
    private static List<Especialidade> especialidades;
    private List<String> consultorios;
    private static final Double Fator_desconto_idoso = 0.80;

    public Hospital() {
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.consultas = new ArrayList<>();
        this.internacoes = new ArrayList<>();
        this.planoDeSaude = new ArrayList<>();
        this.quartos = new ArrayList<>();
        this.especialidades = new ArrayList<>();
        this.consultorios = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            this.consultorios.add("Consultório " + i);
        }
        for (int i = 101; i <= 110; i++) {
            this.quartos.add(new Quarto(i));
        }
    }

    public void setConsultorios(List<String> consultorios) {
        this.consultorios = consultorios;
    }

    public static void setInternacoes(List<Internacao> internacoes) {
        Hospital.internacoes = internacoes;
    }

    public void setPlanoDeSaude(List<PlanoDeSaude> planoDeSaude) {
        this.planoDeSaude = planoDeSaude;
    }

    public void setQuartos(List<Quarto> quartos) {
        this.quartos = quartos;
    }

    public static void setConsultas(List<Consultas> consultas) {
        Hospital.consultas = consultas;
    }

    public static List<Consultas> getConsultas() {
        return consultas;
    }

    public List<String> getConsultorios() {
        return consultorios;
    }

    public List<PlanoDeSaude> getPlanoDeSaude() {
        return planoDeSaude;
    }

    public List<Quarto> getQuartos() {
        return quartos;
    }

    public static List<Medico> getMedicos() {
        return medicos;
    }

    public static List<Paciente> getPacientes() {
        return pacientes;
    }

    public static List<Consultas> getHistoricu() {
        return consultas;
    }

    public static List<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public static void setEspecialidades(List<Especialidade> especialidades) {
        Hospital.especialidades = especialidades;
    }

    public static void addEspecialidade(Especialidade especialidade) {
        List<Especialidade> especialidadess = Hospital.getEspecialidades();
        especialidadess.add(especialidade);
        Hospital.setEspecialidades(especialidadess);
    }

    public static void setPacientes(List<Paciente> pacientes) {
        Hospital.pacientes = pacientes;
    }

    public static List<Internacao> getInternacoes() {
        return internacoes;
    }

    public static void setMedicos(List<Medico> medicos) {
        Hospital.medicos = medicos;
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

    public void cadastrarMedico(Scanner scanner) {
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
                String nomeEspecialidade = scanner.nextLine().trim();
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
            Arquivos.SalvarMedico(novoMedico);
            this.medicos.add(novoMedico);
            System.out.println("\n--- Médico Cadastrado com Sucesso! ---");
            System.out.println(novoMedico);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. O custo da consulta deve ser um número.");
            scanner.nextLine();
        }
    }

    public void cadastrarPaciente(Scanner scanner) {
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
            if (!planoSaude.equalsIgnoreCase("S")) {
                novoPaciente = new Paciente(nome, cpf, idade);
                Arquivos.SalvarPaciente(novoPaciente);
                this.pacientes.add(novoPaciente);
                System.out.println("\n--- Paciente Cadastrado com Sucesso! ---");
                System.out.println(novoPaciente);
                return;
            }
            if (this.planoDeSaude.isEmpty()) {
                System.out.println("Não há um plano cadastrado, é necessário cadastrar um plano primeiro." +
                        "\nO paciente será cadastrado como um paciente comum.");
                novoPaciente = new Paciente(nome, cpf, idade);
                Arquivos.SalvarPaciente(novoPaciente);
                this.pacientes.add(novoPaciente);
                System.out.println("\n--- Paciente Cadastrado com Sucesso! ---");
                System.out.println(novoPaciente);
                return;
            }
            for (PlanoDeSaude plano : this.planoDeSaude) {
                System.out.println(contador + " - " + plano.getNome());
                contador++;
            }
            System.out.println("Digite o número do plano do paciente: ");
            Integer numeroPlano = scanner.nextInt();
            scanner.nextLine();
            PlanoDeSaude planoescolhido = this.planoDeSaude.get(numeroPlano - 1);
            novoPaciente = new PacienteEspecial(nome, cpf, idade, planoescolhido);
            this.pacientes.add(novoPaciente);
            System.out.println("\n--- Paciente Cadastrado com Sucesso! ---");
            System.out.println(novoPaciente);
            Arquivos.SalvarPaciente(novoPaciente);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. A idade deve ser um número.");
            scanner.nextLine();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Erro: Opção de plano de saúde inválida. Tente o cadastro novamente.");
        }
    }

    public void cadastrarPlanoDeSaude(Scanner scanner) {
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

    public void agendarConsulta(Scanner scanner) {
        System.out.println("\n--- Agendamento de Nova Consulta ---");
        String cpfPaciente = InputHandler.lerTextoNaoVazio("Digite o CPF do paciente: ", scanner);
        Paciente pacienteEncontrado = getPaciente(cpfPaciente, this.pacientes);
        if (pacienteEncontrado == null) {
            System.out.println("Erro: Paciente com CPF " + cpfPaciente + " não encontrado.");
            return;
        }
        String crmMedico = InputHandler.lerTextoNaoVazio("Digite o crm do medico: ", scanner);
        Medico medicoEncontrado = getMedicoBusca(crmMedico, this.medicos);
        if (medicoEncontrado == null) {
            System.out.println("Erro: Médico com CRM " + crmMedico + " não encontrado.");
            return;
        }
        System.out.println("Médico selecionado: " + medicoEncontrado.getNome());
        List<Especialidade> especialidadesDoMedico = medicoEncontrado.getEspecialidades();
        if (especialidadesDoMedico.isEmpty()) {
            System.out.println("Erro: O médico selecionado não tem especialidades cadastradas. ");
            return;
        }
        System.out.println("\nEspecialidades do(a) Dr(a). " + medicoEncontrado.getNome());
        Integer contador = 1;
        for (Especialidade especial : especialidadesDoMedico) {
            System.out.println(contador + " - Especialidade: " + especial.getNome());
            contador++;
        }
        Integer escolhaesp = InputHandler.digitarIntIntervalo("Escolha a especialidade para a consulta: "
                , scanner
                , 1
                , especialidadesDoMedico.size());
        Especialidade especialidadeescolhida = especialidadesDoMedico.get(escolhaesp - 1);
        System.out.println("\nLocais disponíveis para consulta: ");
        Integer contador1 = 1;
        for (String consultorios1 : consultorios) {
            System.out.println(contador1 + " - Consulta: " + consultorios1);
            contador1++;
        }
        Integer escolhaLocal = InputHandler.digitarIntIntervalo("Digite o número do local de consulta: ", scanner, 1, consultorios.size());
        String LocalEscolhido = this.consultorios.get(escolhaLocal - 1);
        LocalDateTime dataHora = InputHandler.lerDataHora("Digite a data e a hora da consulta (dessa forma: -dia/mês/ano hora:minutos-): ", scanner);
        if (IsHorarioOcupado(medicoEncontrado, LocalEscolhido, dataHora)) {
            return;
        }
        Consultas novaconsultas = new Consultas(dataHora, null, LocalEscolhido, medicoEncontrado, pacienteEncontrado, StatusConsulta.AGENDADO, especialidadeescolhida);
        this.consultas.add(novaconsultas);
        medicoEncontrado.adicionarAgendaOcupada(dataHora);
        Arquivos.SalvarMedico(medicoEncontrado);
        Arquivos.SalvarConsulta(novaconsultas);
        System.out.println("\n--- Consulta Agendada com Sucesso! ---");
        System.out.println("Paciente: " + pacienteEncontrado.getNome());
        System.out.println("Médico: " + medicoEncontrado.getNome());
        System.out.println("Local: " + LocalEscolhido);
        System.out.println("Data/Hora: " + dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm")));
    }

    private boolean IsHorarioOcupado(Medico medico, String local, LocalDateTime dataHora) {
        for (Consultas consultaExistente : this.consultas) {
            if (!consultaExistente.getDataHora().equals(dataHora)) {
                return false;
            }
            if (consultaExistente.getMedico().equals(medico)) {
                System.out.println("Erro: O médico " + medico.getNome() + " já possui uma consulta neste horário.");
                return true;
            }
            if (consultaExistente.getLocal().equalsIgnoreCase(local)) {
                System.out.println("Erro: O local '" + local + "' já está ocupado neste horário.");
                return true;
            }
        }
        return false;
    }

    public void registrarInternacao(Scanner scanner) {
        System.out.println("\n--- Registrar Nova Internação ---");
        String cpfPaciente = InputHandler.lerTextoNaoVazio("Digite o CPF do paciente que irá ser internado: ", scanner);
        Paciente paciente = Searcher.getPaciente(cpfPaciente, this.pacientes);
        if (paciente == null) {
            System.out.println("Erro: O Paciente não foi encontrado! ");
            return;
        }
        String crmMedico = InputHandler.lerTextoNaoVazio("Digite o CRM do médico que irá ser o responsável: ", scanner);
        Medico medico = Searcher.getMedicoBusca(crmMedico, this.medicos);
        if (medico == null) {
            System.out.println("Erro: O Médico não foi encontrado! ");
            return;
        }
        List<Quarto> quartosDisponiveis = new ArrayList<>();
        for (Quarto quarto : this.quartos) {
            if (!quarto.isOcupado()) {
                quartosDisponiveis.add(quarto);
            }
        }
        if (quartosDisponiveis.isEmpty()) {
            System.out.println("Erro: Não há quartos livres no momento! ");
            return;
        }
        System.out.println("\nQuartos disponíveis:");
        Integer contador = 1;
        for (Quarto quarto : quartosDisponiveis) {
            System.out.println(contador + " - Quarto n° " + quarto.getNumero());
            contador++;
        }
        Integer escolhaqua = InputHandler.digitarIntIntervalo("Digite qual será o quarto escolhido: ", scanner, 1, quartosDisponiveis.size());
        Quarto quartoEscolhido = quartosDisponiveis.get(escolhaqua - 1);
        quartoEscolhido.ocupar();
        Internacao novaInternacao = new Internacao(paciente, quartoEscolhido, medico);
        Arquivos.SalvarInternacao(novaInternacao);
        this.internacoes.add(novaInternacao);
        paciente.adicionarInternacao(novaInternacao);
        System.out.println("\n--- Internação Registrada! ---");
        System.out.println("Paciente: " + paciente.getNome());
        System.out.println("Medico: " + medico.getNome());
        System.out.println("Quarto: " + quartoEscolhido.getNumero());
    }
    public static Internacao buscarInternacaoAtivaPorPaciente(Paciente paciente) {
        for (Internacao internacao : Hospital.getInternacoes()) {
            if (internacao.getPaciente().equals(paciente) && internacao.isAtiva()) {
                return internacao;
            }
        }
        return null;
    }
    //codigo foda
    public static List<Internacao> buscarInternacaoAtivaPorPaciente(String cpf) {
        List<Internacao> internacoes = new ArrayList<>();
        Paciente paciente = Searcher.getPaciente(cpf, Hospital.getPacientes());
        for (Internacao internacao : Hospital.getInternacoes()) {
            if (internacao.getPaciente().equals(paciente)) {
                internacoes.add(internacao);
            }
        }
        return internacoes;
    }
    public void registrarAltoOficial(Scanner scanner) {
        System.out.println("\n--- Registrar Alta de Paciente ---");
        String cpf = InputHandler.lerTextoNaoVazio("Digite o CPF do paciente que receberá alta: ", scanner);
        Paciente paciente = Searcher.getPaciente(cpf, this.pacientes);
        if (paciente == null) {
            System.out.println("Erro: Paciente não encontrado.");
            return;
        }
        Internacao internacao = buscarInternacaoAtivaPorPaciente(paciente);
        if (internacao == null) {
            System.out.println("Erro: Este paciente não possui uma internação ativa.");
            return;
        }
        long diasInternado = ChronoUnit.DAYS.between(internacao.getDataEntrada(), LocalDateTime.now());
        if (diasInternado == 0) diasInternado = 1;
        double custoTotal = diasInternado * 500.0;
        System.out.println("Paciente: " + paciente.getNome());
        System.out.printf("Custo total da internação: R$ %.2f\n", custoTotal);
        boolean confirmar = InputHandler.lerSimNao("Confirmar alta e liberar o quarto " + internacao.getQuarto().getNumero() + "? (S/N): ", scanner);
        if (confirmar) {
            internacao.registrarAlta(custoTotal);
            System.out.println("Alta registrada e quarto liberado com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
    public void cancelarInternacao(Scanner scanner) {
        System.out.println("\n--- Cancelar Internação Ativa ---");
        String cpf = InputHandler.lerTextoNaoVazio("Digite o CPF do paciente: ", scanner);
        Paciente paciente = Searcher.getPaciente(cpf, this.pacientes);
        if (paciente == null) {
            System.out.println("Erro: Paciente não encontrado.");
            return;
        }
        Internacao internacao = buscarInternacaoAtivaPorPaciente(paciente);
        if (internacao == null) {
            System.out.println("Erro: Este paciente não possui uma internação ativa para cancelar.");
            return;
        }
        boolean confirmar = InputHandler.lerSimNao("Confirmar o CANCELAMENTO da internação no quarto " + internacao.getQuarto().getNumero() + "? (S/N): ", scanner);
        if (confirmar) {
            internacao.getQuarto().desocupar();
            this.internacoes.remove(internacao);
            paciente.getInternacoes().remove(internacao);
            System.out.println("Internação cancelada e quarto liberado com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }
    private Consultas buscarConsultaAgendadaPorPaciente(Paciente paciente) {
        for (Consultas consulta : this.consultas) {
            if (consulta.getPaciente().equals(paciente) && consulta.getStatusConsulta() == StatusConsulta.AGENDADO) {
                return consulta;
            }
        }
        return null;
    }
    public void concluirConsulta(Scanner scanner) {
        System.out.println("\n--- Concluir Consulta ---");
        Paciente paciente = Searcher.getPaciente(InputHandler.lerTextoNaoVazio("Digite o CPF do paciente: ", scanner), this.pacientes);
        if (paciente == null) {
            System.out.println("Erro: Paciente não encontrado.");
            return;
        }
        Consultas consulta = buscarConsultaAgendadaPorPaciente(paciente);
        if (consulta == null) {
            System.out.println("Erro: Nenhuma consulta agendada encontrada para este paciente.");
            return;
        }
        System.out.println("Consulta encontrada: Dr(a). " + consulta.getMedico().getNome() + " em " + consulta.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        String diagnostico = InputHandler.lerTextoNaoVazio("Digite o diagnóstico/prescrição da consulta: ", scanner);

        consulta.setDiagnostico(diagnostico);
        consulta.setStatusConsulta(StatusConsulta.CONCLUIDO);
        double custoFinal = calcularCustoConsulta(consulta);
        System.out.println("\n--- Consulta Concluída com Sucesso! ---");
        System.out.println("Diagnóstico: " + diagnostico);
        System.out.printf("Valor final a ser pago: R$ %.2f\n", custoFinal);
    }
    public double calcularCustoConsulta(Consultas consulta) {
        Paciente paciente = consulta.getPaciente();
        Medico medico = consulta.getMedico();
        double custoBase = medico.getCustoDaConsulta();
        if (paciente instanceof PacienteEspecial) {
            System.out.println("-> Aplicando desconto de Plano de Saúde...");
            PacienteEspecial pacienteEspecial = (PacienteEspecial) paciente;
            PlanoDeSaude plano = pacienteEspecial.getPlanoDeSaude();
            double descontoDoPlano = plano.getDescontoPara(consulta.getEspecialidadeDaConsulta());
            double valorFinal = custoBase * (1.0 - descontoDoPlano);
            plano.registrarEconomia(custoBase - valorFinal);
            return valorFinal;
        } else if (paciente.getIdade() >= 60) {
            System.out.println("-> Aplicando desconto para maiores de 60 anos...");
            return custoBase * Fator_desconto_idoso;
        } else {
            System.out.println("-> Aplicando custo padrão (sem descontos).");
            return custoBase;
        }
    }
    public void cancelarConsulta(Scanner scanner) {
        System.out.println("\n--- Cancelar Consulta Agendada ---");
        Paciente paciente = Searcher.getPaciente(InputHandler.lerTextoNaoVazio("Digite o CPF do paciente: ", scanner), this.pacientes);
        if (paciente == null) {
            System.out.println("Erro: Paciente não encontrado.");
            return;
        }
        Consultas consulta = buscarConsultaAgendadaPorPaciente(paciente);
        if (consulta == null) {
            System.out.println("Erro: Nenhuma consulta agendada encontrada para este paciente.");
            return;
        }
        System.out.println("Consulta encontrada: Dr(a). " + consulta.getMedico().getNome() + " em " + consulta.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        boolean confirmar = InputHandler.lerSimNao("Confirmar o cancelamento desta consulta? (S/N): ", scanner);
        if (confirmar) {
            consulta.setStatusConsulta(StatusConsulta.CANCELADO);
            System.out.println("Consulta cancelada com sucesso.");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

}
