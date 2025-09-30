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
                System.out.println("\nOpção inválida. Por favor, digite exatamente uma das opções listadas.\n");
            }
        }

    }
}