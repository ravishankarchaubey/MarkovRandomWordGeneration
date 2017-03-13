import java.io.*;
import java.util.*;
/**
 * Write a description of EfficientMarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EfficientMarkovWord implements IMarkovModel{
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<Integer,ArrayList<String>> map;
    
    public EfficientMarkovWord(int seed) {
        myRandom = new Random();
        myOrder=seed;
        map=new HashMap<Integer,ArrayList<String>>();
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
        buildMap();
        for(int k=0; k < numWords-myOrder; k++){
            ArrayList<String> follows = getFollows(key);
            //System.out.println(key+":"+follows);
            if (follows==null || follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = key.shiftAdd(next);
        }
        
        map.clear();
        return sb.toString().trim();
    }
    
    private ArrayList<String> getFollows(WordGram kGram) {
        String s=kGram.toString();
        int hCode=s.hashCode();
        ArrayList<String> follows=map.get(hCode);
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
    
    public int hashCode(WordGram wg){
        String s=wg.toString();
        return s.hashCode();
    }
    
    public void buildMap(){
        for(int i=0;i<=myText.length-myOrder;++i){
            WordGram wg=new WordGram(myText,i,myOrder);
            String s=wg.toString();
            int hCode=s.hashCode();
            if(!map.containsKey(hCode)){
                ArrayList<String> follows=new ArrayList<String>();
                int pos=0;
                while(pos<myText.length){
                    int start=indexOf(myText,wg,pos);
                    if(start==-1){
                        break;
                    }
                    if(start+wg.length()>=myText.length-1){
                        break;
                    }
                    String next=myText[start+wg.length()];
                    follows.add(next);
                    pos=start+wg.length()+1;
                }
                map.put(hCode,follows);
            }
        }
        printHashMapInfo();
    }
    
    public void printHashMapInfo(){
        /*for(Integer i:map.keySet()){
            System.out.println(i+":"+map.get(i));
        }*/
        System.out.println("no of keys:"+map.size());
        int max=0;
        ArrayList<Integer> maxAl=new ArrayList<Integer>();
        for(Integer i:map.keySet()){
            if(map.get(i).size()>max){
                max=map.get(i).size();
            }
        }
        /*for(Integer i:map.keySet()){
            if(map.get(i).size()==max){
               maxAl.add(i);
            }
        }*/
        System.out.println("max no of element: "+max);
        //System.out.println(maxAl);
    }
}
