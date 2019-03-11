package edu.asu.heal.reachv3.api.models;

import java.util.HashMap;

public class SUDSActivitiesWrapper{
	
	HashMap<String,Boolean> actvityMap;
	boolean sudsConfig;
	
	public SUDSActivitiesWrapper() {
		this.actvityMap=null;
		sudsConfig=false;
		
	}
	public SUDSActivitiesWrapper(HashMap<String,Boolean> map, boolean sudsConfig ) {
		this.sudsConfig=sudsConfig;
		this.actvityMap=map;
	}
	public HashMap<String, Boolean> getActvityMap() {
		return actvityMap;
	}
	public void setActvityMap(HashMap<String, Boolean> actvityMap) {
		this.actvityMap = actvityMap;
	}
	public boolean getSudsConfig() {
		return sudsConfig;
	}
	public void setSudsConfig(boolean sudsConfig) {
		this.sudsConfig = sudsConfig;
	}
	

}
