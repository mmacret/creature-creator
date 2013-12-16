/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package generationModele;

import java.util.*;

/**
 *<b>A generationEngine permit to generate a behavior randomly</b>`
 *
 * @author mmacret
 */
public class generationEngine {

    /**
     * The generator of random numbers.
     *
     * @see Random
     */
    private Random generator;
//private String distribution;//Non implementé

    /**
     * GenerationEngine constructor.
     *
     * @see generationEngine#generator
     */
    public generationEngine() {
        Random gen = new Random();
        this.generator = gen;
    }

    /**
     * Give the generator of random numbers.
     *
     * @return the generator of random numbers.
     */
    public Random getGenerator() {
        return generator;
    }

    /**
     * Generate a random violating behavior with completely defined parameters.
     *
     * @param norm
     *     The norm on which the behavior is built.
     *
     * @param gapSuceptibility
     *     Quantify the violation.
     * @return A random violating behavior.
     *
     * @throws java.lang.Exception
     */
    public Behavior generateViolatingBehavior(Norm norm,double gapSuceptibility) throws Exception {
        Behavior violatingBehavior = null;
        //Tendancy of an institution to generate violating agent.
        double sigma = norm.getMotherInstitution().getSigma();

        //Tendancy of a norm to generate violating agent.
        double to = norm.getTo();

        //Random
        double beta = generator.nextDouble();

        if(beta < sigma){
            if(beta < to){
                //Creation of a norm with the institution constraint
                Norm institutionNorm = new Norm(norm.getName(),norm.getMotherInstitution(),norm.getColor());
                institutionNorm = institutionNorm.completeNorm();

                
                //Generation of a behavior with this norm.
                violatingBehavior = generateBehavior(institutionNorm);

                //Try to generate a behavior until the violation constraint would be respected.
                while(calculateGap(norm,violatingBehavior) > gapSuceptibility){
                    violatingBehavior = generateBehavior(institutionNorm);
                }


            }
            else violatingBehavior = generateBehavior(norm);
        }
        else violatingBehavior = generateBehavior(norm);

        //Tag the behavior as violating.
        violatingBehavior.setViolating(true);



        
        return violatingBehavior;
    }
/**
 * quantify the gap between a normal generating behavior and a violating behavior.
 * @param norm Reference Norm
 *
 * @param behavior Violating behavior to quantify.
 * @return the quantification
 * @throws java.lang.Exception
 */
    public double calculateGap(Norm norm,Behavior behavior) throws Exception{

        double gap = 0;

        int normSize = norm.getEnvironnementParameters().size() + norm.getInstitutionalParameters().size();

        for(Parameter param : norm.getEnvironnementParameters().values()){
            double value = behavior.getParameters().get(param.getName()).getValue();

            //Discrete Parameter
            if(param.getDefinitionSet().isIsDiscrete()){
                if(!param.getDefinitionSet().getEns().contains(value)) gap = gap + 1.0;
            }
            //Continuous Parameter
            else{

            double borneInfNorm = param.getDefinitionSet().getBorneInf();
            double borneInfInstitution = param.getFatherParamater().getDefinitionSet().getBorneInf();
            double borneSupNorm = param.getDefinitionSet().getBorneSup();
            double borneSupInstitution = param.getFatherParamater().getDefinitionSet().getBorneSup();
            double defInstitutionSize = borneSupInstitution - borneInfInstitution;

            

            if(value > borneSupNorm){
                gap = gap + (Math.abs(value - borneSupNorm)/defInstitutionSize);
            }
            if(value < borneInfNorm){
                gap = gap + (Math.abs(value - borneInfNorm)/defInstitutionSize);
            }
            }
        }

        for(Parameter param : norm.getInstitutionalParameters().values()){
            double value = behavior.getParameters().get(param.getName()).getValue();
            //Discrete Parameter
            if(param.getDefinitionSet().isIsDiscrete()){
                if(!param.getDefinitionSet().getEns().contains(value)) gap = gap + 1.0;
            }
            else{
            //Continuous Parameter
            double borneInfNorm = param.getDefinitionSet().getBorneInf();
            double borneInfInstitution = param.getFatherParamater().getDefinitionSet().getBorneInf();
            double borneSupNorm = param.getDefinitionSet().getBorneSup();
            double borneSupInstitution = param.getFatherParamater().getDefinitionSet().getBorneSup();
            double defInstitutionSize = borneSupInstitution - borneInfInstitution;


            if(value > borneSupNorm){
                gap = gap + (Math.abs(value - borneSupNorm)/defInstitutionSize);
            }
            if(value < borneInfNorm){
                gap = gap + (Math.abs(value - borneInfNorm)/defInstitutionSize);
            }
            }
        }
        
        

        gap = gap / normSize;

        return gap;

    }

    /**
     * Generate a random behavior with completely defined parameters.
     *
     * @param norm
     *     The norm on which the behavior is built.
     * @return A random behavior
     *
     * @throws java.lang.Exception
     */
    public Behavior generateBehavior(Norm norm) throws Exception{
        Behavior behavior = new Behavior(norm);
        double inf;
        double sup;
        double value;
        int nbElements;


        for (String key : behavior.getParameters().keySet()) {
            if (behavior.getParameters().get(key).isBelongNorm()) {
                if (behavior.getParameters().get(key).getFatherNormParameter().getDefinitionSet().isIsDiscrete()) {
                    nbElements = behavior.getParameters().get(key).getFatherNormParameter().getDefinitionSet().getEns().size();
                    value = behavior.getParameters().get(key).getFatherNormParameter().getDefinitionSet().getEns().get(this.getGenerator().nextInt(nbElements));

                    //Instantiation du paramètre
                    behavior.getParameters().get(key).setValue(value);
                } else {
                    inf = behavior.getParameters().get(key).getFatherNormParameter().getDefinitionSet().getBorneInf();
                    sup = behavior.getParameters().get(key).getFatherNormParameter().getDefinitionSet().getBorneSup();

                    value = this.getGenerator().nextDouble() * (sup - inf) + inf;

                    //Instantiation du paramètre
                    behavior.getParameters().get(key).setValue(value);
                }
            } else {
                if (behavior.getParameters().get(key).getFatherInstitutionalParameter().getDefinitionSet().isIsDiscrete()) {
                    nbElements = behavior.getParameters().get(key).getFatherInstitutionalParameter().getDefinitionSet().getEns().size();
                    value = behavior.getParameters().get(key).getFatherInstitutionalParameter().getDefinitionSet().getEns().get(this.getGenerator().nextInt(nbElements));

                    //Instantiation du paramètre
                    behavior.getParameters().get(key).setValue(value);
                } else {
                    inf = behavior.getParameters().get(key).getFatherInstitutionalParameter().getDefinitionSet().getBorneInf();
                    sup = behavior.getParameters().get(key).getFatherInstitutionalParameter().getDefinitionSet().getBorneSup();

                    value = this.getGenerator().nextDouble() * (sup - inf) + inf;

                    //Instantiation du paramètre
                    behavior.getParameters().get(key).setValue(value);
                }
            }


        }
        return behavior;
    }

    public static void main(String[] args) {
        generationEngine engine = new generationEngine();

        for (int i = 0; i < 3; i++) {
            System.out.println(engine.getGenerator().nextDouble());
        }




    // TODO code application logic here
    }
}
