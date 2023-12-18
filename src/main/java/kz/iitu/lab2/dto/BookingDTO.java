package kz.iitu.lab2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double depositAmount;
    private Long userId;
//    private boolean isRefundable;
}
