package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


import org.apache.lucene.analysis.Analyzer;


public class Main
{
    public static  void main(String args[]) throws Exception
    {
        TestIndexManager testIndexManager;
        TestIndexManager tm = new TestIndexManager();
        tm.createIndex();

//there is something wrong with the qrel file, so I create this method to add a column to qrel file.
        modifyCranqrel mc = new modifyCranqrel();
        mc.modify();

        System.out.println("Please select the type of Similarity:\n"+
                "1---BM25Similarity()\n"+
                "2---BooleanSimilarity()\n"+
                "3---ClassicSimilarity()\n" +
                "4---LMDirichletSimilarity()\n"+
                "5---TFIDF");
        Scanner sc = new Scanner(System.in);
        String code = sc.nextLine();


        convertqrytoArraylist conarr = new convertqrytoArraylist();
        ArrayList<HashMap<String,String>> arr = conarr.getArraylist();

        if(code.contentEquals("1"))
        {
            System.out.println("you have selected the BM25Similarity");
        }
        if(code.contentEquals("2"))
        {
            System.out.println("you have selected the BooleanSimilarity");
        }
        if(code.contentEquals("3"))
        {
            System.out.println("you have selected the ClassicSimilarity");
        }
        if(code.contentEquals("4"))
        {
            System.out.println("you have selected the LMDirichletSimilarity");
        }


        if(code.contentEquals("5"))
        {
            System.out.println("you have selected the tfidf scoring algorithm, so input the query please");
            Scanner sc2 = new Scanner(System.in);
            String queryString = sc2.nextLine();
            Searcher searcher = new Searcher(arr,code);
            searcher.indexsearcher2VSM(queryString);

        }

        if(!code.contentEquals("1")&&!code.contentEquals("2")&&!code.contentEquals("3")&&!code.contentEquals("4")&&!code.contentEquals("5"))
        {
            System.out.println("you input the wrong code");

        }

        else{
            Searcher searcher = new Searcher(arr,code);
            searcher.indexsearcher();
        }
    }





}
