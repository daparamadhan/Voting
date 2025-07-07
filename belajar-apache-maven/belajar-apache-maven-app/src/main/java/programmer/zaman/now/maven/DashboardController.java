package programmer.zaman.now.maven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import programmer.zaman.now.maven.model.Calon;
import programmer.zaman.now.maven.repository.CalonRepository;
import programmer.zaman.now.maven.repository.SuaraRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.*;

@Controller
@RequestMapping("/admin/crud")
public class DashboardController {

    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private CalonRepository calonRepository;

    @Autowired
    private SuaraRepository suaraRepository;

    private final Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads");

    @GetMapping
    public String viewDashboard(Model model,
                                @ModelAttribute("success") String success,
                                @ModelAttribute("error") String error) {

        List<Calon> calonList = calonRepository.findAll();
        Map<Long, Long> suaraPerCalon = new HashMap<>();

        for (Calon calon : calonList) {
            suaraPerCalon.put(calon.getId(), suaraRepository.countByCalonId(calon.getId()));
        }

        model.addAttribute("listCalon", calonList);
        model.addAttribute("calon", new Calon());
        model.addAttribute("suaraPerCalon", suaraPerCalon);
        model.addAttribute("listSuara", suaraRepository.findAll());
        model.addAttribute("success", success);
        model.addAttribute("error", error);

        return "dashboard";
    }

    @PostMapping("/save")
    public String saveCalon(@ModelAttribute Calon calon,
                            @RequestParam(value = "file", required = false) MultipartFile file,
                            RedirectAttributes redirectAttributes) {

        try {
            Calon existing = calon.getId() != null
                    ? calonRepository.findById(calon.getId()).orElse(null)
                    : null;

            if (file != null && !file.isEmpty()) {
                if (!file.getContentType().startsWith("image/")) {
                    redirectAttributes.addFlashAttribute("error", "File harus berupa gambar.");
                    return "redirect:/admin/crud";
                }

                Files.createDirectories(uploadPath);
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                file.transferTo(filePath.toFile());
                calon.setFoto(fileName);
            } else {
                calon.setFoto(existing != null ? existing.getFoto() :
                        (calon.getFoto() != null && !calon.getFoto().isEmpty() ? calon.getFoto() : "default.png"));
            }

            calonRepository.save(calon);
            redirectAttributes.addFlashAttribute("success", "Data calon berhasil disimpan!");
        } catch (IOException e) {
            log.error("Gagal upload file", e);
            redirectAttributes.addFlashAttribute("error", "Gagal menyimpan data calon!");
        }

        return "redirect:/admin/crud";
    }

    @GetMapping("/edit/{id}")
    public String editCalon(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Calon> calonOpt = calonRepository.findById(id);
        if (!calonOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Calon tidak ditemukan!");
            return "redirect:/admin/crud";
        }

        List<Calon> calonList = calonRepository.findAll();
        Map<Long, Long> suaraPerCalon = new HashMap<>();
        for (Calon c : calonList) {
            suaraPerCalon.put(c.getId(), suaraRepository.countByCalonId(c.getId()));
        }

        model.addAttribute("calon", calonOpt.get());
        model.addAttribute("listCalon", calonList);
        model.addAttribute("suaraPerCalon", suaraPerCalon);
        model.addAttribute("listSuara", suaraRepository.findAll());

        return "dashboard";
    }

    @GetMapping("/delete/{id}")
    public String deleteCalon(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        calonRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Data calon berhasil dihapus!");
        return "redirect:/admin/crud";
    }

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path file = uploadPath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            log.error("Invalid file URL: {}", filename, e);
            return ResponseEntity.notFound().build();
        }
    }
}
