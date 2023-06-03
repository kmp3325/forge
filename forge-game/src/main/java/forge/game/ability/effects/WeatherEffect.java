package forge.game.ability.effects;

import forge.game.Game;
import forge.game.Weather;
import forge.game.ability.SpellAbilityEffect;
import forge.game.card.Card;
import forge.game.spellability.SpellAbility;
import forge.game.staticability.StaticAbilityCantChangeWeather;

public class WeatherEffect extends SpellAbilityEffect {
    @Override
    protected String getStackDescription(SpellAbility sa) {
        final StringBuilder sb = new StringBuilder();
        sb.append("It becomes ").append(sa.getParam("Value").toLowerCase()).append(".");
        return sb.toString();
    }

    @Override
    public void resolve(SpellAbility sa) {
        Card host = sa.getHostCard();
        if (!StaticAbilityCantChangeWeather.canChangeWeather(host)) {
            return;
        }
        Game game = host.getGame();

        String newValue = sa.getParam("Value");
        if (newValue.equalsIgnoreCase("SUNNY")) {
            game.setWeather(Weather.SUNNY);
        } else if (newValue.equalsIgnoreCase("WINDY")) {
            game.setWeather(Weather.WINDY);
        } else if (newValue.equalsIgnoreCase("FOGGY")) {
            game.setWeather(Weather.FOGGY);
        } else if (newValue.equalsIgnoreCase("RAINY")) {
            game.setWeather(Weather.RAINY);
        } else if (newValue.equalsIgnoreCase("SNOWY")) {
            game.setWeather(Weather.SNOWY);
        } else if (newValue.equalsIgnoreCase("Choose")) {
            game.setWeather(host.getController().getController().chooseWeather(sa));
        }
    }
}
