package forge.game.staticability;

import forge.game.card.Card;
import forge.game.zone.ZoneType;

public class StaticAbilityCantChangeWeather {

  static String MODE = "CantChangeWeather";

  static public boolean canChangeWeather(Card card) {
    for (Card ca : card.getGame().getCardsIn(ZoneType.STATIC_ABILITIES_SOURCE_ZONES)) {
      for (final StaticAbility stAb : ca.getStaticAbilities()) {
        if (stAb.checkConditions(MODE)) {
          return false;
        }
      }
    }
    return true;
  }
}
