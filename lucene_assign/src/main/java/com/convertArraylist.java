package com;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class convertArraylist {

    public ArrayList<HashMap<String,String>> getArraylist() throws IOException
    {

        String filePath = "./cran1400";
        File fi = new File(filePath);
        System.out.println(fi.exists());
        FileInputStream file = new FileInputStream(fi);
        InputStreamReader reader = new InputStreamReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = "";
        ArrayList<HashMap<String,String>>arr = new ArrayList<>();
        //int cnt = 0;
        String contentTitle = "";
        String contentB = "";
        String contentW = "";
        String contentA = "";

        line=bufferedReader.readLine();


        //while(line !=null)\
        for(int t = 0; t<1400;t++)
        {


            //Check whether the first two characters are category name such as .T
            HashMap<String,String> hm = new HashMap<>();
            String classString = new StringBuilder().append(line.charAt(0)).append(line.charAt(1)).toString();
            //System.out.println(line);
            if(classString.contentEquals(".I"))
            {
                String[] parts = line.split(".I ");
                hm.put("id",parts[1]);
                //System.out.println(parts[1]);
                line = bufferedReader.readLine();
                classString = new StringBuilder().append(line.charAt(0)).append(line.charAt(1)).toString();
                if(classString.contentEquals(".T"))
                {
                    while(!classString.contentEquals(".A"))
                    {
                        line = bufferedReader.readLine();
                        classString = new StringBuilder().append(line.charAt(0)).append(line.charAt(1)).toString();
                        if(classString.contentEquals(".A"))continue;
                        contentTitle = contentTitle +line;

                    }

                    //System.out.println(contentTitle);
                    hm.put("Title", contentTitle);
                    classString = new StringBuilder().append(line.charAt(0)).append(line.charAt(1)).toString();
                    contentTitle="";
                    if(classString.contentEquals(".A"))
                    {
                        while(!classString.contentEquals(".B"))
                        {
                            line = bufferedReader.readLine();
                            classString = new StringBuilder().append(line.charAt(0)).append(line.charAt(1)).toString();
                            if(classString.contentEquals(".B"))continue;
                            contentA= contentA +line;

                        }
                        hm.put("name",contentA);
                        classString = new StringBuilder().append(line.charAt(0)).append(line.charAt(1)).toString();
                        contentA="";
//                        contentTitle = "";
//                        line = bufferedReader.readLine();
//                        hm.put("name",line);
//                        line = bufferedReader.readLine();
//                        classString = new StringBuilder().append(line.charAt(0)).append(line.charAt(1)).toString();

                        if(classString.contentEquals(".B"))
                        {
                            while(!classString.contentEquals(".W"))
                            {
                                line = bufferedReader.readLine();
                                classString = new StringBuilder().append(line.charAt(0)).append(line.charAt(1)).toString();
                                if(classString.contentEquals(".W"))continue;
                                contentB = contentW +line;

                            }
                            hm.put("department",contentB);
                            contentB = "";

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

                        }

                    }
                }

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
