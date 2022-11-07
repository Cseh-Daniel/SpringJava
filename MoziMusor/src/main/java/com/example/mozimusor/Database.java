package com.example.mozimusor;

public class Database {
    private String filmcim;
    private String szarmazas;
    private String mufaj;
    private String mozinev;
    private String cim;

    public Database() {
    }

    public Database(String filmcim, String szarmazas, String mufaj, String mozinev, String cim) {
        this.filmcim = filmcim;
        this.szarmazas = szarmazas;
        this.mufaj = mufaj;
        this.mozinev = mozinev;
        this.cim = cim;
    }

    public String getFilmcim() {
        return filmcim;
    }

    public void setFilmcim(String filmcim) {
        this.filmcim = filmcim;
    }

    public String getSzarmazas() {
        return szarmazas;
    }

    public void setSzarmazas(String szarmazas) {
        this.szarmazas = szarmazas;
    }

    public String getMufaj() {
        return mufaj;
    }

    public void setMufaj(String mufaj) {
        this.mufaj = mufaj;
    }

    public String getMozinev() {
        return mozinev;
    }

    public void setMozinev(String mozinev) {
        this.mozinev = mozinev;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }
}
