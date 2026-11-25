package lab4;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

/**
 * Etapa 5 - Reguli de reducere interschimbabile + lambda.
 */
public class Etapa5Demo {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        System.out.println("=== Etapa 5: Reguli de reducere ===\n");

        Stand stand = Stand.shaormaExpress();
        List<Produs> produse = stand.getProduse();

        Produs pui = find(produse, "Shaorma Pui");
        Produs vita = find(produse, "Shaorma Vita");
        Produs cola = find(produse, "Cola");
        Produs cartofi = find(produse, "Cartofi prajiti");

        // Aceeasi comanda, trei reguli diferite
        RegulaReducere r1 = new ReducereProcentFix();           // 7%
        RegulaReducere r2 = new ReducerePestePrag();            // 15 lei peste 130
        RegulaReducere r3 = new ReducereDuoShaorma();           // regula proprie
        // Lambda / clasa anonima: reducere de 10 lei daca subtotal > 80
        RegulaReducere rLambda = new RegulaReducere() {
            @Override
            public BigDecimal calculeaza(Comanda comanda) {
                if (comanda.subtotal().compareTo(new BigDecimal("80.00")) > 0) {
                    return new BigDecimal("10.00");
                }
                return BigDecimal.ZERO.setScale(2);
            }
            @Override
            public String nume() { return "Lambda: -10 lei daca subtotal > 80"; }
        };
        // Forma lambda pura (echivalenta):
        // RegulaReducere rL = c -> c.subtotal().compareTo(new BigDecimal("80")) > 0
        //         ? new BigDecimal("10.00") : BigDecimal.ZERO.setScale(2);

        System.out.println("Comanda test: Shaorma Pui x1 + Shaorma Vita x1 + Cartofi x1 + Cola x1");
        System.out.println("(subtotal asteptat: 55+68+25+15.84 = 163.84)\n");

        for (RegulaReducere r : List.of(r1, r2, r3, rLambda)) {
            // Reconstruim stocul / comanda pentru fiecare test
            Stand s = Stand.shaormaExpress();
            Produs p1 = find(s.getProduse(), "Shaorma Pui");
            Produs p2 = find(s.getProduse(), "Shaorma Vita");
            Produs p3 = find(s.getProduse(), "Cartofi prajiti");
            Produs p4 = find(s.getProduse(), "Cola");

            Comanda c = new Comanda();
            c.adauga(p1, 1);
            c.adauga(p2, 1);
            c.adauga(p3, 1);
            c.adauga(p4, 1);

            Bon b = new Bon(c, r);
            System.out.println(b);
            System.out.println("  (regula: " + (r.nume().isEmpty() ? "lambda >80 lei => -10" : r.nume()) + ")\n");
        }

        System.out.println("Verificare manuala:");
        System.out.println("  Procent 7%: 163.84 * 0.07 = 11.47 => total 152.37");
        System.out.println("  Peste prag 130: -15.00 => total 148.84");
        System.out.println("  Duo-Shaorma: min(55,68)*0.5 = 27.50 => total 136.34");
        System.out.println("  Lambda >80: -10.00 => total 153.84");
    }

    private static Produs find(List<Produs> list, String nume) {
        for (Produs p : list) {
            if (p.getDenumire().equals(nume)) return p;
        }
        throw new IllegalStateException("Produs negasit: " + nume);
    }
}
