/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package generationModele;
import java.awt.Color;
import java.util.Random;
/**
 * RandomColor is a coass that allows the user to create random
 * colors and random grays.
 *
 * @author William Austad
 * @version 5/12/03
 */
public class RandomColor
{

    private Random rand;

    /**
     * Constructor for objects of class RandomColor initializes the
     * random number generator
     */
    public RandomColor()
    {
        rand = new Random();
    }

    /**
     * randomColor returns a pseudorandom Color
     *
     * @return a pseudorandom Color
     */
    public Color randomColor()
    {
        return(new Color(rand.nextInt(256),
                         rand.nextInt(256),
                         rand.nextInt(256)));
    }

    /**
     * randomGray returns a pseudorandom gray Color
     *
     * @return a pseudorandom Color
     */
    public Color randomGray()
    {
        int intensity = rand.nextInt(256);
        return(new Color(intensity,intensity,intensity));
    }
}