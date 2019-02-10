package edu.asu.heal.reachv3.api.models.schedule;

public class UIPersonalizationFile {

	private String level_1= "<PATH TO THE FILE IN RESOURCE>";
	private String level_2= "<PATH TO THE FILE IN RESOURCE>";
	
	public UIPersonalizationFile() {
		
	}
	
	public UIPersonalizationFile(String l_1, String l_2) {
		level_1=l_1;
		level_2=l_2;
	}
	
	public String getLevel_1() {
		return level_1;
	}
	public void setLevel_1(String level_1) {
		this.level_1 = level_1;
	}
	public String getLevel_2() {
		return level_2;
	}
	public void setLevel_2(String level_2) {
		this.level_2 = level_2;
	}

	@Override
	public String toString() {
		return "UIPersonalizationFile{" +
				", level_1='" + level_1 + '\'' +
				", level_2=" + level_2 +
				'}';
	}

}
