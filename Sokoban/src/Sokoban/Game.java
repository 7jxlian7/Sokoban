/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

import Exceptions.BuilderException;
import Board.Position;
import Board.Board;
import Board.Directions;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jforme
 */
public class Game  {

    public Player player;
    public ArrayList<Position> movements = new ArrayList<>();
    public static Board board;
    public static int board_id;

    public Game(Player p) {
        this.player = p;
    }

    /**
     * Méthode permettant de jouer au jeu.
     * 
     * @throws BuilderException
     * @throws SQLException
     */
    public void run() throws BuilderException, SQLException {
        boolean ended;

        try {
            board.drawBoard();
            do {
                move(board);
                board.drawBoard();
                ended = ended(board);
            } while (!ended);
            displayWinningText();
        } catch (BuilderException e) {

        }

    }

    /**
     * Méthode permettant d'afficher le texte lorsque qu'une partie est gagnée.
     * 
     */
    public void displayWinningText() {
        System.out.println("* Partie terminée");
        System.out.println("* Félicitations! Voici la liste des déplacements effectués : ");
        System.out.print("*");
        movements.forEach(move -> {
            System.out.print(" (" + move.row + "," + move.col + ")");
        });
        System.out.println();
        System.out.println();
    }

    /**
     * Méthode permettant de tester si une partie est terminée.
     * 
     * @param b le board à tester.
     * @return true ssi la partie est terminée.
     */
    public boolean ended(Board b) {
        return b.targets.equals(b.boxes);
    }

    /**
     * Méthode permettant de faire bouger le joueur.
     * 
     * @param b le board sur lequel se déroule le jeu.
     * @return la position choisie par le joueur.
     * @throws SQLException
     */
    public Position move(Board b) throws SQLException {
        Position p = writeCoordinates(b);
        return p;
    }

    /**
     * Méthode permettant de lire les coordonnées entrées par le joueur.
     * 
     * @param b le board sur lequel se déroule le jeu.
     * @return le choix de l'utilisateur
     * @throws SQLException
     */
    public String readCoordinates(Board b) throws SQLException {
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        if ("/leave".equals(choice) || "/quit".equals(choice)) {
            System.out.println("* La partie vient d'être annulée");
            // Database.update(board_id, b);
            System.exit(0);
        }
        choice = choice.toUpperCase();
        return choice;
    }

    /**
     * Méthode permettant d'intéragir avec l'utilisateur pour une action.
     * 
     * @param b le board sur lequel se déroule le jeu.
     * @return la nouvelle position de l'utilisateur
     * @throws SQLException
     */
    public Position writeCoordinates(Board b) throws SQLException {
        Position p = null;
        String choice;
        boolean askAgain;
        do {
            System.out.println("* Quelle action voulez-vous effectuer? (U, D, L, R)");
            choice = readCoordinates(b);
            askAgain = false;
            for (int i = 0; i < choice.length(); i++) {
                switch (choice.charAt(i)) {
                    case 'U':
                        p = makeMoves(b, Directions.NORD);
                        break;
                    case 'D':
                        p = makeMoves(b, Directions.SUD);
                        break;
                    case 'L':
                        p = makeMoves(b, Directions.OUEST);
                        break;
                    case 'R':
                        p = makeMoves(b, Directions.EST);
                        break;
                    default:
                        System.out.println("* Le mouvement " + choice.charAt(i) + " est invalide.");
                        break;
                }
            }
        } while (askAgain);
        return p;
    }

     /**
     * Méthode permettant d'effectuer les déplacements sur le plateau.
     * 
     * @param board le board sur lequel se déroule le jeu.
     * @param d la direction choisie.
     * @return la nouvelle position de l'utilisateur
     */
    public Position makeMoves(Board board, Directions d) {
        Position nextPosition = new Position(board.character.row + d.mvtVertical(), board.character.col + d.mvtHorizontal());

        // Si notre prochaine position n'est pas un mur et est dans le plateau
        if (!board.isCollisionWithWall(nextPosition) && board.isInBoard(nextPosition)) {
            // Si notre prochaine position contient une boîte
            if (board.isCollisionWithBox(nextPosition)) {
                boolean isBoxMoving = moveBox(nextPosition, d);
                if (isBoxMoving) {
                    moveCharacter(nextPosition);
                }
                // Si pas de boîte sur notre prochaine position, on avance normalement
            } else {
                moveCharacter(nextPosition);
            }
        }
        return nextPosition;
    }

    /**
     * Méthode permettant d'effectuer le déplacement du joueur.
     * 
     * @param nextPosition la position choisie par le joueur.
     */
    public void moveCharacter(Position nextPosition) {
        board.setPosition(nextPosition.row, nextPosition.col);
        addPosition(nextPosition);
    }

    /**
     * Méthode permettant de déplacer une boîte et de renvoyer vrai si une boîte
     * a été déplacée
     *
     * @param nextPosition Position dans de la boîte à bouger
     * @param d Direction dans laquelle bouger la boîte
     * @return true ssi une boîte a été déplacée
     */
    public boolean moveBox(Position nextPosition, Directions d) {
        Position nextBoxPosition = nextPosition;
        boolean isBoxMoving = false;
        // On regarde la position suivant tant qu'on tombe sur une case
        do {
            nextBoxPosition = new Position(nextBoxPosition.row + d.mvtVertical(), nextBoxPosition.col + d.mvtHorizontal());
        } while (board.isCollisionWithBox(nextBoxPosition));
        // Si notre position est libre, on bouge, sinon on ne fait rien
        if (!board.isCollisionWithWall(nextBoxPosition) && board.isInBoard(nextBoxPosition)) {
            board.boxes.remove(nextPosition);
            board.boxes.add(nextBoxPosition);
            isBoxMoving = true;
        }
        return isBoxMoving;
    }

    public void addPosition(Position p) {
        movements.add(p);
    }
}
