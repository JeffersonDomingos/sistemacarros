package com.aps.sistemacarros.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aps.sistemacarros.model.User;
import com.aps.sistemacarros.service.QRCodeService;
import com.aps.sistemacarros.service.UserService;

@RestController
@RequestMapping("/api/usuarios")
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private QRCodeService qrCodeService;

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }
    
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody User user) {
        userService.changePassword(user.getId(), user.getsenhaAntiga(), user.getnovaSenha());
        return ResponseEntity.ok("Senha alterada com sucesso!");
    }
    
    @GetMapping("/{id}/qrcode")
    public ResponseEntity<byte[]> generateUserQRCode(@PathVariable Long id) {
        User user = userService.getUserById(id);
        String userInfo = String.format("Nome: %s, Email: %s", user.getNome(), user.getEmail());

        byte[] qrCodeImage = qrCodeService.generateQRCode(userInfo, 300, 300);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCodeImage);
    }
}
