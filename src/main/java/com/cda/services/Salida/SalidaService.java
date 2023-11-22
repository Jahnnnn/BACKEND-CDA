package com.cda.services.Salida;

import com.cda.dto.SalidaDto;
import com.cda.models.Salida;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface SalidaService {
    List<SalidaDto> GetAll();
    Salida GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(Salida salida);
    Boolean Update(String id, Salida salida);
    Boolean Delete(String id);
}
