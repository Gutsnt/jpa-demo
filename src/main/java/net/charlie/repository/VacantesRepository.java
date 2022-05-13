package net.charlie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.charlie.model.Vacante;

public interface VacantesRepository extends JpaRepository<Vacante, Integer> {

}
