package com.muweb.MUweb.service;

import com.muweb.MUweb.model.RolUsuario;
import com.muweb.MUweb.model.Usuario;
import com.muweb.MUweb.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con username: " + username));
    }

    @Transactional
    public Usuario crearUsuario(String username, String password, RolUsuario rol) {
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("El nombre de usuario ya existe.");
        }
        
        Usuario nuevoUsuario = Usuario.builder()
                .username(username)
                // En un escenario real de producción, este password debe ser encriptado con BCryptPasswordEncoder
                .password(password)
                .rol(rol)
                .build();
                
        return usuarioRepository.save(nuevoUsuario);
    }

    @Transactional
    public Usuario actualizarRol(Long usuarioId, RolUsuario nuevoRol) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuario.setRol(nuevoRol);
        return usuarioRepository.save(usuario);
    }
}