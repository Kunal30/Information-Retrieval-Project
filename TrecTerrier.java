import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.terrier.applications.TRECQuerying;
import org.terrier.indexing.BasicIndexer;
import org.terrier.indexing.Indexer;
import org.terrier.matching.MatchingQueryTerms;
import org.terrier.matching.ResultSet;
import org.terrier.querying.FeedbackDocument;
import org.terrier.querying.FeedbackSelector;
import org.terrier.querying.Manager;
import org.terrier.querying.Request;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.Index;
import org.terrier.structures.MetaIndex;
import org.terrier.structures.TRECQuery;
import org.terrier.utility.ApplicationSetup;
import org.terrier.indexing.Collection;
import org.terrier.indexing.SimpleFileCollection;

public class TrecTerrier {
	static int value=1;

	public static void main(String[] args) throws IOException {

		/*---------------Defining Output Stream For Original Query Retrieval-------------------*/
		
		FileOutputStream fop = null;
		File file;
		String res_output_filename="/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_result_ZF"+value+".res";//In_expC2_2.res";
		
		file = new File(res_output_filename);
		fop = new FileOutputStream(file);

		// if file doesn't exist, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		
		
		/*---------------Defining Output Stream For Query Expansion Retrieval-------------------*/
		
		FileOutputStream fop_exp = null;
		File file_exp;
		String res_exp="/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_result_expanded_ZF"+value+".res";//In_expC2_2.res";
		
		file_exp = new File(res_exp);
		fop_exp = new FileOutputStream(file_exp);

		// if file doesn't exist, then create it
				if (!file_exp.exists()) {
					file_exp.createNewFile();
				}
		
	    //loading in the terrier.properties file
		
		InputStream in = new FileInputStream("/home/kunal/Desktop/terrier-3.6/etc/terrier.properties");
		ApplicationSetup.configure(in);
		// Indexing the documents
		
		// Directory containing files to index
		/*------------- Load the index ----------------*/
		Index index = Index.createIndex("/home/kunal/Desktop/terrier-3.6/var/index", "data");
		System.out.println(index.getCollectionStatistics());		
		System.out.println(index.getProperties());

		/*------------- Original Retrieval Part starts here ----------------*/
		Manager queryingManager = new Manager(index);
		final MetaIndex metaIndex = index.getMetaIndex();
		final String metaIndexDocumentKey = ApplicationSetup.getProperty("trec.querying.outputformat.docno.meta.key", "docno");

		// Get queryIds
		TRECQuery trecquery = new TRECQuery();
		String[] queryID = trecquery.getQueryIds();

		MyTrecQuerying trecQuerying = new MyTrecQuerying(index);
		trecQuerying.setWeighingModel("In_expC2");

		for (String qID : queryID) {
			SearchRequest srq = trecQuerying.processQuery(qID,trecquery.getQuery(qID));
			System.out.println(srq.getOriginalQuery());

			ResultSet result_BM25 = srq.getResultSet();
			int[] docId = result_BM25.getDocids();
			double scores[] = result_BM25.getScores();

			String[] docnos = metaIndex.getItems(metaIndexDocumentKey,result_BM25.getDocids());

			String query = srq.getQuery().toString();
			 for (int j = 0; j < docnos.length; j++) {
				String rank_list=(qID+" Q0 "+docnos[j] + " "+j+" "+scores[j]+" In_expC2"+"\n");
				
				
				// get the content in bytes
				byte[] contentInBytes = rank_list.getBytes();

				fop.write(contentInBytes);
			    fop.flush();
				
			}
	     /*------------- Original Retrieval Part ends here ----------------*/
			 
			 /*------------- Pseudo Relevance Feedback Retrieval Part starts here ----------------*/
			
				FeedbackSelector feedbackSelector = new FeedbackSelector() {

					@Override
					public FeedbackDocument[] getFeedbackDocuments(Request request) {
						ResultSet result = request.getResultSet();
						int[] docids = result.getDocids();
						double[] scores = result.getScores();

						String[] docNos = null;
						try {
							docNos = metaIndex.getItems(metaIndexDocumentKey, result.getDocids());
						} catch (IOException e) {
							e.printStackTrace();
						}

					
						ArrayList<FeedbackDocument> finalDocs = new ArrayList<FeedbackDocument>();
						for (int i = 0; i < 10; i++) {
							// TODO here score is not relevance score
							// change here to switch btw pseudorelevance and
							// relevance
						//	if (counter <= Constants.RELEVANCE_THRESHOLD) {
						//		if (relevantDocs.containsKey(docNos[i])) {
						//			finalDocs.add(new FeedbackDocument(docids[i], i, scores[i]));
						//		}
						//	} else {
								finalDocs.add(new FeedbackDocument(docids[i], i, scores[i]));
						//	}
						//	counter++;
						}
						return finalDocs.toArray(new FeedbackDocument[finalDocs.size()]);
					}
				};

				MatchingQueryTerms queryTerms = new MatchingQueryTerms(qID, (Request) srq);
				MyQueryExpansion qe = new MyQueryExpansion(feedbackSelector);
				qe.process(queryingManager, srq);
				qe.expandQuery(queryTerms, (Request) srq);
				ResultSet resultFeedback = srq.getResultSet();
				int[] docIdFeedback = resultFeedback.getDocids();
				double scoresFeedback[] = resultFeedback.getScores();
				String[] docnosFeedback = metaIndex.getItems(metaIndexDocumentKey, resultFeedback.getDocids());
				
				// Writing Feedback Results
				for (int j = 0; j < docIdFeedback.length; j++) {
					String str=(qID + " " + "Q0" + " " + docnosFeedback[j] + " " + j + " " + scoresFeedback[j]+ " " + queryingManager.getInfo(srq)+"\n");
					byte[] contentInBytes = str.getBytes();
					//System.out.println("Hello from the feedback printing part!!!!!!!");
					fop_exp.write(contentInBytes);
				    fop_exp.flush();
					//fop.close();		
		}
			//fop.close();
		/*------------------------- Pseudo Relevance Feedback Retrieval Ends Here------------------------------*/
			
	}
		fop.close();
		fop_exp.close();
}
}