package lab4;

/**
 * Shaorma vegana - al doilea nivel al ierarhiei.
 * Mosteneste capacitatile de la Shaorma (Personalizabil, LaPachet, PregatitLaComanda).
 * Nivelul intermediar Shaorma nu este abstract: se pot vinde shaorme clasice.
 */
public class ShaormaVegana extends Shaorma {

    private static final double SUPLIMENT_HUMMUS = 4.00;
    private final boolean cuHummus;

    public ShaormaVegana(String denumire, double pret, int stoc,
                         Picanterie picanterie, boolean cuHummus) {
        super(denumire, pret, stoc, picanterie);
        this.cuHummus = cuHummus;
    }

    public boolean isCuHummus() { return cuHummus; }

    @Override
    public double pretFinal() {
        double extra = 0;
        for (String ignored : getIngredienteExtra()) {
            extra += 2.0;
        }
        if (esteLaPachet()) {
            extra += costAmbalaj();
        }
        return rotunjeste(getPret() + getPicanterie().getSupliment()
                + (cuHummus ? SUPLIMENT_HUMMUS : 0) + extra);
    }

    @Override
    public String categorie() {
        return "Shaorma vegana";
    }

    @Override
    public int timpPregatireMinute() {
        return 10;
    }

    @Override
    public String toString() {
        String base = super.toString();
        String tag = cuHummus ? "[vegana, cu hummus," : "[vegana,";
        return base.replace("[shaorma,", tag);
    }
}
