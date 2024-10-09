package homecode.opteamer;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Operation Team Application",
				description = "Application make for fun and try to organize operations and manipulations" +
						" in imaginary hospital",
				version = "v1",
				contact = @Contact(
						name = "DraganovTS",
						email = "Opteamer@gmail.com",
						url = ""
				),
				license = @License(
						name = "Apache 2.0",
						url = ""
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Operation Team Application REST API Documentation",
				url = "https://tze.com"
		)
)
public class OpteamerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpteamerApplication.class, args);
	}

}
