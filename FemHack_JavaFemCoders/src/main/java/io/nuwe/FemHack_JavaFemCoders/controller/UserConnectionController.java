package io.nuwe.FemHack_JavaFemCoders.controller;

import io.nuwe.FemHack_JavaFemCoders.model.dto.UserConnectionDTO;
import io.nuwe.FemHack_JavaFemCoders.model.service.UserConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserConnectionController {

    private final UserConnectionService userConnectionService;

    @GetMapping("/log")
    public ResponseEntity<List<UserConnectionDTO>> getUsersConnection() {
        return new ResponseEntity<>(userConnectionService.getLast25Connections(), HttpStatus.OK);
    }
}
