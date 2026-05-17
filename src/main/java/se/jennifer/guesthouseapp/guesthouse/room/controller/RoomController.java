package se.jennifer.guesthouseapp.guesthouse.room.controller;

import org.springframework.web.bind.annotation.*;
import se.jennifer.guesthouseapp.guesthouse.booking.model.Booking;
import se.jennifer.guesthouseapp.guesthouse.room.model.Room;
import se.jennifer.guesthouseapp.guesthouse.room.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms(){
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id){
        return roomService.getRoomById(id);
    }

    @GetMapping("/{id]/bookings")
    public List<Booking> getBookingsForRoom(@PathVariable Long id){
        return roomService.getBookingsForRoom(id);
    }

    @GetMapping("/available")
    public List<Room> getAvailableRooms(){
        return roomService.getAvailableRooms();
    }

    @PostMapping
    public Room createRoom(@RequestBody Room room){
        return roomService.createRoom(room);
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody Room updateRoom){
        return  roomService.updateRoom(id, updateRoom);
    }



}
