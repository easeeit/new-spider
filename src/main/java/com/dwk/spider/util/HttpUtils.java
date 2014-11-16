package com.dwk.spider.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http utils
 * 
 * @author: xp
 * @data : 2014-6-10
 * @since : 1.5
 */
public class HttpUtils {

  private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

  private HttpUtils() {}

  /**
   * Http call.
   * 
   * @param method Http call method.
   * @param url Target url
   * @return
   */
  public static String call(Method method, String url) {
    return call(method, url, null);
  }

  /**
   * @param headers Http request header data
   */
  public static String call(Method method, String url, Map<String, String> headers) {
    return call(method, url, headers, null);
  }

  /**
   * @param reqEntity Http request body data
   */
  public static String call(Method method, String url, Map<String, String> headers, String reqEntity) {
    return call(method, url, headers, reqEntity, null);
  }

  /**
   * @param parameters Http request parameters
   */
  public static String call(Method method, String url, Map<String, String> headers, String reqEntity, Map<String, String> parameters) {
    return call(method, url, headers, reqEntity, parameters, null);
  }

  /**
   * @param config Request config
   */
  public static String call(Method method, String url, Map<String, String> headers, String reqEntity, Map<String, String> parameters,
      RequestConfig config) {
    if (StringUtils.isBlank(url)) {
      logger.error("Http call, 'url' is null.");
      return null;
    }

    try {
      if (!MapUtils.isEmpty(parameters)) {
        List<NameValuePair> listParam = new ArrayList<NameValuePair>();
        for (Entry<String, String> entry : parameters.entrySet()) {
          listParam.add(new BasicNameValuePair(String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
        }
        url += "?" + URLEncodedUtils.format(listParam, Consts.UTF_8);
      }

      HttpUriRequest m = Method.buildMethod(method, url, config);

      if (!MapUtils.isEmpty(headers)) {
        for (Entry<String, String> entry : headers.entrySet()) {
          m.addHeader(entry.getKey(), entry.getValue());
        }
      }

      if (reqEntity != null && reqEntity.length() > 0 && (m instanceof HttpPut || m instanceof HttpPost)) {
        ((HttpEntityEnclosingRequestBase) m).setEntity(new StringEntity(reqEntity.toString(), Consts.UTF_8));
      }

      HttpClient client = HttpClientBuilder.create().build();
      HttpResponse response = client.execute(m);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      HttpEntity resEntity = null;
      if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK && (resEntity = response.getEntity()) != null) {
        InputStream is = null;
        try {
          is = resEntity.getContent();
          if (resEntity.getContentEncoding() != null && resEntity.getContentEncoding().getValue().contains("gzip")) {
            is = new GZIPInputStream(is);
          }
          int len;
          byte[] buf = new byte[1024];
          while ((len = is.read(buf)) > 0) {
            baos.write(buf, 0, len);
          }
        } catch (Exception ex) {
          logger.error("Http call ok, read reponse content has error. Cause:", ex);
        } finally {
          try {
            is.close();
            baos.close();
          } catch (Exception ex) {
            logger.error("Http call ok, close outstream has error. Cause:", ex);
          }
        }
      }
      String charset = "UTF-8";
      Header contentType = response.getFirstHeader("Content-Type");
      if (contentType != null && contentType.getValue().contains("gb")) {
        charset = "GBK";
      }
      return new String(baos.toByteArray(), charset);
    } catch (Exception ex) {
      logger.error("Http call has exception, 'url' is " + url, ex);
      return null;
    }
  }

  public enum Method {
    get {
      @Override
      public HttpUriRequest create(String url, RequestConfig config) {
        HttpGet get = new HttpGet(url);
        if(config!=null) {
          get.setConfig(config);
        }
        return get;
      }
    },
    post {
      @Override
      public HttpUriRequest create(String url, RequestConfig config) {
        HttpPost post = new HttpPost(url);
        if(config!=null) {
          post.setConfig(config);
        }
        return post;
      }
    },
    put {
      @Override
      public HttpUriRequest create(String url, RequestConfig config) {
        HttpPut put = new HttpPut(url);
        if(config!=null) {
          put.setConfig(config);
        }
        return put;
      }
    },
    delete {
      @Override
      public HttpUriRequest create(String url, RequestConfig config) {
        HttpDelete delete = new HttpDelete(url);
        if(config!=null) {
          delete.setConfig(config);
        }
        return delete;
      }
    };
    /**
     * create http method
     */
    abstract HttpUriRequest create(String url, RequestConfig config);

    /**
     * bulid method
     */
    public static HttpUriRequest buildMethod(Method method, String url, RequestConfig config) {
      if (method == null) {
        return Method.get.create(url, config);
      }
      return method.create(url, config);
    }
  }

}
