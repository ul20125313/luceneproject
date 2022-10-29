package com;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;

import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.LMDirichletSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;


import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;





public class Searcher {
    private  String code;
    private ArrayList<HashMap<String,String>>queryArr;
    public Searcher(ArrayList<HashMap<String,String>> queryArr,String code)
    {
        this.queryArr = queryArr;
        this.code = code;
    }

    public void indexsearcher() throws  Exception
    {
        //create the analyzer
        Analyzer analyzer = new StandardAnalyzer();
        //The first parameter is analyzer, the second parameter is
        QueryParser queryp = new QueryParser("description",analyzer);

        Directory dir = FSDirectory.open(Paths.get("../index"));
        //create the inputstream object
        IndexReader reader = DirectoryReader.open(dir);


        File file = new File("./result1");
        FileWriter fw = new FileWriter(file,false);
        BufferedWriter bw = new BufferedWriter(fw);


        //System.out.println(queryArr.get(50));


        for( int i=0;i<queryArr.size();i++)
        {

            Query query = queryp.parse(queryArr.get(i).get("description"));

            IndexSearcher searcher = new IndexSearcher(reader);
            setSimilarity(searcher);

            TopDocs top = searcher.search(query,20);
            ScoreDoc[] scoreDocs = top.scoreDocs;
            if(scoreDocs!= null)
            {
                int rank = 1;
                for(ScoreDoc scoreDoc: scoreDocs)
                {
                    //lucene unique identifier, it is distributed randomly
                    int docID =scoreDoc.doc;
                    Document doc = searcher.doc(docID);
                    bw.write(queryArr.get(i).get("queryID")+" 1 "+doc.get("id")+" "+rank+" "+scoreDoc.score+" 1\n");
                    rank++;

                }
            }

        }
        bw.close();
        fw.close();
        //traverse the results
        reader.close();
        dir.close();

    }

    public void setSimilarity(IndexSearcher searcher) {
        switch (code){
            case "1":
                searcher.setSimilarity(new BM25Similarity());
                break;
            case "2":
                searcher.setSimilarity(new BooleanSimilarity());
                break;
            case "3":
                searcher.setSimilarity(new ClassicSimilarity());
                break;
            case "4" :
                searcher.setSimilarity(new LMDirichletSimilarity());
                break;
            default:
                break;
        }

    }

    public void indexsearcher2VSM(String queryString) throws  Exception
    {
        //create the analyzer
        Analyzer analyzer = new StandardAnalyzer();

        //The first parameter is analyzer, the second parameter is
        QueryParser queryp = new QueryParser("description",analyzer);
        Query query = queryp.parse("what similarity laws must be obeyed when constructing aeroelastic models\n" +
                "of heated high speed aircraft.");
        Directory dir = FSDirectory.open(Paths.get("../index"));
        //create the inputstream object
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs top = searcher.search(query,5);
        ScoreDoc[] scoreDocs = top.scoreDocs;
        String[] s =queryString.split(" ");

        ArrayList<HashMap<String,Long>>array;
        array = this.tfidf(scoreDocs, reader);
        for(int j = 0;j<array.size();j++)
        {
            int scores = 0;
            HashMap<String,Long>hm = array.get(j);
            for(int i=0;i<s.length;i++)
            {
                long score;
                if(hm.get(s[i]) ==null)
                {
                    score = 0;
                }
                else
                {
                    score = hm.get(s[i]);
                }
                scores+=score;
            }
            Document doc = searcher.doc(scoreDocs[j].doc);
            System.out.printf("tfidf score is %d in document %s \n",scores,doc.get("id"));
        }

        //traverse the results
        reader.close();
        dir.close();

    }

    public ArrayList<HashMap<String,Long>> tfidf(ScoreDoc[] scoreDocs, IndexReader reader)throws  Exception
    {
        ArrayList<HashMap<String,Long>> array= new ArrayList<>();
        if(scoreDocs!= null)
        {
            for(ScoreDoc scoreDoc: scoreDocs)
            {
                //lucene unique identifier, it is distributed randomly
                int docID =scoreDoc.doc;
                Fields fields = reader.getTermVectors(docID);
                HashMap<String, Long>hm = new HashMap<>();
                for(String field:fields)
                {

                    Terms terms = fields.terms(field);

                    BytesRef termByte = null;
                    TermsEnum termsEnum = terms.iterator();
                    //System.out.println(termsEnum);
                    while ((termByte = termsEnum.next()) != null)
                    {

                        int id;
                        // for each term retrieve its postings list
                        PostingsEnum posting = null;
                        posting = termsEnum.postings(posting, PostingsEnum.FREQS);
                        // This only processes the single document we retrieved earlier
                        while ((id = posting.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS)
                        {
                            // convert the term from a byte array to a string
                            String termString = termByte.utf8ToString();

                            // extract some stats from the index
                            Term term = new Term(field, termString);

                            long freq = posting.freq();
                            long docFreq = reader.docFreq(term);
                            //long totFreq = reader.totalTermFreq(term);
                            hm.put(termString, freq*(long)Math.sqrt((1400/docFreq)));
                        }
                    }
                }
                array.add(hm);
            }
        }
        return array;

    }


}







