
public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }

    public String toString(){
        String ret = "";
        // TODO: Complete this method
        for(int i=0;i<myWords.length;++i){
            ret+=myWords[i]+" ";
        }
        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        // TODO: Complete this method
        if(this.length()!=other.length()){
            return false;
        }
        for(int i=0;i<myWords.length;++i){
            if(!myWords[i].equals(other.wordAt(i))){
                return false;
            }
        }
        return true;

    }
    
    public int length(){
        // TODO: Complete this method
        return myWords.length;
    }
    
    public WordGram shiftAdd(String word) { 
        WordGram out = new WordGram(myWords, 0, myWords.length);
        // shift all words one towards 0 and add word at the end.
        String []w=new String[myWords.length];
        System.arraycopy(myWords,1,w,0,myWords.length-1);
        w[myWords.length-1]=word;
        out=new WordGram(w,0,myWords.length);
        // you lose the first word
        // TODO: Complete this method
        return out;
    }

}