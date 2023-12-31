CREATE TABLE campo_customizado (
	id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	rotulo VARCHAR(255) NOT NULL,
	ativo TINYINT(1) DEFAULT 1,
	tipo_campo_customizado_id BIGINT UNSIGNED NOT NULL,
	
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  	updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  	deleted_at TIMESTAMP,
	
	FOREIGN KEY (tipo_campo_customizado_id) REFERENCES tipo_campo_customizado(id)
);