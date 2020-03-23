package edu.asu.heal.reachv3.api.models;

import edu.asu.heal.core.api.models.IHealModelType;

import java.util.List;

public class SUDSQuestion implements IHealModelType {

	int questionId;
	String question;
	String questionType;
	String response;
	List<SudsOption> options;

	
	public SUDSQuestion(){}

	public SUDSQuestion(String question, String questionType, String response) {
		this.question = question;
		this.questionType = questionType;
		this.response = response;

	}

    public SUDSQuestion(String question, String questionType, String response, List<SudsOption> options) {
        this.question = question;
        this.questionType = questionType;
        this.response = response;
        this.options = options;
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

    public List<SudsOption> getOptions() {
        return options;
    }

    public void setOptions(List<SudsOption> options) {
        this.options = options;
    }

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "SUDSQuestion{" +
				", questionId='" + questionId + '\'' +
				", question='" + question + '\'' +
				", questionType='" + questionType + '\'' +
				", response='" + response + '\'' +
				'}';
	}
}
