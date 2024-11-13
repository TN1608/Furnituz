package java5.asm.utils;

import jakarta.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUtils {

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);


    public static String uploadFile(MultipartFile file, String dir) {
        if (!file.isEmpty()) {
            try {
                String fileName = file.getOriginalFilename();
                Path uploadPath = Paths.get(dir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                log.info("File uploaded: {}", fileName);
                return fileName;
            } catch (Exception e) {
                log.error("File upload error: {}", e.getMessage());
                return null;
            }
        }
        return null;
    }
}
