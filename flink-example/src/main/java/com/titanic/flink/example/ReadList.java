package com.titanic.flink.example;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Row;

import java.util.ArrayList;

/**
 * 集合中读取数据
 */
public class ReadList
{
    public static void main(String[] args) throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        ArrayList<Row> list = new ArrayList<Row>();
        list.add(Row.of("3", 3, 7));
        list.add(Row.of("5", 2, 6));


        DataStream<Row> data = env.fromCollection(list);

        data.print();
        env.execute("load List");
    }
}
