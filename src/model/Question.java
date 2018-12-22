package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
    public static final long serialversionUID = 1L;

    private String question;
    private List<String> statments;
    private Category category;
    private String feedback;

    public Question(String question, Category category, String feedback) {
        setQuestion(question);
        statments = new ArrayList<>();
        setCategory(category);
        setFeedback(feedback);
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        if(question == null || question.trim().isEmpty()) throw new DomainExeption();
        this.question = question;
    }

    public List<String> getStatments() {
        return statments;
    }

    public void addStatement(String statment) {
        this.statments.add(statment);
    }

    public void addStatements(List<String> statments) { this.statments.addAll(statments); }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        if(feedback == null || feedback.trim().isEmpty()) throw new DomainExeption();
        this.feedback = feedback;
    }

    public List<String> getStatements(){
        return this.statments;
    }

    public String getSolution() {
        return this.statments.get(0);
    }

    @Override
    public String toString() {
        return this.question;
    }

}
