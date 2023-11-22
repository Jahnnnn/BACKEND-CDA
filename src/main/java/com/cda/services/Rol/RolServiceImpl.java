package com.cda.services.Rol;

import com.cda.dto.BloqueDto;
import com.cda.dto.RolDto;
import com.cda.models.Bloque;
import com.cda.models.Rol;
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
public class RolServiceImpl implements RolService{

    private static final Logger logger = LoggerFactory.getLogger(RolServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="rol";

    @Override
    public List<RolDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS ROLES");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(rol -> {
                        final var rolDocument = rol.toObject(Rol.class);
                        return RolDto.builder()
                                .id(rol.getId())
                                .nombreRol(rolDocument.getNombreRol())
                                .permiso(rolDocument.getPermiso())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS ROLES");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Rol GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Rol rol = null;
        if(document.exists()) {
            rol = document.toObject(Rol.class);
            logger.info("INFO: SE OBTUVO EL ROL: " + rol);
            return rol;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL ROL CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Rol rol) {
        Map<String, Object> rolData = new HashMap<>();
        rolData.put("nombreRol", rol.getNombreRol());

        if (rol.getPermiso() != null) {
            Map<String, Object> permisoMap = new HashMap<>();
            permisoMap.put("id", rol.getPermiso().getId());
            permisoMap.put("nombrePermiso", rol.getPermiso().getNombrePermiso());
            if (rol.getPermiso().getModulo() != null) {
                Map<String, Object> moduloMap = new HashMap<>();
                moduloMap.put("id", rol.getPermiso().getModulo().getId());
                moduloMap.put("nombreModulo", rol.getPermiso().getModulo().getNombreModulo());
                moduloMap.put("descripcionModulo", rol.getPermiso().getModulo().getDescripcionModulo());
                permisoMap.put("modulo", moduloMap);
            }
            rolData.put("permiso", permisoMap);
        }

        CollectionReference roles = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = roles.document().create(rolData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO ROL AGREGADO: " + rolData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL ROL: " + rolData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Rol rol) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(rol);
            logger.info("INFO: SE ACTUALIZO EL ROL CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL ROL CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL ROL CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL ROL CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
