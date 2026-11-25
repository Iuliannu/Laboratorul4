package lab4;

/** Dimensiunea portiei pentru garnituri; coeficientul multiplica pretul de baza. */
public enum Portie {
    MICA(0.7, 150),
    NORMALA(1.0, 250),
    MARE(1.4, 350);

    private final double coeficient;
    private final int grame;

    Portie(double coeficient, int grame) {
        this.coeficient = coeficient;
        this.grame = grame;
    }

    public double getCoeficient() { return coeficient; }
    public int getGrame() { return grame; }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
