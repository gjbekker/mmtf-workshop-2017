package edu.sdsc.mmtf.exercises;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.col;

public class Solution12 {

	/** Creating dataset example.
	 *
	 * @author Yana Valasatava
	 */

	private String getDataFile() {
		ClassLoader classLoader = getClass().getClassLoader();
		return classLoader.getResource("data.csv").getFile();
	}

	public static void main(String[] args) {

		SparkSession sparkSession = SparkSession.builder().master("local[*]").appName("app")
				.config("spark.driver.maxResultSize", "4g")
				.config("spark.executor.memory", "4g")
				.getOrCreate();

		// TODO: create a dataset by reading a .csv file

		Solution12 s = new Solution12();
		String path = s.getDataFile();

		Dataset<Row> data = sparkSession.read().option("header", "true").csv(path);

		data.printSchema();
		data.show(10);
		System.out.println("Total number of rows: "+data.count());

		// TODO: use filter function to get genes living on negative strand

		Dataset<Row> negStr = data.filter(col("orientation").equalTo("-"));
		negStr.show(5);
		System.out.println("Total number of rows for genes living on negative strand: "+negStr.count());

		sparkSession.stop();
	}
}
