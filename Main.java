import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.Map.Entry;

//ngrams(3, "abcde") = ["abc", "bcd", "cde"]
class Ngrams {
    public static List<String> ngrams(int n, String str) {
        List<String> ngrams = new ArrayList<String>();
        for (int i = 0; i < str.length() - n + 1; i++){
            // Add the substring or size n
            ngrams.add(str.substring(i, i + n));
        // In each iteration, the window moves one step forward
        // Hence, each n-gram is added to the list
        }
        return ngrams;
    }
}

class Hashing {
    // d is the number of characters in the input alphabet
    public final static int d = 256;
    public final static int q = 1007;

    public static Hashtable<Integer, String> doHashing(String inputGiven) {

        Hashtable<Integer, String> ht = new Hashtable<Integer, String>();

        inputGiven = Arrays.stream(inputGiven.split("\\s+")).distinct().collect(Collectors.joining(" "));

        // remove unwanted part left out
        String words[] = { "is", "sample", "an", "or", "and", "of", "to", "that", "a","the","off" };

        for (String word : words) {

            word = "(?i)\\s*\\b" + word + "\\b\\s*";
            inputGiven = inputGiven.replaceAll(word, " ");

        }

        String[] setOfWords = inputGiven.trim().split("\\s+");

        // int inputGivenerenceWordsLength = setOfWords.length;
        
        //System.out.println(setOfWords+"\n....\n");

        for (int i = 0; i < setOfWords.length; i++) {

            String s = setOfWords[i];
            List<String> ngrams = Ngrams.ngrams(3, s);
            for (String ngram : ngrams) {

                int hashOfWord = 0;
               // hashOfWord = (d * hashOfWord + ngram.charAt(i)) % q;
                for (int j = 0; j < ngram.length(); j++) {
                    hashOfWord = (d * hashOfWord + ngram.charAt(j)) % q;

                }
                // System.out.println(hashOfWord+"\n....\n");
                // System.out.println(ngram+"\n....\n");
                ht.put(hashOfWord, ngram);

            }

        }

        return ht;

    }
}

class doChecking{

    public static int matching(Hashtable<Integer, String> firstHashTable ,Hashtable<Integer, String> secondHashTable)
    {
        // Set<Entry<String, String>> entires = secondHashTable.entrySet();
        // for(Entry<String,String> ent:entires){
        //     System.out.println(ent.getKey()+" ==> "+ent.getValue());
        // }
       
            int chkcount = 0;
            for(Entry<Integer,String> entry:secondHashTable.entrySet()){
                String presentInFirst = firstHashTable.get(entry.getKey());
                String presentInSecond = entry.getValue();
                if(presentInSecond != null && presentInFirst != null && presentInSecond.equals(presentInFirst)){
                    chkcount = chkcount + 1;
                    
                }
            }
            System.out.println(chkcount);

            return chkcount;
    }
}


public class Main {
    public static void main(String[] args) {

       

        // String inputOne = " plagiarism is an act or instance of using or closely imitating the language and"
        //         + "thoughts of another author without authorization.";

        // String inputTwo = "plagiarism is an act of copying the"
        //         + "ideas or words of another person without giving credit to  that person.";

        String firstFile;
        String secondFile;
      
        String inputOne= "";
        String inputTwo = "";
        Scanner scan= new Scanner(System.in);
        
        System.out.println("Enter location of test text file:");
        
        firstFile = scan.nextLine();
        
        System.out.println("\n -> You entered: \"" + firstFile + "\"");
        System.out.println("\n-> Enter location of reference text file :");
        
        secondFile = scan.nextLine();
        
        System.out.println("\n\nYou entered: \"" + secondFile + "\"");
        
       try
       {
        FileReader fr = new FileReader(firstFile);
        int j;
        while ((j=fr.read())!=-1)
        {
           inputOne+=(Character.toString((char) j));
        }   
        fr.close();
       }
        
        catch (IOException e)
        {
           System.out.println("File not found");
        }
        
  
        try
       {
        FileReader fr1 = new FileReader(secondFile);
        int j;
        while ((j=fr1.read())!=-1)
        {
           inputTwo+=(Character.toString((char) j));
        } 
        fr1.close();  
       }
        
        catch (IOException e)
        {
           System.out.println("File not found");
        }
  

        Hashtable<Integer, String> htOne = Hashing.doHashing(inputOne);

        Hashtable<Integer, String> htTwo = Hashing.doHashing(inputTwo);


        int totalMatch=doChecking.matching(htOne,htTwo);

        int firstHashTableSize=htOne.size();
        int secondHashTableSize=htTwo.size();

       
        float plagarismScore=(2*(float)totalMatch*100)/(float)(firstHashTableSize+secondHashTableSize);

        
        System.out.println("\n -> First hash Table Size is :  "+ firstHashTableSize);
        System.out.println("\n -> Second hash Table Size is :  "+ secondHashTableSize);
        System.out.println("\n -> Total Match  is :  "+ totalMatch);

        System.out.println(".........................................");
        System.out.println("\n The plagarismScore is "+ plagarismScore);

        // System.out.println("\n\n"+txt+"\n\n");
        // Function Call
        // RabinKarp.search(pat, txt, q);

        System.out.println(".........................................\n\n");
        scan.close();
    }
}
