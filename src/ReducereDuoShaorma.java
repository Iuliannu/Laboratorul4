package lab4;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Regula proprie din lab3: daca exista cel putin 2 portii de shaorma
 * (orice tip), cea mai ieftina portie primeste 50% reducere.
 * Mutata din Comanda intr-o clasa separata.
 */
public class ReducereDuoShaorma implements RegulaReducere {

    public static final int PRAG = 2;
    public static final BigDecimal FACTOR = new BigDecimal("0.50");

    @Override
    public BigDecimal calculeaza(Comanda comanda) {
        if (comanda == null || comanda.esteGoala()) {
            return BigDecimal.ZERO.setScale(2);
        }
        int portii = 0;
        BigDecimal celMaiIeftin = null;
        for (LinieComanda l : comanda.getLinii()) {
            Produs p = l.getProdus();
            if (p instanceof Shaorma) { // include si ShaormaVegana
                portii += l.getCantitate();
                BigDecimal unitar = BigDecimal.valueOf(p.pretFinal());
                if (celMaiIeftin == null || unitar.compareTo(celMaiIeftin) < 0) {
                    celMaiIeftin = unitar;
                }
            }
        }
        if (portii < PRAG || celMaiIeftin == null) {
            return BigDecimal.ZERO.setScale(2);
        }
        return celMaiIeftin.multiply(FACTOR).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String nume() {
        return "Duo-Shaorma (50% la cea mai ieftina)";
    }
}
