package securitydancewear.securitydancewear;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import securitydancewear.securitydancewear.entities.Role;
import securitydancewear.securitydancewear.entities.User;
import securitydancewear.securitydancewear.repositories.RoleRepository;
import securitydancewear.securitydancewear.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SecurityDancewearApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityDancewearApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByAuthority("ADMIN").isPresent()) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));
			roleRepository.save(new Role("DANCE_TEACHER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User(1, "admin", passwordEncode.encode("password"),  roles);

			userRepository.save(admin);
		};
	}
}
