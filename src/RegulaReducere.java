package lab4;

import java.math.BigDecimal;

/**
 * Interfata functionala pentru regulile de reducere interschimbabile.
 * Metoda intoarce valoarea reducerii (nu totalul redus).
 */
@FunctionalInterface
public interface RegulaReducere {

    /**
     * Calculeaza reducerea aplicabila comenzii.
     * @return valoarea reducerii (pozitiva) sau ZERO daca regula nu se aplica
     */
    BigDecimal calculeaza(Comanda comanda);

    /** Numele regulii, pentru raport. */
    default String nume() {
        return getClass().getSimpleName();
    }
}
