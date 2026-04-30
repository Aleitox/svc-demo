-- =============================
-- 2. DATA POPULATION
-- =============================

-- 2.1 Populate 'ingredient' with basic ingredients and macros.
INSERT INTO ingredient (
    name, calories_per_100g, proteins_per_100g, carbohydrates_per_100g, fats_per_100g, nutrition_source
) VALUES
    ('Pescado Blanco', 110.00, 24.00, 0.00, 1.00, null),
    ('Cebolla', 40.00, 1.00, 10.00, 0.00, null),
    ('Limon', 29.00, 1.10, 9.30, 0.30, null),
    ('Aji', 40.00, 1.50, 8.80, 0.40, null),
    ('Cilantro', 23.00, 2.10, 3.70, 0.50, null),
    ('Maiz Tierno', 86.00, 3.20, 19.00, 1.20, null),
    ('Maiz Tostado', 100.00, 4.00, 18.00, 2.00, null),
    ('Camote', 86.00, 1.60, 20.00, 0.10, null);

-- 2.2 Create dish and recipe for Ceviche Peruano.
INSERT INTO dish (
    name, description
) VALUES (
    'Ceviche Peruano',
    'Ceviche de pescado con limon, cebolla, aji y cilantro. Ideal para 6 personas como entrada.'
);

INSERT INTO recipe (
    name, description
) VALUES (
    'Ceviche Peruano Base',
    'Receta base para preparar ceviche peruano.'
);

INSERT INTO dish_recipe (
    dish_id, recipe_id
) VALUES (
    1, 1
);

-- 2.3 Link ingredients to the recipe in 'recipe_component'.
INSERT INTO recipe_component (
    recipe_id, component_type, ingredient_id, child_recipe_id, quantity, unit
) VALUES
    (1, 'INGREDIENT', 1, null, 800.00, 'g'),
    (1, 'INGREDIENT', 2, null, 100.00, 'g'),
    (1, 'INGREDIENT', 3, null, 150.00, 'g'),
    (1, 'INGREDIENT', 4, null, 10.00, 'g'),
    (1, 'INGREDIENT', 5, null, 5.00, 'g'),
    (1, 'INGREDIENT', 6, null, 50.00, 'g'),
    (1, 'INGREDIENT', 7, null, 30.00, 'g'),
    (1, 'INGREDIENT', 8, null, 50.00, 'g');

-- 2.4 Add step-by-step instructions in 'recipe_step'.
INSERT INTO recipe_step (
    recipe_id, step_order, description
) VALUES
    (1, 1, 'Corte el pescado en un angulo de 45 grados y coloquelo en un bowl.'),
    (1, 2, 'Poner la cebolla en remojo para restarle el amargor.'),
    (1, 3, 'Agregar el jugo de limon, sal, 3/4 de la cebolla, aji picado y cilantro.'),
    (1, 4, 'Cubrir con papel film y dejar 5-10 minutos o hasta que el pescado comience a cocinarse.'),
    (1, 5, 'Servir con guarniciones como maiz tostado, maiz cocido y camote.');
