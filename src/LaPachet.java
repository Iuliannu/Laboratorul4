package lab4;

/**
 * Capacitate: produsul poate fi servit la pachet.
 * Interfata (nu clasa abstracta) pentru ca descrie un comportament comun
 * unor produse fara legatura de mostenire obligatorie.
 */
public interface LaPachet {

    /** True daca produsul este comandat la pachet. */
    boolean esteLaPachet();

    /** Seteaza modul de servire. */
    void setLaPachet(boolean laPachet);

    /** Cost suplimentar pentru ambalaj (lei). */
    double costAmbalaj();

    /** Metoda default: descriere scurta a modului de servire. */
    default String descriereServire() {
        if (esteLaPachet()) {
            return "la pachet (+" + String.format("%.2f", costAmbalaj()) + " lei ambalaj)";
        }
        return "la farfurie";
    }
}
