package kz.iitu.lab2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "establishment_id")
    private Establishments establishment;

    private double depositAmount;

    private boolean isRefundable;

    public LocalDateTime getBookingTime() {
        return startTime; // Просто вернем время начала бронирования, но вы можете реализовать свою логику здесь
    }
    public boolean canBeCanceled() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime bookingTime = getBookingTime(); // Предположим, что у вас есть метод getBookingTime()
        long hoursUntilBooking = ChronoUnit.HOURS.between(now, bookingTime);

        // Бронирование можно отменить, если осталось более 24 часов до назначенного времени
        return hoursUntilBooking > 24;
    }

}
