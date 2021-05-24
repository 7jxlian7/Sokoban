/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Exceptions.BuilderException;
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

    /**
     * Méthode permettant de construire un plateau.
     * 
     * @return le plateau construit.
     * @throws BuilderException
     */
    @Override
    public Board build() throws BuilderException {
        Board b = null;
        if(!level.contains(".txt")){
            level = level.concat(".txt");
        }
        try ( Scanner scanner = new Scanner(new File("data/" + level))) {
            try {
                nameOfBoard = scanner.nextLine();
                TextBoardBuilder board = new TextBoardBuilder(nameOfBoard);
                while (scanner.hasNextLine()) {
                    board.addRow(scanner.nextLine());
                }

                b = board.build();
            } catch (StringIndexOutOfBoundsException e) {
                throw new BuilderException("* Impossible de créer le plateau");
            }
        } catch (FileNotFoundException ex) {
            throw new BuilderException("* Fichier non trouvé : " + level + ".txt");
        }

        return b;
    }

}
