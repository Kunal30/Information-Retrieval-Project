import org.terrier.applications.TRECQuerying;
import org.terrier.structures.Index;

public class MyTrecQuerying extends TRECQuerying {

	public MyTrecQuerying(Index index) {
		super(index);
	}

	public void setWeighingModel(String model) {
		this.wModel = model;
	}
}
