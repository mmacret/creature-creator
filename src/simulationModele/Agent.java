/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulationModele;

import generationModele.*;
import java.util.*;

/**
 *<b>Agent</b>
 * <p>
 * A agent consists of :
 * <ul>
 * <li>An id</li>
 * <li>A behavior</li>
 * <li>An environment where to move</li>
 * <li>A position on the grid</li>
 * <li>A generationEngine for the reproduction</li>
 * <li>A generator of random numbers</li>
 * <li>A behavior</li>
 * <li>A boolean indicating if it is active or not</li>
 * <ul>
 * </p>
 * @author mmacret
 */
public class Agent {

    /**
     * Id of the Agent. It's assigned when the agent is put on the grid.
     *
     * @see Grid#addNewAgent(simulationModele.Coordinates, simulationModele.Agent) 
     * @see Agent#Agent(generationModele.Behavior, generationModele.generationEngine, simulationModele.Grid)
     */
    private int number;
    /**
     * Behavior of the Agent
     *
     * @see Agent#Agent(generationModele.Behavior, generationModele.generationEngine, simulationModele.Grid)
     * @see Behavior
     * @see generationEngine#generateBehavior(generationModele.Norm)
     *
     */
    private Behavior behavior;
    /**
     * Position of the Agent
     *
     * @see Coordinates
     */
    private Coordinates position;
    /**
     * Environment where the agent moves.
     *
     * @see Environment
     */
    private Environment grille;
    /**
     * Generator of random number. It is used to simulate the moving of the agent.
     *
     * @see Random
     * @see Agent#Move()
     */
    private Random generator;
    /**
     * The generationEngine which permit to create new agents. It is used for the
     * reproduction.
     *
     * @see generationEngine
     * @see Agent#wantToMove()
     */
    private generationEngine gen;
    /**
     * Boolean which indicates if the agent is active.
     *
     * @see Agent#run()
     */
    private boolean active;
    /**
     * Coordinates of the point the agent wants to go to during the next activation.
     *
     * @see Agent#wantToMove()
     */
    private Coordinates seekPoint;
    /**
     * Variable which controls how fast the "wander direction" changes.
     */
    private int volatility;
    /**
     * Variable which indicates the step of deplacement. It is link to the volatility.
     */
    private double radius;
    /**
     * Variable which quantify the fatigue of the agent. If it is equal to 0, the agent can
     * interact with other agents.
     *
     * @see Agent#wantToMove() 
     */
    private int fatigue = 50;

    /**
     * Constructor for tests.
     */
    public Agent() {
        this.generator = new Random();
        radius = 5;
        volatility = 10;
    }

    /**
     * Copy Constructor
     * @param tempAgent
     */
    public Agent(Agent tempAgent) {
        this.behavior = tempAgent.behavior;
        this.gen = tempAgent.gen;
        this.grille = tempAgent.grille;
        this.generator = new Random();
        radius = 20;
        volatility = 10;
    }

    /**
     * The Main Agent Constructor.
     *
     * @param behavior
     *      The behavior of the agent which is produced by a generationEngine.
     * @param gen
     *      The generationEngine used for reproduction.
     * @param grille
     *      The environment where the agent moves.
     *
     * @see generationEngine
     * @see generationEngine#generateBehavior(generationModele.Norm)
     * @see Grid
     */
    public Agent(Behavior behavior, generationEngine gen, Environment grille) {
        this.behavior = behavior;
        this.grille = grille;
        this.gen = gen;
        this.generator = new Random();
        radius = behavior.getParameters().get("moving").getValue();
        volatility = 10;


    }

    public Agent(generationEngine gen, Environment grille) {
        this.grille = grille;
        this.gen = gen;
        this.generator = new Random();
        radius = 20;
        volatility = 10;


    }

    /**
     * @return The behavior of the agent
     */
    public Behavior getBehavior() {
        return behavior;
    }

    /**
     * Set the behavior of the Agent.
     * @param behavior
     */
    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    /**
     * Set the id of the agent. This method is used when a agent is put on the grid.
     *
     * @see Grid#addNewAgent(simulationModele.Coordinates, simulationModele.Agent)
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    /**
     * Permit to activate or desactivate a agent.
     * @param active
     *
     * @see Agent#run()
     *
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Set the coordinates of the position of the agent. This method is used when an
     * agent is put on the grid or when it is moving.
     * @param position
     *
     * @see Grid#addNewAgent(simulationModele.Coordinates, simulationModele.Agent)
     * @see Agent#Move()
     */
    public void setPosition(Coordinates position) {
        this.position = position;
    }

    /**
     *
     * @return the coordinates of the agent.
     */
    public Coordinates getPosition() {
        return position;
    }

    public int getFatigue() {
        return fatigue;
    }

    public void setFatigue(int fatigue) {
        this.fatigue = fatigue;
    }

    /**
     * Give a first random seekPoint to the agent.
     *
     * @see Environment#addNewAgent(simulationModele.Coordinates, simulationModele.Agent)
     */

    public void init() {
        //A random vector is generated.
        Vecteur vect = new Vecteur(generator.nextInt(), generator.nextInt());

        //Add more random
        if (generator.nextInt() > 0.5) {
            vect.setX(vect.getX() * (-1));

        }
        if (generator.nextInt() > 0.5) {
            vect.setY(vect.getY() * (-1));



        }
        //The vector is normalized and scaled to the specified radius of the agent.
        vect.normalize();
        vect.scale(radius);

        //The seekPoint is found by adding this vector to the position of the agent.
        seekPoint = new Coordinates(position);
        seekPoint = seekPoint.add(vect);

    }

    /**
     * This method calculate the next seekpoint of the agent and return
     * the direction vector.
     * @return Direction vector.
     */
    public Vecteur calculate() {
        //A random vector is generated.
        Vecteur vect = new Vecteur(generator.nextInt(), generator.nextInt());

        //Add more random
        if (generator.nextInt() > 0.5) {
            vect.setX(vect.getX() * (-1));

        }
        if (generator.nextInt() > 0.5) {
            vect.setY(vect.getY() * (-1));

        //Scale the vector to respect the volatility parameter
        }
        if (vect.length() > volatility) {
            vect.normalize();
            vect.scale(volatility);
        }

        //Calculate the new direction vector.
        seekPoint = seekPoint.add(vect);
        Vecteur temp = new Vecteur(seekPoint.getAbscisse() - position.getAbscisse(), seekPoint.getOrdonnee() - position.getOrdonnee());
        temp.normalize();
        temp.scale(radius);

        //Calculate the new seekPoint
        seekPoint = position.add(temp);

        //Verification if the seekPoint is inside the grid else the direction vector is
        //changed to the opposite.
        if (isOutside()) {
            
            temp.opposite();
            seekPoint = position.add(temp);
        }

        return temp;
    }

    /**
     * This method is used to abstract the intention of the agent to move.
     * <ul>
     * <li>If the position it wants to go is free then the agent is put on this position
     * on the grid.</li>
     * <li>If the position it want to go is already occupied then :</li>
     * <ul>
     * <li>If the other agent doesn't come from the same norm, it fights it.</li>
     * <li>If the other agent comes from the same norm, it reproduces.</li>
     * </ul>
     *
     * @throws java.lang.Exception
     *
     */
    public void wantToMove() throws Exception {
        //A new seekpoint is calculated and a direction vector is created.
        Vecteur directionVect = calculate();
        this.grille.lockGrid();

        //Interaction Algorithm

        //If the seekPoint and its neighborhood are free, the agent moves to this point else the seekPoint variable
        //is remplaced by the coordinates of the agent situated there.
        if (this.grille.isFree(seekPoint)) {

            //The agent moves.
            this.grille.moveAgent(seekPoint, this);
            //The new seekpoint is set.
            seekPoint = seekPoint.add(directionVect);
        if(fatigue != 0) fatigue = fatigue - 1;

        } else {
            //Check the fatigue.
            System.out.println("rencontre "+fatigue);

            Agent occupant = this.grille.getGrid().get(seekPoint);
            if ((fatigue == 0) && (occupant.getFatigue()==0)) {

                System.out.println("pas fatigué");

                
                String occupantNorm = occupant.getBehavior().getNormMother().getName();
                //The two agents belong to the same norm.
                if (this.behavior.getNormMother().getName().equals(occupantNorm) ) {
                    System.out.println("reproduction");
                    int nbChildren = (int) this.behavior.getParameters().get("nbChildren").getValue();
                    System.out.println("nbChildren = " + nbChildren);
                    for (int i = 0; i < nbChildren; i++) {
                        Coordinates childCoordinate = this.grille.GetPlaceForChildren(position);
                        Agent child = new Agent(this.gen.generateBehavior(this.behavior.getNormMother()), this.gen, this.grille);

                        this.grille.addNewAgent(childCoordinate, child);
                        grille.fireReproduction(occupant, this, child);
                    }

                    //The agent change his direction Vector to the opposite one. (To avoid chain interaction)
                    directionVect.opposite();
                    seekPoint = seekPoint.add(directionVect);
                    
                //The two agents belong to different norms.
                } else {
                    System.out.println("combat");
                    double mySize = this.behavior.getParameters().get("size").getValue();
                    double itsSize = occupant.getBehavior().getParameters().get("size").getValue();

                    //Comparing their size.
                    if (mySize < itsSize) {
                        this.active = false;
                        
                        this.grille.getGrid().remove(this.getPosition());
                        grille.fireCombat(occupant, this);
                    } else {
                        this.grille.getGrid().get(seekPoint).setActive(false);
                        
                        this.grille.getGrid().remove(seekPoint);
                        grille.fireCombat(this, occupant);
                    }
                }

                //The agent is tired after interacting
                fatigue = 20;
            } //Decrease the fatigue.
            else {
                if(fatigue != 0) fatigue = fatigue - 1;
                //The agent change his direction Vector to the opposite one. (To avoid chain interaction)
                directionVect.opposite();
                seekPoint = seekPoint.add(directionVect);
            }
        }


        this.grille.unlockGrid();
    }

    /**
     *
     * @return true if the agent is outside the grid
     */
    public boolean isOutside() {
        if (seekPoint.getAbscisse() < 0 || seekPoint.getOrdonnee() < 0 || seekPoint.getAbscisse() > this.grille.getLastLargeur() || seekPoint.getOrdonnee() > this.grille.getLastHauteur()) {
            return true;
        } else {
            return false;

        }
    }
/**
 *
 * @return the seekPoint of the agent.
 */
    public Coordinates getSeekPoint() {
        return seekPoint;
    }
/**
 * Set the seekpoint.
 * @param seekPoint
 * @see Agent#wantToMove() 
 */
    public void setSeekPoint(Coordinates seekPoint) {
        this.seekPoint = seekPoint;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof Agent) {
            Agent agent = (Agent) o;
            if (this.getNumber() == agent.getNumber()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public String toString() {
        return "Agent " + number;
    }

    public static void main(String[] args) {
        Agent agent = new Agent();
        agent.setPosition(new Coordinates(100.0, 100.0));
        agent.init();
        System.out.println(agent.getSeekPoint());
        agent.getSeekPoint().cut();
        System.out.println(agent.getSeekPoint());
    }
}

