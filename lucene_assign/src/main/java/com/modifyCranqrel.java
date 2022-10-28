package com;

import java.io.*;

public class modifyCranqrel {
    public void modify() throws Exception {
        FileInputStream inputfile = new FileInputStream("D:\\IDEA_Workspace\\luceneproject1\\src\\main\\resources\\cran\\cranqrel");
        InputStreamReader reader = new InputStreamReader(inputfile);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line;
        line = bufferedReader.readLine();

        File file = new File("D:\\IDEA_Workspace\\luceneproject1\\src\\main\\resources\\modfiedCranqrel");
        FileWriter fw = new FileWriter(file,false);
        BufferedWriter bw = new BufferedWriter(fw);

        while(line !=null)
        {
            String[] parts = line.split(" ");
            line = bufferedReader.readLine();
            bw.write(parts[0]+" 0 "+parts[1]+" "+parts[2]+"\n");

        }
        bufferedReader.close();
        reader.close();

        bw.close();
        fw.close();

    }


}
