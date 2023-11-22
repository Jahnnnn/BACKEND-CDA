package com.cda.services.Programa;

import com.cda.dto.ProgramaDto;
import com.cda.models.Programa;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ProgramaService {

    List<ProgramaDto> GetAll();

    Programa GetById(String id) throws ExecutionException, InterruptedException;

    Boolean Add(Programa programa);

    Boolean Update(String id, Programa programa);

    Boolean Delete(String id);
}
