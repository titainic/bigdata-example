package com.titanic.flink.example;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

//flink示例
public class WDFlinkExample
{
    public static void main(String[] args) throws Exception
    {
        //创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        String inputPath = "flink-example/src/main/resources/hello.txt";

        DataSet<String> inputDS = env.readTextFile(inputPath);

        DataSet<Tuple2<String, Integer>> resultSet = inputDS.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>()
        {
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception
            {
                String[] words = s.split(" ");

                for (String word : words)
                {
                    collector.collect(new Tuple2<>(word,1));
                }
            }
        }).groupBy(0).sum(1);
        resultSet.print();


    }
}
