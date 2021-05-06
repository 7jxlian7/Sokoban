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

    public int nbTurn;
    public Player player;
    public ArrayList<Position> movements = new ArrayList<>();

    public Game(Player p) {
        this.player = p;
    }

    public void run() {
        boolean ended = false;
        Board b = createBoard();
        b.drawBoard();
        do {
            movements.add(move(b));
            b.drawBoard();
        } while (!ended);
    }

    public Board createBoard() {
        Board b = new Board("Mon super plateau", 5, 6);
        b.addHorizontalWall(0, 0, 5);
        b.addHorizontalWall(4, 0, 6);
        b.addVerticalWall(0, 0, 5);
        b.addVerticalWall(0, 5, 5);
        b.addBox(2, 1);
        b.addBox(3, 3);
        b.addTarget(2, 1);
        b.addTarget(3, 3);
        b.setPosition(3, 4);
        return b;
    }
    
    public boolean ended(Board b) {
        boolean booleann = b.targets.equals(b.boxes);
        System.out.println(booleann);
        return booleann;
        //return false;
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
        Position p = new Position(0, 0);
        String choice;
        boolean askAgain = true;
        do {
            System.out.println("* Quelle action voulez-vous effectuer? (U, D, L, R)");
            //try{
            choice = readCoordinates(b);
            askAgain = ended(b);
            for (int i = 0; i < choice.length(); i++) {
                switch (choice.charAt(i)) {
                    case 'U':
                        moveCharacter(b, Directions.NORD);
                        break;
                    case 'D':
                        moveCharacter(b, Directions.SUD);
                        break;
                    case 'L':
                        moveCharacter(b, Directions.OUEST);
                        break;
                    case 'R':
                        moveCharacter(b, Directions.EST);
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
    
    public void moveCharacter(Board b, Directions d) {
        int move;
        if(d == Directions.NORD || d == Directions.SUD){
            move = d.mvtVertical();
            
        } else {
            move = d.mvtHorizontal();
        }
        
        Position p = new Position(b.character.row + move, b.character.col);

        if (b.isInBoard(p) && !b.isCollisionWithWall(d,p)) {
            if(d == Directions.NORD || d == Directions.SUD){
            b.character.row += d.mvtVertical();
        } else {
            b.character.col += d.mvtHorizontal();
        }
        } else {
            System.out.println("* Impossible d'aller dans cette direction.");
        }
    }
}
