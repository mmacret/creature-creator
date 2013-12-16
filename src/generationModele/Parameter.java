/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generationModele;

/**
 *<b>A parameter is a parameter defining an norm</b>`
 * <p>
 * A parameter consists of :
 * <ul>
 * <li>A institutional parameter father on which the parameter is based</li>
 * <li>A name which is assigned at its creation and which is the same than
 * its institutional parameter father.</li>
 * <li>A set of definition which can be continous or discrete and which is constrained by the
 * institutional parameter father</li>
 * <li>A default Value</li>
 * </ul>
 * </p>
 * @author mmacret
 */
public class Parameter {

    /**
     * The name of the parameter. It's the same than its institutional parameter father's one.
     * It's assigned at the creation of the objet.
     *
     * @see InstitutionalParameter
     * @see InstitutionalParameter#name
     * @see Parameter#Parameter(modele_creature.definitionSet, double, modele_creature.InstitutionalParameter)
     * @see Parameter#Parameter(double, double, double, modele_creature.InstitutionalParameter)
     */
    private String name;
    /**
     * The set of definition of the parameter. It is constrained by the set of definition
     * of the institutional parameter father.
     *
     * @see definitionSet
     * @see Parameter#Parameter(modele_creature.definitionSet, double, modele_creature.InstitutionalParameter)
     * @see Parameter#Parameter(double, double, double, modele_creature.InstitutionalParameter)
     */
    private definitionSet definitionSet;
    /**
     *Default value of the parameter. She must belong to the set of definition
     *of the parameter.
     *
     *@see definitionSet
     * @see Parameter#definitionSet
     *
     */
    private double defaultValue;
    /**
     * The institutional paramater on which the current parameter is built.
     *
     * @see InstitutionalParameter
     */
    private InstitutionalParameter fatherParamater;

    /**
     * Parameter Constructor.
     * @param definitionSet
     *      The set of definition of the parameter. It must be compatible with
     * the institutional parameter father 's one.
     * @param defaultValue
     *      The default value of the parameter. It must belong to the set of
     * definition.
     * @param fatherParamater
     *      The institutional parameter father on which is built the parameter.
     * @throws java.lang.Exception
     *      Raise an exception if the defaultValue doesn't belong to the set of
     * definition.
     *      Raise an exception if the set of definition is not compatible with
     * the parameter father's one.
     *
     * @see definitionSet
     * @see InstitutionalParameter
     * @see Parameter#defaultValue
     */
    public Parameter(definitionSet definitionSet, double defaultValue, InstitutionalParameter fatherParamater) throws Exception {
        //Verification de la compatibilité entre l'ensemble de définition et celui de son père
        if (definitionSet.isIsDiscrete() == fatherParamater.getDefinitionSet().isIsDiscrete()) {
            throw new Exception("L'ensemble de définition n'est pas compatible avec celui du père");
        }

        if (definitionSet.isIsDiscrete()) {
            if (!definitionSet.getEns().contains(defaultValue)) {
                throw new Exception("L'ensemble ne contient pas la valeur par défaut");
            }
            if (definitionSet.getEns().containsAll(fatherParamater.getDefinitionSet().getEns())) {
                this.definitionSet = definitionSet;
            } else {
                throw new Exception("L'ensemble n'est pas inclu dans l'ensemble père");
            }
        } else {
            if (!(defaultValue > definitionSet.getBorneInf() || defaultValue < definitionSet.getBorneSup())) {
                throw new Exception("La valeur par défaut n'appartient pas à l'ensemble de définition");
            }
            if (definitionSet.getBorneInf() >= fatherParamater.getDefinitionSet().getBorneInf() &&
                    definitionSet.getBorneSup() <= fatherParamater.getDefinitionSet().getBorneSup()) {
                this.definitionSet = definitionSet;
            } else {
                throw new Exception("Les bornes du paramètres sont en dehors des bornes du paramètre père");
            }
        }
        this.defaultValue = defaultValue;
        this.fatherParamater = fatherParamater;
        this.name = fatherParamater.getName();
    }

    /**
     * Parameter Constructor.
     * @param borneInf
     * The lower bound of the set of definition of the parameter. It must be compatible with
     * the institutional parameter father 's one.
     *     @param borneSup
     * The upper bound of the set of definition of the parameter. It must be compatible with
     * the institutional parameter father 's one.
     * @param defaultValue
     *      The default value of the parameter. It must belong to the set of
     * definition.
     * @param fatherParamater
     *      The institutional parameter father on which is built the parameter.
     * @throws java.lang.Exception
     *      Raise an exception if the defaultValue doesn't belong to the set of
     * definition.
     *      Raise an exception if the set of definition is not compatible with
     * the parameter father's one.
     *      Raise an exception if the lower bound is superior to the upper bound.
     *
     * @see definitionSet#definitionSet(double, double)
     * @see InstitutionalParameter
     * @see definitionSet
     */
    public Parameter(double borneInf, double borneSup, double defaultValue, InstitutionalParameter fatherParamater) throws Exception {
        //Verification de la compatibilité entre l'ensemble de définition et celui de son père
        if (fatherParamater.getDefinitionSet().isIsDiscrete()) {
            throw new Exception("L'ensemble de définition n'est pas compatible avec celui du père");
        }

        if (!(defaultValue > borneInf || defaultValue < borneSup)) {
            throw new Exception("La valeur par défaut n'appartient pas à l'ensemble de définition");
        }
        if (borneInf >= fatherParamater.getDefinitionSet().getBorneInf() &&
                borneSup <= fatherParamater.getDefinitionSet().getBorneSup()) {
            this.definitionSet = new definitionSet(borneInf, borneSup);
        } else {
            throw new Exception("Les bornes du paramètres sont en dehors des bornes du paramètre père");
        }
        this.defaultValue = defaultValue;
        this.fatherParamater = fatherParamater;
        this.name = fatherParamater.getName();
    }

    public Parameter(InstitutionalParameter institutionalParameter) throws Exception{
        this.definitionSet = new definitionSet(institutionalParameter.getDefinitionSet());
        this.fatherParamater = institutionalParameter;
        this.name = institutionalParameter.getName();
    }
/**
 * Return the default value of the parameter.
 * @return the default value.
 *
 * @see Parameter#defaultValue
 */
    public double getDefaultValue() {
        return defaultValue;
    }

/**
 * Return the set of definition of the parameter.
 * @return the set of definition.
 *
 * @see Parameter#definitionSet
 * @see definitionSet
 */
    public definitionSet getDefinitionSet() {
        return definitionSet;
    }

/**
 * Return the institutional parameter father on which is built the parameter.
 * @return the institutional parameter father.
 *
 * @see Parameter#fatherParamater
 * @see InstitutionalParameter
 */
    public InstitutionalParameter getFatherParamater() {
        return fatherParamater;
    }

/**
 * Return the name of the parameter
 * @return the name of the parameter.
 *
 * @see Parameter#name
 * @see InstitutionalParameter#name
 */
    public String getName() {
        return name;
    }

}
