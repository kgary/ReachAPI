package edu.asu.heal.core.api.service;

import java.lang.reflect.Constructor;

public class HealServiceFactory {

    /* TODO -- Doubt - @dpurbey -- discuss with team & Dr.G
    *
    * here returning the service instance is singleton instance, which makes me wonder, if we are to deploy
    * for example 3 API from a single WAR, then how to achieve service instantiation of 2 other applications
    *
    * We will have to change this from singleton to something else where you can have multiple instances of service,
    * each for a specific applications
    *
    * */

    // service bound to HealService should be instantiated
    private static HealService _theService;

    public static HealService getTheService(String serviceClassName){
        if(_theService == null){
            return HealServiceFactory.initializeService(serviceClassName);
        }

        return _theService;
    }

    private static HealService initializeService(String serviceClassName){
        try {

            Class<?> serviceClass = Class.forName(serviceClassName);
            Constructor<?> serviceClassConstructor = serviceClass.getConstructor();
            _theService = (HealService) serviceClassConstructor.newInstance();

        } catch (ClassNotFoundException ce){
            System.out.println(ce.getMessage());
        } catch (Exception ex){
            System.out.println("Exception occurred: " + ex.getMessage());
        }

        return _theService;
    }

}
