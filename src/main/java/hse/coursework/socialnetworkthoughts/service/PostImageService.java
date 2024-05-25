package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.model.ImagePath;
import hse.coursework.socialnetworkthoughts.model.Id;
import hse.coursework.socialnetworkthoughts.repository.PostImagesRepository;
import hse.coursework.socialnetworkthoughts.service.api.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostImageService implements IImageService {

    private final PostImagesRepository postImagesRepository;

    @Value("${file.storage}")
    private String storage;

    @Override
    public Id saveImage(UUID id) {
        return postImagesRepository.save(id);
    }

    @Override
    public void savePath(UUID id, String path) {
        postImagesRepository.savePath(id, path);
    }

    @Override
    public String getStorage() {
        return storage;
    }

    @Transactional(readOnly = true)
    public List<ImagePath> findPathsByPostId(UUID postId) {
        return postImagesRepository.findPathsByPostId(postId);
    }
}
