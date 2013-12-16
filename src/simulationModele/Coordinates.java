/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulationModele;

/**
 *<b>Coordinates</b>
 *<p>
 * Coordinates consists of :
 * <ul>
 * <li>A x-coordinate</li>
 * <li>A y-coordinate</li>
 * </ul>
 *</p>
 * @author mmacret
 */
public class Coordinates {

    /**
     * X-Coordinate
     */
    private double abscisse;
    /**
     * Y-Coordinate
     */
    private double ordonnee;

    /**
     * Coordinates constructor.
     * You must specify the X-Coordinate and the Y-Coordinate
     * @param abscisse
     *      The X-Coordinate
     * @param ordonnee
     *      The Y-Coordinate
     */
    public Coordinates(double abscisse, double ordonnee) {
        this.abscisse = abscisse;
        this.ordonnee = ordonnee;

    }

    /** Copyconstructor
     * @param coord Coordinates to copy
     */
    public Coordinates(Coordinates coord) {
        abscisse = coord.getAbscisse();
        ordonnee = coord.getOrdonnee();
    }

    /**
     *
     * @return The X-Coordinate.
     */
    public double getAbscisse() {
        return abscisse;
    }

    /**
     *
     * @return The Y-Coordinate.
     */
    public double getOrdonnee() {
        return ordonnee;
    }

    /**
     * Set the X- Coordinate and the Y-Coordinate.
     * @param X
     * @param Y
     */
    public void setCoordinates(double X, double Y) {
        this.abscisse = X;
        this.ordonnee = Y;
    }

    /** Adds a vector to this podouble and returns the resulting podouble
     * @param v Vector
     * @return Resulting coordinates
     */
    public Coordinates add(Vecteur v) {
        return (new Coordinates(abscisse + v.getX(), ordonnee + v.getY()));
    }

    /**
     * Troncate the X-Coordinates and Y-Coordinates
     */
    public void cut() {
        abscisse = (double) (int) abscisse;
        ordonnee = (double) (int) ordonnee;
    }

    @Override
    public String toString() {
        return "X = " + this.abscisse + " Y = " + this.ordonnee;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Coordinates) {
            Coordinates coord2 = (Coordinates) o;
            if ((this.abscisse == coord2.getAbscisse()) && (this.ordonnee == coord2.getOrdonnee())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.abscisse) ^ (Double.doubleToLongBits(this.abscisse) >>> 32));
        hash = 11 * hash + (int) (Double.doubleToLongBits(this.ordonnee) ^ (Double.doubleToLongBits(this.ordonnee) >>> 32));
        return hash;
    }
}
