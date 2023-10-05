package forge.game.ability.effects;

import java.util.List;

import forge.game.Game;
import forge.game.GameObject;
import forge.game.ability.AbilityKey;
import forge.game.ability.SpellAbilityEffect;
import forge.game.card.Card;
import forge.game.card.CardFactory;
import forge.game.keyword.EvolveFrom;
import forge.game.player.Player;
import forge.game.spellability.SpellAbility;
import forge.game.trigger.TriggerType;
import forge.game.zone.ZoneType;

public class EvolveFromEffect extends SpellAbilityEffect {

  @Override
  public void resolve(SpellAbility sa) {
    final Card host = sa.getHostCard();
    final Player p = host.getOwner();
    final Game game = host.getGame();
    if (host.isCopiedSpell()) {
      host.setCopiedSpell(false);
      host.setToken(true);
    }

    final List<GameObject> targets = getDefinedOrTargeted(sa, "Defined");
    final Card target = (Card)targets.get(0);

    EvolveFrom evolveFromKeyword = (EvolveFrom) sa.getKeyword();
    if (evolveFromKeyword.getNewPower().isPresent()) {
        host.setBasePower(evolveFromKeyword.getNewPower().get());
    }
    if (evolveFromKeyword.getNewToughness().isPresent()) {
      host.setBaseToughness(evolveFromKeyword.getNewToughness().get());
    }

    if (sa.isSpell()) {
      host.setController(p, 0);
    }

    final boolean wasFaceDown = target.isFaceDown();

    host.setMergedToCard(target);

    if (!target.hasMergedCard()) {
      target.addMergedCard(target);
    }

    target.addMergedCardToTop(host);

    target.removeEvolvedStates();
    // Now add all abilities from bottom cards
    final Long ts = game.getNextTimestamp();
    target.setEvolvedTimestamp(ts);

    target.addCloneState(CardFactory.getMutatedCloneStates(target, sa), ts);

    // currently used by Tezzeret, Cruel Machinist and Yedora, Grave Gardener
    // when mutating onto the FaceDown, their effect should end, then 721.2e would stop trigger
    // Do I need this for evolve too? Beats the shit out of me! Might as well leave it!
    if (wasFaceDown && !target.isFaceDown()) {
      target.runFaceupCommands();
    }

    // Re-register triggers for target card
    game.getTriggerHandler().clearActiveTriggers(target, null);
    game.getTriggerHandler().registerActiveTrigger(target, false);

    game.getAction().moveTo(p.getZone(ZoneType.Merged), host, sa);

    host.setTapped(target.isTapped());
    host.setFlipped(target.isFlipped());
    target.setTimesEvolved(target.getTimesEvolved() + 1);
    target.updateStateForView();
    target.updateTokenView();
    if (host.isCommander()) {
      host.getOwner().updateMergedCommanderInfo(target, host);
      target.updateCommanderView();
    }

    game.getTriggerHandler().runTrigger(TriggerType.Evolved, AbilityKey.mapFromCard(target), false);
  }

}
