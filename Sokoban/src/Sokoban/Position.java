/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

import java.util.Objects;

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
    public int hashCode(){
        return Objects.hash(row, col);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.row != other.row) {
            return false;
        }
        return this.col == other.col;
    }
}
