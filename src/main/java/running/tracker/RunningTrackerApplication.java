package running.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "running.tracker")
public class RunningTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RunningTrackerApplication.class, args);
	}
}