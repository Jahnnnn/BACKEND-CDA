package com.cda.services.Cargo;

import com.cda.dto.CargoDto;
import com.cda.models.Cargo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CargoService {
    List<CargoDto> GetAll();
    Cargo GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(Cargo cargo);
    Boolean Update(String id, Cargo cargo);
    Boolean Delete(String id);
}
