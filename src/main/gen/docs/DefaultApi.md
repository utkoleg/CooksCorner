# DefaultApi

All URIs are relative to */cookscorner*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**authActivateGet**](DefaultApi.md#authActivateGet) | **GET** /auth/activate | Activate a user account |
| [**authAuthenticatePost**](DefaultApi.md#authAuthenticatePost) | **POST** /auth/authenticate | Authenticate a user |
| [**authRegisterPost**](DefaultApi.md#authRegisterPost) | **POST** /auth/register | Register a new user |
| [**authResetPasswordPost**](DefaultApi.md#authResetPasswordPost) | **POST** /auth/reset-password | Request a password reset |
| [**authUpdateBioPost**](DefaultApi.md#authUpdateBioPost) | **POST** /auth/update-bio | Update user bio |
| [**authUpdateImagePost**](DefaultApi.md#authUpdateImagePost) | **POST** /auth/update-image | Update user profile image |
| [**authUpdatePasswordPost**](DefaultApi.md#authUpdatePasswordPost) | **POST** /auth/update-password | Update user password |
| [**idGet**](DefaultApi.md#idGet) | **GET** /{id} | Get a specific user |
| [**idPost**](DefaultApi.md#idPost) | **POST** /{id} | Follow a user |
| [**recipeCategoryGet**](DefaultApi.md#recipeCategoryGet) | **GET** /recipe/category | Get recipes by category |
| [**recipeGet**](DefaultApi.md#recipeGet) | **GET** /recipe | Get all recipes |
| [**recipeIdGet**](DefaultApi.md#recipeIdGet) | **GET** /recipe/{id} | Get a specific recipe |
| [**recipePost**](DefaultApi.md#recipePost) | **POST** /recipe | Add a new recipe |


<a name="authActivateGet"></a>
# **authActivateGet**
> String authActivateGet(token)

Activate a user account

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String token = "token_example"; // String | 
    try {
      String result = apiInstance.authActivateGet(token);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#authActivateGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **token** | **String**|  | |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User account activated |  -  |

<a name="authAuthenticatePost"></a>
# **authAuthenticatePost**
> AuthenticationResponse authAuthenticatePost(authenticationRequest)

Authenticate a user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    AuthenticationRequest authenticationRequest = new AuthenticationRequest(); // AuthenticationRequest | 
    try {
      AuthenticationResponse result = apiInstance.authAuthenticatePost(authenticationRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#authAuthenticatePost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **authenticationRequest** | [**AuthenticationRequest**](AuthenticationRequest.md)|  | |

### Return type

[**AuthenticationResponse**](AuthenticationResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User authenticated successfully |  -  |

<a name="authRegisterPost"></a>
# **authRegisterPost**
> AuthenticationResponse authRegisterPost(registerRequest)

Register a new user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    RegisterRequest registerRequest = new RegisterRequest(); // RegisterRequest | 
    try {
      AuthenticationResponse result = apiInstance.authRegisterPost(registerRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#authRegisterPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **registerRequest** | [**RegisterRequest**](RegisterRequest.md)|  | |

### Return type

[**AuthenticationResponse**](AuthenticationResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User registered successfully |  -  |

<a name="authResetPasswordPost"></a>
# **authResetPasswordPost**
> String authResetPasswordPost(email)

Request a password reset

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String email = "email_example"; // String | 
    try {
      String result = apiInstance.authResetPasswordPost(email);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#authResetPasswordPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **email** | **String**|  | |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Password reset email sent |  -  |

<a name="authUpdateBioPost"></a>
# **authUpdateBioPost**
> String authUpdateBioPost(bio, id)

Update user bio

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String bio = "bio_example"; // String | 
    UUID id = UUID.randomUUID(); // UUID | 
    try {
      String result = apiInstance.authUpdateBioPost(bio, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#authUpdateBioPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **bio** | **String**|  | |
| **id** | **UUID**|  | |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Bio updated successfully |  -  |

<a name="authUpdateImagePost"></a>
# **authUpdateImagePost**
> String authUpdateImagePost(image, id)

Update user profile image

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    File image = new File("/path/to/file"); // File | 
    UUID id = UUID.randomUUID(); // UUID | 
    try {
      String result = apiInstance.authUpdateImagePost(image, id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#authUpdateImagePost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **image** | **File**|  | [optional] |
| **id** | **UUID**|  | [optional] |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Image updated successfully |  -  |

<a name="authUpdatePasswordPost"></a>
# **authUpdatePasswordPost**
> String authUpdatePasswordPost(token, password)

Update user password

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String token = "token_example"; // String | 
    String password = "password_example"; // String | 
    try {
      String result = apiInstance.authUpdatePasswordPost(token, password);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#authUpdatePasswordPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **token** | **String**|  | |
| **password** | **String**|  | |

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Password updated successfully |  -  |

<a name="idGet"></a>
# **idGet**
> UserResponseDTO idGet(id)

Get a specific user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    UUID id = UUID.randomUUID(); // UUID | 
    try {
      UserResponseDTO result = apiInstance.idGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#idGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |

### Return type

[**UserResponseDTO**](UserResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User details |  -  |

<a name="idPost"></a>
# **idPost**
> UserResponseDTO idPost(id, userId)

Follow a user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    UUID id = UUID.randomUUID(); // UUID | 
    UUID userId = UUID.randomUUID(); // UUID | 
    try {
      UserResponseDTO result = apiInstance.idPost(id, userId);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#idPost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |
| **userId** | **UUID**|  | |

### Return type

[**UserResponseDTO**](UserResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | User followed successfully |  -  |

<a name="recipeCategoryGet"></a>
# **recipeCategoryGet**
> List&lt;Recipe&gt; recipeCategoryGet(id)

Get recipes by category

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String id = "id_example"; // String | 
    try {
      List<Recipe> result = apiInstance.recipeCategoryGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#recipeCategoryGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **String**|  | |

### Return type

[**List&lt;Recipe&gt;**](Recipe.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of recipes in the specified category |  -  |

<a name="recipeGet"></a>
# **recipeGet**
> List&lt;Recipe&gt; recipeGet()

Get all recipes

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    try {
      List<Recipe> result = apiInstance.recipeGet();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#recipeGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Recipe&gt;**](Recipe.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | List of recipes |  -  |

<a name="recipeIdGet"></a>
# **recipeIdGet**
> RecipeResponseDTO recipeIdGet(id)

Get a specific recipe

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    UUID id = UUID.randomUUID(); // UUID | 
    try {
      RecipeResponseDTO result = apiInstance.recipeIdGet(id);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#recipeIdGet");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | **UUID**|  | |

### Return type

[**RecipeResponseDTO**](RecipeResponseDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Recipe details |  -  |

<a name="recipePost"></a>
# **recipePost**
> UUID recipePost(name, description, difficulty, category, preparationTime, listOfIngredients, image)

Add a new recipe

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.DefaultApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("/cookscorner");

    DefaultApi apiInstance = new DefaultApi(defaultClient);
    String name = "name_example"; // String | 
    String description = "description_example"; // String | 
    String difficulty = "difficulty_example"; // String | 
    String category = "category_example"; // String | 
    String preparationTime = "preparationTime_example"; // String | 
    List<Ingredient> listOfIngredients = Arrays.asList(); // List<Ingredient> | 
    File image = new File("/path/to/file"); // File | 
    try {
      UUID result = apiInstance.recipePost(name, description, difficulty, category, preparationTime, listOfIngredients, image);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling DefaultApi#recipePost");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **name** | **String**|  | [optional] |
| **description** | **String**|  | [optional] |
| **difficulty** | **String**|  | [optional] |
| **category** | **String**|  | [optional] |
| **preparationTime** | **String**|  | [optional] |
| **listOfIngredients** | [**List&lt;Ingredient&gt;**](Ingredient.md)|  | [optional] |
| **image** | **File**|  | [optional] |

### Return type

[**UUID**](UUID.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: multipart/form-data
 - **Accept**: text/plain

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Recipe added successfully |  -  |

