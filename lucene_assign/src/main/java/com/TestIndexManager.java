package com;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class TestIndexManager {

    public void createIndex()throws IOException
    {

        convertArraylist ca = new convertArraylist();
        ArrayList<HashMap<String,String>> arr;
        arr =  ca.getArraylist();//get the data
        ArrayList<Document>docList = new ArrayList<>();
        //HashMap<String,String>Hm;
        for (HashMap<String,String> hs:arr)
        {
            //create the document object
            Document document = new Document();

            //create the fields and store them in the document object
            document.add(new StringField("id",hs.get("id"), Field.Store.YES));
            document.add(new TextField("Title",hs.get("Title"), Field.Store.YES));
            document.add(new TextField("name",hs.get("name"), Field.Store.YES));
            document.add(new TextField("department",hs.get("department"), Field.Store.YES));

            FieldType fieldType = new FieldType();
            fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            fieldType.setStored(true);
            fieldType.setTokenized(true);
            fieldType.setOmitNorms(true);
            fieldType.setStoreTermVectors(true);
            Field newField = new Field("description", hs.get("description"), fieldType);
            document.add(newField);

            docList.add(document);

        }
        //create the tokenizer
        Analyzer analyzer = new StandardAnalyzer();
        //create the directory object, the index will be this directory
        //Fsdirectory is abstract class, so I can make a new intance of it.

        Directory dir = FSDirectory.open(Paths.get("../index"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexwriter = new IndexWriter(dir, config);
        //write the documents to the index Directory
        for(Document doc: docList)
        {
            indexwriter.addDocument(doc);
        }
        //close the indexwriter
        indexwriter.close();
    }


}


