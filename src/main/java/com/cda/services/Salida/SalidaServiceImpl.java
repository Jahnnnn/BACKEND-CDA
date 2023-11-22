package com.cda.services.Salida;

import com.cda.dto.SalidaDto;
import com.cda.models.Salida;
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
public class SalidaServiceImpl implements SalidaService{

    private static final Logger logger = LoggerFactory.getLogger(SalidaServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="salida";

    @Override
    public List<SalidaDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODAS LAS SALIDAS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(salida -> {
                        final var salidaDocument = salida.toObject(Salida.class);
                        return SalidaDto.builder()
                                .id(salida.getId())
                                .fecha(salidaDocument.getFecha())
                                .persona(salidaDocument.getPersona())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODAS LAS SALIDAS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Salida GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Salida salida = null;
        if(document.exists()) {
            salida = document.toObject(Salida.class);
            logger.info("INFO: SE OBTUVO LA SALIDA: " + salida);
            return salida;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER LA SALIDA CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Salida salida) {

        Map<String, Object> salidaData = new HashMap<>();
        salidaData.put("fecha", salida.getFecha());

        if (salida.getPersona() != null) {
            Map<String, Object> personaMap = new HashMap<>();
            personaMap.put("id", salida.getPersona().getId());
            personaMap.put("primerNombre", salida.getPersona().getPrimerNombre());
            personaMap.put("segundoNombre", salida.getPersona().getSegundoNombre());
            personaMap.put("primerApellido", salida.getPersona().getPrimerApellido());
            personaMap.put("segundoApellido", salida.getPersona().getSegundoApellido());
            personaMap.put("numeroDocumento", salida.getPersona().getNumeroDocumento());
            personaMap.put("contrasena", salida.getPersona().getContrasena());
            if(salida.getPersona().getCarnet() != null){
                Map<String, Object> carnetMap = new HashMap<>();
                carnetMap.put("id", salida.getPersona().getCarnet().getId());
                carnetMap.put("codigoUniversidad", salida.getPersona().getCarnet().getCodigoUniversidad());
                carnetMap.put("serialCarnet", salida.getPersona().getCarnet().getSerialCarnet());
                personaMap.put("carnet", carnetMap);
            }
            if(salida.getPersona().getTipoDocumento() != null){
                Map<String, Object> tipoDocumentoMap = new HashMap<>();
                tipoDocumentoMap.put("id", salida.getPersona().getTipoDocumento().getId());
                tipoDocumentoMap.put("sigla", salida.getPersona().getTipoDocumento().getSigla());
                tipoDocumentoMap.put("descripcion", salida.getPersona().getTipoDocumento().getDescripcion());
                personaMap.put("tipoDocumento", tipoDocumentoMap);
            }
            if(salida.getPersona().getPrograma() != null){
                Map<String, Object> programaMap = new HashMap<>();
                programaMap.put("id", salida.getPersona().getPrograma().getId());
                programaMap.put("nombrePrograma", salida.getPersona().getPrograma().getNombrePrograma());
                if(salida.getPersona().getPrograma().getCampus() != null){
                    Map<String, Object> campusMap = new HashMap<>();
                    campusMap.put("id", salida.getPersona().getPrograma().getCampus().getId());
                    campusMap.put("nombreDelCampus", salida.getPersona().getPrograma().getCampus().getNombreDelCampus());
                    if(salida.getPersona().getPrograma().getCampus().getSede() != null){
                        Map<String, Object> sedeMap = new HashMap<>();
                        sedeMap.put("id", salida.getPersona().getPrograma().getCampus().getSede().getId());
                        sedeMap.put("nombreSede", salida.getPersona().getPrograma().getCampus().getSede().getNombreSede());
                        sedeMap.put("direccionSede", salida.getPersona().getPrograma().getCampus().getSede().getDireccionSede());
                        if(salida.getPersona().getPrograma().getCampus().getSede().getBloque() != null){
                            Map<String, Object> bloqueMap = new HashMap<>();
                            bloqueMap.put("id", salida.getPersona().getPrograma().getCampus().getSede().getBloque().getId());
                            bloqueMap.put("numeroDeBloque", salida.getPersona().getPrograma().getCampus().getSede().getBloque().getNumeroDeBloque());
                            sedeMap.put("bloque", bloqueMap);
                        }
                        campusMap.put("sede", sedeMap);
                    }
                    programaMap.put("campus", campusMap);
                }
                personaMap.put("programa", programaMap);
            }
            if(salida.getPersona().getCampus() != null){
                Map<String, Object> campusMap = new HashMap<>();
                campusMap.put("id", salida.getPersona().getPrograma().getCampus().getId());
                campusMap.put("nombreDelCampus", salida.getPersona().getPrograma().getCampus().getNombreDelCampus());
                if(salida.getPersona().getPrograma().getCampus().getSede() != null){
                    Map<String, Object> sedeMap = new HashMap<>();
                    sedeMap.put("id", salida.getPersona().getPrograma().getCampus().getSede().getId());
                    sedeMap.put("nombreSede", salida.getPersona().getPrograma().getCampus().getSede().getNombreSede());
                    sedeMap.put("direccionSede", salida.getPersona().getPrograma().getCampus().getSede().getDireccionSede());
                    if(salida.getPersona().getPrograma().getCampus().getSede().getBloque() != null){
                        Map<String, Object> bloqueMap = new HashMap<>();
                        bloqueMap.put("id", salida.getPersona().getPrograma().getCampus().getSede().getBloque().getId());
                        bloqueMap.put("numeroDeBloque", salida.getPersona().getPrograma().getCampus().getSede().getBloque().getNumeroDeBloque());
                        sedeMap.put("bloque", bloqueMap);
                    }
                    campusMap.put("sede", sedeMap);
                }
                personaMap.put("campus", campusMap);
            }
            if(salida.getPersona().getContacto() != null){
                Map<String, Object> contactoMap = new HashMap<>();
                contactoMap.put("id", salida.getPersona().getContacto().getId());
                contactoMap.put("direccionResidencia", salida.getPersona().getContacto().getDireccionResidencia());
                contactoMap.put("telefonoCelular", salida.getPersona().getContacto().getTelefonoCelular());
                contactoMap.put("correoElectronico", salida.getPersona().getContacto().getCorreoElectronico());
                personaMap.put("contacto", contactoMap);
            }
            if(salida.getPersona().getCargo() != null){
                Map<String, Object> cargoMap = new HashMap<>();
                cargoMap.put("id", salida.getPersona().getCargo().getId());
                cargoMap.put("nombreCargo", salida.getPersona().getCargo().getNombreCargo());
                personaMap.put("cargo", cargoMap);
            }
            if(salida.getPersona().getFoto() != null){
                Map<String, Object> fotoMap = new HashMap<>();
                fotoMap.put("id", salida.getPersona().getFoto().getId());
                fotoMap.put("foto", salida.getPersona().getFoto().getFoto());
                fotoMap.put("fechaDeCreacion", salida.getPersona().getFoto().getFechaDeCreacion());
                personaMap.put("foto", fotoMap);
            }
            if(salida.getPersona().getRol() != null){
                Map<String, Object> rolMap = new HashMap<>();
                rolMap.put("id", salida.getPersona().getRol().getId());
                rolMap.put("nombreRol", salida.getPersona().getRol().getNombreRol());
                if(salida.getPersona().getRol().getPermiso() != null){
                    Map<String, Object> permisoMap = new HashMap<>();
                    permisoMap.put("id", salida.getPersona().getRol().getPermiso().getId());
                    permisoMap.put("nombrePermiso", salida.getPersona().getRol().getPermiso().getNombrePermiso());
                    if(salida.getPersona().getRol().getPermiso().getModulo() != null){
                        Map<String, Object> moduloMap = new HashMap<>();
                        moduloMap.put("id", salida.getPersona().getRol().getPermiso().getModulo().getId());
                        moduloMap.put("nombreModulo", salida.getPersona().getRol().getPermiso().getModulo().getNombreModulo());
                        moduloMap.put("descripcionModulo", salida.getPersona().getRol().getPermiso().getModulo().getDescripcionModulo());
                        permisoMap.put("modulo", moduloMap);
                    }
                    rolMap.put("permiso", permisoMap);
                }
                personaMap.put("rol", rolMap);
            }
            salidaData.put("persona", personaMap);
        }

        CollectionReference salidas = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = salidas.document().create(salidaData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVA SALIDA AGREGADO: " + salidaData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR LA SALIDA: " + salidaData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Salida salida) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(salida);
            logger.info("INFO: SE ACTUALIZO LA SALIDA CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR LA SALIDA CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO LA SALIDA CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR LA SALIDA CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
