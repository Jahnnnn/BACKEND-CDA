package com.cda.services.Campus;

import com.cda.dto.CampusDto;
import com.cda.models.Campus;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CampusService {

    List<CampusDto> GetAll();

    Campus GetById(String id) throws ExecutionException, InterruptedException;

    Boolean Add(Campus campus);

    Boolean Update(String id, Campus campus);

    Boolean Delete(String id);
}
