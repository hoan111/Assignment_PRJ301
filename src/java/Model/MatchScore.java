/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author hoan
 */
public class MatchScore {
    private String ctName;
    private String tName;
    private int ctScore;
    private int tScore;

    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public int getCtScore() {
        return ctScore;
    }

    public void setCtScore(int ctScore) {
        this.ctScore = ctScore;
    }

    public int gettScore() {
        return tScore;
    }

    public void settScore(int tScore) {
        this.tScore = tScore;
    }
    
    

}
