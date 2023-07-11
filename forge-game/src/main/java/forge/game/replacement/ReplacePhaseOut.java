package forge.game.replacement;

import java.util.Map;

import forge.game.ability.AbilityKey;
import forge.game.card.Card;

public class ReplacePhaseOut extends ReplacementEffect {
  /**
   * Instantiates a new replacement effect.
   *
   * @param map       the map
   * @param host      the host
   * @param intrinsic
   */
  public ReplacePhaseOut(Map<String, String> map, Card host, boolean intrinsic) {
    super(map, host, intrinsic);
  }

  @Override
  public boolean canReplace(Map<AbilityKey, Object> runParams) {
    if (!matchesValidParam("ValidCards", runParams.get(AbilityKey.Affected))) {
      return false;
    }
    return true;
  }
}
