package lab4;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Catalogul standului - lucreaza exclusiv cu tipul abstract Produs.
 * Nu apare numele niciunei subclase concrete.
 */
public class Catalog {

    private final List<Produs> produse = new ArrayList<>();

    public void adauga(Produs p) {
        if (p == null) throw new IllegalArgumentException("Produs null");
        produse.add(p);
    }

    public List<Produs> getProduse() {
        return Collections.unmodifiableList(produse);
    }

    /** Afiseaza toate produsele: categorie, pret baza, pret final. */
    public void afiseaza() {
        System.out.println("--- Catalog ---");
        for (Produs p : produse) {
            System.out.printf("  %-20s | %-15s | baza=%6.2f | final=%6.2f | stoc=%3d%n",
                    p.getDenumire(), p.categorie(), p.getPret(), p.pretFinal(), p.getStoc());
        }
    }

    /**
     * Valoarea totala a stocului = suma (pretFinal * stoc), cu BigDecimal.
     */
    public BigDecimal valoareStoc() {
        BigDecimal total = BigDecimal.ZERO;
        for (Produs p : produse) {
            BigDecimal linie = BigDecimal.valueOf(p.pretFinal())
                    .multiply(BigDecimal.valueOf(p.getStoc()))
                    .setScale(2, RoundingMode.HALF_UP);
            total = total.add(linie);
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /** Produsele din categoria data (comparatie case-insensitive). */
    public List<Produs> dinCategoria(String categorie) {
        if (categorie == null) return List.of();
        return produse.stream()
                .filter(p -> p.categorie().equalsIgnoreCase(categorie.trim()))
                .collect(Collectors.toList());
    }

    /**
     * Primele k produse dupa criteriul dat.
     * Nu depinde de clase concrete.
     */
    public List<Produs> top(int k, Comparator<Produs> criteriu) {
        if (k <= 0) return List.of();
        List<Produs> sorted = new ArrayList<>(produse);
        sorted.sort(criteriu);
        return sorted.subList(0, Math.min(k, sorted.size()));
    }
}
