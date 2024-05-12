package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.model.FilePath;
import hse.coursework.socialnetworkthoughts.model.Id;
import hse.coursework.socialnetworkthoughts.repository.ProfilesPicturesRepository;
import hse.coursework.socialnetworkthoughts.service.api.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileFileService implements IFileService {

    private final ProfilesPicturesRepository repository;

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
    public FilePath findPathsByProfileId(UUID profileId) {
        return repository.findPathsByProfileId(profileId);
    }
}
