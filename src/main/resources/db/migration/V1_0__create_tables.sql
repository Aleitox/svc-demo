-- =============================
-- 1. CREACIÓN DE TABLAS
-- =============================

CREATE TABLE comida (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    calorias DECIMAL(10,2) NOT NULL,
    proteinas DECIMAL(10,2) NOT NULL,
    carbohidratos DECIMAL(10,2) NOT NULL,
    grasas DECIMAL(10,2) NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL
) ENGINE=InnoDB;

CREATE TABLE plato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL
) ENGINE=InnoDB;

CREATE TABLE plato_comida (
    id INT AUTO_INCREMENT PRIMARY KEY,
    plato_id INT NOT NULL,
    comida_id INT NOT NULL,
    cantidad DECIMAL(10,2) NOT NULL,  -- cantidad en gramos
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL,
    CONSTRAINT fk_plato_comida_plato FOREIGN KEY (plato_id) REFERENCES plato(id),
    CONSTRAINT fk_plato_comida_comida FOREIGN KEY (comida_id) REFERENCES comida(id)
) ENGINE=InnoDB;

CREATE TABLE receta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    plato_id INT NOT NULL,
    orden INT NOT NULL,              -- número de paso en la receta
    descripcion TEXT NOT NULL,
    created_at DATE NOT NULL,
    updated_at DATE NOT NULL,
    CONSTRAINT fk_receta_plato FOREIGN KEY (plato_id) REFERENCES plato(id)
) ENGINE=InnoDB;
