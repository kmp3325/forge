package forge.game.keyword;

import java.util.Optional;

public class MutateOnto extends KeywordWithCost {
    protected Integer newPower = null;
    protected Integer newToughness = null;
    protected String onto = "";
    /* (non-Javadoc)
     * @see forge.game.keyword.KeywordWithCost#parse(java.lang.String)
     */
    @Override
    protected void parse(String details) {
        String[] detailsArr = details.split(":");
        onto = detailsArr[1];
        if (detailsArr.length > 2) {
            newPower = Integer.parseInt(detailsArr[2].split("/")[0]);
            newToughness = Integer.parseInt(detailsArr[2].split("/")[1]);
        }
        super.parse(details);
    }

    /* (non-Javadoc)
     * @see forge.game.keyword.KeywordWithCost#formatReminderText(java.lang.String)
     */
    @Override
    protected String formatReminderText(String reminderText) {
        String newPtText = "";
        if (getNewPower().isPresent() && getNewToughness().isPresent()) {
            newPtText = "with base power and toughness " + getNewPower().get() + "/" + getNewToughness().get() + " ";
        }
        return String.format(reminderText, onto, newPtText, onto);
    }

    public Optional<Integer> getNewPower() {
        return Optional.ofNullable(newPower);
    }

    public Optional<Integer> getNewToughness() {
        return Optional.ofNullable(newToughness);
    }
}
