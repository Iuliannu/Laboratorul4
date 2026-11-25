package lab4;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Regula: daca subtotalul depaseste pragul (100 + 10*(N%4)),
 * se acorda o reducere fixa de 15 lei.
 */
public class ReducerePestePrag implements RegulaReducere {

    private final BigDecimal prag;
    private final BigDecimal reducereFixa;

    public ReducerePestePrag(int pragLei, double reducereLei) {
        this.prag = BigDecimal.valueOf(pragLei).setScale(2);
        this.reducereFixa = BigDecimal.valueOf(reducereLei).setScale(2, RoundingMode.HALF_UP);
    }

    public ReducerePestePrag() {
        this(Parametri.PRAG_REDUCERE, 15.00);
    }

    @Override
    public BigDecimal calculeaza(Comanda comanda) {
        if (comanda == null || comanda.esteGoala()) {
            return BigDecimal.ZERO.setScale(2);
        }
        if (comanda.subtotal().compareTo(prag) > 0) {
            return reducereFixa;
        }
        return BigDecimal.ZERO.setScale(2);
    }

    @Override
    public String nume() {
        return "Peste prag " + prag + " lei (-" + reducereFixa + ")";
    }
}
