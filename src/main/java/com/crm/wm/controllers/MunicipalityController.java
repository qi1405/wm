package com.crm.wm.controllers;

import com.crm.wm.entities.Municipality;
import com.crm.wm.repository.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/municipalities")
public class MunicipalityController {

    @Autowired
    MunicipalityRepository municipalityRepository;

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/create")
    public Municipality createMunicipality(@RequestBody Municipality municipality) {
        return municipalityRepository.create(municipality);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/list")
    public List<Municipality> readAllMunicipalities() {
        return municipalityRepository.readAll();
    }

    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/update/{municipalityID}")
    public Municipality updateMunicipality(@PathVariable Long municipalityID, @RequestBody Municipality municipality) {
        municipality.setMunicipalityID(municipalityID);
        return municipalityRepository.update(municipality);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{municipalityID}")
    public String deleteMunicipality(@PathVariable Long municipalityID) {
        boolean deleted = municipalityRepository.delete(municipalityID);

        if (deleted) {
            return "Municipality deleted successfully.";
        } else {
            return "Municipality not found or delete failed.";
        }
    }
}