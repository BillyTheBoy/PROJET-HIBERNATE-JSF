package mappedclass;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "livre")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num_li;


    private String titre_li;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "num_th",referencedColumnName = "num_th")
    private Theme theme;

    public Livre(String titre_li, Theme fantastique) {
        this.titre_li = titre_li;
        this.theme = fantastique;
    }

    public Livre() {

    }

    public int getNum_li() {
        return num_li;
    }

    public void setNum_li(int num_li) {
        this.num_li = num_li;
    }

    public String getTitre_li() {
        return titre_li;
    }

    public void setTitre_li(String titre_li) {
        this.titre_li = titre_li;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livre livre = (Livre) o;

        return Objects.equals(titre_li, livre.titre_li)
                && Objects.equals(theme.getLib_th(), livre.theme.getLib_th());
    }

    @Override
    public int hashCode() {
        return Objects.hash(num_li, titre_li, theme);
    }

}
