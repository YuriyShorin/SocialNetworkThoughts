package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.model.ImagePath;
import hse.coursework.socialnetworkthoughts.model.Id;
import hse.coursework.socialnetworkthoughts.repository.ProfilesImagesRepository;
import hse.coursework.socialnetworkthoughts.service.api.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileImageService implements IImageService {

    private final ProfilesImagesRepository profilesImagesRepository;

    @Value("${file.storage}")
    private String storage;

    @Override
    public Id saveImage(UUID id) {
        return profilesImagesRepository.save(id);
    }

    @Override
    public void savePath(UUID id, String path) {
        profilesImagesRepository.savePath(id, path);
    }

    @Override
    public String getStorage() {
        return storage;
    }

    @Transactional(readOnly = true)
    public ImagePath findPathsByProfileId(UUID profileId) {
        return profilesImagesRepository.findPathsByProfileId(profileId);
    }
}
