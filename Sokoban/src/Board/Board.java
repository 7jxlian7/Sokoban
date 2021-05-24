/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Exceptions.BuilderException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Julian
 */
public class Board {

    public String name;
    public int row, col;
    public Set<Position> boxes = new HashSet<>();
    public Set<Position> walls = new HashSet<>();
    public Set<Position> targets = new HashSet<>();
    public Position character;
    public char[][] board;

    public Board(String name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
    }

    /**
     * Méthode permettant de créer un plateau depuis les informations contenues
     * dans les HashSet.
     */
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
    
    /**
     * Méthode permettant de dessiner des nombres au-dessus du plateau.
     */
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

    /**
     * Méthode permettant de l'intérieur du plateau.
     */
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

    /**
     * Méthode permettant de dessiner le plateau.
     * 
     * @throws BuilderException
     */
    public void drawBoard() throws BuilderException {
        System.out.println();
        System.out.println("* " + name + " *");
        System.out.println();
        drawNumbers();
        drawContent();
        System.out.println(); 
    }

    /**
     * Méthode permettant de définir la position du joueur.
     * 
     * @param row ligne où déplacer le joueur.
     * @param col colonne où déplacer le joueur.
     */
    public void setPosition(int row, int col) {
        character = new Position(row, col);
    }

    /**
     * Méthode permettant d'ajouter une caisse.
     * 
     * @param row ligne où ajouter la caisse.
     * @param col colonne où ajouter la caisse.
     */
    public void addBox(int row, int col) {
        boxes.add(new Position(row, col));
    }

    /**
     * Méthode permettant d'ajouter une cible.
     * 
     * @param row ligne où ajouter la cible.
     * @param col colonne où ajouter la cible.
     */
    public void addTarget(int row, int col) {
        targets.add(new Position(row, col));
    }

    /**
     * Méthode permettant d'ajouter un mur horizontal.
     * 
     * @param row ligne où ajouter la mur.
     * @param col colonne où ajouter la mur.
     * @param length longueur du mur.
     */
    public void addHorizontalWall(int row, int col, int length) {
        for (int i = 0; i < length; i++) {
            walls.add(new Position(row, col + i));
        }
    }

    /**
     * Méthode permettant d'ajouter un mur vertical.
     * 
     * @param row ligne où ajouter la mur.
     * @param col colonne où ajouter la mur.
     * @param length longueur du mur.
     */
    public void addVerticalWall(int row, int col, int length) {
        for (int i = 0; i < length; i++) {
            walls.add(new Position(row + i, col));
        }
    }

    /**
     * Méthode permettant de vérifier si une position est dans le plateau.
     * 
     * @param p position à tester.
     * @return true ssi la position est dans le plateau.
     */
    public boolean isInBoard(Position p) {
        return p.row >= 0 && p.row < row && p.col >= 0 && p.col < col;
    }

    /**
     * Méthode permettant de vérifier si une position est libre (pas occupée
     * par une caisse ni par un mur).
     * 
     * @param p position à tester.
     * @return true ssi la position est libre.
     */
    public boolean isFree(Position p){
        return !isCollisionWithWall(p) && !isCollisionWithBox(p);
    }
    
    /**
     * Méthode permettant de vérifier si une position est en collision avec un mur
     * 
     * @param p position à tester.
     * @return true ssi la position est en collision.
     */
    public boolean isCollisionWithWall(Position p) {
        boolean isWall = false;
        for (Position wall : walls) {
            if (wall.row == p.row && wall.col == p.col) {
                isWall = true;
            }
        }
        return isWall;
    }

    /**
     * Méthode permettant de vérifier si une position est en collision avec une caisse.
     * 
     * @param p position à tester.
     * @return true ssi la position est en collision.
     */
    public boolean isCollisionWithBox(Position p) {
        boolean isBox = false;
        for(Position box : boxes){
            if (box.row == p.row && box.col == p.col) {
                isBox = true;
            }
        }
        return isBox;
    }
    
    /**
     * Méthode permettant de convertir une ligne du plateau en texte.
     * 
     * @param rowNumber ligne à convertir en texte.
     * @return la chaine de caractère correspondant à la ligne choisie.
     */
    public String rowToText(int rowNumber){
        String rowText = "";
        for(int c = 0; c < col; c++){
            String oneChar = Character.toString(board[rowNumber][c]);
            rowText = rowText.concat(oneChar);
        }
        return rowText;
    }
}
