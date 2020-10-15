package org.ydc.system.system.controller.DownloadFile;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.ydc.system.system.entity.Download;
import org.ydc.system.system.entity.SysRole;
import org.ydc.system.system.service.DownloadService;
import springfox.documentation.spring.web.json.Json;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;


@Controller
public class downloadModel {

    @Autowired
    private DownloadService downloadService;
    @RequestMapping("downloadFile/{fileName}")
    @ResponseBody
    public String Download(HttpServletResponse response, @PathVariable String fileName) {//fileName,被下载文件的名称
        // 获取文件
        File file = new File("Z:/wallpapers/1/wallhaven-839kvo你好.jpg");
        //文件名
     //   String fileName = file.getName();

        // 清空缓冲区，状态码和响应头(headers)
        response.reset();
        // 设置ContentType，响应内容为二进制数据流，编码为utf-8，此处设定的编码是文件内容的编码
        response.setContentType("application/octet-stream;charset=utf-8");
        // 以（Content-Disposition: attachment; filename="filename.jpg"）格式设定默认文件名，设定utf编码，此处的编码是文件名的编码，使能正确显示中文文件名
    //    response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "utf-8"));

        // 实现文件下载
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            // 获取字节流
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            System.out.println("Download successfully!");
            return "okk";
        } catch (Exception e) {
            System.out.println("Download failed!");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "okk";

}

    @ResponseBody
    @GetMapping("TestDownloadFile")
    public String DownloadFile() throws IOException {//String fileName,String filePath){
        File dest = new File("C:\\Users\\25007\\Desktop\\RuoYi\\ruoyi-system\\src\\main\\resources\\mapper\\system\\SysConfigMapper.xml");
        File soure = new File("D:\\Download\\M_system - 副本\\system_provide\\src\\main\\java\\org\\ydc\\system\\system\\mapper\\xml\\SysConfigMapper.xml");

        //把一个文件的内容读到另一个文件
        try
        {
            FileInputStream fis = new FileInputStream(dest);//创建输入流对象
            FileOutputStream fos = new FileOutputStream(soure); //创建输出流对象
            byte datas[] = new byte[1024*8];//创建搬运工具
            int len = 0;//创建长度
            while((len = fis.read(datas))!=-1)//循环读取数据
            {
                fos.write(datas,0,len);
            }
            fis.close();//释放资源
            fis.close();//释放资源
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "okkk";
    }
    @ResponseBody
    @GetMapping("TestDownloadController")
    public String TestDownloadController(){
        Download download= new Download();
        download.setFilelmp("package org.ydc.system.system.service.impl;\n" +
                "import java.util.List;\n" +
                "import javax.annotation.PostConstruct;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "import com.ruoyi.common.constant.Constants;\n" +
                "import com.ruoyi.common.constant.UserConstants;\n" +
                "import com.ruoyi.common.core.text.Convert;\n" +
                "import com.ruoyi.common.exception.BusinessException;\n" +
                "import com.ruoyi.common.utils.CacheUtils;\n" +
                "import com.ruoyi.common.utils.StringUtils;\n" +
                "import com.ruoyi.system.domain.SysConfig;\n" +
                "import com.ruoyi.system.mapper.SysConfigMapper;\n" +
                "import com.ruoyi.system.service.ISysConfigService;\n" +
                "\n" +
                "/**\n" +
                " * 参数配置 服务层实现\n" +
                " * \n" +
                " * @author ruoyi\n" +
                " */\n" +
                "@Service\n" +
                "public class SysConfigServiceImpl implements ISysConfigService\n" +
                "{\n" +
                "    @Autowired\n" +
                "    private SysConfigMapper configMapper;\n" +
                "\n" +
                "    /**\n" +
                "     * 项目启动时，初始化参数到缓存\n" +
                "     */\n" +
                "    @PostConstruct\n" +
                "    public void init()\n" +
                "    {\n" +
                "        List<SysConfig> configsList = configMapper.selectConfigList(new SysConfig());\n" +
                "        for (SysConfig config : configsList)\n" +
                "        {\n" +
                "            CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 查询参数配置信息\n" +
                "     * \n" +
                "     * @param configId 参数配置ID\n" +
                "     * @return 参数配置信息\n" +
                "     */\n" +
                "    @Override\n" +
                "    public SysConfig selectConfigById(Long configId)\n" +
                "    {\n" +
                "        SysConfig config = new SysConfig();\n" +
                "        config.setConfigId(configId);\n" +
                "        return configMapper.selectConfig(config);\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 根据键名查询参数配置信息\n" +
                "     * \n" +
                "     * @param configKey 参数key\n" +
                "     * @return 参数键值\n" +
                "     */\n" +
                "    @Override\n" +
                "    public String selectConfigByKey(String configKey)\n" +
                "    {\n" +
                "        String configValue = Convert.toStr(CacheUtils.get(getCacheName(), getCacheKey(configKey)));\n" +
                "        if (StringUtils.isNotEmpty(configValue))\n" +
                "        {\n" +
                "            return configValue;\n" +
                "        }\n" +
                "        SysConfig config = new SysConfig();\n" +
                "        config.setConfigKey(configKey);\n" +
                "        SysConfig retConfig = configMapper.selectConfig(config);\n" +
                "        if (StringUtils.isNotNull(retConfig))\n" +
                "        {\n" +
                "            CacheUtils.put(getCacheName(), getCacheKey(configKey), retConfig.getConfigValue());\n" +
                "            return retConfig.getConfigValue();\n" +
                "        }\n" +
                "        return StringUtils.EMPTY;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 查询参数配置列表\n" +
                "     * \n" +
                "     * @param config 参数配置信息\n" +
                "     * @return 参数配置集合\n" +
                "     */\n" +
                "    @Override\n" +
                "    public List<SysConfig> selectConfigList(SysConfig config)\n" +
                "    {\n" +
                "        return configMapper.selectConfigList(config);\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 新增参数配置\n" +
                "     * \n" +
                "     * @param config 参数配置信息\n" +
                "     * @return 结果\n" +
                "     */\n" +
                "    @Override\n" +
                "    public int insertConfig(SysConfig config)\n" +
                "    {\n" +
                "        int row = configMapper.insertConfig(config);\n" +
                "        if (row > 0)\n" +
                "        {\n" +
                "            CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());\n" +
                "        }\n" +
                "        return row;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 修改参数配置\n" +
                "     * \n" +
                "     * @param config 参数配置信息\n" +
                "     * @return 结果\n" +
                "     */\n" +
                "    @Override\n" +
                "    public int updateConfig(SysConfig config)\n" +
                "    {\n" +
                "        int row = configMapper.updateConfig(config);\n" +
                "        if (row > 0)\n" +
                "        {\n" +
                "            CacheUtils.put(getCacheName(), getCacheKey(config.getConfigKey()), config.getConfigValue());\n" +
                "        }\n" +
                "        return row;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 批量删除参数配置对象\n" +
                "     * \n" +
                "     * @param ids 需要删除的数据ID\n" +
                "     * @return 结果\n" +
                "     */\n" +
                "    @Override\n" +
                "    public int deleteConfigByIds(String ids)\n" +
                "    {\n" +
                "        Long[] configIds = Convert.toLongArray(ids);\n" +
                "        for (Long configId : configIds)\n" +
                "        {\n" +
                "            SysConfig config = selectConfigById(configId);\n" +
                "            if (StringUtils.equals(UserConstants.YES, config.getConfigType()))\n" +
                "            {\n" +
                "                throw new BusinessException(String.format(\"内置参数【%1$s】不能删除 \", config.getConfigKey()));\n" +
                "            }\n" +
                "        }\n" +
                "        int count = configMapper.deleteConfigByIds(Convert.toStrArray(ids));\n" +
                "        if (count > 0)\n" +
                "        {\n" +
                "\n" +
                "            CacheUtils.removeAll(getCacheName());\n" +
                "        }\n" +
                "        return count;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 清空缓存数据\n" +
                "     */\n" +
                "    @Override\n" +
                "    public void clearCache()\n" +
                "    {\n" +
                "        CacheUtils.removeAll(getCacheName());\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 校验参数键名是否唯一\n" +
                "     * \n" +
                "     * @param config 参数配置信息\n" +
                "     * @return 结果\n" +
                "     */\n" +
                "    @Override\n" +
                "    public String checkConfigKeyUnique(SysConfig config)\n" +
                "    {\n" +
                "        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();\n" +
                "        SysConfig info = configMapper.checkConfigKeyUnique(config.getConfigKey());\n" +
                "        if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue())\n" +
                "        {\n" +
                "            return UserConstants.CONFIG_KEY_NOT_UNIQUE;\n" +
                "        }\n" +
                "        return UserConstants.CONFIG_KEY_UNIQUE;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 获取cache name\n" +
                "     * \n" +
                "     * @return 缓存名\n" +
                "     */\n" +
                "    private String getCacheName()\n" +
                "    {\n" +
                "        return Constants.SYS_CONFIG_CACHE;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * 设置cache key\n" +
                "     * \n" +
                "     * @param configKey 参数键\n" +
                "     * @return 缓存键key\n" +
                "     */\n" +
                "    private String getCacheKey(String configKey)\n" +
                "    {\n" +
                "        return Constants.SYS_CONFIG_KEY + configKey;\n" +
                "    }\n" +
                "}");
        download.setFileservice("package org.ydc.system.system.service;\n" +
                "public interface SysConfigService\n" +
                "{\n" +
                "    /**\n" +
                "     * 查询参数配置信息\n" +
                "     * \n" +
                "     * @param configId 参数配置ID\n" +
                "     * @return 参数配置信息\n" +
                "     */\n" +
                "    public SysConfig selectConfigById(Long configId);\n" +
                "\n" +
                "    /**\n" +
                "     * 根据键名查询参数配置信息\n" +
                "     * \n" +
                "     * @param configKey 参数键名\n" +
                "     * @return 参数键值\n" +
                "     */\n" +
                "    public String selectConfigByKey(String configKey);\n" +
                "\n" +
                "    /**\n" +
                "     * 查询参数配置列表\n" +
                "     * \n" +
                "     * @param config 参数配置信息\n" +
                "     * @return 参数配置集合\n" +
                "     */\n" +
                "    public List<SysConfig> selectConfigList(SysConfig config);\n" +
                "\n" +
                "    /**\n" +
                "     * 新增参数配置\n" +
                "     * \n" +
                "     * @param config 参数配置信息\n" +
                "     * @return 结果\n" +
                "     */\n" +
                "    public int insertConfig(SysConfig config);\n" +
                "\n" +
                "    /**\n" +
                "     * 修改参数配置\n" +
                "     * \n" +
                "     * @param config 参数配置信息\n" +
                "     * @return 结果\n" +
                "     */\n" +
                "    public int updateConfig(SysConfig config);\n" +
                "\n" +
                "    /**\n" +
                "     * 批量删除参数配置信息\n" +
                "     * \n" +
                "     * @param ids 需要删除的数据ID\n" +
                "     * @return 结果\n" +
                "     */\n" +
                "    public int deleteConfigByIds(String ids);\n" +
                "\n" +
                "    /**\n" +
                "     * 清空缓存数据\n" +
                "     */\n" +
                "    public void clearCache();\n" +
                "\n" +
                "    /**\n" +
                "     * 校验参数键名是否唯一\n" +
                "     * \n" +
                "     * @param config 参数信息\n" +
                "     * @return 结果\n" +
                "     */\n" +
                "    public String checkConfigKeyUnique(SysConfig config);\n" +
                "}");
        download.setFilemapper("package org.ydc.system.system.mapper;\n" +
                "import java.util.List;\n" +
                "import com.ruoyi.system.domain.SysConfig;\n" +
                "\n" +
                "/**\n" +
                " * 参数配置 数据层\n" +
                " * \n" +
                " * @author ruoyi\n" +
                " */\n" +
                "public interface SysConfigMapper\n" +
                "{\n" +
                "    /**\n" +
                "     * 查询参数配置信息\n" +
                "     * \n" +
                "     * @param config 参数配置信息\n" +
                "     * @return 参数配置信息\n" +
                "     */\n" +
                "    public SysConfig selectConfig(SysConfig config);\n" +
                "\n" +
                "    /**\n" +
                "     * 查询参数配置列表\n" +
                "     * \n" +
                "     * @param config 参数配置信息\n" +
                "     * @return 参数配置集合\n" +
                "     */\n" +
                "    public List<SysConfig> selectConfigList(SysConfig config);\n" +
                "\n" +
                "    /**\n" +
                "     * 根据键名查询参数配置信息\n" +
                "     * \n" +
                "     * @param configKey 参数键名\n" +
                "     * @return 参数配置信息\n" +
                "     */\n" +
                "    public SysConfig checkConfigKeyUnique(String configKey);\n" +
                "\n" +
                "    /**\n" +
                "     * 新增参数配置\n" +
                "     * \n" +
                "     * @param config 参数配置信息\n" +
                "     * @return 结果\n" +
                "     */\n" +
                "    public int insertConfig(SysConfig config);\n" +
                "\n" +
                "    /**\n" +
                "     * 修改参数配置\n" +
                "     * \n" +
                "     * @param config 参数配置信息\n" +
                "     * @return 结果\n" +
                "     */\n" +
                "    public int updateConfig(SysConfig config);\n" +
                "\n" +
                "    /**\n" +
                "     * 批量删除参数配置\n" +
                "     * \n" +
                "     * @param configIds 需要删除的数据ID\n" +
                "     * @return 结果\n" +
                "     */\n" +
                "    public int deleteConfigByIds(String[] configIds);\n" +
                "}");
        download.setFilexml("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper\n" +
                "        PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
                "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                "<mapper namespace=\"org.ydc.system.system.mapper.SysConfigMapper\">\n" +
                "\n" +
                "    <resultMap type=\"SysConfig\" id=\"SysConfigResult\">\n" +
                "        <id     property=\"configId\"      column=\"config_id\"      />\n" +
                "        <result property=\"configName\"    column=\"config_name\"    />\n" +
                "        <result property=\"configKey\"     column=\"config_key\"     />\n" +
                "        <result property=\"configValue\"   column=\"config_value\"   />\n" +
                "        <result property=\"configType\"    column=\"config_type\"    />\n" +
                "        <result property=\"createBy\"      column=\"create_by\"      />\n" +
                "        <result property=\"createTime\"    column=\"create_time\"    />\n" +
                "        <result property=\"updateBy\"      column=\"update_by\"      />\n" +
                "        <result property=\"updateTime\"    column=\"update_time\"    />\n" +
                "    </resultMap>\n" +
                "\n" +
                "    <sql id=\"selectConfigVo\">\n" +
                "        select config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark\n" +
                "        from sys_config\n" +
                "    </sql>\n" +
                "\n" +
                "    <!-- 查询条件 -->\n" +
                "    <sql id=\"sqlwhereSearch\">\n" +
                "        <where>\n" +
                "            <if test=\"configId !=null\">\n" +
                "                and config_id = #{configId}\n" +
                "            </if>\n" +
                "            <if test=\"configKey !=null and configKey != ''\">\n" +
                "                and config_key = #{configKey}\n" +
                "            </if>\n" +
                "        </where>\n" +
                "    </sql>\n" +
                "\n" +
                "    <select id=\"selectConfig\" parameterType=\"SysConfig\" resultMap=\"SysConfigResult\">\n" +
                "        <include refid=\"selectConfigVo\"/>\n" +
                "        <include refid=\"sqlwhereSearch\"/>\n" +
                "    </select>\n" +
                "\n" +
                "    <select id=\"selectConfigList\" parameterType=\"SysConfig\" resultMap=\"SysConfigResult\">\n" +
                "        <include refid=\"selectConfigVo\"/>\n" +
                "        <where>\n" +
                "            <if test=\"configName != null and configName != ''\">\n" +
                "                AND config_name like concat('%', #{configName}, '%')\n" +
                "            </if>\n" +
                "            <if test=\"configType != null and configType != ''\">\n" +
                "                AND config_type = #{configType}\n" +
                "            </if>\n" +
                "            <if test=\"configKey != null and configKey != ''\">\n" +
                "                AND config_key like concat('%', #{configKey}, '%')\n" +
                "            </if>\n" +
                "            <if test=\"params.beginTime != null and params.beginTime != ''\"><!-- 开始时间检索 -->\n" +
                "                and date_format(create_time,'%y%m%d') &gt;= date_format(#{params.beginTime},'%y%m%d')\n" +
                "            </if>\n" +
                "            <if test=\"params.endTime != null and params.endTime != ''\"><!-- 结束时间检索 -->\n" +
                "                and date_format(create_time,'%y%m%d') &lt;= date_format(#{params.endTime},'%y%m%d')\n" +
                "            </if>\n" +
                "        </where>\n" +
                "    </select>\n" +
                "\n" +
                "    <select id=\"checkConfigKeyUnique\" parameterType=\"String\" resultMap=\"SysConfigResult\">\n" +
                "        <include refid=\"selectConfigVo\"/>\n" +
                "        where config_key = #{configKey}\n" +
                "    </select>\n" +
                "\n" +
                "    <insert id=\"insertConfig\" parameterType=\"SysConfig\">\n" +
                "        insert into sys_config (\n" +
                "        <if test=\"configName != null and configName != '' \">config_name,</if>\n" +
                "        <if test=\"configKey != null and configKey != '' \">config_key,</if>\n" +
                "        <if test=\"configValue != null and configValue != '' \">config_value,</if>\n" +
                "        <if test=\"configType != null and configType != '' \">config_type,</if>\n" +
                "        <if test=\"createBy != null and createBy != ''\">create_by,</if>\n" +
                "        <if test=\"remark != null and remark != ''\">remark,</if>\n" +
                "        create_time\n" +
                "        )values(\n" +
                "        <if test=\"configName != null and configName != ''\">#{configName},</if>\n" +
                "        <if test=\"configKey != null and configKey != ''\">#{configKey},</if>\n" +
                "        <if test=\"configValue != null and configValue != ''\">#{configValue},</if>\n" +
                "        <if test=\"configType != null and configType != ''\">#{configType},</if>\n" +
                "        <if test=\"createBy != null and createBy != ''\">#{createBy},</if>\n" +
                "        <if test=\"remark != null and remark != ''\">#{remark},</if>\n" +
                "        sysdate()\n" +
                "        )\n" +
                "    </insert>\n" +
                "\n" +
                "    <update id=\"updateConfig\" parameterType=\"SysConfig\">\n" +
                "        update sys_config\n" +
                "        <set>\n" +
                "            <if test=\"configName != null and configName != ''\">config_name = #{configName},</if>\n" +
                "            <if test=\"configKey != null and configKey != ''\">config_key = #{configKey},</if>\n" +
                "            <if test=\"configValue != null and configValue != ''\">config_value = #{configValue},</if>\n" +
                "            <if test=\"configType != null and configType != ''\">config_type = #{configType},</if>\n" +
                "            <if test=\"updateBy != null and updateBy != ''\">update_by = #{updateBy},</if>\n" +
                "            <if test=\"remark != null\">remark = #{remark},</if>\n" +
                "            update_time = sysdate()\n" +
                "        </set>\n" +
                "        where config_id = #{configId}\n" +
                "    </update>\n" +
                "\n" +
                "    <delete id=\"deleteConfigByIds\" parameterType=\"String\">\n" +
                "        delete from sys_config where config_id in\n" +
                "        <foreach item=\"configId\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">\n" +
                "            #{configId}\n" +
                "        </foreach>\n" +
                "    </delete>\n" +
                "\n" +
                "</mapper>");
        download.setFileentity("package org.ydc.system.system.entity;\n" +
                "import javax.validation.constraints.*;\n" +
                "import org.apache.commons.lang3.builder.ToStringBuilder;\n" +
                "import org.apache.commons.lang3.builder.ToStringStyle;\n" +
                "import com.ruoyi.common.annotation.Excel;\n" +
                "import com.ruoyi.common.annotation.Excel.ColumnType;\n" +
                "import com.ruoyi.common.core.domain.BaseEntity;\n" +
                "\n" +
                "/**\n" +
                " * 参数配置表 sys_config\n" +
                " * \n" +
                " * @author ruoyi\n" +
                " */\n" +
                "public class SysConfig extends BaseEntity\n" +
                "{\n" +
                "    private static final long serialVersionUID = 1L;\n" +
                "\n" +
                "    /** 参数主键 */\n" +
                "    @Excel(name = \"参数主键\", cellType = ColumnType.NUMERIC)\n" +
                "    private Long configId;\n" +
                "\n" +
                "    /** 参数名称 */\n" +
                "    @Excel(name = \"参数名称\")\n" +
                "    private String configName;\n" +
                "\n" +
                "    /** 参数键名 */\n" +
                "    @Excel(name = \"参数键名\")\n" +
                "    private String configKey;\n" +
                "\n" +
                "    /** 参数键值 */\n" +
                "    @Excel(name = \"参数键值\")\n" +
                "    private String configValue;\n" +
                "\n" +
                "    /** 系统内置（Y是 N否） */\n" +
                "    @Excel(name = \"系统内置\", readConverterExp = \"Y=是,N=否\")\n" +
                "    private String configType;\n" +
                "\n" +
                "    public Long getConfigId()\n" +
                "    {\n" +
                "        return configId;\n" +
                "    }\n" +
                "\n" +
                "    public void setConfigId(Long configId)\n" +
                "    {\n" +
                "        this.configId = configId;\n" +
                "    }\n" +
                "\n" +
                "    @NotBlank(message = \"参数名称不能为空\")\n" +
                "    @Size(min = 0, max = 100, message = \"参数名称不能超过100个字符\")\n" +
                "    public String getConfigName()\n" +
                "    {\n" +
                "        return configName;\n" +
                "    }\n" +
                "\n" +
                "    public void setConfigName(String configName)\n" +
                "    {\n" +
                "        this.configName = configName;\n" +
                "    }\n" +
                "\n" +
                "    @NotBlank(message = \"参数键名长度不能为空\")\n" +
                "    @Size(min = 0, max = 100, message = \"参数键名长度不能超过100个字符\")\n" +
                "    public String getConfigKey()\n" +
                "    {\n" +
                "        return configKey;\n" +
                "    }\n" +
                "\n" +
                "    public void setConfigKey(String configKey)\n" +
                "    {\n" +
                "        this.configKey = configKey;\n" +
                "    }\n" +
                "\n" +
                "    @NotBlank(message = \"参数键值不能为空\")\n" +
                "    @Size(min = 0, max = 500, message = \"参数键值长度不能超过500个字符\")\n" +
                "    public String getConfigValue()\n" +
                "    {\n" +
                "        return configValue;\n" +
                "    }\n" +
                "\n" +
                "    public void setConfigValue(String configValue)\n" +
                "    {\n" +
                "        this.configValue = configValue;\n" +
                "    }\n" +
                "\n" +
                "    public String getConfigType()\n" +
                "    {\n" +
                "        return configType;\n" +
                "    }\n" +
                "\n" +
                "    public void setConfigType(String configType)\n" +
                "    {\n" +
                "        this.configType = configType;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public String toString() {\n" +
                "        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)\n" +
                "            .append(\"configId\", getConfigId())\n" +
                "            .append(\"configName\", getConfigName())\n" +
                "            .append(\"configKey\", getConfigKey())\n" +
                "            .append(\"configValue\", getConfigValue())\n" +
                "            .append(\"configType\", getConfigType())\n" +
                "            .append(\"createBy\", getCreateBy())\n" +
                "            .append(\"createTime\", getCreateTime())\n" +
                "            .append(\"updateBy\", getUpdateBy())\n" +
                "            .append(\"updateTime\", getUpdateTime())\n" +
                "            .append(\"remark\", getRemark())\n" +
                "            .toString();\n" +
                "    }\n" +
                "}");
        download.setId(1);
        download.setFilename("sys_config");
        boolean save = downloadService.save(download);
        System.out.println(save);
        return "okk";
    }
    @ResponseBody
    @GetMapping("getByid")
    public Download getByid() {
        Download byId = downloadService.getById(1);
        return byId;
    }

    @ResponseBody
    @PostMapping("cretefile")
    public String cretefile(@RequestBody Download download) throws IOException {
        if (download.getUrl().equals("")||download.getFilename().equals(""))
            return "error";
        else{
        QueryWrapper<Download> wrapper = new QueryWrapper<>();
        wrapper
                .eq("filename",download.getFilename());
        Download one = downloadService.getOne(wrapper);
        File fileXml = new File(download.getUrl()+"\\mapper\\xml\\"+download.getFilename()+"Mapper.xml");//xml
        if(!fileXml.exists()){
            fileXml.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(fileXml.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write(one.getFilexml());
        bw.close();


        File fileentity = new File(download.getUrl()+"\\entity\\"+download.getFilename()+".java");//entity
        if(!fileentity.exists()){
            fileentity.createNewFile();
        }
        FileWriter fileWriterentity = new FileWriter(fileentity.getAbsoluteFile());
        BufferedWriter bwentity= new BufferedWriter(fileWriterentity);
        bwentity.write(one.getFileentity());
        bwentity.close();

        File fileMapper= new File(download.getUrl()+"\\mapper\\"+download.getFilename()+"Mapper.java");//Mapper
        if(!fileMapper.exists()){
            fileMapper.createNewFile();
        }
        FileWriter fileWriterMapper = new FileWriter(fileMapper.getAbsoluteFile());
        BufferedWriter bwMapper= new BufferedWriter(fileWriterMapper);
        bwMapper.write(one.getFilemapper());
        bwMapper.close();

        File fileService = new File(download.getUrl()+"\\service\\"+download.getFilename()+"Service.java");//service
        if(!fileService.exists()){
            fileService.createNewFile();
        }
        FileWriter fileWriterService = new FileWriter(fileService.getAbsoluteFile());
        BufferedWriter bwService = new BufferedWriter(fileWriterService);
        bwService.write(one.getFileservice());
        bwService.close();


        File fileImpl = new File(download.getUrl()+"\\service\\impl\\"+download.getFilename()+"ServiceImpl.java");//impl
        if(!fileImpl.exists()){
            fileImpl.createNewFile();
        }
        FileWriter fileWriterImpl = new FileWriter(fileImpl.getAbsoluteFile());
        BufferedWriter bwImpl = new BufferedWriter(fileWriterImpl);
        bwImpl.write(one.getFilelmp());
        bwImpl.close();



        return "true";}
    }


}


