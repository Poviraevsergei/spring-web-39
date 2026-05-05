package com.tms.services;

import com.tms.exceptions.FileException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileService {

    private final Path ROOT_FILE_PATH = Paths.get("data");

    public void upload(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), ROOT_FILE_PATH.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Resource getFileByFilename(String filename) throws FileNotFoundException, FileException {
        Path filePath = ROOT_FILE_PATH.resolve(filename);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()){
                throw new FileNotFoundException(filename);
            }
            if (resource.isReadable()){
                return resource;
            }
            throw new FileException();
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<String> getListOfFilenames() throws IOException {
        return Files.walk(ROOT_FILE_PATH)
                .filter(path -> !path.equals(ROOT_FILE_PATH))
                .map(Path::toString)
                .map(path -> path.replaceAll("data\\\\",""))
                .toList();
    }

    public boolean deleteFileByFilename(String filename) throws FileNotFoundException {
        Path filePath = ROOT_FILE_PATH.resolve(filename);

        File file = new File(filePath.toUri());
        if (file.exists()){
            return file.delete();
        }
        throw new FileNotFoundException();
    }
}
