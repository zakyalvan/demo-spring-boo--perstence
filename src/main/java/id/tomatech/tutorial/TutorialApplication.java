package id.tomatech.tutorial;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TutorialApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(TutorialApplication.class)
			.run(args);
	}
}
