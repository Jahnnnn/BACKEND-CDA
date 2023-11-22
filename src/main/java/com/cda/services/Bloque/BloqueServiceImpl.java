package com.cda.services.Bloque;

import com.cda.dto.BloqueDto;
import com.cda.models.Bloque;
import com.cda.services.Firebase.FirebaseInitializer;
import com.cda.utils.ObjectToMapConverter.ObjectToMapConverter;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class BloqueServiceImpl implements BloqueService{

    private static final Logger logger = LoggerFactory.getLogger(BloqueServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="bloque";

    @Override
    public List<BloqueDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS BLOQUES");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(bloque -> {
                        final var bloqueDocument = bloque.toObject(Bloque.class);
                        return BloqueDto.builder()
                                .id(bloque.getId())
                                .numeroDeBloque(bloqueDocument.getNumeroDeBloque())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS BLOQUES");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Bloque GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Bloque bloque = null;
        if(document.exists()) {
            bloque = document.toObject(Bloque.class);
            logger.info("INFO: SE OBTUVO EL BLOQUE: " + bloque);
            return bloque;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL BLOQUE CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Bloque bloque) {
        Map<String, Object> bloqueData = ObjectToMapConverter.convertObjectToMap(bloque);
        CollectionReference bloques = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = bloques.document().create(bloqueData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO BLOQUE AGREGADO: " + bloqueData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL BLOQUE: " + bloqueData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Bloque bloque) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(bloque);
            logger.info("INFO: SE ACTUALIZO EL BLOQUE CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL BLOQUE CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL BLOQUE CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL BLOQUE CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

}
