/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.extractors.DedicatedAccountsExtractor;
import com.asset.ccat.lookup_service.database.extractors.FootPrintPagesExtractor;
import com.asset.ccat.lookup_service.database.extractors.LookupsExtractor;
import com.asset.ccat.lookup_service.database.extractors.ProfilesServiceClassesExtractor;
import com.asset.ccat.lookup_service.database.extractors.ServiceClassesExtractor;
import com.asset.ccat.lookup_service.database.extractors.ServiceOfferingPlansExtractor;
import com.asset.ccat.lookup_service.database.extractors.SmsTemplateParamExtractor;
import com.asset.ccat.lookup_service.database.extractors.SuperFlexOffersMapExtractor;
import com.asset.ccat.lookup_service.database.row_mapper.LkVoucherServersRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AIRServer;
import com.asset.ccat.lookup_service.models.AdmAccountGroup;
import com.asset.ccat.lookup_service.models.DedicatedAccount;
import com.asset.ccat.lookup_service.models.FeatureModel;
import com.asset.ccat.lookup_service.models.FootPrintPageModel;
import com.asset.ccat.lookup_service.models.HlrProfileModel;
import com.asset.ccat.lookup_service.models.LookupModel;
import com.asset.ccat.lookup_service.models.MenuModel;
import com.asset.ccat.lookup_service.models.MonetaryLimitModel;
import com.asset.ccat.lookup_service.models.OfferModel;
import com.asset.ccat.lookup_service.models.OfferStateModel;
import com.asset.ccat.lookup_service.models.OfferTypeModel;
import com.asset.ccat.lookup_service.models.RefillPaymentProfileModel;
import com.asset.ccat.lookup_service.models.ServiceClassModel;
import com.asset.ccat.lookup_service.models.ServiceOfferingPlan;
import com.asset.ccat.lookup_service.models.SmsActionModel;
import com.asset.ccat.lookup_service.models.SmsTemplateModel;
import com.asset.ccat.lookup_service.models.SmsTemplateParamModel;
import com.asset.ccat.lookup_service.models.SuperFlexLookupModel;
import com.asset.ccat.lookup_service.models.VoucherServerModel;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClassResponse;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Mahmoud Shehab
 */
@Repository
public class LookupsDao {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Autowired
  ServiceClassesExtractor classesExtractor;

  @Autowired
  LookupsExtractor lookupsExtractor;

  @Autowired
  ProfilesServiceClassesExtractor profilesServiceClassesExtractor;

  @Autowired
  ServiceOfferingPlansExtractor serviceOfferingPlansExtractor;

  @Autowired
  DedicatedAccountsExtractor dedicatedAccountsExtractor;

  @Autowired
  FootPrintPagesExtractor footPrintPagesExtractor;

  @Autowired
  SmsTemplateParamExtractor smsTemplateParamExtractor;

  @Autowired
  SuperFlexOffersMapExtractor superFlexOffersMapExtractor;

  private String retrieveServiceClasses;
  private String retrieveRefillProfiles;
  private String retrieveOffersQuery;
  private String retrieveOfferStatesQuery;
  private String retrieveOfferTypesQuery;
  private String retrieveHLRProfiles;
  private String retrieveProfilesServiceClassesQuery;
  private String retrieveServiceOfferingPlansWithBitsQuery;

  public List<AIRServer> retrieveAirServers() {
    List<AIRServer> result = null;
    String sql = "";
    try {
      sql = "SELECT * FROM " + Defines.LOOKUPS.LK_AIR_SERVERS
          + " Order By "
          + Defines.LOOKUPS.AGENT_NAME
          + " ASC ";
      result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AIRServer.class));
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while retrieving air servers" + sql);
      CCATLogger.ERROR_LOGGER.error("Error while retrieving air servers " + sql, ex);
    }
    return result;
  }

  public HashMap<String, String> retrieveRegions() {
    HashMap<String, String> result = null;
    String sql = "";
    try {
      sql = "SELECT * FROM " + Defines.LOOKUPS.LK_REGIONS;
      result = jdbcTemplate.query(sql, (ResultSet rs) -> {
        HashMap<String, String> map = new HashMap<>();
        while (rs.next()) {
          map.put(rs.getString(DatabaseStructs.LK_REGIONS.REGION),
              rs.getString(DatabaseStructs.LK_REGIONS.REGION_DESCRIPTION));
        }
        return map;
      });
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while retrieving regions" + sql);
      CCATLogger.ERROR_LOGGER.error("Error while retrieving regions " + sql, ex);
    }
    return result;
  }

  public List<ServiceClassModel> retrieveServiceClasses() throws LookupException {
    List<ServiceClassModel> result = null;
    try {
      if (retrieveServiceClasses == null) {
        String sql = " select " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.NAME + ","
            + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.CODE + ","
            + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.PREACTIVATION_ALLOWED + ","
            + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.IS_GRANDFATHER + ","
            + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.IS_CI_CONVERSION + ","
            + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.CI_SERVICE_NAME + ","
            + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.CI_PACKAGE_NAME + ","
            + DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASS_ACC.ACC_ID + ","
            + DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASS_ACC.DESCRIPTION + ","
            + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID + ","
            + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASS_DA.DESCRIPTION + ","
            + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASS_DA.RATING_FACTOR + ","
            + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.IS_ALLOW_MIGRATION
            + " from " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + ","
            + DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME + ","
            + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME
            + " Where " + DatabaseStructs.ADM_SERVICE_CLASSES.IS_DELETED + " = 0 "
            + " And " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
            + " = " + DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASS_ACC.SERVICE_CLASS_ID + "(+)"
            + " And " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
            + " = " + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASS_DA.SERVICE_CLASS_ID + "(+)"
            + " Order by lower(" + DatabaseStructs.ADM_SERVICE_CLASSES.NAME + ")";
        retrieveServiceClasses = sql;
      }
      CCATLogger.DEBUG_LOGGER.debug("retrieveServiceClasses " + retrieveServiceClasses);
      result = jdbcTemplate.query(retrieveServiceClasses, classesExtractor);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveServiceClasses + "\n" + ex);
      CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveServiceClasses, ex);
    }
    return result;
  }

  public List<VoucherServerModel> retrieveVoucherServers() {
    List<VoucherServerModel> result = null;
    String sql = "";
    try {
      sql = "SELECT * FROM " + DatabaseStructs.LK_VOUCHER_SERVERS.TABLE_NAME + " ORDER BY "
          + DatabaseStructs.LK_VOUCHER_SERVERS.VOUCHER_SERIAL_LENGTH;
      result = jdbcTemplate.query(sql, new LkVoucherServersRowMapper());
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while retrieving voucher servers" + sql);
      CCATLogger.ERROR_LOGGER.error("Error while retrieving voucher servers " + sql, ex);
    }
    return result;
  }

//	public List<ServiceClassModel> retrieveServiceClasses() throws LookupException {
//		List<ServiceClassModel> result = null;
//		try {
//			if (retrieveServiceClasses == null) {
//				String sql = " select " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.ID + "," + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
//						+ "." + DatabaseStructs.ADM_SERVICE_CLASSES.NAME + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.CODE + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.PREACTIVATION_ALLOWED + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.IS_GRANDFATHER + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.IS_CI_CONVERSION + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.CI_SERVICE_NAME + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.CI_PACKAGE_NAME + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASS_ACC.ACC_ID + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASS_ACC.DESCRIPTION + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASS_DA.DESCRIPTION + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASS_DA.RATING_FACTOR + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.IS_ALLOW_MIGRATION + " from "
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME + ","
//						+ DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + " Where "
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.IS_DELETED + " = 0 " + " And "
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.ID
//						+ " = " + DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASS_ACC.SERVICE_CLASS_ID + "(+)" + " And "
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.ID
//						+ " = " + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASS_DA.SERVICE_CLASS_ID + "(+)" + " Order by lower("
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.NAME + ")";
//				retrieveServiceClasses = sql;
//			}
//			CCATLogger.DEBUG_LOGGER.debug("retrieveServiceClasses " + retrieveServiceClasses);
//			result = jdbcTemplate.query(retrieveServiceClasses, classesExtractor);
//		} catch (Exception ex) {
//			CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveServiceClasses + "\n" + ex);
//			CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveServiceClasses, ex);
//		}
//		return result;
//	}

  public List<LookupModel> retrieveLookup(String lookupName) {
    try {
      String sql = "SELECT ID,NAME FROM " + lookupName
          + " Order By "
          + lookupName + ".NAME"
          + " ASC ";
      return jdbcTemplate.query(sql, lookupsExtractor);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Exception occurred while retrieving " + lookupName);
      CCATLogger.ERROR_LOGGER.error("Exception occurred while retrieving " + lookupName, ex);
    }
    return null;
  }


  public List<RefillPaymentProfileModel> retrieveRefillProfiles() {
    List<RefillPaymentProfileModel> result = null;
    try {
      if ("".equals(retrieveRefillProfiles) || retrieveRefillProfiles == null) {
        retrieveRefillProfiles =
            "SELECT * " + " FROM " + DatabaseStructs.LK_REFILL_PAYMENT_PROFILE.TABLE_NAME
                + " ORDER BY LOWER(" + DatabaseStructs.LK_REFILL_PAYMENT_PROFILE.REFILL_PROFILE_NAME
                + ")";
      }
      return jdbcTemplate.query(retrieveRefillProfiles,
          new BeanPropertyRowMapper<>(RefillPaymentProfileModel.class));
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error(
          "Error while retrieving refill profiles " + retrieveRefillProfiles);
      CCATLogger.ERROR_LOGGER.error(
          "Error while retrieving refill profiles " + retrieveRefillProfiles, ex);
    }
    return result;
  }

  public List<OfferModel> retrieveOffers() {
    List<OfferModel> offers = null;
    try {
      if ("".equals(retrieveOffersQuery) || retrieveOffersQuery == null) {
        retrieveOffersQuery = "SELECT " + DatabaseStructs.ADM_OFFERS.TABLE_NAME + "."
            + DatabaseStructs.ADM_OFFERS.OFFER_ID + ", " + DatabaseStructs.ADM_OFFERS.TABLE_NAME
            + "."
            + DatabaseStructs.ADM_OFFERS.OFFER_DESC + ", " + DatabaseStructs.ADM_OFFERS.TABLE_NAME
            + "."
            + DatabaseStructs.ADM_OFFERS.IS_DROP_DOWN_ENABLED + ", "
            + DatabaseStructs.LK_OFFERS_TYPES.TABLE_NAME + "."
            + DatabaseStructs.LK_OFFERS_TYPES.TYPE_DESC
            + ", " + DatabaseStructs.LK_OFFERS_TYPES.TABLE_NAME + "."
            + DatabaseStructs.LK_OFFERS_TYPES.TYPE_ID + " FROM "
            + DatabaseStructs.ADM_OFFERS.TABLE_NAME
            + " INNER JOIN " + DatabaseStructs.LK_OFFERS_TYPES.TABLE_NAME + " ON "
            + DatabaseStructs.ADM_OFFERS.TABLE_NAME + "." + DatabaseStructs.ADM_OFFERS.OFFER_TYPE_ID
            + " = "
            + DatabaseStructs.LK_OFFERS_TYPES.TABLE_NAME + "."
            + DatabaseStructs.LK_OFFERS_TYPES.TYPE_ID
            + " ORDER BY " + DatabaseStructs.ADM_OFFERS.TABLE_NAME + "."
            + DatabaseStructs.ADM_OFFERS.OFFER_ID;
      }
      offers = jdbcTemplate.query(retrieveOffersQuery,
          new BeanPropertyRowMapper<>(OfferModel.class));

    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while retrieving offers " + retrieveOffersQuery);
      CCATLogger.ERROR_LOGGER.error("Error while retrieving offers " + retrieveOffersQuery, ex);
    }
    return offers;
  }

  public List<OfferStateModel> retrieveOfferStates() {
    List<OfferStateModel> result = null;
    try {
      if ("".equals(retrieveOfferStatesQuery) || retrieveOfferStatesQuery == null) {
        retrieveOfferStatesQuery = "SELECT * FROM " + DatabaseStructs.LK_OFFERS_STATES.TABLE_NAME
            + " Order By "
            + DatabaseStructs.LK_OFFERS_STATES.STATE_DESC
            + " ASC ";
      }
      result = jdbcTemplate.query(retrieveOfferStatesQuery,
          new BeanPropertyRowMapper<>(OfferStateModel.class));
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error(
          "Error while retrieving offer states " + retrieveOfferStatesQuery);
      CCATLogger.ERROR_LOGGER.error(
          "Error while retrieving offer states " + retrieveOfferStatesQuery, ex);
    }
    return result;
  }

  public List<OfferTypeModel> retrieveOfferTypes() {
    List<OfferTypeModel> result = null;
    try {
      if ("".equals(retrieveOfferTypesQuery) || retrieveOfferTypesQuery == null) {
        retrieveOfferTypesQuery = "SELECT * FROM " + DatabaseStructs.LK_OFFERS_TYPES.TABLE_NAME
            + " Order By "
            + DatabaseStructs.LK_OFFERS_TYPES.TYPE_DESC
            + " ASC ";
      }
      result = jdbcTemplate.query(retrieveOfferTypesQuery,
          new BeanPropertyRowMapper<>(OfferTypeModel.class));
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error(
          "Error while retrieving offer types " + retrieveOfferTypesQuery);
      CCATLogger.ERROR_LOGGER.error("Error while retrieving offer types " + retrieveOfferTypesQuery,
          ex);
    }
    return result;
  }

  public List<FeatureModel> retrieveAllFeatures() {
    String query = "SELECT * FROM " + DatabaseStructs.LK_FEATURES.TABLE_NAME
        + " Order By "
        + DatabaseStructs.LK_FEATURES.NAME
        + " ASC ";
    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(FeatureModel.class));
  }

  public List<MonetaryLimitModel> retrieveAllMonetaryLimits() {
    String query = "SELECT * FROM " + DatabaseStructs.LK_MONETARY_LIMITS.TABLE_NAME
        + " Order By "
        + DatabaseStructs.LK_MONETARY_LIMITS.LIMIT_NAME
        + " ASC ";
    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(MonetaryLimitModel.class));
  }

  public List<MenuModel> retrieveAllMenus() {
    String query = "SELECT * FROM " + DatabaseStructs.LK_MENUS.TABLE_NAME
        + " Order By "
        + DatabaseStructs.LK_MENUS.MENU_ID
        + " ASC ";
    return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(MenuModel.class));
  }

  @LogExecutionTime
  public HashMap<Integer, List<AdmServiceClassResponse>> retrieveProfilesServiceClasses()
      throws LookupException {
    HashMap<Integer, List<AdmServiceClassResponse>> result = null;
    try {
      if (retrieveProfilesServiceClassesQuery == null) {
        retrieveProfilesServiceClassesQuery = "SELECT "
            + DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.PROFILE_ID
            + " , "
            + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
            + " , "
            + DatabaseStructs.ADM_SERVICE_CLASSES.NAME
            + " FROM "
            + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME
            + " INNER JOIN "
            + DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.TABLE_NAME
            + " ON "
            + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.CODE
            + " = "
            + DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.TABLE_NAME + "."
            + DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.SERVICE_CLASS_ID
            + " WHERE "
            + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_CLASSES.IS_DELETED + " = 0 "
            + " Order By "
            + DatabaseStructs.ADM_SERVICE_CLASSES.NAME
            + " ASC ";
      }
      CCATLogger.DEBUG_LOGGER.debug(
          "retrieveProfilesServiceClassesQuery : " + retrieveProfilesServiceClassesQuery);
      result = jdbcTemplate.query(retrieveProfilesServiceClassesQuery,
          profilesServiceClassesExtractor);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error(
          "error while executing: " + retrieveProfilesServiceClassesQuery);
      CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveProfilesServiceClassesQuery,
          ex);
    }
    return result;
  }

  public List<HlrProfileModel> retrieveHLRProfiles() throws LookupException {
    CCATLogger.DEBUG_LOGGER.error("Starting HlrProfileDao - retrieveHLRProfiles");
    List<HlrProfileModel> hlrProfiles = null;
    try {
      if (retrieveHLRProfiles == null) {
        retrieveHLRProfiles = "SELECT * FROM " + DatabaseStructs.ADM_HLR_PROFILES.TABLE_NAME
            + " Order By Lower(" + DatabaseStructs.ADM_HLR_PROFILES.CODE + ")";
      }
      hlrProfiles = jdbcTemplate.query(retrieveHLRProfiles,
          new BeanPropertyRowMapper<>(HlrProfileModel.class));
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while retrieving hlr profiles " + retrieveHLRProfiles);
      CCATLogger.ERROR_LOGGER.error("Error while retrieving hlr profiles " + retrieveHLRProfiles,
          ex);
    }
    return hlrProfiles;

  }

  public List<AdmAccountGroup> retrieveAccountGroups() {
    List<AdmAccountGroup> result = null;
    String sql = "";
    try {
      sql = "SELECT * " + " FROM " + DatabaseStructs.ADM_ACCOUNT_GROUPS.TABLE_NAME + " WHERE "
          + DatabaseStructs.ADM_ACCOUNT_GROUPS.IS_DELETED + " = 0"
          + " Order By "
          + DatabaseStructs.ADM_ACCOUNT_GROUPS.NAME
          + " ASC ";
      result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AdmAccountGroup.class));
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while retrieving hlr profiles " + sql);
      CCATLogger.ERROR_LOGGER.error("Error while retrieving hlr profiles " + sql, ex);
    }
    return result;
  }

//	@LogExecutionTime
//	public HashMap<Integer, List<AdmServiceClassResponse>> retrieveProfilesServiceClasses() throws LookupException {
//		HashMap<Integer, List<AdmServiceClassResponse>> result = null;
//		try {
//			if (retrieveProfilesServiceClassesQuery == null) {
//				retrieveProfilesServiceClassesQuery = "SELECT " + DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.PROFILE_ID
//						+ " , " + DatabaseStructs.ADM_SERVICE_CLASSES.ID + " , "
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.CODE + " , " + DatabaseStructs.ADM_SERVICE_CLASSES.NAME
//						+ " FROM " + DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + " INNER JOIN "
//						+ DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.TABLE_NAME + " ON "
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "." + DatabaseStructs.ADM_SERVICE_CLASSES.ID
//						+ " = " + DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_PROFILE_SERVICE_CLASS.SERVICE_CLASS_ID + " WHERE "
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME + "."
//						+ DatabaseStructs.ADM_SERVICE_CLASSES.IS_DELETED + " = 0 ";
//			}
//			CCATLogger.DEBUG_LOGGER
//					.debug("retrieveProfilesServiceClassesQuery : " + retrieveProfilesServiceClassesQuery);
//			result = jdbcTemplate.query(retrieveProfilesServiceClassesQuery, profilesServiceClassesExtractor);
//		} catch (Exception ex) {
//			CCATLogger.DEBUG_LOGGER.error("error while executing: " + retrieveProfilesServiceClassesQuery);
//			CCATLogger.ERROR_LOGGER.error("error while executing: " + retrieveProfilesServiceClassesQuery, ex);
//		}
//		return result;
//	}

  @LogExecutionTime
  public HashMap<Integer, ServiceOfferingPlan> retrieveServiceOfferingPlansWithBits()
      throws LookupException {
    HashMap<Integer, ServiceOfferingPlan> result = null;
    try {
      if (retrieveServiceOfferingPlansWithBitsQuery == null) {
        retrieveServiceOfferingPlansWithBitsQuery = "SELECT * FROM "
            + DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME + " INNER JOIN "
            + DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME + " ON "
            + DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID + " = "
            + DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.PLAN_ID + " WHERE "
            + DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME + "."
            + DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.IS_DELETED + " = 0 "
            + " Order By "
            + DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.NAME
            + " ASC ";
      }
      CCATLogger.DEBUG_LOGGER
          .debug("retrieveServiceOfferingPlansWithBitsQuery : "
              + retrieveServiceOfferingPlansWithBitsQuery);
      result = jdbcTemplate.query(retrieveServiceOfferingPlansWithBitsQuery,
          serviceOfferingPlansExtractor);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error(
          "error while executing: " + retrieveServiceOfferingPlansWithBitsQuery);
      CCATLogger.ERROR_LOGGER.error(
          "error while executing: " + retrieveServiceOfferingPlansWithBitsQuery, ex);
    }
    return result;
  }

  @LogExecutionTime
  public List<ServiceOfferingPlan> retrieveServiceOfferingPlans() {
    List<ServiceOfferingPlan> result = null;
    String sql = "";
    try {
      sql =
          "SELECT * " + " FROM " + DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.TABLE_NAME + " WHERE "
              + DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.IS_DELETED + " = 0"
              + " Order By "
              + DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.NAME
              + " ASC ";
      result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ServiceOfferingPlan.class));
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while executing: " + sql);
      CCATLogger.ERROR_LOGGER.error("error while executing: " + sql, ex);
    }
    return result;
  }

  public HashMap<String, String> retrieveMainProductTypes() {
    HashMap<String, String> result = null;
    String sql = "";
    try {
      sql = "SELECT * FROM " + DatabaseStructs.LK_MAIN_PRODUCT_TYPES.TABLE_NAME
          + " Order By "
          + DatabaseStructs.LK_MAIN_PRODUCT_TYPES.DISPLAY_NAME
          + " ASC ";
      result = jdbcTemplate.query(sql, (ResultSet rs) -> {
        HashMap<String, String> map = new HashMap<>();
        while (rs.next()) {
          map.put(rs.getString(DatabaseStructs.LK_MAIN_PRODUCT_TYPES.TYPE),
              rs.getString(DatabaseStructs.LK_MAIN_PRODUCT_TYPES.DISPLAY_NAME));
        }
        return map;
      });
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while executing: " + sql);
      CCATLogger.ERROR_LOGGER.error("error while executing: " + sql, ex);
    }
    return result;
  }

  public HashMap<String, String> retrieveAccountsFlags() {
    HashMap<String, String> result = null;
    String sql = "";
    try {
      sql = "SELECT * FROM " + DatabaseStructs.LK_ACCOUNT_FLAGS.TABLE_NAME
          + " Order By "
          + DatabaseStructs.LK_ACCOUNT_FLAGS.ACCOUNT_FLAG_NAME
          + " ASC ";
      result = jdbcTemplate.query(sql, (ResultSet rs) -> {
        HashMap<String, String> map = new HashMap<>();
        while (rs.next()) {
          map.put(rs.getString(DatabaseStructs.LK_ACCOUNT_FLAGS.ACCOUNT_FLAG_CODE),
              rs.getString(DatabaseStructs.LK_ACCOUNT_FLAGS.ACCOUNT_FLAG_NAME));
        }
        return map;
      });
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while executing: " + sql);
      CCATLogger.ERROR_LOGGER.error("error while executing: " + sql, ex);
    }
    return result;
  }

  public HashMap<String, String> retrieveBtStatus() {
    HashMap<String, String> result = null;
    String sql = "";
    try {
      sql = "SELECT * FROM " + DatabaseStructs.LK_BT_TRANSACTION_STATUS.TABLE_NAME
          + " Order By "
          + DatabaseStructs.LK_BT_TRANSACTION_STATUS.NAME
          + " ASC ";
      result = jdbcTemplate.query(sql, (ResultSet rs) -> {
        HashMap<String, String> map = new HashMap<>();
        while (rs.next()) {
          map.put(rs.getString(DatabaseStructs.LK_BT_TRANSACTION_STATUS.CODE),
              rs.getString(DatabaseStructs.LK_BT_TRANSACTION_STATUS.NAME));
        }
        return map;
      });
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("error while executing: " + sql);
      CCATLogger.ERROR_LOGGER.error("error while executing: " + sql, ex);
    }
    return result;
  }

  public HashMap<Integer, HashMap<Integer, DedicatedAccount>> retrieveServiceClassesDAsMap() {
    HashMap<Integer, HashMap<Integer, DedicatedAccount>> daMap = null;
    String retrieveDAsQuery = null;
    try {
      retrieveDAsQuery = "SELECT * FROM " + DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME
          + " Order By "
          + DatabaseStructs.ADM_SERVICE_CLASS_DA.DESCRIPTION
          + " ASC ";
      daMap = jdbcTemplate.query(retrieveDAsQuery, dedicatedAccountsExtractor);
    } catch (Exception ex) {
      CCATLogger.ERROR_LOGGER.debug("error while executing " + retrieveDAsQuery);
      CCATLogger.ERROR_LOGGER.error("error while executing " + retrieveDAsQuery, ex);
    }
    return daMap;
  }

  public HashMap<String, FootPrintPageModel> retrieveFootPrintPageMap() {
    HashMap<String, FootPrintPageModel> footprintPageMap = null;
    StringBuilder retrieveFootPrintPageQuery = new StringBuilder();
    try {
      retrieveFootPrintPageQuery.append(" SELECT ")
          .append(DatabaseStructs.LK_FOOTPRINT_PAGES.TABLE_NAME).append(".*")
          .append(",")
          .append(DatabaseStructs.LK_FOOTPRINT_PAGE_INFO.TABLE_NAME).append(".*")
          .append(" FROM ")
          .append(DatabaseStructs.LK_FOOTPRINT_PAGES.TABLE_NAME)
          .append(" LEFT JOIN ")
          .append(DatabaseStructs.LK_FOOTPRINT_PAGE_INFO.TABLE_NAME)
          .append(" ON ")
          .append(DatabaseStructs.LK_FOOTPRINT_PAGES.TABLE_NAME).append(".")
          .append(DatabaseStructs.LK_FOOTPRINT_PAGES.PAGE_ID)
          .append("=")
          .append(DatabaseStructs.LK_FOOTPRINT_PAGE_INFO.TABLE_NAME).append(".")
          .append(DatabaseStructs.LK_FOOTPRINT_PAGE_INFO.PAGE_ID);
      CCATLogger.DEBUG_LOGGER.debug("retrieveFootPrintPageQuery " + retrieveFootPrintPageQuery);
      footprintPageMap = jdbcTemplate.query(retrieveFootPrintPageQuery.toString(),
          footPrintPagesExtractor);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error(
          "Error while executing: " + retrieveFootPrintPageQuery + "\n" + ex);
      CCATLogger.ERROR_LOGGER.error("Error while executing: " + retrieveFootPrintPageQuery, ex);
    }
    return footprintPageMap;
  }

  public List<SmsActionModel> retrieveSmsActions() {
    List<SmsActionModel> smsActionList = null;
    String retrieveSmsActionsQuery = null;
    try {
      retrieveSmsActionsQuery = "SELECT * FROM " + DatabaseStructs.LK_SMS_ACTION.TABLE_NAME
          + " Order By "
          + DatabaseStructs.LK_SMS_ACTION.ACTION_NAME
          + " ASC ";
      smsActionList = jdbcTemplate.query(retrieveSmsActionsQuery,
          new BeanPropertyRowMapper<>(SmsActionModel.class));
    } catch (Exception ex) {
      CCATLogger.ERROR_LOGGER.debug("error while executing " + retrieveSmsActionsQuery);
      CCATLogger.ERROR_LOGGER.error("error while executing " + retrieveSmsActionsQuery, ex);
    }
    return smsActionList;
  }


  public List<SmsTemplateModel> retrieveSmsTemplates() {
    List<SmsTemplateModel> smsTemplateList = null;
    String retrieveSmsTemplatesQuery = null;
    try {
      retrieveSmsTemplatesQuery = "SELECT "
          + DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME + "." + DatabaseStructs.ADM_SMS_TEMPLATE.ID
          + " , "
          + DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME + "."
          + DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_TEXT + " , "
          + DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME + "."
          + DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_ID + " , "
          + DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME + "."
          + DatabaseStructs.ADM_SMS_TEMPLATE.LANGUAGE_ID + " , "
          + DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME + "."
          + DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_STATUS + " , "
          + DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME + "."
          + DatabaseStructs.ADM_SMS_TEMPLATE.CS_TEMPLATE_ID + " , "
          + DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME + "."
          + DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_PARAMETERS + " , "
          + DatabaseStructs.LK_SMS_ACTION.TABLE_NAME + "."
          + DatabaseStructs.LK_SMS_ACTION.ACTION_NAME + " "
          + " FROM " + DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME
          + " LEFT JOIN "
          + DatabaseStructs.LK_SMS_ACTION.TABLE_NAME
          + " ON "
          + DatabaseStructs.ADM_SMS_TEMPLATE.TABLE_NAME + "."
          + DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_ID
          + " = "
          + DatabaseStructs.LK_SMS_ACTION.TABLE_NAME + "."
          + DatabaseStructs.LK_SMS_ACTION.SMS_ACTION_ID
          + " Order By "
          + DatabaseStructs.ADM_SMS_TEMPLATE.TEMPLATE_TEXT
          + " ASC ";
      smsTemplateList = jdbcTemplate.query(retrieveSmsTemplatesQuery,
          new BeanPropertyRowMapper<>(SmsTemplateModel.class));
      CCATLogger.DEBUG_LOGGER.info("smsTemplates:  " + smsTemplateList.size());

    } catch (Exception ex) {
      CCATLogger.ERROR_LOGGER.debug("error while executing " + retrieveSmsTemplatesQuery);
      CCATLogger.ERROR_LOGGER.error("error while executing " + retrieveSmsTemplatesQuery, ex);
    }
    return smsTemplateList;
  }

  public HashMap<Integer, List<SmsTemplateParamModel>> retrieveSmsTemplateParam() {
    HashMap<Integer, List<SmsTemplateParamModel>> smsTemplateMap = null;
    String retrieveSmsTemplateParamQuery = null;
    try {
      retrieveSmsTemplateParamQuery =
          "SELECT * FROM " + DatabaseStructs.LK_SMS_TEMPLATE_PARAM.TABLE_NAME
              + " Order By "
              + DatabaseStructs.LK_SMS_TEMPLATE_PARAM.PARAMETER_NAME
              + " ASC ";
      smsTemplateMap = jdbcTemplate.query(retrieveSmsTemplateParamQuery, smsTemplateParamExtractor);
    } catch (Exception ex) {
      CCATLogger.ERROR_LOGGER.debug("error while executing " + retrieveSmsTemplateParamQuery);
      CCATLogger.ERROR_LOGGER.error("error while executing " + retrieveSmsTemplateParamQuery, ex);
    }
    return smsTemplateMap;
  }

  public HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> retrieveSuperFLexOffersMap() {
    String query = "";
    HashMap<Integer, HashMap<Integer, SuperFlexLookupModel>> map = null;
    try {
      query = "Select * " + " From " + DatabaseStructs.LK_FLEX_OFFERS.TABLE_NAME
          + " Order By "
          + DatabaseStructs.LK_FLEX_OFFERS.NAME
          + " ASC ";
      map = jdbcTemplate.query(query, superFlexOffersMapExtractor);
    } catch (Exception ex) {
      CCATLogger.ERROR_LOGGER.debug("error while executing [" + query + "]");
      CCATLogger.ERROR_LOGGER.error("error while executing [" + query + "]", ex);
    }
    return map;
  }
}
