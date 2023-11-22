package com.cda.services.Carnet;

import com.cda.dto.CarnetDto;
import com.cda.models.Carnet;
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
public class CarnetServiceImpl implements CarnetService{

    private static final Logger logger = LoggerFactory.getLogger(CarnetServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="carnet";
    @Override
    public List<CarnetDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS CARNETS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(carnet -> {
                        final var carnetDocument = carnet.toObject(Carnet.class);
                        return CarnetDto.builder()
                                .id(carnet.getId())
                                .codigoUniversidad(carnetDocument.getCodigoUniversidad())
                                .serialCarnet(carnetDocument.getSerialCarnet())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS CARNETS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Carnet GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Carnet carnet = null;
        if(document.exists()) {
            carnet = document.toObject(Carnet.class);
            logger.info("INFO: SE OBTUVO EL CARNET: " + carnet);
            return carnet;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL CARNET CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Carnet carnet) {
        Map<String, Object> carnetData = ObjectToMapConverter.convertObjectToMap(carnet);
        CollectionReference carnets = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = carnets.document().create(carnetData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO BLOQUE AGREGADO: " + carnetData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL BLOQUE: " + carnetData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Carnet carnet) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(carnet);
            logger.info("INFO: SE ACTUALIZO EL CARNET CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL CARNET CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL CARNET CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL CARNET CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
