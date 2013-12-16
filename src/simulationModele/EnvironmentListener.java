/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulationModele;

import java.util.*;

/**
 *
 * @author mmacret
 */
public interface EnvironmentListener {
    void gridChanged(HashMap<Coordinates,Agent> newGrid);
    void reproduction(Agent maman,Agent papa,Agent fils );
    void combat(Agent winner, Agent loser);

}
