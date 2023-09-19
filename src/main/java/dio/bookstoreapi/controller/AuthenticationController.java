package dio.bookstoreapi.controller;

import dio.bookstoreapi.dto.AuthenticationDTO;
import dio.bookstoreapi.dto.LoginResponseDTO;
import dio.bookstoreapi.dto.RegisterUserDTO;
import dio.bookstoreapi.model.Users;
import dio.bookstoreapi.repository.UserRepo;
import dio.bookstoreapi.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Users) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserDTO registerUserDTO){
        if (this.userRepo.findByLogin(registerUserDTO.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerUserDTO.password());
        Users user = new Users(registerUserDTO.login(), encryptedPassword, registerUserDTO.role());

        this.userRepo.save(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
        return ResponseEntity.ok(userRepo.findAll());
    }
}
