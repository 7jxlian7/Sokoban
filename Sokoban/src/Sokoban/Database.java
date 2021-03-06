/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sokoban;

import Exceptions.BuilderException;
import Board.Board;
import Board.TextBoardBuilder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import org.sqlite.SQLiteException;

/**
 *
 * @author Julian
 */
public class Database {

    public static Connection c;

    public Database() {
        String path = "db/librairie.sqlite3";
        String URL = "jdbc:sqlite:" + path;
        loadDrivers();
        try {
            Connection connection = DriverManager.getConnection(URL);
            this.c = connection;
        } catch (SQLException ex) {
            System.err.println("* Base " + URL + " introuvable.");
        }
    }

    public static void loadDrivers() {
        String sqlite_driver = "org.sqlite.JDBC";
        try {
            Class.forName(sqlite_driver);
        } catch (ClassNotFoundException ex) {
            System.err.println("* Driver " + sqlite_driver + " introuvable.");
            System.exit(1);
        }
    }

    /**
     * Méthode permettant d'ajouter un plateau dans la base.
     * 
     * @param board le plateau à ajouter
     * @throws BuilderException
     * @throws SQLException
     */
    public void add(Board board) throws SQLException, BuilderException {

        int id = 0;
        boolean insert = true;

        try {
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select * from boards");

            while (r.next()) {
                if (board.name.equals(r.getString("name"))) {
                    insert = false;
                }
                if (id <= r.getInt("board_id")) {
                    id = r.getInt("board_id") + 1;
                }
            }

            if (insert) {
                PreparedStatement addInBoards = c.prepareStatement("insert into boards values(?,?,?,?)");

                addInBoards.setInt(1, id);
                addInBoards.setString(2, board.name);
                addInBoards.setInt(3, board.row);
                addInBoards.setInt(4, board.col);

                addInBoards.execute();

                PreparedStatement addInRows = c.prepareStatement("insert into rows values(?,?,?)");

                for (int row = 0; row < board.row; row++) {
                    addInRows.setInt(1, id);
                    addInRows.setInt(2, row);
                    addInRows.setString(3, board.rowToText(row));
                    addInRows.execute();
                }

                System.out.println("* Le plateau '" + board.name + "' a été inséré dans la base.");

            } else {
                System.out.println("* Erreur de la base : ce plateau existe déjà.");
            }
        } catch (SQLiteException e) {
            System.out.println("* Erreur de la base : la base n'existe pas.");
        }
    }

    /**
     * Méthode permettant de supprimer un plateau de la base.
     * 
     * @param id l'id du plateau à supprimer.
     * @throws SQLException
     */
    public void remove(int id) throws SQLException {
        try {
            PreparedStatement removeBoard = c.prepareStatement("delete from boards where board_id = ?");
            removeBoard.setInt(1, id);
            removeBoard.executeUpdate();

            PreparedStatement removeRows = c.prepareStatement("delete from rows where board_id = ?");
            removeRows.setInt(1, id);
            removeRows.execute();

            System.out.println("* Le plateau de jeu numéro " + id + " a bien été supprimé.");
        } catch (SQLiteException e) {
            System.out.println("* Erreur de la base : la base n'existe pas.");
        }
    }

    /**
     * Méthode permettant de récupérer un plateau depuis un id.
     * 
     * @param id l'id du plateau à récupérer.
     * @return le board récupéré.
     * @throws BuilderException
     * @throws SQLException
     */
    public Board get(int id) throws SQLException, BuilderException {
        Board b = null;
        try {
            PreparedStatement getBoard = c.prepareStatement("select * from rows where board_id = ?");
            getBoard.setInt(1, id);
            ResultSet r = getBoard.executeQuery();

            if (r.next()) {
                PreparedStatement nameQuery = c.prepareStatement("select name from boards where board_id = ?");
                nameQuery.setInt(1, id);
                ResultSet r2 = nameQuery.executeQuery();
                String name = r2.getString("name");

                TextBoardBuilder board = new TextBoardBuilder(name);

                board.addRow(r.getString("description"));

                while (r.next()) {
                    board.addRow(r.getString("description"));
                }
                b = board.build();
            }

        } catch (SQLiteException e) {
            System.out.println("* Erreur de la base : la base n'existe pas.");
        }
        return b;
    }

    /**
     * Méthode permettant de récupérer tous les plateaux de la base.
     * 
     * @return une liste des plateaux de la base associés à leur id.
     * @throws SQLException
     */
    public HashMap<Integer, String> getAllBoards() throws SQLException {
        HashMap<Integer, String> listBoards = new HashMap<>();
        try {
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("select * from boards");
            while (r.next()) {
                listBoards.put(r.getInt("board_id"), r.getString("name"));
            }

        } catch (SQLiteException e) {
            System.out.println("* Erreur de la base : la base n'existe pas.");
        }
        return listBoards;
    }

    /**
     * Méthode permettant de créer les tables de la base de données.
     * 
     * @throws SQLException
     */
    public void createDatabase() throws SQLException {
        Statement s = c.createStatement();
        s.execute("create table if not exists boards(board_id integer primary key, name text, nb_rows integer, nb_cols integer)");
        s.execute("create table if not exists rows(board_id integer, row_num integer, description text)");
        System.out.println("* La base de données vient d'être créée.");
    }

    /**
     * Méthode permettant de vider les tables de la base de données.
     * 
     * @throws SQLException
     */
    public void clearDatabase() throws SQLException {
        try {
            Statement s = c.createStatement();
            s.execute("delete from boards");
            s.execute("delete from rows");
            System.out.println("* La base de données vient d'être vidée.");
        } catch (SQLiteException e) {
            System.out.println("* Erreur de la base : la base n'existe pas.");
        }

    }

    /**
     * Méthode permettant de supprimer les tables de la base de données.
     * 
     * @throws SQLException
     */
    public void deleteDatabase() throws SQLException {
        Statement s = c.createStatement();
        s.execute("drop table if exists boards");
        s.execute("drop table if exists rows");
        System.out.println("* La base de données vient d'être effacée.");
    }
}
