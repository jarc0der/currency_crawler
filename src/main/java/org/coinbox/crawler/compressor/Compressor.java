package org.coinbox.crawler.compressor;

import java.io.File;
import java.util.List;

public interface Compressor {

	boolean compress(List<File> files);
	
}
