package util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import pc.Cfg;
import pc.easyMethod.MethodParamVO;
import pc.easyMethod.MethodVO;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wenwen.fu on 2017/4/24.
 */
public class ParseMethodXmlUtil {
    /**
     * 解析 test/method.xml获得方法列表
     *
     * @return
     * @throws DocumentException
     */
    public static List<MethodVO> parseXML(String methodXmlUrl) {
        List<MethodVO> methodVOList = new ArrayList<MethodVO>();

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(methodXmlUrl));

            Element root = document.getRootElement();
            List<Element> methods = root.elements();

            for (Element method : methods) {
                MethodVO methodVO = MethodVO.getInstance();

                methodVO.setName(method.attribute("name").getValue());
                methodVO.setComments(method.attribute("comments").getValue());

                //request AND response
                List<Element> requestAndResponses = method.elements();
                for (Element e : requestAndResponses) {
                    if (e.getName().equals("request")) {
                        for (Element element : (List<Element>) e.elements()) {
                            MethodParamVO paramVO = new MethodParamVO();
                            paramVO.setComments(element.attributeValue("comments"));
                            paramVO.setName(element.attributeValue("name"));
                            paramVO.setType(element.attributeValue("type"));
                            methodVO.addRequest(paramVO);
                        }
                    } else if (e.getName().equals("response")) {
                        for (Element element : (List<Element>) e.elements()) {
                            MethodParamVO paramVO = new MethodParamVO();
                            paramVO.setComments(element.attributeValue("comments"));
                            paramVO.setName(element.attributeValue("name"));
                            paramVO.setType(element.attributeValue("type"));
                            methodVO.addResponse(paramVO);
                        }
                    }
                }
                methodVOList.add(methodVO);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return methodVOList;
    }
}
