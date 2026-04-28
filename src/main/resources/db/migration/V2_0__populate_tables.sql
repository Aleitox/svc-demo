-- =============================
-- 2. INSERCIÓN DE DATOS
-- =============================

-- 2.1 Poblamos la tabla 'comida' con ingredientes básicos y macros aproximados.
--     Ajusta los valores de calorías, proteínas, carbohidratos y grasas según tus necesidades.
INSERT INTO comida (
    nombre, calorias, proteinas, carbohidratos, grasas, created_at, updated_at
) VALUES
    ('Pescado Blanco', 110.00, 24.00, 0.00, 1.00, '2025-04-06', '2025-04-06'),
    ('Cebolla', 40.00, 1.00, 10.00, 0.00, '2025-04-06', '2025-04-06'),
    ('Limón', 29.00, 1.10, 9.30, 0.30, '2025-04-06', '2025-04-06'),
    ('Ají', 40.00, 1.50, 8.80, 0.40, '2025-04-06', '2025-04-06'),
    ('Cilantro', 23.00, 2.10, 3.70, 0.50, '2025-04-06', '2025-04-06'),
    ('Maíz Tierno', 86.00, 3.20, 19.00, 1.20, '2025-04-06', '2025-04-06'),
    ('Maíz Tostado', 100.00, 4.00, 18.00, 2.00, '2025-04-06', '2025-04-06'),
    ('Camote', 86.00, 1.60, 20.00, 0.10, '2025-04-06', '2025-04-06');

-- 2.2 Creamos un plato para el Ceviche Peruano
INSERT INTO plato (
    nombre, descripcion, created_at, updated_at
) VALUES (
    'Ceviche Peruano',
    'Ceviche de pescado con limón, cebolla, ají y cilantro. Ideal para 6 personas como entrada.',
    '2025-04-06', '2025-04-06'
);

-- 2.3 Relacionamos el plato con sus ingredientes en 'plato_comida'
--     Ajusta las cantidades en gramos según la receta.
--     Asumimos que el plato_id = 1 (por ser el primer INSERT).
INSERT INTO plato_comida (
    plato_id, comida_id, cantidad, created_at, updated_at
) VALUES
    -- Pescado: ~800 g
    (1, 1, 800.00, '2025-04-06', '2025-04-06'),
    -- Cebolla: ~100 g (1 cebolla mediana aprox.)
    (1, 2, 100.00, '2025-04-06', '2025-04-06'),
    -- Limón: ~150 g (jugos de ~10-15 limones)
    (1, 3, 150.00, '2025-04-06', '2025-04-06'),
    -- Ají: ~10 g (1 ají picado sin semillas)
    (1, 4, 10.00, '2025-04-06', '2025-04-06'),
    -- Cilantro: ~5 g (2-3 ramas picadas)
    (1, 5, 5.00, '2025-04-06', '2025-04-06'),
    -- Maíz tierno: ~50 g (acompañamiento)
    (1, 6, 50.00, '2025-04-06', '2025-04-06'),
    -- Maíz tostado: ~30 g (acompañamiento)
    (1, 7, 30.00, '2025-04-06', '2025-04-06'),
    -- Camote: ~50 g (acompañamiento)
    (1, 8, 50.00, '2025-04-06', '2025-04-06');

-- 2.4 Agregamos los pasos de la receta en la tabla 'receta'
INSERT INTO receta (
    plato_id, orden, descripcion, created_at, updated_at
) VALUES
    (1, 1, 'Corte el pescado en un ángulo de 45 grados y colóquelo en un bowl.', '2025-04-06', '2025-04-06'),
    (1, 2, 'Poner la cebolla en remojo para restarle el amargor.', '2025-04-06', '2025-04-06'),
    (1, 3, 'Agregar el jugo de limón, sal, 3/4 de la cebolla, ají picado y cilantro.', '2025-04-06', '2025-04-06'),
    (1, 4, 'Cubrir con papel film y dejar 5-10 minutos o hasta que el pescado comience a cocinarse.', '2025-04-06', '2025-04-06'),
    (1, 5, 'Servir con guarniciones como maíz tostado, maíz cocido y camote.', '2025-04-06', '2025-04-06');
