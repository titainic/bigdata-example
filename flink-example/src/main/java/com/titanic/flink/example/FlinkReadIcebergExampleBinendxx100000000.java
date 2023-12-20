package com.titanic.flink.example;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.data.GenericRowData;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.data.StringData;
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

/**
 * flink基于iceberg读取hive存储在minio上面的数据
 * 使用hive的catalog读取数据
 * 集群版
 */
public class FlinkReadIcebergExampleBinendxx100000000
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

//        StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("binend1", 8081, "/Users/binend/soft/intellij_workspace/bigdata-example/flink-example/target/flink-example-1.0-SNAPSHOT.jar");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.createLocalEnvironment();

        IcebergSource source = IcebergSource.forRowData()
                .tableLoader(tableLoader)
                .assignerFactory(new SimpleSplitAssignerFactory())
                .streaming(true)
                .streamingStartingStrategy(StreamingStartingStrategy.INCREMENTAL_FROM_LATEST_SNAPSHOT)
                .monitorInterval(Duration.ofSeconds(60))
                .build();

        DataStream<RowData> stream = env.fromSource(
                source,
                WatermarkStrategy.noWatermarks(),
                "My Iceberg Source",
                TypeInformation.of(RowData.class));

        DataStream<RowData>  printStram = stream.map(new MapFunction<RowData, RowData>()
        {
            @Override
            public RowData map(RowData rowData) throws Exception
            {
                String uuid = rowData.getString(0).toString();
                String c_date = rowData.getString(1).toString();
                String c_time = rowData.getString(2).toString();
                String age = rowData.getString(3).toString();
                GenericRowData row = new GenericRowData(4);

                row.setField(0,uuid);
                row.setField(1,c_date);
                row.setField(2,c_time);
                row.setField(3,age);

                return row;
            }
        });



        DataStream<RowData>  filteStram = printStram.filter(new FilterFunction<RowData>()
        {
            @Override
            public boolean filter(RowData rowData) throws Exception
            {
                StringData s = StringData.fromString(rowData.getString(3).toString());


                if (s.equals("20") )
                {
                    return true;
                }
               return false;

            }
        });

        filteStram.print();

        env.execute("printStram");
    }
}
