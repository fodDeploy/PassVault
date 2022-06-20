# PassVault



This is an open-soruce REST-API to safely and reliably save users' passwords used for different digital accounts.

Terminology:
```
Main Account: this is the account which the user creates in order to use the app.

Sub Account: the  digital accounts, to which the saved information (email, password, platform) belongs.
```

<sub>
Example of a sub account: 
 </sub><br>
 
 <sub>
 - Email: example_email@gmail.com
 </sub><br>
 
 <sub>
 - Password: funPas2w8rd@
 </sub><br>
 
 <sub>
 - At: Github
 </sub><br>
 

## End-points:
### For the Main Account

```bash
 /main_account/xxxx
```
Whereby xxxx here refers to the part which belongs to the specific resource (i.e. /sign_up).

All requests are Post requests or derivatives of it. With every request a Json Object is expected containing at least the sign-in/up information, other information may be needed depending on the action.

JSON Format:
```bash
{
"email":xxxxx,
"password":xxxxx
}
```

#### Signing up

Request:
```bash
 /sign_up
```
fully:

```bash
 /main_account/sign_up
```
with: JSON Object

Return:
```bash
 200: Succcessful
 500: The given Email already exists
```
#### Deleting the Account

Request:
```bash
 /delete
```
with: JSON Object

Return:
```bash
 200: Succcessful
 500: Log-in password is incorrect
 404: Log-in Email does not exist
```

#### Updating the Account's Email

Request:

```bash
 /update_email
```
with: JSON Object

Add to the JSON Object
```bash
"email_new":xxxxx
```
Return:
```bash
 200: Succcessful
 500: Log-in password is incorrect  or the new given Email already exists for another account
 404: Log-in Email does not exist
```

#### Updating the Account's Password
Request:

```bash
 /update_password
```
with: JSON Object

Add to the JSON Object
```bash
"password_new":xxxxx
```

Return:
```bash
 200: Succcessful
 500: Log-in password is incorrect
 404: Log-in Email does not exist
```

#### To verify the input Sign-in data
Request:

```bash
 /sign_in
```
with: JSON Object

Return:
```bash
 200: Succcessful
 500: Log-in password is incorrect
 404: Log-in Email does not exist
```


### For the Sub Accounts

```bash
 /sub_account/xxxx
```

Whereby xxxx here refers to the part which belongs to the specific resource (i.e. /add).


All requests are Post requests or derivatives of it. With every request a Json Object is expected containing at least the sign-in/up information, other information may be needed depending on the action.
JSON Format for log-in data:
```bash
{
"main_email":xxxxx,
"add_password":xxxxx
}
```

#### Creating/Saving a new Sub Account

Request:
```bash
 /add
```
fully:

```bash
 sub_account/add
```
with: JSON Object

Add to the JSON Object
```bash
"sub_email":xxxx,
"sub_password":xxxx;
"sub_at":xxxx;
```
Return:
```bash
 200: Succcessful
 500: The log-in password is incorrect
 404: Either the log-in email is incorrect or the to-be-created sub account already exists
```

#### Deleting a Sub Account

Request:
```bash
 /delete
```
with: JSON Object

Return:
```bash
 200: Succcessful
 500: The log-in password is incorrect
 404: Either the log-in email is incorrect or the to-be-deleted sub account does not exist
```

#### Updating the Email of a Sub Account

Request:
```bash
 /update_email
```
with: JSON Object

Add to the JSON Object
```bash
"sub_email":xxxx,
"sub_email_new":xxxx,
"sub_password":xxxx,
"sub_at":xxxx
```
Return:
```bash
 200: Succcessful
 500: The log-in password is incorrect
 404: Either the log-in email is incorrect or the to-be-updated sub account does not exist
```

#### Updating the Password of a Sub Account

Request:
```bash
 /update_password
```
with: JSON Object

Add to the JSON Object
```bash
"sub_email":xxxx,
"sub_password_new":xxxx,
"sub_password":xxxx,
"sub_at":xxxx
```
Return:
```bash
 200: Succcessful
 500: The log-in password is incorrect
 404: Either the log-in email is incorrect or the to-be-updated sub account does not exist
```

#### Requesting the Password of a specific Sub Account

Request:
```bash
 /get_password
```
with: JSON Object

Add to the JSON Object
```bash
"sub_email":xxxx,
"sub_at":xxxx
```
Return:
```bash
 "email not found": The log-in email is incorrect
 "failed login": The log-in password is incorrect
 "no sub account found": no sub accounts exist
 
 otherwise the password will be returned
```

#### Requesting all Subaccounts

Request:
```bash
 /get_all
```
with: JSON Object


Return:
```bash
  Json Object Array With one Element which has the attribute "successs" set to 0: Either log-in failed or no sub accounts found
 or
 Json Object Array with the appropriate information and attribute "success" set to 1 for each element
```

Format 
```bash
[
{"sucess":1,
"email":example_*****@gmail.com,
"at":Xcompany},
{"sucess":1,
"email":email.*****@gmail.com,
"at":Yplatform}
,
.
.
]
```

### Extra

#### Request an Auto-generated Password
Request:
```bash
 /generatePassword
```

Return:
The password will be returned as a String



 ### Currently NOT in deployment
