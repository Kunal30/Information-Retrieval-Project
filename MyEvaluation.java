import java.io.*;

import org.terrier.evaluation.AdhocEvaluation;
public class MyEvaluation {
public static void main(String args[])throws IOException
{
	AdhocEvaluation myeval=new AdhocEvaluation("/home/kunal/Desktop/IR_Project/Qrels/ZF_51-200/ZF_QREL");
	myeval.initialise();
	myeval.evaluate("/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_combMNZresult_ZF1.res");
	myeval.writeEvaluationResultOfEachQuery("/home/kunal/Desktop/terrier-3.6/var/results/combMNZ_eachqry.eval");
	
	AdhocEvaluation myeval1=new AdhocEvaluation("/home/kunal/Desktop/IR_Project/Qrels/ZF_51-200/ZF_QREL");
	myeval1.initialise();
	myeval1.evaluate("/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_interpolationresult_ZF1.res");
	myeval1.writeEvaluationResultOfEachQuery("/home/kunal/Desktop/terrier-3.6/var/results/interpol_eachqry.eval");
	
	AdhocEvaluation myeval2=new AdhocEvaluation("/home/kunal/Desktop/IR_Project/Qrels/ZF_51-200/ZF_QREL");
	myeval2.initialise();
	myeval2.evaluate("/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_re_rankresult_ZF1.res");
	myeval2.writeEvaluationResultOfEachQuery("/home/kunal/Desktop/terrier-3.6/var/results/rerank_eachqry.eval");
	
	AdhocEvaluation myeval3=new AdhocEvaluation("/home/kunal/Desktop/IR_Project/Qrels/ZF_51-200/ZF_QREL");
	myeval3.initialise();
	myeval3.evaluate("/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_result_ZF1.res");
	myeval3.writeEvaluationResultOfEachQuery("/home/kunal/Desktop/terrier-3.6/var/results/init_eachqry.eval");
	
	AdhocEvaluation myeval4=new AdhocEvaluation("/home/kunal/Desktop/IR_Project/Qrels/ZF_51-200/ZF_QREL");
	myeval4.initialise();
	myeval4.evaluate("/home/kunal/Desktop/terrier-3.6/var/results/Retrieval_result_expanded_ZF1.res");
	myeval4.writeEvaluationResultOfEachQuery("/home/kunal/Desktop/terrier-3.6/var/results/expanded_eachqry.eval");
	
}
}
