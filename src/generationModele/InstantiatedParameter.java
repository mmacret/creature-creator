/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package generationModele;

/**
 *<b>A InstantiatedParameter is a parameter defining an behavior</b>`
 * <p>
 * A instantiated parameter is an institutional parameter or an norm parameter in which
 * a value has been chosen in the set of definition.
 * </p>
 * @author mmacret
 */
public class InstantiatedParameter {
/**
 * The name of the instantiated parameter. It's the same than the norm/institutional
 * parameter father.
 *
 * @see Parameter
 * @see InstitutionalParameter
 * @see Parameter#name
 * @see InstitutionalParameter#name
 */
private String name;
/**
 * The institutional parameter father.
 * It's the parameter on which the instantiated parameter is built.
 * It's only assigned at the creation of the object.
 *
 * @see InstitutionalParameter
 * @see InstantiatedParameter#InstantiatedParameter(modele_creature.InstitutionalParameter)
 */
private InstitutionalParameter fatherInstitutionalParameter;
/**
 * The norm parameter father.
 * It's the parameter on which the instantiated parameter is built.
 * It's only assigned at the creation of the object.
 *
 * @see Parameter
 * @see InstantiatedParameter#InstantiatedParameter(modele_creature.Parameter)
 *
 */
private Parameter fatherNormParameter;
/**
 * The value of the parameter.
 * It is assigned by the generationEngine.
 *
 * @see generationEngine
 * @see generationEngine#generateBehavior(modele_creature.Norm)
 *
 */
private double value;
/**
 *Specify if the instantiated parameter branches off a norm parameter.
 *
 * @see InstantiatedParameter#InstantiatedParameter(modele_creature.InstitutionalParameter)
 * @see InstantiatedParameter#InstantiatedParameter(modele_creature.Parameter)
 */
private boolean belongNorm = false;


/**
 * InstantiatedParameter Constructor.
 * It creates an object which depends on an institutional parameter.
 *
 * @param fatherInstitutionalParameter
 *      The institutional parameter on which the parameter is based.
 *
 * @see InstitutionalParameter
 */

    public InstantiatedParameter(InstitutionalParameter fatherInstitutionalParameter) {
        this.fatherInstitutionalParameter = fatherInstitutionalParameter;
        this.name = fatherInstitutionalParameter.getName();
        this.belongNorm = false;
        
    }
    /**
 * InstantiatedParameter Constructor.
 * It creates an object which depends on an norm parameter.
 *
 * @param fatherNormParameter
 *      The norm parameter on which the parameter is based.
     *
     * @see Parameter
 */

    public InstantiatedParameter(Parameter fatherNormParameter) {
        this.belongNorm = true;
        this.fatherNormParameter = fatherNormParameter;
        this.name = fatherNormParameter.getName();
    }
/**
 * Indicate if the parameter belongs to a norm.
 * @return true if it belongs to a norm and false if not.
 */
    public boolean isBelongNorm() {
        return belongNorm;
    }
/**
 * Return the institutional parameter father on which the parameter is built.
 * @return
 *      The institutional parameter father.
 * @throws java.lang.Exception
 *      Raise an exception if the parameter depends on a norm parameter.
 *
 * @see InstitutionalParameter
 */
    public InstitutionalParameter getFatherInstitutionalParameter() throws Exception{
        if(belongNorm) throw new Exception("Le parametre ne depend pas d'un parametre institutionel");
        else return fatherInstitutionalParameter;
    }

/**
 * Return the nomr parameter father on which the parameter is built.
 * @return
 *      The norm parameter father.
 * @throws java.lang.Exception
 *      Raise an exception if the parameter depends on an institutional parameter.
 *
 * @see Parameter
 */

    public Parameter getFatherNormParameter() throws Exception {
        if(belongNorm) return fatherNormParameter;
        else throw new Exception("Le parametre ne depend pas d'un parametre defini dans une norme");
    }
/**
 * Give the name of the parameter.
 * @return
 *      The name of the parameter.
 *
 * @see InstantiatedParameter#name
 */

    public String getName() {
        return name;
    }


/**
 * Return the value of the parameter.
 * @return
 *      The value of the parameter.
 */
    public double getValue() {
        return value;
    }
/**
 * Set the value of the parameter. The belonging to the set of definition is
 * checked by the generationEngine.
 * @param value
 *      The value to set.
 *
 * @see generationEngine#generateBehavior(modele_creature.Norm)
 * @see InstantiatedParameter#value
 */
    public void setValue(double value){
        this.value = value;
    }
}
