package cl.duoc.ms_penalizaciones;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableFeignClients
public class MsPenalizacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsPenalizacionesApplication.class, args);
	}

}
