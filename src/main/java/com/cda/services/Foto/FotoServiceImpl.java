package com.cda.services.Foto;

import com.cda.dto.BloqueDto;
import com.cda.dto.FotoDto;
import com.cda.models.Bloque;
import com.cda.models.Foto;
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
public class FotoServiceImpl implements FotoService{

    private static final Logger logger = LoggerFactory.getLogger(FotoServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="foto";

    @Override
    public List<FotoDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODAS LAS FOTOS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(foto -> {
                        final var fotoDocument = foto.toObject(Foto.class);
                        return FotoDto.builder()
                                .id(foto.getId())
                                .foto(fotoDocument.getFoto())
                                .fechaDeCreacion(fotoDocument.getFechaDeCreacion())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODAS LAS FOTOS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Foto GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Foto foto = null;
        if(document.exists()) {
            foto = document.toObject(Foto.class);
            logger.info("INFO: SE OBTUVO LA FOTO: " + foto);
            return foto;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER LA FOTO CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Foto foto) {
        Map<String, Object> fotoData = ObjectToMapConverter.convertObjectToMap(foto);
        CollectionReference fotos = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = fotos.document().create(fotoData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO FOTO AGREGADA: " + fotoData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR LA FOTO: " + fotoData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Foto foto) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(foto);
            logger.info("INFO: SE ACTUALIZO LA FOTO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR LA FOTO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO LA FOTO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR LA FOTO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
