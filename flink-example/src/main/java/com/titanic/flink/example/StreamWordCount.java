package com.titanic.flink.example;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * 实时计算
 *
 */
public class StreamWordCount
{
    public static void main(String[] args) throws Exception
    {
        //获取执行环节
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        //设置并行度
        env.setParallelism(8);

        //加载数据
        DataStream<String> inputDataStream = env.readTextFile("flink-example/src/main/resources/hello.txt");

        //类似于spark的flatMap处理
        DataStream<Tuple2<String, Integer>> resultDataStream = inputDataStream.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>()
        {
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception
            {
                // 按空格分词
                String[] words = s.split(" ");
                // 遍历所有word，包成二元组输出
                for (String word : words)
                {
                    collector.collect(new Tuple2<String, Integer>(word, 1));
                }
            }
        }).keyBy(0).sum(1);

        //打印
        resultDataStream.print();

        //执行流处理
        env.execute();
    }
}
