/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package generationModele;

import java.util.*;
import java.util.ArrayList;

/**
 *<b>definitionSet is a class which represents an discrete or continous set of definition.</b>
 * <p>
 * An definitionSet consists of :
 * <ul>
 * <li>A boolean which indicates if the set of definition is discrete or continous</li>
 * <li>A lower and an upper bound if it is continous. </li>
 * <li>A set of discrete values is il is discrete.</li>
 * </ul>
 * </p>
 * <p>
 * It's possible to add parameters to or remove parameters from an institution .
 * </p>
 * @author mmacret
 */
public class definitionSet{
    
/**
 *Lower bound of the set of definition. It's assigned at the creation.
 *
 * @see definitionSet#definitionSet(double, double)
 * @see definitionSet#getBorneInf()
 */
private double borneInf;
/**
 *Upper bound of the set of definition. It's assigned at the creation.
 *
 * @see definitionSet#definitionSet(double, double)
 * @see definitionSet#getBorneSup()
 */
private double borneSup;
/**
 * Specify if the set of definition is discrete or continous. 
 * This boolean is set at the creation. It depends of the parameters of the constructor.
 *
 * @see definitionSet#definitionSet(java.util.List)
 * @see definitionSet#definitionSet(double)
 * @see definitionSet#definitionSet(double, double)
 * @see definitionSet#isIsDiscrete() 
 */
private boolean isDiscrete;
/**
 *List of discrete values.
 *New values can be added.
 *
 *@see definitionSet#setElement(double)
 *@see definitionSet#setEns(java.util.List)
 *@see definitionSet#getEns()
 *@see definitionSet#definitionSet(java.util.List)
 *@see definitionSet#definitionSet(double)
 */
private List<Double> ens = new ArrayList<Double>();
/**
 * DefinitionSet Constructor for a continous set of definition.
 * You must specify as parameters, the lower and upper bound of the set of definition.
 *
 * @param borneInf
 *      Lower bound.
 * @param borneSup
 *      Upper bound
 * @throws java.lang.Exception
 *      Raise an exception it the lower bound is superior to the upper bound.
 *
 * @see definitionSet#borneInf
 * @see definitionSet#borneSup
 */

    public definitionSet(double borneInf, double borneSup) throws Exception{
        if(borneInf< borneSup){
        this.borneInf = borneInf;
        this.borneSup = borneSup;
        this.isDiscrete = false;
        }
        else throw new Exception("La borneInf doit être supérieure à borneSup");
    }
    /**
     * DefinitionSet Constructor for a discrete set of definition.
     * You must specify as parameter a list of discrete values.
     * @param ens
     *      List of discrete values.
     *
     * @see definitionSet#ens
     */
    
    public definitionSet(List<Double> ens){
        this.ens = ens;
        this.isDiscrete = true;
    }
    /**
     * DefinitionSet Constructor for a discrete set of definition.
     * You must specify as parameter a element to add to the list of discrete values.
     * @param element
     *      Element to add.
     *
     * @see definitionSet#ens
     */
    public definitionSet(double element){
        this.isDiscrete = true;
        this.ens.add(element);
    }

    public definitionSet(definitionSet definitionSet) throws Exception {
        if(definitionSet.isDiscrete){
          this.ens = new ArrayList<Double>(definitionSet.getEns());
        }else{
        this.borneInf = definitionSet.getBorneInf();
        this.borneSup = definitionSet.getBorneSup();
        }
        this.isDiscrete = definitionSet.isIsDiscrete();
    }

    

    public void setBorneInf(double borneInf) throws Exception {
        if(this.isDiscrete) throw new Exception("L'ensemble est discret");
        else {
        this.borneInf = borneInf;
        }
    }

    public void setBorneSup(double borneSup) throws Exception {
        if(this.isDiscrete) throw new Exception("L'ensemble est discret");
        else {
        this.borneSup = borneSup;
        }
    }
    
/**
 * Return the lower bound of the continous set of definition.
 *
 * @return the lower bound
 * @throws java.lang.Exception
 *      Raise an exception if the set is discrete.
 *
 * @see definitionSet#borneInf
 */
public double getBorneInf() throws Exception{
    if(this.isDiscrete) throw new Exception("L'ensemble est discret");
        else {
        return borneInf;
        }
    
}
/**
 * Return the upper bound of the continous set of definition.
 *
 * @return the upper bound
 * @throws java.lang.Exception
 *      Raise an exception if the set is discrete.
 *
 * @see definitionSet#borneSup
 */
public double getBorneSup() throws Exception{
    if(this.isDiscrete) throw new Exception("L'ensemble est discret");
        else {
        return borneSup;
        }
}
/**
 * Add an element to the list of discrete values.
 * @param element
 *      The element to add.
 * @throws java.lang.Exception
 *      Raise an exception if the set of definition is continous.
 */
public void setElement(double element) throws Exception{
    if(isDiscrete){
        this.ens.add(element);
            }
    else throw new Exception("L'ensemble n'est pas discret");
}
/**
 * Set a list of discrete values.
 *
 * @param ens
 *      The list to add.
 */
    public void setEns(List<Double> ens) {
        if(isDiscrete){
        this.ens = ens;
        }
        else new Exception("L'ensemble n'est pas discret");
    }
/**
 * Return the list of discrete values.
 *
 * @return the set of discrete value
 *
 * @throws java.lang.Exception
 *      Raise an exception if the set of definition is continous.
 */

    public List<Double> getEns() throws Exception{
        if(isDiscrete){
        return ens;
        }
        else throw new Exception("L'ensemble n'est pas discret");
    }
/**
 * Indicate if the set of definition is discrete.
 * @return true if it's discrete else false.
 */
    public boolean isIsDiscrete() {
        return isDiscrete;
    }
    
}


