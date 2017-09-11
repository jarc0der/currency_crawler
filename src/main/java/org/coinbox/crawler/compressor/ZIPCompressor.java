package org.coinbox.crawler.compressor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.coinbox.crawler.app.Config;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZIPCompressor implements Compressor{

	@Override
	public boolean compress(List<File> files) {
		try {
			ZipFile zipFile = new ZipFile(Config.ZIP_PATH);
			ZipParameters parameters = new ZipParameters();
			
			// set compression method to store compression
	        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

	        // Set the compression level
	        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
	        
	        zipFile.createZipFile((ArrayList<File>)files, parameters);
	        
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

}
