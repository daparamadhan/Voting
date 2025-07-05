package programmer.zaman.now.maven;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping("/")
    public String landingPage(Model model) {
        model.addAttribute("judul", "Welcome to My Landing Page");
        model.addAttribute("deskripsi", "This is a perfect landing page created with Spring Boot and Thymeleaf.");
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("judul", "Login Pemilihan Ketua");
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        if ("admin".equals(username) && "123".equals(password)) {
            return "redirect:/dashboard";
        }
        model.addAttribute("judul", "Login Pemilihan Ketua");
        model.addAttribute("error", "Username atau password salah");
        return "login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("judul", "Lupa Password");
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        // TODO: Implementasi kirim email reset password
        System.out.println("Request reset password untuk: " + email);

        model.addAttribute("judul", "Lupa Password");
        model.addAttribute("message", "Jika email terdaftar, link reset password akan dikirim ke " + email);
        return "forgot-password";
    }

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
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/voting")
    public String votingPage() {
    return "voting";
    }

    @PostMapping("/voting")
    public String processVoting(@RequestParam String calon) {
    System.out.println("Suara diterima untuk: " + calon);
    return "redirect:/hasil";
    }

    @GetMapping("/hasil")
    public String hasilVoting() {
    return "hasil";
    }

}
