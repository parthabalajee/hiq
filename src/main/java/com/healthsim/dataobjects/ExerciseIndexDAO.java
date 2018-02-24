package com.healthsim.dataobjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class ExerciseIndexDAO extends UserActivityDAO<ExerciseIndex> {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseIndexDAO.class);

    @Value( "${exercisefile}" )
    String fileName;

    @PostConstruct
    public void init() throws IOException {
        File file = resourceLoader.getResource("classpath:"+fileName).getFile();
        logger.debug("file Name "+file);
        loadIndexes(file);
    }

    @Override
    ExerciseIndex createUserActiivty(String[] sp) {
        return new ExerciseIndex(Integer.valueOf(sp[0]),
                sp[1], Integer.valueOf(sp[2]));
    }
}
