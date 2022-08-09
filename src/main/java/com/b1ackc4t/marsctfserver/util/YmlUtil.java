package com.b1ackc4t.marsctfserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class YmlUtil {

    public static String bootstrap_file = "application.yml";
    public static String base_file = "application.yml";

    static {
        Map<String, String> ymlByFileName = YmlUtil.getYmlByFileName(YmlUtil.bootstrap_file,"spring", "profiles");
        bootstrap_file = String.format("application-%s.yml", ymlByFileName.get("spring.profiles.active"));
    }

    private static Map<String,String> result = new HashMap<>();

    private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {

        try (FileOutputStream outputStream = new FileOutputStream(file)) {

            int read;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            // commons-io
            //IOUtils.copy(inputStream, outputStream);

        }

    }

    /**
     * 根据文件名获取yml的文件内容
     * @param filePath
     * @param keys 第一个参数对应第一个key，第二个参数对应第二个key 比如spring.name下的所有 就是两个参数、
     *              getYmlByFileName(bootstrap_file,"spring", "name");
     * @return
     */
    public static Map<String,String> getYmlByFileName(String filePath, String... keys) {
        result = new HashMap<>();
        if(filePath == null) {
            filePath = bootstrap_file;
        }
        InputStream in = null;
        try {
            Resource resource = new ClassPathResource(filePath);
            InputStream inputStream = resource.getInputStream();
            in = new BufferedInputStream(inputStream);
            Yaml props = new Yaml();
            Object obj = props.loadAs(in,Map.class);
            Map<String,Object> param = (Map<String, Object>) obj;

            for(Map.Entry<String,Object> entry:param.entrySet()){
                String key = entry.getKey();
                Object val = entry.getValue();
                if (keys.length != 0 && !keys[0].equals(key)){
                    continue;
                }
                if(val instanceof Map){
                    forEachYaml(key,(Map<String, Object>) val, 1, keys);
                }else{
                    result.put(key, val.toString());
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * 根据key获取值
     * @param key
     * @return
     */
    public static String getValue(String key) throws FileNotFoundException {
        Map<String,String> map = getYmlByFileName(null);
        if(map==null)return null;
        return map.get(key);
    }


    /**
     * 遍历yml文件，获取map集合
     * @param key_str
     * @param obj
     * @param i
     * @param keys
     * @return
     */
    public static Map<String,String> forEachYaml(String key_str,Map<String, Object> obj, int i, String... keys){
        for(Map.Entry<String,Object> entry:obj.entrySet()){
            String key = entry.getKey();
            Object val = entry.getValue();
            if (keys.length > i && !keys[i].equals(key)){
                continue;
            }
            String str_new = "";
            if(key_str != null && !key_str.equals("")){
                str_new = key_str+ "."+key;
            }else{
                str_new = key;
            }
            if(val instanceof Map){
                forEachYaml(str_new,(Map<String, Object>) val, ++i, keys);
                i--;
            }else{

                result.put(str_new,val != null ? val.toString() : "");
            }
        }

        return result;
    }


    /**
     * 获取bootstrap.yml的name
     * @return
     */
    public static String getApplicationName() throws FileNotFoundException {
        return getYmlByFileName(bootstrap_file).get("server.port");
    }

    /**
     * 获取bootstrap.yml的name
     * @return
     */
    public static String getApplicationName1() throws FileNotFoundException {
        String name =  getYmlByFileName(bootstrap_file).get("spring.application.name");
        return name + "center";
    }

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println(YmlUtil.bootstrap_file);

    }


}