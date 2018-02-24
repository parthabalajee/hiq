package com.healthsim.service;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.healthsim.dataobjects.ExerciseIndex;
import com.healthsim.dataobjects.ExerciseIndexDAO;
import com.healthsim.dataobjects.FoodIndex;
import com.healthsim.dataobjects.FoodIndexDAO;
import com.healthsim.model.ExerciseDoneMessage;
import com.healthsim.model.FoodIntakeMessage;
import com.healthsim.model.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthSimulationService {

    private static final Logger logger = LoggerFactory.getLogger(HealthSimulationService.class);

    @Autowired
    private FoodIndexDAO foodIndexDAO;

    @Autowired
    private ExerciseIndexDAO exerciseIndexDAO;

    @Autowired
    ActorSystem actorSystem;


    public void updateUserEvent(UserEvent ue){
        ActorSelection actorSelection = actorSystem.actorSelection("akka://Health-Simulator/user/UserId"+ue.userID);


        Object message = null;
        if(ue.eventType == UserEvent.EventType.FOOD) {
            FoodIndex fd  = foodIndexDAO.getUserActivityIdex(ue.id);
            message = new FoodIntakeMessage(ue.id, ue.time,fd.getIndex());
        }else {
            ExerciseIndex ed = exerciseIndexDAO.getUserActivityIdex(ue.id);
            message = new ExerciseDoneMessage(ue.id, ue.time,ed.getIndex());
        }
        actorSelection.tell(message, ActorRef.noSender());
        System.out.println(ue);
    }


}
