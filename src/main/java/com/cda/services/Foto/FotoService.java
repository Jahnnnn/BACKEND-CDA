package com.cda.services.Foto;

import com.cda.dto.FotoDto;
import com.cda.models.Foto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FotoService {
    List<FotoDto> GetAll();
    Foto GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(Foto foto);
    Boolean Update(String id, Foto foto);
    Boolean Delete(String id);
}
