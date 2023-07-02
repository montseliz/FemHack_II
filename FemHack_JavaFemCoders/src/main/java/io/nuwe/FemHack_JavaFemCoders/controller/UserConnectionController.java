package io.nuwe.FemHack_JavaFemCoders.controller;

import io.nuwe.FemHack_JavaFemCoders.model.dto.AdminDTO;
import io.nuwe.FemHack_JavaFemCoders.model.exceptions.BadCredentialsException;
import io.nuwe.FemHack_JavaFemCoders.model.service.UserConnectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserConnectionController {

    private final UserConnectionService userConnectionService;

    @GetMapping("/log")
    @Operation(summary = "Get all connections made to the API.", description = "Get the connections made by users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a list of all connections."),
            @ApiResponse(responseCode = "401", description = "Incorrect credentials, try again."),
            @ApiResponse(responseCode = "403", description = "Access denied, you're not an Admin.")
                    })
    public ResponseEntity<?> getUsersConnection(@RequestBody @Valid AdminDTO adminDTO) {
        try {
            if (userConnectionService.isUserAdmin(adminDTO)) {
                return new ResponseEntity<>(userConnectionService.getConnections(), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied, you're not an Admin.");
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
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
