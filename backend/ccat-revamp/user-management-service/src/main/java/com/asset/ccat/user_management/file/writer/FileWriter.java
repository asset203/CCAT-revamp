package com.asset.ccat.user_management.file.writer;

import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.models.dtoWrappers.ExtractAllUsersProfilesWrapper;
import com.asset.ccat.user_management.models.users.UserModel;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public interface FileWriter {
    
    public abstract byte[] writeToFile(List<UserModel> users) throws UserManagementException;
    byte[] writeUsersProfilesFile(ExtractAllUsersProfilesWrapper extractAllUsersProfilesWrapper) throws UserManagementException;

}
