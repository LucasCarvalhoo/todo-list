package com.lucas.tarefas.controllers;

import com.lucas.tarefas.Repository.UsuarioRepository;
import com.lucas.tarefas.entities.DTO.AuthenticationDTO;
import com.lucas.tarefas.entities.DTO.LoginResponseDTO;
import com.lucas.tarefas.entities.DTO.RegisterDTO;
import com.lucas.tarefas.entities.Usuario;
import com.lucas.tarefas.exception.EmailJaCadastradoException;
import com.lucas.tarefas.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto){
        if(this.usuarioRepository.findByEmail(dto.email()) != null) {
            throw new EmailJaCadastradoException("Este email já está registrado.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.senha());
        Usuario newUser = new Usuario(dto.name(), dto.email(), encryptedPassword, dto.role());

        this.usuarioRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
