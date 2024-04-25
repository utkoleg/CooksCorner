package com.example.cookscorner.config;

import com.example.cookscorner.entities.User;
import com.example.cookscorner.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscriptionTask {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * ?") // this will run at midnight every day
    public void checkSubscriptions() {
        List<User> usersToUnsubscribe = userRepository.findAllByIsSubscribedAndSubscriptionEndDateBefore(true, LocalDateTime.now());
        for (User user : usersToUnsubscribe) {
            user.setIsSubscribed(false);
            user.setSubscriptionEndDate(null);
            userRepository.save(user);
        }
    }
}
