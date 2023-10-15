package forge.game.ability.effects;

import forge.game.Game;
import forge.game.ability.SpellAbilityEffect;
import forge.game.card.Card;
import forge.game.card.CardCollection;
import forge.game.card.CardFactory;
import forge.game.spellability.SpellAbility;
import forge.game.zone.ZoneType;

public class StealAbilitiesEffect extends SpellAbilityEffect {

  @Override
  protected String getStackDescription(SpellAbility sa) {
    CardCollection targets = getTargetCards(sa);
    String tgtText = targets.isEmpty() ? "a chosen card" : targets.toString();
    return sa.getHostCard().getName() + " gains all abilities of " + tgtText;
  }
  @Override
  public void resolve(SpellAbility sa) {
    Card top = sa.getHostCard();
    Game game = top.getGame();
    CardCollection targets = getTargetCards(sa);
    final long ts = game.getNextTimestamp();
    for (Card target : targets) {
      // just simulate it through mutate, idgaf, it works fine I guess
      Card bottom = CardFactory.copyCard(target, true);
      bottom.setToken(true);
      if (!top.hasMergedCard()) {
        top.addMergedCard(top);
      }

      top.addMergedCard(bottom);
      top.addCloneState(CardFactory.getMutatedCloneStates(top, sa), ts);
      game.getAction().moveTo(top.getOwner().getZone(ZoneType.Merged), bottom, sa);
      bottom.updateStateForView();
    }
    top.clearMergedCards();
    top.updateStateForView();
  }
}
