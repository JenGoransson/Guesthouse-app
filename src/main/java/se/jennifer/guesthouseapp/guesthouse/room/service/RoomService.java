package se.jennifer.guesthouseapp.guesthouse.room.service;

import org.springframework.stereotype.Service;
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
    public boolean isRoomBoken(long roomId){
        Room room = getRoomById(roomId);
        return !room.getBookings().isEmpty();
    }
    public List<Room> getAvailableRooms(){
        return roomRepository.findAll().stream().filter(room -> room.getBookings().isEmpty()).toList();
    }


    /* TODO:
    *   metod som kollar om ett rum är bokat - boolean?.
    *   metod som hämtar alla lediga rum.
    *   metod som hämtar alla bokningar för ett rum - bra för admin.
    *   extra --> metod som skapar ett rum
    *   extra --> metod som uppdaterar ett rum: typ pris, antal sängar etc.
    *
    * */
}
