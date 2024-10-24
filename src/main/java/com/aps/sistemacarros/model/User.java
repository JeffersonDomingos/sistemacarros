package com.aps.sistemacarros.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome obrigatório!")
	private String nome;

	@Email(message = "Email deve ser válido!")
	private String email;

	@NotBlank(message = "Senha obrigatória!")
	@Size(min = 6, message = "A senha deve conter pelo menos 8 caracteres!")
	private String senha;

	@Transient
	private String senhaAntiga;

	@Transient
	private String novaSenha;

	public User() {
    }

	public User(Long id, @NotBlank(message = "Nome obrigatório!") String nome,
			@Email(message = "Email deve ser válido!") String email,
			@NotBlank(message = "Senha obrigatória!") @Size(min = 6, message = "A senha deve conter pelo menos 8 caracteres!") String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	// Getters e setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getsenhaAntiga() {
		return senhaAntiga;
	}

	public void setsenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String getnovaSenha() {
		return novaSenha;
	}

	public void setnovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
}
