/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

/**
 *
 * @author Mahmoud Shehab
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestBody;
    private ByteArrayOutputStream cachedBytes;

    public HttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        try {
            //            requestBody = IOUtils.toByteArray(request.getInputStream());
            cachedBytes = new ByteArrayOutputStream();
            IOUtils.copy(super.getInputStream(), cachedBytes);
            requestBody = cachedBytes.toByteArray();
        } catch (IOException ex) {
            requestBody = new byte[0];
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public boolean isReady() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void setReadListener(ReadListener listener) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

}
