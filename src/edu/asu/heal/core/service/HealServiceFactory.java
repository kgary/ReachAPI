package edu.asu.heal.core.service;

import java.lang.reflect.Constructor;

public class HealServiceFactory {
    private static HealService _theService;

    public HealServiceFactory() {
    }

    public static HealService getTheService(){
        if(_theService == null){
            System.out.println("The service is null");
        }
        return _theService;
    }

    static {
        try {
            Class<?> apiClass = Class.forName("edu.asu.heal.reachv3.api.service.ReachService");
            Constructor<?> apiClassConstructor = apiClass.getConstructor();
            _theService = (HealService) apiClassConstructor.newInstance();

        }catch (ClassNotFoundException ce){
            System.out.println("The required class is not found");
        }catch (NoSuchMethodException ne){
            System.out.println("No Such method");
        }catch (Exception e){
            System.out.println("Other exception in getting service");
        }
    }
}
