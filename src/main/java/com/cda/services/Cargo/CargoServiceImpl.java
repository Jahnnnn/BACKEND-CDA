package com.cda.services.Cargo;

import com.cda.dto.CargoDto;
import com.cda.models.Cargo;
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
public class CargoServiceImpl implements CargoService{

    private static final Logger logger = LoggerFactory.getLogger(CargoServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="cargo";

    @Override
    public List<CargoDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS CARGOS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(cargo -> {
                        final var cargoDocument = cargo.toObject(Cargo.class);
                        return CargoDto.builder()
                                .id(cargo.getId())
                                .nombreCargo(cargoDocument.getNombreCargo())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS CARGOS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Cargo GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Cargo cargo = null;
        if(document.exists()) {
            cargo = document.toObject(Cargo.class);
            logger.info("INFO: SE OBTUVO EL CARGO: " + cargo);
            return cargo;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL CARGO CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Cargo cargo) {
        Map<String, Object> cargoData = ObjectToMapConverter.convertObjectToMap(cargo);
        CollectionReference cargos = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = cargos.document().create(cargoData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO CARGO AGREGADO: " + cargoData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL CARGO: " + cargoData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Cargo cargo) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(cargo);
            logger.info("INFO: SE ACTUALIZO EL CARGO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL CARGO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL CARGO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL CARGO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
