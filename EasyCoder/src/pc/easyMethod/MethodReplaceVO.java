package pc.easyMethod;

/**
 * Created by wenwen.fu on 2016/1/1.
 */
public class MethodReplaceVO {
    /**方法描述*/
    private String comments;
    /**方法注释*/
    private String des;
    /**方法需要的参数列表以逗号隔开 int i, String j..*/
    private String params;

    /**不带参数类型的参数列表以逗号隔开 i, j..*/
    private String noTypeParams;

    /**方法名*/
    private String methodName;
    /**参数返回   map.put("locs", userLocs);\n map.put(CODE, CODE_SUCC);*/
    private String returnParams;
    /**给参数赋值 int way = Integer.parseInt(request.getParameter("way"));*/
    private String setParams;

    private String extra;

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getSetParams() {
        return setParams;
    }

    public void setSetParams(String setParams) {
        this.setParams = setParams;
    }

    public String getNoTypeParams() {
        return noTypeParams;
    }

    public void setNoTypeParams(String noTypeParams) {
        this.noTypeParams = noTypeParams;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public String getReturnParams() {
        return returnParams;
    }

    public void setReturnParams(String returnParams) {
        this.returnParams = returnParams;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public MethodReplaceVO(){
        this.methodName = "";
        this.params = "";
        this.des = "";
        this.returnParams = "";
    }
}
