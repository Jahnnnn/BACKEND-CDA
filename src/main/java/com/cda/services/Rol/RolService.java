package com.cda.services.Rol;

import com.cda.dto.RolDto;
import com.cda.models.Rol;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface RolService {
    List<RolDto> GetAll();
    Rol GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(Rol rol);
    Boolean Update(String id, Rol rol);
    Boolean Delete(String id);
}
