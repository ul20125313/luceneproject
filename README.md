# luceneproject
Before running this project, make sure that you have the java and maven.
commands:
sudo su -
cd luceneproject
cd lucene_assign
make package
cd target
java -jar lucene_assign-1.0-SNAPSHOT.jar 
(Then it will let you choose the Similarity methods, and after you input the method code, system will store your results in a specific path. However, there is 
something different with tfidf method. As for tfidf method, it won't use the cranqrel as the qrel. you need to input the qrel by yourself. You just need to input one
query, and the system will give you every queried document's tfidf score.)

trec_eval commands:
cd trec_eval 
./trec_eval -q -m map -m iprec_at_recall.0.1 modfiedCranqrel ../result1
