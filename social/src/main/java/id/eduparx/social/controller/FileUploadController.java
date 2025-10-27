package id.eduparx.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import id.eduparx.social.dto.FileUploadResponse;
import id.eduparx.social.dto.UserDto;
import id.eduparx.social.security.UserPrincipal;
import id.eduparx.social.service.FileStorageService;
import id.eduparx.social.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {
    private final FileStorageService fileStorageService;

    @Autowired
    private UserService userService;

    // Constructor
    public FileUploadController(FileStorageService fileStorageService, UserService userService) {
        this.fileStorageService = fileStorageService;
        this.userService = userService;
    }

    // upload file
    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(fileName)
                .toUriString();

        FileUploadResponse response = new FileUploadResponse(
                fileName,
                fileDownloadUri,
                file.getContentType(),
                file.getSize(),
                currentUser.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile-image")
    public ResponseEntity<UserDto> uploadProfileImage(@RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserPrincipal currentUser) {

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/files/download/")
                .path(fileName)
                .toUriString();

        UserDto updatedUser = userService.updateProfileImage(currentUser.getId(), fileDownloadUri);

        return ResponseEntity.ok(updatedUser);
    }

}
