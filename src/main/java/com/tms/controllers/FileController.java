package com.tms.controllers;

import com.tms.exceptions.FileException;
import com.tms.services.FileService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> upload(@RequestParam("file-key") MultipartFile file) {
        fileService.upload(file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) throws FileException, FileNotFoundException {
        Resource resource = fileService.getFileByFilename(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @Hidden
    @GetMapping
    public ResponseEntity<List<String>> getListOfFilenames() throws IOException {
        List<String> filenames = fileService.getListOfFilenames();
        return new ResponseEntity<>(filenames, HttpStatus.OK);
    }

    @Tag(name = "Remove endpoints", description = "Эндпоинты ответственные за удаление")
    @DeleteMapping("/{filename}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable("filename") String filename) throws FileNotFoundException {
        if (fileService.deleteFileByFilename(filename)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
