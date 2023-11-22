package com.cda.services.Evento;

import com.cda.dto.BloqueDto;
import com.cda.dto.EventoDto;
import com.cda.models.Bloque;
import com.cda.models.Evento;
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
public class EventoServiceImpl implements EventoService{

    private static final Logger logger = LoggerFactory.getLogger(EventoServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="evento";

    @Override
    public List<EventoDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS EVENTOS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(evento -> {
                        final var eventoDocument = evento.toObject(Evento.class);
                        return EventoDto.builder()
                                .id(evento.getId())
                                .nombreEvento(eventoDocument.getNombreEvento())
                                .descripcionEvento(eventoDocument.getDescripcionEvento())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS EVENTOS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Evento GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Evento evento = null;
        if(document.exists()) {
            evento = document.toObject(Evento.class);
            logger.info("INFO: SE OBTUVO EL EVENTO: " + evento);
            return evento;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL EVENTO CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Evento evento) {
        Map<String, Object> eventoData = ObjectToMapConverter.convertObjectToMap(evento);
        CollectionReference eventos = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = eventos.document().create(eventoData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO EVENTO AGREGADO: " + eventoData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL EVENTO: " + eventoData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Evento evento) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(evento);
            logger.info("INFO: SE ACTUALIZO EL EVENTO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL EVENTO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL EVENTO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL EVENTO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
