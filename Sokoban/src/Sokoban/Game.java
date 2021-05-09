/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jforme
 */
public class Game {

    public Player player;
    public ArrayList<Position> movements = new ArrayList<>();
    Board board;

    public Game(Player p, Board b) {
        this.player = p;
        this.board = b;
    }

    public void run() {
        boolean ended;
        board = createBoard();
        board.drawBoard();
        do {
            move(board);
            board.drawBoard();
            ended = ended(board);
        } while (!ended);
        displayWinningText();
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
        System.out.println("* Félicitations! Voici la liste des déplacements : ");
        System.out.print("*");
        movements.forEach(move -> {
            System.out.print(" (" + move.row + "," + move.col + ")");
        });
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
        if ("/leave".equals(choice)) {
            System.out.println("* La partie vient d'être annulée");
            System.exit(0);
        }
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
                        p = moveCharacter(b, Directions.NORD);
                        break;
                    case 'D':
                        p = moveCharacter(b, Directions.SUD);
                        break;
                    case 'L':
                        p = moveCharacter(b, Directions.OUEST);
                        break;
                    case 'R':
                        p = moveCharacter(b, Directions.EST);
                        break;
                    default:
                        System.out.println("* Le mouvement " + choice.charAt(i) + " est invalide.");
                        break;
                }
            }
        } while (askAgain);
        return p;
    }

    public Position moveCharacter(Board board, Directions d) {

        Position nextPosition = new Position(board.character.row + d.mvtVertical(), board.character.col + d.mvtHorizontal());
        Position nextBoxPosition = nextPosition;

        // Si notre prochaine position n'est pas un mur et est dans le plateau
        if (!board.isCollisionWithWall(nextPosition) && board.isInBoard(nextPosition)) {
            // Si notre prochaine position contient une boîte
            if (board.isCollisionWithBox(nextPosition)) {
                boolean isBox = true;
                do {
                    nextBoxPosition = new Position(nextBoxPosition.row + d.mvtVertical(), nextBoxPosition.col + d.mvtHorizontal());
                    // Si ma position après ma boîte est libre et est dans le plateau
                    if (!board.isCollisionWithWall(nextBoxPosition) && !board.isCollisionWithBox(nextBoxPosition) && board.isInBoard(nextBoxPosition)) {
                        board.boxes.remove(nextPosition);
                        board.boxes.add(nextBoxPosition);
                        board.setPosition(nextPosition.row, nextPosition.col);
                        addPosition(nextPosition);
                        isBox = false;
                    }
                } while (board.isCollisionWithBox(nextBoxPosition) && isBox);
                // Si pas de boîte sur notre prochaine position, on avance normalement
            } else {
                board.setPosition(nextPosition.row, nextPosition.col);
                addPosition(nextPosition);
            }
        }
        return nextPosition;
    }

    public void addPosition(Position p) {
        movements.add(p);
    }
}
