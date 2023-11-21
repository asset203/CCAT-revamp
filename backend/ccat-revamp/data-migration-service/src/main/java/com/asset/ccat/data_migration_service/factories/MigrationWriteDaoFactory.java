package com.asset.ccat.data_migration_service.factories;

import com.asset.ccat.data_migration_service.constants.Tables;
import com.asset.ccat.data_migration_service.database.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Assem.Hassan
 */
@Component
public class MigrationWriteDaoFactory {

    @Autowired
    AdmAccountGroupBitsDao admAccountGroupBitsDao;
    @Autowired
    AdmAccountGroupsDao admAccountGroupsDao;
    @Autowired
    AdmAgBitsScDescriptionDao admAgBitsScDescriptionDao;
    @Autowired
    AdmBusinessPlansDao admBusinessPlansDao;
    @Autowired
    AdmCommunitiesDao admCommunitiesDao;
    @Autowired
    AdmFafBlackRestrictionsDao admFafBlackRestrictionsDao;
    @Autowired
    AdmFafIndicatorsDao admFafIndicatorsDao;
    @Autowired
    AdmFafPlansDao admFafPlansDao;
    @Autowired
    AdmFafWhiteRestrictionsDao admFafWhiteRestrictionsDao;
    @Autowired
    AdmMarqueeDataDao admMarqueeDataDao;
    @Autowired
    AdmOffersDao admOffersDao;
    @Autowired
    AdmProfilesDao admProfilesDao;
    @Autowired
    AdmProfileMonetaryLimitDao admProfileMonetaryLimitDao;
    @Autowired
    AdmProfileServiceClassDao admProfileServiceClassDao;
    @Autowired
    AdmPromotionPlansDao admPromotionPlansDao;
    @Autowired
    AdmReasonActivityDao admReasonActivityDao;
    @Autowired
    AdmServiceClassesDao admServiceClassesDao;
    @Autowired
    AdmServiceClassAccDao admServiceClassAccDao;
    @Autowired
    AdmServiceClassDaDao admServiceClassDaDao;
    @Autowired
    AdmServiceOfferingBitsDao admServiceOfferingBitsDao;
    @Autowired
    AdmServiceOfferingPlansDao admServiceOfferingPlansDao;
    @Autowired
    AdmServiceOffegPlanBitsDao admServiceOffegPlanBitsDao;
    @Autowired
    AdmSoBitsScDescriptionDao admSoBitsScDescriptionDao;
    @Autowired
    AdmSoScDescriptionDao admSoScDescriptionDao;
    @Autowired
    AdmTxCodesDao admTxCodesDao;
    @Autowired
    AdmTxCodesRenewalValueDao admTxCodesRenewalValueDao;
    @Autowired
    AdmTxFeatureTypesDao admTxFeatureTypesDao;
    @Autowired
    AdmTxLinksDao admTxLinksDao;
    @Autowired
    AdmTxTypesDao admTxTypesDao;
    @Autowired
    AdmUsersDao admUsersDao;
    @Autowired
    DssColumnsDao dssColumnsDao;
    @Autowired
    EtopupTransactionsTestDao etopupTransactionsTestDao;
    @Autowired
    LkAccountFlagsDao lkAccountFlagsDao;
    @Autowired
    LkAccumulatorsDao lkAccumulatorsDao;
    @Autowired
    LkAirServersDao lkAirServersDao;
    @Autowired
    LkBtTransactionStatusDao lkBtTransactionStatusDao;
    @Autowired
    LkCcFeaturesDao lkCcFeaturesDao;
    @Autowired
    LkDedAccountsDao lkDedAccountsDao;
    @Autowired
    LkDisconnectionCodesDao lkDisconnectionCodesDao;
    @Autowired
    LkLanguagesDao lkLanguagesDao;
    @Autowired
    LkMaredCardListDao lkMaredCardListDao;
    @Autowired
    LkMonetaryLimitsDao lkMonetaryLimitsDao;
    @Autowired
    LkOffersStatesDao lkOffersStatesDao;
    @Autowired
    LkOffersTypesDao lkOffersTypesDao;
    @Autowired
    LkPamClassesDao lkPamClassesDao;
    @Autowired
    LkPamPeriodDao lkPamPeriodDao;
    @Autowired
    LkPamPriorityDao lkPamPriorityDao;
    @Autowired
    LkPamSchedulesDao lkPamSchedulesDao;
    @Autowired
    LkPamServicesDao lkPamServicesDao;
    @Autowired
    LkRefillPaymentProfileDao lkRefillPaymentProfileDao;
    @Autowired
    LkUnitTypeDescDao lkUnitTypeDescDao;
    @Autowired
    LkUsageCounterDescDao lkUsageCounterDescDao;
    @Autowired
    LkVoucherServersDao lkVoucherServersDao;
    @Autowired
    AdmSystemPropertiesDao admSystemPropertiesDao;

    public BaseMigrationWriteDao getDao(String tableName) {

        if (tableName.equals(Tables.ADM_ACCOUNT_GROUP_BITS.name())) {
            return admAccountGroupBitsDao;
        } else if (tableName.equals(Tables.ADM_ACCOUNT_GROUPS.name())) {
            return admAccountGroupsDao;
        } else if (tableName.equals(Tables.ADM_AG_BITS_SC_DESCRIPTION.name())) {
            return admAgBitsScDescriptionDao;
        } else if (tableName.equals(Tables.ADM_BUSINESS_PLANS.name())) {
            return admBusinessPlansDao;
        } else if (tableName.equals(Tables.ADM_COMMUNITIES.name())) {
            return admCommunitiesDao;
        } else if (tableName.equals(Tables.ADM_FAF_BLACK_RESTRICTIONS.name())) {
            return admFafBlackRestrictionsDao;
        } else if (tableName.equals(Tables.ADM_FAF_INDICATORS.name())) {
            return admFafIndicatorsDao;
        } else if (tableName.equals(Tables.ADM_FAF_PLANS.name())) {
            return admFafPlansDao;
        } else if (tableName.equals(Tables.ADM_FAF_WHITE_RESTRICTIONS.name())) {
            return admFafWhiteRestrictionsDao;
        } else if (tableName.equals(Tables.ADM_MARQUEE_DATA.name())) {
            return admMarqueeDataDao;
        } else if (tableName.equals(Tables.ADM_OFFERS.name())) {
            return admOffersDao;
        } else if (tableName.equals(Tables.ADM_PROFILES.name())) {
            return admProfilesDao;
        } else if (tableName.equals(Tables.ADM_PROFILE_MONETARY_LIMIT.name())) {
            return admProfileMonetaryLimitDao;
        } else if (tableName.equals(Tables.ADM_PROFILE_SERVICECLASS.name())) {
            return admProfileServiceClassDao;
        } else if (tableName.equals(Tables.ADM_PROMOTION_PLANS.name())) {
            return admPromotionPlansDao;
        } else if (tableName.equals(Tables.ADM_REASON_ACTIVITY.name())) {
            return admReasonActivityDao;
        } else if (tableName.equals(Tables.ADM_SERVICE_CLASSES.name())) {
            return admServiceClassesDao;
        } else if (tableName.equals(Tables.ADM_SERVICE_CLASS_ACC.name())) {
            return admServiceClassAccDao;
        } else if (tableName.equals(Tables.ADM_SERVICE_CLASS_DA.name())) {
            return admServiceClassDaDao;
        } else if (tableName.equals(Tables.ADM_SERVICE_OFFERING_BITS.name())) {
            return admServiceOfferingBitsDao;
        } else if (tableName.equals(Tables.ADM_SERVICE_OFFERING_PLANS.name())) {
            return admServiceOfferingPlansDao;
        } else if (tableName.equals(Tables.ADM_SERVICE_OFFG_PLAN_BITS.name())) {
            return admServiceOffegPlanBitsDao;
        } else if (tableName.equals(Tables.ADM_SO_BITS_SC_DESCRIPTION.name())) {
            return admSoBitsScDescriptionDao;
        } else if (tableName.equals(Tables.ADM_SO_SC_DESCRIPTION.name())) {
            return admSoScDescriptionDao;
        } else if (tableName.equals(Tables.ADM_TX_CODES.name())) {
            return admTxCodesDao;
        } else if (tableName.equals(Tables.ADM_TX_CODES_RENEWAL_VALUE.name())) {
            return admTxCodesRenewalValueDao;
        } else if (tableName.equals(Tables.ADM_TX_FEATURE_TYPES.name())) {
            return admTxFeatureTypesDao;
        } else if (tableName.equals(Tables.ADM_TX_LINKS.name())) {
            return admTxLinksDao;
        } else if (tableName.equals(Tables.ADM_TX_TYPES.name())) {
            return admTxTypesDao;
        } else if (tableName.equals(Tables.ADM_USERS.name())) {
            return admUsersDao;
        } else if (tableName.equals(Tables.DSS_COLUMNS.name())) {
            return dssColumnsDao;
        } else if (tableName.equals(Tables.ETOPUP_TRANSACTIONS_TEST.name())) {
            return etopupTransactionsTestDao;
        } else if (tableName.equals(Tables.LK_ACCOUNT_FLAGS.name())) {
            return lkAccountFlagsDao;
        } else if (tableName.equals(Tables.LK_ACCUMULATORS.name())) {
            return lkAccumulatorsDao;
        } else if (tableName.equals(Tables.LK_AIR_SERVERS.name())) {
            return lkAirServersDao;
        } else if (tableName.equals(Tables.LK_BT_TRANSACTION_STATUS.name())) {
            return lkBtTransactionStatusDao;
        } else if (tableName.equals(Tables.LK_CC_FEATURES.name())) {
            return lkCcFeaturesDao;
        } else if (tableName.equals(Tables.LK_DED_ACCOUNTS.name())) {
            return lkDedAccountsDao;
        } else if (tableName.equals(Tables.ADM_DISCONNECTION_CODES.name())) {
            return lkDisconnectionCodesDao;
        } else if (tableName.equals(Tables.ADM_LANGUAGES.name())) {
            return lkLanguagesDao;
        } else if (tableName.equals(Tables.LK_MAREDCARD_LIST.name())) {
            return lkMaredCardListDao;
        } else if (tableName.equals(Tables.LK_MONETARY_LIMITS.name())) {
            return lkMonetaryLimitsDao;
        } else if (tableName.equals(Tables.LK_OFFERS_STATES.name())) {
            return lkOffersStatesDao;
        } else if (tableName.equals(Tables.LK_OFFERS_TYPES.name())) {
            return lkOffersTypesDao;
        } else if (tableName.equals(Tables.LK_PAM_CLASSES.name())) {
            return lkPamClassesDao;
        } else if (tableName.equals(Tables.LK_PAM_PERIOD.name())) {
            return lkPamPeriodDao;
        } else if (tableName.equals(Tables.LK_PAM_PRIORITY.name())) {
            return lkPamPriorityDao;
        } else if (tableName.equals(Tables.LK_PAM_SCHEDULES.name())) {
            return lkPamSchedulesDao;
        } else if (tableName.equals(Tables.LK_PAM_SERVICES.name())) {
            return lkPamServicesDao;
        } else if (tableName.equals(Tables.LK_REFILL_PAYMENT_PROFILE.name())) {
            return lkRefillPaymentProfileDao;
        } else if (tableName.equals(Tables.LK_UNIT_TYPE_DESC.name())) {
            return lkUnitTypeDescDao;
        } else if (tableName.equals(Tables.LK_USAGE_COUNTER_DESC.name())) {
            return lkUsageCounterDescDao;
        } else if (tableName.equals(Tables.LK_VOUCHER_SERVERS.name())) {
            return lkVoucherServersDao;
        } else if (tableName.equals(Tables.ADM_SYSTEM_PROPERTIES.name())) {
            return admSystemPropertiesDao;
        } else {
            return null;
        }
    }
}
