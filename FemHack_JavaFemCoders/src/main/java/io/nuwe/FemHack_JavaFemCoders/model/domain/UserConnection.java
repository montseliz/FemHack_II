package io.nuwe.FemHack_JavaFemCoders.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserConnection implements Serializable {
    private String ipAddress;
    private LocalDateTime connectionTime;
    private String endpoint;
}
