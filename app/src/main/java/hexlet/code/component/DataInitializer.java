package hexlet.code.component;

import hexlet.code.model.TaskStatus;
import hexlet.code.model.User;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private TaskStatusRepository statusRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        var email = "hexlet@example.com";
        var userData = new User();
        userData.setEmail(email);
        userData.setPasswordDigest("qwerty");
        userService.createUser(userData);

        Map<String, String> defaultStatuses = new HashMap<>(Map.of(
                "Draft", "draft",
                "To review", "to_review",
                "To be fixed", "to_be_fixed",
                "To publish", "to_publish",
                "Published", "published"
        ));

        defaultStatuses.entrySet().stream()
                .filter(entry -> statusRepository.findBySlug(entry.getValue()).isEmpty()) // Проверяем, есть ли запись в базе
                .map(entry -> {
                    TaskStatus status = new TaskStatus();
                    status.setName(entry.getKey());
                    status.setSlug(entry.getValue());
                    return status;
                })
                .forEach(statusRepository::save); // Сохраняем только новые записи

    }
}
