package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.enums.ExceptionMessageEnum;
import hse.coursework.socialnetworkthoughts.enums.SeparatorEnum;
import hse.coursework.socialnetworkthoughts.exception.CommonRuntimeException;
import hse.coursework.socialnetworkthoughts.model.URL;
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

    public String save(MultipartFile file) {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (fileExtension == null) {
            throw new CommonRuntimeException(HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessageEnum.COULD_NOT_DETERMINE_FILE_FORMAT_MESSAGE.getValue());
        }

        Path path = Path.of(storage).resolve(StringUtils.cleanPath(generateFileName(fileExtension)));
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CommonRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ExceptionMessageEnum.UNEXPECTED_ERROR_MESSAGE.getValue(),
                    e.getCause());
        }

        return path.toString();
    }

    private static String generateFileName(String fileExtension) {
        return UUID.randomUUID().toString()
                .concat(SeparatorEnum.DOT.getValue())
                .concat(fileExtension);
    }

    public List<URL> findUrlsByPostId(UUID postId) {
        return fileRepository.findUrlsByPostId(postId);
    }

    public byte[] load(String fileName) {
        try {
            Path path = Path.of(fileName);
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
