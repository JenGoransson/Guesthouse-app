package se.jennifer.guesthouseapp.guesthouse.room.service;

import org.springframework.stereotype.Service;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.booking.repository.BookingRepository;
import se.jennifer.guesthouseapp.guesthouse.error.NotFoundException;
import se.jennifer.guesthouseapp.guesthouse.room.model.Room;
import se.jennifer.guesthouseapp.guesthouse.room.repository.RoomRepository;
import se.jennifer.guesthouseapp.guesthouse.booking.service.BookingService;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final BookingService bookingService;

    public RoomService(RoomRepository roomRepository, BookingService bookingService) {
        this.roomRepository = roomRepository;
        this.bookingService = bookingService;
    }

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found"));
    }

    public List<Room> getAvailableRoomsByDate(LocalDate date){
        return roomRepository.findAll().stream().filter(room -> !bookingService.isRoomBooked(room.getId(), date, date)).toList();
    }

    public List<Room> getAvailableRoomsByInterval(LocalDate start, LocalDate end){
        return roomRepository.findAll().stream().filter(room -> !bookingService.isRoomBooked(room.getId(), start, end)).toList();
    }

    public Room createRoom(Room room){
        return roomRepository.save(room);
    }

    public Room updateRoom(long id, Room updatedRoom){
        Room room = getRoomById(id);

        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setBeds(updatedRoom.getBeds());
        room.setPricePerNight(updatedRoom.getPricePerNight());
        room.setType(updatedRoom.getType());
        room.setExtraBedAllowed(updatedRoom.isExtraBedAllowed());
        return roomRepository.save(room);
    }
}
