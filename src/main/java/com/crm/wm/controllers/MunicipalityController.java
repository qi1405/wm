package com.crm.wm.controllers;

import com.crm.wm.entities.Municipality;
import com.crm.wm.repository.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Municipality> readAllMunicipalities() {
        return municipalityRepository.readAll();
    }

    @PutMapping("/{municipalityID}")
    public Municipality updateMunicipality(@PathVariable Long municipalityID, @RequestBody Municipality municipality) {
        municipality.setMunicipalityID(municipalityID);
        return municipalityRepository.update(municipality);
    }

    @DeleteMapping("/{municipalityID}")
    public String deleteMunicipality(@PathVariable Long municipalityID) {
        boolean deleted = municipalityRepository.delete(municipalityID);

        if (deleted) {
            return "Municipality deleted successfully.";
        } else {
            return "Municipality not found or delete failed.";
        }
    }
}