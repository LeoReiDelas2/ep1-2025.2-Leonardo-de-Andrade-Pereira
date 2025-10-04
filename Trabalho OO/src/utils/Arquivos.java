package utils;

import Services.Hospital;
import entities.Paciente;
import entities.PacienteEspecial;

import java.io.*;
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
        File [] arquivos = pastaArquivo.listFiles((dir, nome) -> nome.endsWith(nomearquivo));
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
}
