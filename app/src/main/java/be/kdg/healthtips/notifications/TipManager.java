package be.kdg.healthtips.notifications;

import android.content.Context;

import java.util.Random;

import be.kdg.healthtips.activity.TipDetailActivity;

public class TipManager {
    public static void throwRandomStepTip(String reden, Context context) {
        throwTipOfSubject("steps", reden, "Get more steps", context, NotificationThrower.IconType.T_STEPS);
    }

    public static void throwRandomRunningTip(String reden, Context context) {
        throwTipOfSubject("running", reden, "Run more efficiently", context, NotificationThrower.IconType.T_RUNNING);
    }

    public static void throwRandomFallingASleepTip(String reden, Context context) {
        throwTipOfSubject("sleep", reden, "Fall asleep faster", context, NotificationThrower.IconType.T_SLEEP);
    }

    public static void throwRandomSleepTip(String reden, Context context) {
        throwTipOfSubject("sleep", reden, "Sleep better", context, NotificationThrower.IconType.T_SLEEP);
    }

    public static void throwRandomGewichtTip(String reden, Context context) {
        Random r = new Random();
        if (r.nextBoolean()) {
            throwRandomFoodTip(reden, context);
        } else {
            throwTipOfSubject("food", reden, "Loose weight", context, NotificationThrower.IconType.T_WEIGHT);
        }
    }

    public static void throwRandomFoodTip(String reden, Context context) {
        throwTipOfSubject("food", reden, "Eat Healthy", context, NotificationThrower.IconType.T_FOOD);
    }

    private static void throwTipOfSubject(String subject, String reden, String titel, Context context, NotificationThrower.IconType typeIcon) {
        TipGetter tipGetter = new TipGetter();
        int tipnr = tipGetter.getRandomTipNr(subject, context);
        NotificationThrower.throwNotification(context, typeIcon, titel, reden, TipDetailActivity.class, tipnr);
    }


}
