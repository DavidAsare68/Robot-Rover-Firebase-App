package rc.rover.model;

public class UserAction {

    public enum Direction { none, left, right };
    public enum Throttle { none, forward, reverse }

    private String roverName;
    private Direction direction;
    private Throttle motion;

    public UserAction() {}


    public UserAction(String roverName, Direction direction, Throttle motion) {
        this.roverName = roverName;
        this.direction = direction;
        this.motion = motion;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Throttle getMotion() {
        return motion;
    }

    public void setMotion(Throttle motion) {
        this.motion = motion;
    }

    public String getRoverName() {
        return roverName;
    }

    public void setRoverName(String roverName) {
        this.roverName = roverName;
    }

    @Override
    public String toString() {
        return "UserAction{" +
                "roverName='" + roverName + '\'' +
                ", direction=" + direction +
                ", motion=" + motion +
                '}';
    }
}
