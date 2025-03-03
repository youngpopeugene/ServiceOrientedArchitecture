package soa.spring.teamservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/teams", produces = MediaType.APPLICATION_XML_VALUE)

public class ApplicationController {
    @Autowired
    private final ApplicationService service;

    @PatchMapping("/{team-id}/join/{humanbeing-id}")
    public ResponseEntity addHumanBeingToTeam(@PathVariable("team-id") String teamId, @PathVariable("humanbeing-id") String humanBeingId) {
        System.out.println("heello");
        Response r = service.addHumanBeingToTeam(teamId, humanBeingId);
        System.out.println(r.getContent());
        return new ResponseEntity<>(r.getContent(), HttpStatus.valueOf(r.getCode()));
    }

    @DeleteMapping("/{team-id}/unjoin/{humanbeing-id}")
    public ResponseEntity removeHumanBeingFromTeam(@PathVariable("team-id") String teamId, @PathVariable("humanbeing-id") String humanBeingId) {
        Response r =  service.removeHumanBeingFromTeam(teamId, humanBeingId);
        return new ResponseEntity<>(r.getContent(), HttpStatus.valueOf(r.getCode()));
    }

    @PatchMapping("/{team-id}/car/add")
    public ResponseEntity updateTeamCars(@PathVariable("team-id") String teamId) {
        Response r = service.updateTeamCars(teamId);
        return new ResponseEntity<>(r.getContent(), HttpStatus.valueOf(r.getCode()));
    }

    @PatchMapping("/{team-id}/make-depressive")
    public ResponseEntity makeTeamDepressive(@PathVariable("team-id") String teamId) {
        Response r = service.makeTeamDepressive(teamId);
        return new ResponseEntity<>(r.getContent(), HttpStatus.valueOf(r.getCode()));
    }
}
