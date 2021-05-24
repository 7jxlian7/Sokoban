/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Exceptions.BuilderException;

/**
 *
 * @author Julian Forme
 */
public interface BoardBuilder {
    Board build() throws BuilderException;
}
