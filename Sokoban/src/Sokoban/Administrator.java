/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author Julian Forme
 */
public class Administrator {

    public static Database db = new Database();

    public static void main(String[] args) throws BuilderException, SQLException {

        boolean again = true;

        while (again) {
            System.out.println();
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
                    db.createDatabase();
                    break;
                case "2":
                    listBoards();
                    break;
                case "3":
                    //showBoard();
                    break;
                case "4":
                    Board b = boardFormFile();
                    if (b != null) {
                        db.add(b);
                    }
                    break;
                case "5":
                    db.clearDatabase();
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

    public static Board boardFormFile() throws BuilderException {
        File dir = new File("data");
        String fileList[] = dir.list();
        boolean build = false;
        Board board = null;

        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                System.out.println("* " + fileList[i]);
            }
        } else {
            System.out.println("* Nom de fichier invalide");
        }
        System.out.println();

        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        choice = choice.toLowerCase().replaceAll(" ", "");
        if (!choice.contains(".txt")) {
            choice = choice.concat(".txt");
        }

        for (int i = 0; i < fileList.length; i++) {
            fileList[i] = fileList[i].toLowerCase().replaceAll(" ", "");
            if (fileList[i].equals(choice)) {
                build = true;
            }
        }

        if (build) {
            choice = choice.toLowerCase().replaceAll(" ", "");

            FileBoardBuilder b = new FileBoardBuilder(choice);
            board = b.build();
            System.out.println();
        } else {
            System.out.println("* Le fichier que vous avez saisi est incorrect.");
        }

        return board;
    }

    public static void listBoards() throws SQLException {
        ArrayList<String> boards = db.getAllBoards();
        System.out.println("* Voici la liste des plateaux de jeu :");
        System.out.println();
        for (String boardName : boards) {
            System.out.println("* " + boardName);
        }
    }

}
