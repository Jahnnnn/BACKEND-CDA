package com.cda.services.Visitante;

import com.cda.dto.VisitanteDto;
import com.cda.models.Persona;
import com.cda.models.Visitante;
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
public class VisitanteServiceImpl implements VisitanteService{

    private static final Logger logger = LoggerFactory.getLogger(VisitanteServiceImpl.class);

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public static final String COL_NAME="visitante";
    @Override
    public List<VisitanteDto> GetAll() {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            logger.info("INFO: CONSULTANDO TODOS LOS VISITANTES");
            return dbFirestore.collection(COL_NAME).get().get().getDocuments().parallelStream()
                    .map(visitante -> {
                        final var visitanteDocument = visitante.toObject(Visitante.class);
                        return VisitanteDto.builder()
                                .id(visitante.getId())
                                .numeroDocumento(visitanteDocument.getNumeroDocumento())
                                .descripcionVisita(visitanteDocument.getDescripcionVisita())
                                .tipoDocumento(visitanteDocument.getTipoDocumento())
                                .persona(visitanteDocument.getPersona())
                                .evento(visitanteDocument.getEvento())
                                .build();
                    }).collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("WARN: NO SE LOGRO CONSULTAR TODOS LOS VISITANTES");
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @Override
    public Visitante GetById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Visitante visitante = null;
        if(document.exists()) {
            visitante = document.toObject(Visitante.class);
            logger.info("INFO: SE OBTUVO EL VISITANTE: " + visitante);
            return visitante;
        }else {
            logger.warn("WARN: NO SE LOGRO OBTENER EL VISITANTE CON ID: " + id);
            return null;
        }
    }

    @Override
    public Boolean Add(Visitante visitante) {
        Map<String, Object> visitanteData = new HashMap<>();
        visitanteData.put("numeroDocumento", visitante.getNumeroDocumento());
        visitanteData.put("descripcionVisita", visitante.getDescripcionVisita());

        if(visitante.getTipoDocumento() != null){
            Map<String, Object> tipoDocumentoMap = new HashMap<>();
            tipoDocumentoMap.put("id", visitante.getTipoDocumento().getId());
            tipoDocumentoMap.put("sigla", visitante.getTipoDocumento().getSigla());
            tipoDocumentoMap.put("descripcion", visitante.getTipoDocumento().getDescripcion());
            visitanteData.put("tipoDocumento", tipoDocumentoMap);
        }
        if (visitante.getPersona() != null) {
            Map<String, Object> personaMap = new HashMap<>();
            personaMap.put("id", visitante.getPersona().getId());
            personaMap.put("primerNombre", visitante.getPersona().getPrimerNombre());
            personaMap.put("segundoNombre", visitante.getPersona().getSegundoNombre());
            personaMap.put("primerApellido", visitante.getPersona().getPrimerApellido());
            personaMap.put("segundoApellido", visitante.getPersona().getSegundoApellido());
            personaMap.put("numeroDocumento", visitante.getPersona().getNumeroDocumento());
            personaMap.put("contrasena", visitante.getPersona().getContrasena());
            if(visitante.getPersona().getCarnet() != null){
                Map<String, Object> carnetMap = new HashMap<>();
                carnetMap.put("id", visitante.getPersona().getCarnet().getId());
                carnetMap.put("codigoUniversidad", visitante.getPersona().getCarnet().getCodigoUniversidad());
                carnetMap.put("serialCarnet", visitante.getPersona().getCarnet().getSerialCarnet());
                personaMap.put("carnet", carnetMap);
            }
            if(visitante.getPersona().getTipoDocumento() != null){
                Map<String, Object> tipoDocumentoMap = new HashMap<>();
                tipoDocumentoMap.put("id", visitante.getPersona().getTipoDocumento().getId());
                tipoDocumentoMap.put("sigla", visitante.getPersona().getTipoDocumento().getSigla());
                tipoDocumentoMap.put("descripcion", visitante.getPersona().getTipoDocumento().getDescripcion());
                personaMap.put("tipoDocumento", tipoDocumentoMap);
            }
            if(visitante.getPersona().getPrograma() != null){
                Map<String, Object> programaMap = new HashMap<>();
                programaMap.put("id", visitante.getPersona().getPrograma().getId());
                programaMap.put("nombrePrograma", visitante.getPersona().getPrograma().getNombrePrograma());
                if(visitante.getPersona().getPrograma().getCampus() != null){
                    Map<String, Object> campusMap = new HashMap<>();
                    campusMap.put("id", visitante.getPersona().getPrograma().getCampus().getId());
                    campusMap.put("nombreDelCampus", visitante.getPersona().getPrograma().getCampus().getNombreDelCampus());
                    if(visitante.getPersona().getPrograma().getCampus().getSede() != null){
                        Map<String, Object> sedeMap = new HashMap<>();
                        sedeMap.put("id", visitante.getPersona().getPrograma().getCampus().getSede().getId());
                        sedeMap.put("nombreSede", visitante.getPersona().getPrograma().getCampus().getSede().getNombreSede());
                        sedeMap.put("direccionSede", visitante.getPersona().getPrograma().getCampus().getSede().getDireccionSede());
                        if(visitante.getPersona().getPrograma().getCampus().getSede().getBloque() != null){
                            Map<String, Object> bloqueMap = new HashMap<>();
                            bloqueMap.put("id", visitante.getPersona().getPrograma().getCampus().getSede().getBloque().getId());
                            bloqueMap.put("numeroDeBloque", visitante.getPersona().getPrograma().getCampus().getSede().getBloque().getNumeroDeBloque());
                            sedeMap.put("bloque", bloqueMap);
                        }
                        campusMap.put("sede", sedeMap);
                    }
                    programaMap.put("campus", campusMap);
                }
                personaMap.put("programa", programaMap);
            }
            if(visitante.getPersona().getCampus() != null){
                Map<String, Object> campusMap = new HashMap<>();
                campusMap.put("id", visitante.getPersona().getPrograma().getCampus().getId());
                campusMap.put("nombreDelCampus", visitante.getPersona().getPrograma().getCampus().getNombreDelCampus());
                if(visitante.getPersona().getPrograma().getCampus().getSede() != null){
                    Map<String, Object> sedeMap = new HashMap<>();
                    sedeMap.put("id", visitante.getPersona().getPrograma().getCampus().getSede().getId());
                    sedeMap.put("nombreSede", visitante.getPersona().getPrograma().getCampus().getSede().getNombreSede());
                    sedeMap.put("direccionSede", visitante.getPersona().getPrograma().getCampus().getSede().getDireccionSede());
                    if(visitante.getPersona().getPrograma().getCampus().getSede().getBloque() != null){
                        Map<String, Object> bloqueMap = new HashMap<>();
                        bloqueMap.put("id", visitante.getPersona().getPrograma().getCampus().getSede().getBloque().getId());
                        bloqueMap.put("numeroDeBloque", visitante.getPersona().getPrograma().getCampus().getSede().getBloque().getNumeroDeBloque());
                        sedeMap.put("bloque", bloqueMap);
                    }
                    campusMap.put("sede", sedeMap);
                }
                personaMap.put("campus", campusMap);
            }
            if(visitante.getPersona().getContacto() != null){
                Map<String, Object> contactoMap = new HashMap<>();
                contactoMap.put("id", visitante.getPersona().getContacto().getId());
                contactoMap.put("direccionResidencia", visitante.getPersona().getContacto().getDireccionResidencia());
                contactoMap.put("telefonoCelular", visitante.getPersona().getContacto().getTelefonoCelular());
                contactoMap.put("correoElectronico", visitante.getPersona().getContacto().getCorreoElectronico());
                personaMap.put("contacto", contactoMap);
            }
            if(visitante.getPersona().getCargo() != null){
                Map<String, Object> cargoMap = new HashMap<>();
                cargoMap.put("id", visitante.getPersona().getCargo().getId());
                cargoMap.put("nombreCargo", visitante.getPersona().getCargo().getNombreCargo());
                personaMap.put("cargo", cargoMap);
            }
            if(visitante.getPersona().getFoto() != null){
                Map<String, Object> fotoMap = new HashMap<>();
                fotoMap.put("id", visitante.getPersona().getFoto().getId());
                fotoMap.put("foto", visitante.getPersona().getFoto().getFoto());
                fotoMap.put("fechaDeCreacion", visitante.getPersona().getFoto().getFechaDeCreacion());
                personaMap.put("foto", fotoMap);
            }
            if(visitante.getPersona().getRol() != null){
                Map<String, Object> rolMap = new HashMap<>();
                rolMap.put("id", visitante.getPersona().getRol().getId());
                rolMap.put("nombreRol", visitante.getPersona().getRol().getNombreRol());
                if(visitante.getPersona().getRol().getPermiso() != null){
                    Map<String, Object> permisoMap = new HashMap<>();
                    permisoMap.put("id", visitante.getPersona().getRol().getPermiso().getId());
                    permisoMap.put("nombrePermiso", visitante.getPersona().getRol().getPermiso().getNombrePermiso());
                    if(visitante.getPersona().getRol().getPermiso().getModulo() != null){
                        Map<String, Object> moduloMap = new HashMap<>();
                        moduloMap.put("id", visitante.getPersona().getRol().getPermiso().getModulo().getId());
                        moduloMap.put("nombreModulo", visitante.getPersona().getRol().getPermiso().getModulo().getNombreModulo());
                        moduloMap.put("descripcionModulo", visitante.getPersona().getRol().getPermiso().getModulo().getDescripcionModulo());
                        permisoMap.put("modulo", moduloMap);
                    }
                    rolMap.put("permiso", permisoMap);
                }
                personaMap.put("rol", rolMap);
            }
            visitanteData.put("persona", personaMap);
        }
        if(visitante.getEvento() != null){
            Map<String, Object> eventoMap = new HashMap<>();
            eventoMap.put("id", visitante.getEvento().getId());
            eventoMap.put("nombreEvento", visitante.getEvento().getNombreEvento());
            eventoMap.put("descripcionEvento", visitante.getEvento().getDescripcionEvento());
            visitanteData.put("evento", eventoMap);
        }

        CollectionReference visitantes = firebaseInitializer.getFirestore().collection(COL_NAME);
        ApiFuture<WriteResult> writeResultApiFuture = visitantes.document().create(visitanteData);
        try {
            if (null != writeResultApiFuture.get()){
                logger.info("INFO: NUEVO VISITANTE AGREGADO: " + visitanteData);
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO AGREGAR EL VISITANTE: " + visitanteData);
            ex.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean Update(String id, Visitante visitante) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            dbFirestore.collection(COL_NAME).document(id).set(visitante);
            logger.info("INFO: SE ACTUALIZO EL VISITANTE CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ACTUALIZAR EL VISITANTE CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean Delete(String id) {
        try{
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(id).delete();
            logger.info("INFO: SE ELIMINO EL VISITANTE CON ID: " + id);
            return true;
        }catch (Exception ex){
            logger.warn("WARN: NO SE LOGRO ELIMINAR EL VISITANTE CON ID: " + id);
            ex.printStackTrace();
            return false;
        }
    }
}
