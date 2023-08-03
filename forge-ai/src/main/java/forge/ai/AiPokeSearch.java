package forge.ai;

import forge.game.card.Card;
import forge.game.keyword.Keyword;
import forge.game.keyword.MutateOnto;
import forge.game.spellability.SpellAbility;
import forge.game.zone.ZoneType;

public class AiPokeSearch {

  static boolean okToActivate(SpellAbility sa) {
    if (!sa.hasParam("AISpecificPokeSearch")) {
      return true;
    }

    for (Card card : sa.getActivatingPlayer().getCardsIn(ZoneType.Hand)) {
      if (card.hasKeyword(Keyword.MUTATE_ONTO)) {
        String onto = card.getKeywords(Keyword.MUTATE_ONTO).stream().findFirst()
            .map(m -> (MutateOnto) m)
            .flatMap(MutateOnto::getOnto)
            .orElse("");
        if (onto.equals("")) {
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
