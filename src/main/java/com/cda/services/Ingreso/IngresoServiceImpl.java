package com.cda.services.Ingreso;

import com.cda.dto.IngresoDto;
import com.cda.models.Ingreso;
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
public class IngresoServiceImpl implements IngresoService{

    private static final Logger logger = LoggerFactory.getLogger(IngresoServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="ingreso";

    @Override
    public List<IngresoDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS INGRESOS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(ingreso -> {
                        final var ingresoDocument = ingreso.toObject(Ingreso.class);
                        return IngresoDto.builder()
                                .id(ingreso.getId())
                                .fecha(ingresoDocument.getFecha())
                                .persona(ingresoDocument.getPersona())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS INGRESOS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Ingreso GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Ingreso ingreso = null;
        if(document.exists()) {
            ingreso = document.toObject(Ingreso.class);
            logger.info("INFO: SE OBTUVO EL INGRESO: " + ingreso);
            return ingreso;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL INGRESO CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Ingreso ingreso) {

        Map<String, Object> ingresoData = new HashMap<>();
        ingresoData.put("fecha", ingreso.getFecha());

        if (ingreso.getPersona() != null) {
            Map<String, Object> personaMap = new HashMap<>();
            personaMap.put("id", ingreso.getPersona().getId());
            personaMap.put("primerNombre", ingreso.getPersona().getPrimerNombre());
            personaMap.put("segundoNombre", ingreso.getPersona().getSegundoNombre());
            personaMap.put("primerApellido", ingreso.getPersona().getPrimerApellido());
            personaMap.put("segundoApellido", ingreso.getPersona().getSegundoApellido());
            personaMap.put("numeroDocumento", ingreso.getPersona().getNumeroDocumento());
            personaMap.put("contrasena", ingreso.getPersona().getContrasena());
            if(ingreso.getPersona().getCarnet() != null){
                Map<String, Object> carnetMap = new HashMap<>();
                carnetMap.put("id", ingreso.getPersona().getCarnet().getId());
                carnetMap.put("codigoUniversidad", ingreso.getPersona().getCarnet().getCodigoUniversidad());
                carnetMap.put("serialCarnet", ingreso.getPersona().getCarnet().getSerialCarnet());
                personaMap.put("carnet", carnetMap);
            }
            if(ingreso.getPersona().getTipoDocumento() != null){
                Map<String, Object> tipoDocumentoMap = new HashMap<>();
                tipoDocumentoMap.put("id", ingreso.getPersona().getTipoDocumento().getId());
                tipoDocumentoMap.put("sigla", ingreso.getPersona().getTipoDocumento().getSigla());
                tipoDocumentoMap.put("descripcion", ingreso.getPersona().getTipoDocumento().getDescripcion());
                personaMap.put("tipoDocumento", tipoDocumentoMap);
            }
            if(ingreso.getPersona().getPrograma() != null){
                Map<String, Object> programaMap = new HashMap<>();
                programaMap.put("id", ingreso.getPersona().getPrograma().getId());
                programaMap.put("nombrePrograma", ingreso.getPersona().getPrograma().getNombrePrograma());
                if(ingreso.getPersona().getPrograma().getCampus() != null){
                    Map<String, Object> campusMap = new HashMap<>();
                    campusMap.put("id", ingreso.getPersona().getPrograma().getCampus().getId());
                    campusMap.put("nombreDelCampus", ingreso.getPersona().getPrograma().getCampus().getNombreDelCampus());
                    if(ingreso.getPersona().getPrograma().getCampus().getSede() != null){
                        Map<String, Object> sedeMap = new HashMap<>();
                        sedeMap.put("id", ingreso.getPersona().getPrograma().getCampus().getSede().getId());
                        sedeMap.put("nombreSede", ingreso.getPersona().getPrograma().getCampus().getSede().getNombreSede());
                        sedeMap.put("direccionSede", ingreso.getPersona().getPrograma().getCampus().getSede().getDireccionSede());
                        if(ingreso.getPersona().getPrograma().getCampus().getSede().getBloque() != null){
                            Map<String, Object> bloqueMap = new HashMap<>();
                            bloqueMap.put("id", ingreso.getPersona().getPrograma().getCampus().getSede().getBloque().getId());
                            bloqueMap.put("numeroDeBloque", ingreso.getPersona().getPrograma().getCampus().getSede().getBloque().getNumeroDeBloque());
                            sedeMap.put("bloque", bloqueMap);
                        }
                        campusMap.put("sede", sedeMap);
                    }
                    programaMap.put("campus", campusMap);
                }
                personaMap.put("programa", programaMap);
            }
            if(ingreso.getPersona().getCampus() != null){
                Map<String, Object> campusMap = new HashMap<>();
                campusMap.put("id", ingreso.getPersona().getPrograma().getCampus().getId());
                campusMap.put("nombreDelCampus", ingreso.getPersona().getPrograma().getCampus().getNombreDelCampus());
                if(ingreso.getPersona().getPrograma().getCampus().getSede() != null){
                    Map<String, Object> sedeMap = new HashMap<>();
                    sedeMap.put("id", ingreso.getPersona().getPrograma().getCampus().getSede().getId());
                    sedeMap.put("nombreSede", ingreso.getPersona().getPrograma().getCampus().getSede().getNombreSede());
                    sedeMap.put("direccionSede", ingreso.getPersona().getPrograma().getCampus().getSede().getDireccionSede());
                    if(ingreso.getPersona().getPrograma().getCampus().getSede().getBloque() != null){
                        Map<String, Object> bloqueMap = new HashMap<>();
                        bloqueMap.put("id", ingreso.getPersona().getPrograma().getCampus().getSede().getBloque().getId());
                        bloqueMap.put("numeroDeBloque", ingreso.getPersona().getPrograma().getCampus().getSede().getBloque().getNumeroDeBloque());
                        sedeMap.put("bloque", bloqueMap);
                    }
                    campusMap.put("sede", sedeMap);
                }
                personaMap.put("campus", campusMap);
            }
            if(ingreso.getPersona().getContacto() != null){
                Map<String, Object> contactoMap = new HashMap<>();
                contactoMap.put("id", ingreso.getPersona().getContacto().getId());
                contactoMap.put("direccionResidencia", ingreso.getPersona().getContacto().getDireccionResidencia());
                contactoMap.put("telefonoCelular", ingreso.getPersona().getContacto().getTelefonoCelular());
                contactoMap.put("correoElectronico", ingreso.getPersona().getContacto().getCorreoElectronico());
                personaMap.put("contacto", contactoMap);
            }
            if(ingreso.getPersona().getCargo() != null){
                Map<String, Object> cargoMap = new HashMap<>();
                cargoMap.put("id", ingreso.getPersona().getCargo().getId());
                cargoMap.put("nombreCargo", ingreso.getPersona().getCargo().getNombreCargo());
                personaMap.put("cargo", cargoMap);
            }
            if(ingreso.getPersona().getFoto() != null){
                Map<String, Object> fotoMap = new HashMap<>();
                fotoMap.put("id", ingreso.getPersona().getFoto().getId());
                fotoMap.put("foto", ingreso.getPersona().getFoto().getFoto());
                fotoMap.put("fechaDeCreacion", ingreso.getPersona().getFoto().getFechaDeCreacion());
                personaMap.put("foto", fotoMap);
            }
            if(ingreso.getPersona().getRol() != null){
                Map<String, Object> rolMap = new HashMap<>();
                rolMap.put("id", ingreso.getPersona().getRol().getId());
                rolMap.put("nombreRol", ingreso.getPersona().getRol().getNombreRol());
                if(ingreso.getPersona().getRol().getPermiso() != null){
                    Map<String, Object> permisoMap = new HashMap<>();
                    permisoMap.put("id", ingreso.getPersona().getRol().getPermiso().getId());
                    permisoMap.put("nombrePermiso", ingreso.getPersona().getRol().getPermiso().getNombrePermiso());
                    if(ingreso.getPersona().getRol().getPermiso().getModulo() != null){
                        Map<String, Object> moduloMap = new HashMap<>();
                        moduloMap.put("id", ingreso.getPersona().getRol().getPermiso().getModulo().getId());
                        moduloMap.put("nombreModulo", ingreso.getPersona().getRol().getPermiso().getModulo().getNombreModulo());
                        moduloMap.put("descripcionModulo", ingreso.getPersona().getRol().getPermiso().getModulo().getDescripcionModulo());
                        permisoMap.put("modulo", moduloMap);
                    }
                    rolMap.put("permiso", permisoMap);
                }
                personaMap.put("rol", rolMap);
            }
            ingresoData.put("persona", personaMap);
        }

        CollectionReference ingresos = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = ingresos.document().create(ingresoData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO INGRESO AGREGADO: " + ingresoData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL INGRESO: " + ingresoData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Ingreso ingreso) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(ingreso);
            logger.info("INFO: SE ACTUALIZO EL INGRESO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL INGRESO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL INGRESO CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL INGRESO CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
