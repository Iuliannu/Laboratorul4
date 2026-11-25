package lab4;

import java.util.Locale;

/**
 * Etapa 2 - Interfetele standului.
 * Parcurge List<Produs> si afiseaza capacitatile folosind instanceof doar pe interfete.
 */
public class Etapa2Demo {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        System.out.println("=== Etapa 2: Interfete ===\n");

        Stand stand = Stand.shaormaExpress();

        // Personalizam o shaorma
        for (Produs p : stand.getProduse()) {
            if (p instanceof Personalizabil pers && p.getDenumire().contains("Pui")) {
                pers.adaugaIngredient("ceapa carmelizata");
                pers.adaugaIngredient("sos picant");
                if (p instanceof LaPachet lp) {
                    lp.setLaPachet(true);
                }
            }
        }

        System.out.println("Capacitati pe List<Produs> (instanceof doar pe interfete):\n");
        for (Produs p : stand.getProduse()) {
            System.out.print(p.getDenumire() + " [" + p.categorie() + "]: ");
            boolean are = false;
            if (p instanceof Personalizabil pers) {
                System.out.print("Personalizabil(" + pers.rezumatPersonalizare() + "); ");
                are = true;
            }
            if (p instanceof LaPachet lp) {
                System.out.print("LaPachet(" + lp.descriereServire() + "); ");
                are = true;
            }
            if (p instanceof PregatitLaComanda prep) {
                System.out.print("PregatitLaComanda(" + prep.mesajAsteptare() + "); ");
                are = true;
            }
            if (!are) {
                System.out.print("(fara capacitati speciale)");
            }
            System.out.println();
        }

        System.out.println("\nObservatii:");
        System.out.println("- Shaorma implementeaza Personalizabil + LaPachet + PregatitLaComanda");
        System.out.println("- Garnitura implementeaza LaPachet");
        System.out.println("- MeniuCombo implementeaza PregatitLaComanda");
        System.out.println("- Bautura nu implementeaza niciuna (nu are sens)");
        System.out.println("- Personalizabil are metoda default rezumatPersonalizare()");
    }
}
