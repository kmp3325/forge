package forge.game.replacement;

import forge.game.ability.AbilityKey;
import forge.game.card.Card;

import java.util.Map;

public class ReplaceFight extends ReplacementEffect {
    /**
     * Instantiates a new replacement effect.
     *
     * @param map       the map
     * @param host      the host
     * @param intrinsic
     */
    public ReplaceFight(Map<String, String> map, Card host, boolean intrinsic) {
        super(map, host, intrinsic);
    }

    @Override
    public boolean canReplace(Map<AbilityKey, Object> runParams) {
        if (!matchesValidParam("ValidAffected", runParams.get(AbilityKey.Affected))) {
            return false;
        }
        return true;
    }
}
