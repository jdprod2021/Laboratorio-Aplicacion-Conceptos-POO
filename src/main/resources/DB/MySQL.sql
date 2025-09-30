-- ============ ENTIDADES ============

CREATE TABLE IF NOT EXISTS PERSONA (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombres    VARCHAR(100) NOT NULL,
  apellidos  VARCHAR(100) NOT NULL,
  email      VARCHAR(120),
  CONSTRAINT uq_persona_email UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS PROFESOR (
  id              BIGINT PRIMARY KEY,              
  tipo_contrato   VARCHAR(50) NOT NULL,
  CONSTRAINT fk_profesor_persona
    FOREIGN KEY (id) REFERENCES PERSONA(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS FACULTAD (
  id         BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre     VARCHAR(120) NOT NULL,
  decano_id  BIGINT,                                
  CONSTRAINT fk_facultad_decano
    FOREIGN KEY (decano_id) REFERENCES PERSONA(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS PROGRAMA (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  nombre      VARCHAR(120) NOT NULL,
  duracion    DOUBLE,            
  registro    DATE,
  facultad_id BIGINT NOT NULL,
  CONSTRAINT fk_programa_facultad
    FOREIGN KEY (facultad_id) REFERENCES FACULTAD(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS CURSO (
  id          INT AUTO_INCREMENT PRIMARY KEY,
  nombre      VARCHAR(120) NOT NULL,
  programa_id BIGINT NOT NULL,
  activo      BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT fk_curso_programa
    FOREIGN KEY (programa_id) REFERENCES PROGRAMA(id) ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ESTUDIANTE (
  id          BIGINT PRIMARY KEY,                  
  codigo      BIGINT,                              
  programa_id BIGINT,                             
  activo      BOOLEAN NOT NULL DEFAULT TRUE,
  promedio    DOUBLE,
  CONSTRAINT fk_estudiante_persona
    FOREIGN KEY (id) REFERENCES PERSONA(id) ON DELETE CASCADE,
  CONSTRAINT fk_estudiante_programa
    FOREIGN KEY (programa_id) REFERENCES PROGRAMA(id) ON DELETE SET NULL,
  CONSTRAINT uq_estudiante_codigo UNIQUE (codigo)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS INSCRIPCION (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  curso_id      INT NOT NULL,
  anio          INT NOT NULL,
  semestre      INT NOT NULL CHECK (semestre IN (1, 2)),
  estudiante_id BIGINT NOT NULL,
  CONSTRAINT fk_insc_curso
    FOREIGN KEY (curso_id) REFERENCES CURSO(id) ON DELETE RESTRICT,
  CONSTRAINT fk_insc_estudiante
    FOREIGN KEY (estudiante_id) REFERENCES ESTUDIANTE(id) ON DELETE CASCADE,
  CONSTRAINT uq_insc_unique UNIQUE (curso_id, anio, semestre, estudiante_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS CURSO_PROFESOR (
  profesor_id  BIGINT NOT NULL,
  curso_id     INT NOT NULL,
  anio         INT NOT NULL,
  semestre     INT NOT NULL CHECK (semestre IN (1, 2)),
  CONSTRAINT pk_cp PRIMARY KEY (profesor_id, anio, semestre, curso_id),
  CONSTRAINT fk_cp_profesor
    FOREIGN KEY (profesor_id) REFERENCES PROFESOR(id) ON DELETE RESTRICT,
  CONSTRAINT fk_cp_curso
    FOREIGN KEY (curso_id)    REFERENCES CURSO(id)    ON DELETE RESTRICT
) ENGINE=InnoDB;
