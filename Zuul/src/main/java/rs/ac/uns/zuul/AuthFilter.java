package rs.ac.uns.zuul;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import feign.FeignException;
import rs.ac.uns.zuul.client.AuthClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthFilter extends ZuulFilter {

	@Autowired
	AuthClient authClient;
	
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true; // stavi na true kad budes radio kasnije
    }

    private void setFailedRequest(String body, int code) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        if (request.getHeader("Auth") == null) {
            return null;
        };

        String token = request.getHeader("Auth").substring(7);
        try {
        	
        	String username = authClient.getUsername(token);
        	String permissions = authClient.getPermissions(token);
        	
            ctx.addZuulRequestHeader("username", username);
            ctx.addZuulRequestHeader("permissions", permissions);
            
            System.out.println("GATEWAY->PERMISIJE: " + permissions);
            System.out.println("GATEWAY->USERNAME: " + username);
        } catch (FeignException.NotFound e) {
            setFailedRequest("Consumer does not exist!", 403);
        }

        return null;
    }


}
