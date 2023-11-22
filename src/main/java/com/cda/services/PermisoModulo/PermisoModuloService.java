package com.cda.services.PermisoModulo;

import com.cda.dto.PermisoModuloDto;
import com.cda.models.PermisoModulo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PermisoModuloService {
    List<PermisoModuloDto> GetAll();
    PermisoModulo GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(PermisoModulo permisoModulo);
    Boolean Update(String id, PermisoModulo permisoModulo);
    Boolean Delete(String id);
}
