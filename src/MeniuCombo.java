package lab4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Meniu combo: este un Produs (se comanda, apare pe bon) si are produse (agregare).
 * Implementeaza PregatitLaComanda (se pregateste la comanda).
 */
public class MeniuCombo extends Produs implements PregatitLaComanda {

    private final List<Produs> componente;

    public MeniuCombo(String denumire, Produs... componente) {
        super(denumire, sumaBaza(componente), 0);
        this.componente = new ArrayList<>(Arrays.asList(componente));
    }

    private static double sumaBaza(Produs[] componente) {
        if (componente == null || componente.length < 2) {
            throw new IllegalArgumentException("Un combo are cel putin doua componente");
        }
        double suma = 0;
        for (Produs c : componente) {
            if (c == null) throw new IllegalArgumentException("Componenta null");
            suma += c.getPret();
        }
        return suma;
    }

    public List<Produs> getComponente() {
        return Collections.unmodifiableList(componente);
    }

    @Override
    public double pretFinal() {
        double suma = 0;
        for (Produs c : componente) {
            suma += c.pretFinal();
        }
        return rotunjeste(suma * (100 - Parametri.REDUCERE_COMBO) / 100.0);
    }

    @Override
    public String categorie() {
        return "Combo";
    }

    /** Stocul combo-ului = minimul stocurilor componentelor. */
    @Override
    public int getStoc() {
        int minim = Integer.MAX_VALUE;
        for (Produs c : componente) {
            minim = Math.min(minim, c.getStoc());
        }
        return minim == Integer.MAX_VALUE ? 0 : minim;
    }

    @Override
    public void scadeStoc(int cantitate) {
        if (!areStoc(cantitate)) {
            throw new IllegalStateException("Stoc insuficient pentru " + getDenumire());
        }
        for (Produs c : componente) {
            c.scadeStoc(cantitate);
        }
        inregistreazaVanzare(cantitate);
    }

    @Override
    public int timpPregatireMinute() {
        int max = 0;
        for (Produs c : componente) {
            if (c instanceof PregatitLaComanda p) {
                max = Math.max(max, p.timpPregatireMinute());
            }
        }
        return max + 2; // +2 min pentru asamblare
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append(" [combo -").append(Parametri.REDUCERE_COMBO).append("%:");
        for (Produs c : componente) {
            sb.append(' ').append(c.getDenumire()).append(';');
        }
        if (!componente.isEmpty()) {
            sb.setLength(sb.length() - 1);
        }
        return sb.append(']').toString();
    }
}
