package cl.duoc.ms_clients.mapper;

import cl.duoc.ms_clients.dto.ClientDTO;
import cl.duoc.ms_clients.model.ClientModel;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDTO toDTO(ClientModel model) {

        if(model == null) return null;

        return ClientDTO.builder()
                .id(model.getId())
                .rut(model.getRut())
                .primerNombre(model.getPrimerNombre())
                .segundoNombre(model.getSegundoNombre())
                .apellidoPaterno(model.getApellidoPaterno())
                .apellidoMaterno(model.getApellidoMaterno())
                .email(model.getEmail())
                .telefono(model.getTelefono())
                .direccion(model.getDireccion())
                .fechaNacimiento(model.getFechaNacimiento())
                .activoCliente(model.getActivoCliente())
                .build();
    }

    public ClientModel toEntity(ClientDTO dto) {

        if(dto == null) return null;

        return ClientModel.builder()
                .id(dto.getId())
                .rut(dto.getRut())
                .primerNombre(dto.getPrimerNombre())
                .segundoNombre(dto.getSegundoNombre())
                .apellidoPaterno(dto.getApellidoPaterno())
                .apellidoMaterno(dto.getApellidoMaterno())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .fechaNacimiento(dto.getFechaNacimiento())
                .activoCliente(dto.getActivoCliente())
                .build();
    }
}