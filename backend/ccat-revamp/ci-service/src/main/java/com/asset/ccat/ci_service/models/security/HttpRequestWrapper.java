/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ci_service.models.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;
/**
 *
 * @author Mahmoud Shehab
 */
public class HttpRequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestBody;

    public HttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);

        try {
            requestBody = IOUtils.toByteArray(request.getInputStream());
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

