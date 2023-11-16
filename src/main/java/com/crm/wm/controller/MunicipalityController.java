package com.crm.wm.controller;

import com.crm.wm.entity.Municipality;
import com.crm.wm.repository.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class MunicipalityController {

    @Autowired
    MunicipalityRepository municipalityRepository;

    @PostMapping("/municipalities")
    public Municipality createMunicipality(@RequestBody Municipality municipality) {
        return municipalityRepository.create(municipality);
    }

    @PutMapping("/municipalities/{id}")
    public Municipality updateMunicipality(@RequestBody Municipality municipality, @PathVariable("id") long id) {
        
        return municipalityRepository.update(municipality);
    }
}
