package utils;

import Services.Hospital;
import entities.*;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Arquivos
{
    public static void SalvarArquivo(String pasta, String nome, String dados)
    {
        String local = "Banco_de_Dados/" + pasta;
        new File(local).mkdirs();
        String arquivo = local + '/' + nome + ".txt";
        try(BufferedWriter salvar = new BufferedWriter(new FileWriter(arquivo)))
        {
            salvar.write(dados);
        } catch (IOException e){
            System.out.println("Erro ao salvar arquivo");
        }
    }
    public static void SalvarPaciente(Paciente paciente)
    {
        String cpf = paciente.getCpf();
        String dados = paciente.objectToString();
        SalvarArquivo("pacientes", cpf + "paciente", dados);
    }
    public static List<String> carregarArquivos(String pasta, String nomearquivo)
    {
        File pastaArquivo = new File("Banco_de_Dados/" + pasta);
        if (!(pastaArquivo.exists() && pastaArquivo.isDirectory()))
        {
            return null;
        }
        File[] arquivos = pastaArquivo.listFiles((dir, nome) -> nome.endsWith(nomearquivo));
        if (arquivos == null)
        {
            return null;
        }
        List<String> informacoes = new ArrayList<>();
        for (File arquivo : arquivos)
        {
            try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo)))
            {
                String dados = leitor.readLine();
                informacoes.add(dados + "@");

            } catch (IOException e) {
                System.out.println("Erro ao carregar arquivos");
            }
        }
        return informacoes;
    }
    public static void carregarPacientes()
    {
        List<String> pacientesarquivos = carregarArquivos("pacientes", "paciente.txt");
        if (pacientesarquivos == null)
        {
            return;
        }
        List<Paciente> pacientes = new ArrayList<>();
        for (String pacientearquivo : pacientesarquivos)
        {
            Paciente paciente = Paciente.fromString(pacientearquivo);
            pacientes.add(paciente);
        }
        Hospital.setPacientes(pacientes);
    }
    public static void SalvarMedico(Medico medico)
    {
        String crm = medico.getCrm();
        String dados = medico.objectToString();
        SalvarArquivo("medicos", crm + "medico", dados);
    }
    public static void carregarMedicos()
    {
        List<String> medicosarquivos = carregarArquivos("medicos", "medico.txt");
        if (medicosarquivos == null)
        {
            return;
        }
        List<Medico> medicos = new ArrayList<>();
        for (String medicoarquivo : medicosarquivos)
        {
            Medico medico = Medico.fromString(medicoarquivo.replace("@",""));
            medicos.add(medico);
        }
        Hospital.setMedicos(medicos);
    }
    public static void SalvarConsulta(Consultas consulta)
    {
        String cpf = consulta.getPaciente().getCpf();
        String crm = consulta.getMedico().getCrm();
        String dataFormatada = consulta.getDataHora()
                .format(java.time.format.DateTimeFormatter.ofPattern("ddMMyyyyHHmm"));
        String nomeArquivo = cpf + "_" + crm + "_" + dataFormatada + "consulta";
        String dados = consulta.objectToString();
        SalvarArquivo("consultas", nomeArquivo + "consulta", dados);
    }
    public static void carregarConsultas()
    {
        List<String> consultasarquivos = carregarArquivos("consultas", "consulta.txt");
        if (consultasarquivos == null)
        {
            return;
        }
        List<Paciente> todosPacientes = Hospital.getPacientes();
        List<Medico> todosMedicos = Hospital.getMedicos();
        List<Especialidade> todasEspecialidades = Hospital.getEspecialidades();
        List<Consultas> consultas = new ArrayList<>();
        for (String consultaarquivo : consultasarquivos)
        {
            Consultas consulta = Consultas.fromString(
                    consultaarquivo,
                    todosPacientes,
                    todosMedicos,
                    todasEspecialidades
            );
            if (consulta != null) {
                consultas.add(consulta);
            }
        }
        Hospital.setConsultas(consultas);
    }
    public static void SalvarInternacao(Internacao internacao)
    {
        String cpf = internacao.getPaciente().getCpf();
        String crm = internacao.getResponsavel().getCrm();
        String dataFormatada = internacao.getDataEntrada()
                .format(java.time.format.DateTimeFormatter.ofPattern("ddMMyyyyHHmm"));
        String nomeArquivo = cpf + "_" + crm + "_" + dataFormatada + "internacao";
        String dados = internacao.objectToString();
        SalvarArquivo("internacoes", nomeArquivo + "internacao", dados);
    }
    public static void carregarInternacoes()
    {
        List<String> internacoesarquivos = carregarArquivos("internacoes", "internacao.txt");
        if (internacoesarquivos == null)
        {
            return;
        }
        List<Paciente> todosPacientes = Hospital.getPacientes();
        List<Medico> todosMedicos = Hospital.getMedicos();
        List<Internacao> internacoes = new ArrayList<>();
        for (String internacaoarquivo : internacoesarquivos)
        {
            Internacao internacao = Internacao.fromString(
                    internacaoarquivo,
                    todosPacientes,
                    todosMedicos
            );
            if (internacao != null) {
                internacoes.add(internacao);
                internacao.getPaciente().adicionarInternacao(internacao);
            }
        }
        Hospital.setInternacoes(internacoes);
    }
    public static void SalvarPlanoDeSaude(PlanoDeSaude plano)
    {
        String nomeArquivo = plano.getNome().replaceAll("\\s+", "_") + "plano";
        String dados = plano.objectToString();

        SalvarArquivo("planos", nomeArquivo + "plano", dados);
    }
    public static void carregarPlanosDeSaude()
    {
        List<String> planosarquivos = carregarArquivos("planos", "plano.txt");
        if (planosarquivos == null)
        {
            return;
        }
        List<Especialidade> todasEspecialidades = Hospital.getEspecialidades();
        List<PlanoDeSaude> planos = new ArrayList<>();
        for (String planoarquivo : planosarquivos)
        {
            PlanoDeSaude plano = PlanoDeSaude.fromString(
                    planoarquivo,
                    todasEspecialidades
            );
            if (plano != null) {
                planos.add(plano);
            }
        }
        Hospital.setPlanoDeSaude(planos);
    }
}

