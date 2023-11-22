package com.cda.services.Modulo;

import com.cda.dto.BloqueDto;
import com.cda.dto.ModuloDto;
import com.cda.models.Bloque;
import com.cda.models.Modulo;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ModuloServiceImpl implements ModuloService{

    private static final Logger logger = LoggerFactory.getLogger(ModuloServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="modulo";

    @Override
    public List<ModuloDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS MODULOS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(modulo -> {
                        final var moduloDocument = modulo.toObject(Modulo.class);
                        return ModuloDto.builder()
                                .id(modulo.getId())
                                .nombreModulo(moduloDocument.getNombreModulo())
                                .descripcionModulo(moduloDocument.getDescripcionModulo())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS MODULOS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Modulo GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Modulo modulo = null;
        if(document.exists()) {
            modulo = document.toObject(Modulo.class);
            logger.info("INFO: SE OBTUVO EL MODULO: " + modulo);
            return modulo;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL MODULO CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Modulo modulo) {
        Map<String, Object> moduloData = ObjectToMapConverter.convertObjectToMap(modulo);
        CollectionReference modulos = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = modulos.document().create(moduloData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO MODULO AGREGADO: " + moduloData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL MODULO: " + moduloData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Modulo modulo) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(modulo);
            logger.info("INFO: SE ACTUALIZO EL MODULO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL MODULO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL MODULO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL MODULO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
