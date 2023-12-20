CREATE TABLE contato_campo_customizado (
	id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	valor VARCHAR(255) NOT NULL,
	contato_id BIGINT UNSIGNED NOT NULL,
	campo_customizado_id BIGINT UNSIGNED NOT NULL,

  	deleted_at TIMESTAMP,
	
  	FOREIGN KEY (contato_id) REFERENCES contato(id),
	FOREIGN KEY (campo_customizado_id) REFERENCES campo_customizado(id)
);