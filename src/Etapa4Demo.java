package lab4;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Etapa 4 - Ordonare cu Comparable si Comparator.
 */
public class Etapa4Demo {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        System.out.println("=== Etapa 4: Ordonare ===\n");

        Stand stand = Stand.shaormaExpress();
        Catalog cat = stand.getCatalog();

        // Simulam cateva vanzari
        for (Produs p : cat.getProduse()) {
            if (p.getDenumire().contains("Falafel")) p.inregistreazaVanzare(5);
            if (p.getDenumire().contains("Pui")) p.inregistreazaVanzare(3);
            if (p.getDenumire().contains("Cola")) p.inregistreazaVanzare(4);
            if (p.getDenumire().contains("Cartofi")) p.inregistreazaVanzare(2);
        }

        System.out.println("1) Ordine naturala (Comparable - alfabetic dupa denumire):");
        List<Produs> alfabetic = cat.top(cat.getProduse().size(), Comparator.naturalOrder());
        for (Produs p : alfabetic) {
            System.out.println("  " + p.getDenumire());
        }

        System.out.println("\n2) Dupa pret final (crescator):");
        Comparator<Produs> dupaPret = Comparator.comparingDouble(Produs::pretFinal);
        for (Produs p : cat.top(cat.getProduse().size(), dupaPret)) {
            System.out.printf("  %-20s %6.2f lei%n", p.getDenumire(), p.pretFinal());
        }

        System.out.println("\n3) Top " + Parametri.TOP_K + " dupa unitati vandute (descrescator):");
        Comparator<Produs> dupaVanzari = Comparator.comparingInt(Produs::getUnitatiVandute).reversed();
        List<Produs> top = cat.top(Parametri.TOP_K, dupaVanzari);
        for (Produs p : top) {
            System.out.printf("  %-20s %d buc.%n", p.getDenumire(), p.getUnitatiVandute());
        }
    }
}
