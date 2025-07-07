package programmer.zaman.now.maven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmer.zaman.now.maven.model.Pemilih;

public interface PemilihRepository extends JpaRepository<Pemilih, Long> {
    Pemilih findByNpm(String npm);
}
