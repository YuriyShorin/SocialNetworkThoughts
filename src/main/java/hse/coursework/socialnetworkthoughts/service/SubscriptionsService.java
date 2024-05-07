package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionsService {

    private final SubscriptionRepository subscriptionRepository;

    @Transactional(readOnly = true)
    public List<UUID> findSubscriptionsByProfileId(UUID profileId) {
        return subscriptionRepository.findSubscriptionsByProfileId(profileId);
    }

    @Transactional(readOnly = true)
    public List<UUID> findSubscribersByProfileId(UUID profileId) {
        return subscriptionRepository.findSubscribersByProfileId(profileId);
    }

    @Transactional
    public void save(UUID id, UUID profileId) {
        subscriptionRepository.save(id, profileId);
    }

    @Transactional
    public void deleteByProfileIdAndSubscriptionProfileId(UUID id, UUID profileId) {
        subscriptionRepository.deleteByProfileIdAndSubscriptionProfileId(id, profileId);
    }
}
