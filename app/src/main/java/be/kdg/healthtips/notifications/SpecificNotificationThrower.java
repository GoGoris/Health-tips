package be.kdg.healthtips.notifications;

import android.content.Context;

import be.kdg.healthtips.activity.SingleTipActivity;

/**
 * Created by school on 8/2/2015.
 */
public class SpecificNotificationThrower {
    public static void throwYouHaveEatenBeforeSleeping(Context context){
        //wanneer je ziet dat de gebruiker heeft gegeten voor het slapen gaan
        NotificationThrower.throwSpecificTip(context, NotificationThrower.IconType.T_SLEEP,"Eten voor slapen","", SingleTipActivity.class,"Eten voor slapen","Eten vlak voor je gaat slapen is niet goed en zorgt ervoor dat je minder snel in slaap valt. Sommige snacks zijn wel ok; melk, kersen, brood of een banaan. Als je echt honger hebt voor het slapen gaan kan je best een van deze snacks eten");
    }

    public static void throwYouHaveEatenBeforeSporting(Context context){
        NotificationThrower.throwSpecificTip(context, NotificationThrower.IconType.T_RUNNING,"Eten voor sporten","", SingleTipActivity.class,"Eten voor sporten","Voeding gegeten voor een inspanning is alleen van nut wanneer de gegeten voedingsmiddelen ook effectief verteerd worden en opgenomen worden via het maagdarmstelsel. Dit wil zeggen dat er tijd nodig is om het gegeten voedsel daadwerkelijk als brandstof voor de inspanning te gebruiken. De tijd die hiervoor nodig is, is afhankelijk van de soort voeding. Voedingsmiddelen met veel vet, veel eiwit en veel voedingsvezel verteren langzamer en kunnen, wanneer ze nog niet volledig verteerd zijn, maaglast bezorgen tijdens het sporten. Grote hoeveelheden hebben ook langer nodig om te verteren dan kleine porties. En over het algemeen wordt voeding beter verdragen tijdens inspanningen aan lage intensiteit of tijdens sporten waarbij het lichaam wordt “gedragen” ( wielrennen versus lopen). Een algemene richtlijn is om de laatste maaltijd 3 tot 4 uur voor het sporten te plannen terwijl een lichte snack nog kan tot nog 1-2 uur voor de inspanning. Maar het is aan de sporter om hiermee te experimenteren wat het beste scenario is.\n" +
                "\n" +
                "Een goede maaltijd voor de inspanning en een goede bevoorrading tijdens het sporten (meer dan 1 uur) gaan hand in hand.");
    }

    public static void throwYouSportBeforeSleeping(Context context){
        NotificationThrower.throwSpecificTip(context, NotificationThrower.IconType.T_SLEEP,"Sporten voor slapen","", SingleTipActivity.class,"Sporten voor slapen","Wees ook voorzichtig met sporten vlak voor het slapen gaan. Sommige niet-slapers proberen zich af te sloven vlak voordat ze naar bed gaan, in de hoop dat de slaap op die manier wel gaat komen. Dat is echter een misrekening, want te zware lichaamsinspanningen zullen een negatief in plaats van een positief effect hebben.\n" +
                "\n" +
                "Een wandeling vlak voor slaaptijd is te verkiezen boven enkele kilometers joggen aan een strak tempo. Het komt er eigenlijk op aan om niet alleen het verstand op nul te zetten, maar ook om het lichaam aan het verstand te brengen dat er op een lager pitje moet worden gedraaid. Wie met deze en nog een aantal andere factoren rekening houdt, zal al veel minder moeite hebben om de slaap te vatten. ");
    }

    public static void throwBadFoodHabit(Context context){
        //bv. gebruiker eet elke week 1x fastfood
        NotificationThrower.throwSpecificTip(context, NotificationThrower.IconType.T_FOOD,"Eet gewoonte","",SingleTipActivity.class,"Slechte eetgewoontes","U heeft de gewoonte om elke zondag -fastfood- te eten, probeer dit af te leren.");
    }
}
