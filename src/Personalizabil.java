package lab4;

/**
 * Capacitate: produsul poate fi personalizat cu ingrediente suplimentare.
 * Interfata (nu clasa abstracta) pentru ca nu are stare proprie si poate
 * fi implementata de clase din ramuri diferite ale ierarhiei.
 */
public interface Personalizabil {

    /** Adauga un ingredient suplimentar; intoarce true daca a reusit. */
    boolean adaugaIngredient(String ingredient);

    /** Lista ingredientelor adaugate (nemodificabila sau copie). */
    java.util.List<String> getIngredienteExtra();

    /** Metoda default: afiseaza un rezumat al personalizarii. */
    default String rezumatPersonalizare() {
        java.util.List<String> extra = getIngredienteExtra();
        if (extra == null || extra.isEmpty()) {
            return "fara personalizare";
        }
        return "extra: " + String.join(", ", extra);
    }
}
