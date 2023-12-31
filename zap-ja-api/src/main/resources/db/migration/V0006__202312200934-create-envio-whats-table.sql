CREATE TABLE envio_whats (
	id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	celular_origem VARCHAR(20) NOT NULL,
	status VARCHAR(50) DEFAULT 'NOVO',
	log VARCHAR(500) DEFAULT NULL,
	servidor VARCHAR(255) DEFAULT NULL,
	data_prevista DATE NOT NULL,
	data_real DATE DEFAULT NULL,
	template_whats_id BIGINT UNSIGNED NOT NULL,
	contato_id BIGINT UNSIGNED NOT NULL,

	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  	deleted_at TIMESTAMP,
	
  	FOREIGN KEY (template_whats_id) REFERENCES template_whats(id),
	FOREIGN KEY (contato_id) REFERENCES contato(id)
);