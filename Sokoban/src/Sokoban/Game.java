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
        System.out.println("* Partie terminée");
    }

    public Board createBoard() {
        board.addHorizontalWall(0, 0, 5);
        board.addHorizontalWall(9, 0, 6);
        board.addVerticalWall(0, 0, 10);
        board.addVerticalWall(0, 5, 10);
        board.addHorizontalWall(5, 2, 2);
        board.addBox(2, 1);
        board.addBox(2, 3);
        board.addTarget(3, 1);
        board.addTarget(1, 4);
        board.setPosition(3, 4);
        return board;
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
            //try{
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
                        break;
                }
            }
            //} catch (InvalidCoordinatesException e){
            //   System.out.println("TEST");
            //}
        } while (askAgain);
        return p;
    }

    public Position moveCharacter(Board b, Directions d) {

        Position p = new Position(b.character.row + d.mvtVertical(), b.character.col + d.mvtHorizontal());

        if (b.isInBoard(p) && !b.isCollisionWithWall(d, p)) {
            if (d == Directions.NORD || d == Directions.SUD) {
                b.character.row += d.mvtVertical();
            } else {
                b.character.col += d.mvtHorizontal();
            }
            addPosition(p);
            System.out.println("* Coup effectué : " + d.toString());
        } else {
            System.out.println("* Impossible d'aller dans cette direction.");
        }
        return p;
    }
    
    public void addPosition(Position p){
        movements.add(p);
    }
}
