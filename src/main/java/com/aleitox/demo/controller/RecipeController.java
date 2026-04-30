package com.aleitox.demo.controller;

import com.aleitox.demo.dto.RecipeIngredientRequestDto;
import com.aleitox.demo.dto.RecipeIngredientResponseDto;
import com.aleitox.demo.dto.RecipeRequestDto;
import com.aleitox.demo.dto.RecipeResponseDto;
import com.aleitox.demo.dto.RecipeStepRequestDto;
import com.aleitox.demo.dto.RecipeStepResponseDto;
import com.aleitox.demo.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDto> create(@Valid @RequestBody RecipeRequestDto request) {
        RecipeResponseDto created = recipeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<RecipeResponseDto>> getAll() {
        return ResponseEntity.ok(recipeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(recipeService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDto> update(@PathVariable Integer id,
                                                    @Valid @RequestBody RecipeRequestDto request) {
        return ResponseEntity.ok(recipeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{recipeId}/ingredients")
    public ResponseEntity<RecipeIngredientResponseDto> addIngredient(@PathVariable Integer recipeId,
                                                                     @Valid @RequestBody RecipeIngredientRequestDto request) {
        RecipeIngredientResponseDto created = recipeService.addIngredient(recipeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{recipeId}/ingredients/{recipeIngredientId}")
    public ResponseEntity<RecipeIngredientResponseDto> updateIngredient(@PathVariable Integer recipeId,
                                                                        @PathVariable Integer recipeIngredientId,
                                                                        @Valid @RequestBody RecipeIngredientRequestDto request) {
        return ResponseEntity.ok(recipeService.updateIngredient(recipeId, recipeIngredientId, request));
    }

    @DeleteMapping("/{recipeId}/ingredients/{recipeIngredientId}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Integer recipeId,
                                                 @PathVariable Integer recipeIngredientId) {
        recipeService.deleteIngredient(recipeId, recipeIngredientId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{recipeId}/steps")
    public ResponseEntity<RecipeStepResponseDto> addStep(@PathVariable Integer recipeId,
                                                         @Valid @RequestBody RecipeStepRequestDto request) {
        RecipeStepResponseDto created = recipeService.addStep(recipeId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{recipeId}/steps/{recipeStepId}")
    public ResponseEntity<RecipeStepResponseDto> updateStep(@PathVariable Integer recipeId,
                                                            @PathVariable Integer recipeStepId,
                                                            @Valid @RequestBody RecipeStepRequestDto request) {
        return ResponseEntity.ok(recipeService.updateStep(recipeId, recipeStepId, request));
    }

    @DeleteMapping("/{recipeId}/steps/{recipeStepId}")
    public ResponseEntity<Void> deleteStep(@PathVariable Integer recipeId,
                                           @PathVariable Integer recipeStepId) {
        recipeService.deleteStep(recipeId, recipeStepId);
        return ResponseEntity.noContent().build();
    }
}
