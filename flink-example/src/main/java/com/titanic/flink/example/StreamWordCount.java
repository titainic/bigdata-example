package com.titanic.flink.example;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * 实时计算
 */
public class StreamWordCount
{
    public static void main(String[] args) throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        String host = parameterTool.get("host");
        int port = parameterTool.getInt("port");

        DataStream<String> inputStream = env.socketTextStream(host, port);

        DataStream<Tuple2<String,Integer>> resultStream = inputStream.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>()
        {
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception
            {
                String[] words = s.split(" ");

                for (String word : words)
                {
                    collector.collect(new Tuple2<String, Integer>(word,1));
                }
            }
        }).keyBy(0).sum(1).setParallelism(2).slotSharingGroup("red");

        resultStream.print();
        env.execute();
    }
}
