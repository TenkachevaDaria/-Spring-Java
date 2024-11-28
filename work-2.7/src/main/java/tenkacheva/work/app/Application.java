package tenkacheva.work.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.ShellApplicationRunner;
import org.springframework.shell.jline.InteractiveShellRunner;

@SpringBootApplication
public class Application implements ShellApplicationRunner {
    private final boolean isApplicationReadyListenerEnabled;

    private final ApplicationContext context;
    private final InteractiveShellRunner runner;

    public Application(
            ApplicationContext context,
            InteractiveShellRunner runner,
            @Value("${app.ready.listener.enabled}") boolean isApplicationReadyListenerEnabled) {
        this.context = context;
        this.runner = runner;
        this.isApplicationReadyListenerEnabled = isApplicationReadyListenerEnabled;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (isApplicationReadyListenerEnabled) {
            context.publishEvent(new ApplicationReadyEvent());
        }

        runner.run(args);
    }
}
