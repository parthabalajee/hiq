package com.healthsim.dataobjects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public abstract class UserActivityDAO<T extends AbstractUserActivityIndex> {
    private boolean initialized ;

    private Map<Integer, T> indexMap = new HashMap<Integer, T>();

    abstract T createUserActiivty(String[] sp);


    @Autowired
    ResourceLoader resourceLoader;

    void loadIndexes(File xfile) throws IOException{
        if(initialized)
            return;

        Scanner sc = new Scanner(xfile);
        try {
            while (sc.hasNextLine()) {
                String s = sc.nextLine();
                if(s.trim().isEmpty())
                    continue;
                String[] sp = s.split(",");
                this.indexMap.put(Integer.valueOf(sp[0]), createUserActiivty(sp));
            }
        } finally {
            sc.close();
        }
    }

    public T getUserActivityIdex(Integer id) {
        return this.indexMap.get(id);

    }
}
