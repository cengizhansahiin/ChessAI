package logic.move;

import java.util.Map;


public final class MoveMapper {

    public static final Map<String, Integer> decodeChar = Map.ofEntries(Map.entry("A", 0),
                                                                        Map.entry("B", 1),
                                                                        Map.entry("C", 2),
                                                                        Map.entry("D", 3),
                                                                        Map.entry("E", 4),
                                                                        Map.entry("F", 5),
                                                                        Map.entry("G", 6),
                                                                        Map.entry("H", 7));

}
