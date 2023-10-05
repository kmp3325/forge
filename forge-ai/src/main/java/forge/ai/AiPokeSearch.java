package forge.ai;

import forge.game.card.Card;
import forge.game.keyword.EvolveFrom;
import forge.game.keyword.Keyword;
import forge.game.spellability.SpellAbility;
import forge.game.zone.ZoneType;

public class AiPokeSearch {

  static boolean okToActivate(SpellAbility sa) {
    if (!sa.hasParam("AISpecificPokeSearch")) {
      return true;
    }

    for (Card card : sa.getActivatingPlayer().getCardsIn(ZoneType.Hand)) {
      if (card.hasKeyword(Keyword.EVOLVE_FROM)) {
        String onto = card.getKeywords(Keyword.EVOLVE_FROM).stream().findFirst()
            .map(m -> (EvolveFrom) m)
            .flatMap(EvolveFrom::getFrom)
            .orElse("");
        if (onto.isEmpty()) {
          continue;
        }
        for (Card cardInDeck : sa.getActivatingPlayer().getCardsIn(ZoneType.Library)) {
          if (cardInDeck.getName().equals(onto)) {
            return true;
          }
        }
      }
    }

    return false;
  }
}
