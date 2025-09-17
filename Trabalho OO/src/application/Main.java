package application;

import java.util.Scanner;
import utils.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int escolha;

        do{
            System.out.println("BEM VINDO AO MENU\ndigite 1 para modo paciente\ndigite 2 para modo medico");
            escolha = InputHandler.digitarIntIntervalo("digite um inteiro entre 0 e 2", sc,0,2);

            switch (escolha)
            {
                case 1:
                    System.out.println("digitou 1");
                    break;

                case 2:
                    System.out.println("digitou 2");
                    break;

                case 0:
                    System.out.println("digitou 0");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("digitou outro num");
                    break;

            }
        }while(escolha !=0);
    }
}