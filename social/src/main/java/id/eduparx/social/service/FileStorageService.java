package id.eduparx.social.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import id.eduparx.social.exception.FileStorageException;
import id.eduparx.social.exception.InvalidFileException;
import id.eduparx.social.exception.ResourceNotFoundException;
import jakarta.annotation.PostConstruct;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;

    @Value("${file.allowed-extensions}")
    private String allowedExtensions;

    @Value("${file.max-size}")
    private long maxFileSize;

    public FileStorageService(@Value("{file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new FileStorageExeption("Tidak dapat membuat folder", e);
        }
    }

    /*
     * Menyimpan file dengan validasi tipe file dan ukuran
     */

    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidFileException("File tidak boleh kosong");
        }

        if (file.getSize() > maxFileSize) {
            throw new InvalidFileException(
                    String.format("Ukuran file melebihi ukuran batas maksimum %d bytes", maxFileSize));
        }

        // Normalisasi nama file
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = FilenameUtils.getExtension(originalFileName);

        if (!isValidExtension(extension)) {
            throw new InvalidFileException(String.format("Tipe file .%s tidak diijinkan. Tipe yang diijinkan $s",
                    extension, allowedExtensions));
        }

        // Generate nama file
        String fileName = generateUniqueFileName(originalFileName);

        try {
            // check karakter tidak valid
            if (fileName.contains("..")) {
                throw new FileStorageException("Nama file berisi karakter yang tidak valid " + fileName);

            }
            // Copy file ke target penyimpanan
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            throw new FileStorageException("Tidak dapat menyimpan " + fileName, e);
        }

    }

    // Load file dari hasil upload
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File tidak ditemukan: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("File tidak ditemukan: " + fileName, e);
        }
    }

    // hapus file
    public boolean deleteFile(String fileName){
        try {
            Path filePath= this.fileStorageLocation.resolve(fileName).normalize();
            return Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new FileStorageException("Tidak dapat menghapus file : "+ fileName, e);
        }
    }

    // cek valid ekstensi yang diijinkan
    private boolean isValidExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return false;
        }
        String[] allowedExts = allowedExtensions.split(",");
        return Arrays.stream(allowedExts).anyMatch(ext -> ext.trim().equalsIgnoreCase(extension));
    }

    // Generate nama file yang unik dengan UUID
    private String generateUniqueFileName(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        String baseName = FilenameUtils.getBaseName(originalFileName);
        String uniqueId = UUID.randomUUID().toString();
        return String.format("$s_$s.$s", baseName, uniqueId, extension);
    }

}
