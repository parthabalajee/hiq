package com.healthsim.actors;

import akka.actor.AbstractActor;
import akka.actor.AbstractActorWithTimers;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.healthsim.dataobjects.UserProfile;
import com.healthsim.model.ExerciseDoneMessage;
import com.healthsim.model.FoodIntakeMessage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class UserProfileActor extends AbstractActorWithTimers {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    public static Props props(UserProfile userProfile) {
        return Props.create(UserProfileActor.class,userProfile);
    }


    private final UserProfile userProfile;

    public UserProfileActor(UserProfile userProfile) {
        this.userProfile = userProfile;
    }


    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(FoodIntakeMessage.class, fit -> {
                    this.userProfile.setLastFoodTime(fit.time);
                    getTimers().startSingleTimer(fit.time.toString(),
                            new BloodSugarChangeEvent(fit.index/2,fit.index/2),
                            FiniteDuration.apply(1, TimeUnit.SECONDS));
                    getTimers().startSingleTimer(fit.time.toString()+"1",
                            new BloodSugarChangeEvent(fit.index/2,0),
                            FiniteDuration.apply(1, TimeUnit.SECONDS));
                    System.out.println(fit);
                })
                .match(ExerciseDoneMessage.class, ed -> {
                    this.userProfile.setLastExerciseTime(ed.time);
                    this.createExerciseBSCE(ed);
                    System.out.println(ed);
                })
                .match(BloodSugarChangeEvent.class, msg -> {
                    this.userProfile.updateBloodSugar(msg.increase);
                    System.out.println(msg);
                })
                .match(BloodSugarNormalizerEvent.class, msg -> {
                    // check the last update time food / Exercise
                    // if less than 2 hours stop this event.
                    this.userProfile.updateBloodSugar(-1);
                    System.out.println(msg);
                })
                .build();
    }
    public static final int MINUTES = 60;



    private void createExerciseBSCE(ExerciseDoneMessage ed){
        int gc = lcd(ed.index,MINUTES);
        if(gc == -1)
            return;
        int v = ed.index;

        while( v > 0){
            getTimers().startSingleTimer(ed.time.toString()+v,
                    new BloodSugarChangeEvent(gc,v),
                    FiniteDuration.apply(gc, TimeUnit.SECONDS));
            v-=gc;
        }

    }

    static int lcd(int a, int b){
        int gc = gcd(a,b);
        if( gc == 1){
            return 1;
        }
        for (int i = 2; i < gc ; i++) {
            if(gc % i == 0)
                return i;
        }
        return -1;
    }
    static int gcd(int a, int b)
    {
        if(a == 0 || b == 0) return a+b; // base case
        return gcd(b,a%b);
    }

    public static final class BloodSugarChangeEvent {
        public final int increase;
        public final int balance;

        public BloodSugarChangeEvent(int increase, int balance) {
            this.increase = increase;
            this.balance = balance;
        }

        @Override
        public String toString() {
            return "BloodSugarChangeEvent{" +
                    "increase=" + increase +
                    ", balance=" + balance +
                    '}';
        }
    }
    public static class BloodSugarNormalizerEvent {}



}
