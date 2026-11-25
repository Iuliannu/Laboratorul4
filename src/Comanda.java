package lab4;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Comanda este proprietarul liniilor sale (compozitie). */
public class Comanda {

    private final List<LinieComanda> linii = new ArrayList<>();
    private boolean inchisa = false;

    public boolean adauga(Produs p, int cantitate) {
        if (p == null || cantitate <= 0) {
            throw new IllegalArgumentException("Produs sau cantitate invalide");
        }
        if (inchisa) {
            throw new IllegalStateException("Comanda este inchisa (bonul a fost emis)");
        }
        if (!p.areStoc(cantitate)) {
            System.out.println("  Refuzat: " + p.getDenumire() + " x" + cantitate
                    + " (stoc disponibil: " + p.getStoc() + ")");
            return false;
        }
        p.scadeStoc(cantitate);
        LinieComanda existenta = gasesteLinia(p);
        if (existenta != null) {
            existenta.maresteCantitatea(cantitate);
        } else {
            linii.add(new LinieComanda(p, cantitate));
        }
        System.out.println("  Adaugat: " + p.getDenumire() + " x" + cantitate);
        return true;
    }

    private LinieComanda gasesteLinia(Produs p) {
        for (LinieComanda l : linii) {
            if (l.getProdus().equals(p)) {
                return l;
            }
        }
        return null;
    }

    public List<LinieComanda> getLinii() {
        return Collections.unmodifiableList(linii);
    }

    public boolean esteGoala() { return linii.isEmpty(); }

    public BigDecimal subtotal() {
        BigDecimal suma = BigDecimal.ZERO;
        for (LinieComanda l : linii) {
            suma = suma.add(l.subtotal());
        }
        return suma.setScale(2, RoundingMode.HALF_UP);
    }

    void inchide() { inchisa = true; }

    public boolean esteInchisa() { return inchisa; }
}
