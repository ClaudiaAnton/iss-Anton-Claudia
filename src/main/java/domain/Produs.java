package domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "Produs" )
public class Produs {
    private String denumire;
    private double pret;
    private long cantitate;

    public Produs(String nume, double pret, long cantitate) {
        this.denumire = nume;
        this.pret = pret;
        this.cantitate = cantitate;
    }

    public Produs() {
    }

    @Id
    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public long getCantitate() {
        return cantitate;
    }

    public void setCantitate(long cantitate) {
        this.cantitate = cantitate;
    }
}
