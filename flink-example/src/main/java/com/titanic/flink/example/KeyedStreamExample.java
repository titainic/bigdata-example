package com.titanic.flink.example;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Row;

import java.util.ArrayList;

public class KeyedStreamExample
{
    public static void main(String[] args) throws Exception
    {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("titanic", 18081, "flink-example/target/flink-example-1.0-SNAPSHOT.jar");

        ArrayList<String> list = new ArrayList<String>();
        list.add("sensor_1,1547718199,35.8");
        list.add("sensor_6,1547718201,15.4");
        list.add("sensor_7,1547718202,6.7");
        list.add("sensor_10,1547718205,38.1");
        list.add("sensor_1,1547718207,36.3");
        list.add("sensor_1,1547718209,32.8");
        list.add("sensor_1,1547718212,37.1");

        DataStream<String> data = env.fromCollection(list);

        DataStream<Row> dataStream = data.map(new MapFunction<String, Row>()
        {
            public Row map(String s) throws Exception
            {
                String[] field = s.split(",");
                String id = field[0];
                Long ti = new Long(field[1]);
                Double w = new Double(field[2]);

                return Row.of(id,ti,w);
            }
        });
        dataStream.print();
//        KeyedStream<Row, Tuple> keyedStream = resultSet.keyBy(0);

        env.execute("KeyedStreamExample");
    }
}
