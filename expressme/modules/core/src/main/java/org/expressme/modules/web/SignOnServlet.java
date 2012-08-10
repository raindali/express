package org.expressme.modules.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.expressme.modules.persist.DaoAccessor;
import org.expressme.modules.utils.HttpUtils;
import org.expressme.modules.web.security.IdentityManager;
import org.expressme.persist.TransactionManager;

import com.google.inject.Inject;

/**
 * Handle sign on.
 * 
 * @author Michael Liao (askxuefeng@gmail.com)
 */
public class SignOnServlet extends HttpServlet {

    private static final long ONE_HOUR = 3600000L;
    private static final long TWO_HOUR = ONE_HOUR * 2L;
    private static final String ATTR_MAC = "openid_mac";

    @Inject IdentityManager identityManager;
    @Inject TransactionManager txManager;
    @Inject DaoAccessor daoAccessor;
    //@Inject ViewEngine viewEngine;
    //@Inject OpenIdManagerLocator openIdManagerLocator;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String op = request.getParameter("op");
//        if ("signOut".equals(op)) {
//            identityManager.removeIdentity(request, response);
//            String url = HttpUtils.getRefererURL(request);
//            response.sendRedirect(url==null ? "/" : url);
//            return;
//        }
//        if ("select".equals(op)) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("redirect", URLEncoder.encode(getRedirect(request), "UTF-8"));
//            response.setContentType("text/html;charset=UTF-8");
//            response.setCharacterEncoding("UTF-8");
//            viewEngine.render("/signOn.html", map, response.getWriter());
//            return;
//        }
//        if (op==null) {
//            // check response:
//            final String nonce = request.getParameter("openid.response_nonce");
//            if (nonce==null || nonce.length()<20) {
//                response.sendRedirect("/deny.html");
//                return;
//            }
//            final long nonceTime = getNonceTime(nonce);
//            long diff = System.currentTimeMillis() - nonceTime;
//            if (diff<0)
//                diff = (-diff);
//            if (diff>ONE_HOUR)
//                throw new OpenIdException("Bad nonce time.");
//            OpenIdManager openIdManager = openIdManagerLocator.getOpenIdManager(request.getServerName().toLowerCase());
//            final Authentication authentication = openIdManager.getAuthentication(request, (byte[]) request.getSession().getAttribute(ATTR_MAC));
//            Object[] objs = new TransactionCallback<Object[]>(txManager) {
//                @Override
//                protected Object[] doInTransaction() throws Exception {
//                    if (facade.queryOpenIdNonceExist(nonce))
//                        throw new OpenIdException("Verify nonce failed.");
//                    String email = getEmail(authentication);
//                    facade.createOpenIdNonce(nonce, nonceTime + TWO_HOUR);
//                    User user = null;
//                    try {
//                        user = facade.queryUserByOpenId(authentication.getIdentity());
//                    }
//                    catch (ResourceNotFoundException e) {}
//                    if (user==null) {
//                        // user first sign on by OpenID:
//                        if (email==null)
//                            email = RandomUtils.nextId() + "@unknown.org";
//                        String openId = authentication.getIdentity();
//                        user = new User();
//                        user.setOpenId(openId);
//                        user.setThemeId(null);
//                        user.setUserEmail(email);
//                        user.setUserHost(openId);
//                        user.setUserName(getNameFromEmail(email));
//                        user.setUserPassword(User.EMPTY_PASSWORD);
//                        user.setUserSubTitle(email);
//                        user.setUserTitle(email);
//                        facade.createUser(user);
//                        return new Object[] { user, Boolean.TRUE };
//                    }
//                    // user has been registered:
//                    return new Object[] { user, Boolean.FALSE };
//                }
//
//                private String getEmail(Authentication auth) {
//                    String email = auth.getEmail();
//                    if (email!=null) {
//                        email = email.trim().toLowerCase();
//                    }
//                    return email;
//                }
//            }.execute();
//            User user = (User) objs[0];
//            Boolean isNewUser = (Boolean) objs[1];
//            identityManager.storeIdentity(request, response, new Identity(user.getId(), user.getUserName(), user.getUserEmail(), user.getUserHost()));
//            response.sendRedirect(isNewUser.booleanValue() ? "setting" : SessionUtils.getLastUrl(request));
//            return;
//        }
//        // store redirect url:
//        String redirect = getRedirect(request);
//        SessionUtils.setLastUrl(request.getSession(), redirect);
//        // generate open id authenticate url:
//        OpenIdManager openIdManager = openIdManagerLocator.getOpenIdManager(request.getServerName().toLowerCase());
//        Endpoint endpoint = openIdManager.lookupEndpoint(op);
//        Association association = openIdManager.lookupAssociation(endpoint);
//        request.getSession().setAttribute(ATTR_MAC, association.getRawMacKey());
//        String url = openIdManager.getAuthenticationUrl(endpoint, association);
//        response.sendRedirect(url);
    }

    private String getRedirect(HttpServletRequest request) {
        String redirect = request.getParameter("redirect");
        if (redirect==null)
            redirect = HttpUtils.getRefererURL(request);
        if (redirect==null)
            redirect = HttpUtils.getHostURL(request);
        return redirect;
    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        final String email = request.getParameter("email");
//        final String password = request.getParameter("password");
//        final boolean rememberMe = "true".equals(request.getParameter("rememberMe"));
//        User user = new TransactionCallback<User>(txManager) {
//            @Override
//            protected User doInTransaction() throws Exception {
//                return facade.queryUserWithPasswordByEmail(email);
//            }
//        }.execute();
//        if (user.getUserPassword().equals(password)) {
//            // set identity:
//            identityManager.storeIdentity(request, response, new Identity(user.getId(), user.getUserName(), user.getUserHost()), rememberMe);
//            // redirect:
//            response.sendRedirect(HttpUtils.getRedirectURL(request));
//            return;
//        }
//        throw new SignOnException();
//    }

    long getNonceTime(String nonce) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .parse(nonce.substring(0, 19) + "+0000")
                    .getTime();
        }
        catch(ParseException e) {
            throw new RuntimeException(e); // OpenIdException("Bad nonce.");
        }
    }

    String getNameFromEmail(String email) {
        int n = email.indexOf('@');
        if (n<=0)
            return "OpenIdUser";
        String name = email.substring(0, n);
        if (name.length()>10)
            name = name.substring(0, 10);
        return name;
    }
}
