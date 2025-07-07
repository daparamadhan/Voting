package programmer.zaman.now.maven.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "suara")
public class Suara {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pemilih_id")
    private Pemilih pemilih;

    @ManyToOne
    @JoinColumn(name = "calon_id")
    private Calon calon;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // === Getter & Setter ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pemilih getPemilih() {
        return pemilih;
    }

    public void setPemilih(Pemilih pemilih) {
        this.pemilih = pemilih;
    }

    public Calon getCalon() {
        return calon;
    }

    public void setCalon(Calon calon) {
        this.calon = calon;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
