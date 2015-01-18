package org.bimserver.shared.interfaces;

/******************************************************************************
 * Copyright (C) 2009-2014  BIMserver.org
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************************/

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.bimserver.interfaces.objects.SUser;
import org.bimserver.shared.exceptions.ServerException;
import org.bimserver.shared.exceptions.UserException;

@WebService(name = "AuthInterface", targetNamespace="org.bimserver")
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL, parameterStyle = ParameterStyle.WRAPPED)
public interface AuthInterface extends PublicInterface {
	/**
	 * @return The User that it currently loggedin on this ServiceInterface
	 * @throws ServerException, UserException
	 */
	@WebMethod(action = "getLoggedInUser")
	SUser getLoggedInUser() throws ServerException, UserException;

	/**
	 * Change a User's password, not the preferred way, use requestPasswordChange for a safer version
	 * @param uoid The ObjectID of the User
	 * @param oldPassword The old password
	 * @param newPassword The new password
	 * @return Whether the password was successfully changed
	 * @throws ServerException, UserException
	 */
	@WebMethod(action = "changePassword")
	Boolean changePassword(
		@WebParam(name = "uoid", partName = "changePassword.uoid") Long uoid,
		@WebParam(name = "oldPassword", partName = "changePassword.oldPassword") String oldPassword,
		@WebParam(name = "newPassword", partName = "changePassword.newPassword") String newPassword) throws ServerException, UserException;

	/**
	 * Change a User's password, not the preferred way, use requestPasswordChange for a safer version
	 * @param uoid The ObjectID of the User
	 * @param oldPassword The old password
	 * @param newPassword The new password
	 * @return Whether the password was successfully changed
	 * @throws ServerException, UserException
	 */
	@WebMethod(action = "setHash")
	void setHash(
			@WebParam(name = "uoid", partName = "setHash.uoid") Long uoid,
			@WebParam(name = "hash", partName = "setHash.hash") byte[] hash,
			@WebParam(name = "salt", partName = "setHash.salt") byte[] salt) throws ServerException, UserException;
	
	/**
	 * Request a password change, an e-mail will be send with a validation url
	 * @param username The username of the user to change tot password for
	 * @throws ServerException, UserException
	 */
	@WebMethod(action = "requestPasswordChange")
	void requestPasswordChange(
		@WebParam(name = "username", partName = "requestPasswordChange.username") String username,
		@WebParam(name = "resetUrl", partName = "requestPasswordChange.resetUrl") String resetUrl) throws ServerException, UserException;

//	@WebMethod(action = "createToken")
//	String createToken(
//		@WebParam(name = "validitySeconds", partName = "createToken.validitySeconds") Integer validitySeconds) throws UserException, ServerException;

	/**
	 * @param uoid The ObejctID of the User
	 * @param token The token generated by requestPasswordChange
	 * @param password The new password
	 * @return A User object if the change is successful, null otherwise
	 * @throws ServerException, UserException
	 */
	@WebMethod(action = "validateAccount")
	SUser validateAccount(
		@WebParam(name = "uoid", partName = "validateAccount.uoid") Long uoid,
		@WebParam(name = "token", partName = "validateAccount.token") String token,
		@WebParam(name = "password", partName = "validateAccount.password") String password) throws ServerException, UserException;
}
