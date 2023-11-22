package com.cda.services.Programa;

import com.cda.dto.ProgramaDto;
import com.cda.models.Programa;
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
public class ProgramaServiceImpl implements ProgramaService{

    private static final Logger logger = LoggerFactory.getLogger(ProgramaServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="programa";

    @Override
    public List<ProgramaDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS PROGRAMAS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(programa -> {
                        final var programaDocument = programa.toObject(Programa.class);
                        return ProgramaDto.builder()
                                .id(programa.getId())
                                .nombrePrograma(programaDocument.getNombrePrograma())
                                .campus(programaDocument.getCampus())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS PROGRAMAS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Programa GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Programa programa = null;
        if(document.exists()) {
            programa = document.toObject(Programa.class);
            logger.info("INFO: SE OBTUVO EL PROGRAMA: " + programa);
            return programa;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL PROGRAMA CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Programa programa) {
        Map<String, Object> programaData = new HashMap<>();
        programaData.put("nombrePrograma", programa.getNombrePrograma());

        if(programa.getCampus() != null){
            Map<String, Object> campusMap = new HashMap<>();
            campusMap.put("id", programa.getCampus().getId());
            campusMap.put("nombreDelCampus", programa.getCampus().getNombreDelCampus());
            if(programa.getCampus().getSede() != null){
                Map<String, Object> sedeMap = new HashMap<>();
                sedeMap.put("id", programa.getCampus().getSede().getId());
                sedeMap.put("nombreSede", programa.getCampus().getSede().getNombreSede());
                sedeMap.put("direccionSede", programa.getCampus().getSede().getDireccionSede());
                if(programa.getCampus().getSede().getBloque() != null){
                    Map<String, Object> bloqueMap = new HashMap<>();
                    bloqueMap.put("id", programa.getCampus().getSede().getBloque().getId());
                    bloqueMap.put("numeroDeBloque", programa.getCampus().getSede().getBloque().getNumeroDeBloque());
                    sedeMap.put("bloque", bloqueMap);
                }
                campusMap.put("sede", sedeMap);
            }
            programaData.put("campus", campusMap);
        }

        CollectionReference programas = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = programas.document().create(programaData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO PROGRAMA AGREGADO: " + programaData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL PROGRAMA: " + programaData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Programa programa) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(programa);
            logger.info("INFO: SE ACTUALIZO EL PROGRAMA CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL PROGRAMA CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL PROGRAMA CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL PROGRAMA CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
