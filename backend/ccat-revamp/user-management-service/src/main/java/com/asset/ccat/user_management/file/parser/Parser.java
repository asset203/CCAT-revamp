package com.asset.ccat.user_management.file.parser;

import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.models.users.UserModel;
import java.io.InputStream;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public interface Parser {

    public List<UserModel> parse(InputStream inputStream, Boolean requiredAll) throws UserManagementException;

}
