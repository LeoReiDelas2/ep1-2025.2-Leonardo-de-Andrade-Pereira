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
}