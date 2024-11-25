package com.asset.ccat.data_migration_service.cache;


import com.asset.ccat.data_migration_service.constants.Tables;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Assem.Hassan
 */
@Component
public class InsertQueriesCache {

    private ConcurrentHashMap<String, String> queriesMap;


    @PostConstruct
    private void postConstruct() {
        queriesMap = new ConcurrentHashMap<>();
        queriesMap.put(Tables.ADM_ACCOUNT_GROUP_BITS.name(), "INSERT INTO " + Tables.ADM_ACCOUNT_GROUP_BITS
                + "(BIT_POSITION,BIT_NAME) values(?,?)");
        queriesMap.put(Tables.ADM_ACCOUNT_GROUPS.name(), "INSERT INTO " + Tables.ADM_ACCOUNT_GROUPS
                + "(GROUP_ID,NAME,IS_DELETED) values(?,?,?)");
        queriesMap.put(Tables.ADM_AG_BITS_SC_DESCRIPTION.name(), "INSERT INTO " + Tables.ADM_AG_BITS_SC_DESCRIPTION
                + "(BIT_POSITION,SERVICE_CLASS_ID,DESCRIPTION) values(?,?,?)");
        queriesMap.put(Tables.ADM_BUSINESS_PLANS.name(), "INSERT INTO " + Tables.ADM_BUSINESS_PLANS
                + "(ID,NAME,CODE,SERVICE_CLASS_ID,HLR_ID,IS_DELETED,ACTIVATION_PLAN) values(?,?,?,?,?,?,?)");
        queriesMap.put(Tables.ADM_COMMUNITIES.name(), "INSERT INTO " + Tables.ADM_COMMUNITIES
                + "(COMMUNITY_ID,COMMUNITY_DESCRIPTION,IS_DELETED) values(?,?,?)");
        queriesMap.put(Tables.ADM_FAF_BLACK_RESTRICTIONS.name(), "INSERT INTO " + Tables.ADM_FAF_BLACK_RESTRICTIONS
                + "(RESTRICTION_ID,NUMBER_PATTERN,DESCRIPTION,IS_DELETED) values(?,?,?,?)");
        queriesMap.put(Tables.ADM_FAF_INDICATORS.name(), "INSERT INTO " + Tables.ADM_FAF_INDICATORS
                + "(ID,INDICATOR_ID,INDICATOR_NAME,IS_DELETED,MAPPED_INDICATOR_ID) values(?,?,?,?,?)");
        queriesMap.put(Tables.ADM_FAF_PLANS.name(), "INSERT INTO " + Tables.ADM_FAF_PLANS
                + "(PLAN_ID,NAME,INDICATOR_ID,IS_DELETED) values(?,?,?,?)");
        queriesMap.put(Tables.ADM_FAF_WHITE_RESTRICTIONS.name(), "INSERT INTO " + Tables.ADM_FAF_WHITE_RESTRICTIONS
                + "(RESTRICTION_ID,NUMBER_PATTERN,DESCRIPTION,IS_DELETED) values(?,?,?,?)");
        queriesMap.put(Tables.ADM_MARQUEE_DATA.name(), "INSERT INTO " + Tables.ADM_MARQUEE_DATA
                + "(TITLE,DESCRIPTION) values(?,?)");
        queriesMap.put(Tables.ADM_OFFERS.name(), "INSERT INTO " + Tables.ADM_OFFERS
                + "(OFFER_ID,OFFER_DESC,OFFER_TYPE_ID,IS_DROP_DOWN_ENABLED) values(?,?,?,?)");
        queriesMap.put(Tables.ADM_PROFILES.name(), "INSERT INTO " + Tables.ADM_PROFILES
                + "(PROFILE_ID,PROFILE_NAME,IS_DELETED,IS_FOOTPRINT_REQUIRED,SESSION_LIMIT,ADJUSTMENTS_LIMITED) values(?,?,?,?,?,?)");
        queriesMap.put(Tables.ADM_PROFILE_MONETARY_LIMIT.name(), "INSERT INTO " + Tables.ADM_PROFILE_MONETARY_LIMIT
                + "(PROFILE_ID,LIMIT_ID,VALUE) values(?,?,?)");
        queriesMap.put(Tables.ADM_PROFILE_SERVICECLASS.name(), "INSERT INTO " + Tables.ADM_PROFILE_SERVICE_CLASS
                + "(SERVICE_CLASS_ID,PROFILE_ID) values(?,?)");
        queriesMap.put(Tables.ADM_PROMOTION_PLANS.name(), "INSERT INTO " + Tables.ADM_PROMOTION_PLANS
                + "(ID,PLAN_ID,NAME,IS_DEFAULT,IS_DELETED) values(?,?,?,?,?)");
        queriesMap.put(Tables.ADM_REASON_ACTIVITY.name(), "INSERT INTO " + Tables.ADM_REASON_ACTIVITY
                + "(ACTIVITY_ID,ACTIVITY_TYPE,PARENT_ID,ACTIVITY_NAME,IS_ACTIVE) values(?,?,?,?,?)");
        queriesMap.put(Tables.ADM_SERVICE_CLASSES.name(), "INSERT INTO " + Tables.ADM_SERVICE_CLASSES
                + "(NAME,CODE,PREACTIVATION_ALLOWED,IS_DELETED,IS_CI_CONVERSION,CI_SERVICE_NAME,CI_PACKAGE_NAME,IS_ALLOW_MIGRATION,TYPE,HAS_EMPTY_LIMIT,DEFAULT_LIMIT,CUSTOM_LIMIT) values(?,?,?,?,?,?,?,?,?,?,?,?)");
        queriesMap.put(Tables.ADM_SERVICE_CLASS_ACC.name(), "INSERT INTO " + Tables.ADM_SERVICE_CLASS_ACC
                + "(SERVICE_CLASS_ID,DESCRIPTION,ACC_ID) values(?,?,?)");
        queriesMap.put(Tables.ADM_SERVICE_CLASS_DA.name(), "INSERT INTO " + Tables.ADM_SERVICE_CLASS_DA
                + "(SERVICE_CLASS_ID,DESCRIPTION,DA_ID,RATING_FACTOR) values(?,?,?,?)");
        queriesMap.put(Tables.ADM_SERVICE_OFFERING_BITS.name(), "INSERT INTO " + Tables.ADM_SERVICE_OFFERING_BITS
                + "(BIT_POSITION,BIT_NAME) values(?,?)");
        queriesMap.put(Tables.ADM_SERVICE_OFFERING_PLANS.name(), "INSERT INTO " + Tables.ADM_SERVICE_OFFERING_PLANS
                + "(PLAN_ID,NAME,IS_DELETED) values(?,?,?)");
        queriesMap.put(Tables.ADM_SERVICE_OFFG_PLAN_BITS.name(), "INSERT INTO " + Tables.ADM_SERVICE_OFFG_PLAN_BITS
                + "(PLAN_ID,BIT_POSITION,IS_ENABLED) values(?,?,?)");
        queriesMap.put(Tables.ADM_SO_BITS_SC_DESCRIPTION.name(), "INSERT INTO " + Tables.ADM_SO_BITS_SC_DESCRIPTION
                + "(BIT_POSITION,SERVICE_CLASS_ID,DESCRIPTION) values(?,?,?)");
        queriesMap.put(Tables.ADM_SO_SC_DESCRIPTION.name(), "INSERT INTO " + Tables.ADM_SO_SC_DESCRIPTION
                + "(PLAN_ID,SERVICE_CLASS_ID,DESCRIPTION) values(?,?,?)");
        queriesMap.put(Tables.ADM_TX_CODES.name(), "INSERT INTO " + Tables.ADM_TX_CODES
                + "(ID,NAME,VALUE,IS_DEFAULT,IS_DELETED) values(?,?,?,?,?)");
        queriesMap.put(Tables.ADM_TX_CODES_RENEWAL_VALUE.name(), "INSERT INTO " + Tables.ADM_TX_CODES_RENEWAL_VALUE
                + "(ID,CODE_ID,RENEWALS_VALUE) values(?,?,?)");
        queriesMap.put(Tables.ADM_TX_FEATURE_TYPES.name(), "INSERT INTO " + Tables.ADM_TX_FEATURE_TYPES
                + "(FEATURE_ID,TYPE_ID) values(?,?)");
        queriesMap.put(Tables.ADM_TX_LINKS.name(), "INSERT INTO " + Tables.ADM_TX_LINKS
                + "(TX_TYPE_ID,TX_CODE_ID) values(?,?)");
        queriesMap.put(Tables.ADM_TX_TYPES.name(), "INSERT INTO " + Tables.ADM_TX_TYPES
                + "(ID,NAME,VALUE,IS_DEFAULT,IS_DELETED) values(?,?,?,?,?)");
        queriesMap.put(Tables.ADM_USERS.name(), "INSERT INTO " + Tables.ADM_USERS
                + "(USER_ID,NT_ACCOUNT,PROFILE_ID,IS_DELETED,IS_ACTIVE,SOURCE,SESSION_COUNTER,THEME_ID,BILLING_ACCOUNT,CREATION_DATE,MODIFICATION_DATE,LAST_LOGIN) values(?,?,?,?,?,?,?,?,?,to_date(?, 'YYYY-MM-DD'),to_date(?, 'YYYY-MM-DD'),to_date(?, 'YYYY-MM-DD'))");
        queriesMap.put(Tables.DSS_COLUMNS.name(), "INSERT INTO " + Tables.DSS_COLUMNS
                + "(DSS_PAGENAME,COLUMN_NAME,DISPLAYNAME) values(?,?,?)");
        queriesMap.put(Tables.ETOPUP_TRANSACTIONS_TEST.name(), "INSERT INTO " + Tables.ETOPUP_TRANSACTIONS_TEST
                + "(TRANSFER_DATE,TRANSACTION_WAY,TRANSACTION_TYPE,TRANSITION_CATEGORY,CHANNEL_TYPE,SENDER_MSISDN,RECEIVER_MSISDN,SENDER_CATEGORY,RECEIVER_CATEGORY,TRANSFER_AMOUNT,PRODUCT,REQUEST_SOURCE) values(?,?,?,?,?,?,?,?,?,?,?,?)");
        queriesMap.put(Tables.LK_ACCOUNT_FLAGS.name(), "INSERT INTO " + Tables.LK_ACCOUNT_FLAGS
                + "(ACCOUNT_FLAG_CODE,ACCOUNT_FLAG_NAME) values(?,?)");
        queriesMap.put(Tables.LK_ACCUMULATORS.name(), "INSERT INTO " + Tables.LK_ACCUMULATORS
                + "(ACC_ID) values(?)");
        queriesMap.put(Tables.LK_AIR_SERVERS.name(), "INSERT INTO " + Tables.LK_AIR_SERVERS
                + "(URL,AGENT_NAME,HOST,AUTHORIZATION,IS_DOWN,ID,CAPABILITY_VALUE) values(?,?,?,?,?,?,?)");
        queriesMap.put(Tables.LK_BT_TRANSACTION_STATUS.name(), "INSERT INTO " + Tables.LK_BT_TRANSACTION_STATUS
                + "(CODE,NAME) values(?,?)");
        queriesMap.put(Tables.LK_CC_FEATURES.name(), "INSERT INTO " + Tables.LK_CC_FEATURES
                + "(CC_FEATURE_ID,CC_FEATURE_NAME,HAS_TRX_TYPE,PAGE_NAME) values(?,?,?,?)");
        queriesMap.put(Tables.LK_DED_ACCOUNTS.name(), "INSERT INTO " + Tables.LK_DED_ACCOUNTS
                + "(DA_ID) values(?)");
        queriesMap.put(Tables.ADM_DISCONNECTION_CODES.name(), "INSERT INTO " + Tables.LK_DISCONNECTION_CODES
                + "(ID,CODE_ID,NAME,IS_DELETED) values(?,?,?,?)");
        queriesMap.put(Tables.ADM_LANGUAGES.name(), "INSERT INTO " + Tables.LK_LANGUAGES
                + "(ID,NAME) values(?,?)");
        queriesMap.put(Tables.LK_MAREDCARD_LIST.name(), "INSERT INTO " + Tables.LK_MAREDCARD_LIST
                + "(NAME,ID) values(?,?)");
        queriesMap.put(Tables.LK_MONETARY_LIMITS.name(), "INSERT INTO " + Tables.LK_MONETARY_LIMITS
                + "(LIMIT_ID,LIMIT_NAME,DEFAULT_VALUE) values(?,?,?)");
        queriesMap.put(Tables.LK_OFFERS_STATES.name(), "INSERT INTO " + Tables.LK_OFFERS_STATES
                + "(STATE_ID,STATE_DESC) values(?,?)");
        queriesMap.put(Tables.LK_OFFERS_TYPES.name(), "INSERT INTO " + Tables.LK_OFFERS_TYPES
                + "(TYPE_ID,TYPE_DESC) values(?,?)");
        queriesMap.put(Tables.LK_PAM_CLASSES.name(), "INSERT INTO " + Tables.LK_PAM_CLASSES
                + "(ID,NAME) values(?,?)");
        queriesMap.put(Tables.LK_PAM_PERIOD.name(), "INSERT INTO " + Tables.LK_PAM_PERIOD
                + "(ID,NAME) values(?,?)");
        queriesMap.put(Tables.LK_PAM_PRIORITY.name(), "INSERT INTO " + Tables.LK_PAM_PRIORITY
                + "(ID,NAME) values(?,?)");
        queriesMap.put(Tables.LK_PAM_SCHEDULES.name(), "INSERT INTO " + Tables.LK_PAM_SCHEDULES
                + "(ID,NAME) values(?,?)");
        queriesMap.put(Tables.LK_PAM_SERVICES.name(), "INSERT INTO " + Tables.LK_PAM_SERVICES
                + "(ID,NAME) values(?,?)");
        queriesMap.put(Tables.LK_REFILL_PAYMENT_PROFILE.name(), "INSERT INTO " + Tables.LK_REFILL_PAYMENT_PROFILE
                + "(REFILL_PROFILE_ID,REFILL_PROFILE_NAME,AMOUNT_FROM,AMOUNT_TO) values(?,?,?,?)");
        queriesMap.put(Tables.LK_UNIT_TYPE_DESC.name(), "INSERT INTO " + Tables.LK_UNIT_TYPE_DESC
                + "(ID,NAME) values(?,?)");
        queriesMap.put(Tables.LK_USAGE_COUNTER_DESC.name(), "INSERT INTO " + Tables.LK_USAGE_COUNTER_DESC
                + "(ID,NAME) values(?,?)");
        queriesMap.put(Tables.LK_VOUCHER_SERVERS.name(), "INSERT INTO " + Tables.LK_VOUCHER_SERVERS
                + "(URL,AGENT_NAME,HOST,AUTHORIZATION,SERVER_INDEX,VOUCHER_SERIAL_LENGTH,CAPABILITY_VALUE) values(?,?,?,?,?,?,?)");
    }

    public ConcurrentHashMap<String, String> getQueriesMap() {
        return queriesMap;
    }

    public void setQueriesMap(ConcurrentHashMap<String, String> queriesMap) {
        this.queriesMap = queriesMap;
    }
}