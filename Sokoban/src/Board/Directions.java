/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

/**
 *
 * @author jforme
 */
public enum Directions {
    NORD,
    SUD,
    EST,
    OUEST,
    NORD_EST,
    NORD_OUEST,
    SUD_EST,
    SUD_OUEST;

    /**
     * Liste toutes les directions cardinales.
     *
     * @return un tableau de Directions contenant toutes les directions cardinales.
     */
    static Directions[] cardinales() {
        Directions[] cardinales = {NORD, SUD, EST, OUEST};
        return cardinales;
    }
    
    /**
     * Liste toutes les directions diagonales.
     *
     * @return un tableau de Directions contenant toutes les directions diagonales.
     * @author Julian Forme
     */
    static Directions[] diagonales() {
        Directions[] diagonales = {NORD_EST, NORD_OUEST, SUD_EST, SUD_OUEST};
        return diagonales;
    }

    /**
     * Retourne le mouvement vertical correspondant à la direction appelant la méthode
     *
     * @return le mouvement vertical correspondant à la direction
     */
    public int mvtVertical() {
        int mvt = 0;
        switch (this) {
            case NORD:
            case NORD_EST:
            case NORD_OUEST:
                mvt = -1;
                break;
            case SUD:
            case SUD_EST:
            case SUD_OUEST:
                mvt = 1;
                break;
        }
        return mvt;
    }
    
    /**
     * Retourne le mouvement horizontal correspondant à la direction appelant la méthode
     *
     * @return le mouvement vertical correspondant à la direction
     */
    public int mvtHorizontal() {
        int mvt = 0;
        switch (this) {
            case EST:
            case NORD_EST:
            case SUD_EST:
                mvt = 1;
                break;
            case OUEST:
            case NORD_OUEST:
            case SUD_OUEST:
                mvt = -1;
                break;
        }
        return mvt;
    }
}