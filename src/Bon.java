package lab4;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Bonul refera o comanda (agregare) si primeste regula(regulele) de reducere.
 * Daca se aplica mai multe reguli, se alege cea mai avantajoasa pentru client.
 */
public class Bon {

    private static int contor = 0;

    private final int numar;
    private final Comanda comanda;
    private final RegulaReducere regulaAplicata;
    private final BigDecimal valoareReducere;
    private final BigDecimal total;

    /**
     * Construieste bonul alegand regula cea mai avantajoasa din lista data.
     * Daca lista e goala, nu se aplica nicio reducere.
     */
    public Bon(Comanda comanda, List<RegulaReducere> reguli) {
        if (comanda == null || comanda.esteGoala()) {
            throw new IllegalArgumentException("Comanda invalida sau goala");
        }
        this.numar = ++contor;
        this.comanda = comanda;
        comanda.inchide();

        RegulaReducere ceaMaiBuna = null;
        BigDecimal maxReducere = BigDecimal.ZERO.setScale(2);

        if (reguli != null) {
            for (RegulaReducere r : reguli) {
                if (r == null) continue;
                BigDecimal red = r.calculeaza(comanda);
                if (red.compareTo(maxReducere) > 0) {
                    maxReducere = red;
                    ceaMaiBuna = r;
                }
            }
        }

        this.regulaAplicata = ceaMaiBuna;
        this.valoareReducere = maxReducere;
        this.total = comanda.subtotal().subtract(maxReducere).setScale(2, RoundingMode.HALF_UP);
    }

    /** Constructor cu o singura regula (sau null). */
    public Bon(Comanda comanda, RegulaReducere regula) {
        this(comanda, regula == null ? List.of() : List.of(regula));
    }

    public int getNumar() { return numar; }
    public Comanda getComanda() { return comanda; }
    public RegulaReducere getRegulaAplicata() { return regulaAplicata; }
    public BigDecimal getValoareReducere() { return valoareReducere; }
    public BigDecimal getTotal() { return total; }

    public boolean areCadou() {
        return numar % Parametri.K_CADOU == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== BON nr. ").append(numar).append(" ==========\n");
        for (LinieComanda l : comanda.getLinii()) {
            sb.append(String.format("  %-20s x%d  %8.2f lei%n",
                    l.getProdus().getDenumire(), l.getCantitate(), l.subtotal()));
        }
        sb.append(String.format("  Subtotal:              %8.2f lei%n", comanda.subtotal()));
        if (valoareReducere.compareTo(BigDecimal.ZERO) > 0) {
            String numeReg = regulaAplicata != null ? regulaAplicata.nume() : "?";
            sb.append(String.format("  Reducere (%s): -%6.2f lei%n", numeReg, valoareReducere));
        }
        sb.append(String.format("  TOTAL:                 %8.2f lei%n", total));
        if (areCadou()) {
            sb.append("  *** CADOU: ").append(Parametri.CADOU).append(" ***\n");
        }
        sb.append("====================================");
        return sb.toString();
    }
}
