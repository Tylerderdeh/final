package kz.iitu.lab2.controller;

import kz.iitu.lab2.Exceptions.BookingCancellationException;
import kz.iitu.lab2.Exceptions.InsufficientFundsException;
import kz.iitu.lab2.dto.BookingDTO;
import kz.iitu.lab2.entity.Booking;
import kz.iitu.lab2.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.getBooking(bookingId);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable Long bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return new ResponseEntity<>("Booking canceled successfully", HttpStatus.OK);
        } catch (BookingCancellationException e) {
            return new ResponseEntity<>("Booking cancellation failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long userId) {
        List<Booking> userBookings = bookingService.getUserBookings(userId);
        return new ResponseEntity<>(userBookings, HttpStatus.OK);
    }
    @PostMapping("/user/{userId}/create/{establishmentId}")
    public ResponseEntity<BookingDTO> createBooking(
            @RequestBody BookingDTO bookingDTO,
            @PathVariable Long userId,
            @PathVariable Long establishmentId) {
        try {
            Booking createdBooking = bookingService.createBookingWithDeposit(bookingDTO, userId, establishmentId);
            BookingDTO createdBookingDTO = bookingService.mapBookingToDTO(createdBooking);
            return new ResponseEntity<>(createdBookingDTO, HttpStatus.CREATED);
        } catch (InsufficientFundsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

