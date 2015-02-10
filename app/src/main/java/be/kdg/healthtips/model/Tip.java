package be.kdg.healthtips.model;

public class Tip {
    private int nummer;
    private String titel;
    private String beschrijving;
    private String onderwerp;

    public Tip(int nummer, String titel, String beschrijving) {
        this.nummer = nummer;
        this.titel = titel;
        this.beschrijving = beschrijving;
    }

    public String getOnderwerp() {
        return onderwerp;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public int getNummer() {
        return nummer;
    }

    public String getTitel() {
        return titel;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    @Override
    public String toString() {
        return this.titel;
    }



}
