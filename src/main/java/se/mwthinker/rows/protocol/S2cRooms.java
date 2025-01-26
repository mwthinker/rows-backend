package se.mwthinker.rows.protocol;

import java.util.List;

public record S2cRooms(List<Room> rooms) implements Message {
}
