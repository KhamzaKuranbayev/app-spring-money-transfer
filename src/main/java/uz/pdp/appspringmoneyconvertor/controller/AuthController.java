package uz.pdp.appspringmoneyconvertor.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appspringmoneyconvertor.dto.UserDto;
import uz.pdp.appspringmoneyconvertor.security.JwtProvider;
import uz.pdp.appspringmoneyconvertor.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final AuthService authService;
    final JwtProvider jwtProvider;
    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService,
                          JwtProvider jwtProvider,
                          PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody UserDto userDto) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getUsername(),
                            userDto.getPassword()));

            String token = jwtProvider.generateToken(userDto.getUsername());
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException badCredentialsException) {
            return ResponseEntity.status(401).body("Login or Password incorrect!");
        }
    }


}
