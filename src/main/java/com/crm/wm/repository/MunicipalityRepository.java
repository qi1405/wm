package com.crm.wm.repository;

import com.crm.wm.entities.Municipality;

import java.util.List;

public interface MunicipalityRepository {
    Municipality create (Municipality municipality);

    List<Municipality> readAll();

    Municipality update (Municipality municipality);

    boolean delete(Long municipalityID);
}
