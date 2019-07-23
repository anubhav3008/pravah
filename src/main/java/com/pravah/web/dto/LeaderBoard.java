package com.pravah.web.dto;

public class LeaderBoard {
    private String name;
    private int tmod;
    private int ttm;
    private int ahCounter;
    private int grammarian;
    private int generalEvaluator;
    private int timer;
    private int speech;
    private int evaluator;
    private int total;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTmod() {
        return tmod;
    }

    public void setTmod(int tmod) {
        this.tmod = tmod;
    }

    public int getTtm() {
        return ttm;
    }

    public void setTtm(int ttm) {
        this.ttm = ttm;
    }

    public int getAhCounter() {
        return ahCounter;
    }

    public void setAhCounter(int ahCounter) {
        this.ahCounter = ahCounter;
    }

    public int getGrammarian() {
        return grammarian;
    }

    public void setGrammarian(int grammarian) {
        this.grammarian = grammarian;
    }

    public int getGeneralEvaluator() {
        return generalEvaluator;
    }

    public void setGeneralEvaluator(int generalEvaluator) {
        this.generalEvaluator = generalEvaluator;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getSpeech() {
        return speech;
    }

    public void setSpeech(int speech) {
        this.speech = speech;
    }

    public int getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(int evaluator) {
        this.evaluator = evaluator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LeaderBoard(){}
    public LeaderBoard(int tmod, int ttm, int ahCounter, int grammarian, int generalEvaluator, int timer, int speech, int evaluator, String name) {
        this.tmod = tmod;
        this.ttm = ttm;
        this.ahCounter = ahCounter;
        this.grammarian = grammarian;
        this.generalEvaluator = generalEvaluator;
        this.timer = timer;
        this.speech = speech;
        this.evaluator = evaluator;
        this.name = name;
    }
}
