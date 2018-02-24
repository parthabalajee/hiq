package com.healthsim.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.Props;
import com.healthsim.actors.DeadLetterActor;
import com.healthsim.actors.UserProfileActor;
import com.healthsim.dataobjects.UserProfile;
import com.healthsim.model.UserEvent;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;


@Configuration
@ComponentScan(basePackages = {"com.healthsim.*"})
@PropertySource("classpath:Application.properties")

@SpringBootApplication
public class HealthSimulationApp implements CommandLineRunner {
    public static final String EXERCISE = "E";
    public static final String FOOD = "F";

    private static final Logger logger = LoggerFactory.getLogger(HealthSimulationApp.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    HealthSimulationService healthSimulationService;

    @Bean
    public ActorSystem actorSystem(){
        ActorSystem system = ActorSystem.create("Health-Simulator",akkaConfiguration());
//        SpringExtension springExtension = applicationContext.getBean(SpringExtension.class);
//        springExtension.initialize(applicationContext);
        ActorRef dactor = system.actorOf(Props.create(DeadLetterActor.class));
        system.eventStream().subscribe(dactor, DeadLetter.class);
        return system;
    }

    ActorRef createUserProfileActor(UserProfile userProfile){
        ActorRef actorRef  = actorSystem().actorOf(UserProfileActor.props(userProfile),
                "UserId"+userProfile.getUserID());
        System.out.println(actorRef.path());
        return actorRef;
    }

    @Bean
    public Config akkaConfiguration(){
        return ConfigFactory.load();
    }

    @Override
    public void run(String ...args) throws Exception {
        Arrays.asList(args).forEach(n -> logger.debug(n));
        String dataFile = "data.csv";
        if ( args.length > 0 ){
            dataFile = args[0];
        }
        File file = resourceLoader.getResource("classpath:"+dataFile).getFile();
        Scanner sc = new Scanner(file);

        try {
            int userId = sc.nextInt();
            UserProfile up = new UserProfile(userId,80);
            createUserProfileActor(up);
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                if (s.trim().isEmpty())
                    continue;
                String[] sp = s.split(",");
                UserEvent.EventType etype = UserEvent.EventType.FOOD;
                if (EXERCISE.equals(sp[1]))
                    etype = UserEvent.EventType.EXERCISE;
                UserEvent ue = new UserEvent(userId,Integer.valueOf(sp[0]),
                        new Date(Long.valueOf(sp[2])), etype);
                healthSimulationService.updateUserEvent(ue);
            }
        } finally {
            sc.close();
        }

    }

    public static void main(String[] args) throws Exception {

        SpringApplication.run(HealthSimulationApp.class, args);

    }

}
