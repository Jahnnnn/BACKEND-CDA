package com.cda.services.Modulo;

import com.cda.dto.ModuloDto;
import com.cda.models.Modulo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ModuloService {
    List<ModuloDto> GetAll();
    Modulo GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(Modulo modulo);
    Boolean Update(String id, Modulo modulo);
    Boolean Delete(String id);
}
