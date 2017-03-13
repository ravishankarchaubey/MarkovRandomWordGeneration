import java.util.*;
import java.io.*;
/**
 * Write a description of MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MarkovWord implements IMarkovModel{
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    
    public MarkovWord(int seed) {
        myRandom = new Random();
        myOrder=seed;
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
        WordGram key = new WordGram(myText,index,myOrder);
        sb.append(key);
        sb.append(" ");
        //System.out.println(key);
        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = getFollows(key);
            //System.out.println(key+":"+follows);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = key.shiftAdd(next);
        }
        
        return sb.toString().trim();
    }
    
    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos=0;
        //System.out.println("getfollows called"+kGram);
        //System.out.println(myText.length);
        while(pos<myText.length){
            int start=indexOf(myText,kGram,pos);
            //System.out.println("start "+ start+" pos "+pos);
            if(start==-1){
                break;
            }
            if(start+kGram.length()>=myText.length-1){
                break;
            }
            String next=myText[start+kGram.length()];
            follows.add(next);
            pos=start+kGram.length()+1;
        }
        return follows;
    }

    private int indexOf(String []words,WordGram target,int start){
        //System.out.println("indexof called+"+target);
        for(int i=start;i<=words.length-target.length();i+=1) {
			WordGram wg =new WordGram(words,i,target.length());
			if(target.equals(wg)){
			    return i;
			}
		}
        return -1;
    }
    
    public int hashCode(){
        int hashCode=0;
        String s="sample";
        return s.hashCode();
    }
}
