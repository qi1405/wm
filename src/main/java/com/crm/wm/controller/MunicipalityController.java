package com.crm.wm.controller;

import com.crm.wm.entity.Municipality;
import com.crm.wm.repository.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/municipalities")
public class MunicipalityController {

    @Autowired
    MunicipalityRepository municipalityRepository;

    @PostMapping("/")
    public Municipality createMunicipality(@RequestBody Municipality municipality) {
        return municipalityRepository.create(municipality);
    }

    @PutMapping("/{municipalityID}")
    public Municipality updateMunicipality(@PathVariable Long municipalityID, @RequestBody Municipality municipality) {
        municipality.setMunicipalityID(municipalityID);
        return municipalityRepository.update(municipality);
    }
}
