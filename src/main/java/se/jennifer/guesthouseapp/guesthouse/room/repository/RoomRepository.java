package se.jennifer.guesthouseapp.guesthouse.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jennifer.guesthouseapp.guesthouse.room.model.Room;

public interface RoomRepository extends JpaRepository<Room,Long> {
}
