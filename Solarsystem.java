package solarsystem;

import java.io.*;
import static java.lang.System.in;
import java.util.ArrayList;

/**
 *
 * @author Hanzheng Qin and My partner Junjie Chen
 */
public class Solarsystem {
     public ArrayList<planet> SolarSystem;
     public Solarsystem(){
         SolarSystem=new  ArrayList<>();
     }

    public static void main(String[] args) throws IOException{    
        FileReader fr=new FileReader("solarsystem.dat");
         Solarsystem sunSystem=new Solarsystem();
        BufferedReader br=new BufferedReader(fr);
        String line="";
        int lines=0;
        String[] arrs=null;        
        while ((line=br.readLine())!=null) {
            lines++;
            if(lines==2){
            line=line.replaceAll("\\t", " ");
            line=line.replaceAll(" +", " ");
            arrs=line.trim().split(" ");        
            planet NEWplanet=new planet(arrs[0],arrs[1],arrs[2],arrs[3],arrs[4]);
            sunSystem.SolarSystem.add(NEWplanet);
            System.out.println(arrs[0]+" "+arrs[1]+" "+arrs[2]+" "+arrs[3]+" "+arrs[4]);
            }
            
             if(lines==3){
            line=line.replaceAll("\\t", " ");
            line=line.replaceAll(" +", " ");
            arrs=line.trim().split(" ");         
            planet NEWplanet=new planet(arrs[0],arrs[1],arrs[2],arrs[3],arrs[4]);
            sunSystem.SolarSystem.add(NEWplanet);
            System.out.println(arrs[0]+" "+arrs[1]+" "+arrs[2]+" "+arrs[3]+" "+arrs[4]);
            }
             
              if(lines==5){
            line=line.replaceAll("\\t", " ");
            line=line.replaceAll(" +", " ");
            arrs=line.trim().split(" ");          

            planet NEWplanet=new planet(arrs[0],arrs[1],arrs[2],arrs[3],arrs[4]);
            sunSystem.SolarSystem.add(NEWplanet);
            System.out.println(arrs[0]+" "+arrs[1]+" "+arrs[2]+" "+arrs[3]+" "+arrs[4]);
            }
              
               if(lines==6){
            line=line.replaceAll("\\t", " ");
            line=line.replaceAll(" +", " ");
            arrs=line.trim().split(" ");

            planet NEWplanet=new planet(arrs[0],arrs[1],arrs[2],arrs[3],arrs[4]);
            sunSystem.SolarSystem.add(NEWplanet);
            System.out.println(arrs[0]+" "+arrs[1]+" "+arrs[2]+" "+arrs[3]+" "+arrs[4]);
            }
            
    }
       br.close();
        fr.close(); 
 }
}

   class planet{
        private String name;
        private String mass;
        private String Diameter;
        private String orbit;
        private String distance;
        public planet(){
        }
        public planet (String name ,String mass,String Diameter, String orbit,String distance){
            this.name=name;
            this.mass=mass;
            this.Diameter=Diameter;
            this.orbit=orbit;
            this.distance=distance;
        }

   }
