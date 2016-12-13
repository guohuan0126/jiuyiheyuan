package util;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.duanrong.util.LoadConstantProterties2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiao on 2015/10/26.
 */
public final class OssUtil {

    //访问oss的用户秘要
    static final String accessKeyId = "mKiqQnIG5aey9bVM";
    static final String accessKeySecret = "eOSt9ywAjyKTGMRAGRHPenZl1Ed8mm";

    //访问oss地址
    static final String endpoint = LoadConstantProterties2.getValueByDefaultPro("oss_endpoint");

    static OSSClient client;

    //初始化client
    static {
        //OssClient 参数配置
        ClientConfiguration conf = new ClientConfiguration();
        // 配置代理为本地8080端口
        /*conf.setProxyHost("127.0.0.1");
        conf.setProxyPort(8080);*/
        //设置用户名和密码
         /* conf.setProxyUsername("2634113451@qq.com");
        conf.setProxyPassword("920616");*/
        // 设置HTTP最大连接数为10

        conf.setMaxConnections(10);
        // 设置TCP连接超时为5000毫秒
        conf.setConnectionTimeout(5000);
        // 设置最大的重试次数为3
        conf.setMaxErrorRetry(3);
        // 设置Socket传输数据超时的时间为2000毫秒
        conf.setSocketTimeout(2000);
        client = new OSSClient(endpoint,accessKeyId, accessKeySecret, conf);
    }


    /**
     * 创建一个bucket
     * @param bucketName
     */
    public static void createBucket(String bucketName) {
        client.createBucket(bucketName);
    }

    /**
     * 删除一个bucket（当bucket中存在object时，不允许删除）
     * @param bucketName
     */
    public static void deleteBucket(String bucketName){
        client.deleteBucket(bucketName);
    }
    /**
     * 列出所有的bucket
     * @return
     */
    public static List<String> listBucket() {
        List<Bucket> buckets = client.listBuckets();
        List<String> bucketNames = new ArrayList<>(buckets.size());
        for (Bucket bucket : buckets){
            bucketNames.add(bucket.getName());
        }
        return bucketNames;
    }

    /**
     * 是否包含一个bucket
     * @param bucketName
     * @return
     */
    public static boolean hasBucket(String bucketName){
        return client.doesBucketExist(bucketName);
    }

    /**
     * 创建模拟额文件夹
     * @param bucketName
     * @param folderName
     * @throws IOException
     */
    public static void createFolder(String bucketName, String folderName) throws IOException {

        //oss 没有文件夹概念, 模拟文件夹的本质是一个size为0的object, 命名最后以 "/" 结尾
        folderName = folderName.trim() + "/";
        ObjectMetadata meta = new ObjectMetadata();
        byte[] buffer = new byte[0];
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        meta.setContentLength(0);
        client.putObject(bucketName, folderName, in, meta);
        in.close();
    }

    /**
     * 简单上传
     * @param bucketName bucket
     * @param key 上传的名字
     * @param contentType 上传文件类型
     * @param filePath 资源路径
     * @return
     * @throws FileNotFoundException
     */
    public static String putObject(String bucketName, String key, String filePath, String contentType){
        File file = new File(filePath);
        InputStream in;
		try {
			in = new FileInputStream(file);
		
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(file.length());       
        PutObjectResult result = client.putObject(bucketName, key, in, meta);
        in.close();
        return result.getETag();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
        
    }

    /**
     * 简单上传
     * @param bucketName bucket
     * @param key 上传的名字
     * @param contentType 上传文件类型
     * @return
     * @throws FileNotFoundException
     */
    public static String putObject(String bucketName, String key, InputStream in, String contentType){
	
        ObjectMetadata meta = new ObjectMetadata();
        //meta.setContentLength(in.);  
        meta.setContentType(contentType);
        PutObjectResult result = client.putObject(bucketName, key, in, meta);
        try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result.getETag();
		
        
    }

    
    /**
     * 栓出一个指定的对象
     * @param bucketName
     * @param key
     */
    public static void deleteObject(String bucketName, String key){
        // 删除Object
        client.deleteObject(bucketName, key);
    }



    /**
     * 获取指定bucket下的文件夹信息
     * @param bucketName
     *//*
    public static List<String> listFolders(String bucketName, String folderName){
        List<String> folderList = new ArrayList<>();
        ObjectListing objectListing = listObjects(bucketName, folderName);
        for(String commonPrefix : objectListing.getCommonPrefixes()){
            folderList.add(commonPrefix);
        }
        return folderList;
    }

    *//**
     * 获取指定bucket下的文件信息
     * @param bucketName
     *//*
    public static List<String> listFiles(String bucketName, String folderName){

        List<String> fileList = new ArrayList<>();
        ObjectListing objectListing = listObjects(bucketName, folderName);
        // 遍历所有Object
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            fileList.add(objectSummary.getKey());
        }
        return fileList;
    }

    *//**
     * 获取指定bucket下, 指定文件夹下的所有Object信息
     * @param bucketName
     * @return
     *//*
    private static ObjectListing listObjects(String bucketName, String folderName) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        listObjectsRequest.setDelimiter("/");
        if(folderName != null && !"".equals(folderName.trim())){
            listObjectsRequest.setMarker(folderName);
        }
        // 获取指定bucket下的所有Object信息
        return client.listObjects(listObjectsRequest);

    }*/

    /**
     * 获取bucket下所有的object
     * @param bucketName
     * @return
     */
    private static List<String> listObjects(String bucketName) {

        List<String> fileList = new ArrayList<>();
        // 获取指定bucket下的所有Object信息
        ObjectListing objectListing = client.listObjects(bucketName);

        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            fileList.add(objectSummary.getKey());
        }
        return fileList;

    }

    public static InputStream getObject(String bucketName, String key) throws IOException {
        // 获取Object，返回结果为OSSObject对象
        OSSObject object = client.getObject(bucketName, key);
        // 获取Object的输入流
        InputStream objectContent = object.getObjectContent();
        // 处理Object
        return objectContent;
    }
    
}