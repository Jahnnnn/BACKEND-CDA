package com.cda.services.Carnet;

import com.cda.dto.CarnetDto;
import com.cda.models.Carnet;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CarnetService {

    List<CarnetDto> GetAll();

    Carnet GetById(String id) throws ExecutionException, InterruptedException;

    Boolean Add(Carnet carnet);

    Boolean Update(String id, Carnet carnet);

    Boolean Delete(String id);
}
