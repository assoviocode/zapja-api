create table usuario (
  	id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL UNIQUE,
	login varchar(255) NOT NULL UNIQUE,
	senha VARCHAR(255) NOT NULL,
	role VARCHAR(255) NOT NULL,

	cliente_id BIGINT UNSIGNED NOT NULL,
	FOREIGN KEY (cliente_id) REFERENCES cliente(id),

	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  	deleted_at TIMESTAMP
);