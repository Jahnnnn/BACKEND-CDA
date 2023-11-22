package com.cda.services.TipoDocumento;

import com.cda.dto.BloqueDto;
import com.cda.dto.TipoDocumentoDto;
import com.cda.models.Bloque;
import com.cda.models.TipoDocumento;
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
public class TipoDocumentoServiceImpl implements TipoDocumentoService{

    private static final Logger logger = LoggerFactory.getLogger(TipoDocumentoServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="tipoDocumento";

    @Override
    public List<TipoDocumentoDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS TIPOS DE DOCUMENTO");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(tipoDocumento -> {
                        final var tipoDocument = tipoDocumento.toObject(TipoDocumento.class);
                        return TipoDocumentoDto.builder()
                                .id(tipoDocumento.getId())
                                .sigla(tipoDocument.getSigla())
                                .descripcion(tipoDocument.getDescripcion())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS TIPOS DE DOCUMENTO");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public TipoDocumento GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        TipoDocumento tipoDocumento = null;
        if(document.exists()) {
            tipoDocumento = document.toObject(TipoDocumento.class);
            logger.info("INFO: SE OBTUVO EL TIPO DE DOCUMENTO: " + tipoDocumento);
            return tipoDocumento;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL TIPO DE DOCUMENTO CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(TipoDocumento tipoDocumento) {
        Map<String, Object> tipoDocumentoData = ObjectToMapConverter.convertObjectToMap(tipoDocumento);
        CollectionReference tiposDocumento = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = tiposDocumento.document().create(tipoDocumentoData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO TIPO DE DOCUMENTO AGREGADO: " + tipoDocumentoData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL TIPO DE DOCUMENTO: " + tipoDocumentoData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, TipoDocumento tipoDocumento) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(tipoDocumento);
            logger.info("INFO: SE ACTUALIZO EL TIPO DE DOCUMENTO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL TIPO DE DOCUMENTO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL TIPO DE DOCUMENTO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL TIPO DE DOCUMENTO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
