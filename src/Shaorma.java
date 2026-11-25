package lab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Shaorma clasica (cu carne). Implementeaza Personalizabil, LaPachet si PregatitLaComanda.
 */
public class Shaorma extends Produs implements Personalizabil, LaPachet, PregatitLaComanda {

    private final Picanterie picanterie;
    private final List<String> ingredienteExtra = new ArrayList<>();
    private boolean laPachet = false;

    public Shaorma(String denumire, double pret, int stoc, Picanterie picanterie) {
        super(denumire, pret, stoc);
        if (picanterie == null) {
            throw new IllegalArgumentException("picanterie null");
        }
        this.picanterie = picanterie;
    }

    public Picanterie getPicanterie() { return picanterie; }

    @Override
    public double pretFinal() {
        double extra = 0;
        for (String ignored : ingredienteExtra) {
            extra += 2.0;
        }
        if (laPachet) {
            extra += costAmbalaj();
        }
        return rotunjeste(getPret() + picanterie.getSupliment() + extra);
    }

    @Override
    public String categorie() {
        return "Shaorma";
    }

    @Override
    public boolean adaugaIngredient(String ingredient) {
        if (ingredient == null || ingredient.isBlank()) return false;
        ingredienteExtra.add(ingredient.trim());
        return true;
    }

    @Override
    public List<String> getIngredienteExtra() {
        return Collections.unmodifiableList(ingredienteExtra);
    }

    @Override
    public boolean esteLaPachet() { return laPachet; }

    @Override
    public void setLaPachet(boolean laPachet) { this.laPachet = laPachet; }

    @Override
    public double costAmbalaj() { return 1.50; }

    @Override
    public int timpPregatireMinute() { return 8; }

    @Override
    public String toString() {
        return super.toString() + " [shaorma, picanterie " + picanterie.getEticheta()
                + ", " + rezumatPersonalizare() + ", " + descriereServire() + "]";
    }
}
