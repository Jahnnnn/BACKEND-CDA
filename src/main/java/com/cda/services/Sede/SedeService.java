package com.cda.services.Sede;

import com.cda.dto.SedeDto;
import com.cda.models.Sede;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SedeService {
    List<SedeDto> GetAll();
    Sede GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(Sede sede);
    Boolean Update(String id, Sede sede);
    Boolean Delete(String id);
}
