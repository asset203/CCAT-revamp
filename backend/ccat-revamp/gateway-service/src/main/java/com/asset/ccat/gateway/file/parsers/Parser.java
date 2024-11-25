package com.asset.ccat.gateway.file.parsers;

import com.asset.ccat.gateway.exceptions.GatewayException;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public interface Parser {

    public List<String> parse(InputStream inputStream) throws GatewayException;

}
