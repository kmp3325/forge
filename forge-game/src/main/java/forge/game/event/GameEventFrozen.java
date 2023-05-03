package forge.game.event;

import forge.game.card.Card;

public class GameEventFrozen extends GameEvent {

    public final Card card;

    public GameEventFrozen(Card card) {
        this.card = card;
    }

    @Override
    public <T> T visit(IGameEventVisitor<T> visitor) {
        return visitor.visit(this);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return (card != null ? card.toString() : "(unknown)") + " is frozen.";
    }
}

