package com.aleitox.demo.service;

import com.aleitox.demo.dto.RecipeIngredientRequestDto;
import com.aleitox.demo.dto.RecipeIngredientResponseDto;
import com.aleitox.demo.dto.RecipeRequestDto;
import com.aleitox.demo.dto.RecipeResponseDto;
import com.aleitox.demo.dto.RecipeStepRequestDto;
import com.aleitox.demo.dto.RecipeStepResponseDto;
import com.aleitox.demo.entity.IngredientEntity;
import com.aleitox.demo.entity.RecipeEntity;
import com.aleitox.demo.entity.RecipeIngredientEntity;
import com.aleitox.demo.entity.RecipeStepEntity;
import com.aleitox.demo.mapper.RecipeIngredientMapper;
import com.aleitox.demo.mapper.RecipeMapper;
import com.aleitox.demo.mapper.RecipeStepMapper;
import com.aleitox.demo.repository.IngredientRepository;
import com.aleitox.demo.repository.RecipeIngredientRepository;
import com.aleitox.demo.repository.RecipeRepository;
import com.aleitox.demo.repository.RecipeStepRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipeMapper recipeMapper;
    private final RecipeIngredientMapper recipeIngredientMapper;
    private final RecipeStepMapper recipeStepMapper;

    public RecipeService(RecipeRepository recipeRepository,
                         IngredientRepository ingredientRepository,
                         RecipeIngredientRepository recipeIngredientRepository,
                         RecipeStepRepository recipeStepRepository,
                         RecipeMapper recipeMapper,
                         RecipeIngredientMapper recipeIngredientMapper,
                         RecipeStepMapper recipeStepMapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeStepRepository = recipeStepRepository;
        this.recipeMapper = recipeMapper;
        this.recipeIngredientMapper = recipeIngredientMapper;
        this.recipeStepMapper = recipeStepMapper;
    }

    @Transactional
    public RecipeResponseDto create(RecipeRequestDto request) {
        validateDuplicateIngredientIds(request.ingredients());
        validateDuplicateStepOrders(request.steps());

        LocalDateTime now = LocalDateTime.now();
        RecipeEntity recipe = recipeMapper.toEntity(recipeMapper.toDomain(request, null));
        recipe.setCreatedAt(now);
        recipe.setUpdatedAt(now);
        RecipeEntity savedRecipe = recipeRepository.save(recipe);

        List<RecipeIngredientEntity> savedIngredients = saveIngredients(savedRecipe, request.ingredients(), now);
        List<RecipeStepEntity> savedSteps = saveSteps(savedRecipe, request.steps(), now);
        return toResponse(savedRecipe, savedIngredients, savedSteps);
    }

    public List<RecipeResponseDto> getAll() {
        return recipeRepository.findAll()
                .stream()
                .map(recipe -> toResponse(
                        recipe,
                        recipeIngredientRepository.findByRecipeId(recipe.getId()),
                        recipeStepRepository.findByRecipeIdOrderByStepOrderAsc(recipe.getId())
                ))
                .toList();
    }

    public RecipeResponseDto getById(Integer id) {
        RecipeEntity recipe = findRecipeById(id);
        return toResponse(
                recipe,
                recipeIngredientRepository.findByRecipeId(id),
                recipeStepRepository.findByRecipeIdOrderByStepOrderAsc(id)
        );
    }

    @Transactional
    public RecipeResponseDto update(Integer id, RecipeRequestDto request) {
        validateDuplicateIngredientIds(request.ingredients());
        validateDuplicateStepOrders(request.steps());

        RecipeEntity existing = findRecipeById(id);
        var incoming = recipeMapper.toDomain(request, id);
        existing.setName(incoming.name());
        existing.setDescription(incoming.description());
        existing.setUpdatedAt(LocalDateTime.now());
        RecipeEntity updatedRecipe = recipeRepository.save(existing);

        recipeIngredientRepository.deleteByRecipeId(id);
        recipeStepRepository.deleteByRecipeId(id);

        LocalDateTime now = LocalDateTime.now();
        List<RecipeIngredientEntity> savedIngredients = saveIngredients(updatedRecipe, request.ingredients(), now);
        List<RecipeStepEntity> savedSteps = saveSteps(updatedRecipe, request.steps(), now);
        return toResponse(updatedRecipe, savedIngredients, savedSteps);
    }

    @Transactional
    public void delete(Integer id) {
        Objects.requireNonNull(id, "Id cannot be null");
        if (!recipeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found with id: " + id);
        }
        recipeIngredientRepository.deleteByRecipeId(id);
        recipeStepRepository.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
    }

    @Transactional
    public RecipeIngredientResponseDto addIngredient(Integer recipeId, RecipeIngredientRequestDto request) {
        RecipeEntity recipe = findRecipeById(recipeId);
        validateRecipeIngredientDoesNotExist(recipeId, request.ingredientId());
        IngredientEntity ingredient = findIngredientById(request.ingredientId());

        RecipeIngredientEntity entity = new RecipeIngredientEntity();
        entity.setRecipe(recipe);
        entity.setIngredient(ingredient);
        entity.setQuantity(request.quantity());
        entity.setUnit(request.unit());
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        RecipeIngredientEntity saved = recipeIngredientRepository.save(entity);
        touchRecipeUpdateTime(recipe);
        return recipeIngredientMapper.toResponseDto(recipeIngredientMapper.toDomain(saved));
    }

    @Transactional
    public RecipeIngredientResponseDto updateIngredient(Integer recipeId,
                                                        Integer recipeIngredientId,
                                                        RecipeIngredientRequestDto request) {
        RecipeEntity recipe = findRecipeById(recipeId);
        RecipeIngredientEntity existing = recipeIngredientRepository.findByIdAndRecipeId(recipeIngredientId, recipeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recipe ingredient not found with id: " + recipeIngredientId + " for recipe id: " + recipeId));

        if (!Objects.equals(existing.getIngredient().getId(), request.ingredientId())) {
            validateRecipeIngredientDoesNotExist(recipeId, request.ingredientId());
        }
        IngredientEntity ingredient = findIngredientById(request.ingredientId());

        existing.setIngredient(ingredient);
        existing.setQuantity(request.quantity());
        existing.setUnit(request.unit());
        existing.setUpdatedAt(LocalDateTime.now());

        RecipeIngredientEntity saved = recipeIngredientRepository.save(existing);
        touchRecipeUpdateTime(recipe);
        return recipeIngredientMapper.toResponseDto(recipeIngredientMapper.toDomain(saved));
    }

    @Transactional
    public void deleteIngredient(Integer recipeId, Integer recipeIngredientId) {
        RecipeEntity recipe = findRecipeById(recipeId);
        RecipeIngredientEntity existing = recipeIngredientRepository.findByIdAndRecipeId(recipeIngredientId, recipeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recipe ingredient not found with id: " + recipeIngredientId + " for recipe id: " + recipeId));
        recipeIngredientRepository.deleteById(Objects.requireNonNull(existing.getId(), "Recipe ingredient id cannot be null"));
        touchRecipeUpdateTime(recipe);
    }

    @Transactional
    public RecipeStepResponseDto addStep(Integer recipeId, RecipeStepRequestDto request) {
        RecipeEntity recipe = findRecipeById(recipeId);
        validateStepOrderDoesNotExist(recipeId, request.stepOrder());

        RecipeStepEntity entity = new RecipeStepEntity();
        entity.setRecipe(recipe);
        entity.setStepOrder(request.stepOrder());
        entity.setDescription(request.description());
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        RecipeStepEntity saved = recipeStepRepository.save(entity);
        touchRecipeUpdateTime(recipe);
        return recipeStepMapper.toResponseDto(recipeStepMapper.toDomain(saved));
    }

    @Transactional
    public RecipeStepResponseDto updateStep(Integer recipeId, Integer recipeStepId, RecipeStepRequestDto request) {
        RecipeEntity recipe = findRecipeById(recipeId);
        RecipeStepEntity existing = recipeStepRepository.findByIdAndRecipeId(recipeStepId, recipeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recipe step not found with id: " + recipeStepId + " for recipe id: " + recipeId));

        if (!Objects.equals(existing.getStepOrder(), request.stepOrder())
                && recipeStepRepository.existsByRecipeIdAndStepOrder(recipeId, request.stepOrder())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Recipe step order already exists for recipe id: " + recipeId + " and step order: " + request.stepOrder());
        }

        existing.setStepOrder(request.stepOrder());
        existing.setDescription(request.description());
        existing.setUpdatedAt(LocalDateTime.now());

        RecipeStepEntity saved = recipeStepRepository.save(existing);
        touchRecipeUpdateTime(recipe);
        return recipeStepMapper.toResponseDto(recipeStepMapper.toDomain(saved));
    }

    @Transactional
    public void deleteStep(Integer recipeId, Integer recipeStepId) {
        RecipeEntity recipe = findRecipeById(recipeId);
        RecipeStepEntity existing = recipeStepRepository.findByIdAndRecipeId(recipeStepId, recipeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recipe step not found with id: " + recipeStepId + " for recipe id: " + recipeId));
        recipeStepRepository.deleteById(Objects.requireNonNull(existing.getId(), "Recipe step id cannot be null"));
        touchRecipeUpdateTime(recipe);
    }

    private RecipeEntity findRecipeById(Integer id) {
        Objects.requireNonNull(id, "Id cannot be null");
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found with id: " + id));
    }

    private IngredientEntity findIngredientById(Integer id) {
        Objects.requireNonNull(id, "Id cannot be null");
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found with id: " + id));
    }

    private List<RecipeIngredientEntity> saveIngredients(RecipeEntity recipe,
                                                         List<RecipeIngredientRequestDto> ingredients,
                                                         LocalDateTime timestamp) {
        return ingredients.stream()
                .map(request -> {
                    IngredientEntity ingredient = findIngredientById(request.ingredientId());
                    RecipeIngredientEntity entity = new RecipeIngredientEntity();
                    entity.setRecipe(recipe);
                    entity.setIngredient(ingredient);
                    entity.setQuantity(request.quantity());
                    entity.setUnit(request.unit());
                    entity.setCreatedAt(timestamp);
                    entity.setUpdatedAt(timestamp);
                    return recipeIngredientRepository.save(entity);
                })
                .toList();
    }

    private List<RecipeStepEntity> saveSteps(RecipeEntity recipe,
                                             List<RecipeStepRequestDto> steps,
                                             LocalDateTime timestamp) {
        return steps.stream()
                .map(request -> {
                    RecipeStepEntity entity = new RecipeStepEntity();
                    entity.setRecipe(recipe);
                    entity.setStepOrder(request.stepOrder());
                    entity.setDescription(request.description());
                    entity.setCreatedAt(timestamp);
                    entity.setUpdatedAt(timestamp);
                    return recipeStepRepository.save(entity);
                })
                .toList();
    }

    private void validateDuplicateIngredientIds(List<RecipeIngredientRequestDto> ingredients) {
        Set<Integer> ingredientIds = new HashSet<>();
        for (RecipeIngredientRequestDto ingredient : ingredients) {
            if (!ingredientIds.add(ingredient.ingredientId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Duplicate ingredient id in recipe ingredients: " + ingredient.ingredientId());
            }
        }
    }

    private void validateDuplicateStepOrders(List<RecipeStepRequestDto> steps) {
        Set<Integer> stepOrders = new HashSet<>();
        for (RecipeStepRequestDto step : steps) {
            if (!stepOrders.add(step.stepOrder())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Duplicate step order in recipe steps: " + step.stepOrder());
            }
        }
    }

    private void validateRecipeIngredientDoesNotExist(Integer recipeId, Integer ingredientId) {
        if (recipeIngredientRepository.existsByRecipeIdAndIngredientId(recipeId, ingredientId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ingredient already linked to recipe. recipe id: " + recipeId + ", ingredient id: " + ingredientId);
        }
    }

    private void validateStepOrderDoesNotExist(Integer recipeId, Integer stepOrder) {
        if (recipeStepRepository.existsByRecipeIdAndStepOrder(recipeId, stepOrder)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Recipe step order already exists for recipe id: " + recipeId + " and step order: " + stepOrder);
        }
    }

    private void touchRecipeUpdateTime(RecipeEntity recipe) {
        recipe.setUpdatedAt(LocalDateTime.now());
        recipeRepository.save(recipe);
    }

    private RecipeResponseDto toResponse(RecipeEntity recipe,
                                         List<RecipeIngredientEntity> ingredients,
                                         List<RecipeStepEntity> steps) {
        return new RecipeResponseDto(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                ingredients.stream()
                        .map(recipeIngredientMapper::toDomain)
                        .map(recipeIngredientMapper::toResponseDto)
                        .toList(),
                steps.stream()
                        .map(recipeStepMapper::toDomain)
                        .map(recipeStepMapper::toResponseDto)
                        .toList(),
                recipe.getCreatedAt(),
                recipe.getUpdatedAt()
        );
    }
}
