package com.changhong.client.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.changhong.client.domain.HandleMessage;
import com.changhong.client.domain.HandleStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

/**
 * User: Jack Wang
 * Date: 16-4-1
 * Time: 上午8:21
 * <p/>
 * 请求的JSON格式
 * request：{
 * appType://APP 类型 1-IOS or 2-Android
 * appVersion://APP current version number
 * businessType://需要访问的业务
 * body:{} //具体的逻辑参数
 * }
 * <p/>
 * 返回的JSON格式
 * response：{
 * message：//参考HandleMessage
 * status: //参考HandleStatus
 * body: {} //具体的返回参数
 * }
 */
@Controller
public class ClientResourceController {

    private static final Log logger = LogFactory.getLog(ClientResourceController.class);

    public final static String APP_TYPE = "appType";
    public final static String APP_VERSION = "appVersion";
    public final static String BUSINESS_TYPE = "businessType";
    public final static String REQUEST_BODAY = "body";

    public final static String MESSAGE_TILE = "message";
    public final static String MESSAGE_STATUS = "status";
    public final static String RESPONSE_BODY = "body";

    @RequestMapping("/client/resources.html")
    protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String json = ServletRequestUtils.getStringParameter(request, "json", "");

        logger.info(json);

        String responseJSON = "";
        try {
            if (!StringUtils.hasText(json)) {
                responseJSON = generateDataNotRightResponse().toJSONString();
            } else {
                JSONObject jsonObject = JSONObject.parseObject(json);
                String businessType = jsonObject.getString(BUSINESS_TYPE);
                String requestBody = jsonObject.getString(REQUEST_BODAY);

                if (ClientBusiness.PROFRAM_INFO.equals(businessType)) {
                    responseJSON = null;
                } else {
                    responseJSON = generateDataNotRightResponse().toJSONString();
                }
             }
        } catch (Exception e) {
            responseJSON = ClientResourceController.generateFailedResponse().toJSONString();
        }

        //返回结果
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(responseJSON);
        writer.flush();
        writer.close();
    }

    /*********************************************分发不同的REQUEST***************************************************/

    public static JSONObject generateDataNotRightResponse() {
        JSONObject jsonObject = new JSONObject();
        generateDataNotRightResponse(jsonObject);
        return jsonObject;
    }

    public static JSONObject generateFailedResponse() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_FAIL.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.FAILED);
        return jsonObject;
    }

    public static JSONObject generateDataNotRightResponse(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_DATA_ERROR.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.DATA_NOT_OK);
        return jsonObject;
    }

    public static JSONObject generateDataNotFoundResponse(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_DATA_NOTFOUND.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.DATA_NOT_FOUND);
        return jsonObject;
    }

    public static JSONObject generateOKResponse(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_OK.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.OK);
        return jsonObject;
    }

    public static JSONObject generateFailedResponse(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_FAIL.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.FAILED);
        return jsonObject;
    }

    public static JSONObject generateNoAuthority(JSONObject jsonObject) {
        jsonObject.put(MESSAGE_TILE, HandleMessage.MESSAGE_NO_AUTHORITY.getDescription());
        jsonObject.put(MESSAGE_STATUS, HandleStatus.NO_AUTHORITY);
        return jsonObject;
    }

    /*********************************************读取REQUEST中的信息***************************************************/

    /**
     * 读取文件
     */
    public static final byte[] readBytes(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return message;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new byte[]{};
    }

}
