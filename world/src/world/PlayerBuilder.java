package world;

public interface PlayerBuilder {

  void updatePlayerRoomInfo(String roomName);

  int movePlayer();

  public String pickUp();

  String lookAround(String playerName);

  String displayPlayerInfo(String playerName);

  int autoMoveTarget();

}
