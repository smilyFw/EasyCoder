package pc.easyMethod;

/**
 * 方法的参数信息
 * Created by wenwen.fu on 2016/1/1.
 */
public class MethodParamVO {
    /**参数名*/
    public String name;
    /**参数类型*/
    public String type;
    /**参数的注释*/
    public String comments;

    public MethodParamVO(){
        name = "";
        type = "";
        comments = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


}
