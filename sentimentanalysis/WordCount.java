package stubs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class WordCount extends Configured implements Tool{
	
  public static void main(String[] args) throws Exception {
		int exitCode = ToolRunner.run(new Configuration(), new WordCount(), args);
		System.exit(exitCode);
	}
	  
  public int run(String[] args) throws Exception {

    if (args.length != 2) {
      System.out.printf(
          "Usage: WordCount <input dir> <output dir>\n");
      System.exit(-1);
    }
    
    Job job = new Job(getConf());
    
    job.setJarByClass(WordCount.class);
    
    job.setJobName("Word Count Partitioner");

    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    job.setMapperClass(WordMapper.class);
    job.setPartitionerClass(SentimentPartitioner.class);
    job.setReducerClass(SumReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    
    job.setNumReduceTasks(3);
    
    boolean success = job.waitForCompletion(true);
    //System.exit(success ? 0 : 1);
    return success ? 0 : 1;
  }
}
