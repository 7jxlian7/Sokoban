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
        Board b = new Board("Mon super plateau", 10, 6);
        Game g = new Game(new Player(), b);
        g.run();
    }
}

