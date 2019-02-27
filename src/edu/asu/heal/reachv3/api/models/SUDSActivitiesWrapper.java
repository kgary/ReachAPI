package edu.asu.heal.reachv3.api.models;

import java.util.HashMap;

public class SUDSActivitiesWrapper{
	
	HashMap<String,Boolean> actvityMap;
	boolean sudsconfig;
	
	public SUDSActivitiesWrapper() {
		this.actvityMap=null;
		sudsconfig=false;
		
	}
	public SUDSActivitiesWrapper(HashMap<String,Boolean> map, boolean config ) {
		this.sudsconfig=config;
		this.actvityMap=map;
	}
	public HashMap<String, Boolean> getActvityMap() {
		return actvityMap;
	}
	public void setActvityMap(HashMap<String, Boolean> actvityMap) {
		this.actvityMap = actvityMap;
	}
	public boolean getConfig() {
		return sudsconfig;
	}
	public void setConfig(boolean config) {
		this.sudsconfig = config;
	}
	

}
