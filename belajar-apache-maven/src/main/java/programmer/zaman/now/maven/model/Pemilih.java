package programmer.zaman.now.maven.model;

import jakarta.persistence.*;

@Entity
public class Pemilih {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String npm;
    private String password;

    private boolean sudahMemilih = false;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNpm() { return npm; }
    public void setNpm(String npm) { this.npm = npm; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isSudahMemilih() { return sudahMemilih; }
    public void setSudahMemilih(boolean sudahMemilih) { this.sudahMemilih = sudahMemilih; }
}

