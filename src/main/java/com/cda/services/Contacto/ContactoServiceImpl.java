package com.cda.services.Contacto;

import com.cda.dto.BloqueDto;
import com.cda.dto.ContactoDto;
import com.cda.models.Bloque;
import com.cda.models.Contacto;
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
public class ContactoServiceImpl implements ContactoService{

    private static final Logger logger = LoggerFactory.getLogger(ContactoServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="contacto";

    @Override
    public List<ContactoDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS CONTACTOS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(contacto -> {
                        final var contactoDocument = contacto.toObject(Contacto.class);
                        return ContactoDto.builder()
                                .id(contacto.getId())
                                .direccionResidencia(contactoDocument.getDireccionResidencia())
                                .telefonoCelular(contactoDocument.getTelefonoCelular())
                                .correoElectronico(contactoDocument.getCorreoElectronico())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS CONTACTOS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Contacto GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Contacto contacto = null;
        if(document.exists()) {
            contacto = document.toObject(Contacto.class);
            logger.info("INFO: SE OBTUVO EL CONTACTO: " + contacto);
            return contacto;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL CONTACTO CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Contacto contacto) {
        Map<String, Object> contactoData = ObjectToMapConverter.convertObjectToMap(contacto);
        CollectionReference contactos = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = contactos.document().create(contactoData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO CONTACTO AGREGADO: " + contactoData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL CONTACTO: " + contactoData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Contacto contacto) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(contacto);
            logger.info("INFO: SE ACTUALIZO EL CONTACTO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL CONTACTO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL CONTACTO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL CONTACTO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
