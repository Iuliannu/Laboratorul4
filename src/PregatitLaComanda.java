package lab4;

/**
 * Capacitate: produsul se pregateste la comanda si are timp de asteptare.
 * Interfata (nu clasa abstracta) - comportament comun shaormelor si combo-urilor,
 * fara a forta o legatura de mostenire intre ele.
 */
public interface PregatitLaComanda {

    /** Timp estimat de pregatire in minute. */
    int timpPregatireMinute();

    default String mesajAsteptare() {
        return "Pregatire ~" + timpPregatireMinute() + " min";
    }
}
