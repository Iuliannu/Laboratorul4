package lab4;

import java.util.Objects;

/**
 * Superclasa abstracta a ierarhiei de produse.
 * Nu se poate instanția: un „produs oarecare" nu se vinde;
 * se vinde intotdeauna o shaorma, garnitura, bautura sau combo.
 * pretFinal() si categorie() sunt abstracte - fiecare subclasa le implementeaza.
 */
public abstract class Produs implements Comparable<Produs> {

    private static int contor = 0;

    private final int id;
    private final String denumire;
    private double pret;
    private int stoc;
    /** Numar de unitati vandute (pentru comparatorul dupa vanzari). */
    private int unitatiVandute;

    public Produs(String denumire, double pret) {
        this(denumire, pret, 0);
    }

    public Produs(String denumire, double pret, int stoc) {
        if (denumire == null || denumire.isBlank()) {
            throw new IllegalArgumentException("Denumirea produsului nu poate fi goala");
        }
        valideazaPret(pret);
        if (stoc < 0) {
            throw new IllegalArgumentException("Stocul nu poate fi negativ: " + stoc);
        }
        this.id = ++contor;
        this.denumire = denumire.trim();
        this.pret = pret;
        this.stoc = stoc;
        this.unitatiVandute = 0;
    }

    private static void valideazaPret(double pret) {
        if (Double.isNaN(pret) || pret < 0) {
            throw new IllegalArgumentException("Pretul nu poate fi negativ: " + pret);
        }
    }

    protected static double rotunjeste(double valoare) {
        return Math.round(valoare * 100) / 100.0;
    }

    public int getId() { return id; }
    public String getDenumire() { return denumire; }
    public double getPret() { return pret; }

    public void setPret(double pret) {
        valideazaPret(pret);
        this.pret = pret;
    }

    public int getStoc() { return stoc; }

    public int getUnitatiVandute() { return unitatiVandute; }

    /** Inregistreaza vanzarea (apelata de Comanda dupa scadeStoc). */
    public void inregistreazaVanzare(int cantitate) {
        if (cantitate > 0) {
            unitatiVandute += cantitate;
        }
    }

    /** Pretul final - implementat de fiecare subclasa concreta. */
    public abstract double pretFinal();

    /** Denumirea categoriei produsului (ex. „Shaorma", „Bautura"). */
    public abstract String categorie();

    public boolean areStoc(int cantitate) {
        return getStoc() >= cantitate;
    }

    public void scadeStoc(int cantitate) {
        if (cantitate <= 0) {
            throw new IllegalArgumentException("Cantitatea trebuie sa fie pozitiva: " + cantitate);
        }
        if (!areStoc(cantitate)) {
            throw new IllegalStateException("Stoc insuficient pentru " + denumire);
        }
        this.stoc -= cantitate;
        inregistreazaVanzare(cantitate);
    }

    @Override
    public String toString() {
        return String.format("[%d] %s (%s) pret baza=%.2f pret final=%.2f stoc=%d",
                id, denumire, categorie(), pret, pretFinal(), getStoc());
    }

    /** Egalitate dupa denumire (ignorand majuscule/spatii), nu dupa id. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produs)) return false;
        return denumire.equalsIgnoreCase(((Produs) o).denumire);
    }

    @Override
    public int hashCode() {
        return denumire.toLowerCase().hashCode();
    }

    /** Ordinea naturala: alfabetica dupa denumire. */
    @Override
    public int compareTo(Produs other) {
        return this.denumire.compareToIgnoreCase(other.denumire);
    }
}
