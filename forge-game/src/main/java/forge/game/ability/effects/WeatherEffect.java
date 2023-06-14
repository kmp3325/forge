package forge.game.ability.effects;

import java.util.Map;

import forge.game.Game;
import forge.game.Weather;
import forge.game.ability.AbilityKey;
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
        final Map<AbilityKey, Object> runParams = AbilityKey.newMap();
        runParams.put(AbilityKey.Weather, newValue);
        if (newValue.equalsIgnoreCase("SUNNY")) {
            game.setWeather(Weather.SUNNY, runParams);
        } else if (newValue.equalsIgnoreCase("WINDY")) {
            game.setWeather(Weather.WINDY, runParams);
        } else if (newValue.equalsIgnoreCase("FOGGY")) {
            game.setWeather(Weather.FOGGY, runParams);
        } else if (newValue.equalsIgnoreCase("RAINY")) {
            game.setWeather(Weather.RAINY, runParams);
        } else if (newValue.equalsIgnoreCase("SNOWY")) {
            game.setWeather(Weather.SNOWY, runParams);
        } else if (newValue.equalsIgnoreCase("Choose")) {
            game.setWeather(host.getController().getController().chooseWeather(sa), runParams);
        }
    }
}
