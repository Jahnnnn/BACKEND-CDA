package com.cda.services.Persona;

import com.cda.dto.PersonaDto;
import com.cda.models.Persona;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PersonaService {

    List<PersonaDto> GetAll();

    Persona GetById(String id) throws ExecutionException, InterruptedException;

    Boolean Add(Persona persona);

    Boolean Update(String id, Persona persona);

    Boolean Delete(String id);
}
