package bg.softuni.bikeshopapp.service.schedulers;

import bg.softuni.bikeshopapp.service.VerificationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VerifyTokenCleanUp {

    private final VerificationService verificationService;

    public VerifyTokenCleanUp(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @Scheduled(cron = "* 0 0 * * *")
    public void cleanUp() {
        verificationService.cleanUp();
    }

}
