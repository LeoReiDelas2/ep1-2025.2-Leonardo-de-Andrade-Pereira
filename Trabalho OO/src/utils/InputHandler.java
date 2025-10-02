package utils;

import java.util.Scanner;

public class InputHandler
{
    static public int digitarIntIntervalo(String mensagem, Scanner scanner, int min, int max)
    {
        while(true)
        {
            System.out.println(mensagem);
            try
            {
                String numero = scanner.nextLine();
                int escolha = Integer.parseInt(numero);

                if (escolha < min || escolha > max)
                {
                    throw(new Exception("numero digitado fora dos limites"));
                }
                return escolha;
            }catch(NumberFormatException e)
            {
                System.out.println("digite um numero, não uma string");
            }catch(Exception e)
            {
                System.out.println(e.getMessage());
                System.out.println("minimo: "+min);
                System.out.println("maximo: "+max);
            }
        }
    }
    static public <T extends Enum<T>> T digitarEnum(String mensagem, Scanner scanner, Class<T> enumClass){
        T[] options = enumClass.getEnumConstants();
        while (true) {
            System.out.println(mensagem);
            System.out.println("Opções disponíveis:");
            for (T option : options) {
                System.out.println("- " + option.name());
            }
            System.out.print("Digite a opção desejada: ");
            try {
                String texto = scanner.nextLine().trim().toUpperCase();
                T escolha = T.valueOf(enumClass, texto);
                return escolha;
            } catch (IllegalArgumentException e) {
                System.out.println("\nErro: Por favor, digite exatamente uma das opções listadas.\n");
            }
        }

    }
    static public String lerTextoNaoVazio(String mensagem, Scanner scanner)
    {
        while (true)
        {
            System.out.print(mensagem);
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty())
            {
                return texto;
            }
            System.out.println("Erro: O campo não pode estar vazio. Tente novamente.");
        }
    }

    static public boolean lerSimNao(String mensagem, Scanner scanner)
    {
        while (true)
        {
            System.out.print(mensagem);
            String resposta = scanner.nextLine().trim().toUpperCase();
            if (resposta.equals("S") || resposta.equals("SIM"))
            {
                return true;
            } else if (resposta.equals("N") || resposta.equals("NAO") || resposta.equals("NÃO"))
            {
                return false;
            }
            System.out.println("Erro: Resposta inválida. Digite S ou N.");
        }
    }
    static public double lerDoubleIntervalo(String mensagem, double min, double max, Scanner scanner)
    {
        while (true)
        {
            System.out.print(mensagem);
            try
            {
                String linha = scanner.nextLine();
                double valor = Double.parseDouble(linha);
                if (valor >= min && valor <= max)
                {
                    return valor;
                }
                System.out.println("Erro: O valor deve estar entre " + min + " e " + max + ".");
            } catch (NumberFormatException e)
            {
                System.out.println("Erro: Digite um número válido.");
            }
        }
    }

}