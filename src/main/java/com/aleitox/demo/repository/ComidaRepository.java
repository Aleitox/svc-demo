package com.aleitox.demo.repository;

import com.aleitox.demo.entity.ComidaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComidaRepository extends JpaRepository<ComidaEntity, Integer> {
}
