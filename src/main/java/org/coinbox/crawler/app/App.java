package org.coinbox.crawler.app;

import org.coinbox.crawler.core.ProcessorImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	long t1 = System.currentTimeMillis();
        new ProcessorImpl().process(Config.FILE_LINKS);
        long t2 = System.currentTimeMillis();
        
        System.out.println((t2 - t1) / 1000 + " sec.");
    }
}
