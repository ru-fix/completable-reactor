package ru.fix.cpapsm.completable.reactor.example.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fix.cpapsm.completable.reactor.api.ProcessorDescription;

import java.util.concurrent.CompletableFuture;


/**
 * @author Kamil Asfandiyarov
 */
@ProcessorDescription(doc = {
        "Описание процессора №5",
        "Тоже многострочное",
        "Тоже довольно подробное",
        "которое выглядит, например, как tooltip"

})
public class UserLogProcessor  {
    private static final Logger log = LoggerFactory.getLogger(UserLogProcessor.class);

    public CompletableFuture<Void> logAction(Long userId, String message){
        log.info("Userlog id: {} message: {}",userId, message);
        return CompletableFuture.completedFuture(null);
    }
}
