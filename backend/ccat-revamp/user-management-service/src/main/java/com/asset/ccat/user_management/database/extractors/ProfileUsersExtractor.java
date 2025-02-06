package com.asset.ccat.user_management.database.extractors;

import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.models.shared.UserExtractModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class ProfileUsersExtractor implements ResultSetExtractor<List<UserExtractModel>> {
    @Override
    public List<UserExtractModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        List<UserExtractModel> userExtractModelList = new ArrayList<>();
        while (resultSet.next()) {
            UserExtractModel user = new UserExtractModel();
            user.setUserId(resultSet.getInt(DatabaseStructs.ADM_USERS.USER_ID));
            user.setUsername(resultSet.getString(DatabaseStructs.ADM_USERS.NT_ACCOUNT));
            user.setSource(resultSet.getString(DatabaseStructs.ADM_USERS.SOURCE));
            if(resultSet.getDate(DatabaseStructs.ADM_USERS.CREATION_DATE) != null) {
                user.setCreationDate(resultSet.getDate(DatabaseStructs.ADM_USERS.CREATION_DATE).getTime());
            }
            if(resultSet.getDate(DatabaseStructs.ADM_USERS.MODIFICATION_DATE) != null) {
                user.setModificationDate(resultSet.getDate(DatabaseStructs.ADM_USERS.MODIFICATION_DATE).getTime());
            }
            Date lastLoginDate = resultSet.getDate(DatabaseStructs.ADM_USERS.LAST_LOGIN);
            if (Objects.nonNull(lastLoginDate)) {
                user.setLastLogin(lastLoginDate.getTime());
            }
            userExtractModelList.add(user);
        }
        return userExtractModelList;
    }
}
