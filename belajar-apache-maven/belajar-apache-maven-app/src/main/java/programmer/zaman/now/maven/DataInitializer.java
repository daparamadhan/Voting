package programmer.zaman.now.maven;

import jakarta.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import programmer.zaman.now.maven.model.Pemilih;
import programmer.zaman.now.maven.repository.PemilihRepository;

import java.io.InputStream;

@Component
public class DataInitializer {

    @Autowired
    private PemilihRepository pemilihRepository;

    @PostConstruct
    public void init() {
        try {
            if (pemilihRepository.count() > 0) return;

            InputStream inputStream = new ClassPathResource("Absensi_Angkatan_2024_Teknik_Informatika.xlsx").getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            int counter = 1;
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                Cell npmCell = row.getCell(1);   // Kolom B (NPM)
                Cell namaCell = row.getCell(2);  // Kolom C (NAMA)

                if (npmCell == null || namaCell == null) continue;

                String npm = getCellValueAsString(npmCell);
                String nama = getCellValueAsString(namaCell);

                if (npm.isEmpty() || nama.isEmpty()) continue;

                Pemilih pemilih = new Pemilih();
                pemilih.setNpm(npm);
                pemilih.setNama(nama);
                pemilih.setPassword("pw" + String.format("%03d", counter));
                pemilih.setSudahMemilih(false);

                pemilihRepository.save(pemilih);
                counter++;
            }

            workbook.close();
            System.out.println("✅ Data pemilih berhasil diimpor dari Excel.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> Boolean.toString(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }
}
