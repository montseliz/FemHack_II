package io.nuwe.FemHack_JavaFemCoders.model.exceptions;

import java.util.List;

public record ErrorResponse(String message, List<ValidationError> errors) {
}