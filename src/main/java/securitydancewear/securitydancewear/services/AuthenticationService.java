package securitydancewear.securitydancewear.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import securitydancewear.securitydancewear.entities.Role;
import securitydancewear.securitydancewear.entities.User;
import securitydancewear.securitydancewear.payload.LoginResponseDTO;
import securitydancewear.securitydancewear.repositories.RoleRepository;
import securitydancewear.securitydancewear.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;


@Service
@Transactional
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public User registerUser(String username, String password, String roleAuthority) {

        String encodedPassword = passwordEncoder.encode(password);


        Role userRole = roleRepository.findByAuthority(roleAuthority)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role specified: " + roleAuthority));


        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);


        logger.info("User registered: {}", username);

        return userRepository.save(new User(0, username, encodedPassword,  authorities));
    }
    public LoginResponseDTO loginUser(String username, String password) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = jwtService.generateJwt(auth);
            logger.info("User logged in: {}", username);
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);
        } catch (AuthenticationException e) {
            logger.error("Login failed for user: " + username, e);
            return new LoginResponseDTO(null, "");
        }
    }

}
