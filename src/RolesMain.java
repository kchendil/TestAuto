

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Properties;

import javax.xml.rpc.ServiceException;

import com.novell.idm.nrf.soap.ws.resource.IRemoteResource;
import com.novell.idm.nrf.soap.ws.resource.ResourceService;
import com.novell.idm.nrf.soap.ws.resource.ResourceServiceImpl;
import com.novell.idm.nrf.soap.ws.role.RoleService;
import com.novell.idm.nrf.soap.ws.DNString;
import com.novell.idm.nrf.soap.ws.LocalizedValue;
import com.novell.idm.nrf.soap.ws.NrfServiceException;

import com.novell.idm.nrf.soap.ws.RoleInfo;
import com.novell.idm.nrf.soap.ws.RoleRequest;
import com.novell.idm.nrf.soap.ws.role.RoleServiceImpl;
import com.novell.idm.nrf.soap.ws.role.IRemoteRole;
import com.novell.soa.ws.portable.Stub;


import org.testng.annotations.*;


public class RolesMain {


	@Test
	public static void main(String[] args) throws Exception {
	
		Properties properties = new Properties();
		try {
		   // properties.load(new FileInputStream(args[0]));
		   properties.load(new FileInputStream("f:\\roles.properties"));
		} catch (IOException e) {
		}
		
		
		String name, description, username, password, url,user;
		long level;
	    url=properties.getProperty("url");
	    username=properties.getProperty("username");
	    name=properties.getProperty("rolename");
	    password=properties.getProperty("password");
	    description=properties.getProperty("description");
	    String username2 = properties.getProperty("user");
	    String group = properties.getProperty("group");
		user=username2;
	    level=Integer.parseInt(properties.getProperty("rolelevel"));
	   

		
		IRemoteRole stub=getRolesStub(url,username,password);	
//		IRemoteResource stub2=getResourcesStub(url,username,password);	
		try {		
			
			long random=(long) (Math.random()*1000000000);
			System.out.println(random);
		    //Role Creation using createRole method
			RoleRequest roleRequest = new RoleRequest();
			roleRequest.setName(name);		
			roleRequest.setDescription(description);
			roleRequest.setRoleLevel(level);
			String roleDN=null;
			roleDN=stub.createRole(roleRequest).getDn();
		
			String username2_quotes_removed = username2.substring(1,username2.lastIndexOf("\""));
			String groupname_quotes_removed = group.substring(1,group.lastIndexOf("\""));
			
			
			System.out.println(roleDN);
			System.out.println("==================================");
			System.out.println();
			
			//Delete a role created earlier using removeRoles method
			DNString[] dns = new DNString[1];		
			dns[0] = new DNString();
			dns[0].setDn(roleDN);
			if(stub.removeRoles(dns)[0].getDn().equals(roleDN)) {
				System.out.println("Role Deleted Successfully");
			}
			
			System.out.println("==================================");
			System.out.println();
			
			//Role Creation with CorelationID using createRoleAid method
			roleRequest = new RoleRequest();		
					
			roleRequest.setDescription(description);
			roleRequest.setRoleLevel(level);
			roleRequest.setName(name+"Cor2");	
			
			roleDN=stub.createRoleAid(roleRequest, String.valueOf(random)).getDn();
			
			
			System.out.println(roleDN);
			System.out.println("==================================");
			System.out.println();
			
			//Delete a role created earlier using removeRoles method
			dns = new DNString[1];		
			dns[0] = new DNString();
			dns[0].setDn(roleDN);
			if(stub.removeRolesAid(dns,String.valueOf(random))[0].getDn().equals(roleDN)) {
				System.out.println("Role Deleted Successfully");
			}
			
			System.out.println("==================================");
			System.out.println();
			Thread.sleep(1000);
			roleDN=stub.createRole(roleRequest).getDn();
			dns[0] = new DNString();
			dns[0].setDn(roleDN);
			roleRequest.setName(name);
			
						
			//Get Role details using getRole method
			System.out.println(stub.getRole(roleDN).getName());
			System.out.println(stub.getRole(roleDN).getDescription());
			System.out.println(stub.getRole(roleDN).getRoleLevel().getName());
			System.out.println(stub.getRole(roleDN).getEntityKey());
			
			System.out.println("==================================");
			System.out.println();
			
			//Get Role categories using getRoleCategories method
			for (int i=0;i<stub.getRoleCategories().length;i++){
				System.out.println(stub.getRoleCategories()[i].getCategoryLabel());
			}
			
			System.out.println("==================================");
			System.out.println();
			
			//Get Role levels using getRoleLevels method
			for (int i=0;i<stub.getRoleLevels().length;i++){
				System.out.println(stub.getRoleLevels()[i].getName());
			}
			
			System.out.println("==================================");
			System.out.println();
			
			//Get Role configuration using getConfiguration method
			System.out.println(stub.getConfiguration().getDefaultRequestDef());
			System.out.println(stub.getConfiguration().getDefaultSODRequestDef());
			System.out.println(stub.getConfiguration().getRemovalGracePeriod());
			System.out.println(stub.getConfiguration().getReportContainer());
			System.out.println(stub.getConfiguration().getRoleRequestContainer());
			System.out.println(stub.getConfiguration().getRolesContainer());
			System.out.println(stub.getConfiguration().getSODContainer());
			System.out.println(stub.getConfiguration().getSODQuorum());
			System.out.println(stub.getConfiguration().getSODRequestDef());
						
			for (int i=0;i<stub.getConfiguration().getRoleLevels().length;i++){
				System.out.println(stub.getConfiguration().getRoleLevels()[i].getName());
			}
			System.out.println(stub.getConfiguration().getSODApprovers());
					
			System.out.println("==================================");
			System.out.println();
			
		/*	//Get User details using getUser method
			System.out.println(stub.getUser(username2_quotes_removed).getFirstName());
			System.out.println(stub.getUser(username2_quotes_removed).getLastName());
			System.out.println(stub.getUser(username2_quotes_removed).getCn());
			System.out.println(stub.getUser(username2_quotes_removed).getEmail());
			System.out.println(stub.getUser(username2_quotes_removed).getEmail());		
*/
			
			System.out.println("==================================");
			System.out.println();
			
			//Get Version details using getVersion method
			System.out.println(stub.getVersion().getValue());
			
			System.out.println("==================================");
			System.out.println();
			
			//Find if the user has the role  details using isUserInRole method
			System.out.println(stub.isUserInRole(username2_quotes_removed, roleDN));
			
			System.out.println("==================================");
			System.out.println();
			
			//Get group details using getGroup method
		//	System.out.println(stub.getGroup(groupname_quotes_removed).getIdentityType().getValue());
			
			System.out.println("==================================");
			System.out.println();
		
			//Display all the roles in Permission Role
			long[] rolelevels = new long[1];	
			rolelevels[0]=10;
		
		
			RoleInfo[] roleinfo = stub.getRolesInfoByLevel(rolelevels);
			for (int i=0;i<roleinfo.length;i++){
				System.out.println(roleinfo[i].getName());
			}
			
			System.out.println("==================================");
			System.out.println();
			
			//Display all the roles in IT Role
			rolelevels = new long[1];			
			rolelevels[0]=20;
		
		
			 roleinfo = stub.getRolesInfoByLevel(rolelevels);
			for (int i=0;i<roleinfo.length;i++){
				System.out.println(roleinfo[i].getName());
			}
			
			System.out.println("==================================");
			System.out.println();
			
			//Display all the roles in Business Role
			rolelevels = new long[1];		
			rolelevels[0]=30;
		
			 roleinfo = stub.getRolesInfoByLevel(rolelevels);
			for (int i=0;i<roleinfo.length;i++){
				System.out.println(roleinfo[i].getName());
			}
			
			System.out.println("==================================");
			System.out.println();	

			
			//Get the Localized value of the role
			LocalizedValue[] LocolizedNames=stub.getRoleLocalizedStrings(dns[0], 1);
			
			for (int i=0;i<LocolizedNames.length;i++){
				System.out.println(LocolizedNames[i].getValue());
			}
			
			System.out.println("==================================");
			System.out.println();
			
			
			} catch (NrfServiceException e) {
			//	e.getReason();
				e.printStackTrace();
				
			}
		
		
		
	}	

/*    private static IRemoteResource getResourcesStub(String url,
			String username, String password) throws ServiceException {
        Stub stub = null;

        
        ResourceService service = new ResourceServiceImpl();
        stub = (Stub) service.getIRemoteResourcePort();
        stub._setProperty(Stub.USERNAME_PROPERTY, username);
        stub._setProperty(Stub.PASSWORD_PROPERTY, password);
        
        stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY,url +"/role/service");
        stub._setProperty(Stub.SESSION_MAINTAIN_PROPERTY, Boolean.TRUE);            
    
        return (IRemoteResource) stub;
}
	*/

	public static IRemoteRole getRolesStub(String url,String username,String password)
        throws Exception
    {
        Stub stub = null;

     
            RoleService service = new RoleServiceImpl();
            stub = (Stub) service.getIRemoteRolePort();
            stub._setProperty(Stub.USERNAME_PROPERTY, username);
            stub._setProperty(Stub.PASSWORD_PROPERTY, password);
            
            stub._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY,url +"/role/service");
            stub._setProperty(Stub.SESSION_MAINTAIN_PROPERTY, Boolean.TRUE);            
        
            return (IRemoteRole) stub;
    }

}
