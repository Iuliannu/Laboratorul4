package lab4;

/**
 * Bautura. Volumul este in mililitri; pretul de baza este pentru 250 ml.
 * Nu implementeaza interfete de personalizare/pregatire (nu are sens).
 */
public class Bautura extends Produs {

    private final int volumMl;

    public Bautura(String denumire, double pret, int stoc, int volumMl) {
        super(denumire, pret, stoc);
        if (volumMl <= 0) {
            throw new IllegalArgumentException("Volumul trebuie sa fie pozitiv: " + volumMl);
        }
        this.volumMl = volumMl;
    }

    public int getVolumMl() { return volumMl; }

    @Override
    public double pretFinal() {
        // pretul de baza este pentru 250 ml
        return rotunjeste(getPret() * volumMl / 250.0);
    }

    @Override
    public String categorie() {
        return "Bautura";
    }

    @Override
    public String toString() {
        return super.toString() + " [bautura " + volumMl + " ml]";
    }
}
