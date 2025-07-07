package programmer.zaman.now.maven;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import programmer.zaman.now.maven.model.Calon;
import programmer.zaman.now.maven.model.Pemilih;
import programmer.zaman.now.maven.model.Suara;
import programmer.zaman.now.maven.repository.CalonRepository;
import programmer.zaman.now.maven.repository.PemilihRepository;
import programmer.zaman.now.maven.repository.SuaraRepository;

@Controller
public class VotingController {

    @Autowired
    private PemilihRepository pemilihRepository;

    @Autowired
    private SuaraRepository suaraRepository;

    @Autowired
    private CalonRepository calonRepository;

    // Halaman Voting
    @GetMapping("/voting")
    public String votingPage(HttpSession session, Model model) {
        Pemilih pemilih = (Pemilih) session.getAttribute("user");

        if (pemilih == null) {
            return "redirect:/";
        }

        if (pemilih.isSudahMemilih()) {
            return "redirect:/hasil";
        }

        // Kirim daftar calon ke halaman voting
        model.addAttribute("listCalon", calonRepository.findAll());

        return "voting";
    }

    // Proses Submit Voting
    @PostMapping("/voting")
    public String processVoting(@RequestParam("calonId") Long calonId, HttpSession session) {
        Pemilih pemilih = (Pemilih) session.getAttribute("user");

        if (pemilih == null || pemilih.isSudahMemilih()) {
            return "redirect:/";
        }

        Calon calon = calonRepository.findById(calonId).orElse(null);
        if (calon == null) {
            return "redirect:/voting"; // calon tidak ditemukan
        }

        // Simpan suara
        Suara suara = new Suara();
        suara.setPemilih(pemilih);
        suara.setCalon(calon);
        suaraRepository.save(suara);

        // Update status pemilih agar tidak bisa memilih lagi
        pemilih.setSudahMemilih(true);
        pemilihRepository.save(pemilih);

        return "redirect:/hasil";
    }

    // Halaman Hasil Voting
    @GetMapping("/hasil")
    public String hasilVoting(Model model) {
        // Kamu bisa tambahkan logika statistik di sini kalau perlu
        return "hasil";
    }
}
