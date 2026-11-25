package lab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Garnitura. Implementeaza LaPachet (se poate lua la pachet).
 */
public class Garnitura extends Produs implements LaPachet {

    private final Portie portie;
    private boolean laPachet = false;

    public Garnitura(String denumire, double pret, int stoc, Portie portie) {
        super(denumire, pret, stoc);
        if (portie == null) throw new IllegalArgumentException("portie null");
        this.portie = portie;
    }

    public Portie getPortie() { return portie; }

    @Override
    public double pretFinal() {
        double p = getPret() * portie.getCoeficient();
        if (laPachet) {
            p += costAmbalaj();
        }
        return rotunjeste(p);
    }

    @Override
    public String categorie() {
        return "Garnitura";
    }

    @Override
    public boolean esteLaPachet() { return laPachet; }

    @Override
    public void setLaPachet(boolean laPachet) { this.laPachet = laPachet; }

    @Override
    public double costAmbalaj() { return 0.80; }

    @Override
    public String toString() {
        return super.toString() + " [garnitura " + portie + ", " + portie.getGrame()
                + " g, " + descriereServire() + "]";
    }
}
