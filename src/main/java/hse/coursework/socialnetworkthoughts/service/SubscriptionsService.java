package hse.coursework.socialnetworkthoughts.service;

import hse.coursework.socialnetworkthoughts.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionsService {

    private final SubscriptionRepository subscriptionRepository;

    public List<UUID> findSubscriptionsByProfileId(UUID profileId) {
        return subscriptionRepository.findSubscriptionsByProfileId(profileId);
    }

    public List<UUID> findSubscribersByProfileId(UUID profileId) {
        return subscriptionRepository.findSubscribersByProfileId(profileId);
    }

    public void save(UUID id, UUID profileId) {
        subscriptionRepository.save(id, profileId);
    }

    public void deleteByProfileIdAndSubscriptionProfileId(UUID id, UUID profileId) {
        subscriptionRepository.deleteByProfileIdAndSubscriptionProfileId(id, profileId);
    }
}
