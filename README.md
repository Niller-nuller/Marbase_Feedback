Opgaven for Feedback-opgaven Mission Marsbase Monitoring.

Opgaven er blevet lavet af Johan, Gustav og Niels.

Vi har anvendt CodeWithMe så vi også har kunne hjælpe hinanden samt øve lidt Pair Programming.

AI-Brug:

Opgaven har en bestemt AI branch Assisted.

Vi har anvendt Claude og Copilot agent, i ask og agent mode.

Vi har brugt Claude til hjælp med fejlfinding og forståelse af visse kode muligheder.

Vi har efter vi selv har sagt "done" brugt AI agent til at review the gode og det dårlige ved programmet.

Den har givet tilbage at Sensor-loopet var usikkert og kunne bruge bestemte ændringer.

Så som at rykke loopet og Socket creation væk fra resten af koden.

At vi åbner og lukker vores sensor igennem hvert loop, for at sikre os at hvis serveren går ned, så står vores sensor ikke og "flyver"/venter for evigt selvom serveren kommer back online.

Ændringer på hardkodet værdier som PORT, HOST, Max_Threads. Som vi valgte ikke at ændre på.

I ai branchen bad vi om at implementere det.

Vi både har tjekket koden igennem og kørte programmet for at sikre at de forventede ændringer virkede og om andre bugs eller fejl ville ske efter ændringerne.

En anden ting var en null besked fra mars protokolen, som skete fordi hvert gang Sensor stoppede lyttede server stadig efter den og printerede dens besked ud som nu var ingen ting siden den var lukket.

Fremgangsmåde:

Vi startede med at læse hele opgaven, snakke om hvad der skulle laves og gik i gang.

Vi lavede hele serveren, protokollen og FileWriteren i et hug.

Vi har lavet Sensor og alle dens forventet Threads og logik klar.

Derefter testede vi en Thread for at finde fejl.

Det gjorde vi også, vi brugt souts til at finde ud af om value eller key var forkert.

Som Sensor sender den korrekte data, kan protokollen dele den ordenligt og behandle den ordentligt.

Som eksempel TEMP's value er en double men var forventet at være en int så den gav fejl, men vi vidste at den noget så langt så det var lidt at finde.

Vi ændrede vores FileWriter Til MarsLogger.

Vi fik alle Threads til at virke og skrive samtidig, før vi fortsatte med mulige store ændringer eller udvidelser.

Ai-Agent-Fremgangs Del:

Vi har brugt specifikke instruktioner til agenten hver gang vi har bedt den om noget så som ikke at kode, ændre og commit/push noget til github.

Vi bad den om at skrive i plan.xml og i Sensor:Klassen så vi kunne se ændringerne får de skete.

Vi har været forsikrede med at anvende den og tør sige vi forstår koden.

