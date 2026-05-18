package se.jennifer.guesthouseapp.guesthouse.room.service;

import org.springframework.stereotype.Service;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.booking.repository.BookingRepository;
import se.jennifer.guesthouseapp.guesthouse.error.NotFoundException;
import se.jennifer.guesthouseapp.guesthouse.room.RoomType;
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

    public Room createRoom(Room room){
        if(roomRepository.existsByRoomNumber(room.getRoomNumber())){
            throw new RuntimeException("Room number already exists");
        }
        validateRoom(room);
        return roomRepository.save(room);
    }

    public Room updateRoom(long id, Room updatedRoom){
        Room room = getRoomById(id);
        validateRoom(updatedRoom);

        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setBeds(updatedRoom.getBeds());
        room.setPricePerNight(updatedRoom.getPricePerNight());
        room.setType(updatedRoom.getType());
        room.setExtraBedAllowed(updatedRoom.isExtraBedAllowed());
        return roomRepository.save(room);
    }

    private void validateRoom(Room room) {
        if (room.getType() == RoomType.ENKEL){
            if (room.isExtraBedAllowed()){
                throw new RuntimeException("Single room cannot have extra beds");
            }
            if (room.getBeds() != 1){
                throw new RuntimeException("Single rooms must have exactly 1 bed");
            }
        }
        if (room.getType() == RoomType.DUBBEL){
            if (room.getBeds() != 2){
                throw new RuntimeException("Double rooms must have exactly 2 beds");
            }
        }
    }

    public List<Room> getAvailableRoomsByDate(LocalDate date){
        return roomRepository.findAll().stream().filter(room -> !bookingService.isRoomBooked(room.getId(), date, date)).toList();
    }

    public List<Room> getAvailableRoomsByInterval(LocalDate start, LocalDate end){
        return roomRepository.findAll().stream().filter(room -> !bookingService.isRoomBooked(room.getId(), start, end)).toList();
    }

}
