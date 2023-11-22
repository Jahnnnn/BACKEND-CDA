package com.cda.services.Evento;

import com.cda.dto.EventoDto;
import com.cda.models.Evento;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface EventoService {
    List<EventoDto> GetAll();
    Evento GetById(String id) throws ExecutionException, InterruptedException;
    Boolean Add(Evento evento);
    Boolean Update(String id, Evento evento);
    Boolean Delete(String id);
}
