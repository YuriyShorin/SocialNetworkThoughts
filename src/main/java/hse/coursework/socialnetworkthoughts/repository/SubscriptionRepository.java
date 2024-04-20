package hse.coursework.socialnetworkthoughts.repository;

import hse.coursework.socialnetworkthoughts.model.Subscription;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface SubscriptionRepository {

    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "profileId", column = "profile_id"),
            @Result(property = "subscriptionProfileId", column = "subscription_profile_id")
    })
    @Select("SELECT * FROM Subscriptions " +
            "WHERE profile_id = '${profileId}' " +
            "AND subscription_profile_id = '${subscriptionProfileId}';")
    Optional<Subscription> findByProfileIdAndSubscriptionProfileId(
            @Param("profileId") UUID profileId,
            @Param("subscriptionProfileId") UUID subscriptionProfileId);

    @Select("SELECT s.subscription_profile_id FROM Subscriptions s " +
            "WHERE s.profile_id = '${profileId}';")
    List<UUID> findSubscriptionsByProfileId(@Param("profileId") UUID profileId);

    @Select("SELECT s.profile_id FROM Subscriptions s " +
            "WHERE s.subscription_profile_id = '${profileId}';")
    List<UUID> findSubscribersByProfileId(@Param("profileId") UUID profileId);

    @Insert("INSERT INTO Subscriptions (profile_id, subscription_profile_id) " +
            "VALUES ('${profileId}', '${subscriptionProfileId}');")
    void save(@Param("profileId") UUID profileId, @Param("subscriptionProfileId") UUID subscriptionProfileId);

    @Delete("DELETE FROM Subscriptions " +
            "WHERE profile_id = '${profileId}' " +
            "AND subscription_profile_id = '${subscriptionProfileId}';")
    void deleteByProfileIdAndSubscriptionProfileId(
            @Param("profileId") UUID profileId,
            @Param("subscriptionProfileId") UUID subscriptionProfileId
    );
}
