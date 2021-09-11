package rc.rover.model;

public class RoverStatus {
    private String roverName;
    private float rpm;
    private float distance;

    private UserAction.Throttle throttle = UserAction.Throttle.none;;
    private UserAction.Direction direction = UserAction.Direction.none;
    private String user;

    public RoverStatus() {}

    public RoverStatus(String roverName, float rpm, float distance) {
        this.roverName = roverName;
        this.rpm = rpm;
        this.distance = distance;
    }

    public Float getRpm() {
        return rpm;
    }

    public void setRpm(Float rpm) {
        this.rpm = rpm;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getRoverName() {
        return roverName;
    }

    public void setRoverName(String roverName) {
        this.roverName = roverName;
    }

    public UserAction.Throttle getThrottle() {
        return throttle;
    }

    public void setThrottle(UserAction.Throttle throttle) {
        this.throttle = throttle;
    }

    public UserAction.Direction getDirection() {
        return direction;
    }

    public void setDirection(UserAction.Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "RoverStatus{" +
                "roverName='" + roverName + '\'' +
                ", rpm=" + rpm +
                ", distance=" + distance +
                '}';
    }

    public void setUser(String currentUser) {
        this.user = currentUser;
    }

    public String getUser() {
        return user;
    }
}
