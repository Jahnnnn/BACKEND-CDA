package com.cda.services.Bloque;

import com.cda.dto.BloqueDto;
import com.cda.models.Bloque;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface BloqueService {
    List<BloqueDto> GetAll();
    Bloque GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(Bloque bloque);
    Boolean Update(String id, Bloque bloque);
    Boolean Delete(String id);
}
