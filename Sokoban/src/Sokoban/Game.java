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
    
    public Game(Player p){
        this.player = p;
    }
    
    public void run(){
        boolean ended = false;
        Board b = createBoard();
        do{
            movements.add(move(b));
            b.drawBoard();
        }while(!ended);
    }
    
    public Board createBoard(){
        Board b = new Board("Mon super plateau", 5, 6);
        b.addHorizontalWall(0, 0, 5);
        b.addHorizontalWall(4, 0, 6);
        b.addVerticalWall(0, 0, 5);
        b.addVerticalWall(0, 5, 5);
        b.addBox(2, 1);
        b.addBox(2, 3);
        b.addTarget(3, 1);
        b.addTarget(3, 2);
        b.setPosition(3, 4);
        return b;
    }
    
    public boolean play(Position p){
        return true;
    }
    
    public Position move(Board b){
        Position p = writeCoordinates(b);
        return new Position(0,0);
    }
         
    public Position writeCoordinates(Board b){
        Position p = new Position(0,0);
        String choice;
        boolean askAgain = true;
        do{
            System.out.println("* Quelle action voulez-vous effectuer? (U, D, L, R)");
            //try{
                choice = readCoordinates(b);
                askAgain = !play(p);
                for(int i = 0; i < choice.length(); i++){
                    switch(choice.charAt(i)){
                        case 'U':
                            b.character.row += Directions.NORD.mvtVertical();
                            break;
                        case 'D':
                            b.character.row += Directions.SUD.mvtVertical();
                            break;
                        case 'L':
                            b.character.col += Directions.OUEST.mvtHorizontal();
                            break;
                        case 'R':
                            b.character.col += Directions.EST.mvtHorizontal();
                            break;
                        default:
                            break;
                    }
                }
            //} catch (InvalidCoordinatesException e){
             //   System.out.println("TEST");
            //}
        } while(askAgain);
        return p;
    }
    
    public String readCoordinates(Board b){
        String choice;
        Position charPos = b.character;
        Scanner in = new Scanner(System.in);
        choice = in.nextLine();
        if("/leave".equals(choice)){
            System.out.println("* La partie vient d'être annulée");
            System.exit(0);
        }
        
        
        return choice;
    }
}
