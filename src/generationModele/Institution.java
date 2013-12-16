/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package generationModele;

import java.util.*;

/**
 *<b>Institution is a class which represents an institution.</b>
 * <p>
 * An institution consists of :
 * <ul>
 * <li>A name which is assigned at its creation.</li>
 * <li>A list of institutional paramaters.</li>
 * <li>A list of environnemental paramaters.</li>
 * </ul>
 * </p>
 * <p>
 * It's possible to add parameters to or remove parameters from an institution .
 * </p>
 *
 * @author mmacret
 */
public class Institution {
    /**
     * Name of an Institution. This name is assigned at creation of the object.
     *
     * @see Institution#getName()
     * @see Institution#Institution(java.lang.String) 
     */
private String name;
/**
 * HashMap of institutional parameters.
 * The key is the name of the parameter and the value is the parameter.
 * It's possible to add or remove parameters.
 *
 * @see Institution#addparameter(java.lang.String, modele_creature.InstitutionalParameter)
 * @see Institution#getInstitutionalParameters()
 *
 */
private HashMap<String,InstitutionalParameter> institutionalParameters = new HashMap<String,InstitutionalParameter>();
/**
 * HashMap of environnemental parameters.
 * The key is the name of the parameter and the value is the parameter.
 * It's possible to add or remove parameters.
 *
 * @see Institution#addparameter(java.lang.String, modele_creature.InstitutionalParameter)
 * @see Institution#getEnvironnementParamaters()
 *
 */
private HashMap<String,InstitutionalParameter> environnementParamaters = new HashMap<String,InstitutionalParameter>();

/**
 * Tendancy of an institution to generate violating agent. It is between 0 and 1.
 */
private double sigma = 1.0;
/**
 * Institution constructor.
 * @param name
 *          Name of the institution.
 *
 * @see Institution#name
 */
    public Institution(String name) {
        this.name = name;
    }

/**
 * Return the list of the environnemental parameters which compose the institution
 * @return A HashMap of the environnemental parameters
 *
 * @see Institution#environnementParamaters
 */
    public HashMap<String, InstitutionalParameter> getEnvironnementParamaters() {
        return environnementParamaters;
    }
/**
 * Replace the HashMap of the environnemental parameters.
 *
 * @param environnementParamaters
 *          New HashMap of environnemental parameters.
 *
 * @see institution#environnementparameters
 */
    public void setEnvironnementParamaters(HashMap<String, InstitutionalParameter> environnementParamaters) {
        this.environnementParamaters = environnementParamaters;
    }
/**
 * Return the list of the institutional parameters which compose the institution
 * @return A HashMap of the institutional parameters
 *
 * @see Institution#institutionalParameters
 */
    public HashMap<String, InstitutionalParameter> getInstitutionalParameters() {
        return institutionalParameters;
    }
/**
 * Replace the HashMap of the institutional parameters.
 *
 * @param institutionalParamaters
 *          New HashMap of institutional parameters.
 *
 * @see Institution#institutionalParamaters
 */
    public void setInstitutionalParameters(HashMap<String, InstitutionalParameter> institutionalParameters) {
        this.institutionalParameters = institutionalParameters;
    }
/**
 * Return the name of the institution
 * @return
 *      Name of the institution.
 *
 * @see Institution#name
 */
    public String getName() {
        return name;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }
/**
 * Add a parameter to the institutional parameters hashMap or to the environnemental
 * parameters hashMap.
 * @param type
 *      Specify in which hashMap adding the parameter.
 *      Accepted strings : "institutional" or "environnement"
 * @param parameter
 *      The parameter to add.
 * @throws java.lang.Exception
 *      Raise an exception if the type is different from "institutional" or from "environnement".
 */
public  void addparameter(String type,InstitutionalParameter parameter) throws Exception{
    if(type.equals("institutional")) this.institutionalParameters.put(parameter.getName(), parameter);
    else if(type.equals("environnement")) this.environnementParamaters.put(parameter.getName(), parameter);
    else throw new Exception("Type doit etre soit institutional ou environnement");

}

    @Override
public String toString(){
    return name;
}

    public static void main(String[] args) throws Exception {
        Institution institution = new Institution("test");
        institution.addparameter("institutional", new InstitutionalParameter("parameter", -12,12));
        institution.addparameter("institutional", new InstitutionalParameter("parameter2", -12,12));
        for(String keys : institution.getInstitutionalParameters().keySet()){
            System.out.println(keys);
        }
      
        
        
    }

}
