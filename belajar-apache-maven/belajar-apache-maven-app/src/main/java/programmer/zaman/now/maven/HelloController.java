package programmer.zaman.now.maven;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import programmer.zaman.now.maven.model.Pemilih;
import programmer.zaman.now.maven.repository.PemilihRepository;

@Controller
public class HelloController {

    @Autowired
    PemilihRepository pemilihRepository;

    // Landing page / Login page
    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute("judul", "Login - Pemilihan Ketua");
        model.addAttribute("deskripsi", "Gunakan NPM dan password untuk login.");
        model.addAttribute("error", null);
        return "index";
    }

    // Login logic
    @PostMapping("/")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        Pemilih pemilih = pemilihRepository.findByNpm(username);
        if (pemilih != null && pemilih.getPassword().equals(password)) {
            session.setAttribute("user", pemilih);

            // Cek role user
            if ("ADMIN".equalsIgnoreCase(pemilih.getRole())) {
                return "redirect:/admin/crud";
            } else {
                return "redirect:/voting";
            }
        }

        model.addAttribute("judul", "Login - Pemilihan Ketua");
        model.addAttribute("deskripsi", "Gunakan NPM dan password untuk login.");
        model.addAttribute("error", "Username atau password salah");
        return "index";
    }

    // Lupa password
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("judul", "Lupa Password");
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        System.out.println("Request reset password untuk: " + email);
        model.addAttribute("judul", "Lupa Password");
        model.addAttribute("message", "Jika email terdaftar, link reset password akan dikirim ke " + email);
        return "forgot-password";
    }

    // Halaman register (belum simpan ke DB)
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String nama,
                                  @RequestParam String npm,
                                  @RequestParam String username,
                                  @RequestParam String password) {
        System.out.println("Mendaftarkan: " + nama + " - " + npm);
        return "redirect:/";
    }

    // Dashboard (jika perlu)
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
