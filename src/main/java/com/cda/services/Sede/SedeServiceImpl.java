package com.cda.services.Sede;

import com.cda.dto.BloqueDto;
import com.cda.dto.SedeDto;
import com.cda.models.Bloque;
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
public class SedeServiceImpl implements SedeService{

    private static final Logger logger = LoggerFactory.getLogger(SedeServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="sede";

    @Override
    public List<SedeDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODAS LAS SEDES");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(sede -> {
                        final var sedeDocument = sede.toObject(Sede.class);
                        return SedeDto.builder()
                                .id(sede.getId())
                                .nombreSede(sedeDocument.getNombreSede())
                                .direccionSede(sedeDocument.getDireccionSede())
                                .bloque(new BloqueDto(sedeDocument.getBloque().getId(),sedeDocument.getBloque().getNumeroDeBloque()))
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODAS LAS SEDES");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Sede GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Sede sede = null;
        if(document.exists()) {
            sede = document.toObject(Sede.class);
            logger.info("INFO: SE OBTUVO LA SEDE: " + sede);
            return sede;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER LA SEDE CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Sede sede) {

        Map<String, Object> sedeData = new HashMap<>();
        sedeData.put("nombreSede", sede.getNombreSede());
        sedeData.put("direccionSede", sede.getDireccionSede());

        if (sede.getBloque() != null) {
            Map<String, Object> bloqueMap = new HashMap<>();
            bloqueMap.put("id", sede.getBloque().getId());
            bloqueMap.put("numeroDeBloque", sede.getBloque().getNumeroDeBloque());
            sedeData.put("bloque", bloqueMap);
        }

        CollectionReference sedes = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = sedes.document().create(sedeData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVA SEDE AGREGADA: " + sedeData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR LA SEDE: " + sedeData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Sede sede) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(sede);
            logger.info("INFO: SE ACTUALIZO LA SEDE CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR LA SEDE CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO LA SEDE CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR LA SEDE CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
