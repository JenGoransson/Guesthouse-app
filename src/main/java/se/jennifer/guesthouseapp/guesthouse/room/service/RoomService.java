package se.jennifer.guesthouseapp.guesthouse.room.service;

import org.springframework.stereotype.Service;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.error.NotFoundException;
import se.jennifer.guesthouseapp.guesthouse.room.model.Room;
import se.jennifer.guesthouseapp.guesthouse.room.repository.RoomRepository;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    public Room getRoomById(Long id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found"));
    }
    public boolean isRoomBooked(long roomId){
        Room room = getRoomById(roomId);
        return !room.getBookings().isEmpty();
    }
    public List<Room> getAvailableRooms(){
        return roomRepository.findAll().stream()
                .filter(room -> room.getBookings().isEmpty()).toList();
    }
    public List<Booking> getBookingsForRoom(Long roomId){
        Room room = getRoomById(roomId);
        return room.getBookings();
    }

    public Room createRoom(Room room){
        return roomRepository.save(room);
    }

    public Room updateRoom(long id, Room updatedRoom){
        Room room = getRoomById(id);

        room.setRoomNumber(updatedRoom.getRoomNumber());
        room.setBeds(updatedRoom.getBeds());
        room.setPricePerNight(updatedRoom.getPricePerNight());
        return roomRepository.save(room);
    }
}
