/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jforme
 */
public class Game implements BoardBuilder {

    public Player player;
    public ArrayList<Position> movements = new ArrayList<>();
    public Board board;

    public Game(Player p) {
        this.player = p;
    }

    public void run() throws BuilderException {
        boolean ended;

        /*board = createBoard();
        board.drawBoard();*/
        
        //FileBoardBuilder builder = new FileBoardBuilder(level);

        /*builder.addRow("#####");
        builder.addRow("#x.x#");
        builder.addRow("#x.C#");
        builder.addRow("#C..#");
        builder.addRow("#CP.#");
        builder.addRow("#.#.#");
        builder.addRow("#...#");
        builder.addRow("#####");*/
        try {
            // board = builder.build();
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

    public Board createBoard() {
        board.addHorizontalWall(0, 0, 5);
        board.addHorizontalWall(9, 0, 6);
        board.addVerticalWall(0, 0, 10);
        board.addVerticalWall(0, 5, 10);
        board.addHorizontalWall(5, 2, 2);
        board.addBox(2, 1);
        board.addBox(2, 4);
        board.addTarget(3, 1);
        board.addTarget(1, 4);
        board.setPosition(3, 4);
        return board;
    }

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

    public boolean ended(Board b) {
        return b.targets.equals(b.boxes);
    }

    public Position move(Board b) {
        Position p = writeCoordinates(b);
        return p;
    }

    public String readCoordinates(Board b) {
        String choice;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        if ("/leave".equals(choice) || "/quit".equals(choice)) {
            System.out.println("* La partie vient d'être annulée");
            System.exit(0);
        }
        choice = choice.toUpperCase();
        return choice;
    }

    public Position writeCoordinates(Board b) {
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

    @Override
    public Board build() throws BuilderException {
        return createBoard();
    }
}
