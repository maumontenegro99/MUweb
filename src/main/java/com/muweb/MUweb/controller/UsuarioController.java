package com.muweb.MUweb.controller;

import com.muweb.MUweb.model.RolUsuario;
import com.muweb.MUweb.model.Usuario;
import com.muweb.MUweb.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestParam String username, @RequestParam String password, @RequestParam RolUsuario rol) {
        return ResponseEntity.ok(usuarioService.crearUsuario(username, password, rol));
    }

    @PutMapping("/{id}/rol")
    public ResponseEntity<Usuario> actualizarRol(@PathVariable Long id, @RequestParam RolUsuario nuevoRol) {
        return ResponseEntity.ok(usuarioService.actualizarRol(id, nuevoRol));
    }
}