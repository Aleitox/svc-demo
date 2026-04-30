-- =============================
-- FOOD RECIPE APP SCHEMA
-- MySQL
-- =============================

CREATE TABLE ingredient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    calories_per_100g DECIMAL(10,2) NOT NULL,
    proteins_per_100g DECIMAL(10,2) NOT NULL,
    carbohydrates_per_100g DECIMAL(10,2) NOT NULL,
    fats_per_100g DECIMAL(10,2) NOT NULL,
    nutrition_source VARCHAR(255) NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uq_ingredient_name UNIQUE (name),
    CONSTRAINT chk_ingredient_calories CHECK (calories_per_100g >= 0),
    CONSTRAINT chk_ingredient_proteins CHECK (proteins_per_100g >= 0),
    CONSTRAINT chk_ingredient_carbohydrates CHECK (carbohydrates_per_100g >= 0),
    CONSTRAINT chk_ingredient_fats CHECK (fats_per_100g >= 0)
);

CREATE TABLE recipe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uq_recipe_name UNIQUE (name)
);

CREATE TABLE recipe_ingredient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    recipe_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    unit VARCHAR(50) NOT NULL DEFAULT 'g',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_recipe_ingredient_recipe
        FOREIGN KEY (recipe_id) REFERENCES recipe(id),

    CONSTRAINT fk_recipe_ingredient_ingredient
        FOREIGN KEY (ingredient_id) REFERENCES ingredient(id),

    CONSTRAINT uq_recipe_ingredient
        UNIQUE (recipe_id, ingredient_id),

    CONSTRAINT chk_recipe_ingredient_quantity
        CHECK (quantity > 0)
);

CREATE TABLE recipe_step (
    id INT AUTO_INCREMENT PRIMARY KEY,
    recipe_id INT NOT NULL,
    step_order INT NOT NULL,
    description TEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_recipe_step_recipe
        FOREIGN KEY (recipe_id) REFERENCES recipe(id),

    CONSTRAINT uq_recipe_step_order
        UNIQUE (recipe_id, step_order),

    CONSTRAINT chk_recipe_step_order
        CHECK (step_order > 0)
);

CREATE TABLE dish (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT uq_dish_name UNIQUE (name)
);

CREATE TABLE dish_recipe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dish_id INT NOT NULL,
    recipe_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_dish_recipe_dish
        FOREIGN KEY (dish_id) REFERENCES dish(id),

    CONSTRAINT fk_dish_recipe_recipe
        FOREIGN KEY (recipe_id) REFERENCES recipe(id),

    CONSTRAINT uq_dish_recipe
        UNIQUE (dish_id, recipe_id)
);

CREATE TABLE meal_entry (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dish_id INT NULL,
    eaten_at DATETIME NOT NULL,
    notes TEXT,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_meal_entry_dish
        FOREIGN KEY (dish_id) REFERENCES dish(id)
);

CREATE TABLE meal_entry_recipe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    meal_entry_id INT NOT NULL,
    recipe_id INT NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    unit VARCHAR(50) NOT NULL DEFAULT 'g',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_meal_entry_recipe_meal_entry
        FOREIGN KEY (meal_entry_id) REFERENCES meal_entry(id),

    CONSTRAINT fk_meal_entry_recipe_recipe
        FOREIGN KEY (recipe_id) REFERENCES recipe(id),

    CONSTRAINT chk_meal_entry_recipe_quantity
        CHECK (quantity > 0)
);

-- =============================
-- INDEXES
-- =============================

CREATE INDEX idx_recipe_ingredient_recipe_id
    ON recipe_ingredient(recipe_id);

CREATE INDEX idx_recipe_ingredient_ingredient_id
    ON recipe_ingredient(ingredient_id);

CREATE INDEX idx_recipe_step_recipe_id
    ON recipe_step(recipe_id);

CREATE INDEX idx_dish_recipe_dish_id
    ON dish_recipe(dish_id);

CREATE INDEX idx_dish_recipe_recipe_id
    ON dish_recipe(recipe_id);

CREATE INDEX idx_meal_entry_dish_id
    ON meal_entry(dish_id);

CREATE INDEX idx_meal_entry_eaten_at
    ON meal_entry(eaten_at);

CREATE INDEX idx_meal_entry_recipe_meal_entry_id
    ON meal_entry_recipe(meal_entry_id);

CREATE INDEX idx_meal_entry_recipe_recipe_id
    ON meal_entry_recipe(recipe_id);