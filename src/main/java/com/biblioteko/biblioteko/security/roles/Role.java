package com.biblioteko.biblioteko.security.roles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private RoleEnum name;

	public Role() {

	}

	public Role(RoleEnum name) {
		this.name = name;
	}

}
