package lab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Standul „Shaorma Express FFI".
 * Pastreaza catalogul de produse si meniurile (agregare).
 */
public class Stand {

    private final String nume;
    private final Catalog catalog = new Catalog();
    private final List<Meniu> meniuri = new ArrayList<>();

    public Stand(String nume) {
        this.nume = nume;
    }

    public String getNume() { return nume; }
    public Catalog getCatalog() { return catalog; }

    public void adaugaProdus(Produs p) {
        catalog.adauga(p);
    }

    public void adaugaMeniu(Meniu m) {
        meniuri.add(m);
    }

    public List<Produs> getProduse() {
        return catalog.getProduse();
    }

    public List<Meniu> getMeniuri() {
        return Collections.unmodifiableList(meniuri);
    }

    /** Factory: construieste standul Shaorma Express cu produsele standard. */
    public static Stand shaormaExpress() {
        Stand s = new Stand("Shaorma Express FFI");

        Shaorma pui = new Shaorma("Shaorma Pui", 55.00, 10, Picanterie.MEDIE);
        Shaorma vita = new Shaorma("Shaorma Vita", 65.00, 8, Picanterie.PICANTA);
        ShaormaVegana falafel = new ShaormaVegana("Shaorma Falafel", 50.00, 6, Picanterie.FARA, true);
        Garnitura cartofi = new Garnitura("Cartofi prajiti", 25.00, 15, Portie.NORMALA);
        Garnitura coleslaw = new Garnitura("Salata coleslaw", 20.00, 12, Portie.MICA);
        Bautura ayran = new Bautura("Ayran", 12.00, 20, 250);
        Bautura cola = new Bautura("Cola", 12.00, 20, 330);
        Bautura limonada = new Bautura("Limonada", 9.00, 15, 500);
        Bautura apa = new Bautura("Apa plata", 5.00, 25, 500);

        MeniuCombo comboClasic = new MeniuCombo("Combo Clasic", pui, cartofi, cola);
        MeniuCombo comboVegan = new MeniuCombo("Combo Vegan", falafel, coleslaw, ayran);

        s.adaugaProdus(pui);
        s.adaugaProdus(vita);
        s.adaugaProdus(falafel);
        s.adaugaProdus(cartofi);
        s.adaugaProdus(coleslaw);
        s.adaugaProdus(ayran);
        s.adaugaProdus(cola);
        s.adaugaProdus(limonada);
        s.adaugaProdus(apa);
        s.adaugaProdus(comboClasic);
        s.adaugaProdus(comboVegan);

        Meniu dimineata = new Meniu("Meniul de dimineata");
        dimineata.adaugaProdus(pui);
        dimineata.adaugaProdus(cola);
        Meniu zilei = new Meniu("Meniul zilei");
        zilei.adaugaProdus(pui);
        zilei.adaugaProdus(vita);
        zilei.adaugaProdus(cartofi);
        s.adaugaMeniu(dimineata);
        s.adaugaMeniu(zilei);

        return s;
    }
}
