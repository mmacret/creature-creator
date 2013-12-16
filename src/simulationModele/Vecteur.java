/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulationModele;


/**
 *<b>Vecteur</b>
 *<p>
 * Vecteur consists of :
 * <ul>
 * <li>A x-coordinate</li>
 * <li>A y-coordinate</li>
 * </ul>
 *</p>
 * @author mmacret
 */
public class Vecteur {

    private double X;
    private double Y;

/** Construct zero length vector */
	public Vecteur()
	{
		X=0;
		Y=0;
	}

	/** Construct vector from x and y info
	 * @param x X member
	 * @param y Y member
	 */
	public Vecteur(double x,double y)
	{
		X=x;
		Y=y;
	}

	/** Copyconstructor
	 * @param v Vector to copy
	 */
	public Vecteur(Vecteur v)
	{
		X=v.getX();
		Y=v.getY();
	}

	/** Construct vector from podouble
	 * @param p The podouble
	 */
	public Vecteur(Coordinates p)
	{
		X = p.getAbscisse();
		Y = p.getOrdonnee();
	}
    /**
     * Construct vector from 2 podoubles
     */
    public Vecteur(Coordinates a, Coordinates b){
        X = b.getAbscisse() - a.getAbscisse();
        Y = b.getOrdonnee() - b.getOrdonnee();
    }

	//////////////////////////////////////////////////////////////////////
	//
	// MANIPULATORS
	//
	//////////////////////////////////////////////////////////////////////

	/** Sets the x member
     * @param x The new x member value
     */
	public void setX(double x)
	{
		X=x;
	}

    /** Sets the y member
     * @param y The new y member value
     */
	public void setY(double y) { Y=y; }

	//////////////////////////////////////////////////////////////////////
	//
	//  ACCESSORS
	//
	//////////////////////////////////////////////////////////////////////

	/** Returns the x member
     * @return X member
     */
	public double getX()
	{
		return X;
	}

    /** Returns the y member
     * @return Y member
     */
	public double getY()
	{
		return Y;
	}


	//////////////////////////////////////////////////////////////////////
	//
	//  UTILITY FUNCTIONS
	//
	//////////////////////////////////////////////////////////////////////

    /** Add another vector to this vector
     * @param v The other vector
     * @return New vector
     */
	public Vecteur add(Vecteur v)
	{
		return (new Vecteur(X+v.getX(), Y+v.getY()));
	}

    /** Subtract a vector from this vector
     * @param v The other vector
     * @return The new vector
     */
	public Vecteur sub(Vecteur v)
	{
		return (new Vecteur(X-v.getX(), Y-v.getY()));
	}

    /** Returns the length of the vector
     * @return The length
     */
	public double length()
	{
		double result;
		try
		{
			result=  Math.sqrt(X*X+Y*Y);
		}
		catch (java.lang.ArithmeticException e)
		{
			result = 0;
		}
		return result;
	}

    /** Calculates the langth of the vector in squared space
     * @return Squared length of vector
     */
	public double lengthSquared()
	{
		return (X*X+Y*Y);
	}

    /** Scales the vector
     * @param a New scale
     */
	public void scale(double a)
	{
		X*=a;
		Y*=a;
	}

    /** Normalizes the vector */
	public void normalize()
	{
		try
		{
			scale(1/length());
		}
		catch (java.lang.ArithmeticException e)
		{
			X = 0;
			Y = 0;
		}
	}

    /** Calculates the dot product of this vector with the other vector
     * @param v The other vector
     * @return Dot product
     */
	public double dot(Vecteur v)
	{
		return X * v.getX() + Y * v.getY();
	}
    /**
     * Transform the vector in the opposite vector
     */
    public void opposite(){

        X = -X;
        Y = -Y;

    }

    @Override
    public String toString(){
      return "Vecteur: X = " +X+" Y= "+Y;
    }
}
