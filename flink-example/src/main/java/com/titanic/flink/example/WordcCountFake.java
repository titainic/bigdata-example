package com.titanic.flink.example;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class WordcCountFake
{
    public static void main(String[] args) throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("binend1", 8081, "flink-example/target/flink-example-1.0-SNAPSHOT.jar");


//        StreamExecutionEnvironment env = StreamExecutionEnvironment.
//        DataStreamSource<String> ds = env.readTextFile("s3://config/input.txt");
        DataStreamSource<String> ds = env.readTextFile("hdfs://binend1:8020/config/input.txt");

        SingleOutputStreamOperator<Tuple2<String, Integer>> wordAndOne = ds.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>()
        {
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception
            {
                String[] arrayStr = s.split(" ");

                for (String s1 : arrayStr)
                {
                    Tuple2<String, Integer> tu = Tuple2.of(s1, 1);
                    collector.collect(tu);
                }
            }
        });

        KeyedStream<Tuple2<String, Integer>, String> streamKS = wordAndOne.keyBy(new KeySelector<Tuple2<String, Integer>, String>()
        {
            public String getKey(Tuple2<String, Integer> stringIntegerTuple2) throws Exception
            {

                return stringIntegerTuple2.f0;
            }
        });

        SingleOutputStreamOperator<Tuple2<String, Integer>> sum = streamKS.sum(1);

        sum.print();

        env.execute();


    }
}
