package alif.com.mainproject.service;

import alif.com.mainproject.repository.TokenBlockListRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@EnableScheduling
@EnableAsync
public class SchedulerService {
    private final TokenBlockListRepository tokenBlockListRepository;

    public SchedulerService(TokenBlockListRepository tokenBlockListRepository) {
        this.tokenBlockListRepository = tokenBlockListRepository;
    }

    @Async
    @Scheduled(cron = "0  0 * * * *" )
    public void deleteTokenWithScheduled(){
        tokenBlockListRepository.deleteAllByExpireDateIsBefore(new Date());
    }
}
