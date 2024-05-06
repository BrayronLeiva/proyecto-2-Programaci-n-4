package com.example.proyecto_2_progra_4.logic.Services;

import com.example.proyecto_2_progra_4.logic.DTOEntities.ProveedoresDTO;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DTOService {

    public List<ProveedoresDTO> transformarDTO(List<Proveedores> list){
        List<ProveedoresDTO> proveedpresDTO = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Proveedores current = list.get(i);
            proveedpresDTO.add(new ProveedoresDTO(current.getIdProveedor(), current.getTipo(), current.getNombre(),
                    current.getUsuario(), current.isEstado()));
        }

        return proveedpresDTO;
    }
}
