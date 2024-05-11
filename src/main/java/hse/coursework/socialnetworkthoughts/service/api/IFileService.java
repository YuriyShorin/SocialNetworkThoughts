package hse.coursework.socialnetworkthoughts.service.api;

import hse.coursework.socialnetworkthoughts.enums.ExceptionMessageEnum;
import hse.coursework.socialnetworkthoughts.enums.SeparatorEnum;
import hse.coursework.socialnetworkthoughts.exception.CommonRuntimeException;
import hse.coursework.socialnetworkthoughts.model.Id;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public interface IFileService {

    @Transactional
    default String save(MultipartFile file, UUID postId) {
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        if (fileExtension == null) {
            throw new CommonRuntimeException(HttpStatus.BAD_REQUEST.value(),
                    ExceptionMessageEnum.COULD_NOT_DETERMINE_FILE_FORMAT_MESSAGE.getValue());
        }

        Path path = Path.of(getStorage()).resolve(StringUtils.cleanPath(generateFileName(fileExtension, postId)));
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new CommonRuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    ExceptionMessageEnum.UNEXPECTED_ERROR_MESSAGE.getValue(),
                    e.getCause());
        }

        return path.toString();
    }

    private String generateFileName(String fileExtension, UUID id) {
        Id fileId = saveFile(id);
        String fileName = fileId.getId().toString().concat(SeparatorEnum.DOT.getValue()).concat(fileExtension);

        String path = getStorage().concat(SeparatorEnum.SLASH.getValue()).concat(fileName);
        savePath(fileId.getId(), path);

        return fileName;
    }

    Id saveFile(UUID id);

    void savePath(UUID id, String path);

    String getStorage();
}
