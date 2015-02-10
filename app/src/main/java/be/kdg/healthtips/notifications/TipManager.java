package be.kdg.healthtips.notifications;

import android.content.Context;

import java.util.Random;

import be.kdg.healthtips.activity.TipDetailActivity;

public class TipManager {
    public static void throwRandomStepTip(String reason, Context context) {
        throwTipOfSubject("steps", reason, "Doe meer stappen", context, NotificationThrower.IconType.T_STEPS);
    }

    public static void throwRandomRunningTip(String reason, Context context) {
        throwTipOfSubject("running", reason, "Loop vaker", context, NotificationThrower.IconType.T_RUNNING);
    }

    public static void throwRandomFallingASleepTip(String reason, Context context) {
        throwTipOfSubject("sleep", reason, "Val sneller in slaap", context, NotificationThrower.IconType.T_SLEEP);
    }

    public static void throwRandomSleepTip(String reason, Context context) {
        throwTipOfSubject("sleep", reason, "Slaap beter", context, NotificationThrower.IconType.T_SLEEP);
    }

    public static void throwRandomGewichtTip(String reason, Context context) {
        Random r = new Random();
        if (r.nextBoolean()) {
            throwRandomFoodTip(reason, context);
        } else {
            throwTipOfSubject("food", reason, "Verlies gewicht", context, NotificationThrower.IconType.T_WEIGHT);
        }
    }

    public static void throwRandomFoodTip(String reason, Context context) {
        throwTipOfSubject("food", reason, "Eet gezonder", context, NotificationThrower.IconType.T_FOOD);
    }

    private static void throwTipOfSubject(String subject, String reason, String title, Context context, NotificationThrower.IconType typeIcon) {
        TipGetter tipGetter = new TipGetter();
        int tipnr = tipGetter.getRandomTipNr(subject, context);
        NotificationThrower.throwNotification(context, typeIcon, title, reason, TipDetailActivity.class, tipnr);
    }


}
