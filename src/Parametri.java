package lab4;

/**
 * Parametrii personali ai lucrarii nr. 4, calculati din N = 7.
 */
public final class Parametri {

    public static final int N = 7;

    /** Reducerea regulii „procent fix", %: 5 + N % 5 */
    public static final int REDUCERE_PROCENT_FIX = 5 + N % 5; // 7

    /** Pragul comenzii pentru regula „peste prag", lei: 100 + 10 * (N % 4) */
    public static final int PRAG_REDUCERE = 100 + 10 * (N % 4); // 130

    /** Cate produse afiseaza topul din etapa 4: 3 + N % 3 */
    public static final int TOP_K = 3 + N % 3; // 4

    /** Reducerea meniului combo (din lab3): 10 + N % 6 */
    public static final int REDUCERE_COMBO = 10 + N % 6; // 11

    /** Fiecare al k-lea bon primeste cadou: k = 3 + N % 4 */
    public static final int K_CADOU = 3 + N % 4; // 6

    public static final String CADOU = "Sos de usturoi + autocolant FFI";

    private Parametri() { }
}
