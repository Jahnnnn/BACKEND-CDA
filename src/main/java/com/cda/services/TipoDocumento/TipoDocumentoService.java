package com.cda.services.TipoDocumento;

import com.cda.dto.TipoDocumentoDto;
import com.cda.models.TipoDocumento;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface TipoDocumentoService {

    List<TipoDocumentoDto> GetAll();

    TipoDocumento GetById(String id) throws ExecutionException, InterruptedException;

    Boolean Add(TipoDocumento tipoDocumento);

    Boolean Update(String id, TipoDocumento tipoDocumento);

    Boolean Delete(String id);
}
