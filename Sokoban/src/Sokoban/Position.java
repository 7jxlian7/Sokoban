/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

/**
 *
 * @author jforme
 */
public class Position {

    int row, col;

    public Position(int x, int y) {
        this.row = x;
        this.col = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return this.equals(((Position) obj).row) && this.equals(((Position) obj).col);
    }
}
