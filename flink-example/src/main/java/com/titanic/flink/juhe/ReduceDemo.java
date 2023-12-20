//package com.titanic.flink.juhe;
//
//import com.titanic.flink.bean.BinendBean;
//import org.apache.flink.api.common.eventtime.WatermarkStrategy;
//import org.apache.flink.api.common.functions.ReduceFunction;
//import org.apache.flink.api.common.typeinfo.TypeInformation;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.table.data.RowData;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.iceberg.catalog.TableIdentifier;
//import org.apache.iceberg.flink.CatalogLoader;
//import org.apache.iceberg.flink.TableLoader;
//import org.apache.iceberg.flink.source.IcebergSource;
//import org.apache.iceberg.flink.source.StreamingStartingStrategy;
//import org.apache.iceberg.flink.source.assigner.SimpleSplitAssignerFactory;
//
//import java.time.Duration;
//import java.util.HashMap;
//import java.util.Map;
//
//public class ReduceDemo
//{
//    public static void main(String[] args) throws Exception
//    {
//        //设置minio用户和链接
//        Configuration hadoopConf = new Configuration();
//        hadoopConf.set("fs.s3a.access.key", "admin");
//        hadoopConf.set("fs.s3a.secret.key", "12345678");
//        hadoopConf.set("aws.region", "us-east-1");
//        hadoopConf.set("fs.s3a.endpoint", "http://binend9:8000");
//        hadoopConf.set("fs.s3a.path.style.access", "true");
//
//        //设置hive链接
//        Map<String, String> properties = new HashMap<>();
//        properties.put("type", "iceberg");
//        properties.put("catalog-type", "hive");
//        properties.put("property-version", "1");
//        properties.put("warehouse", "s3a://hadoop/s3/hive/warehouse/");
//        properties.put("uri", "thrift://binend4:9083");
//
//        //catalog设置
//        CatalogLoader catalogLoader = CatalogLoader.hive("iceberg_s3_hive", hadoopConf, properties);
//        TableIdentifier identifier = TableIdentifier.of("default", "binendxx");
//        TableLoader tableLoader = TableLoader.fromCatalog(catalogLoader, identifier);
//
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.createRemoteEnvironment("binend1", 8081, "/Users/binend/soft/intellij_workspace/bigdata-example/flink-example/target/flink-example-1.0-SNAPSHOT.jar");
////        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
//
//        IcebergSource source = IcebergSource.forRowData()
//                .tableLoader(tableLoader)
//                .assignerFactory(new SimpleSplitAssignerFactory())
//                .streaming(true)
//                .streamingStartingStrategy(StreamingStartingStrategy.TABLE_SCAN_THEN_INCREMENTAL)
//                .monitorInterval(Duration.ofSeconds(60))
//                .build();
//
//        DataStream<BinendBean> stream = env.fromSource(
//                source,
//                WatermarkStrategy.noWatermarks(),
//                "hive tables binendxxx",
//                TypeInformation.of(RowData.class));
//
//
//
//        stream.keyBy(11).reduce(new ReduceFunction<BinendBean>()
//        {
//            @Override
//            public BinendBean reduce(BinendBean binendBean, BinendBean t1) throws Exception
//            {
//                System.out.println("value1="+binendBean.getLevel());
//                System.out.println("value2="+t1.getLevel());
//
//                return new BinendBean();
//            }
//        }).print();
//
//        env.execute("reduce demo");
//    }
//}
