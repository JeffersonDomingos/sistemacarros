package com.aps.sistemacarros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aps.sistemacarros.model.User;
import com.aps.sistemacarros.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User registerUser(User user) {
		user.setSenha(passwordEncoder.encode(user.getSenha()));
		User savedUser = userRepository.save(user);

		emailService.sendConfirmationEmail(user.getEmail(), "Bem-vindo ao Sistema de Carros",
				"Olá " + user.getNome() + ",\n\nSeu cadastro foi realizado com sucesso!");

		return savedUser;
	}

	public User updateUser(Long id, User user) {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
		existingUser.setNome(user.getNome());
		existingUser.setEmail(user.getEmail());

		existingUser.setSenha(user.getSenha());
		return userRepository.save(existingUser);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public List<User> listUsers() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow();
	}

	public void changePassword(Long userId, String senhaAntiga, String novaSenha) {
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		if (!passwordEncoder.matches(senhaAntiga, user.getSenha())) {
			throw new RuntimeException("Senha antiga incorreta");
		}

		user.setSenha(passwordEncoder.encode(novaSenha));
		userRepository.save(user);
	}

}
