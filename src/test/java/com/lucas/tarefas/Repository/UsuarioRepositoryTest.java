package com.lucas.tarefas.Repository;

import com.lucas.tarefas.entities.DTO.UsuarioDTO;
import com.lucas.tarefas.entities.Usuario;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.lucas.tarefas.entities.enums.Roles.USER;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void findByNameSuccess() {
        UsuarioDTO data = new UsuarioDTO("Lucas", "lucas@gmail.com", "12345678", USER);
        this.createUser(data);

        Optional<Usuario> result = this.usuarioRepository.findByName(data.name());

        assertThat(result.isPresent()).isTrue();

        // Valida o conteúdo do resultado
        assertThat(result.get().getName()).isEqualTo(data.name());
        assertThat(result.get().getEmail()).isEqualTo(data.email());
    }

    @Test
    void findByEmailSuccess() {
        UsuarioDTO data = new UsuarioDTO("Lucas", "lucas@gmail.com", "12345678", USER);
        this.createUser(data);

        UserDetails userDetails = this.usuarioRepository.findByEmail(data.email());

        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo("lucas@gmail.com");
        assertThat(userDetails.getPassword()).isEqualTo("12345678");
        assertThat(userDetails.isEnabled()).isTrue();
    }

    private Usuario createUser(UsuarioDTO data) {
        Usuario usuario = new Usuario();

        usuario.setName(data.name());
        usuario.setEmail(data.email());
        usuario.setSenha(data.senha());
        usuario.setRole(data.role());

        return usuarioRepository.save(usuario);
    }
}