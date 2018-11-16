
package find;
import java.io.*;
import java.util.*;

/**
 *Cite: Yuhao Zhu
 * @author Spirit
 * Thanks to my friend Siyu Chen illastrate the hashmap.
 */
public class find {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader("E:\\Frankenstein.txt"));
        String content = "";
        ArrayList<String> str = new ArrayList<String>();
        HashMap<String,Integer> common = new HashMap<>();
        HashMap<String,Integer> wordCount = new HashMap<>();
        Scanner br1 = new Scanner(new BufferedReader(new FileReader("E:\\shortwords.txt")));
        while(br1.hasNext()){
            str.add(br1.next());    
        }
        for(int i = 0;i < str.size(); i++){
            common.put(str.get(i),0);   
        }
        while((content=br.readLine())!=null){
            String regex = "[ \\?\\.\\,\\:\\;\\!\\(\\)]+";
            String[] content1 = content.split(regex);
            for(String c:content1){
                if(common.containsKey(c)){
                    Integer count = common.get(c);
                    count++;
                    common.put(c,count);
                }
            }
        }
        for(String key :common.keySet()){
            System.out.println("times of the words of "+ key +" is "+ common.get(key));
        }
        br.close();
        BufferedReader br2 = new BufferedReader(new FileReader("E:\\Frankenstein.txt"));
        ArrayList<String> array1 = new ArrayList<String>();
        while((content=br2.readLine()) != null){
            String[] content1 = content.split("[ \\.\\?\\,\\:\\;\\!\\(\\)]+");
            for(int i=1;i<content1.length;i++){
                array1.add(content1[i]);
            }
        }
        String[] array1_str = (String[]) array1.toArray(new String[0]);
        for(String key : common.keySet()){
            ArrayList<String> before_after = new ArrayList<>();
            for(int i=0; i < array1_str.length; ++i){
                if(key.equals(array1_str[i])){
                    before_after.add(array1_str[i - 1]);
                    before_after.add(array1_str[i + 1]);
                }
            }
            for(String ba : before_after){
                if(wordCount.containsKey(ba)){
                    wordCount.put(ba,wordCount.get(ba)+1 );
                }
                else
                    wordCount.put(ba,1);
            }
            for(String key1:wordCount.keySet()){
                if( wordCount.get(key1) >= 50){
                    System.out.println("follow or precede the common word "+ key +" is "+key1 + "==>" + wordCount.get(key1));
                }
            }
        }
    }
    
}
