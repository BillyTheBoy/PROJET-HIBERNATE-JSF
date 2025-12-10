package mappedclass;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "theme")
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int num_th;

    private String lib_th;

    public Theme(String lib_th) {
        this.lib_th = lib_th;
    }

    public Theme() {

    }

    public int getNum_th() {
        return num_th;
    }

    public void setNum_th(int num_th) {
        this.num_th = num_th;
    }

    public String getLib_th() {
        return lib_th;
    }

    public void setLib_th(String lib_th) {
        this.lib_th = lib_th;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return num_th == theme.num_th && Objects.equals(lib_th, theme.lib_th);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num_th, lib_th);
    }

}

