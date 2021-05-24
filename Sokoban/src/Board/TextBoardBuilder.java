/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Board.BoardBuilder;
import Exceptions.BuilderException;

/**
 *
 * @author Julian Forme
 */
public class TextBoardBuilder implements BoardBuilder {

    public String name;
    public String textBoard = "";
    public int colNumbers;
    public int rowNumbers = 0;

    public TextBoardBuilder(String name) {
        this.name = name;
    }

    /**
     * Méthode permettant de construire un plateau.
     * 
     * @return le plateau construit.
     * @throws BuilderException
     */
    @Override
    public Board build() throws BuilderException {
        Board board = new Board(name, rowNumbers, colNumbers);
        Position p;
        int i = 0;
        // Savoir si le plateau est conforme
        if (textBoard.length() == (colNumbers * rowNumbers)) {
            for (int row = 0; row < rowNumbers; row++) {
                for (int col = 0; col < colNumbers; col++) {
                    p = new Position(row, col);
                    switch (textBoard.charAt(i)) {
                        case '#':
                            board.walls.add(p);
                            break;
                        case 'C':
                            board.boxes.add(p);
                            break;
                        case 'x':
                            board.targets.add(p);
                            break;
                        case 'P':
                            board.character = p;
                            break;
                        default:
                            break;
                    }
                    i++;
                }
            }
        } else {
            throw new BuilderException("* Impossible de créer le plateau, plateau non conforme.");
        }
        board.buildTextBoard();
        return board;
    }

    /**
     * Méthode permettant d'ajouter une ligne au plateau textuel.
     * 
     * @param row ligne à ajouter.
     */
    public void addRow(String row) {
        textBoard = textBoard.concat(row);
        colNumbers = row.length();
        rowNumbers++;
    }
}
