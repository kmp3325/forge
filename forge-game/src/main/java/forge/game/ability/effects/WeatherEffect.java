package forge.game.ability.effects;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;

import forge.game.Game;
import forge.game.Weather;
import forge.game.ability.AbilityKey;
import forge.game.ability.AbilityUtils;
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
        boolean changedWeather = false;
        if (newValue.equalsIgnoreCase("SUNNY")) {
            changedWeather = game.setWeather(Weather.SUNNY, runParams);
        } else if (newValue.equalsIgnoreCase("WINDY")) {
            changedWeather = game.setWeather(Weather.WINDY, runParams);
        } else if (newValue.equalsIgnoreCase("FOGGY")) {
            changedWeather = game.setWeather(Weather.FOGGY, runParams);
        } else if (newValue.equalsIgnoreCase("RAINY")) {
            changedWeather = game.setWeather(Weather.RAINY, runParams);
        } else if (newValue.equalsIgnoreCase("SNOWY")) {
            changedWeather = game.setWeather(Weather.SNOWY, runParams);
        } else if (newValue.equalsIgnoreCase("Choose")) {
            changedWeather = game.setWeather(host.getController().getController().chooseWeather(sa), runParams);
        } else if (newValue.equalsIgnoreCase("ChooseHasntBeen")) {
            List<Weather> choices = Sets.difference(new HashSet<>(Arrays.asList(Weather.values())), game.getWeatherSeen()).stream().collect(Collectors.toList());
            Optional<Weather> choice = host.getController().getController().chooseWeather(sa, choices);
            if (choice.isPresent()) {
                changedWeather = game.setWeather(choice.get(), runParams);
            }
        }

        if (changedWeather) {
            SpellAbility sub = sa.getAdditionalAbility("ChangedSubAbility");
            if (sub != null) {
                AbilityUtils.resolve(sub);
            }
        } else {
            SpellAbility sub = sa.getAdditionalAbility("NoChangeSubAbility");
            if (sub != null) {
                AbilityUtils.resolve(sub);
            }
        }
    }
}
