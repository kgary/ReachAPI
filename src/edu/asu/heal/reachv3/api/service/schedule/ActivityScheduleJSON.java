package edu.asu.heal.reachv3.api.service.schedule;

public class ActivityScheduleJSON {
	
	private RelaxJSON relaxation;
	private DailyDiaryJSON dailyDiary;
	private EmotionJSON emotions;
	private FaceItJSON faceIt;
	private MakeBelieveJSON makeBelieve;
	private StandUpJSON standUp;
	private SwapJSON swap;
	private WorryHeadJSON worryHeads;
	
	public ActivityScheduleJSON() {}
	
	public ActivityScheduleJSON(String module) {
		
		relaxation=new RelaxJSON();
		dailyDiary=new DailyDiaryJSON();
		emotions = new EmotionJSON();
		switch(module) {
		case "2":
			swap = new SwapJSON();
			break;
		case "3":
			swap = new SwapJSON();
			makeBelieve = new MakeBelieveJSON();
			break;
		case "4":
			swap = new SwapJSON();
			makeBelieve = new MakeBelieveJSON();
			standUp = new StandUpJSON();
			break;
		case "5":
			swap = new SwapJSON();
			makeBelieve = new MakeBelieveJSON();
			standUp = new StandUpJSON();
			faceIt = new FaceItJSON();
			break;
		}
		
	}

	public RelaxJSON getRelaxation() {
		return relaxation;
	}

	public void setRelaxation(RelaxJSON relaxation) {
		this.relaxation = relaxation;
	}

	public DailyDiaryJSON getDailyDiary() {
		return dailyDiary;
	}

	public void setDailyDiary(DailyDiaryJSON dailyDiary) {
		this.dailyDiary = dailyDiary;
	}

	public EmotionJSON getEmotions() {
		return emotions;
	}

	public void setEmotions(EmotionJSON emotions) {
		this.emotions = emotions;
	}

	public FaceItJSON getFaceIt() {
		return faceIt;
	}

	public void setFaceIt(FaceItJSON faceIt) {
		this.faceIt = faceIt;
	}

	public MakeBelieveJSON getMakeBelieve() {
		return makeBelieve;
	}

	public void setMakeBelieve(MakeBelieveJSON makeBelieve) {
		this.makeBelieve = makeBelieve;
	}

	public StandUpJSON getStandUp() {
		return standUp;
	}

	public void setStandUp(StandUpJSON standUp) {
		this.standUp = standUp;
	}

	public SwapJSON getSwap() {
		return swap;
	}

	public void setSwap(SwapJSON swap) {
		this.swap = swap;
	}

	public WorryHeadJSON getWorryHeads() {
		return worryHeads;
	}

	public void setWorryHeads(WorryHeadJSON worryHeads) {
		this.worryHeads = worryHeads;
	}
	

}
