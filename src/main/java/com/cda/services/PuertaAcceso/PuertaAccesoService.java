package com.cda.services.PuertaAcceso;

import com.cda.dto.PuertaAccesoDto;
import com.cda.models.PuertaAcceso;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PuertaAccesoService {
    List<PuertaAccesoDto> GetAll();
    PuertaAcceso GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(PuertaAcceso puertaAcceso);
    Boolean Update(String id, PuertaAcceso puertaAcceso);
    Boolean Delete(String id);
}
