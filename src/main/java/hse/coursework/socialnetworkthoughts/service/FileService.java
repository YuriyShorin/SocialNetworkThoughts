package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.enums.ExceptionMessageEnum;
import hse.coursework.socialnetworkthoughts.enums.SeparatorEnum;
import hse.coursework.socialnetworkthoughts.exception.CommonRuntimeException;
import hse.coursework.socialnetworkthoughts.model.FilePath;
import hse.coursework.socialnetworkthoughts.model.Id;
import hse.coursework.socialnetworkthoughts.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Value("${file.storage}")
    private String storage;

    public String save(MultipartFile file, UUID postId) {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (fileExtension == null) {
            throw new CommonRuntimeException(HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessageEnum.COULD_NOT_DETERMINE_FILE_FORMAT_MESSAGE.getValue());
        }

        Path path = Path.of(storage).resolve(StringUtils.cleanPath(generateFileName(fileExtension, postId)));
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CommonRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ExceptionMessageEnum.UNEXPECTED_ERROR_MESSAGE.getValue(),
                    e.getCause());
        }

        return path.toString();
    }

    private String generateFileName(String fileExtension, UUID postId) {
        Id fileId = fileRepository.save(postId);
        String fileName = fileId.getId().toString().concat(SeparatorEnum.DOT.getValue()).concat(fileExtension);

        String path = storage.concat(SeparatorEnum.SLASH.getValue()).concat(fileName);
        fileRepository.savePath(fileId.getId(), path);

        return fileName;
    }

    public List<FilePath> findPathsByPostId(UUID postId) {
        return fileRepository.findPathsByPostId(postId);
    }

    public byte[] load(String fileName) {
        try {
            Path path = Path.of(fileName);
            System.out.println(path);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource.getContentAsByteArray();
            } else {
                throw new CommonRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ExceptionMessageEnum.UNEXPECTED_ERROR_MESSAGE.getValue());
            }
        } catch (IOException e) {
            throw new CommonRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ExceptionMessageEnum.UNEXPECTED_ERROR_MESSAGE.getValue(), e.getCause());
        }
    }
}
