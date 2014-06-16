

import java.io.FileInputStream;
import java.io.IOException;

import java.rmi.RemoteException;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import com.novell.idm.nrf.soap.ws.resource.IRemoteResource;
import com.novell.idm.nrf.soap.ws.resource.ResourceService;
import com.novell.idm.nrf.soap.ws.resource.ResourceServiceImpl;
import com.novell.idm.nrf.soap.ws.role.RoleService;
import com.novell.idm.nrf.soap.ws.Category;
import com.novell.idm.nrf.soap.ws.CategoryKey;
import com.novell.idm.nrf.soap.ws.DNString;
import com.novell.idm.nrf.soap.ws.IdentityType;
import com.novell.idm.nrf.soap.ws.LocalizedValue;
import com.novell.idm.nrf.soap.ws.NrfServiceException;
import com.novell.idm.nrf.soap.ws.Role;

import com.novell.idm.nrf.soap.ws.RoleInfo;
import com.novell.idm.nrf.soap.ws.RoleRequest;
import com.novell.idm.nrf.soap.ws.role.RoleServiceImpl;
import com.novell.idm.nrf.soap.ws.role.IRemoteRole;
import com.novell.soa.ws.portable.Stub;


import org.testng.annotations.*;


public class Roles_SOAPEndpoints {

	String name, description, adminName, adminPassword, url,user,ldapAdminName,role,container;
	long level;
	String endUserName;
	String group; 
	IRemoteRole stub=null;
	
	@BeforeClass
	public void setup() throws Exception {
	
		Properties properties = new Properties();
		try {

			System.out.println("==================================");
		   // properties.load(new FileInputStream(args[0]));
		   properties.load(new FileInputStream("f:\\roles.properties"));
		   
			url=properties.getProperty("url");
		    adminName=properties.getProperty("username");
		    ldapAdminName = properties.getProperty("ldapadminname");	
		    adminPassword=properties.getProperty("password");
		    
		    name=properties.getProperty("rolename");		    
		    description=properties.getProperty("description");	     
		    endUserName = properties.getProperty("user");		     
		    group = properties.getProperty("group");
		    role = properties.getProperty("role");
		    container=properties.getProperty("container");
		    level=Integer.parseInt(properties.getProperty("rolelevel"));
			
		    stub=getRolesStub(url,adminName,adminPassword);
		
		} catch (IOException e) {
		}
	}
		
		
		
@Test	   
public void createRole() throws RemoteException, NrfServiceException
{
	long random=(long) (Math.random()*1000000000);
	System.out.println(random);
    //Role Creation using createRole method
	RoleRequest roleRequest = new RoleRequest();
	roleRequest.setName(name);		
	roleRequest.setDescription(description);
	roleRequest.setRoleLevel(level);
	String roleDN=null;
	roleDN=stub.createRole(roleRequest).getDn();
	
	System.out.println(roleDN);
	System.out.println("==================================");
	System.out.println();
	cleanUpRemoveRole(roleDN);
	
}

@Test	   
public void removeRole() throws RemoteException, NrfServiceException
{
	long random=(long) (Math.random()*1000000000);
	System.out.println(random);
    //Role Creation using createRole method
	RoleRequest roleRequest = new RoleRequest();
	roleRequest.setName(name);		
	roleRequest.setDescription(description);
	roleRequest.setRoleLevel(level);
	String roleDN=null;
	roleDN=stub.createRole(roleRequest).getDn();
	
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
	
}


@Test	   
public void createRoleAid() throws RemoteException, NrfServiceException
{
	long random=(long) (Math.random()*1000000000);
	System.out.println(random);
    //Role Creation using createRole method
	RoleRequest roleRequest = new RoleRequest();
	roleRequest.setName(name);		
	roleRequest.setDescription(description);
	roleRequest.setRoleLevel(level);
	String roleDN=null;
	
	//Role Creation with CorelationID using createRoleAid method
	roleRequest = new RoleRequest();		
			
	roleRequest.setDescription(description);
	roleRequest.setRoleLevel(level);
	roleRequest.setName(name+"Cor2");	
	
	roleDN=stub.createRoleAid(roleRequest, String.valueOf(random)).getDn();
	
	
	System.out.println(roleDN);
	System.out.println("==================================");
	System.out.println();
	cleanUpRemoveRole(roleDN);
	
}
		
public void cleanUpRemoveRole(String roleDN) throws RemoteException, NrfServiceException

{
	//Delete a role created earlier using removeRoles method
		DNString[] dns = new DNString[1];		
		dns[0] = new DNString();
		dns[0].setDn(roleDN);
		if(stub.removeRoles(dns)[0].getDn().equals(roleDN)) {
			System.out.println("Role Deleted Successfully");
		}
	
}

@Test	   
public void removeRoleAid() throws RemoteException, NrfServiceException
{
	long random=(long) (Math.random()*1000000000);
	System.out.println(random);
    //Role Creation using createRole method
	RoleRequest roleRequest = new RoleRequest();
	roleRequest.setName(name);		
	roleRequest.setDescription(description);
	roleRequest.setRoleLevel(level);
	String roleDN=null;
	
	//Role Creation with CorelationID using createRoleAid method
	roleRequest = new RoleRequest();		
			
	roleRequest.setDescription(description);
	roleRequest.setRoleLevel(level);
	roleRequest.setName(name+"Cor2");	
	
	roleDN=stub.createRoleAid(roleRequest, String.valueOf(random)).getDn();
	
	
	System.out.println(roleDN);
	System.out.println("==================================");
	System.out.println();
	
	DNString[] dns = new DNString[1];		
	//Delete a role created earlier using removeRoles method
	dns = new DNString[1];		
	dns[0] = new DNString();
	dns[0].setDn(roleDN);
	if(stub.removeRolesAid(dns,String.valueOf(random))[0].getDn().equals(roleDN)) {
		System.out.println("Role Deleted Successfully");
	}
	
	System.out.println("==================================");
	System.out.println();
	
}
	
@Test
public void getRole() throws RemoteException, NrfServiceException

{
	
	//Get Role details using getRole method
	System.out.println(stub.getRole("cn=provManager,cn=System,cn=Level20,cn=RoleDefs,cn=RoleConfig,cn=AppConfig,cn=User Application Driver,cn=driverset1,o=system").getName());
	System.out.println(stub.getRole("cn=provManager,cn=System,cn=Level20,cn=RoleDefs,cn=RoleConfig,cn=AppConfig,cn=User Application Driver,cn=driverset1,o=system").getDescription());
	System.out.println(stub.getRole("cn=provManager,cn=System,cn=Level20,cn=RoleDefs,cn=RoleConfig,cn=AppConfig,cn=User Application Driver,cn=driverset1,o=system").getRoleLevel().getName());
	System.out.println(stub.getRole("cn=provManager,cn=System,cn=Level20,cn=RoleDefs,cn=RoleConfig,cn=AppConfig,cn=User Application Driver,cn=driverset1,o=system").getEntityKey());
	System.out.println(stub.getRole("cn=provManager,cn=System,cn=Level20,cn=RoleDefs,cn=RoleConfig,cn=AppConfig,cn=User Application Driver,cn=driverset1,o=system").getQuorum());
	System.out.println(stub.getRole("cn=provManager,cn=System,cn=Level20,cn=RoleDefs,cn=RoleConfig,cn=AppConfig,cn=User Application Driver,cn=driverset1,o=system").getRoleCategoryKeys()[0].getCategoryKey());
	System.out.println(stub.getRole("cn=provManager,cn=System,cn=Level20,cn=RoleDefs,cn=RoleConfig,cn=AppConfig,cn=User Application Driver,cn=driverset1,o=system").getSystemRole());
	System.out.println(stub.getRole("cn=provManager,cn=System,cn=Level20,cn=RoleDefs,cn=RoleConfig,cn=AppConfig,cn=User Application Driver,cn=driverset1,o=system").getRequestDef());
	System.out.println(stub.getRole("cn=provManager,cn=System,cn=Level20,cn=RoleDefs,cn=RoleConfig,cn=AppConfig,cn=User Application Driver,cn=driverset1,o=system").getRevokeRequestDefinition());
	
	
	System.out.println("==================================");
	System.out.println();
	
}

@Test

public void getRoleInfo() throws RemoteException, NrfServiceException
{
	DNString[] dns = new DNString[1];
	dns = new DNString[1];		
	dns[0] = new DNString();
	dns[0].setDn(role);
	System.out.println("Get Role Info");
	System.out.println(stub.getRolesInfo(dns)[0].getName());
	System.out.println(stub.getRolesInfo(dns)[0].getDescription());
	System.out.println(stub.getRolesInfo(dns)[0].getEntityKey());
	System.out.println(stub.getRolesInfo(dns)[0].getRoleCategoryKeys()[0].getCategoryKey());
	System.out.println(stub.getRolesInfo(dns)[0].getRoleLevel());	
}

@Test
public void getRoleCategories() throws RemoteException, NrfServiceException
{
	
	//Get Role categories using getRoleCategories method
	for (int i=0;i<stub.getRoleCategories().length;i++){
		System.out.println(stub.getRoleCategories()[i].getCategoryLabel());
	}
	
	System.out.println("==================================");
	System.out.println();
}

@Test
public void getRolesInfoByCategory() throws RemoteException, NrfServiceException
{

	System.out.println("======getRolesInfoByCategory=====");
	CategoryKey[] roleCategory=new CategoryKey[1];
	roleCategory[0]=new CategoryKey();
	roleCategory[0].setCategoryKey("system");
	RoleInfo[] rolesInfoByCategory = stub.getRolesInfoByCategory(roleCategory);
	for(int i=0;i<rolesInfoByCategory.length;++i){
		System.out.println(rolesInfoByCategory[i].getEntityKey());
		System.out.println(rolesInfoByCategory[i].getName());
		System.out.println(rolesInfoByCategory[i].getDescription());
		System.out.println(rolesInfoByCategory[i].getRoleLevel());
		for(int j=0; j<rolesInfoByCategory[i].getRoleCategoryKeys().length;++j)
			System.out.println(rolesInfoByCategory[i].getRoleCategoryKeys()[j].getCategoryKey());
	}
}

@Test
public void getConfigProperty() throws RemoteException, NrfServiceException
{
	System.out.println("Get Config Property");
	System.out.println("==================================");
	System.out.println(stub.getConfigProperty("DirectoryService/realms/jndi/params/USER_ROOT_CONTAINER").getConfigPropertyKey());
	System.out.println(stub.getConfigProperty("DirectoryService/realms/jndi/params/USER_ROOT_CONTAINER").getConfigPropertyValue());
	
	System.out.println(stub.getConfigProperty("DirectoryService/realms/jndi/params/GROUP_ROOT_CONTAINER").getConfigPropertyKey());
	System.out.println(stub.getConfigProperty("DirectoryService/realms/jndi/params/GROUP_ROOT_CONTAINER").getConfigPropertyValue());
	
	System.out.println(stub.getConfigProperty("DirectoryService/realms/jndi/params/ROOT_NAME").getConfigPropertyKey());
	System.out.println(stub.getConfigProperty("DirectoryService/realms/jndi/params/ROOT_NAME").getConfigPropertyValue());
	
	System.out.println(stub.getConfigProperty("DirectoryService/realms/jndi/params/PROVISIONING_ROOT").getConfigPropertyKey());
	System.out.println(stub.getConfigProperty("DirectoryService/realms/jndi/params/PROVISIONING_ROOT").getConfigPropertyValue());

}

@Test
public void getRoleLevels() throws RemoteException, NrfServiceException
{
	
	//Get Role levels using getRoleLevels method
	for (int i=0;i<stub.getRoleLevels().length;i++){
		System.out.println(stub.getRoleLevels()[i].getName());
	}
	
	System.out.println("==================================");
	System.out.println();
}

@Test
public void getConfiguration() throws RemoteException, NrfServiceException
{
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
}


@Test
public void getVersion() throws RemoteException
{
	System.out.println(stub.getVersion().getValue());
}

@Test
public void isUserInRole() throws RemoteException, NrfServiceException
{

	System.out.println("isUserRole");
	System.out.println(stub.isUserInRole(ldapAdminName, "cn=provAdmin,cn=System,cn=Level20,cn=RoleDefs,cn=RoleConfig,cn=AppConfig,cn=User Application Driver,cn=driverset1,o=system"));
}

@Test
public void getUser() throws RemoteException, NrfServiceException
{

//	String username2_quotes_removed = endUserName;
	//String username2_quotes_removed = endUserName.substring(1,endUserName.lastIndexOf("\""));
	System.out.println(stub.getUser(endUserName).getFirstName());
	System.out.println(stub.getUser(endUserName).getLastName());
	System.out.println(stub.getUser(endUserName).getCn());
	System.out.println(stub.getUser(endUserName).getEmail());
	System.out.println(stub.getUser(endUserName).getEmail());		
}


@Test
public void getGroup() throws RemoteException, NrfServiceException
{
	System.out.println(stub.getGroup(group).getIdentityType().getValue());	
}


/*@Test
public void getContainer() throws RemoteException, NrfServiceException
{
	
	System.out.println("GetContainer===========");
	System.out.println("Null");
	//System.out.println(stub.getContainer("o=data").getIdentityType());	
	//System.out.println(stub.getContainer("o=data").getEntityKey());
	
}
*/



@Test
public void getRolesInfoByLevel() throws RemoteException, NrfServiceException
{	
	//Display all the roles in Permission Role
	long[] rolelevels = new long[1];	
	rolelevels[0]=20;

RoleInfo[] roleinfo = stub.getRolesInfoByLevel(rolelevels);
for (int i=0;i<roleinfo.length;i++){
	System.out.println(roleinfo[i].getName());
}
}

/*@Test
public void getLocalizedNames() throws RemoteException, NrfServiceException
{
	System.out.println("Get Localized Names==========");
	DNString[] dns = new DNString[1];
	dns = new DNString[1];		
	dns[0] = new DNString();
	dns[0].setDn(role);
	LocalizedValue[] LocolizedNames=stub.getRoleLocalizedStrings(dns[0], 1);

	for (int i=0;i<LocolizedNames.length;i++){
		System.out.println(LocolizedNames[i].getValue());
	}
}*/
	

@Test
public void getAssignedIdentities() throws RemoteException, NrfServiceException
{
	System.out.println("Get Assigned Identities==========");	
	IdentityType idT=new IdentityType();
	boolean directAssignOnly=true;
	System.out.println(stub.getAssignedIdentities(role,idT.USER, directAssignOnly)[0].getRole());
	
}

@Test
public void modifyRole() throws RemoteException, NrfServiceException
{
	
	long random=(long) (Math.random()*1000000000);
	System.out.println(random);
    //Role Creation using createRole method
	RoleRequest roleRequest = new RoleRequest();
	roleRequest.setName(name+"Modify"+random);		
	roleRequest.setDescription(description);
	roleRequest.setRoleLevel(level);
	String roleDN=null;
	roleDN=stub.createRole(roleRequest).getDn();
	
	System.out.println(roleDN);
	System.out.println("==================================");
	System.out.println();
	
	Role modifyRole = stub.getRole(roleDN);
	modifyRole.setName(modifyRole+"New");
	System.out.println(stub.modifyRole(modifyRole));
	
	cleanUpRemoveRole(roleDN);
}


@Test
public void modifyRoleAid() throws RemoteException, NrfServiceException
{
	
	long random=(long) (Math.random()*1000000000);
	System.out.println(random);
    //Role Creation using createRole method
	RoleRequest roleRequest = new RoleRequest();
	roleRequest.setName(name+"Modify"+random);		
	roleRequest.setDescription(description);
	roleRequest.setRoleLevel(level);
	String roleDN=null;
	roleDN=stub.createRoleAid(roleRequest, String.valueOf(random)).getDn();
	
	System.out.println(roleDN);
	System.out.println("==================================");
	System.out.println();
	
	Role modifyRole = stub.getRole(roleDN);
	modifyRole.setName(modifyRole+"New");
	System.out.println(stub.modifyRoleAid(modifyRole,String.valueOf(random)));
	
	cleanUpRemoveRole(roleDN);
}

@Test
public void getRoleLocalizedStrings() throws RemoteException, NrfServiceException

{
	DNString localizeRole;
	localizeRole = new DNString();
	localizeRole.setDn(role);	
	
	int type=1;
	LocalizedValue[] roleLocalizedStrings = stub.getRoleLocalizedStrings(localizeRole, type);
	System.out.println("=============getRoleLocalizedStrings: Name=====================");
	for(int i=0;i<roleLocalizedStrings.length;++i)
		{
			System.out.println(roleLocalizedStrings[i].getLocale());
			System.out.println(roleLocalizedStrings[i].getValue());
		}
	
	type=2;
	roleLocalizedStrings = stub.getRoleLocalizedStrings(localizeRole, type);
	System.out.println("=============getRoleLocalizedStrings: Description=====================");
	for(int i=0;i<roleLocalizedStrings.length;++i)
		{
			System.out.println(roleLocalizedStrings[i].getLocale());
			System.out.println(roleLocalizedStrings[i].getValue());
		}
}
	


@Test
public void getContainer() throws RemoteException, NrfServiceException
{
	System.out.println("=============getContainer: Name=====================");
	System.out.println(stub.getContainer("ou=users,o=data").getEntityKey());
	System.out.println(stub.getContainer("ou=users,o=data").getIdentityType());
}

//@Test
public void setRoleLocalizedStrings() throws RemoteException, NrfServiceException

{
	long random=(long) (Math.random()*1000000000);
	System.out.println(random);	
	DNString localizeRole;
	localizeRole = new DNString();
	localizeRole.setDn(role);	
	
	int type=1;
	LocalizedValue[] roleLocalizedStrings = new LocalizedValue[2] ; 
	roleLocalizedStrings[0]=new LocalizedValue();
	roleLocalizedStrings[0].setLocale("en");
	roleLocalizedStrings[0].setValue("test"+random);
	LocalizedValue[] setRoleLocalizedStrings= stub.setRoleLocalizedStrings(localizeRole, roleLocalizedStrings,type);
	System.out.println("=============setRoleLocalizedStrings: Name=====================");
	for(int i=0;i<setRoleLocalizedStrings.length;++i)
	{
		System.out.println(setRoleLocalizedStrings[i].getLocale());
		System.out.println(setRoleLocalizedStrings[i].getValue());
	}
	
	
	
}
	


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