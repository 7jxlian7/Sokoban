/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Julian
 */
public class Board {

    String name;
    int row, col;
    Set<Position> boxes = new HashSet<>();
    Set<Position> walls = new HashSet<>();
    Set<Position> targets = new HashSet<>();
    Position character;

    public Board(String name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
    }
    
    public void drawNumbers() {
        int number = 0;
        System.out.print("   ");
        for (int i = 0; i < col; i++) {
            if (i < 9) {
                System.out.print(" ");
            }
            System.out.print(number + " ");
            number += 1;
        }
        System.out.println("");
    }
    
    public void drawContent(){
        char[][] board = new char[row][col];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
        boxes.forEach(box -> {
            board[box.row][box.col] = 'C';
        });
        targets.forEach(target -> {
            board[target.row][target.col] = 'x';
        });
        walls.forEach(wall -> {
            board[wall.row][wall.col] = '#';
        });
        board[character.row][character.col] = 'P';
        
        for (int i = 0; i < row; i++) {
            int u = 0;
            if (i <= 9) {
                System.out.print(" ");
            }
            System.out.print(i + " ");
            while (u < col) {

                System.out.print(" ");
                System.out.print(board[i][u]);
                System.out.print(" ");
                u++;
            }
            System.out.println("");
        }
        
    }
    
    public void drawBoard(){
        drawNumbers();
        drawContent();       
    }
    
    public void setPosition(int row, int col){
        character = new Position(row, col);
    }
    
    public void addBox(int row, int col){
        boxes.add(new Position(row, col));
    }
    
    public void addTarget(int row, int col){
        targets.add(new Position(row, col));
    }
    
    public void addHorizontalWall(int row, int col, int length){
        
        for(int i = 0; i < length; i++){
            walls.add(new Position(row, col+i));
        }
    }
    
    public void addVerticalWall(int row, int col, int length){
        for(int i = 0; i < length; i++){
            walls.add(new Position(row+i, col));
        }
    }
    
    public void showInfos(){
        
        System.out.println("Walls : ");
        for(Position p : walls){
            System.out.println("* " + p.row + "," + p.col);
        }
        
        System.out.println("Boxes : ");
        for(Position p : boxes){
            System.out.println("* " + p.row + "," + p.col);
        }
        
        System.out.println("Targets : ");
        for(Position p : targets){
            System.out.println("* " + p.row + "," + p.col);
        }
        
        System.out.println("Character : ");
            System.out.println("* " + character.row + "," + character.col);
    }
    
}