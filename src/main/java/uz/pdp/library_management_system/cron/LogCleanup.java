package uz.pdp.library_management_system.cron;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@Component
@Slf4j
public class LogCleanup {
    private static final String LOG_FILE_PATH = "C:\\Abbos\\Spring Project\\Test Projects\\LibraryManagementSystem\\logs\\LibraryManagementSystem.log";

    @Scheduled(cron = "* */3 * * * * ") // har 3 minutda tozalaydi
    public void clearLogFile() {
        File file = new File(LOG_FILE_PATH);
        if (file.exists()) {
            try {
                Files.write(file.toPath(), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
                log.info("✅ Log fayli tozalandi: " + LOG_FILE_PATH);
                System.out.println("clearLogFile ishlamoqda...");
            } catch (IOException e) {
                log.error("❌ Log faylni tozalashda xatolik: " + e.getMessage());
            }
        } else {
            log.warn("⚠ Log fayl topilmadi: " + LOG_FILE_PATH);
        }
    }
}