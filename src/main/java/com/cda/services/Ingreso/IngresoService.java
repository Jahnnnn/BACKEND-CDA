package com.cda.services.Ingreso;

import com.cda.dto.IngresoDto;
import com.cda.models.Ingreso;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IngresoService {
    List<IngresoDto> GetAll();
    Ingreso GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(Ingreso ingreso);
    Boolean Update(String id, Ingreso ingreso);
    Boolean Delete(String id);
}
