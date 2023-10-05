package forge.item.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import forge.StaticData;
import forge.card.CardRarity;
import forge.item.PaperCard;

public class PokeGeneration {

  public static int getEvos(List<PaperCard> result, CardRarity rarity, int num) {
    Optional<PaperCard> evo = getEvo(result, rarity);
    if (evo.isPresent()) {
      result.add(evo.get());
    } else {
      num++;
    }
    while (evo.isPresent() && num > 0) {
      evo = getEvo(result, rarity);
      if (evo.isPresent()) {
        result.add(evo.get());
      } else {
        num++;
      }
      num--;
    }

    return num;
  }

  private static Optional<PaperCard> getEvo(List<PaperCard> cards, CardRarity rarity) {
    List<PaperCard> cardsInRarity = new ArrayList<>();
    for (PaperCard card : cards) {
      if (card.getRarity().equals(rarity)) {
        cardsInRarity.add(card);
      }
    }
    for (PaperCard card : cardsInRarity) {
      Optional<PaperCard> evo = getEvo(card);
      if (!evo.isPresent() || cards.contains(evo.get())) {
        continue;
      }
      return evo;
    }

    return Optional.empty();
  }

  private static Optional<PaperCard> getEvo(PaperCard card) {
    Optional<String> evo = Optional.empty();
    for (String keyword : card.getRules().getMainPart().getKeywords()) {
      if (keyword.startsWith("Evolve from")) {
        evo = Optional.of(keyword);
        break;
      }
    }
    if (!evo.isPresent()) {
      return Optional.empty();
    }
    String[] evolveParts = evo.get().split(":");
    if (evolveParts.length < 3) {
      return Optional.empty();
    }
    String evoName = evolveParts[2];
    return Optional.ofNullable(StaticData.instance().getCommonCards().getCard(evoName));
  }
}
