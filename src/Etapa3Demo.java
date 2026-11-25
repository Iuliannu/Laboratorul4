package lab4;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Etapa 3 - Catalog polimorf.
 * Catalog nu cunoaste subclasele concrete.
 */
public class Etapa3Demo {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        System.out.println("=== Etapa 3: Catalog polimorf ===\n");

        Stand stand = Stand.shaormaExpress();
        Catalog cat = stand.getCatalog();

        cat.afiseaza();

        System.out.println("\nValoare stoc (BigDecimal): " + cat.valoareStoc() + " lei");

        // Verificare manuala pentru 3 produse
        Produs pui = null, cartofi = null, cola = null;
        for (Produs p : cat.getProduse()) {
            if (p.getDenumire().equals("Shaorma Pui")) pui = p;
            if (p.getDenumire().equals("Cartofi prajiti")) cartofi = p;
            if (p.getDenumire().equals("Cola")) cola = p;
        }
        if (pui != null && cartofi != null && cola != null) {
            BigDecimal manual = BigDecimal.valueOf(pui.pretFinal() * pui.getStoc()
                    + cartofi.pretFinal() * cartofi.getStoc()
                    + cola.pretFinal() * cola.getStoc())
                    .setScale(2, java.math.RoundingMode.HALF_UP);
            System.out.println("Verificare manuala (Pui + Cartofi + Cola): " + manual + " lei");
        }

        System.out.println("\nProduse din categoria \"Shaorma\":");
        for (Produs p : cat.dinCategoria("Shaorma")) {
            System.out.println("  " + p.getDenumire() + " -> " + p.pretFinal());
        }

        System.out.println("\n(Catalog nu contine numele niciunei subclase concrete.)");
    }
}
