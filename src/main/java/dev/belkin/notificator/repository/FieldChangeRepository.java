package dev.belkin.notificator.repository;

import dev.belkin.notificator.model.FieldChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldChangeRepository extends JpaRepository<FieldChangeEntity, Integer> {
}
