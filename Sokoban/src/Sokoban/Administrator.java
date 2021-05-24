/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

import Exceptions.BuilderException;
import Board.FileBoardBuilder;
import Board.Board;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Julian Forme
 */
public class Administrator {

    /**
     *
     */
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
            System.out.println("6. Clear all boards from database [DANGEROUS]");
            System.out.println("7. Delete database [DANGEROUS]");
            System.out.println("8. Quit");

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
                    if(isGameReady()){
                        Player.main(args);
                    }
                    break;
                case "4":
                    Board b = boardFormFile();
                    if (b != null) {
                        db.add(b);
                    }
                    break;
                case "5":
                    removeBoard();
                    break;
                case "6":
                    db.clearDatabase();
                    break;
                case "7":
                    db.deleteDatabase();
                    break;
                case "8":
                    System.out.println("* La partie vient d'être annulée");
                    System.exit(0);
                    break;
                default:
                    System.out.println("* Commande inconnue : " + commande);
                    System.out.println();
            }
        }
    }

    /**
     * Méthode permettant de demander l'affichage d'un plateau de jeu.
     *
     * @return l'entier du board choisi
     * @throws SQLException
     */
    public static int showBoard() throws SQLException {
        int id = -1;

        HashMap<Integer, String> boards = db.getAllBoards();
        if (!boards.isEmpty()) {
            listBoards();
            System.out.println("* Veuillez entrer l'ID du plateau de jeu.");
            System.out.println();
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            System.out.println();
            try {
                id = Integer.parseInt(choice);
            } catch (NumberFormatException ex) {
                System.out.println("* Erreur : Veuillez entrer un nombre.");
            }
        }

        return id;
    }

    /**
     * Méthode permettant de construire un plateau à partir d'un fichier.
     *
     * @return le plateau construit
     * @throws BuilderException
     */
    public static Board boardFormFile() throws BuilderException {
        File dir = new File("data");
        String fileList[] = dir.list();
        boolean build = false;
        Board board = null;

        if (fileList != null) {
            for (String fileList1 : fileList) {
                System.out.println("* " + fileList1);
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

    /**
     * Méthode permettant de lister les plateaux de la base de données.
     *
     * @throws SQLException
     */
    public static void listBoards() throws SQLException {
        HashMap<Integer, String> boards = db.getAllBoards();
        if (!boards.isEmpty()) {
            System.out.println("* Voici la liste des plateaux de jeu :");
            System.out.println();
            boards.entrySet().forEach(board -> {
                System.out.println("* " + board.getValue() + " (Board ID : " + board.getKey() + ")");
            });
        } else {
            System.out.println("* Il n'y a aucun plateau enregistré dans la base.");
        }
        System.out.println();
    }

    /**
     * Méthode permettant de supprimer un plateau choisi par l'utilisateur.
     *
     * @throws SQLException
     */
    public static void removeBoard() throws SQLException {
        listBoards();
        int id = -1;
        boolean boardExisting = false;
        System.out.println("* Veuillez entrer l'ID du plateau de jeu à supprimer.");
        System.out.println();
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        System.out.println();
        try {
            id = Integer.parseInt(choice);
        } catch (NumberFormatException ex) {
            System.out.println("* Erreur : Veuillez entrer un nombre.");
        }
        HashMap<Integer, String> boards = db.getAllBoards();
        for (Map.Entry board : boards.entrySet()) {
            if (board.getKey().equals(id)) {
                boardExisting = true;
            }
        }
        if (boardExisting && id > -1) {
            db.remove(id);
        } else {
            System.out.println("* Erreur : Le plateau de jeu n'existe pas.");
        }
    }

    public static boolean isGameReady() throws SQLException, BuilderException {
        HashMap<Integer, String> boards = db.getAllBoards();
        boolean launch = false;
        if (!boards.isEmpty()) {
            int board_id = showBoard();
            Game.board = db.get(board_id);
            if (Game.board != null) {
                Game.board_id = board_id;
                launch = true;
            } else {
                System.out.println("* Erreur : Le plateau de jeu choisi n'existe pas.");
            }
        } else {
            System.out.println("* Erreur : Aucun plateau de jeu n'existe.");
        }
        return launch;
    }
}
