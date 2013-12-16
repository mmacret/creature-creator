/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package generationModele;

import java.util.*;



/**
 *<b>A Institutional Parameter is a parameter defining an institution</b>`
 * <p>
 * An institutional parameter consists of :
 * <ul>
 * <li>A name which is assigned at its creation.</li>
 * <li>A set of definition which can be continous or discrete.</li>
 * </ul>
 * </p>
 * @author mmacret
 */
public class InstitutionalParameter {
    /**
     * Name of an parameter. This name is assigned at creation of the object.
     *
     * @see InstitutionalParameter#getName()
     * @see InstitutionalParameter#InstitutionalParameter(java.lang.String, java.util.List)
     * @see InstitutionalParameter#InstitutionalParameter(java.lang.String, modele_creature.definitionSet)
     * @see InstitutionalParameter#InstitutionalParameter(java.lang.String, double, double)
     */
private String name;
/**
 * Set of definition of the institutional parameter. It can be continous or discrete.
 * It is assigned at the construction of the objet.
 *
 * @see InstitutionalParameter#getDefinitionSet()
 * @see InstitutionalParameter#InstitutionalParameter(java.lang.String, java.util.List)
 * @see InstitutionalParameter#InstitutionalParameter(java.lang.String, modele_creature.definitionSet)
 * @see InstitutionalParameter#InstitutionalParameter(java.lang.String, double, double)
 */
private definitionSet definitionSet;

/**
 *InstitutionalParameter Constructor.
 * You must specify as parameters its name and a set of definition.
 * @param name
 *      The name of the institutional parameter
 * @param definitionSet
 *      The set of definition of the institutional parameter
 *
 * @see InstitutionalParameter#name
 * @see InstitutionalParameter#definitionSet
 * @see definitionSet
 */
public InstitutionalParameter(String name, definitionSet definitionSet) {
        this.name = name;
        this.definitionSet = definitionSet;
    }
/**
 *InstitutionalParameter Constructor.
 * You must specify as parameters its name and a set of definition giving
 * its lower and upper bound.
 *
 * @param name
 *      The name of the parameter.
 * @param borneInf
 *      The lower bound of its set of definition.
 * @param borneSup
 *      The upper bound of its set of definition.
 * @throws java.lang.Exception
 *      Raise an exception if the lower bound is superior to the upper bound.
 *
 * @see definitionSet
 * @see definitionSet#borneInf
 * @see definitionSet#borneSup
 * @see InstitutionalParameter#name
 */
   
    public InstitutionalParameter(String name, double borneInf, double borneSup) throws Exception {
        this.name = name;
        this.definitionSet = new definitionSet(borneInf,borneSup);
    }
/**
 * InstitutionalParameter Constructor.
 * You must specify as parameters its name and a set of definition giving
 * a list of discrete values.
 *
 * @param name
 *      The name of the parameter.
 * @param ens
 *      The list of discrete values
 *
 * @see definitionSet#ens
 * @see InstitutionalParameter#name
 */
    public InstitutionalParameter(String name, List<Double> ens){
        this.name = name;
        this.definitionSet = new definitionSet(ens);
    }
/**
 * Return the set of definition of the parameter.
 * @return the set of definition.
 *
 * @see definitionSet
 * @see InstitutionalParameter#definitionSet
 */
    public definitionSet getDefinitionSet() {
        return definitionSet;
    }
/**
 * Return the name of this parameter.
 * @return the name of the parameter.
 *
 * @see InstitutionalParameter#name
 */
    public String getName() {
        return name;
    }



}
