package pc.easyMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenwen.fu on 2016/1/1.
 * 方法vo
 */
public class MethodVO {
    /**方法名*/
    private String name;
    /**方法注释*/
    private String comments;
    /**要传的参数*/
    private List<MethodParamVO> request;
    /**返回的参数*/
    private List<MethodParamVO> response;

    public List<MethodParamVO> getResponse() {
        return response;
    }

    public void setResponse(List<MethodParamVO> response) {
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<MethodParamVO> getRequest() {
        return request;
    }

    public void setRequest(List<MethodParamVO> request) {
        this.request = request;
    }

    public void addRequest(MethodParamVO paramVO){
        request.add(paramVO);
    }

    public void addResponse(MethodParamVO paramVO){
        response.add(paramVO);
    }

    public static MethodVO getInstance(){
        MethodVO vo = new MethodVO();
        vo.setName("");
        vo.setComments("");
        vo.setRequest(new ArrayList<MethodParamVO>());
        vo.setResponse(new ArrayList<MethodParamVO>());
        return vo;
    }
}
