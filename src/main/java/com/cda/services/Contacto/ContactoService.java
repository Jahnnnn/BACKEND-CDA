package com.cda.services.Contacto;

import com.cda.dto.ContactoDto;
import com.cda.models.Contacto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ContactoService {
    List<ContactoDto> GetAll();
    Contacto GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(Contacto contacto);
    Boolean Update(String id, Contacto contacto);
    Boolean Delete(String id);
}
