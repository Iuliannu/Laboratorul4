package lab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Meniu - agregare de produse (produsele exista independent). */
public class Meniu {

    private final String denumire;
    private final List<Produs> produse = new ArrayList<>();

    public Meniu(String denumire) {
        this.denumire = denumire;
    }

    public String getDenumire() { return denumire; }

    public void adaugaProdus(Produs p) {
        if (p == null) throw new IllegalArgumentException("Produs null");
        produse.add(p);
    }

    public List<Produs> getProduse() {
        return Collections.unmodifiableList(produse);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(denumire).append(": ");
        for (Produs p : produse) {
            sb.append(p.getDenumire()).append("; ");
        }
        return sb.toString();
    }
}
