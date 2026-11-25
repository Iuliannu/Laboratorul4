package lab4;

import java.util.Locale;

/**
 * Etapa 1 - Produs abstract.
 * Verifica ca new Produs(...) nu se compileaza si ca produsele afiseaza categoria.
 */
public class Etapa1Demo {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        System.out.println("=== Etapa 1: Produs abstract ===\n");

        // new Produs("Test", 10.0);  // EROARE DE COMPILARE:
        // error: Produs is abstract; cannot be instantiated

        Stand stand = Stand.shaormaExpress();
        System.out.println("Produsele standului (cu categorie):");
        for (Produs p : stand.getProduse()) {
            System.out.println("  " + p);
        }

        System.out.println("\n(Verificare: clasa Produs este abstracta, pretFinal() si categorie() sunt abstracte.)");
    }
}
