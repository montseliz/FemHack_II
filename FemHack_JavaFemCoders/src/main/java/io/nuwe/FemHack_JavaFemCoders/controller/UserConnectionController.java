package io.nuwe.FemHack_JavaFemCoders.controller;

import io.nuwe.FemHack_JavaFemCoders.model.dto.UserConnectionDTO;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.ConnectionsNotFoundException;
import io.nuwe.FemHack_JavaFemCoders.model.service.UserConnectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserConnectionController {

    private final UserConnectionService userConnectionService;

    @GetMapping("/log")
    @Operation(summary = "Get all connections made to the API.", description = "Get the connections made by users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of all connections."),
            @ApiResponse(responseCode = "404", description = "No connections made yet.")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUsersConnection() {
        try {
            return new ResponseEntity<>(userConnectionService.getConnections(), HttpStatus.OK);
        } catch (ConnectionsNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/count")
    @Operation(summary = "Get the count of users registered in the database.", description = "Get the count of users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "We have a total of count registered users.")
    })
    public ResponseEntity<String> getUsersCount() {
        int count = userConnectionService.numberOfUsers();
        return ResponseEntity.ok("We have a total of " + count + " registered users.");
    }
}
