package edu.asu.heal.reachv3.api.models;

public class SUDSQuestion {
	
	String question;
	String questionType;
	String response;
	int intensity;
	
	public SUDSQuestion(){}

	public SUDSQuestion(String question, String questionType, String response,int intensity) {
		this.question = question;
		this.questionType = questionType;
		this.response = response;
		this.intensity=intensity;
		
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "SUDSQuestion{" +
				", question='" + question + '\'' +
				", questionType='" + questionType + '\'' +
				", response='" + response + '\'' +
				'}';
	}
}
