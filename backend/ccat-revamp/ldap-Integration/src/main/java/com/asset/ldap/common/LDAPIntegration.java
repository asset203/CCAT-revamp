package com.asset.ldap.common;

import com.asset.ldap.model.LdapGroupModel;
import com.asset.ldap.model.LdapUserModel;
import com.asset.ldap.util.ModelComparator;
import com.asset.ldap.util.Util;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.*;

/**
 * The class used in order to connecting to LDAP server and retrieve the
 * required data such as user object attributes and its information and the
 * group names This class is used also to authenticate an user using its
 * username & its password
 *
 * @auther RaGa'e
 */
public class LDAPIntegration {

    public static final boolean ALLOW_PAGING_FLAG = true;
    public static final boolean NO_PAGING_FLAG = false;
    public static final int MIN_SEARCH_RESULT = 1;
    public static final int MAX_SEARCH_RESULT
            = Integer.parseInt(Util.getProperty("ldap.search.maxPageSize"));
    public static final String LDAP_SECURITY_AUTHENTICATION
            = Util.getProperty("ldap.security.authentication");
    public static final String LDAP_VERSION = Util.getProperty("ldap.version");
    public static final String LDAP_CONNECTION_POOLING
            = Util.getProperty("ldap.connection.pooling");
    public static final int DEFAULT_PAGE_SIZE
            = Integer.parseInt(Util.getProperty("ldap.default.pageSize"));

    public static String LDAP_URL;
    public static String LDAP_DOMAIN_NAME;
    private String binddnUser;
    private String binddnCredential;
    private LdapContext ctx;

    /**
     * The constructor with two arguments
     *
     * @param url of the LDAP server, e.g.: ldap://192.9.200.234:389
     * @param domainName of the top node LDAP DC or OU, e.g.
     * misslab.root.missroot.com
     * @auther RaGa'e
     */
    public LDAPIntegration(String url, String domainName) {
        LDAP_URL = url;
        LDAP_DOMAIN_NAME = domainName;
    }

    /**
     *
     * @param url of the LDAP server, e.g.: ldap://192.9.200.234:389
     * @param domainName of the LDAP server, e.g. misslab.root.missroot.com
     * @param binddnUser which is the user used to bind with LDAP server
     * @param binddnCredential which is the passowrd of the bind in user
     * @auther RaGa'e
     */
    public LDAPIntegration(String url, String domainName, String binddnUser,
            String binddnCredential) {
        LDAP_URL = url;
        LDAP_DOMAIN_NAME = domainName;
        setBinddnUser(binddnUser);
        setBinddnCredential(binddnCredential);
    }

    /**
     * The method used to authenticate an user using its NT account & password &
     * searchbase
     *
     * @param ntAccount which is the user name of the will autheticated user
     * @param credential which is the password of the will autheticated user
     * @param searchBase which is the top node LDAP DC or OU that include the
     * user object as a sublevel of it or as a subtree.
     * @return a model contain the authenticated user model
     * @throws NamingException
     * @auther RaGa'e
     */
    public LdapUserModel authenticateUser(String ntAccount, String credential,
            String searchBase) throws NamingException, Exception {
        setBinddnUser(ntAccount);
        setBinddnCredential(credential);
        LdapUserModel resultModel = new LdapUserModel();
        Hashtable env
                = this.getLDAPEnvironments(ntAccount + "@" + LDAP_DOMAIN_NAME, credential);
        String searchFilter
                = "(&(objectClass=" + Util.getProperty("ldap.objectclass.user")
                + ")(" + Util.getProperty("ldap.attribute.user.name")
                + "=" + ntAccount + "))";
        Enumeration resultedObj = searchFor(env, searchBase, searchFilter, MIN_SEARCH_RESULT, false);
        resultModel.setNTPassowrd(credential);
        if (resultedObj != null && resultedObj.hasMoreElements()) {
            resultModel.setLdapObjectModel((SearchResult) resultedObj.nextElement());
        }
        return resultModel;
    }

    /**
     * The method used to searching for a user using its information and its
     * searchbase
     *
     * @param mySearchingModel The model that contains the required user
     * information as its username and/or its atributes such as displayName,
     * employeeid, manager, ... etc
     * @param searchBase which is the top node LDAP DC or OU that include the
     * user object as a sublevel of it or as a subtree.
     * @return
     * @throws Exception
     * @auther RaGa'e
     */
    public LdapUserModel searchForUser(LdapUserModel mySearchingModel,
            String searchBase) throws Exception {
        LdapUserModel[] resultModels = null;
        resultModels = searchForUsers(mySearchingModel, searchBase, MIN_SEARCH_RESULT);
        if (resultModels.length > 0) {
            return resultModels[0];
        } else {
            return null;
        }
    }

    /**
     * The method used to searching for a users that match the passed
     * information. The method return ALL result data NOTE: The number of
     * returned users will not exceed the maxPageSize of the LDAP server, i.e.,
     * if the maxPageSize = 1000 [default], and the matched objects result
     * number = 1200, there is only 1000 result will be returned by the method
     *
     * @param mySearchingModel
     * @param searchBase which is the top node LDAP DC or OU that include the
     * user object as a sublevel of it or as a subtree.
     * @return
     * @throws Exception
     * @auther RaGa'e
     */
    public LdapUserModel[] searchForUsers(LdapUserModel mySearchingModel,
            String searchBase) throws Exception {
        return searchForUsers(mySearchingModel, searchBase, MAX_SEARCH_RESULT);
    }

    /**
     * The method used to searching for a users that match the passed
     * information. The method return the first result data matched this passed
     * information with maximum number of result equal to maxResult parameter.
     *
     * @param mySearchingModel
     * @param searchBase which is the top node LDAP DC or OU that include the
     * user object as a sublevel of it or as a subtree.
     * @param maxResult
     * @return
     * @throws Exception
     * @auther RaGa'e
     */
    public LdapUserModel[] searchForUsers(LdapUserModel mySearchingModel,
            String searchBase,
            int maxResult) throws Exception {
        Vector resultVec = new Vector();
        Hashtable env
                = getLDAPEnvironments(getBinddnUser() + "@" + LDAP_DOMAIN_NAME,
                        getBinddnCredential());
        Enumeration resultedObj
                = searchFor(env, searchBase, mySearchingModel.getSearchingFilterStr(), maxResult, false);
        while (resultedObj != null && resultedObj.hasMoreElements()) {
            LdapUserModel resultModel = new LdapUserModel();
            resultModel.setLdapObjectModel((SearchResult) resultedObj.nextElement());
            resultVec.add(resultModel);
        }
        Collections.sort(resultVec, new ModelComparator());
        return (LdapUserModel[]) resultVec.toArray(new LdapUserModel[resultVec.size()]);
    }

    public LdapGroupModel[] getLDAPGroupsList(String searchBase) throws NamingException, Exception {
        StringBuffer searchFilter = new StringBuffer("(objectClass=" + Util.getProperty("ldap.objectclass.group") + ")");
        Vector resultVec = new Vector();
        Hashtable env
                = getLDAPEnvironments(getBinddnUser() + "@" + LDAP_DOMAIN_NAME,
                        getBinddnCredential());
        Enumeration resultedObj = null;
        StringTokenizer subSearchBases = new StringTokenizer(searchBase, ";");
        String subSearchBase = null;
        while (subSearchBases.hasMoreTokens()) {
            int i = 0;
            boolean isPaging = false;
            subSearchBase = subSearchBases.nextToken();
            do {
                resultedObj = searchFor(env, subSearchBase, searchFilter.toString(), DEFAULT_PAGE_SIZE, isPaging);
                while (resultedObj != null && resultedObj.hasMoreElements()) {
                    LdapGroupModel resultModel = new LdapGroupModel();
                    resultModel.setLdapObjectModel((SearchResult) resultedObj.nextElement());
                    resultModel.setGroupName(resultModel.getAttribute(Util.getProperty("ldap.attribute.group.name")));
                    resultVec.add(resultModel);
                }
                isPaging = true;
            } while (resultedObj != null);
            setCtx(null);
        }
        Collections.sort(resultVec, new ModelComparator());
        return (LdapGroupModel[]) resultVec.toArray(new LdapGroupModel[resultVec.size()]);
    }

    public Collection getLDAPGroupsListNames(String searchBase) throws NamingException, Exception {
        StringBuffer searchFilter = new StringBuffer("(objectClass=" + Util.getProperty("ldap.objectclass.group") + ")");
        Collection resultVec = new ArrayList();
        Hashtable env
                = getLDAPEnvironments(getBinddnUser() + "@" + LDAP_DOMAIN_NAME,
                        getBinddnCredential());
        Enumeration resultedObj = null;
        StringTokenizer subSearchBases = new StringTokenizer(searchBase, ";");
        String subSearchBase = null;
        while (subSearchBases.hasMoreTokens()) {
            int i = 0;
            boolean isPaging = false;
            subSearchBase = subSearchBases.nextToken();
            do {
                resultedObj = searchFor(env, subSearchBase, searchFilter.toString(), DEFAULT_PAGE_SIZE, isPaging);
                while (resultedObj != null && resultedObj.hasMoreElements()) {
                    LdapGroupModel resultModel = new LdapGroupModel();
                    resultModel.setLdapObjectModel((SearchResult) resultedObj.nextElement());
                    resultModel.setGroupName(resultModel.getAttribute(Util.getProperty("ldap.attribute.group.name")).intern());
                    resultModel.setLdapObjectModel(null);
                    resultVec.add(resultModel);
                }
                isPaging = true;
            } while (resultedObj != null);
            setCtx(null);
        }
        Collections.sort((ArrayList) resultVec, new ModelComparator());
        return resultVec;
    }

    /**
     * The general method that searching the LDAP according to the passed
     * information which using in general to search for an object regardless of
     * its type.
     *
     * @param env The LDAP parameters Hashtable
     * @param searchBase which is the top node LDAP DC or OU that include the
     * needed object as a sublevel of it or as a subtree.
     * @param searchFilter which contains the searching statment
     * @return Enumeration of resulted objects
     * @throws NamingException
     * @auther RaGa'e
     */
    private Enumeration searchFor(Hashtable env, String searchBase,
            String searchFilter, int pageSize, boolean paging) throws NamingException, Exception {
        if (getBinddnUser() == null || getBinddnCredential() == null) {
            throw new Exception("Missing binddn user or its credential.");
        }
        if (pageSize == MAX_SEARCH_RESULT && paging) {
            // The user passed error paramters
            paging = false; // just correcting, NO THROWING OF ERRORS
            System.err.println("Passing Error Parameters, the pageSize = " + MAX_SEARCH_RESULT + " [max] while u need paging !!!!");
        }
        byte[] cookie = null;
        // Create the search controls
        SearchControls searchCtls = new SearchControls();
        //Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        //Request the paged results control
        Control[] ctls = null;
        if (!paging) {
            // For No Paging
            if (pageSize == MAX_SEARCH_RESULT) { /// Search for all results, NO PAGING
                ctls = new Control[]{(Control) new FastBindConnectionControl()};
                // Create new Ctx
                setCtx(new InitialLdapContext(env, ctls));
            } // Initiate cookie for this connection
            else try {
                // Searching using cookies [Paging for first page]
                ctls = new Control[]{new PagedResultsControl(pageSize, Control.CRITICAL)};
                // Create CTX
                setCtx(new InitialLdapContext(env, null));
                getCtx().setRequestControls(ctls);
            } catch (IOException ex) {
                // Cookies is DISABLED, Search for all results, NO PAGING
                ctls = new Control[]{(Control) new FastBindConnectionControl()};
                // Create new Ctx
                setCtx(new InitialLdapContext(env, ctls));
            }
        } else { // Do Paging
            // examine the response controls
            cookie = parseControls(getCtx().getResponseControls());
            // pass the cookie back to the server for the next page
            try {
                // Searching using cookies
                ctls = new Control[]{new PagedResultsControl(pageSize, cookie, Control.CRITICAL)};
                getCtx().setRequestControls(ctls);
            } catch (IOException ex) {
                // Cookies is DISABLED, Search for all results, NO PAGING
                ctls = new Control[]{(Control) new FastBindConnectionControl()};
                // Create new Ctx
                setCtx(new InitialLdapContext(env, ctls));
            }
        }
        // Search for objects using the filter
        Enumeration resultedObj = null;
        resultedObj = getCtx().search(searchBase, searchFilter, searchCtls);
        // Close
        if (pageSize == MAX_SEARCH_RESULT && !paging) {
            getCtx().close();
        }
        //System.out.println(cookie);
        if (paging && ((cookie == null) || (cookie.length == 0))) {
            return null;
        } else {
            return resultedObj;
        }
    }

    static byte[] parseControls(Control[] controls) throws NamingException {
        byte[] cookie = null;

        if (controls != null) {

            for (int i = 0; i < controls.length; i++) {
                if (controls[i] instanceof PagedResultsResponseControl) {
                    PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
                    cookie = prrc.getCookie();
                }
            }
        }

        return (cookie == null) ? new byte[0] : cookie;
    }

    public Vector searchForGroups(String mySearchString, String searchBase, int maxResult)
            throws NamingException, Exception {
        Vector resultVec = new Vector();
        Hashtable env = getLDAPEnvironments(getBinddnUser() + "@" + LDAP_DOMAIN_NAME, getBinddnCredential());

        Enumeration resultedObj = searchFor(env, searchBase, mySearchString, maxResult, false);

        while ((resultedObj != null) && (resultedObj.hasMoreElements())) {
            LdapGroupModel resultModel = new LdapGroupModel();
            resultModel.setLdapObjectModel((SearchResult) resultedObj.nextElement());
            resultVec.add(resultModel);
        }

        LdapGroupModel[] m = new LdapGroupModel[resultVec.size()];

        return resultVec;
    }

    /**
     * The method used to fill the LDAP parameters in a Hashtable
     *
     * @param princibal The username of the bind dn user
     * @param credential The password of the bind dn user
     * @return
     * @auther RaGa'e
     */
    private Hashtable getLDAPEnvironments(String princibal,
            String credential) {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        //set security credentials, note using simple cleartext authentication
        env.put(Context.SECURITY_AUTHENTICATION, LDAP_SECURITY_AUTHENTICATION);
        env.put(Context.SECURITY_PRINCIPAL, princibal);
        env.put(Context.SECURITY_CREDENTIALS, credential);
        //connect to my domain controller
        env.put(Context.PROVIDER_URL, LDAP_URL);
        env.put("java.naming.ldap.version", LDAP_VERSION);
        env.put("com.sun.jndi.ldap.connect.pool", LDAP_CONNECTION_POOLING);
        return env;
    }

    /**
     * The encapsulate method for binddnCredential
     *
     * @param bindnCredential
     * @auther RaGa'e
     */
    public void setBinddnCredential(String bindnCredential) {
        this.binddnCredential = bindnCredential;
    }

    /**
     * The encapsulate method for binddnUser
     *
     * @return binddnUser
     * @auther RaGa'e
     */
    public String getBinddnUser() {
        return binddnUser;
    }

    /**
     * The encapsulate method for binddnUser
     *
     * @param binddnUser
     * @auther RaGa'e
     */
    public void setBinddnUser(String binddnUser) {
        this.binddnUser = binddnUser;
    }

    /**
     * The encapsulate method for
     *
     * @return bindnCredential
     * @auther RaGa'e
     */
    public String getBinddnCredential() {
        return binddnCredential;
    }

    private LdapContext getCtx() {
        return ctx;
    }

    private void setCtx(LdapContext ctx) {
        this.ctx = ctx;
    }
}
