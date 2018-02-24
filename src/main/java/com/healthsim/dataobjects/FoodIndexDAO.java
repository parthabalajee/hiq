package com.healthsim.dataobjects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class FoodIndexDAO extends UserActivityDAO<FoodIndex> {
    private static final Logger logger = LoggerFactory.getLogger(FoodIndexDAO.class);

    @Value( "${foodfile}" )
    String fileName;



    @PostConstruct
    public void init() throws IOException {
        File file = resourceLoader.getResource("classpath:"+fileName).getFile();
        logger.debug("file Name "+file);
        loadIndexes(file);
    }

    @Override
    public FoodIndex createUserActiivty(String[] sp) {

        return new FoodIndex(Integer.valueOf(sp[0]),
                sp[1], Integer.valueOf(sp[2]));
    }
}
