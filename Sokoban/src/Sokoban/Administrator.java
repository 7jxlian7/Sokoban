/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

import java.util.Scanner;

/**
 *
 * @author Julian Forme
 */
public class Administrator {

    public static void main(String[] args) throws BuilderException {
        boolean again = true;
        while (again) {
            System.out.println("1. Create new database");
            System.out.println("2. List boards");
            System.out.println("3. Show board");
            System.out.println("4. Add board from file");
            System.out.println("5. Remove board from database [DANGEROUS]");
            System.out.println("6. Quit");

            Scanner in = new Scanner(System.in);
            String commande = in.nextLine();
            System.out.println();

            switch (commande) {
                case "1":
                    again = false;
                    break;
                case "2":
                    //listBoard();
                    break;
                case "3":
                    Player.main(args);
                    break;
                case "4":
                    // chercherLivreParTitre(c);
                    break;
                case "5":
                    // chercherLivreParRef(c);
                    break;
                case "6":
                    System.out.println("* La partie vient d'être annulée");
                    System.exit(0);
                    break;
                default:
                    System.out.println("* Commande inconnue : " + commande);
                    System.out.println();
            }
        }
    }
}
