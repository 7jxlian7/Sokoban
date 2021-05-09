/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

/**
 *
 * @author Julian Forme
 */
public class TextBoardBuilder implements BoardBuilder {

    public String name;
    public String textBoard = "";
    public Board board;
    public int colNumbers;
    public int rowNumbers = 0;

    public TextBoardBuilder(String name) {
        this.name = name;
    }

    @Override
    public Board build() throws BuilderException {
        Board board = new Board(name, rowNumbers, colNumbers);
        Position p;
        int i = 0;
        for (int row = 0; row < rowNumbers; row++) {
            for (int col = 0; col <= colNumbers; col++) {
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
                        System.out.println("vide / invalide");
                        break;
                }
                i++;
            }
        }

        return board;
    }

    void addRow(String row) {
        textBoard = textBoard.concat(row + "\n");
        colNumbers = row.length();
        rowNumbers++;

    }

    void displayTextBoard() {
        System.out.println(textBoard);
        System.out.println(colNumbers);
        System.out.println(rowNumbers);
    }
}
