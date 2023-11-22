package com.cda.services.PermisoModulo;

import com.cda.dto.BloqueDto;
import com.cda.dto.PermisoModuloDto;
import com.cda.models.Bloque;
import com.cda.models.PermisoModulo;
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
public class PermisoModuloServiceImpl implements PermisoModuloService{

    private static final Logger logger = LoggerFactory.getLogger(PermisoModuloServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="permisoModulo";

    @Override
    public List<PermisoModuloDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS PERMISOS DE LOS MODULOS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(permisoModulo -> {
                        final var permisoModuloDocument = permisoModulo.toObject(PermisoModulo.class);
                        return PermisoModuloDto.builder()
                                .id(permisoModulo.getId())
                                .nombrePermiso(permisoModuloDocument.getNombrePermiso())
                                .modulo(permisoModuloDocument.getModulo())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS PERMISOS DE LOS MODULOS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public PermisoModulo GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        PermisoModulo permisoModulo = null;
        if(document.exists()) {
            permisoModulo = document.toObject(PermisoModulo.class);
            logger.info("INFO: SE OBTUVO EL PERMISO DEL MODULO: " + permisoModulo);
            return permisoModulo;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL PERMISO DEL MODULO CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(PermisoModulo permisoModulo) {
        Map<String, Object> permisoModuloData = new HashMap<>();
        permisoModuloData.put("nombrePermiso", permisoModulo.getNombrePermiso());

        if(permisoModulo.getModulo() != null){
            Map<String, Object> moduloMap = new HashMap<>();
            moduloMap.put("id", permisoModulo.getModulo().getId());
            moduloMap.put("nombreModulo", permisoModulo.getModulo().getNombreModulo());
            moduloMap.put("descripcionModulo", permisoModulo.getModulo().getDescripcionModulo());
            permisoModuloData.put("modulo", moduloMap);
        }

        CollectionReference permisosModulo = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = permisosModulo.document().create(permisoModuloData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO PERMISO AL MODULO AGREGADO: " + permisoModuloData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL PERMISO AL MODULO: " + permisoModuloData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, PermisoModulo permisoModulo) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(permisoModulo);
            logger.info("INFO: SE ACTUALIZO EL PERMISO DEL MODULO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL PERMISO DEL MODULO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL PERMISO DEL MODULO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL PERMISO DEL MODULO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
