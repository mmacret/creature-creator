/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulationModele;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Grid
 *
 * It consists of a hashMap. It extends ReentrantLock to have control on the grid access.
 * @author mmacret
 */
public class Grid extends ReentrantLock{
    /**
     * The hashMap representing the grid. The keys are the coordinates and the values are the agents.
     */
    private HashMap<Coordinates,Agent> grid;

    public Grid() {
        this.grid = new HashMap<Coordinates,Agent>();
    }
/**
 * Getter
 * @return
 */
    public HashMap<Coordinates, Agent> getGrid() {
        return grid;
    }
/**
 * Setter
 * @param grid
 */
    public void setGrid(HashMap<Coordinates, Agent> grid) {
        this.grid = new HashMap<Coordinates, Agent>(grid);
    }
    

}
