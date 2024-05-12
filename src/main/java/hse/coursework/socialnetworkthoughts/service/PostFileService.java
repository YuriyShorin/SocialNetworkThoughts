package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.model.FilePath;
import hse.coursework.socialnetworkthoughts.model.Id;
import hse.coursework.socialnetworkthoughts.repository.FileRepository;
import hse.coursework.socialnetworkthoughts.service.api.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostFileService implements IFileService {

    private final FileRepository repository;

    @Value("${file.storage}")
    private String storage;

    @Override
    public Id saveFile(UUID id) {
        return repository.save(id);
    }

    @Override
    public void savePath(UUID id, String path) {
        repository.savePath(id, path);
    }

    @Override
    public String getStorage() {
        return storage;
    }

    @Transactional(readOnly = true)
    public List<FilePath> findPathsByPostId(UUID postId) {
        return repository.findPathsByPostId(postId);
    }
}
