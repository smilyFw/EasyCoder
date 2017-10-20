package pc;


import pc.easyMeta.MetaInit;
import pc.easyMethod.MethodInit;
import pc.easyDao.SqlInit;
import pc.easyModule.ModuleInit;
import org.junit.Test;
import pc.easyVo.VoInit;

/**
 * Created by wenwen.fu on 2015/12/28.
 */
public class AutoCode {
    @Test
    public void testRun() throws Exception {
        //协议名称配置
        String projectid = "eightYears";
        //建立模板
        ModuleInit.makeModuleDir(projectid);
        //自动由xml生成方法
        MethodInit.auto(projectid);
        VoInit.auto(projectid);
        //sql语句生成 model和dao
//        SqlInit.auto(projectid);
        //配置文件
        MetaInit.auto(projectid);
    }
}
