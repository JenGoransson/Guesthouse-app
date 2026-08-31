package se.jennifer.guesthouseapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se.jennifer.guesthouseapp.guesthouse.room.model.RoomType;
import se.jennifer.guesthouseapp.guesthouse.room.model.Room;
import se.jennifer.guesthouseapp.guesthouse.room.repository.RoomRepository;
import se.jennifer.guesthouseapp.guesthouse.room.service.RoomService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoomServiceTest {

    private RoomRepository roomRepository;
    private RoomService roomService;

    @BeforeEach
    void setUp(){
        roomRepository = Mockito.mock(RoomRepository.class);
        roomService = new RoomService(roomRepository);
    }
    @Test
    void createRoom_successfullyCreatesRoom(){
        Room room = new Room("110", RoomType.SINGLE,false, 1,500);

        when(roomRepository.existsByRoomNumber("110")).thenReturn(false);
        when(roomRepository.save(room)).thenReturn(room);

        Room result = roomService.createRoom(room);

        assertEquals("110",result.getRoomNumber());
        verify(roomRepository).save(room);

    }
    @Test
    void createRoom_throwsExceptionWhenRoomNumberExists(){
        Room room = new Room("111",RoomType.SINGLE,true,1,500);
        when(roomRepository.existsByRoomNumber("111")).thenReturn(true);
        assertThrows(RuntimeException.class,() -> roomService.createRoom(room));
    }
    @Test
    void validateRoom_singleRoomCannotHaveExtraBed(){
        Room room = new Room("111",RoomType.SINGLE,true,1,500);

        assertThrows(RuntimeException.class,() -> roomService.createRoom(room));
    }
    @Test
    void validateRoom_doubleRoomMustHaveTwoBeds(){

        Room room = new Room("222",RoomType.DOUBLE, false,1 ,800);
        assertThrows(RuntimeException.class, () -> roomService.createRoom(room));
    }

    @Test
    void createRoom_throwsExceptionWhenPriceIsNegative(){
        Room room = new Room("227", RoomType.SINGLE, false, 1, -100);

        assertThrows(RuntimeException.class, () -> roomService.createRoom(room));
    }
}
