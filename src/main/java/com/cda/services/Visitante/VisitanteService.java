package com.cda.services.Visitante;

import com.cda.dto.VisitanteDto;
import com.cda.models.Visitante;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface VisitanteService {

    List<VisitanteDto> GetAll();

    Visitante GetById(String id) throws ExecutionException, InterruptedException;

    Boolean Add(Visitante visitante);

    Boolean Update(String id, Visitante visitante);

    Boolean Delete(String id);
}
