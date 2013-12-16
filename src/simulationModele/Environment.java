/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulationModele;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *<b>A environment</b>
 *<p>
 * A environment consists of :
 * <ul>
 * <li>A Grid whose the key is a coordinate and the value a agent</li>
 * <li>A population</li>
 * <li>A width</li>
 * <li>A height</li>
 * <li>A step for the grid</li>
 * <li>The x-coordinate of the last point on the width</li>
 * <li>The y-coordinate of the last point on the height</li>
 * <li>A generator of random number to determine a random free point</li>
 * </ul>
 *</p>
 * @author mmacret
 */
public class Environment extends Thread {

    /**
     * The grid whose the key is a coordinate and the value is an agent.
     *
     * @see Agent
     * @see Coordinates
     */
    private Grid grid = new Grid();
    /**
     * The population on the grid.
     *
     * @see Environment#addNewAgent(simulationModele.Coordinates, simulationModele.Agent)
     */
    private int pop = 0;
    /**
     * Listener for the IHM.
     */
    private EnvironmentListener environmentListener;
    /**
     * The width of the grid
     */
    private int largeur;
    /**
     * The height of the grid
     */
    private int hauteur;
    /**
     * The maximum radius for an agent on the environment.
     */
    private double radiusMax;
    /**
     * The x-coordinate of the last available point on the width
     */
    private int lastLargeur;
    /**
     * The y-coordinate of the last available point on the height
     */
    private int lastHauteur;
    /**
     * The generator of random number used to determine a random free point
     *
     * @see Random
     */
    private Random generator = new Random();
    /**
     * Indicate if the thread is active.
     *
     * @see Environment#run() 
     */
    private boolean active = true;
    /**
     * Simulation speed in milliseconds.
     */
    private int speed = 50;

    /**
     * Grid Constructor.
     * You must specify the width and the height of the grid and also the step
     * for the grid
     * @param radiusMax
     *      Step for the grid
     * @param largeur
     *      Width of the grid
     * @param hauteur
     *      Height of the grid
     */
    public Environment(int radiusMax, int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.radiusMax = radiusMax;
        for (int i = radiusMax; i < largeur; i = i + radiusMax) {
            for (int j = radiusMax; j < hauteur; j = j + radiusMax) {
                this.lastHauteur = j;
                this.lastLargeur = i;

            }
        }
        

    }
/**
 * Give the speed of the simulation
 * @return Simulation Speed
 *
 * @see Environment#run()
 */
    public int getSpeed() {
        return speed;
    }
/**
 * Set the simulation speed
 * @param speed
 *
 * @see Environment#run()
 */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Give the height of the grid
     * @return the height of the grid
     *
     * @see Environment#hauteur
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Give the width of the grid
     * @return the width of the grid
     *
     * @see Environment#largeur
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Give the maximum radius for an agent.
     * @return the maximum radius for an agent
     *
     * @see Environment#radiusMax
     * @see Environment#getFreePlace()
     */
    public double getRadiusMax() {
        return radiusMax;
    }

    /**
     *
     * @return The y-coordinate of the last point on the height
     *
     * @see Environment#lastHauteur
     */
    public int getLastHauteur() {
        return lastHauteur;
    }

    /**
     *
     * @return The x-coordinate of the last point on the width
     *
     * @see Environment#lastLargeur
     */
    public int getLastLargeur() {
        return lastLargeur;
    }

    /**
     *
     * @return The grid
     *
     * @see Grid
     */
    public HashMap<Coordinates, Agent> getGrid() {
        return grid.getGrid();
    }

    /**
     * Set the grid
     * @param newGrid an HashMap
     */
    public void setGrid(HashMap<Coordinates, Agent> newGrid) {
        grid.setGrid(newGrid);
    }

    /**
     * Lock the ressource Grid
     */
    public void lockGrid() {
        grid.lock();
    }

    /**
     * Unlock the ressource Grid
     */
    public void unlockGrid() {
        grid.unlock();
    }

    /**
     *
     * @return the population on the grid
     *
     * @see Grid#pop
     */
    public int getPop() {
        return this.pop;
    }

    /**
     *
     * @return The coordinates of a free place on the grid.
     *
     * @see Coordinates
     */
    public Coordinates getFreePlace() {
        Coordinates temp2 = new Coordinates(this.generator.nextInt(this.lastLargeur), this.generator.nextInt(this.lastHauteur));
        while (!this.isFree(temp2)) {
            temp2 = new Coordinates(this.generator.nextInt(this.lastLargeur), this.generator.nextInt(this.lastHauteur));
        }
        
        return temp2;
    }

    /**
     * Move an existing agent on the specified coordinates.
     * @param coord
     *      The coordinates where to set the agent.
     * @param agent
     *      The agent to set.
     * @throws java.lang.Exception
     *      Raise an exception if the coordinate doesn't exist on the grid.
     *      Raise an exception if the coordinate is already occupied.
     *
     * @see Coordinates
     * @see Agent
     *
     */
    public void moveAgent(Coordinates coord, Agent agent) throws Exception {
        if (!(this.isFree(coord))) {
            throw new Exception("La coordonnee est déja occupée");
        }
        this.getGrid().remove(agent.getPosition());
        agent.setPosition(coord);
        this.getGrid().put(coord, agent);
        this.fireGridChanged(this.getGrid());


    }

    /**
     * Add an new agent on the specified coordinates.`A id is assigned to the agent.`
     * @param coord
     *      The coordinates where to set the agent.
     * @param agent
     *      The agent to set.
     * @throws java.lang.Exception
     *      Raise an exception if the coordinate doesn't exist on the grid.
     *      Raise an exception if the coordinate is already occupied.
     *
     * @see Coordinates
     * @see Agent
     */
    public void addNewAgent(Coordinates coord, Agent agent) throws Exception {
        if (!(this.isFree(coord))) {
            throw new Exception("La coordonnee est déja occupée");
        }
        
        agent.setNumber(this.pop);
        agent.setPosition(coord);
        agent.init();
        this.getGrid().put(coord, agent);
        this.pop = this.pop + 1;
        this.fireGridChanged(this.getGrid());

    }

    /**
     * Verify it the coordinate and its neighbourhood are free.
     * If the coordinate is occupied else it is replaced by the occupied coordinate.(See Agent Interaction Algorithm)
     * @param coord
     *      The coordinate to verify.
     * @return
     *      True if it is free and false if not.
     *
     * @see Coordinates
     * @see Agent#wantToMove()
     */
    public boolean isFree(Coordinates coord) {
        boolean temp = true;
        for (Coordinates keys : this.getGrid().keySet()) {
            //Verification of the presence of an other agent
            //in the neighborhood (a disk) of this coordinate

            if (((keys.getAbscisse() - coord.getAbscisse()) * (keys.getAbscisse() - coord.getAbscisse()) + (keys.getOrdonnee() - coord.getOrdonnee()) * (keys.getOrdonnee() - coord.getOrdonnee()) <= radiusMax * radiusMax)) {
                temp = false;
                //The coordinates are replaced by the agent situated in its neighborhood
                coord.setCoordinates(keys.getAbscisse(), keys.getOrdonnee());
            }

        }
        return temp;
    }
    
    /**
     * This method is called when the grid is modified.
     * @param newGrid
     */

    protected void fireGridChanged(HashMap<Coordinates, Agent> newGrid) {
        environmentListener.gridChanged(newGrid);
    }
/**
 * This method is called when there is a reproduction.
 * @param maman : the first agent.
 * @param papa : the second agent.
 * @param fils : the child
 */
    protected void fireReproduction(Agent maman,Agent papa,Agent fils){
        environmentListener.reproduction(maman, papa, fils);
    }
/**
 * This method is called when there is a fight.
 * @param winner : the winner of the fight who is still alive.
 * @param loser : the loser of the fight who is dead.
 */
    protected void fireCombat(Agent winner,Agent loser){
        environmentListener.combat(winner,loser);
    }
    /**
     * Set the IHM Listener.
     * @param environmentListener
     */
    public void setEnvironmentListener(EnvironmentListener environmentListener) {
        this.environmentListener = environmentListener;
    }

    /**
     * Set the boolean active.
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Return the boolean which indicates if the thread is activated.
     * @return a boolan on the state of the thread.
     */
    public boolean isActive() {
        return active;
    }

    //Methods to have the coordinates of the neighbours.
    public Coordinates GetTheN(Coordinates coord) {
        Coordinates newCoord = new Coordinates(coord.getAbscisse(), coord.getOrdonnee() - radiusMax);
        System.out.println("N");
        return newCoord;
    }

    public Coordinates GetTheS(Coordinates coord) {
        Coordinates newCoord = new Coordinates(coord.getAbscisse(), coord.getOrdonnee() + radiusMax);
        System.out.println("S");
        return newCoord;
    }

    public Coordinates GetTheE(Coordinates coord) {
        Coordinates newCoord = new Coordinates(coord.getAbscisse() + radiusMax, coord.getOrdonnee());
        System.out.println("E");
        return newCoord;
    }

    public Coordinates GetTheW(Coordinates coord) {
        Coordinates newCoord = new Coordinates(coord.getAbscisse() - radiusMax, coord.getOrdonnee());
        System.out.println("W");
        return newCoord;
    }

    public Coordinates GetTheNW(Coordinates coord) {
        Coordinates newCoord = new Coordinates(coord.getAbscisse() - radiusMax, coord.getOrdonnee() - radiusMax);
        System.out.println("NW");
        return newCoord;
    }

    public Coordinates GetTheNE(Coordinates coord) {
        Coordinates newCoord = new Coordinates(coord.getAbscisse() + radiusMax, coord.getOrdonnee() - radiusMax);
        System.out.println("NE");
        return newCoord;
    }

    public Coordinates GetTheSE(Coordinates coord) {
        Coordinates newCoord = new Coordinates(coord.getAbscisse() + radiusMax, coord.getOrdonnee() + radiusMax);
        System.out.println("SE");
        return newCoord;
    }

    public Coordinates GetTheSW(Coordinates coord) {
        Coordinates newCoord = new Coordinates(coord.getAbscisse() - radiusMax, coord.getOrdonnee() + radiusMax);
        System.out.println("SW");
        return newCoord;
    }
/**
 * This method gives a random free place around the coordinate.
 * @param father Coordinates around to find a free place.
 * @return a free coordinate.
 *
 * @see Environment#isFree(simulationModele.Coordinates)
 */
 public Coordinates GetPlaceForChildren(Coordinates father){
    Coordinates child = new Coordinates(0, 0);
    int globalTirage = this.generator.nextInt(8);
    switch(globalTirage){
            case 0 : child = this.GetTheN(father);break;
            case 1 : child = this.GetTheS(father);break;
            case 2 : child = this.GetTheE(father);break;
            case 3 : child = this.GetTheW(father);break;
            case 4 : child = this.GetTheNE(father);break;
            case 5 : child = this.GetTheNW(father);break;
            case 6 : child = this.GetTheSW(father);break;
            case 7 : child = this.GetTheSE(father);break;
            default: System.out.println("Probleme avec les enfants");break;
            }
    if(!this.isFree(child)) child = this.GetPlaceForChildren(child);
    return child;
    }
/**
 * Clear the grid.
 */
    public void Clear(){
        getGrid().clear();
        this.fireGridChanged(this.getGrid());
    }

    public static void main(String[] args) throws Exception {
        Environment grille = new Environment(3, 10, 10);
        /*for(Coordonnees keys : grille.getGrid().keySet()){

        System.out.println(keys);
        System.out.println(grille.isFree(keys));

        }

         */
        System.out.println(grille.getLastLargeur());
        Agent agent = new Agent();
        Agent agent2 = new Agent();
        grille.addNewAgent(new Coordinates(4.0, 4.0), agent);
        grille.addNewAgent(new Coordinates(7.0,7), agent2);
        System.out.println(grille.GetPlaceForChildren(new Coordinates(4.0, 4.0)));
        //Coordinates temp = new Coordinates(5, 5);
        //System.out.println(grille.isFree(temp));

        //System.out.print("Une coordonnee libre est : ");
        //System.out.print(temp);

    //System.out.println(grille.getFreePlace());


    }
/**
 *
 */
    @Override
    public void run() {

        while (true) {
            //Pause loop
            synchronized (this) {
                while (!active) {
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            //The hashMap is copied to avoid concurrence problems.
            this.lockGrid();
            HashMap<Coordinates, Agent> tempGrid;
            tempGrid = new HashMap<Coordinates, Agent>(this.getGrid());
            this.unlockGrid();
            
            for (Coordinates keys : tempGrid.keySet()) {
                try {
                    this.getGrid().get(keys).wantToMove();

                } catch (Exception ex) {
                    Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                Thread.sleep(this.speed);
            } catch (InterruptedException ex) {
                Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
    }
}


