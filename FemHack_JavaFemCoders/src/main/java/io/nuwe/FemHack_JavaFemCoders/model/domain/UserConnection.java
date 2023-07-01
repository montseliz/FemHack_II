package io.nuwe.FemHack_JavaFemCoders.model.domain;

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
public class UserConnection {
    private String ipAddress;
    private LocalDateTime connectionTime;
    private HttpMethod requestMethod;
    private String endpoint;
}
