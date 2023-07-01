package io.nuwe.FemHack_JavaFemCoders.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserConnectionDTO {

    private String email;
    private String ipAddress;
    private LocalDateTime connectionTime;
    private String endpoint;
}
