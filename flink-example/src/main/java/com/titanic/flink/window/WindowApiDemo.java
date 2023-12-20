package com.titanic.flink.window;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.StringData;
import org.apache.flink.types.Row;
import org.apache.hadoop.conf.Configuration;
import org.apache.iceberg.catalog.TableIdentifier;
import org.apache.iceberg.flink.CatalogLoader;
import org.apache.iceberg.flink.TableLoader;
import org.apache.iceberg.flink.source.IcebergSource;
import org.apache.iceberg.flink.source.StreamingStartingStrategy;
import org.apache.iceberg.flink.source.assigner.SimpleSplitAssignerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class WindowApiDemo
{
    public static void main(String[] args) throws Exception
    {
        //设置minio用户和链接
        Configuration hadoopConf = new Configuration();
        hadoopConf.set("fs.s3a.access.key", "admin");
        hadoopConf.set("fs.s3a.secret.key", "12345678");
        hadoopConf.set("aws.region", "us-east-1");
        hadoopConf.set("fs.s3a.endpoint", "http://binend9:8000");
        hadoopConf.set("fs.s3a.path.style.access", "true");

        //设置hive链接
        Map<String, String> properties = new HashMap<>();
        properties.put("type", "iceberg");
        properties.put("catalog-type", "hive");
        properties.put("property-version", "1");
        properties.put("warehouse", "s3a://hadoop/s3/hive/warehouse/");
        properties.put("uri", "thrift://binend4:9083");

        //catalog设置
        CatalogLoader catalogLoader = CatalogLoader.hive("iceberg_s3_hive", hadoopConf, properties);
        TableIdentifier identifier = TableIdentifier.of("default", "binendxx");
        TableLoader tableLoader = TableLoader.fromCatalog(catalogLoader, identifier);

        StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("binend1", 8081, "/Users/binend/soft/intellij_workspace/bigdata-example/flink-example/target/flink-example-1.0-SNAPSHOT.jar");
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();



        IcebergSource source = IcebergSource.forRowData()
                .tableLoader(tableLoader)
                .assignerFactory(new SimpleSplitAssignerFactory())
                .streaming(true)
                .streamingStartingStrategy(StreamingStartingStrategy.TABLE_SCAN_THEN_INCREMENTAL)
                .monitorInterval(Duration.ofSeconds(60))
                .build();

        DataStream<RowData> stream = env.fromSource(
                source,
                WatermarkStrategy.noWatermarks(),
                "hive iceberg",
                TypeInformation.of(RowData.class));

        KeyedStream<RowData, Row> keyedStream = stream.keyBy(new KeySelector<RowData, Row>()
        {
            @Override
            public Row getKey(RowData rowData) throws Exception
            {
                int fieldValue = rowData.getInt(11); // 获取字段值
                return Row.of(fieldValue); //
            }
        });
        // 基于时间的
        WindowedStream<RowData, Row, TimeWindow> window = keyedStream.window(TumblingProcessingTimeWindows.of(Time.seconds(10)));// 滚动窗口，窗口长度10s

        DataStream<RowData> reduce = window.reduce(new ReduceFunction<RowData>()
        {
            @Override
            public RowData reduce(RowData rowData, RowData t1) throws Exception
            {
                String a1Age = rowData.getString(3).toString();
                String a2Age = t1.getString(3).toString();

                int a1 = Integer.valueOf(a1Age);
                int a2 = Integer.valueOf(a2Age);

                int a3 = a2 +a1;

                GenericRowData row = new GenericRowData(4);

                row.setField(0,t1.getString(0));//表的id
                row.setField(1,t1.getInt(11));//keyby的分区字段
                row.setField(2,t1.getString(3));//累加之前的年龄
                row.setField(3, StringData.fromString(a3+""));//累加之后的年龄

                return row;
            }
        });

        reduce.print();

        env.execute("keby");

    }
}
