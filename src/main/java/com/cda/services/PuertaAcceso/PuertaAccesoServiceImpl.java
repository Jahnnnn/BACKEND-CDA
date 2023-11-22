package com.cda.services.PuertaAcceso;

import com.cda.dto.BloqueDto;
import com.cda.dto.PuertaAccesoDto;
import com.cda.models.Bloque;
import com.cda.models.PuertaAcceso;
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
public class PuertaAccesoServiceImpl implements PuertaAccesoService{

    private static final Logger logger = LoggerFactory.getLogger(PuertaAccesoServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="puertaAcceso";

    @Override
    public List<PuertaAccesoDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODAS LAS PUERTAS DE ACCESO");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(puertaAcceso -> {
                        final var puertaAccesoDocument = puertaAcceso.toObject(PuertaAcceso.class);
                        return PuertaAccesoDto.builder()
                                .id(puertaAcceso.getId())
                                .numeroDePuerta(puertaAccesoDocument.getNumeroDePuerta())
                                .bloque(puertaAccesoDocument.getBloque())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODAS LAS PUERTAS DE ACCESO");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public PuertaAcceso GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        PuertaAcceso puertaAcceso = null;
        if(document.exists()) {
            puertaAcceso = document.toObject(PuertaAcceso.class);
            logger.info("INFO: SE OBTUVO LA PUERTA DE ACCESO: " + puertaAcceso);
            return puertaAcceso;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER LA PUERTA DE ACCESO CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(PuertaAcceso puertaAcceso) {
        Map<String, Object> puertaAccesoData = new HashMap<>();
        puertaAccesoData.put("numeroDePuerta", puertaAcceso.getNumeroDePuerta());
        if(puertaAcceso.getBloque() != null){
            Map<String, Object> bloqueMap = new HashMap<>();
            bloqueMap.put("id", puertaAcceso.getBloque().getId());
            bloqueMap.put("numeroDeBloque", puertaAcceso.getBloque().getNumeroDeBloque());
            puertaAccesoData.put("bloque", bloqueMap);
        }

        CollectionReference puertasAcceso = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = puertasAcceso.document().create(puertaAccesoData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVA PUERTA DE ACCESO AGREGADO: " + puertaAccesoData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR LA PUERTA DE ACCESO: " + puertaAccesoData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, PuertaAcceso puertaAcceso) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(puertaAcceso);
            logger.info("INFO: SE ACTUALIZO LA PUERTA DE ACCESO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR LA PUERTA DE ACCESO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO LA PUERTA DE ACCESO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR LA PUERTA DE ACCESO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
