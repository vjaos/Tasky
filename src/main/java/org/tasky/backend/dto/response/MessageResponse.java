package org.tasky.backend.dto.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MessageResponse {
    private Timestamp timestamp;
    private String message;

}
