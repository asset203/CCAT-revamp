//package test;
//
//import com.asset.ldap.common.LDAPIntegration;
//
//import com.asset.ldap.model.LdapGroupModel;
//import com.asset.ldap.model.LdapUserModel;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.*;
//import javax.servlet.http.*;
//
//public class TestServlet extends HttpServlet {
//    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
//
//    private static LDAPIntegration LDAP_INTEGRATION;
//    
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//    }
//
//    public void doGet(HttpServletRequest request, 
//                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
//        PrintWriter out = response.getWriter();
//        try  {
//            LDAP_INTEGRATION = new LDAPIntegration("ldap://192.9.200.234:389","miislab.root.miisroot.com");
//            LdapUserModel myUser = LDAP_INTEGRATION.authenticateUser("user","123","DC=miislab,DC=root,DC=miisroot,DC=com");
//            LDAP_INTEGRATION.setBinddnUser("user");
//            LDAP_INTEGRATION.setBinddnCredential("123");
//            LdapUserModel searchUser = new LdapUserModel();
//            searchUser.setAttribute("extensionAttribute7","manager");
//            LdapUserModel[] searchResult = LDAP_INTEGRATION.searchForUsers(searchUser,"DC=miislab,DC=root,DC=miisroot,DC=com",0);
//            System.out.println("Result\tNtaccount\t\tStaffId\t\tName");
//            for(int i=0; searchResult != null  && i< searchResult.length;i++){
//                myUser = searchResult[i];
//                System.out.println(i+"\t\t"+myUser.getNTAccount()+"\t\t"+myUser.getAttribute("employeeid")+"\t\t"+myUser.getAttribute("displayName"));
//            }
////            LdapGroupModel[] groups = LDAP_INTEGRATION.getLDAPGroupsList("OU=Groups,DC=miislab,DC=root,DC=miisroot,DC=com;OU=DL,DC=miislab,DC=root,DC=miisroot,DC=com");
////            for(int i=0; groups != null  && i< groups.length;i++){
////                LdapGroupModel myGroup = groups[i];
////                System.out.println(myGroup.getGroupName());
////            }
//            System.out.println( LDAP_INTEGRATION.getLDAPGroupsListNames("OU=Groups,DC=miislab,DC=root,DC=miisroot,DC=com;OU=DL,DC=miislab,DC=root,DC=miisroot,DC=com").size());
//        } catch (Exception ex)  {
//            ex.printStackTrace();
//        }
//    }
//}
