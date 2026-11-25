package lab4;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Regula: procent fix de (5 + N % 5)% din subtotal.
 */
public class ReducereProcentFix implements RegulaReducere {

    private final int procent;

    public ReducereProcentFix(int procent) {
        this.procent = procent;
    }

    public ReducereProcentFix() {
        this(Parametri.REDUCERE_PROCENT_FIX);
    }

    @Override
    public BigDecimal calculeaza(Comanda comanda) {
        if (comanda == null || comanda.esteGoala()) {
            return BigDecimal.ZERO.setScale(2);
        }
        return comanda.subtotal()
                .multiply(BigDecimal.valueOf(procent))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    @Override
    public String nume() {
        return "Procent fix " + procent + "%";
    }
}
