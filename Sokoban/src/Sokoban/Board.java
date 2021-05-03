/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

import java.util.Arrays;

/**
 *
 * @author Julian
 */
public class Board {
    String name;
    int row, col;
    char[][] board;
    
    public Board(String name, int row, int col){
        this.name = name;
        this.row = row;
        this.col = col;
    }
    
    public void createBoard(){
        board = new char[row][col];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
    }
}
