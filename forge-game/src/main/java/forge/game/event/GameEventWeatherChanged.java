package forge.game.event;

import forge.game.Weather;

public class GameEventWeatherChanged extends GameEvent {
    public final Weather weather;

    public GameEventWeatherChanged(Weather weather) {
        this.weather = weather;
    }

    @Override
    public <T> T visit(IGameEventVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
