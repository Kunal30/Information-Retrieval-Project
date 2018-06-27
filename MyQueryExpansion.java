import org.terrier.querying.FeedbackSelector;
import org.terrier.querying.QueryExpansion;


public class MyQueryExpansion extends QueryExpansion {

	public MyQueryExpansion(FeedbackSelector fbs)
	{
		this.selector=fbs;
	}
}
