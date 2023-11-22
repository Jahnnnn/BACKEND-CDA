package com.cda.services.Campus;

import com.cda.dto.BloqueDto;
import com.cda.dto.CampusDto;
import com.cda.dto.SedeDto;
import com.cda.models.Bloque;
import com.cda.models.Campus;
import com.cda.models.Sede;
import com.cda.services.Firebase.FirebaseInitializer;
import com.cda.utils.ObjectToMapConverter.ObjectToMapConverter;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class CampusServiceImpl implements CampusService{

    private static final Logger logger = LoggerFactory.getLogger(CampusServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="campus";

    @Override
    public List<CampusDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS CAMPUS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(campus -> {
                        final var campusDocument = campus.toObject(Campus.class);
                        return CampusDto.builder()
                                .id(campus.getId())
                                .nombreDelCampus(campusDocument.getNombreDelCampus())
                                .sede(campusDocument.getSede())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS CAMPUS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Campus GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Campus campus = null;
        if(document.exists()) {
            campus = document.toObject(Campus.class);
            logger.info("INFO: SE OBTUVO EL CAMPUS: " + campus);
            return campus;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL CAMPUS CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Campus campus) {

        Map<String, Object> campusData = new HashMap<>();
        campusData.put("nombreDelCampus", campus.getNombreDelCampus());

        if (campus.getSede() != null) {
            Map<String, Object> sedeMap = new HashMap<>();
            sedeMap.put("id", campus.getSede().getId());
            sedeMap.put("nombreSede", campus.getSede().getNombreSede());
            sedeMap.put("direccionSede", campus.getSede().getDireccionSede());
            if (campus.getSede().getBloque() != null) {
                Map<String, Object> bloqueMap = new HashMap<>();
                bloqueMap.put("id", campus.getSede().getBloque().getId());
                bloqueMap.put("numeroDeBloque", campus.getSede().getBloque().getNumeroDeBloque());
                sedeMap.put("bloque", bloqueMap);
            }
            campusData.put("sede", sedeMap);
        }

        CollectionReference numCampus = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = numCampus.document().create(campusData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO CAMPUS AGREGADO: " + campusData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO CAMPUS BLOQUE: " + campusData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Campus campus) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(campus);
            logger.info("INFO: SE ACTUALIZO EL CAMPUS CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL CAMPUS CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL CAMPUS CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL CAMPUS CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
