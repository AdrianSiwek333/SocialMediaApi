package com.socialMedia.demo.repository;

import com.socialMedia.demo.model.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteractionRepository extends JpaRepository<Interaction, Long> {

}
