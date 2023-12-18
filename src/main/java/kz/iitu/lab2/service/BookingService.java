package kz.iitu.lab2.service;

import jakarta.persistence.EntityNotFoundException;
import kz.iitu.lab2.Exceptions.BookingCancellationException;
import kz.iitu.lab2.Exceptions.InsufficientFundsException;
import kz.iitu.lab2.dto.BookingDTO;
import kz.iitu.lab2.entity.Booking;
import kz.iitu.lab2.entity.Establishments;
import kz.iitu.lab2.entity.User;
import kz.iitu.lab2.repository.BookingRepository;
import kz.iitu.lab2.repository.EstablishmentsRepository;
import kz.iitu.lab2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final EstablishmentsRepository establishmentsRepository;



    public Booking createBookingWithDeposit(BookingDTO bookingDTO, Long userId, Long establishmentId) {
        double depositAmount = 5000.0;
        User user = userRepository.findById(userId).orElseThrow();
        Establishments establishment = establishmentsRepository.findById(establishmentId).orElseThrow();

        if (user.getBalance() >= depositAmount) {
            Booking booking = new Booking();
            booking.setDepositAmount(depositAmount);
            user.setBalance(user.getBalance() - depositAmount);
            booking.setStartTime(bookingDTO.getStartTime());
            booking.setEndTime(bookingDTO.getEndTime());
            booking.setUser(user);
            booking.setEstablishment(establishment); // Установка учреждения для бронирования
            userRepository.save(user);
            booking.setRefundable(true);
            return bookingRepository.save(booking);

        } else {
            throw new InsufficientFundsException("У пользователя недостаточно средств для задатка.");
        }
    }

    public Booking getBooking(Long bookingId) {
        // Реализация получения бронирования по идентификатору
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + bookingId));
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = getBooking(bookingId);

        if (booking.isRefundable()) {
            User user = booking.getUser();
            double refundAmount = booking.getDepositAmount();

            if (booking.canBeCanceled()) {
                user.setBalance(user.getBalance() + refundAmount); // Возвращаем деньги пользователю
                userRepository.updateUser(user);
            }

            bookingRepository.delete(booking);
        } else {
            throw new BookingCancellationException("Booking cannot be canceled or refunded.");
        }
    }

    public List<Booking> getUserBookings(Long userId) {
        // Реализация получения всех бронирований пользователя
        return bookingRepository.findByUserId(userId);
    }
    public boolean canBeCanceled(Long bookingId) {
        Booking booking = getBooking(bookingId);
        return booking.canBeCanceled();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public BookingDTO mapBookingToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
//        bookingDTO.setId(booking.getId());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setUserId(Long.valueOf(booking.getUser().getId()));
//        bookingDTO.setEstablishmentId(booking.getEstablishment().getId());
        bookingDTO.setDepositAmount(booking.getDepositAmount());
//        bookingDTO.setRefundable(booking.canBeCanceled()); // Assuming this method returns refundable status

        return bookingDTO;
    }
}

