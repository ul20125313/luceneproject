package com;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class convertqrytoArraylist
{

    public ArrayList<HashMap<String, String>> getArraylist() throws IOException
    {
        FileInputStream file = new FileInputStream("../cran.qry");
        InputStreamReader reader = new InputStreamReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = "";
        ArrayList<HashMap<String, String>> arr = new ArrayList<>();

        String contentTitle = "";
        String contentW = "";


        line = bufferedReader.readLine();


        //while(line !=null)
        //for (int t = 0; t < 365; t++)

        int cnt=0;
        while(line !=null)
        {
            //Check whether the first two characters are category name such as .T
            HashMap<String, String> hm = new HashMap<>();
            String classString = new StringBuilder().append(line.charAt(0)).append(line.charAt(1)).toString();
            //System.out.println(line);
            if (classString.contentEquals(".I"))
            {
                String[] parts = line.split(".I ");
                hm.put("id", parts[1]);
                //System.out.println(parts[1]);
                line = bufferedReader.readLine();
                classString = new StringBuilder().append(line.charAt(0)).append(line.charAt(1)).toString();
                if(classString.contentEquals(".W"))
                {
                    while(!classString.contentEquals(".I")&&line!=null)
                    {
                        if(line==null)break;
                        if((line= bufferedReader.readLine())==null||classString.contentEquals(".I"))break;
                        classString = new StringBuilder().append(line.charAt(0)).append(line.charAt(1)).toString();
                        if(!classString.contentEquals(".I"))contentW = contentW +line;
                    }
                    contentW = contentW.replaceAll("\\p{Punct}", "");//remove the punctuation
                    hm.put("description",contentW);

                    //System.out.println(contentW);
                    contentW = "";

                }
                hm.put("queryID", String.valueOf(cnt+1));
                cnt ++;

            }
            //use the arraylist to store the hashmap;
            //System.out.println(hm.get("id"));
            //every iteration, the arr will add one hashmap.
            arr.add(hm);
            //break;
        }

        bufferedReader.close();
        return arr;

    }
}

