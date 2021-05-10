/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Julian Forme
 */
public class FileBoardBuilder implements BoardBuilder {

    String level;
    String nameOfBoard;

    public FileBoardBuilder(String lvl) {
        this.level = lvl.toLowerCase();
    }

    @Override
    public Board build() throws BuilderException {
        Board b = null;
        try ( Scanner scanner = new Scanner(new File("data/"+ level +".txt"))) {
            int i = 0;
            nameOfBoard = scanner.nextLine();
            TextBoardBuilder board = new TextBoardBuilder(nameOfBoard);
            while (scanner.hasNextLine()) {
                board.addRow(scanner.nextLine());
                i++;
            }
            b = board.build();
        } catch (FileNotFoundException ex) {
            System.out.println("Fichier non trouvé : " + ex);
        }

        return b;
    }

}
