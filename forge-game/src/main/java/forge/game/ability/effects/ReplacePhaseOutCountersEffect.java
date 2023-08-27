package forge.game.ability.effects;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import forge.game.ability.AbilityKey;
import forge.game.ability.SpellAbilityEffect;
import forge.game.card.Card;
import forge.game.card.CounterType;
import forge.game.replacement.ReplacementResult;
import forge.game.spellability.SpellAbility;

public class ReplacePhaseOutCountersEffect extends SpellAbilityEffect {
  @Override
  public void resolve(SpellAbility sa) {
    Set<CounterType> countersToRemove = Collections.emptySet();
    if (sa.hasParam("CounterType")) {
      countersToRemove = Arrays.stream(sa.getParam("CounterType").split(","))
          .map(CounterType::getType)
          .collect(Collectors.toSet());
    }
    Map<AbilityKey, Object> params = (Map<AbilityKey, Object>) sa.getReplacingObject(AbilityKey.OriginalParams);
    Card affected = (Card) params.get(AbilityKey.Affected);
    if (countersToRemove.isEmpty()) {
      affected.clearCounters();
    } else {
      for (CounterType counterType : countersToRemove) {
        affected.subtractCounter(counterType, affected.getCounters(counterType));
      }
    }
    params.put(AbilityKey.ReplacementResult, ReplacementResult.Updated);
  }
}
