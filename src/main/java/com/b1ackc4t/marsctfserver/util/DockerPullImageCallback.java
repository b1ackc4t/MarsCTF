package com.b1ackc4t.marsctfserver.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.b1ackc4t.marsctfserver.dao.ChallengeMapper;
import com.b1ackc4t.marsctfserver.dao.DockerContainerMapper;
import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.b1ackc4t.marsctfserver.service.ChallengeService;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.PullResponseItem;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DockerPullImageCallback extends PullImageResultCallback {

    private Integer cid;
    private String tag;
    private Boolean isExist;

    public DockerPullImageCallback(Integer cid, String tag) {
        this.cid = cid;
        this.tag = tag;
        isExist = false;
    }

    @Override
    public void onComplete() {
        super.onComplete();
        Map<String, String> ymlByFileName = YmlUtil.getYmlByFileName(YmlUtil.bootstrap_file,"spring", "datasource", "druid");
        Connection conn = null;
        Statement stat = null;
        try {
            Class.forName(ymlByFileName.get("spring.datasource.druid.driver-class-name"));
            String url = ymlByFileName.get("spring.datasource.druid.url");
            conn = DriverManager.getConnection(url, ymlByFileName.get("spring.datasource.druid.username"), ymlByFileName.get("spring.datasource.druid.password"));
            stat = conn.createStatement();
            String sql = null;
            if (isExist) {
                sql = "update mc_challenge set download_ok=1 where cid=" + String.valueOf(cid);
            } else {
                sql = "update mc_challenge set download_ok=2 where cid=" + String.valueOf(cid);
            }
            stat.executeUpdate(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onNext(PullResponseItem item) {
        if (tag.equalsIgnoreCase(item.getId())) isExist = true;
        super.onNext(item);
    }

    @SneakyThrows
    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);
        Map<String, String> ymlByFileName = YmlUtil.getYmlByFileName(YmlUtil.bootstrap_file,"spring", "datasource", "druid");
        Class.forName(ymlByFileName.get("spring.datasource.druid.driver-class-name"));
        String url = ymlByFileName.get("spring.datasource.druid.url");
        Connection conn = DriverManager.getConnection(url, ymlByFileName.get("spring.datasource.druid.username"), ymlByFileName.get("spring.datasource.druid.password"));
        Statement stat = conn.createStatement();
        String sql = "update mc_challenge set download_ok=2 where cid=" + String.valueOf(cid);
        stat.executeUpdate(sql);
    }
}
