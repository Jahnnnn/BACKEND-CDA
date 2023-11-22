package com.cda.services.Persona;

import com.cda.dto.*;
import com.cda.models.*;
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
public class PersonaServiceImpl implements PersonaService {

    private static final Logger logger = LoggerFactory.getLogger(PersonaServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="persona";
    @Override
    public List<PersonaDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODAS LAS PERSONAS");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(persona -> {
                        final var personaDocument = persona.toObject(Persona.class);
                        return PersonaDto.builder()
                                .id(persona.getId())
                                .primerNombre(personaDocument.getPrimerNombre())
                                .segundoNombre(personaDocument.getSegundoNombre())
                                .primerApellido(personaDocument.getPrimerApellido())
                                .segundoApellido(personaDocument.getSegundoApellido())
                                .numeroDocumento(personaDocument.getNumeroDocumento())
                                .contrasena(personaDocument.getContrasena())
                                .carnet(personaDocument.getCarnet())
                                .tipoDocumento(personaDocument.getTipoDocumento())
                                .programa(personaDocument.getPrograma())
                                .campus(personaDocument.getCampus())
                                .contacto(personaDocument.getContacto())
                                .cargo(personaDocument.getCargo())
                                .foto(personaDocument.getFoto())
                                .rol(personaDocument.getRol())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODAS LAS PERSONAS");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Persona GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Persona persona = null;
        if(document.exists()) {
            persona = document.toObject(Persona.class);
            logger.info("INFO: SE OBTUVO LA PERSONA: " + persona);
            return persona;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER LA PERSONA CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Persona persona) {
        //Map<String, Object> personaData = ObjectToMapConverter.convertObjectToMap(persona);
        Map<String, Object> personaData = new HashMap<>();
        personaData.put("primerNombre", persona.getPrimerNombre());
        personaData.put("segundoNombre", persona.getSegundoNombre());
        personaData.put("primerApellido", persona.getPrimerApellido());
        personaData.put("segundoApellido", persona.getSegundoApellido());
        personaData.put("numeroDocumento", persona.getNumeroDocumento());
        personaData.put("contrasena", persona.getContrasena());
        if(persona.getCarnet() != null){
            Map<String, Object> carnetMap = new HashMap<>();
            carnetMap.put("id", persona.getCarnet().getId());
            carnetMap.put("codigoUniversidad", persona.getCarnet().getCodigoUniversidad());
            carnetMap.put("serialCarnet", persona.getCarnet().getSerialCarnet());
            personaData.put("carnet", carnetMap);
        }
        if(persona.getTipoDocumento() != null){
            Map<String, Object> tipoDocumentoMap = new HashMap<>();
            tipoDocumentoMap.put("id", persona.getTipoDocumento().getId());
            tipoDocumentoMap.put("sigla", persona.getTipoDocumento().getSigla());
            tipoDocumentoMap.put("descripcion", persona.getTipoDocumento().getDescripcion());
            personaData.put("tipoDocumento", tipoDocumentoMap);
        }
        if(persona.getPrograma() != null){
            Map<String, Object> programaMap = new HashMap<>();
            programaMap.put("id", persona.getPrograma().getId());
            programaMap.put("nombrePrograma", persona.getPrograma().getNombrePrograma());
            if(persona.getPrograma().getCampus() != null){
                Map<String, Object> campusMap = new HashMap<>();
                campusMap.put("id",persona.getPrograma().getCampus().getId());
                campusMap.put("nombreDelCampus", persona.getPrograma().getCampus().getNombreDelCampus());
                if(persona.getPrograma().getCampus().getSede() != null){
                    Map<String, Object> sedeMap = new HashMap<>();
                    sedeMap.put("id", persona.getPrograma().getCampus().getSede().getId());
                    sedeMap.put("nombreSede", persona.getPrograma().getCampus().getSede().getNombreSede());
                    sedeMap.put("direccionSede", persona.getPrograma().getCampus().getSede().getDireccionSede());
                    if(persona.getPrograma().getCampus().getSede().getBloque() != null){
                        Map<String, Object> bloqueMap = new HashMap<>();
                        bloqueMap.put("id", persona.getPrograma().getCampus().getSede().getBloque().getId());
                        bloqueMap.put("numeroDeBloque", persona.getPrograma().getCampus().getSede().getBloque().getNumeroDeBloque());
                        sedeMap.put("bloque", bloqueMap);
                    }
                    campusMap.put("sede", sedeMap);
                }
                programaMap.put("campus", campusMap);
            }
            personaData.put("programa", programaMap);
        }
        if(persona.getCampus() != null){
            Map<String, Object> campusMap = new HashMap<>();
            campusMap.put("id", persona.getPrograma().getCampus().getId());
            campusMap.put("nombreDelCampus", persona.getPrograma().getCampus().getNombreDelCampus());
            if(persona.getPrograma().getCampus().getSede() != null){
                Map<String, Object> sedeMap = new HashMap<>();
                sedeMap.put("id", persona.getPrograma().getCampus().getSede().getId());
                sedeMap.put("nombreSede", persona.getPrograma().getCampus().getSede().getNombreSede());
                sedeMap.put("direccionSede", persona.getPrograma().getCampus().getSede().getDireccionSede());
                if(persona.getPrograma().getCampus().getSede().getBloque() != null){
                    Map<String, Object> bloqueMap = new HashMap<>();
                    bloqueMap.put("id", persona.getPrograma().getCampus().getSede().getBloque().getId());
                    bloqueMap.put("numeroDeBloque", persona.getPrograma().getCampus().getSede().getBloque().getNumeroDeBloque());
                    sedeMap.put("bloque", bloqueMap);
                }
                campusMap.put("sede", sedeMap);
            }
            personaData.put("campus", campusMap);
        }
        if(persona.getContacto() != null){
            Map<String, Object> contactoMap = new HashMap<>();
            contactoMap.put("id", persona.getContacto().getId());
            contactoMap.put("direccionResidencia", persona.getContacto().getDireccionResidencia());
            contactoMap.put("telefonoCelular", persona.getContacto().getTelefonoCelular());
            contactoMap.put("correoElectronico", persona.getContacto().getCorreoElectronico());
            personaData.put("contacto", contactoMap);
        }
        if(persona.getCargo() != null){
            Map<String, Object> cargoMap = new HashMap<>();
            cargoMap.put("id", persona.getCargo().getId());
            cargoMap.put("nombreCargo", persona.getCargo().getNombreCargo());
            personaData.put("cargo", cargoMap);
        }
        if(persona.getFoto() != null){
            Map<String, Object> fotoMap = new HashMap<>();
            fotoMap.put("id", persona.getFoto().getId());
            fotoMap.put("foto", persona.getFoto().getFoto());
            fotoMap.put("fechaDeCreacion", persona.getFoto().getFechaDeCreacion());
            personaData.put("foto", fotoMap);
        }
        if(persona.getRol() != null) {
            Map<String, Object> rolMap = new HashMap<>();
            rolMap.put("id", persona.getRol().getId());
            rolMap.put("nombreRol", persona.getRol().getNombreRol());
            if (persona.getRol().getPermiso() != null) {
                Map<String, Object> permisoMap = new HashMap<>();
                permisoMap.put("id", persona.getRol().getPermiso().getId());
                permisoMap.put("nombrePermiso", persona.getRol().getPermiso().getNombrePermiso());
                if (persona.getRol().getPermiso().getModulo() != null) {
                    Map<String, Object> moduloMap = new HashMap<>();
                    moduloMap.put("id", persona.getRol().getPermiso().getModulo().getId());
                    moduloMap.put("nombreModulo", persona.getRol().getPermiso().getModulo().getNombreModulo());
                    moduloMap.put("descripcionModulo", persona.getRol().getPermiso().getModulo().getDescripcionModulo());
                    permisoMap.put("modulo", moduloMap);
                }
                rolMap.put("permiso", permisoMap);
            }
            personaData.put("rol", rolMap);
        }

        CollectionReference personas = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = personas.document().create(personaData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO PERSONA AGREGADA: " + personaData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR LA PERSONA: " + personaData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Persona persona) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(persona);
            logger.info("INFO: SE ACTUALIZO LA PERSONA CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR LA PERSONA CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO LA PERSONA CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR LA PERSONA CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
