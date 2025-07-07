package programmer.zaman.now.maven.model;

import jakarta.persistence.*;

@Entity
@Table(name = "calon")
public class Calon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nama;

    @Column(name = "visi_misi", length = 1000)
    private String visiMisi;

    private String foto;

    // === Getter & Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getVisiMisi() {
        return visiMisi;
    }

    public void setVisiMisi(String visiMisi) {
        this.visiMisi = visiMisi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
