package sk.patrikscerba.db;

// Trieda slúžiaca na skladanie SQL dotazov pomocou metód (builder štýl)
public class KlientDao {

    // Reťazec pre skladanie SQL dotazu
    private StringBuilder dotaz;

    // Metóda na vytvorenie DELETE dotazu DELETE FROM
    public KlientDao delete(String tabulka){
        dotaz = new StringBuilder();
        dotaz.append("DELETE FROM ");
        dotaz.append(tabulka);
        return this;
    }
    // Metóda na pridanie podmienky WHERE do dotazu
    public KlientDao where(String podmienka){
        dotaz.append(" WHERE ");
        dotaz.append(podmienka);
        return this;
    }
    // Metóda na pridanie tabuľky do FROM časti dotazu
    public KlientDao from(String tabulka){
        dotaz.append(" FROM ");
        dotaz.append(tabulka);
        return this;
    }
    // Metóda na vytvorenie UPDATE dotazu UPDATE tabuľka SET...
    public KlientDao update(String tabulka){
        dotaz = new StringBuilder();
        dotaz.append("UPDATE ");
        dotaz.append(tabulka);
        dotaz.append(" SET ");
        return this;
    }
    // Metóda na pridanie stĺpcov a hodnôt SET stlpec = ?, stlpec2 = ?...
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
    // Metóda na vytvorenie INSERT INTO tabuľka
    public KlientDao insert(String tabulka){
        dotaz = new StringBuilder();
        dotaz.append("INSERT INTO ");
        dotaz.append(tabulka);
        return this;
    }
    // Metóda, ktorá pridá názvy stĺpcov do INSERT dotazu VALUES (?, ?, ?...)
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
    // Metóda select na vytvorenie SELECT dotazu SELECT stlpec1, stlpec2... alebo SELECT *
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
    // Metóda na získanie vytvoreného SQL dotazu ako reťazca, teda výsledný SQL dotaz
    public String getDotaz(){
        return dotaz.toString();
    }
}
