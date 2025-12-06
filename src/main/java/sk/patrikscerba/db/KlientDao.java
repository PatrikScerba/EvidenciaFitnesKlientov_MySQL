package sk.patrikscerba.db;

// Trieda slúžiaca na skladanie SQL dotazov pomocou metód (builder štýl)
public class KlientDao {

    // Pomocný builder pre skladanie SQL dotazov
    private StringBuilder dotaz;

    // Začiatok DELETE dotazu
    public KlientDao delete(String tabulka){
        dotaz = new StringBuilder();
        dotaz.append("DELETE FROM ");
        dotaz.append(tabulka);
        return this;
    }

    // Pridá podmienku WHERE
    public KlientDao where(String podmienka){
        dotaz.append(" WHERE ");
        dotaz.append(podmienka);
        return this;
    }

    // Pridá tabuľku do časti FROM
    public KlientDao from(String tabulka){
        dotaz.append(" FROM ");
        dotaz.append(tabulka);
        return this;
    }

    // Začiatok UPDATE dotazu
    public KlientDao update(String tabulka){
        dotaz = new StringBuilder();
        dotaz.append("UPDATE ");
        dotaz.append(tabulka);
        dotaz.append(" SET ");
        return this;
    }

    // Nastaví stĺpce v časti SET (napr. meno = ?, email = ?)
    public KlientDao set(String[] stlpce){
        int pocet = stlpce.length;
        if (pocet == 0) {
            throw new IllegalArgumentException("Invalid argument count");
        }
        for (String stlpec : stlpce) {
            dotaz.append(stlpec);
            dotaz.append(" = ?");
            dotaz.append(", ");
        }
        dotaz.setLength(dotaz.length() - 2);
        return this;
    }

    // Začiatok INSERT dotazu
    public KlientDao insert(String tabulka){
        dotaz = new StringBuilder();
        dotaz.append("INSERT INTO ");
        dotaz.append(tabulka);
        return this;
    }

    // Pridá hodnoty do INSERT (VALUES ?, ?, ...)
    public KlientDao values(Object[] parametre){
        dotaz.append(" VALUES (");

        int pocet = parametre.length;
        if (pocet == 0){
            throw new IllegalArgumentException("Neplatný počet argumentov");
        }
        for (int i = 0; i < pocet; i++){
            dotaz.append("?, ");
        }
        dotaz.setLength(dotaz.length() - 2);
        dotaz.append(")");
        return this;
    }

    // Začiatok SELECT dotazu
    public KlientDao select(Object[] stlpce){
        dotaz = new StringBuilder();
        dotaz.append("SELECT ");

        if (stlpce != null){
            for (Object stlpec : stlpce) {
                dotaz.append(stlpec).append(", ");
            }
            dotaz.setLength(dotaz.length() - 2);
        } else {
            dotaz.append("*");
        }
        return this;
    }

    // Vráti hotový SQL dotaz
    public String getDotaz(){
        return dotaz.toString();
    }
}
