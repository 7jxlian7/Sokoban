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
    char[][] board;

    public Board(String name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
    }

    public void buildTextBoard(){
        board = new char[row][col];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
        targets.forEach(target -> {
            board[target.row][target.col] = 'x';
        });
        boxes.forEach(box -> {
            board[box.row][box.col] = 'C';
        });
        walls.forEach(wall -> {
            board[wall.row][wall.col] = '#';
        });
        board[character.row][character.col] = 'P';
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

    public void drawContent() {
        buildTextBoard();
        for (int i = 0; i < row; i++) {
            int u = 0;
            if (i <= 9) {
                System.out.print(" ");
            }
            System.out.print(i + " ");
            while (u < col) {
                System.out.print(" " + board[i][u] + " ");
                u++;
            }
            System.out.println("");
        }
        
    }

    public void drawBoard() throws BuilderException {
        System.out.println();
        System.out.println("* " + name + " *");
        System.out.println();
        drawNumbers();
        drawContent();
        System.out.println(); 
    }

    public void setPosition(int row, int col) {
        character = new Position(row, col);
    }

    public void addBox(int row, int col) {
        boxes.add(new Position(row, col));
    }

    public void addTarget(int row, int col) {
        targets.add(new Position(row, col));
    }

    public void addHorizontalWall(int row, int col, int length) {
        for (int i = 0; i < length; i++) {
            walls.add(new Position(row, col + i));
        }
    }

    public void addVerticalWall(int row, int col, int length) {
        for (int i = 0; i < length; i++) {
            walls.add(new Position(row + i, col));
        }
    }

    public void showInfos() {

        System.out.println("Walls : ");
        walls.forEach(p -> {
            System.out.println("* " + p.row + "," + p.col);
        });

        System.out.println("Boxes : ");
        boxes.forEach(p -> {
            System.out.println("* " + p.row + "," + p.col);
        });

        System.out.println("Targets : ");
        targets.forEach(p -> {
            System.out.println("* " + p.row + "," + p.col);
        });

        System.out.println("Character : ");
        System.out.println("* " + character.row + "," + character.col);
    }

    public boolean isInBoard(Position p) {
        return p.row >= 0 && p.row < row && p.col >= 0 && p.col < col;
    }

    public boolean isFree(Position p){
        return !isCollisionWithWall(p) && !isCollisionWithBox(p);
    }
    
    public boolean isCollisionWithWall(Position p) {
        boolean isWall = false;
        for (Position wall : walls) {
            if (wall.row == p.row && wall.col == p.col) {
                isWall = true;
            }
        }
        return isWall;
    }

    boolean isCollisionWithBox(Position p) {
        boolean isBox = false;
        for(Position box : boxes){
            if (box.row == p.row && box.col == p.col) {
                isBox = true;
            }
        }
        return isBox;
    }
    
    public String rowToText(int rowNumber){
        String rowText = "";
        for(int c = 0; c < col; c++){
            String oneChar = Character.toString(board[rowNumber][c]);
            rowText = rowText.concat(oneChar);
        }
        return rowText;
    }
}
