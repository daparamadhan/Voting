package programmer.zaman.now.maven.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import programmer.zaman.now.maven.model.Suara;
import programmer.zaman.now.maven.model.Calon;

import java.util.List;

public interface SuaraRepository extends JpaRepository<Suara, Long> {

    // Hitung jumlah suara berdasarkan objek Calon
    long countByCalon(Calon calon);

    // Hitung jumlah suara berdasarkan ID calon
    long countByCalonId(Long calonId);

    // Ambil semua suara, diurutkan berdasarkan waktu terbaru (jika ada field createdAt)
    List<Suara> findAllByOrderByIdDesc();
}
