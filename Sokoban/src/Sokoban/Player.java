package Sokoban;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Julian
 */
public class Player {

    public static void main(String[] args) {
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
        b.drawBoard();
    }
}

