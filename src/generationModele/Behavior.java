/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package generationModele;

import java.util.*;

/**
 *<b>A behavior defines the behavior of an agent</b>`
 * <p>
 * A behavior consists of :
 * <ul>
 * <li>A norm mother on which the parameters are built</li>
 * <li>A HashMap of Instantiated Parameters</li>
 * </ul>
 * </p>
 * @author mmacret
 */
public class Behavior {
    /**
     * The norm mother on which the parameter are built.
     *
     * @see Norm
     * @see InstantiatedParameter#InstantiatedParameter(modele_creature.Parameter)
     * @see InstantiatedParameter#InstantiatedParameter(modele_creature.InstitutionalParameter)
     */
private Norm normMother;
/**
 * The HashMap of the instantiated parameter which defines the behavior.
 *
 * @see InstantiatedParameter
 */
private HashMap<String,InstantiatedParameter> parameters = new HashMap<String,InstantiatedParameter>();


/**
 * True if this behavior is a violating behavior.
 */
private boolean violating = false;
/**
 * Give the norm Mother
 * @return The norm Mother
 *
 * @see Norm
 * @see Behavior#normMother
 */
    public Norm getNormMother() {
        return normMother;
    }

    public boolean isViolating() {
        return violating;
    }

    public void setViolating(boolean violating) {
        this.violating = violating;
    }
    
/**
 * Behavior Constructor.
 * You must specify the norm mother. All the parameters which compose the norm
 * and the other institutional parameters which are not specified by the norm are
 * added to the behavior.
 * Their values are not set. They will be set by the generationEngine.
 *
 * @param normMother
 *      The norm mother
 *
 * @see generationEngine
 * @see Norm
 * @see Behavior#normMother
 * @see InstantiatedParameter
 */
    public Behavior(Norm normMother) {
        this.normMother = normMother;
        
        //Déclaration des paramètres de la norme
        for(Parameter keys : this.getNormMother().getEnvironnementParameters().values()){
            this.getParameters().put(keys.getName(), new InstantiatedParameter(keys));
            //this.getParameters().get(keys.getName()).setBelongNorm(true);
        }
        for(Parameter keys : this.getNormMother().getInstitutionalParameters().values()){
            this.getParameters().put(keys.getName(), new InstantiatedParameter(keys));
            //this.getParameters().get(keys.getName()).setBelongNorm(true);
        }
        
        //Ajout des paramètres non spécifier par la norme
        for(InstitutionalParameter keys : this.getNormMother().getMotherInstitution().getEnvironnementParamaters().values()){
            if(!this.getParameters().containsKey(keys.getName())){
                this.getParameters().put(keys.getName(),new InstantiatedParameter(keys));
            }
            
        }
        for(InstitutionalParameter keys : this.getNormMother().getMotherInstitution().getInstitutionalParameters().values()){
            if(!this.getParameters().containsKey(keys.getName())){
                this.getParameters().put(keys.getName(),new InstantiatedParameter(keys));
            }
            
        }
          
    }
/**
 * Give the HashMap of parameters
 * @return
 *      The parameters of the behavior
 *
 * @see Behavior#parameters
 * @see InstantiatedParameter
 */
    public HashMap<String, InstantiatedParameter> getParameters() {
        return parameters;
    }
/**
 * Instantiate a parameter.
 * @param key Name of the parameter
 * @param value Value of the parameter
 * @throws java.lang.Exception if the parameter does not exist
 */
    public void instantiateParameter(String key,double value) throws Exception{
        if(!this.parameters.containsKey(key)) throw new Exception("Parameter does not exist");

        parameters.get(key).setValue(value);
    }

   
    


}
