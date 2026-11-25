package lab4;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Etapa 6 - O zi la stand, a doua versiune.
 * Foloseste Catalog, interfetele si regulile de reducere.
 */
public class ZiLaStand {

    private final Stand stand;
    private final List<Bon> bonuri = new ArrayList<>();
    private final Map<String, BigDecimal> reduceriPeRegula = new HashMap<>();

    public ZiLaStand(Stand stand) {
        this.stand = stand;
    }

    public void ruleazaScenariu() {
        System.out.println("########## ZI LA STAND: " + stand.getNume() + " ##########\n");

        Catalog cat = stand.getCatalog();
        List<Produs> p = cat.getProduse();

        RegulaReducere procent = new ReducereProcentFix();
        RegulaReducere prag = new ReducerePestePrag();
        RegulaReducere duo = new ReducereDuoShaorma();
        RegulaReducere lambda = new RegulaReducere() {
            @Override
            public BigDecimal calculeaza(Comanda comanda) {
                if (comanda.subtotal().compareTo(new BigDecimal("90.00")) > 0) {
                    return new BigDecimal("8.00");
                }
                return BigDecimal.ZERO.setScale(2);
            }
            @Override
            public String nume() { return "Lambda: -8 lei daca subtotal > 90"; }
        };

        // Comanda 1: o shaorma + bautura (fara reducere mare)
        emite(comanda(p, "Shaorma Pui", 1, "Ayran", 1), List.of(procent, duo));

        // Comanda 2: combo clasic
        emite(comanda(p, "Combo Clasic", 1), List.of(procent, prag));

        // Comanda 3: doua shaorme - activeaza Duo-Shaorma
        emite(comanda(p, "Shaorma Pui", 1, "Shaorma Vita", 1, "Cola", 1),
                List.of(duo, procent, prag));

        // Comanda 4: comanda mare - peste prag
        emite(comanda(p, "Shaorma Vita", 2, "Cartofi prajiti", 2, "Limonada", 2),
                List.of(prag, procent, lambda));

        // Comanda 5: falafel + coleslaw
        emite(comanda(p, "Shaorma Falafel", 1, "Salata coleslaw", 1, "Ayran", 1),
                List.of(duo, procent));

        // Comanda 6: inca o shaorma + combo vegan (bon cu cadou posibil)
        emite(comanda(p, "Shaorma Pui", 1, "Combo Vegan", 1),
                List.of(duo, lambda, procent));

        // Comanda 7: bauturi
        emite(comanda(p, "Cola", 2, "Apa plata", 1), List.of(procent));

        // Comanda 8: inca doua shaorme
        emite(comanda(p, "Shaorma Falafel", 1, "Shaorma Vita", 1),
                List.of(duo, prag));

        raportulZilei();
    }

    private Comanda comanda(List<Produs> produse, Object... args) {
        Comanda c = new Comanda();
        for (int i = 0; i < args.length; i += 2) {
            String nume = (String) args[i];
            int cant = (Integer) args[i + 1];
            Produs prod = null;
            for (Produs x : produse) {
                if (x.getDenumire().equals(nume)) {
                    prod = x;
                    break;
                }
            }
            if (prod == null) {
                System.out.println("  (produs negasit: " + nume + ")");
                continue;
            }
            c.adauga(prod, cant);
        }
        return c;
    }

    private void emite(Comanda c, List<RegulaReducere> reguli) {
        if (c.esteGoala()) {
            System.out.println("  (comanda goala - fara bon)\n");
            return;
        }
        Bon b = new Bon(c, reguli);
        bonuri.add(b);
        if (b.getRegulaAplicata() != null && b.getValoareReducere().signum() > 0) {
            String nume = b.getRegulaAplicata().nume();
            reduceriPeRegula.merge(nume, b.getValoareReducere(), BigDecimal::add);
        }
        System.out.println(b);
        System.out.println();
    }

    private void raportulZilei() {
        BigDecimal incasari = BigDecimal.ZERO;
        for (Bon b : bonuri) {
            incasari = incasari.add(b.getTotal());
        }

        Catalog cat = stand.getCatalog();
        Comparator<Produs> dupaVanzari = Comparator.comparingInt(Produs::getUnitatiVandute).reversed();
        List<Produs> top = cat.top(Parametri.TOP_K, dupaVanzari);

        System.out.println("################ RAPORTUL ZILEI ################");
        System.out.println("Numar de bonuri: " + bonuri.size());
        System.out.printf("Incasari totale: %.2f lei%n", incasari);

        System.out.println("Top " + Parametri.TOP_K + " produse dupa vanzari:");
        for (Produs p : top) {
            System.out.printf("  %-20s %d buc.%n", p.getDenumire(), p.getUnitatiVandute());
        }

        System.out.println("Valoare stoc ramas: " + cat.valoareStoc() + " lei");

        System.out.println("Reduceri acordate pe reguli:");
        if (reduceriPeRegula.isEmpty()) {
            System.out.println("  (niciuna)");
        } else {
            BigDecimal totalRed = BigDecimal.ZERO;
            for (Map.Entry<String, BigDecimal> e : reduceriPeRegula.entrySet()) {
                System.out.printf("  %-40s %8.2f lei%n", e.getKey(), e.getValue());
                totalRed = totalRed.add(e.getValue());
            }
            System.out.printf("  TOTAL REDUCERI:                          %8.2f lei%n", totalRed);
        }

        System.out.println("Stoc ramas:");
        for (Produs prod : cat.getProduse()) {
            String nota = (prod instanceof MeniuCombo) ? "  (derivat din componente)" : "";
            System.out.printf("  %-16s %3d%s%n", prod.getDenumire(), prod.getStoc(), nota);
        }
        System.out.println("################################################");
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        ZiLaStand zi = new ZiLaStand(Stand.shaormaExpress());
        zi.ruleazaScenariu();
    }
}
