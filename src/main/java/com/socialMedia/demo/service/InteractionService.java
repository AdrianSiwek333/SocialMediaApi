package com.socialMedia.demo.service;

import com.socialMedia.demo.model.Interaction;
import com.socialMedia.demo.repository.InteractionRepository;
import org.springframework.stereotype.Service;

@Service
public class InteractionService {

    private final InteractionRepository interactionRepository;

    public InteractionService(InteractionRepository interactionRepository) {
        this.interactionRepository = interactionRepository;
    }

    public void addInteraction(Interaction interaction) {
        interactionRepository.save(interaction);
    }

    public void removeInteraction(Interaction interaction) {
        interactionRepository.delete(interaction);
    }
}
